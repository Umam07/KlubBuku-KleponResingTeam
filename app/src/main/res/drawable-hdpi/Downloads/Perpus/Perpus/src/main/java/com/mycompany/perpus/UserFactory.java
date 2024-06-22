/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.perpus;

/**
 *
 * @author Akbar
 */
public class UserFactory {
    public static User createUser(int id, String username, String password, String email, int age, boolean isAdmin) {
        return new User(id, username, password, email, age, isAdmin);
    }
}
