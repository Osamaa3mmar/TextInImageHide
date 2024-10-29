module com.example.textinimagehideosama {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.textinimagehideosama to javafx.fxml;
    exports com.example.textinimagehideosama;
}