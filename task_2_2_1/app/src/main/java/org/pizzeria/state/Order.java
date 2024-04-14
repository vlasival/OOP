package org.pizzeria.state;

import java.io.Serializable;

/**
 * Order reccord.
 */
public record Order(int id, String name) implements Serializable {}
