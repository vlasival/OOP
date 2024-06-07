package org.snake.model;

public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    private int directionX;
    private int directionY;

    private Direction(int dirX, int dirY) {
        this.directionX = dirX;
        this.directionY = dirY;
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }
}
