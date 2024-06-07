package org.snake.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.snake.SnakeGame;
import org.snake.logger.MyLogger;
import org.snake.viewmodel.View;

/**
 * Loader scenes class.
 */
public class ChangeSceneManager {

    @Getter
    private static Scene currentScene;

    @Getter
    @Setter
    private static Stage primaryStage;

    /**
     * Loads scene from fxml.
     *
     * @param fxmlFile name of file in resources
     */
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
     */
    public static void changeScene(SceneType type) {
        switch (type) {
            case GAME:
                loadScene("game_space.fxml");
                MyLogger.info("Game scene loaded", ChangeSceneManager.class);
                break;
            case MENU:
                loadScene("menu.fxml");
                MyLogger.info("Menu scene loaded", ChangeSceneManager.class);
                break;
            default:
                break;
        }
    }
}
