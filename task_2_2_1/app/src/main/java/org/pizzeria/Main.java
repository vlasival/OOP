package org.pizzeria;

/**
 * Main class.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args arguments of main
     */
    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria("config.json");
        pizzeria.run();
    }
}
