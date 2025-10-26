package com.example.baraja_cartas_gui;

import com.example.baraja_cartas_gui.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionController {

    @FXML
    private TextField nombreJugadorField;

    @FXML
    private TextField apuestaField;

    @FXML
    protected void onStartRace() {
        String nombreJugador = nombreJugadorField.getText().trim();
        if (nombreJugador.isEmpty()) {
            mostrarAlerta("Debes ingresar tu nombre...");
            return;
        }

        int apuestaJugador;
        try {
            apuestaJugador = Integer.parseInt(apuestaField.getText().trim());
            if (apuestaJugador <= 0) {
                mostrarAlerta("Ingresa un numero positivo...");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("No es un numero valido...");
            return;
        }

        List<Jugador> jugadores = crearJugadores(nombreJugador);
        List<Integer> apuestas = realizarApuesta(jugadores.size() - 1);


        cargarCarrera(jugadores, apuestas, apuestaJugador);
    }

    private List<Jugador> crearJugadores(String nombreJugador) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new JugadorPersona(nombreJugador));
        jugadores.add(new JugadorBot("Bot 1"));
        jugadores.add(new JugadorBot("Bot 2"));
        jugadores.add(new JugadorBot("Bot 3"));
        return jugadores;
    }

    private List<Integer> realizarApuesta(int cantidadBots) {
        List<Integer> apuestas = new ArrayList<>();
        for (int i = 0; i < cantidadBots; i++) {
            apuestas.add((int) (Math.random() * 500) + 1);
        }
        return apuestas;
    }

    private void cargarCarrera(List<Jugador> jugadores, List<Integer> apuestas, int apuestaJugador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("carrera.fxml"));
            Parent root = loader.load();

            CarreraController carreraController = loader.getController();
            carreraController.iniciarInstancias(
                    jugadores,
                    apuestaJugador,
                    apuestas.get(0), apuestas.get(1), apuestas.get(2),
                    jugadores.get(0).getNombre()
            );

            Stage stage = (Stage) nombreJugadorField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Carrera de Caballos");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Ocurrió un problema al cargar la siguiente pantalla.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    public void onExitButtonClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}
