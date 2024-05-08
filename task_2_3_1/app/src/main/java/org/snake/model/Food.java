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
     * @param width the width of the field
     * @param height the width of the field
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
     */
    public void generateNew() {
        x = random.nextInt(width);
        y = random.nextInt(height);
    }
}
