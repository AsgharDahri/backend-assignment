package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RestResource(exported = false)
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Optional<Delivery> findByDeliveryManIdAndStatusTrue(Long deliveryManId);
}
