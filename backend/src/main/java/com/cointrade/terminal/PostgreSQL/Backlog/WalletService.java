package com.cointrade.terminal.PostgreSQL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WalletService {

    private final BalanceRepository balanceRepository;
    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;

    private static final BigDecimal INITIAL_USD_BALANCE = new BigDecimal("5000.00");

    public WalletService(BalanceRepository balanceRepository,
                        TradeRepository tradeRepository,
                        UserRepository userRepository) {
        this.balanceRepository = balanceRepository;
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Initialize wallet with $5000 USD if user has no balances
     */
    @Transactional
    public void initializeWallet(User user) {
        List<Balance> existingBalances = balanceRepository.findByUser(user);

        if (existingBalances.isEmpty()) {
            // Create initial USD balance
            Balance usdBalance = new Balance(user, "USD", INITIAL_USD_BALANCE, INITIAL_USD_BALANCE);
            balanceRepository.save(usdBalance);

            // Create zero balances for crypto assets
            balanceRepository.save(new Balance(user, "BTC", BigDecimal.ZERO, BigDecimal.ZERO));
            balanceRepository.save(new Balance(user, "ETH", BigDecimal.ZERO, BigDecimal.ZERO));
            balanceRepository.save(new Balance(user, "XRP", BigDecimal.ZERO, BigDecimal.ZERO));
        }
    }

    /**
     * Get wallet data for a user
     */
    public WalletDTO getWallet(User user) {
        List<Balance> balances = balanceRepository.findByUser(user);

        // Convert to map
        Map<String, BigDecimal> balanceMap = new HashMap<>();
        BigDecimal portfolioValue = BigDecimal.ZERO;

        for (Balance balance : balances) {
            balanceMap.put(balance.getAsset(), balance.getAmount());
            if (balance.getUsdValue() != null) {
                portfolioValue = portfolioValue.add(balance.getUsdValue());
            }
        }

        // Calculate P&L
        BigDecimal profitLoss = portfolioValue.subtract(INITIAL_USD_BALANCE);
        Double profitLossPercentage = profitLoss.divide(INITIAL_USD_BALANCE, 4, BigDecimal.ROUND_HALF_UP)
                                                .multiply(new BigDecimal("100"))
                                                .doubleValue();

        return new WalletDTO(balanceMap, portfolioValue, profitLoss, profitLossPercentage);
    }

    /**
     * Execute a buy order
     */
    @Transactional
    public Trade executeBuyOrder(User user, String asset, BigDecimal amount, BigDecimal price) {
        // Calculate total cost
        BigDecimal totalCost = amount.multiply(price);

        // Get USD balance
        Balance usdBalance = balanceRepository.findByUserAndAsset(user, "USD")
            .orElseThrow(() -> new RuntimeException("USD balance not found"));

        // Check sufficient funds
        if (usdBalance.getAmount().compareTo(totalCost) < 0) {
            throw new RuntimeException("Insufficient USD balance");
        }

        // Get or create asset balance
        Balance assetBalance = balanceRepository.findByUserAndAsset(user, asset)
            .orElse(new Balance(user, asset, BigDecimal.ZERO, BigDecimal.ZERO));

        // Update balances
        usdBalance.setAmount(usdBalance.getAmount().subtract(totalCost));
        usdBalance.setUsdValue(usdBalance.getAmount()); // USD value equals amount

        assetBalance.setAmount(assetBalance.getAmount().add(amount));
        assetBalance.setUsdValue(assetBalance.getAmount().multiply(price));

        balanceRepository.save(usdBalance);
        balanceRepository.save(assetBalance);

        // Create trade record
        Trade trade = new Trade(asset, amount.doubleValue(), price.doubleValue(), user);
        return tradeRepository.save(trade);
    }

    /**
     * Execute a sell order
     */
    @Transactional
    public Trade executeSellOrder(User user, String asset, BigDecimal amount, BigDecimal price) {
        // Calculate total revenue
        BigDecimal totalRevenue = amount.multiply(price);

        // Get asset balance
        Balance assetBalance = balanceRepository.findByUserAndAsset(user, asset)
            .orElseThrow(() -> new RuntimeException(asset + " balance not found"));

        // Check sufficient assets
        if (assetBalance.getAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient " + asset + " balance");
        }

        // Get USD balance
        Balance usdBalance = balanceRepository.findByUserAndAsset(user, "USD")
            .orElseThrow(() -> new RuntimeException("USD balance not found"));

        // Update balances
        assetBalance.setAmount(assetBalance.getAmount().subtract(amount));
        assetBalance.setUsdValue(assetBalance.getAmount().multiply(price));

        usdBalance.setAmount(usdBalance.getAmount().add(totalRevenue));
        usdBalance.setUsdValue(usdBalance.getAmount());

        balanceRepository.save(assetBalance);
        balanceRepository.save(usdBalance);

        // Create trade record (negative quantity for sell)
        Trade trade = new Trade(asset, -amount.doubleValue(), price.doubleValue(), user);
        return tradeRepository.save(trade);
    }

    /**
     * Reset wallet to initial state
     */
    @Transactional
    public void resetWallet(User user) {
        // Delete all existing balances
        balanceRepository.deleteByUser(user);

        // Re-initialize with $5000
        initializeWallet(user);
    }

    /**
     * Get trade history for a user
     */
    public List<Trade> getTradeHistory(User user) {
        return tradeRepository.findByUserOrderByTimestampDesc(user);
    }

    /**
     * Update USD values for all crypto balances (called when prices change)
     */
    @Transactional
    public void updateUsdValues(User user, Map<String, BigDecimal> prices) {
        List<Balance> balances = balanceRepository.findByUser(user);

        for (Balance balance : balances) {
            if (!balance.getAsset().equals("USD") && prices.containsKey(balance.getAsset())) {
                BigDecimal price = prices.get(balance.getAsset());
                balance.setUsdValue(balance.getAmount().multiply(price));
                balanceRepository.save(balance);
            }
        }
    }
}

