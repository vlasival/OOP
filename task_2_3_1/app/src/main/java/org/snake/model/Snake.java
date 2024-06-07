package org.snake.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

/**
 * Represents a snake in the game.
 */
public class Snake {

    @Getter
    private List<Element> body;
    @Getter
    private Direction direction;
    @Getter
    private int length;
    @Getter
    private int width;
    @Getter
    private int height;

    /**
     * Constructior.
     *
     * @param width width of game board
     * @param height height of game board
     */
    public Snake(int width, int height) {
        this.width = width;
        this.height = height;
        body = new LinkedList<>();
        initSnake();
    }

    /**
     * Returns the head element of the snake.
     *
     * @return snake's head element
     */
    public Element getHead() {
        return body.getFirst();
    }

    /**
     * Returns the last element of the snake's body.
     *
     * @return snake's last element
     */
    public Element getLastElement() {
        return body.getLast();
    }

    /**
     * Adds a new element to the snake's body.
     *
     * @param element the new element to add
     */
    private void addElement(Element element) {
        body.addLast(element);
    }

    /**
     * Grows the snake by adding a new element to its body.
     */
    public void growUp() {
        Element newPart = new Element(getLastElement().getXcord(), getLastElement().getYcord());
        addElement(newPart);
        length++;
        System.out.println("Snake grown up. Length: " + length);
    }

    /**
     * Initializes the snake with a default direction and length.
     */
    private void initSnake() {
        direction = Direction.RIGHT;
        addElement(new Element(0, 0));
        length = 1;
    }

    /**
     * Moves the snake in its current direction.
     */
    protected void move() {
        int newX = (getHead().getXcord() + direction.getDirectionX() + width) % width;
        int newY = (getHead().getYcord() + direction.getDirectionY() + height) % height;
        Element newHead = new Element(newX, newY);
        body.addFirst(newHead);
        body.removeLast();
    }

    /**
     * Turns the snake to the right.
     */
    public void turnRight() {
        if (direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
    }

    /**
     * Turns the snake to the left.
     */
    public void turnLeft() {
        if (direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        }
    }

    /**
     * Turns the snake up.
     */
    public void turnUp() {
        if (direction != Direction.DOWN) {
            direction = Direction.UP;
        }
    }

    /**
     * Turns the snake down.
     */
    public void turnDown() {
        if (direction != Direction.UP) {
            direction = Direction.DOWN;
        }
    }

    public boolean checkHeadCollisionWithItsBody() {
        for (int i = 1; i < body.size(); i++) {
            if (checkCollisionWithHead(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if some element has collided with snake's body.
     *
     * @param collider the element to check for collision
     * @return true if the element has collided with snake's body, false otherwise
     */
    protected boolean checkCollisionWithBody(Element collider) {
        return collider.checkCollisionWithOthers(body);
    }

    /**
     * Checks if some element has collided with snake's head.
     *
     * @param collider the element to check for collision
     * @return true if the snake's head has collided with an element, false otherwise
     */
    protected boolean checkCollisionWithHead(Element collider) {
        if (collider.getXcord() == getHead().getXcord() && collider.getYcord() == getHead().getYcord()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if any of list of elements has collided with snake's head.
     *
     * @param collider list of elements to check for collision
     * @return true if the snake's head has collided with any element, false otherwise
     */
    protected boolean checkCollisionWithHead(List<Element> colliders) {
        for (Element e : colliders) {
            if (checkCollisionWithHead(e)) {
                return true;
            }
        }
        return false;
    }

}
