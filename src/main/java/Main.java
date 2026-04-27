import database.DatabaseManager;
import database.UserDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

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
        Font.loadFont(getClass().getResourceAsStream("/org/turnbasedtitans/project2/fonts/PixelifySans.ttf"), 12);
        userDataManager = new DatabaseManager();
        userDAO = new UserDAO(userDataManager);

        stage.setTitle("Battle Quest");
        stage.setScene(town(stage));

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

    Scene home(Stage stage) {
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
                stage.setScene(town(stage));
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
                stage.setScene(town(stage));
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
    private Scene town(Stage stage) {
        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-font-family: 'Pixelify Sans';");
        Image bgImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/village/village-bg.jpg").toExternalForm());
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        layout.setBackground(new Background(bg));

        Label title = new Label("THE VILLAGE");
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setTopAnchor(title, 20.0);
        AnchorPane.setLeftAnchor(title, 0.0);
        AnchorPane.setRightAnchor(title, 0.0);

        ProgressBar healthBar = new ProgressBar(1.0);
        healthBar.setPrefHeight(25);
        healthBar.setMaxWidth(Double.MAX_VALUE);
        healthBar.setStyle("-fx-accent: #16d715;");
        HBox.setHgrow(healthBar, Priority.ALWAYS);

        Label healthLabel = new Label("100%");
        healthLabel.setStyle("-fx-font-size: 20px;");

        HBox healthContainer = new HBox(6, healthBar, healthLabel);
        healthContainer.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(healthContainer, 85.0);
        AnchorPane.setLeftAnchor(healthContainer, 15.0);
        AnchorPane.setRightAnchor(healthContainer, 15.0);

        Button inventoryButton = shopButton("Inventory", "");
        Button shopButton = shopButton("Shop", "");
        Button fightButton = shopButton("Fight", "");

        VBox mainPanel = new VBox(22, inventoryButton, shopButton, fightButton);
        mainPanel.setAlignment(Pos.CENTER);
        mainPanel.setPrefSize(350, 320);
        mainPanel.setPadding(new Insets(28, 18, 28, 18));
        mainPanel.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");
        AnchorPane.setTopAnchor(mainPanel, 220.0);
        AnchorPane.setLeftAnchor(mainPanel, 520.0);

        Label shopTitle = new Label("SHOP");
        shopTitle.setStyle("-fx-font-size: 38px; -fx-text-fill: white;");

        Label battlesText = new Label("Battle's Won -");
        battlesText.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");

        Label battlesValue = new Label("0");
        battlesValue.setStyle("-fx-font-size: 26px; -fx-text-fill: yellow;");

        HBox battlesWonLabel = new HBox(8, battlesText, battlesValue);
        battlesWonLabel.setAlignment(Pos.CENTER);

        Button armorButton = shopButton("Armor Upgrade -", "1");
        Button swordButton = shopButton("Sword Upgrade -", "2");
        Button healingButton = shopButton("Healing -", "1");
        Button backButton = shopButton("Back", "");

        VBox shopPanel = new VBox(18, shopTitle, battlesWonLabel, armorButton, swordButton, healingButton, backButton);
        shopPanel.setAlignment(Pos.TOP_CENTER);
        shopPanel.setPrefSize(350, 420);
        shopPanel.setMaxHeight(420);
        shopPanel.setPadding(new Insets(28, 18, 18, 18));
        shopPanel.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");
        AnchorPane.setTopAnchor(shopPanel, 160.0);
        AnchorPane.setLeftAnchor(shopPanel, 520.0);
        shopPanel.setVisible(false);

        Label swordLabel = new Label("Sword: Bronze");
        swordLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        Label armorLabel = new Label("Armor: Bronze");
        armorLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        Label potionLabel = new Label("Healing Potions: 0");
        potionLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        Button inventoryBackButton = shopButton("Back", "");

        javafx.scene.control.Separator sep1 = new javafx.scene.control.Separator();
        javafx.scene.control.Separator sep2 = new javafx.scene.control.Separator();

        VBox inventoryPanel = new VBox(12, swordLabel, sep1, armorLabel, sep2, potionLabel, inventoryBackButton);
        inventoryPanel.setAlignment(Pos.TOP_LEFT);
        inventoryPanel.setPrefSize(350, 360);
        inventoryPanel.setPadding(new Insets(28, 18, 28, 18));
        inventoryPanel.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");
        AnchorPane.setTopAnchor(inventoryPanel, 180.0);
        AnchorPane.setLeftAnchor(inventoryPanel, 520.0);
        inventoryPanel.setVisible(false);

        inventoryButton.setOnAction(e -> {
            mainPanel.setVisible(false);
            inventoryPanel.setVisible(true);
        });
        shopButton.setOnAction(e -> {
            mainPanel.setVisible(false);
            shopPanel.setVisible(true);
        });
        fightButton.setOnAction(e -> stage.setScene(dungeonStart(stage)));
        backButton.setOnAction(e -> {
            shopPanel.setVisible(false);
            mainPanel.setVisible(true);
        });
        inventoryBackButton.setOnAction(e -> {
            inventoryPanel.setVisible(false);
            mainPanel.setVisible(true);
        });

        layout.getChildren().addAll(title, healthContainer, mainPanel, shopPanel, inventoryPanel);
        return new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Button shopButton(String itemText, String priceText) {
        Label itemLabel = new Label(itemText);
        itemLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        HBox buttonText;
        if (priceText.isEmpty()) {
            buttonText = new HBox(itemLabel);
        } else {
            Label priceLabel = new Label(priceText);
            priceLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: yellow;");
            buttonText = new HBox(10, itemLabel, priceLabel);
        }

        buttonText.setAlignment(Pos.CENTER);

        Button button = new Button();
        button.setGraphic(buttonText);
        button.setPrefHeight(85);
        button.setMaxWidth(Double.MAX_VALUE);
        String baseStyle = "-fx-background-color: #3C78D8; -fx-background-radius: 0; -fx-border-color: #0A496C; -fx-border-width: 3px; -fx-padding: 0;";
        String hoverStyle = "-fx-background-color: #2E5FB8; -fx-background-radius: 0; -fx-border-color: #0A496C; -fx-border-width: 3px; -fx-padding: 0;";
        String pressedStyle = "-fx-background-color: #244A91; -fx-background-radius: 0; -fx-border-color: #0A496C; -fx-border-width: 3px; -fx-padding: 0;";

        button.setStyle(baseStyle);

        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));
        button.setOnMousePressed(e -> button.setStyle(pressedStyle));
        button.setOnMouseReleased(e -> button.setStyle(hoverStyle));
        return button;
    }


    private Scene dungeonStart (Stage stage) {
        int dungeonSpacing = 10;

        Label titleLabel = new Label(dungeonTitle);
        Label bodyLabel = new Label(bodyText);
        Label conclusionLabel = new Label(conclusionText);
        Label promptLabel = new Label(userPrompt);

        Button backButton = new Button(goBack);
        Button forwardButton = new Button(pressOn);

        backButton.setOnAction(e -> stage.setScene(town(stage)));
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
