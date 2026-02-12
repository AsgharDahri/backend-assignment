package com.bayzdelivery.controller;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.DeliveryDto;
import com.bayzdelivery.model.Delivery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bayzdelivery.service.DeliveryService;

@RestController("/delivery")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Delivery>> createNewDeliveryOrder(@Valid @RequestBody DeliveryDto deliveryRequest) {
        ApiResponse<Delivery> response = deliveryService.createNewDelivery(deliveryRequest);
        HttpStatus status = response.isSuccess()
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    // When order delivered,
    @PutMapping
    public ResponseEntity<ApiResponse<Delivery>> deliveredOrder(@RequestParam Long deliveryId) {
        ApiResponse<Delivery> response = deliveryService.orderDelivered(deliveryId);
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



