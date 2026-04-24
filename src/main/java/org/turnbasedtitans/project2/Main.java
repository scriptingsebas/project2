package org.turnbasedtitans.project2;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.text.Font;

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

        stage.setTitle("RPG Battle Quest");

        Font.loadFont(
                getClass().getResourceAsStream("/org/turnbasedtitans/project2/fonts/PixelifySans.ttf"),
                12
        );


        stage.setScene(SceneFactory.create(SceneType.VILLAGE, stage));

        // sets the screen resolution to always be 4:3, and to start off at a decent ish size on first boot (hopefully)
        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

        double widthRatio = 1024.0 / 1512.0;
        double heightRatio = 768.0 / 982.0;

        double windowWidth = screenBounds.getWidth() * widthRatio;
        double windowHeight = screenBounds.getHeight() * heightRatio;

        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        stage.centerOnScreen();

        double ratio = 4.0 / 3.0;

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            stage.setHeight(newVal.doubleValue() / ratio);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            stage.setWidth(newVal.doubleValue() * ratio);
        });

        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }



}