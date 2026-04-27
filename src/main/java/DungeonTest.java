import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DungeonTest {

    public void stageTest(Stage stage) {
        Main testApp = new Main();
        Scene dungeonScene = testApp.dungeonStart(stage);
        stage.setScene(dungeonScene);
        stage.show();
    }

    @Test
    void titleLabelTest() {

    }
}