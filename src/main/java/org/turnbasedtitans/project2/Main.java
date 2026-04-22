package org.turnbasedtitans.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * [Brief one-sentence description of what this class does.]
 *
 * @author Sebastian Guillen
 * @since 4/22/26
 * @version 0.1.0
 */
public class Main extends Application {

    @Override

    public void start(Stage stage) {

        stage.setTitle("Project 2");

        stage.setScene(SceneFactory.create(SceneType.VILLAGE, stage));

        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }

}