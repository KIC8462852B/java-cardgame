module com.example.baraja_cartas_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.baraja_cartas_gui to javafx.fxml;
    exports com.example.baraja_cartas_gui;
}