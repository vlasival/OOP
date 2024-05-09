package org.snake.model;

/**
 * Represents a basic element in the Snake game, with x and y coordinates.
 */
public class Element {
    
    protected double xcord;
    protected double ycord;

    /**
     * Initializes a new element at the specified position.
     *
     * @param x the x-coordinate of the element
     * @param y the y-coordinate of the element
     */
    public Element(double x, double y) {
        this.xcord = x;
        this.ycord = y;
    }

    /**
     * Returns the x-coordinate of the element.
     *
     * @return the x-coordinate of the element
     */
    public double getX() {
        return xcord;
    }

    /**
     * Returns the y-coordinate of the element.
     *
     * @return the y-coordinate of the element
     */
    public double getY() {
        return ycord;
    }

}
