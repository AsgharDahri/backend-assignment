package com.bayzdelivery.controller;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.DeliveryDto;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bayzdelivery.service.DeliveryService;

@RestController("/delivery")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Delivery>> createNewDelivery(@Valid @RequestBody DeliveryDto deliveryRequest) {
        ApiResponse<Delivery> response = deliveryService.createNewDeliveryOrder(deliveryRequest);
        HttpStatus status = response.isSuccess()
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

  @GetMapping(path = "/{delivery-id}")
  public ResponseEntity<Delivery> getDeliveryById(@PathVariable(name="delivery-id",required=true)Long deliveryId){
    Delivery delivery = deliveryService.findById(deliveryId);
    if (delivery !=null)
      return ResponseEntity.ok(delivery);
    return ResponseEntity.notFound().build();
  }
}



