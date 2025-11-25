// package com.cointrade.terminal.PostgreSQL;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// //This Class represents a Trade entity mapped to the "trades" table in the database
// //Trades table will store trade information such as symbol, quantity, price, timestamp, and the user who made the trade

// @Entity
// @Table(name = "trades")
// public class Trade {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id; // primary key

//     private String symbol;    // e.g. "BTC"
//     private Double quantity;  // e.g. 0.5 BTC
//     private Double price; // e.g. 45000.00 USD

//     private LocalDateTime timestamp; // when the trade was executed

//     @ManyToOne // relationship to User entity
//     @JoinColumn(name = "user_id") // foreign key column
//     private User user; // who made the trade

//     public Trade() {}

//     public Trade(String symbol, Double quantity, Double price, User user) {
//         this.symbol = symbol;
//         this.quantity = quantity;
//         this.price = price;
//         this.timestamp = LocalDateTime.now();
//         this.user = user;
//     }


//     // getters & setters
//     public Long getTradeId() { return tradeId; }
//     public void setTradeId(Long tradeId) { this.tradeId = tradeId; }

//     public Long getOrderId() { return orderId; }
//     public void setOrderId(Long orderId) { this.orderId = orderId; }

//     public String getUsername() { return username; }
//     public void setUsername(String username) { this.username = username; }

//     public String getSymbol() { return symbol; }
//     public void setSymbol(String symbol) { this.symbol = symbol; }

//     public double getQuantity() { return quantity; }
//     public void setQuantity(double quantity) { this.quantity = quantity; }

//     public double getPrice() { return price; }
//     public void setPrice(double price) { this.price = price; }

//     public long getTimestamp() { return timestamp; }
//     public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
// }
