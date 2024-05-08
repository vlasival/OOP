package org.snake.model;

/**
 * Business model of snake game.
 */
public class GameModel {
    private Snake snake;
    private Food food;
    private boolean moving = false;
    public boolean isGameOvered = false;

    /**
     * GameModel constructor.
     *
     * @param width with of the firld
     * @param height height of the firld
     */
    public GameModel(int width, int height) {
        snake = new Snake(width, height);
        food = new Food(width, height);
        food.generateNew();
    }

    /**
     * Getter for Snake object.
     *
     * @return Snake
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Getter for Food object.
     *
     * @return food object
     */
    public Food getFood() {
        return food;
    }

    /**
     * Snake is taking change direction?
     *
     * @return is changing direction now
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Setter for changer direction.
     *
     * @param value value to set
     */
    public void setMoving(boolean value) {
        moving = value; 
    }

    /**
     * Updates the game state.
     */
    public void update() {
        if (isGameOvered) {
            return;
        }
        moving = false;
        snake.move();
        if (snake.checkCollisionWithBody(snake.getHead())) {
            gameOver();
        }
        if (snake.checkCollisionWithHead(food)) {
            snake.growUp();
            do {
                food.generateNew();
            } while (snake.checkCollisionWithBody(food));
        }
    }

    private void gameOver() {
        isGameOvered = true;
        System.out.println("Game over :(");
    }
}
