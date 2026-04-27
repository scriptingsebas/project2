import database.DatabaseManager;
import database.UserDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int SCENE_WIDTH = 430;
    private static final int SCENE_HEIGHT = 720;
    private static final int prefWidth = 200;

    //DUNGEON TEXT PRESETS
    private static final String dungeonTitle = "The Dungeon";
    private static final String bodyText = "The atmosphere is dark and oppressive.";
    private static final String conclusionText = "Many have entered seeking glory, but none have returned.";
    private static final String userPrompt = "What do you do?";
    private static final String goBack = "Turn Back";
    private static final String pressOn = "Press On";

    //FIGHT TEXT PRESETS
    private static final String encounterText = "Your life is in danger!";
    private static final String userAttack = "Attack";
    private static final String userDefend = "Defend";
    private static final String userEscape = "Run";



    public static void main(String[] args) {
        launch(args);
    }

    private DatabaseManager userDataManager;
    private UserDAO userDAO;

    @Override
    //main start of the program don't touch.
    public void start(Stage stage) {
        userDataManager = new DatabaseManager();
        userDAO = new UserDAO(userDataManager);

        stage.setTitle("Battle Quest");
        stage.setScene(home(stage));
        stage.show();
    }

    private Scene home(Stage stage) {
        int spacing = 13;
        Label title = new Label("RPG BATTLE QUEST");
        Button create = new Button("Create Account");
        Button login = new Button("Login");

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
        Label result = new Label();
        Button create = new Button("Create Account");
        Button back = new Button("Back");

        back.setOnAction(e -> {
            stage.setScene(home(stage));
        });

        create.setOnAction(e -> {
            String user = username.getText().trim();
            String pass = password.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                result.setText("Enter username and password.");
                return;
            }

            boolean created = userDAO.createUser(user, pass);

            if (created) {
                result.setText("Account created successfully. this is here because town scene does not exist i will remove later");
                //stage.setScene(town(stage));
            } else {
                result.setText("Username already exists.");
            }
        });

        VBox general = new VBox(title, username, password, result, create, back);
        general.setAlignment(Pos.CENTER);
        general.setPadding(new Insets(30));
        general.setSpacing(15);

        username.setPromptText("Username");
        password.setPromptText("Password");

        return new Scene(general, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Scene logInPage(Stage stage) {
        Label title = new Label("Log In");
        TextField username = new TextField();
        TextField password = new TextField();
        Label result = new Label();
        Button logIn = new Button("log In");
        Button back = new Button("Back");

        back.setOnAction(e -> {
            stage.setScene(home(stage));
        });

        logIn.setOnAction(e -> {
            String user = username.getText().trim();
            String pass = password.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                result.setText("Enter username and password.");
                return;
            }

            if (userDAO.loginUser(user, pass)) {
                result.setText("Logged in successfully. this is here because town scene does not exist I will remove later");
                //stage.setScene(town(stage));
            } else {
                result.setText("Invalid username or password.");
            }
        });

        VBox general = new VBox(title, username, password, result, logIn, back);
        general.setAlignment(Pos.CENTER);
        general.setPadding(new Insets(30));
        general.setSpacing(15);

        username.setPromptText("Username");
        password.setPromptText("Password");

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

        //backButton.setOnAction(e -> stage.setScene(town(stage));
        forwardButton.setOnAction(e -> stage.setScene(dungeonFight(stage)));

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
    private Scene dungeonFight (Stage stage) {
        //TEXT SECTION
        Label encounterLabel = new Label("A wild enemy appears!");
        Label warningLabel = new Label(encounterText);

        //ACTION BUTTON SECTION
        Button attackButton = new Button(userAttack);
        Button defendButton = new Button(userDefend);
        Button escapeButton = new Button(userEscape);

        //POLISH/FORMATTING
        attackButton.setPrefWidth(100);
        defendButton.setPrefWidth(100);
        escapeButton.setPrefWidth(100);

        encounterLabel.setWrapText(true);
        encounterLabel.setMaxWidth(SCENE_WIDTH);

        warningLabel.setWrapText(true);
        warningLabel.setMaxWidth(SCENE_WIDTH);

        //PLACEHOLDER ACTIONS
        //Each button will receive their respective functionality within a method.

        //attackButton.setOnAction(e -> attackFunction());
        //defendButton.setOnAction(e -> defendFunction());
        //escapeButton.setOnAction(e -> dungeonStart(stage));

        VBox textSection = new VBox(10, encounterLabel, warningLabel);
        textSection.setAlignment(Pos.CENTER);

        HBox buttonSection = new HBox(15, attackButton, defendButton, escapeButton);
        buttonSection.setAlignment(Pos.CENTER);

        VBox fightLayout = new VBox(30, textSection, buttonSection);
        fightLayout.setAlignment(Pos.CENTER);
        fightLayout.setPadding(new Insets(30));

        return new Scene(fightLayout, SCENE_WIDTH, SCENE_HEIGHT);
    }
    // TO create scene factory make private Scene scenename(Stage stage)
}

