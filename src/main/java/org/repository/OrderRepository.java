package org.repository;

import org.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreateDateBetween(final String customerId, final LocalDateTime startDate, final LocalDateTime endDate);
    Optional<Order> findByIdAndCustomerId(Long id, String customerId);
}
