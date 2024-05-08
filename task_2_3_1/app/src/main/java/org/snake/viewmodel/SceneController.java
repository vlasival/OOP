package org.snake.viewmodel;

import javafx.application.Application;
import javafx.stage.Stage;

public class SceneController extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int CELL_SIZE = 20;

    /**
     * Starting method for javafx application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Snake Game");
        primaryStage.setResizable(false);
        ChangeSceneManager.setPrimaryStage(primaryStage);
        ChangeSceneManager.changeScene(SceneType.MENU);
    }

    /**
     * Launcher for application.
     */
    public static void launchGame() {
        launch();
    }
}
