/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.perpus;

/**
 *
 * @author Akbar
 */

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


// Abstract class for common attributes
abstract class AbstractItem {
    private SimpleIntegerProperty id;

    // Constructor
    public AbstractItem(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    // Getter and setter for id
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }
}

// Interface for demonstration
interface Displayable {
    void display();
}
public class LibraryApplication extends Application {
    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        scene = new Scene(root);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
        System.out.println();
    }
    //Create list user

    public static void main(String[] args) {
        launch(args);
        System.out.println();
    }

    @FXML
    public static void ErrorDialog(ActionEvent event, String contextText, String headerText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR ALERT!!");
        alert.setContentText(contextText);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    @FXML
    public static boolean ConfirmationDialog(ActionEvent event, String contextText, String headerText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(contextText);
        alert.setHeaderText(headerText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
}
