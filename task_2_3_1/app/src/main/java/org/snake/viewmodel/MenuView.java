package org.snake.viewmodel;

import org.snake.utils.ChangeSceneManager;
import org.snake.utils.SceneType;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * ModelView for menu scene.
 */
public class MenuView implements View {

    @FXML
    private Button easy;

    @FXML
    private Button medium;

    @FXML
    private Button hard;


    /**
     * Method starts menu scene (nothing to do with menu screen).
     */
    @Override
    public void startScene() {
        
    }

    @FXML
    private void presEasy() {
        GameView.setDifficulty(200);
        ChangeSceneManager.changeScene(SceneType.GAME);
    }

    @FXML
    private void presMedium() {
        GameView.setDifficulty(100);
        ChangeSceneManager.changeScene(SceneType.GAME);
    }

    @FXML
    private void presHard() {
        GameView.setDifficulty(50);
        ChangeSceneManager.changeScene(SceneType.GAME);
    }
    
}
