module com.example.textinimagehideosama {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.textinimagehideosama to javafx.fxml;
    exports com.example.textinimagehideosama;
}