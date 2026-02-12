package com.bayzdelivery.service;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.DeliveryDto;
import com.bayzdelivery.model.Delivery;

public interface DeliveryService {

  public ApiResponse<Delivery> createNewDeliveryOrder(DeliveryDto deliveryRequest);

  public Delivery findById(Long deliveryId);
}
