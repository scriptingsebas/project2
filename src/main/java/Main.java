import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    //These are text presets, like they aren't used for anything else aside from setting text
    private static final int SCENE_WIDTH = 430;
    private static final int SCENE_HEIGHT = 720;
    private static final int prefWidth = 200;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    //main start of the program don't touch.
    public void start(Stage stage) {
        stage.setTitle("dao.Battle Quest");
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
            stage.setScene(registerPage(stage));
        });

        login.setOnAction(e -> {
            stage.setScene(logInPage(stage));
        });

        VBox homePage = new VBox(spacing, title, create, login);
        homePage.setPadding(new Insets(30));
        homePage.setAlignment(Pos.CENTER);

        return new Scene(homePage, SCENE_WIDTH, SCENE_HEIGHT);
    }


    private Scene registerPage(Stage stage) {
        Label title = new Label("Create  Account");
        TextField username = new TextField();
        TextField password = new TextField();
        Button create = new Button("Create Account");
        Button back = new Button("Back");

        back.setOnAction(e -> {
            stage.setScene(home(stage));
        });

        create.setOnAction(e -> {
            //stage.setScene(town(stage))
        });

        VBox general = new VBox(title, username, password, create, back);
        general.setAlignment(Pos.CENTER);
        general.setPadding(new Insets(30));
        general.setSpacing(15);

        return new Scene(general, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Scene logInPage(Stage stage) {
        Label title = new Label("Log In");
        TextField username = new TextField("Username");
        TextField password = new TextField("Password");
        Button logIn = new Button("log In");
        Button back = new Button("Back");

        back.setOnAction(e -> {
            stage.setScene(home(stage));
        });

        logIn.setOnAction(e -> {
            //stage.setscene(town(stage));
        });

        VBox general = new VBox(title, username, password, logIn, back);
        general.setAlignment(Pos.CENTER);
        general.setPadding(new Insets(30));
        general.setSpacing(15);

        return new Scene(general, SCENE_WIDTH, SCENE_HEIGHT);
    }

}
