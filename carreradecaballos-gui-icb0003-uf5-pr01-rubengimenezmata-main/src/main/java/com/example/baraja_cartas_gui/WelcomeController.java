package com.example.baraja_cartas_gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML
    private Button empezarPartida;

    @FXML
    private ImageView fondoCaballos;

    @FXML
    public void initialize() {
        Image tapeteImage = new Image(getClass().getResource("fondocaballos.jpg").toExternalForm());
        fondoCaballos.setImage(tapeteImage);
    }


    @FXML
    protected void onHelloButtonClick() {
        try {
            Stage stage = (Stage) empezarPartida.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("configuracion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Configuración de la Partida");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }
}
