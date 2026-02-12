package com.bayzdelivery.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be greater than 0")
    private Long distance;
    @NotNull(message = "Price is required")
    @Positive(message = "Distance must be greater than 0")
    private Long orderPrice;
    @NotNull(message = "Delivery man ID is required")
    private Long deliveryManId;
    @NotNull(message = "Customer ID is required")
    private Long customerId;

}