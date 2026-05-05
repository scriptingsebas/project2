package org.turnbasedtitans.project2.controller;
import org.turnbasedtitans.project2.*;
import org.turnbasedtitans.project2.SceneFactory;
import org.turnbasedtitans.project2.SceneType;
import javafx.stage.Stage;


public class DungeonStartController {
    private SceneFactory sceneFactory;
    private Stage stage;
    private String currentUsername;

    public DungeonStartController(SceneFactory sceneFactory, Stage stage, String currentUsername) {
        this.sceneFactory = sceneFactory;
        this.stage = stage;
        this.currentUsername = currentUsername;
    }
    public void turnBack() {
        stage.setScene(sceneFactory.create(SceneType.TOWN, stage));
    }
    public void pressOn() {
        stage.setScene(sceneFactory.create(SceneType.DUNGEON_FIGHT, stage));
    }
}
