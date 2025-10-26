package com.example.baraja_cartas_gui.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CardsDeck {
    private final Set<Card> cardsDeck;
    private final Set<Card> cartaUsada;
    private final List<Card> cartaSacada;


    // ----------------------------------------- CREAR MAZO DE CARTAS ------------------------------------------------------------

    public CardsDeck() {
        cardsDeck = new HashSet<>();
        cartaUsada = new HashSet<>();
        cartaSacada = new ArrayList<>();

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<CardSuit> cardSuits = List.of(CardSuit.GOLD, CardSuit.CLUBS, CardSuit.CUPS, CardSuit.SWORDS);
        List<CardFace> cardFaces = List.of(CardFace.JACK, CardFace.KNIGHT);

        numeros.forEach(numero -> cardSuits.forEach(palo -> cardsDeck.add(new NumeredCard(numero, palo))));
        cardFaces.forEach(figura -> cardSuits.forEach(palo -> cardsDeck.add(new FacedCard(figura, palo))));

    }

    /**
     * METODO PARA CARTA ALEATORIA
     * @return Carta sacada
     * @throws mazoVacioException Si no quedan mas cartas en el mazo.
     */
    public Card cartaAleatoria() throws mazoVacioException {
        if (cardsDeck.isEmpty()) {
            throw new mazoVacioException("No quedan más cartas en el mazo.");
        }

        List<Card> cartasDisponibles = new ArrayList<>(cardsDeck);
        Collections.shuffle(cartasDisponibles);
        Card cartaSacada = cartasDisponibles.get(0);

        cardsDeck.remove(cartaSacada);
        cartaUsada.add(cartaSacada);
        this.cartaSacada.add(cartaSacada);
        logCartaSacada();
        return cartaSacada;
    }

    /**
     *  METODO PARA REINICIAR MAZO
     */
    public void resetDeck() {
        cardsDeck.addAll(cartaUsada);
        cartaUsada.clear();
        List<Card> mazoAleatorio = new ArrayList<>(cardsDeck);
        Collections.shuffle(mazoAleatorio);
    }

    /**
     * METODO PARA REGISTRAR CARTAS SACADAS EN UN .JSON
     */
    private void logCartaSacada() {
        try (FileWriter writer = new FileWriter("cartas_sacadas.json")) {
            writer.write("[\n");
            for (int i = 0; i < cartaSacada.size(); i++) {
                Card card = cartaSacada.get(i);
                writer.write(String.format("  {\"CARTA\": \"%s\", \"PALO\": \"%s\"}",
                        card.getCardCode(), card.getSuit().name()));
                if (i < cartaSacada.size() - 1) {
                    writer.write(",\n");
                }
            }
            writer.write("\n]");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo en JSON: " + e.getMessage());
        }
    }

    /**
     * EXCEPCIÓN PERSONALIZADA
     */

    public static class mazoVacioException extends Exception {
        public mazoVacioException(String message) {
            super(message);
        }
    }
}
