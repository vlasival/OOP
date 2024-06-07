package org.snake.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Intelligence {

    Snake gameSnake;
    Snake playerSnake;
    Food food;
    int leftRightBuffer = 0;
    int upDownBuffer = 0;

    public Intelligence(Snake gameSnake, Snake playerSnake, Food food) {
        this.gameSnake = gameSnake;
        this.playerSnake = playerSnake;
        this.food = food;
    }

    public void manageGameSnake() {
        Direction direction = makeDecision();
        direction = avoidCollision(direction);
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

    private boolean checkCollisionWithSnakes(Element element) {
        return element.checkCollisionWithOthers(gameSnake.getBody()) 
            || element.checkCollisionWithOthers(playerSnake.getBody());
    }
    
    private Direction makeDecision() {
        Direction snakeDirection = gameSnake.getDirection();
        Element snakesHead = gameSnake.getHead();
        double torDistanceToRight = Math.abs(food.getXcord() - snakesHead.getXcord());
        double torDistanceToLeft = gameSnake.getWidth() - torDistanceToRight;
        if (torDistanceToRight < torDistanceToLeft) {
            if (food.getXcord() - snakesHead.getXcord() > 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else if (torDistanceToRight > torDistanceToLeft) {
            if (food.getXcord() - snakesHead.getXcord() > 0) {
                return Direction.LEFT;
            } else {
                return Direction.RIGHT;
            }
        } else {
            double torDistanceToUp = Math.abs(snakesHead.getYcord() - food.getYcord());
            double torDistanceToDown = gameSnake.getHeight() - torDistanceToUp;
            if (torDistanceToUp < torDistanceToDown) {
                if (food.getYcord() - snakesHead.getYcord() > 0) {
                    return Direction.DOWN;
                } else {
                    return Direction.UP;
                }
            } else if (torDistanceToUp > torDistanceToDown) {
                if (food.getYcord() - snakesHead.getYcord() > 0) {
                    return Direction.UP;
                } else {
                    return Direction.DOWN;
                }
            } else {
                return snakeDirection;
            }
        }
    }

    private Direction avoidCollision(Direction direction) {
        Element snakesHead = gameSnake.getHead();
        Element newElement = new Element(
            snakesHead.getXcord() + direction.getDirectionX(), 
            snakesHead.getYcord() + direction.getDirectionY()
        );
        
        if (checkCollisionWithSnakes(newElement)) {
            List<Direction> spaces = findSpaces();
            List<Direction> availableDirections = Arrays.asList(Direction.values());
            for (Direction dir : spaces) {
                if (Math.abs(availableDirections.indexOf(dir) - 
                            availableDirections.indexOf(direction)) == 2) {
                    continue;
                }
                return dir;
            }
        }
        return direction;
    }

    private List<Direction> findSpaces() {
        List<Direction> fourSides = new ArrayList<>();
        Element snakeHead = gameSnake.getHead();
        Direction[] availableDirections = Direction.values();
        for (int i = 0; i < 4; i++) {
            Element elem = new Element(
                snakeHead.getXcord() + availableDirections[i].getDirectionX(), 
                snakeHead.getYcord() + availableDirections[i].getDirectionY()
            );
            if (!checkCollisionWithSnakes(elem)) {
                fourSides.add(availableDirections[i]);
            }
        }
        return fourSides;
    }
}
