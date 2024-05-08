package org.snake.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a snake in the game.
 */
public class Snake {

    private List<Element> snake;
    private int directionX;
    private int directionY;
    private int lenght;
    private int width;
    private int height;

    /**
     * Initializes a new snake.
     */
    public Snake(int width, int height) {
        this.width = width;
        this.height = height;
        snake = new LinkedList<>();
        initSnake();
    }

    /**
     * Returns the length of the snake.
     *
     * @return the length of the snake
     */
    public int getLength() {
        return lenght;
    }

    /**
     * Returns the head element of the snake.
     *
     * @return snake's head element
     */
    public Element getHead() {
        return snake.getFirst();
    }

    /**
     * Returns the last element of the snake's body.
     *
     * @return snake's last element
     */
    public Element getLastElement() {
        return snake.getLast();
    }

    /**
     * Returns the list of elements that make up the snake's body.
     *
     * @return the list of elements that make up the snake's body
     */
    public List<Element> getElements() {
        return snake;
    }

    /**
     * Adds a new element to the snake's body.
     *
     * @param element the new element to add
     */
    private void addElement(Element element) {
        snake.addLast(element);
    }

    /**
     * Grows the snake by adding a new element to its body.
     */
    public void growUp() {
        Element newPart = new Element(getLastElement().getX(), getLastElement().getY());
        addElement(newPart);
        lenght++;
        System.out.println("Snake grown up. Length: " + lenght);
    }

    /**
     * Initializes the snake with a default direction and length.
     */
    private void initSnake() {
        directionX = 1;
        directionY = 0;
        addElement(new Element(0, 0));
        lenght = 1;
    }

    /**
     * Moves the snake in its current direction.
     */
    protected void move() {
        double newX = (getHead().getX() + directionX + width) % width;
        double newY = (getHead().getY() + directionY + height) % height;
        Element newHead = new Element(newX, newY);
        snake.addFirst(newHead);
        snake.removeLast();
    }

    /**
     * Turns the snake to the right.
     */
    public void turnRight() {
        if (directionX == 0) {
            directionX = 1;
            directionY = 0;
        }
    }

    /**
     * Turns the snake to the left.
     */
    public void turnLeft() {
        if (directionX == 0) {
            directionX = -1;
            directionY = 0;
        }
    }

    /**
     * Turns the snake up.
     */
    public void turnUp() {
        if (directionY == 0) {
            directionX = 0;
            directionY = -1;
        }
    }

    /**
     * Turns the snake down.
     */
    public void turnDown() {
        if (directionY == 0) {
            directionX = 0;
            directionY = 1;
        }
    }

    /**
     * Checks if some element has collided with snake's body.
     *
     * @param collider the element to check for collision
     * @return true if the element has collided with snake's body, false otherwise
     */
    protected boolean checkCollisionWithBody(Element collider) {
        for (int i = 1; i < snake.size(); i++) {
            if (collider.getX() == snake.get(i).getX() 
                && collider.getY() == snake.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if some element has collided with snake's head.
     *
     * @param collider the element to check for collision
     * @return true if the snake's head has collided with an element, false otherwise
     */
    protected boolean checkCollisionWithHead(Element collider) {
        if (collider.getX() == getHead().getX() && collider.getY() == getHead().getY()) {
            return true;
        }
        return false;
    }

}
