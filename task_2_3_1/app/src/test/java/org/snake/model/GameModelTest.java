package org.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;;

@TestInstance(Lifecycle.PER_CLASS)
public class GameModelTest {
    private GameModel gameModel;

    @BeforeAll
    public void setUp() {
        gameModel = new GameModel(10, 10);
    }

    @Test
    public void testSnakeNotNull() {
        assertNotNull(gameModel.getSnake());
    }

    @Test
    public void testFoodNotNull() {
        assertNotNull(gameModel.getFood());
    }

    @Test
    public void testGameInitiallyNotMoving() {
        assertFalse(gameModel.isMoving());
        gameModel.setMoving(true);
        assertTrue(gameModel.isMoving());
    }

    @Test
    public void testGameUpdateMovesSnake() {
        double initialSnakeX = gameModel.getSnake().getHead().getX();
        gameModel.update();
        double newSnakeX = gameModel.getSnake().getHead().getX();
        assertNotEquals(initialSnakeX, newSnakeX);
    }

    @Test
    public void testGameOverWhenSnakeCollidesWithItself() {
        for (int i = 0; i < 5; i++) {
            gameModel.update();
            gameModel.getSnake().growUp();
        }
        
        gameModel.getSnake().turnDown();
        gameModel.update(); 
        gameModel.getSnake().turnRight();
        gameModel.update(); 
        gameModel.getSnake().turnUp();
        gameModel.update();
        gameModel.getSnake().turnLeft();
        gameModel.update();

        assertTrue(gameModel.getSnake().checkCollisionWithBody(gameModel.getSnake().getHead()));
        assertTrue(gameModel.isGameOvered);
        assertEquals(6, gameModel.getSnake().getLength());
    }

    @Test
    public void testListsElementsNotEqual() {
        int initialSnakeList = gameModel.getSnake().getElements().size();
        gameModel.getSnake().growUp();
        gameModel.update();
        int newSnakeList = gameModel.getSnake().getElements().size();
        assertNotEquals(initialSnakeList, newSnakeList);
    }
}
