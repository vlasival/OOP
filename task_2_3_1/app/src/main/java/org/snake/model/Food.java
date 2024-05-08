package org.snake.model;

import java.util.Random;

/**
 * Represents a food object in the Snake game.
 */
public class Food extends Element {

    private Random random = new Random();
    private int width;
    private int height;

    /**
     * Initializes a new food object and set field config.
     *
     * @param x the x-coordinate of the food
     * @param y the y-coordinate of the food
     */
    public Food(int width, int height) {
        super(0, 0);
        this.width = width;
        this.height = height;
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
    public void generateNew() {
        x = random.nextInt(width);
        y = random.nextInt(height);
    }
}
