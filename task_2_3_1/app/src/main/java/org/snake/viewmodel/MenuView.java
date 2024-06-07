package org.snake.viewmodel;

import org.snake.logger.MyLogger;
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

    /**
     * If Easy button was pressed.
     */
    @FXML
    private void presEasy() {
        GameView.setDifficulty(200);
        ChangeSceneManager.changeScene(SceneType.GAME);
        MyLogger.info("Easy mode was chosen", getClass());
    }

    /**
     * If Medium button was pressed.
     */
    @FXML
    private void presMedium() {
        GameView.setDifficulty(100);
        ChangeSceneManager.changeScene(SceneType.GAME);
        MyLogger.info("Medium mode was chosen", getClass());
    }

    /**
     * If Hard button was pressed.
     */
    @FXML
    private void presHard() {
        GameView.setDifficulty(50);
        ChangeSceneManager.changeScene(SceneType.GAME);
        MyLogger.info("Hard mode was chosen", getClass());
    }
    
}
