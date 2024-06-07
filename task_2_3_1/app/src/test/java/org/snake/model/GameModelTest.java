package org.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Class that tests business logic of the game.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class GameModelTest {
    private GameModel gameModel;

    /**
     * Setting up the environment.
     */
    @BeforeAll
    public void setUp() {
        gameModel = new GameModel(10, 10);
    }

    /**
     * Tests if snake not null.
     */
    @Test
    public void testSnakeNotNull() {
        assertNotNull(gameModel.getPlayerSnake());
    }

    /**
     * Tests if food not null.
     */
    @Test
    public void testFoodNotNull() {
        assertNotNull(gameModel.getFood());
    }

    /**
     * Test moving flag.
     */
    @Test
    public void testGameInitiallyNotMoving() {
        assertFalse(gameModel.isMoving());
        gameModel.setMoving(true);
        assertTrue(gameModel.isMoving());
    }

    /**
     * Test snake movement.
     */
    @Test
    public void testGameUpdateMovesSnake() {
        double initialSnakeX = gameModel.getPlayerSnake().getHead().getXcord();
        gameModel.update();
        double newSnakeX = gameModel.getPlayerSnake().getHead().getXcord();
        assertNotEquals(initialSnakeX, newSnakeX);
    }

    /**
     * Test collides with itself.
     */
    @Test
    public void testGameOverWhenSnakeCollidesWithItself() {
        for (int i = 0; i < 5; i++) {
            gameModel.update();
            gameModel.getPlayerSnake().growUp();
        }
        
        gameModel.getPlayerSnake().turnDown();
        gameModel.update(); 
        gameModel.getPlayerSnake().turnRight();
        gameModel.update(); 
        gameModel.getPlayerSnake().turnUp();
        gameModel.update();
        gameModel.getPlayerSnake().turnLeft();
        gameModel.update();

        assertTrue(gameModel.getPlayerSnake().checkCollisionWithBody(
            gameModel.getPlayerSnake().getHead())
        );
        assertTrue(gameModel.isGameOvered);
        assertEquals(6, gameModel.getPlayerSnake().getLength());
    }

    /**
     * Test snake growth.
     */
    @Test
    public void testListsElementsNotEqual() {
        int initialSnakeList = gameModel.getPlayerSnake().getBody().size();
        gameModel.getPlayerSnake().growUp();
        gameModel.update();
        int newSnakeList = gameModel.getPlayerSnake().getBody().size();
        assertNotEquals(initialSnakeList, newSnakeList);
    }
}
