package com.bayzdelivery.repositories;
import org.springframework.data.domain.Pageable;
import com.bayzdelivery.dtos.TopDeliveryManDto;
import com.bayzdelivery.model.Delivery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestResource(exported = false)
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Optional<Delivery> findByDeliveryManIdAndStatusTrue(Long deliveryManId);

    @Query("SELECT new com.bayzdelivery.dtos.TopDeliveryManDto(d.deliveryMan.id, d.deliveryMan.name, SUM(d.commission)) " +
            "FROM Delivery d " +
            "WHERE d.startTime BETWEEN :startDate AND :endDate " +
            "GROUP BY d.deliveryMan.id, d.deliveryMan.name " +
            "ORDER BY SUM(d.commission) DESC")
    List<TopDeliveryManDto> findTop3DeliveryMenByCommission(Instant startDate, Instant endDate, Pageable pageable);

    @Query("SELECT AVG(d.commission) FROM Delivery d WHERE d.startTime BETWEEN :startDate AND :endDate")
    Double getAverageCommissionInInterval(Instant startDate, Instant endDate);

    List<Delivery> findByStartTimeBeforeAndStatusTrue(Instant cutoffTime);

}
