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
    //These are text presets, like they aren't used for anything else aside from setting text
    private static final int SCENE_WIDTH = 720;
    private static final int SCENE_HEIGHT = 320;
    private static final int prefWidth = 200;
    private static final String TITLETEXT = "Login";
    private static final String USERTEXT = "Username";
    private static final String PASSTEXT = "Password";
    private static final String CREATE = "Register";
    private static final String REGUSERTEXT = "Username";
    private static final String REGPASSTEXT = "Password";
    private static final String VERIFY = "Login";
    private static final String FAILED = "Password or Username is incorrect!";

    //DUNGEON TEXT PRESETS
    private static final String dungeonTitle = "The Dungeon";
    private static final String bodyText = "The atmosphere is dark and oppressive.";
    private static final String conclusionText = "Many have entered seeking glory, but none have returned.";
    private static final String userPrompt = "What do you do?";
    private static final String goBack = "Turn Back";
    private static final String pressOn = "Press On";

    public static void main(String[] args) {
        launch(args);
    }

    //SCENE FACTORY ENUM
    public enum RPGScene {
        HOME,
        TOWN,
        DUNGEON_START
    }
    //SCENE FACTORY
    //This is not entirely implemented nor is the RPGScene enum. Update it as you see fit
    //with a separate case for your scene. You'll most likely need to update your methods
    //as well. Below is an example of how to utilize the SceneFactory.
    //stage.setScene(createScene(RPGScene.HOME, stage));
    private Scene sceneCreate(RPGScene rpgScene, Stage stage) {
        switch (rpgScene) {
            case HOME:
                return home(stage);
            case TOWN:
                //return town(stage);
            case DUNGEON_START:
                return dungeonStart(stage);
            default:
                throw new IllegalArgumentException("Scene doesn't exist!" + rpgScene);
        }
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
            //stage.setscene(game(stage));
        });

        VBox general = new VBox(title, username, password, logIn, back);
        general.setAlignment(Pos.CENTER);
        general.setPadding(new Insets(30));
        general.setSpacing(15);

        return new Scene(general, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Scene dungeonStart (Stage stage) {
        int dungeonSpacing = 10;

        Label titleLabel = new Label(dungeonTitle);
        Label bodyLabel = new Label(bodyText);
        Label conclusionLabel = new Label(conclusionText);
        Label promptLabel = new Label(userPrompt);

        Button backButton = new Button(goBack);
        Button forwardButton = new Button(pressOn);

        //The SceneFactory isn't implemented yet (WILL BE UPDATED LATER!)
        //backButton.setOnAction(e -> switchScene(stage, RPGScene.town));
        //forwardButton.setOnAction(e -> switchScene(stage, RPGScene.dungeonDepths));

        VBox titleSection = new VBox(dungeonSpacing, titleLabel);
        titleSection.setAlignment(Pos.CENTER);

        VBox bodySection = new VBox(dungeonSpacing, bodyLabel, conclusionLabel, promptLabel);
        bodySection.setAlignment(Pos.CENTER);

        VBox buttonSection = new VBox(dungeonSpacing, backButton, forwardButton);
        buttonSection.setAlignment(Pos.CENTER);

        VBox dungeonScene = new VBox(dungeonSpacing, titleSection, bodySection, buttonSection);
        dungeonScene.setAlignment(Pos.CENTER);
        dungeonScene.setPadding(new Insets(30));

        return new Scene(dungeonScene, SCENE_WIDTH, SCENE_HEIGHT);
    }
    // TO create scene factory make private Scene scenename(Stage stage)
}
