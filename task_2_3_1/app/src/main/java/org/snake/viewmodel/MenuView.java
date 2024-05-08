package org.snake.viewmodel;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

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
        try {
            GameView.setDifficulty(200);
            ChangeSceneManager.changeScene(SceneType.GAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void presMedium() {
        try {
            GameView.setDifficulty(100);
            ChangeSceneManager.changeScene(SceneType.GAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void presHard() {
        try {
            GameView.setDifficulty(50);
            ChangeSceneManager.changeScene(SceneType.GAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
