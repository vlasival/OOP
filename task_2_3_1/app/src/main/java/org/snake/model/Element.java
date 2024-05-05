package org.snake.model;

/**
 * Represents a basic element in the Snake game, with x and y coordinates.
 */
public class Element {
    
    protected double x;
    protected double y;

    /**
     * Initializes a new element at the specified position.
     *
     * @param x the x-coordinate of the element
     * @param y the y-coordinate of the element
     */
    public Element(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the element.
     *
     * @return the x-coordinate of the element
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the element.
     *
     * @return the y-coordinate of the element
     */
    public double getY() {
        return y;
    }

}
