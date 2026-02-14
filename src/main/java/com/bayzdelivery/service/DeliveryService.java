package com.bayzdelivery.service;

import com.bayzdelivery.dtos.ApiResponse;
import com.bayzdelivery.dtos.DeliveryDto;
import com.bayzdelivery.model.Delivery;

import java.time.Instant;
import java.util.Map;

public interface DeliveryService {
    ApiResponse<Delivery> createNewDelivery(DeliveryDto deliveryRequest);
    ApiResponse<Delivery> orderDelivered(Long deliveryId);
    ApiResponse<Object> findById(Long deliveryId);
    ApiResponse<Map<String, Object>> getTopDeliveryMan(Instant startDate, Instant endDate);
}
