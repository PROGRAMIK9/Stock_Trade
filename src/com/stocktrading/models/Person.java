package com.stocktrading.models;

import java.io.Serializable;

/**
 * Base class demonstrating inheritance
 */
public abstract class Person implements Serializable {
    protected int id;
    protected String name;
    protected String email;
    
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public Person(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    // Abstract method to be implemented by subclasses
    public abstract String getRole();
    
    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
