/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.perpus;

/**
 *
 * @author Akbar
 */

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Define the Book class
public class Book extends AbstractItem {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleIntegerProperty year;
    private SimpleDoubleProperty price;
    private SimpleStringProperty publisher;

    // Constructor
    public Book(String title, String author, Integer year, Double price, String publisher, Integer id) {
        super(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.year = new SimpleIntegerProperty(year);
        this.price = new SimpleDoubleProperty(price);
        this.publisher = new SimpleStringProperty(publisher);
    }


    // Getters and setters for encapsulation
//    public int getId() { return id.get(); }
//    public void setId(int id) { this.id = new SimpleIntegerProperty(id); }
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author = new SimpleStringProperty(author);
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year = new SimpleIntegerProperty(year);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public void setPublisher(String publisher) {
        this.publisher = new SimpleStringProperty(publisher);
    }

    // Strategy pattern: Display strategy
    private DisplayStrategy displayStrategy;

    public void setDisplayStrategy(DisplayStrategy displayStrategy) {
        this.displayStrategy = displayStrategy;
    }

    // Method for Displayable interface
    public void display() {
        if (displayStrategy != null) {
            displayStrategy.display(this);
        } else {
            System.out.println("Book: " + title + " by " + author);
            // Default display behavior
        }
    }
}


