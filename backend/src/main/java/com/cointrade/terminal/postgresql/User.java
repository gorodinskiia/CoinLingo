package com.cointrade.terminal.PostgreSQL;

import jakarta.persistence.*; // JPA annotations for entity mapping 

//This Class represents a User entity mapped to the "users" table in the database
//Users table will store user information such as username, password, email, and daily streak

@Entity //Makes a class a JPA entity or Database Table 
@Table(name = "users") //Specifies the name of the database table to be used for mapping
public class User {

    @Id //Primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-generates the primary key value
    private Long id;  // primary key

    @Column(nullable = false, unique = true) // username must be unique and not null
    private String username;

    @Column(nullable = false) // password cannot be null
    private String password;

    @Column(nullable = false, unique = true) // email must be unique and not null
    private String email;

    @Column(nullable = false) // dailyStreak cannot be null
    private int dailyStreak;

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
}

