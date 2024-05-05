package org.snake.model;

import java.util.Random;

/**
 * Represents a food object in the Snake game.
 */
public class Food extends Element {

    private Random random = new Random();
    public Type foodType;

    /**
     * Initializes a new food object at position (0, 0).
     */
    public Food() {
        super(0, 0);
    }

    /**
     * Initializes a new food object at the specified position.
     *
     * @param x the x-coordinate of the food
     * @param y the y-coordinate of the food
     */
    public Food(double x, double y) {
        super(x, y);
    }

    /**
     * Returns the x-coordinate of the food.
     *
     * @return the x-coordinate of the food
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the food.
     *
     * @return the y-coordinate of the food
     */
    public double getY() {
        return y;
    }

    /**
     * Generates a new random position for the food within the specified width and height.
     *
     * @param width  the maximum width for the new position
     * @param height the maximum height for the new position
     */
    public void generateNew(int width, int height) {
        x = random.nextInt(width);
        y = random.nextInt(height);
    }

    private enum Type {
        APPLE,
        BANANA,
        BATTERY
    }

}
