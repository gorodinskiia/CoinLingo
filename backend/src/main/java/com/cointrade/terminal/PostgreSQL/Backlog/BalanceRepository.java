package com.cointrade.terminal.PostgreSQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    // Find all balances for a specific user
    List<Balance> findByUser(User user);

    // Find a specific balance by user and asset
    Optional<Balance> findByUserAndAsset(User user, String asset);

    // Delete all balances for a user (for wallet reset)
    void deleteByUser(User user);
}

