package com.cointrade.terminal.PostgreSQL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5176"}) // Allow frontend to access
public class WalletController {

    private final WalletService walletService;
    private final UserRepository userRepository;

    public WalletController(WalletService walletService, UserRepository userRepository) {
        this.walletService = walletService;
        this.userRepository = userRepository;
    }

    /**
     * GET /api/wallet/{username}
     * Get wallet data for a user
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getWallet(@PathVariable String username) {
        try {
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

            // Initialize wallet if needed
            walletService.initializeWallet(user);

            WalletDTO wallet = walletService.getWallet(user);
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/wallet/{username}/trade
     * Execute a trade (buy or sell)
     */
    @PostMapping("/{username}/trade")
    public ResponseEntity<?> executeTrade(@PathVariable String username,
                                         @RequestBody TradeRequest tradeRequest) {
        try {
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

            Trade trade;

            if ("buy".equalsIgnoreCase(tradeRequest.getSide())) {
                trade = walletService.executeBuyOrder(
                    user,
                    tradeRequest.getAsset(),
                    tradeRequest.getAmount(),
                    tradeRequest.getPrice()
                );
            } else if ("sell".equalsIgnoreCase(tradeRequest.getSide())) {
                trade = walletService.executeSellOrder(
                    user,
                    tradeRequest.getAsset(),
                    tradeRequest.getAmount(),
                    tradeRequest.getPrice()
                );
            } else {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid side: " + tradeRequest.getSide()));
            }

            // Return updated wallet data
            WalletDTO wallet = walletService.getWallet(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("trade", trade);
            response.put("wallet", wallet);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    /**
     * POST /api/wallet/{username}/reset
     * Reset wallet to initial $5000 state
     */
    @PostMapping("/{username}/reset")
    public ResponseEntity<?> resetWallet(@PathVariable String username) {
        try {
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

            walletService.resetWallet(user);

            WalletDTO wallet = walletService.getWallet(user);
            return ResponseEntity.ok(Map.of("success", true, "wallet", wallet));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    /**
     * GET /api/wallet/{username}/history
     * Get trade history for a user
     */
    @GetMapping("/{username}/history")
    public ResponseEntity<?> getTradeHistory(@PathVariable String username) {
        try {
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

            List<Trade> trades = walletService.getTradeHistory(user);
            return ResponseEntity.ok(trades);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }
}

