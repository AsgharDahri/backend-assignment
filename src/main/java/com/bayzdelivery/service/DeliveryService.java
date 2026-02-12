package com.bayzdelivery.service;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.DeliveryDto;
import com.bayzdelivery.model.Delivery;

public interface DeliveryService {

  public ApiResponse<Delivery> createNewDelivery(DeliveryDto deliveryRequest);

    public ApiResponse<Delivery> orderDelivered(Long deliveryId);
  public Delivery findById(Long deliveryId);
}
