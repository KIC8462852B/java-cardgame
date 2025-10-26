package com.example.baraja_cartas_gui.model;

import java.util.Random;



public class JugadorBot extends Jugador {
    private Random random;

    // DE LA CLASE PADRE, SE AÑADE UN SUPER AQUI, PORQUE LE AÑADIMOS EL RANDOM
    public JugadorBot(String nombre) {
        super(nombre);
        random = new Random();
    }


    // SE SOBREESCRIBE EL MÉTODO EN LA CLASE PADRE
    @Override
    public int realizarApuesta() {
        return random.nextInt(500) + 1;
    }
}


