package com.example.baraja_cartas_gui.model;

public class Caballo {
    private String nombreCaballo;
    private CardSuit palo;
    private int posicion = 0;

    public Caballo(String nombre, CardSuit palo) {
        this.nombreCaballo = nombre;
        this.palo = palo;
    }

    public String getNombreCaballo() {
        return nombreCaballo;
    }

    public CardSuit getPalo() {
        return palo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void avanzar() {
        if (posicion < 10) {
            posicion++;
        }
    }

    public void retroceder() {
        if (posicion > 0) {
            posicion--;
        }
    }
}







