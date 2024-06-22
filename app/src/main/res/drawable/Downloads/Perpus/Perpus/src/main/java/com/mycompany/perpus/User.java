/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.perpus;

/**
 *
 * @author Akbar
 */
import java.util.ArrayList;
import java.util.Arrays;

// Interface for demonstrating multiple interfaces in one class
interface Authenticate {
    boolean authenticate(String password);
}

// User class implementing requirements
public class User extends AbstractItem implements Displayable, Authenticate {
    private String username;
    private String password;
    private String email;
    private int age;
    private boolean isAdmin;

    // ArrayList as a medium for storing data
    private ArrayList<String> roles = new ArrayList<>();

    // Constructor
    public User(int id, String username, String password, String email, int age, boolean isAdmin) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.isAdmin = isAdmin;
    }

    // Encapsulation with getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Overriding method from Displayable interface
    @Override
    public void display() {
        System.out.println("User: " + username + ", Email: " + email);
    }

    // Overriding method from Authenticatable interface
    @Override
    public boolean authenticate(String enteredPassword) {
        return password.equals(enteredPassword);
    }

    // Overloading method for polymorphism
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public void setRoles(String... roles) {
        this.roles.addAll(Arrays.asList(roles));
    }
}

