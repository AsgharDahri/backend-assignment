package com.bayzdelivery.jobs;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class DelayedDeliveryNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(DelayedDeliveryNotifier.class);
    @Autowired
    private DeliveryRepository deliveryRepository;
    /**
     *  Use this method for the TASK 3
     */
    @Scheduled(fixedDelay = 30000)
    public void checkDelayedDeliveries() {
        LOG.info("Checking for delayed deliveries...");
        Instant cutoffTime = Instant.now().minus(Duration.ofMinutes(45));
        List<Delivery> delayedDeliveries = deliveryRepository
                .findByStartTimeBeforeAndStatusTrue(cutoffTime);
        StringBuilder message = new StringBuilder(128);
        for (Delivery delivery : delayedDeliveries) {
            message.setLength(0);
            message.append("Delivery from ")
                    .append(delivery.getDeliveryMan().getName())
                    .append(" to ")
                    .append(delivery.getCustomer().getName())
                    .append(" is delayed.");
            notifyCustomerSupport(message);
        }
    }


    /**
     * This method should be called to notify customer support team
     * It just writes notification on console but it may be email or push notification in real.
     * So that this method should run in an async way.
     */
    @Async
    public void notifyCustomerSupport(StringBuilder message) {
        LOG.info("Customer support team is notified:  {}", message);
    }
}
