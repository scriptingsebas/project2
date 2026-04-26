import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    //These are text presets, like they arent used for anything else aside from setting text
    private static final int SCENE_WIDTH = 720;
    private static final int SCENE_HEIGHT = 320;
    private static final int prefWidth = 200;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    //main start of the program don't touch.
    public void start(Stage stage) {
        stage.setTitle("Battle Quest");
        stage.setScene(home(stage));
        stage.show();
    }

    private Scene home(Stage stage) {
        int spacing = 13;
        Label title = new Label("RPG BATTLE QUEST");
        Button create = new Button("Create Account");
        Button login = new Button("Login");
        Label RESULTS = new Label();

        create.setOnAction(e -> {

        });

        login.setOnAction(e -> {

        });

        VBox homePage = new VBox(spacing, title, create, login);
        homePage.setPadding(new Insets(30));
        homePage.setAlignment(Pos.CENTER);

        return new Scene(homePage, SCENE_WIDTH, SCENE_HEIGHT);
    }

    // TO create scene factory make private Scene scenename(Stage stage)
}
