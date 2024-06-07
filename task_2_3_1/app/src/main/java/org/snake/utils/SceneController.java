package org.snake.utils;

import org.snake.logger.MyLogger;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Javafx Application extention class.
 */
public class SceneController extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int CELL_SIZE = 20;

    /**
     * Starting method for javafx application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake Game");
        primaryStage.setResizable(false);
        ChangeSceneManager.setPrimaryStage(primaryStage);
        MyLogger.info("primary stage setted", this.getClass());
        ChangeSceneManager.changeScene(SceneType.MENU);
        MyLogger.info("primary scene changed", this.getClass());
    }

    /**
     * Launcher for application.
     */
    public static void launchGame() {
        launch();
    }
}
