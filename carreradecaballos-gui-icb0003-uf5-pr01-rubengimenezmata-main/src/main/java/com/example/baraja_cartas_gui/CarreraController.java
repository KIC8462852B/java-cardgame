package com.example.baraja_cartas_gui;

import com.example.baraja_cartas_gui.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class CarreraController {

    @FXML
    private ImageView caballo1, caballo2, caballo3, caballo4;

    @FXML
    private Label cartaCrupierLabel, actionLabel;

    @FXML
    private Button botonJugar;

    private List<Caballo> caballos;
    private List<Jugador> jugadores;
    private List<Integer> posiciones;
    private CardsDeck deck;
    private int rondaActual = 0;

    private int apuestaJugador;
    private String nombreJugador;

    private int apuestaBot1;
    private int apuestaBot2;
    private int apuestaBot3;

    // ----------------------------------------- FONDO DEL TAPETE ------------------------------------------------------------

    @FXML
    private ImageView fondoTapete;

    @FXML
    public void initialize() {
        Image tapeteImage = new Image(Objects.requireNonNull(getClass().getResource("tapete.jpg")).toExternalForm());
        fondoTapete.setImage(tapeteImage);
    }
    // ----------------------------------------- INICIAMOS INSTANCIAS ------------------------------------------------------------
    /**
     * Método para inicializar los datos de la carrera.
     *
     * @param jugadores Lista de jugadores
     * @param apuestaJugador Apuesta del jugador humano
     * @param apuestaBot1 Apuesta del primer bot
     * @param apuestaBot2 Apuesta del segundo bot
     * @param apuestaBot3 Apuesta del tercer bot
     * @param nombreJugador Nombre del jugador
     */
    public void iniciarInstancias(List<Jugador> jugadores, int apuestaJugador, int apuestaBot1, int apuestaBot2, int apuestaBot3, String nombreJugador) {
        this.jugadores = jugadores;
        this.apuestaJugador = apuestaJugador;
        this.apuestaBot1 = apuestaBot1;
        this.apuestaBot2 = apuestaBot2;
        this.apuestaBot3 = apuestaBot3;
        this.nombreJugador = nombreJugador;
        this.deck = new CardsDeck(); //

        // ----------------------------------------- LISTA CABALLOS ------------------------------------------------------------

        List<Caballo> caballoAsignado = new ArrayList<>();
        caballoAsignado.add(new Caballo("KNIGHT_GOLD", CardSuit.GOLD));
        caballoAsignado.add(new Caballo("KNIGHT_SWORDS", CardSuit.SWORDS));
        caballoAsignado.add(new Caballo("KNIGHT_CUPS", CardSuit.CUPS));
        caballoAsignado.add(new Caballo("KNIGHT_CLUBS", CardSuit.CLUBS));

        Collections.shuffle(caballoAsignado);

        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.get(i).setCaballoAsignado(caballoAsignado.get(i));
        }

        this.caballos = caballoAsignado;
        posiciones = new ArrayList<>(Collections.nCopies(caballos.size(), 0)); // Inicializar posiciones a 0

        // ----------------------------------------- INICIALIZAR IMAGENES CON LAS CARTAS EN GUI ------------------------------------------------------------

        caballo1.setImage(cargarImagen("/images/" + caballos.get(0).getNombreCaballo() + ".png"));
        caballo2.setImage(cargarImagen("/images/" + caballos.get(1).getNombreCaballo() + ".png"));
        caballo3.setImage(cargarImagen("/images/" + caballos.get(2).getNombreCaballo() + ".png"));
        caballo4.setImage(cargarImagen("/images/" + caballos.get(3).getNombreCaballo() + ".png"));

        actionLabel.setText("Puedes empezar a jugar...");
    }
    // ----------------------------------------- LOGICA DE LA RONDA ------------------------------------------------------------
    @FXML
    public void jugarRonda() {
        rondaActual++;

        try {
            Card cartaCrupier = deck.cartaAleatoria();
            cartaCrupierLabel.setText("El crupier ha sacado la carta: " + cartaCrupier.getCardCode());

            boolean esRetroceso = (rondaActual % 5 == 0);

            for (int i = 0; i < caballos.size(); i++) {
                Caballo caballo = caballos.get(i);
                if (caballo.getPalo() == cartaCrupier.getSuit()) {
                    if (esRetroceso) {
                        posiciones.set(i, Math.max(posiciones.get(i) - 1, 0));
                        actionLabel.setText(jugadores.get(i).getNombre() + " [Caballo " + caballo.getNombreCaballo() +
                                "] retrocede a la posición " + posiciones.get(i));
                    } else {
                        posiciones.set(i, posiciones.get(i) + 1);
                        actionLabel.setText(jugadores.get(i).getNombre() + " [Caballo " + caballo.getNombreCaballo() +
                                "] avanza a la posición " + posiciones.get(i));
                    }

                    if (posiciones.get(i) >= 10) {
                        mostrarGanador(jugadores.get(i).getNombre(), caballo.getNombreCaballo());
                        return;
                    }
                    break;
                }
            }

        } catch (CardsDeck.mazoVacioException e) {
            cartaCrupierLabel.setText(e.getMessage());
            actionLabel.setText("El mazo se ha reiniciado. Juega una nueva ronda...");
            deck.resetDeck();
        }

        updatePosicionCaballos();

    }
    // ----------------------------------------- UPDATE POSICIONES HORIZONTAL EN TAPETE ------------------------------------------------------------
    private void updatePosicionCaballos() {
        caballo1.setTranslateX(posiciones.get(0) * 80);
        caballo2.setTranslateX(posiciones.get(1) * 80);
        caballo3.setTranslateX(posiciones.get(2) * 80);
        caballo4.setTranslateX(posiciones.get(3) * 80);
    }

    // ----------------------------------------- CARGAR IMAGEN ------------------------------------------------------------
    private Image cargarImagen(String path) {
        try {
            return new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("No se ha podido cargar la imagen: " + path);
            return null;
        }
    }


    // ----------------------------------------- MOSTRAR GANADOR ------------------------------------------------------------
    private void mostrarGanador(String ganador, String caballoGanador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ganador.fxml"));
            Parent root = loader.load();

            GanadorController ganadorController = loader.getController();
            ganadorController.iniciarInstancias(nombreJugador, ganador, caballoGanador, apuestaJugador, apuestaBot1, apuestaBot2, apuestaBot3);

            Stage stage = (Stage) botonJugar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }
}
