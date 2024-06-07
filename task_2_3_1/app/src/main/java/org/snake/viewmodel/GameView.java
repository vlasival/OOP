package org.snake.viewmodel;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.snake.model.Element;
import org.snake.model.GameModel;
import org.snake.utils.ChangeSceneManager;
import org.snake.utils.SceneController;

/**
 * GameView is middle ModelView class.
 */
public class GameView implements View {

    @FXML
    private Label score;

    @FXML
    private Pane pane;

    private GameModel model;

    private Timeline timeline;

    private static int difficulty;

    /**
     * Getter for game speed.
     */
    public static int getDifficulty() {
        return difficulty;
    }

    /**
     * Setter for game speed.
     */
    public static void setDifficulty(int difficulty) {
        GameView.difficulty = difficulty;
    }

    /**
     * Method starts game.
     */
    public void startScene() {
        model = new GameModel(SceneController.WIDTH / SceneController.CELL_SIZE, 
            SceneController.HEIGHT / SceneController.CELL_SIZE);

        ChangeSceneManager.getCurrentScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (model.isMoving()) {
                    return;
                }
                model.setMoving(true);
                KeyCode code = event.getCode();
                if (code == KeyCode.LEFT) {
                    model.getPlayerSnake().turnLeft();
                } else if (code == KeyCode.RIGHT) {
                    model.getPlayerSnake().turnRight();
                } else if (code == KeyCode.UP) {
                    model.getPlayerSnake().turnUp();
                } else if (code == KeyCode.DOWN) {
                    model.getPlayerSnake().turnDown();
                }
            }
        });

        
        timeline = new Timeline(new KeyFrame(Duration.millis(difficulty), e -> {
            update();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    /**
     * Updates the game screen every frame.
     */
    private void updateScreen() {
        pane.getChildren().clear();
        for (Element element : model.getPlayerSnake().getBody()) {
            drawRectangle(Color.GREEN, element.getXcord(), element.getYcord());
        }
        for (Element element : model.getGameSnake().getBody()) {
            drawRectangle(Color.AQUA, element.getXcord(), element.getYcord());
        }
        drawRectangle(Color.RED, model.getFood().getXcord(), model.getFood().getYcord());
    }

    private void update() {
        model.update();
        updateScreen();
        score.setText(String.valueOf(model.getPlayerSnake().getLength()));
        if (model.isGameOvered) {
            gameOver();
        }
    }

    private void gameOver() {
        pane.getChildren().clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        model = new GameModel(SceneController.WIDTH / SceneController.CELL_SIZE, 
                            SceneController.HEIGHT / SceneController.CELL_SIZE);
        model.isGameOvered = false;
    }

    /**
     * Draws a rectangle on the game board.
     *
     * @param color the color of the rectangle
     * @param x     the x-coordinate of the rectangle
     * @param y     the y-coordinate of the rectangle
     */
    private void drawRectangle(Color color, double x, double y) {
        Rectangle rect = new Rectangle(
            x * SceneController.CELL_SIZE, 
            y * SceneController.CELL_SIZE, 
            SceneController.CELL_SIZE, 
            SceneController.CELL_SIZE
        );
        rect.setFill(color);
        pane.getChildren().add(rect);
    }
}
