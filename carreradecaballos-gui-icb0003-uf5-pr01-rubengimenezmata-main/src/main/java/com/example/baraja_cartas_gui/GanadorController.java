package com.example.baraja_cartas_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class GanadorController {

    @FXML
    private Label ganadorLabel;

    @FXML
    private ImageView imagenGanador;

    @FXML
    private Button volverMenuBtn;

    /**
     * DATOS PARA MOSTRAR EL GANADOR
     *
     * @param nombreJugador  Nombre del jugador humano
     * @param ganador        Nombre del jugador ganador
     * @param caballoGanador Nombre del caballo ganador
     * @param apuestaJugador Apuesta realizada por el jugador
     * @param apuestaBot1    Apuesta realizada por el Bot 1
     * @param apuestaBot2    Apuesta realizada por el Bot 2
     * @param apuestaBot3    Apuesta realizada por el Bot 3
     */
    @FXML
    public void iniciarInstancias(String nombreJugador, String ganador, String caballoGanador, int apuestaJugador, int apuestaBot1, int apuestaBot2, int apuestaBot3) {
        int totalFichas = apuestaJugador + apuestaBot1 + apuestaBot2 + apuestaBot3;


        ganadorLabel.setText(
                        "¡El ganador es: " + ganador + " con el caballo " + caballoGanador + "!\n" +
                        "Total de fichas ganadas: " + totalFichas + "\n\n" +
                        "----APUESTAS REALIZADAS ----\n" +
                        nombreJugador+ ": " + apuestaJugador + " fichas\n" +
                        "Bot 1: " + apuestaBot1 + " fichas\n" +
                        "Bot 2: " + apuestaBot2 + " fichas\n" +
                        "Bot 3: " + apuestaBot3 + " fichas"
        );

        // --------------- CARGAMOS LA IMAGEN DEL CABALLO GANADOR ----------------------
        String imagePath = "/images/" + caballoGanador + ".png";
        imagenGanador.setImage(new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm()));
    }

    /**
     * METODO PARA VOLVER AL MENU PRINCIPAL
     */

    @FXML
    public void volverMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 400, 300);
            Stage stage = (Stage) volverMenuBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Carrera de Caballos");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


