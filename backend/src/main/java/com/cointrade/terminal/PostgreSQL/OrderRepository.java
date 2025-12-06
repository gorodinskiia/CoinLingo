package com.cointrade.terminal.PostgreSQL;

import org.springframework.core.OrderComparator.OrderSourceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.cointrade.terminal.PostgreSQL.User;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    // Find all orders for a specific user
    List<Orders> findByUser(User user);

    // Optional: Find orders by status
    List<Orders> findByStatus(String status);

    // Optional: Find orders by date range
    List<OrderSourceProvider> findByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
}
