/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.perpus;

/**
 *
 * @author Akbar
 */
public class ConsoleDisplayStrategy implements DisplayStrategy {
    @Override
    public void display(Book book) {
        System.out.println("Book: " + book.getTitle() + " by " + book.getAuthor());
    }
}
