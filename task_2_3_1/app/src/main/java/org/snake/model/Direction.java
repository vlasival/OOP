package org.snake.model;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 * Represents any possible movement direction in the game.
 */
public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    @Getter
    private int directionX;
    @Getter
    private int directionY;

    /**
     * Private constructor to set parameters.
     *
     * @param dirX parameter for x
     * @param dirY parameter for y
     */
    private Direction(int dirX, int dirY) {
        this.directionX = dirX;
        this.directionY = dirY;
    }

    /**
     * Return for this direction it's opposite.
     *
     * @return opposite direction
     */
    Direction opposite() {
        List<Direction> values = Arrays.asList(Direction.values());
        int index = (values.indexOf(this) + 2) % 4;
        return values.get(index);
    }
}
