package org.snake.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a basic element in the Snake game, with x and y coordinates.
 */
public class Element {
    
    @Getter
    @Setter
    private int xcord;
    @Getter
    @Setter
    private int ycord;

    /**
     * Initializes a new element at the specified position.
     *
     * @param x the x-coordinate of the element
     * @param y the y-coordinate of the element
     */
    public Element(int x, int y) {
        this.xcord = x;
        this.ycord = y;
    }

    public boolean checkCollisionWithOthers(List<Element> elements) {
        for (Element e : elements) {
            if (e.getXcord() == this.getXcord() && e.getYcord() == this.getYcord()) {
                return true;
            }
        }
        return false;
    }

}
