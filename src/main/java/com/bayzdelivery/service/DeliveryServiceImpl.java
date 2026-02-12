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

  public ApiResponse<Delivery> createNewDeliveryOrder(DeliveryDto deliveryRequest) {
      try{
          Optional<Person> deliveryMan = personService.findPersonByIdAndType
                  (deliveryRequest.getDeliveryManId(), PERSON_TYPE.DELIVERY_MAN);

          if(deliveryMan.isEmpty()) {
              throw new Exception("Invalid delivery man id");
          }
          if(getLastOrder(deliveryMan.get().getId()).isPresent()){
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
                  .message("Delivery Order Created successfully")
                  .data(savedDelivery)
                  .build();
      }
      catch(Exception e){

          return ApiResponse.<Delivery>builder()
                  .success(false)
                  .message("EError Occurred while creating new delivery: " + e.getMessage())
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

  private Optional<Delivery> getLastOrder(Long deliveryManId){
      return deliveryRepository.findByDeliveryManIdAndStatusTrue(deliveryManId);
  }

}
