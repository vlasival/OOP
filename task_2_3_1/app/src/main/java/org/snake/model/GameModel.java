package org.snake.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Business model of snake game.
 */
public class GameModel {

    public boolean isGameOvered = false;

    @Getter
    private Snake playerSnake;
    @Getter
    private Snake gameSnake;
    @Getter
    private Food food;
    private Intelligence intelligence;
    @Getter
    @Setter
    private boolean moving = false;

    /**
     * GameModel constructor.
     *
     * @param width with of the firld
     * @param height height of the firld
     */
    public GameModel(int width, int height) {
        playerSnake = new Snake(width, height);
        gameSnake = new Snake(width, height);
        gameSnake.getHead().setYcord(height - 1);
        food = new Food(width, height);
        food.generateNew();
        intelligence = new Intelligence(gameSnake, playerSnake, food);
    }

    /**
     * Updates the game state.
     */
    public void update() {
        if (isGameOvered) {
            return;
        }
        moving = false;
        playerSnake.move();
        intelligence.manageGameSnake();
        gameSnake.move();
        if (playerSnake.checkHeadCollisionWithItsBody()
                || playerSnake.checkCollisionWithHead(gameSnake.getBody())) {
            gameOver();
            return;
        }
        if (gameSnake.checkHeadCollisionWithItsBody()
                || gameSnake.checkCollisionWithHead(playerSnake.getBody())) {
            gameSnake = new Snake(playerSnake.getWidth(), playerSnake.getHeight());
        }
        if (playerSnake.checkCollisionWithHead(food)) {
            playerSnake.growUp();
            do {
                food.generateNew();
            } while (playerSnake.checkCollisionWithBody(food));
        }
    }

    private void gameOver() {
        isGameOvered = true;
        System.out.println("Game over :(");
    }
}
