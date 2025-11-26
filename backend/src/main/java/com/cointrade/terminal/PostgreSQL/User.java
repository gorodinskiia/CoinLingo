package com.cointrade.terminal.PostgreSQL;

import jakarta.persistence.*; // JPA annotations for entity mapping 
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime;
import java.math.BigDecimal;
// Import your entities
import com.cointrade.terminal.PostgreSQL.Orders;
import com.cointrade.terminal.PostgreSQL.Trade;
import com.cointrade.terminal.PostgreSQL.Balance;  


//This Class represents a User entity mapped to the "users" table in the database
//Users table will store user information such as username, password, email, and daily streak

@Entity //Makes a class a JPA entity or Database Table 
@Table(name = "users") //Specifies the name of the database table to be used for mapping
public class User {

    @Id //Primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-generates the primary key value and auto-increments it   
    private Long id;  // primary key

    @Column(nullable = false, unique = true) // username must be unique and not null
    private String username;

    @Column(nullable = false) // password cannot be null
    private String password;

    @Column(nullable = false, unique = true) // email must be unique and not null
    private String email;

    @Column(nullable = false) // dailyStreak cannot be null
    private int dailyStreak;

    // One-to-many relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Balance> balances = new ArrayList<>();

    public User() 
    {

    }

    public User(String username, String password, String email, int dailyStreak) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dailyStreak = dailyStreak;
    }

    // ----- Getters & Setters -----
    public Long getId() 
    { 
        return id; 
    }

    public String getUsername() 
    { 
        return username; 
    }

    public void setUsername(String username) 
    { 
        this.username = username; 
    }

    public String getPassword() 
    { 
        return password; 
    }

    public void setPassword(String password) 
    { 
        this.password = password; 
    }

    public String getEmail() 
    { 
        return email; 
    }

    public void setEmail(String email) 
    { 
        this.email = email; 
    }

    public int getDailyStreak() 
    { 
        return dailyStreak; 
    }
    
    public void setDailyStreak(int dailyStreak) 
    { 
        this.dailyStreak = dailyStreak; 
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }
}

