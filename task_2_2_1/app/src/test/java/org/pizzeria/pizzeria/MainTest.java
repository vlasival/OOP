package org.pizzeria.pizzeria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.pizzeria.Main;

/**
 * Javadoc for the MainTest class.
 */
public class MainTest {

    /**
     * Javadoc for the testMain method.
     */
    @Test
    public void testMain() {
        @SuppressWarnings("unused")
        Main main = new Main();

        boolean thrown = false;

        try {
            String[] args = {};
            Main.main(args);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse(thrown);
    }
}