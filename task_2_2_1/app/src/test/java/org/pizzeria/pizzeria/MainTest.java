package org.pizzeria.pizzeria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.pizzeria.Main;

public class MainTest {
    @Test
    public void testMain() {

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
