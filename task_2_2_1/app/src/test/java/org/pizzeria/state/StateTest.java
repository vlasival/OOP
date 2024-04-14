package org.pizzeria.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StateTest {
    @Test
    void stateTest() {
        State state = new State(3, 4);
        Order order = new Order(10, "Ten");
        try {
            state.getOrders().put(order);
        } catch (InterruptedException e) {
        }
        try {
            assertEquals(order, state.getOrders().get());
        } catch (InterruptedException e) {
        }
    }

    @Test
    void testGetOrders() {
        
    }

    @Test
    void testGetStorage() {
        
    }
}
