package org.turnbasedtitans.project2;

/**
 * [Brief one-sentence description of what this class does.]
 *
 * @author Sebastian Guillen
 * @since 4/22/26
 * @version 0.1.0
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneFactory {

    public static Scene create(SceneType type, Stage stage) {
        try {
            return switch (type) {
                case VILLAGE -> new Scene(
                        FXMLLoader.load(SceneFactory.class.getResource("/org/turnbasedtitans/project2/village/village.fxml")),
                        900, 650
                );
            };
        } catch (Exception e) {
            throw new RuntimeException("Failed to load scene", e);
        }
    }
}
