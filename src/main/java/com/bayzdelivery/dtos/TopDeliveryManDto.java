package com.bayzdelivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopDeliveryManDto {
    private Long deliveryManId;
    private String name;
    private Long totalCommission;
}