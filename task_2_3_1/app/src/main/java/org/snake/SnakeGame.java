package org.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The main class for the Snake game.
 *
 * @author vlasival
 */
public class SnakeGame extends Application {

    static final int WIDTH = 30;
    static final int HEIGHT = 20;
    static final int CELL_SIZE = 20;
    static Timeline timeline;

    private Pane pane;
    private Snake snake;
    private Food food;
    private boolean isMoving = false;

    /**
     * Starts the game.
     *
     * @param primaryStage the primary stage for the game
     */
    @Override
    public void start(Stage primaryStage) {
        pane = new Pane();
        pane.setPrefSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        pane.setStyle("-fx-background-color: #83bf00");

        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            update();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (isMoving) {
                    return;
                }
                isMoving = true;
                KeyCode code = event.getCode();
                if (code == KeyCode.LEFT) {
                    snake.turnLeft();
                } else if (code == KeyCode.RIGHT) {
                    snake.turnRight();
                } else if (code == KeyCode.UP) {
                    snake.turnUp();
                } else if (code == KeyCode.DOWN) {
                    snake.turnDown();
                }
            }
        });

        snake = new Snake();
        Element head = snake.getHead();
        drawRectangle(Color.GREEN, head.getX(), head.getY());

        food = new Food();
        food.generateNew(WIDTH, HEIGHT);
        drawRectangle(Color.RED, food.getX(), food.getY());

        primaryStage.setScene(scene);
        primaryStage.show();
        timeline.play();
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
            x * CELL_SIZE, 
            y * CELL_SIZE, 
            CELL_SIZE, 
            CELL_SIZE
        );
        rect.setFill(color);
        pane.getChildren().add(rect);
    }

    /**
     * Updates the game screen every frame.
     */
    private void updateScreen() {
        pane.getChildren().clear();
        for (Element element : snake.getElements()) {
            drawRectangle(Color.GREEN, element.getX(), element.getY());
        }
        drawRectangle(Color.RED, food.getX(), food.getY());
    }

    /**
     * Updates the game state.
     */
    private void update() {
        isMoving = false;
        snake.move();
        if (snake.checkCollisionWithBody(snake.getHead())) {
            timeline.pause();
            System.out.println("Game over!");
            snake = new Snake();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeline.play();
        }
        if (snake.checkCollisionWithHead(food)) {
            snake.growUp();
            do {
                food.generateNew(WIDTH, HEIGHT);
            } while (snake.checkCollisionWithBody(food));
        }
        updateScreen();
    }

    /**
     * The main method to launch the game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}