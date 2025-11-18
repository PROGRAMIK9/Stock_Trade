package com.stocktrading.models;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> 40f29cea799d793883ef09eb7b60676533eb5699

/**
 * User class demonstrating inheritance from Person
 */
public class User extends Person {
    private String username;
    private String password;
    private Portfolio portfolio;
    
    public User(String username, String password, String name, String email) {
        super(name, email);
        this.username = username;
        this.password = password;
        this.portfolio = new Portfolio(10000.0); // Starting with $10,000
<<<<<<< HEAD
    }
=======
        
    }
    //constructor with ID (when loading from database)
>>>>>>> 40f29cea799d793883ef09eb7b60676533eb5699
    
    public User(int id, String username, String password, String name, String email) {
        super(id, name, email);
        this.username = username;
        this.password = password;
    }
<<<<<<< HEAD
    
    @Override
    public String getRole() {
        return "TRADER";
    }
    
    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Portfolio getPortfolio() { return portfolio; }
    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }
=======

    //Constructor with all fields (for database loading with portfolio)
    public User(int id, String username, String password, String name, String email, Portfolio portfolio) {
        super(id, name, email);
        this.username = username;
        this.password = password;
        this.portfolio = portfolio;

    }
    
>>>>>>> 40f29cea799d793883ef09eb7b60676533eb5699
    
    @Override
    public String toString() {
        return "User{username='" + username + "', name='" + name + "', email='" + email + "'}";
    }
<<<<<<< HEAD
}
=======
        public String getRole() {
            return "TRADER";
        }
        //Getters and setters
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;

        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public Portfolio getPortfolio() {
            return portfolio;
        }
        public void setPortfolio(Portfolio portfolio) {
            this.portfolio = portfolio;
        }


    }

>>>>>>> 40f29cea799d793883ef09eb7b60676533eb5699
