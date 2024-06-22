/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.perpus;

/**
 *
 * @author Akbar
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField txtUsername;
    public TextField txtPass;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static List<User> listUser = new ArrayList<>();
    @FXML
    protected void onExitButtonClick(){ System.exit(0); }
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        //Get username and password user from textField
        String username = txtUsername.getText();
        String password = txtPass.getText();
        //Check if username and password is correct from list user in BookStoreApplication
        for (User user : listUser) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                //If username and password is correct, change scene to crud-view.fxml
                changeScene(event);
                return;
            }
        }
        //If username and password is incorrect, show error with error dialog in bookStoreApplication
        LibraryApplication.ErrorDialog(event, "Username or password is incorrect", "Error");
        //Clear text field
        clearTextField();
    }
    private void changeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("crud-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("CRUD Page");
        stage.setScene(scene);
        stage.show();
    }

    //create private method to clear text field
    private void clearTextField(){
        txtUsername.clear();
        txtPass.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = UserFactory.createUser(1, "admin", "admin123", "user@example.com", 25, true);
        User user2 = UserFactory.createUser(2, "librarian", "hesoyam", "user@example.com", 25, false);

        // Displaying user information
        user.display();

        // Adding roles using overloaded methods
        user.setRoles("ROLE_USER", "ROLE_EDITOR");

        //insert user into list user
        listUser.add(user);
        listUser.add(user2);
    }
}
