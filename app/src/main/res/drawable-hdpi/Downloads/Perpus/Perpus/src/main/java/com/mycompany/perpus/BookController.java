/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.perpus;

/**
 *
 * @author Akbar
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    public TableColumn<Book,Integer> colId;
    public TableColumn<Book,String> colTitle;
    public TableColumn<Book,String> colAuthor;
    public TableColumn<Book,Integer> colYear;
    public TableColumn<Book,Double> colPrice;
    public TableColumn<Book,String> colPublisher;
    @FXML
    public TextField txtId;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtYear;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtPublisher;
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnDeleteBook;
    @FXML
    private Button btnUpdateBook;
    @FXML
    private TableView<Book> tableBook;

    //Use a Hashmap to store the books objects with ID as the key
    private Map<Integer, Book> bookMap = new HashMap<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int bookId = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));

        txtId.setText(String.valueOf(bookId));
    }
    @FXML
    protected void onLogoutButtonClick(ActionEvent event) throws IOException {
        changeScene(event);
    }

    private void changeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("CRUD Page");
        stage.setScene(scene);
        stage.show();
    }
    private Book getFormInput(){
        // Get input from text fields
        String idText = txtId.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String yearText = txtYear.getText();
        String priceText = txtPrice.getText();
        String publisher = txtPublisher.getText();

        // Validate data
        if (idText.isEmpty() || title.isEmpty() || author.isEmpty() || yearText.isEmpty() || priceText.isEmpty() || publisher.isEmpty()) {
            // Handle the case where any field is empty
            LibraryApplication.ErrorDialog(null, "All fields must be filled", "Input Error");
            clearTextFields();
            throw new IllegalArgumentException("All fields must be filled");
        }

        try {
            // Validate data types
            int id = Integer.parseInt(idText);
            int year = Integer.parseInt(yearText);
            double price = Double.parseDouble(priceText);

            // Create and return Book object
            return new Book(title, author, year, price, publisher, id);
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails (e.g., if id, year, or price are not valid integers/doubles)
            LibraryApplication.ErrorDialog(null, "Invalid input for ID, year, or price", "Input Error");
            clearTextFields();
            throw new NumberFormatException("Invalid input for ID, year, or price");
        }
    }

    @FXML
    private void btnAddBookAction() {
        Book book = getFormInput();
        addBookToMap(book);
        addBookToTableView(book);
        book.display();
    }

    @FXML
    private void btnUpdateBookAction() {
        ObservableList<Book> currentTableData = tableBook.getItems();

        // Get input from text fields
        String idText = txtId.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String yearText = txtYear.getText();
        String priceText = txtPrice.getText();
        String publisher = txtPublisher.getText();

        // Validate data
        if (idText.isEmpty() || title.isEmpty() || author.isEmpty() || yearText.isEmpty() || priceText.isEmpty() || publisher.isEmpty()) {
            // Handle the case where any field is empty
            LibraryApplication.ErrorDialog(null, "All fields must be filled", "Input Error");
            return;
        }

        try {
            // Validate data types
            int currentBookId = Integer.parseInt(idText);
            double price = Double.parseDouble(priceText);
            int year = Integer.parseInt(yearText);

            if (bookMap.containsKey(currentBookId)) {
                Book book = bookMap.get(currentBookId);
                book.setAuthor(author);
                book.setPrice(price);
                book.setYear(year);
                book.setPublisher(publisher);
                book.setTitle(title);

                // Update the table view and clear text fields
                tableBook.setItems(FXCollections.observableArrayList(bookMap.values()));
                tableBook.refresh();
                clearTextFields();
            } else {
                LibraryApplication.ErrorDialog(null, "Book with ID " + currentBookId + " not found", "Book Not Found");
            }

        } catch (NumberFormatException e) {
            // Handle the case where parsing fails (e.g., if id, year, or price are not valid integers/doubles)
            LibraryApplication.ErrorDialog(null, "Invalid input for ID, year, or price", "Input Error");
        }
    }

    @FXML
    private void btnDeleteBookAction(ActionEvent event){
        int selectedId = tableBook.getSelectionModel().getSelectedIndex();
        boolean isConfirm = LibraryApplication.ConfirmationDialog(event,"Apakah anda yakin ingin menghapus data tersebut?","Delete Confirmation");
        if (isConfirm) {
            Book selectedBook = tableBook.getItems().get(selectedId);
            int bookIdToRemove = selectedBook.getId();
            bookMap.remove(bookIdToRemove);
            tableBook.setItems(FXCollections.observableArrayList(bookMap.values()));
        }
        clearTextFields();
    }

    @FXML
    void rowClicked(MouseEvent event){
        Book clickedBook = tableBook.getSelectionModel().getSelectedItem();
        if (clickedBook != null) {
            txtId.setText(String.valueOf(clickedBook.getId()));
            txtAuthor.setText(String.valueOf(clickedBook.getAuthor()));
            txtPrice.setText(String.valueOf(clickedBook.getPrice()));
            txtTitle.setText(String.valueOf(clickedBook.getTitle()));
            txtYear.setText(String.valueOf(clickedBook.getYear()));
            txtPublisher.setText(String.valueOf(clickedBook.getPublisher()));
        }
    }

    // Method to add a book to the Map
    private void addBookToMap(Book book) {
        bookMap.put(book.getId(), book);
    }

    // Method to add a book to the TableView
    private void addBookToTableView(Book book) {
        tableBook.setItems(FXCollections.observableArrayList(bookMap.values()));
        bookId++;
        clearTextFields();
    }

    // Clear text fields
    @FXML
    private void clearTextFields() {
        txtTitle.clear();
        txtAuthor.clear();
        txtYear.clear();
        txtPrice.clear();
        txtPublisher.clear();
        txtId.setText(String.valueOf(bookId));
    }



}
