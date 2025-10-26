package com.example.baraja_cartas_gui.model;

import java.util.Scanner;

public class JugadorPersona extends Jugador {
    private Scanner scanner;

    //USAMOS SUPER YA QUE A JUGADOR PERSONA LE AÑADIREMOS UN SCANNER PARA EL JUGADOR
    public JugadorPersona(String nombre) {
        super(nombre);
        scanner = new Scanner(System.in);
    }

    // SE SOBREESCRIBE EL MÉTODO EN LA CLASE PADRE
    @Override
    public int realizarApuesta() {
        System.out.print(nombre + ", ¿Cuántas fichas quieres apostar? ");
        return scanner.nextInt();
    }
}


