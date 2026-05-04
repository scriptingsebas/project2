package org.turnbasedtitans.project2;

import org.turnbasedtitans.project2.database.DatabaseManager;
import org.turnbasedtitans.project2.database.UserDAO;
import org.turnbasedtitans.project2.database.InventoryDAO;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private DatabaseManager userDataManager;
    private UserDAO userDAO;
    private InventoryDAO inventoryDAO;
    private String currentUsername;
    private SceneFactory sceneFactory;
    //only have DAOs, only main, and start.

    private static final int SCENE_WIDTH = 430;
    private static final int SCENE_HEIGHT = 720;
    private static final int prefWidth = 200;
    // Sprite animation settings
    private static final double FRAME_WIDTH = 100;
    private static final double FRAME_HEIGHT = 55;
    private static final int IDLE_FRAMES = 8;
    private static final double SPRITE_SCALE = 7;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    //main start of the program don't touch.
    public void start(Stage stage) {
        Font.loadFont(getClass().getResourceAsStream("/org/turnbasedtitans/project2/fonts/PixelifySans.ttf"), 12);
        userDataManager = new DatabaseManager();
        userDAO = new UserDAO(userDataManager);
        inventoryDAO = new InventoryDAO(userDataManager.getConnection());
        sceneFactory = new SceneFactory(userDataManager, inventoryDAO);

        stage.setTitle("RPG Battle Quest");

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
        stage.setScene(sceneFactory.home(stage));
        stage.show();
    }



    // TO create scene factory make private Scene scenename(Stage stage)
}
