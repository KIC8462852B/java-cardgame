package com.example.baraja_cartas_gui.model;

public abstract class Jugador {
    protected String nombre;
    protected Caballo caballoAsignado;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Caballo getCaballoAsignado() {
        return caballoAsignado;
    }

    public void setCaballoAsignado(Caballo caballoAsignado) {
        this.caballoAsignado = caballoAsignado;
    }

    public abstract int realizarApuesta();
}

