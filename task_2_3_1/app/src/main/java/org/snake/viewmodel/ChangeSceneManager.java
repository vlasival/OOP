package org.snake.viewmodel;

import java.io.IOException;

import org.snake.SnakeGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    private static void loadScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(SnakeGame.class.getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
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
    public static void changeScene(SceneType type) throws IOException {
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
