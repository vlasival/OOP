package org.snake.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.snake.SnakeGame;
import org.snake.logger.MyLogger;
import org.snake.viewmodel.View;

/**
 * Loader scenes class.
 */
public class ChangeSceneManager {

    private static Scene currentScene;

    private static Stage primaryStage;

    /**
     * Getter for primary stage.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Setter for primary stage.
     */
    public static void setPrimaryStage(Stage primaryStage) {
        ChangeSceneManager.primaryStage = primaryStage;
    }

    /**
     * Getter for current loaded scene.
     */
    public static Scene getCurrentScene() {
        return currentScene;
    }

    private static void loadScene(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(SnakeGame.class.getResource(fxmlFile));
        Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            MyLogger.err("Failed to load file in", ChangeSceneManager.class);
            return;
        }
        primaryStage.setScene(scene);
        primaryStage.show();
        currentScene = scene;
        
        View view = loader.getController();
        view.startScene();
    }

    /**
     * Method to change a current scene.
     *
     * @param type scene type
     * @throws IOException throws if coudn't read a file
     */
    public static void changeScene(SceneType type) {
        switch (type) {
            case GAME:
                loadScene("game_space.fxml");
                break;
            case MENU:
                loadScene("menu.fxml");
                break;
            default:
                break;
        }
    }
}
