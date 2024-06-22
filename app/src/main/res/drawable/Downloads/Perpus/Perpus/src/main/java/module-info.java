module com.mycompany.perpus {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.perpus to javafx.fxml;
    exports com.mycompany.perpus;
}
