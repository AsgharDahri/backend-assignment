package com.bayzdelivery.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.DeliveryDto;
import com.bayzdelivery.dtos.TopDeliveryManDto;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.utilites.Formulae;
import com.bayzdelivery.utilites.PERSON_TYPE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

  @Autowired
  DeliveryRepository deliveryRepository;

  @Autowired
  PersonService personService;

  public ApiResponse<Delivery> createNewDelivery(DeliveryDto deliveryRequest) {
      try{
          Optional<Person> deliveryMan = personService.findPersonByIdAndType
                  (deliveryRequest.getDeliveryManId(), PERSON_TYPE.DELIVERY_MAN);
          if(deliveryMan.isEmpty()) {
              throw new Exception("Invalid delivery man id");
          }
          if(getLastOrderByDeliveryManId(deliveryMan.get().getId()).isPresent()){
              throw new Exception("Delivery man already have order");
          }
          Optional<Person> customer = personService.findPersonByIdAndType
                  (deliveryRequest.getCustomerId(),  PERSON_TYPE.CUSTOMER);
          if(customer.isEmpty()) {
              throw new Exception("Invalid customer id");
          }
          Long orderCommission = Formulae.deliverCommissionFormula
                  (deliveryRequest.getOrderPrice(), deliveryRequest.getDistance());
          Delivery deliverModal = Delivery.builder()
                  .distance(deliveryRequest.getDistance())
                  .price(deliveryRequest.getOrderPrice())
                  .commission(orderCommission)
                  .deliveryMan(deliveryMan.get())
                  .customer(customer.get())
                  .startTime(Instant.now())
                  //Assuming the expected time to reach, will be updated when order delivered or PUT API runs
                  .endTime(Instant.now().plusSeconds(3600))
                  .build();
          Delivery savedDelivery = deliveryRepository.save(deliverModal);

          return ApiResponse.<Delivery>builder()
                  .success(true)
                  .message("Delivery Created successfully")
                  .data(savedDelivery)
                  .build();
      }
      catch(Exception e){

          return ApiResponse.<Delivery>builder()
                  .success(false)
                  .message(e.getMessage())
                  .data(null)
                  .build();
      }
  }

  public ApiResponse<Object> findById(Long deliveryId) {
      try{
          Optional<Delivery> delivery = deliveryRepository.findById(deliveryId);
          if (delivery.isPresent()) {

              return ApiResponse.<Object>builder()
                      .success(true)
                      .message("Delivery fetched successfully")
                      .data(delivery)
                      .build();
          }

          return ApiResponse.<Object>builder()
                  .success(false)
                  .message("Delivery not found")
                  .data(null)
                  .build();
      }
      catch (Exception e){

          return ApiResponse.<Object>builder()
                  .success(false)
                  .message(e.getMessage())
                  .data(null)
                  .build();
      }

  }

  @Override
  public ApiResponse<Delivery> orderDelivered(Long deliveryId){
      try{
          Optional<Delivery> lastDelivery = getLastOrderByDeliveryId(deliveryId);
          if(lastDelivery.isEmpty() || !lastDelivery.get().isStatus()){
              throw new Exception("Delivery Not Found or Already Delivered");
          }
          lastDelivery.get().setEndTime(Instant.now());
          lastDelivery.get().setStatus(false); //delivered so status false.
          deliveryRepository.save(lastDelivery.get());

          return ApiResponse.<Delivery>builder()
                  .success(true)
                  .message("Delivery Delivered successfully")
                  .data(null)
                  .build();
      }
      catch (Exception e){

          return ApiResponse.<Delivery>builder()
                  .success(false)
                  .message(e.getMessage())
                  .data(null)
                  .build();
      }
  }

  private Optional<Delivery> getLastOrderByDeliveryManId(Long deliveryManId){

      return deliveryRepository.findByDeliveryManIdAndStatusTrue(deliveryManId);
  }

    private Optional<Delivery> getLastOrderByDeliveryId(Long deliveryId){

        return deliveryRepository.findById(deliveryId);
    }
    public ApiResponse<Map<String, Object>> getTopDeliveryMan(Instant startDate, Instant endDate) {
        try {
            startDate = startDate.minusMillis(1);
            endDate = endDate.plusMillis(1);
            List<TopDeliveryManDto> topEarners = deliveryRepository.findTop3DeliveryMenByCommission(
                    startDate, endDate, PageRequest.of(0, 3));
            Double avgCommission = deliveryRepository.getAverageCommissionInInterval(startDate, endDate);
            Map<String, Object> report = new HashMap<>();
            report.put("topEarners", topEarners);
            report.put("averageCommission", avgCommission != null ? avgCommission : 0.0);

            return ApiResponse.<Map<String, Object>>builder()
                    .success(true)
                    .message("Report generated successfully")
                    .data(report)
                    .build();
        } catch (Exception e) {

            return ApiResponse.<Map<String, Object>>builder()
                    .success(false)
                    .message("Error generating report: " + e.getMessage())
                    .build();
        }
    }
}
