package org.pizzeria.pizzeria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pizzeria.Pizzeria;

import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {

    private Pizzeria pizzeria;

    @BeforeEach
    void setUp() {
        pizzeria = new Pizzeria("config.json");
    }

    @Test
    void addOrderTest() throws InterruptedException {
        int initialOrderCount = pizzeria.getState().getOrders().size();
        pizzeria.addOrder(5);
        int newOrderCount = pizzeria.getState().getOrders().size();
        assertEquals(initialOrderCount + 5, newOrderCount);
    }

    @Test
    void runTest() {
        pizzeria.run();
        assertTrue(pizzeria.getState().getStorage().isEmpty());
    }

    @Test
    void getStateTest() {
        assertNotNull(pizzeria.getState());
    }
}