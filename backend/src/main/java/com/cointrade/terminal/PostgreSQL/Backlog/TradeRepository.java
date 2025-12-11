package com.cointrade.terminal.PostgreSQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    // Find all trades for a specific user
    List<Trade> findByUser(User user);

    // Find trades by user and symbol
    List<Trade> findByUserAndSymbol(User user, String symbol);

    // Find recent trades for a user
    List<Trade> findByUserOrderByTimestampDesc(User user);

    // Find trades in a date range
    List<Trade> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);
}

