package com.bayzdelivery.service;

import java.time.Instant;
import java.util.Optional;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.DeliveryDto;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.utilites.Formulae;
import com.bayzdelivery.utilites.PERSON_TYPE;
import org.springframework.beans.factory.annotation.Autowired;
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

  public Delivery findById(Long deliveryId) {
    Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
    if (optionalDelivery.isPresent()) {
      return optionalDelivery.get();
    }else return null;
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

          Delivery updatedDelivery = deliveryRepository.save(lastDelivery.get());

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

}
