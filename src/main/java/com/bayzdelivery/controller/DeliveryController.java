package com.bayzdelivery.controller;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.DeliveryDto;
import com.bayzdelivery.model.Delivery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bayzdelivery.service.DeliveryService;

import java.time.Instant;
import java.util.Map;

@RestController("/delivery")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

    //Create delivery.
    @PostMapping
    public ResponseEntity<ApiResponse<Delivery>> createNewDeliveryOrder(@Valid @RequestBody DeliveryDto deliveryRequest) {
        ApiResponse<Delivery> response = deliveryService.createNewDelivery(deliveryRequest);
        HttpStatus status = response.isSuccess()
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    // when delivery delevered.
    @PutMapping
    public ResponseEntity<ApiResponse<Delivery>> deliveredOrder(@RequestParam Long deliveryId) {
        ApiResponse<Delivery> response = deliveryService.orderDelivered(deliveryId);
        HttpStatus status = response.isSuccess()
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

  @GetMapping(path = "/{delivery-id}")
  public ResponseEntity<ApiResponse<Object>> getDeliveryById(@PathVariable(name="delivery-id",required=true)Long deliveryId){
      ApiResponse<Object> delivery = deliveryService.findById(deliveryId);
      return ResponseEntity.ok(delivery);
  }

    @GetMapping("/top-delivery-man")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTopDeliveryMan(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate) {

        return ResponseEntity.ok(deliveryService.getTopDeliveryMan(startDate, endDate));
    }
}



