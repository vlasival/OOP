package org.snake.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class-manager for game snake.
 */
public class Intelligence {

    Snake gameSnake;
    Snake playerSnake;
    Food food;

    /**
     * Constructor.
     *
     * @param gameSnake game built-in snake
     * @param playerSnake player's snake
     * @param food food cell
     */
    public Intelligence(Snake gameSnake, Snake playerSnake, Food food) {
        this.gameSnake = gameSnake;
        this.playerSnake = playerSnake;
        this.food = food;
    }

    /**
     * General method, available from outside.
     */
    public void manageGameSnake() {
        Direction direction = makeDecision();
        direction = avoidCollision(direction);
        moveSnake(direction);
    }

    /**
     * Send commands to game snake.
     *
     * @param direction direction of movement
     */
    private void moveSnake(Direction direction) {
        switch (direction) {
            case UP:
                gameSnake.turnUp();
                break;
            case RIGHT:
                gameSnake.turnRight();
                break;
            case DOWN:
                gameSnake.turnDown();
                break;
            case LEFT:
                gameSnake.turnLeft();
                break;
            default:
                break;
        }
    }

    /**
     * Checks if element in collision with both snakes in game.
     *
     * @param element element
     * @return true if collides, false otherwise
     */
    private boolean checkCollisionWithSnakes(Element element) {
        return element.checkCollisionWithOthers(gameSnake.getBody()) 
            || element.checkCollisionWithOthers(playerSnake.getBody());
    }

    /**
     * Intelligent method. Pathfinder on game board.
     *
     * @return primary direction to food
     */
    private Direction makeDecision() {
        Element snakesHead = gameSnake.getHead();
        int headX = snakesHead.getXcord();
        int headY = snakesHead.getYcord();
        int foodX = food.getXcord();
        int foodY = food.getYcord();

        int diffX = foodX - headX;
        int diffY = foodY - headY;

        Direction directionOne = null;
        Direction directionTwo = null;

        if (diffX > 0) {
            if (diffX < gameSnake.getWidth() - diffX) {
                directionOne = Direction.RIGHT;
            } else {
                directionOne = Direction.LEFT;
            }
        } else {
            if (-diffX < gameSnake.getWidth() + diffX) {
                directionOne = Direction.LEFT;
            } else {
                directionOne = Direction.RIGHT;
            }
        }
        if (diffY > 0) {
            if (diffY < gameSnake.getHeight() - diffY) {
                directionTwo = Direction.DOWN;
            } else {
                directionTwo = Direction.UP;
            }
        } else {
            if (-diffY < gameSnake.getHeight() + diffY) {
                directionTwo = Direction.UP;
            } else {
                directionTwo = Direction.DOWN;
            }
        }

        Direction primaryDirection = null;
        Direction secondaryDirection = null;

        if (Math.abs(diffX) > Math.abs(diffY)) {
            primaryDirection = directionOne;
            secondaryDirection = directionTwo;
        } else {
            primaryDirection = directionTwo;
            secondaryDirection = directionOne;
        }

        Element primaryMove = new Element(
            headX + primaryDirection.getDirectionX(), 
            headY + primaryDirection.getDirectionY()
        );
        if (!checkCollisionWithSnakes(primaryMove)) {
            return primaryDirection;
        }

        Element secondaryMove = new Element(
            headX + secondaryDirection.getDirectionX(), 
            headY + secondaryDirection.getDirectionY()
        );
        if (!checkCollisionWithSnakes(secondaryMove)) {
            return secondaryDirection;
        }

        List<Direction> safeDirections = findSpaces();
        if (!safeDirections.isEmpty()) {
            return safeDirections.get(0);
        }

        return gameSnake.getDirection();
    }

    /**
     * Corrects the direction based on around situation.
     *
     * @param direction primary direction
     * @return processed direction
     */
    private Direction avoidCollision(Direction direction) {
        Element snakesHead = gameSnake.getHead();
        int newX = snakesHead.getXcord() + direction.getDirectionX();
        int newY = snakesHead.getYcord() + direction.getDirectionY();

        if (checkCollisionWithSnakes(new Element(newX, newY))) {
            List<Direction> spaces = findSpaces();
            for (Direction dir : spaces) {
                if (dir != direction.opposite()) {
                    return dir;
                }
            }
        }
        return direction;
    }

    /**
     * Finds free spaces around game snake's head.
     *
     * @return list of available directions
     */
    private List<Direction> findSpaces() {
        List<Direction> fourSides = new ArrayList<>();
        Element snakeHead = gameSnake.getHead();
        Direction[] availableDirections = Direction.values();

        for (Direction direction : availableDirections) {
            int newX = snakeHead.getXcord() + direction.getDirectionX();
            int newY = snakeHead.getYcord() + direction.getDirectionY();
            if (!checkCollisionWithSnakes(new Element(newX, newY))) {
                fourSides.add(direction);
            }
        }
        return fourSides;
    }
}
