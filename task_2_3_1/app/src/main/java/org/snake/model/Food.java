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
     * Generates a new random position for the food within the specified width and height.
     */
    public void generateNew() {
        xcord = random.nextInt(width);
        ycord = random.nextInt(height);
    }
}
