package org.turnbasedtitans.project2;

import org.turnbasedtitans.project2.database.InventoryDAO;
import org.turnbasedtitans.project2.controller.TownController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import org.turnbasedtitans.project2.database.DatabaseManager;
import org.turnbasedtitans.project2.database.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStream;


public class SceneFactory {
    private final DatabaseManager userDataManager;
    private final InventoryDAO inventoryDAO;
    private final TownController townController;
    private String currentUsername;

    private static final double FRAME_WIDTH = 100;
    private static final double FRAME_HEIGHT = 55;
    private static final int IDLE_FRAMES = 8;
    private static final double SPRITE_SCALE = 7;

    public SceneFactory(DatabaseManager userDataManager, InventoryDAO inventoryDAO) {
        this.userDataManager = userDataManager;
        this.inventoryDAO = inventoryDAO;
        this.townController = new TownController(userDataManager, inventoryDAO);
    }
    private static final int SCENE_WIDTH = 430;
    private static final int SCENE_HEIGHT = 720;

    //DUNGEON TEXT PRESETS
    private static final String dungeonTitle = "The Dungeon";
    private static final String bodyText = "The atmosphere is dark and oppressive.";
    private static final String conclusionText = "Many have entered seeking glory, but none have returned.";
    private static final String userPrompt = "What do you do?";
    private static final String goBack = "Turn Back";
    private static final String pressOn = "Press On";

    //FIGHT TEXT PRESETS
    private static final String userAttack = "Attack";
    private static final String userDefend = "Defend";
    private static final String userEscape = "Run";


    public Scene create(SceneType type, Stage stage) {
        return switch (type) {
            case HOME -> home(stage);
            case REGISTER -> registerPage(stage);
            case LOGIN -> logInPage(stage);
            case TOWN -> town(stage, currentUsername);
            case DUNGEON_START -> dungeonStart(stage);
            case DUNGEON_FIGHT -> dungeonFight(stage);
        };
    }
    private static Scene home(Stage stage, UserDAO userDAO) {
        int spacing = 13;
        Label title = new Label("RPG BATTLE QUEST");
        Button create = new Button("Create Account");
        Button login = new Button("Login");

        create.setOnAction(e -> {
            stage.setScene(home(stage, userDAO));
        });

        login.setOnAction(e -> {
            stage.setScene(home(stage, userDAO));
        });

        VBox homePage = new VBox(spacing, title, create, login);
        homePage.setPadding(new Insets(30));
        homePage.setAlignment(Pos.CENTER);

        return new Scene(homePage, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Scene registerPage(Stage stage) {
        UserDAO userDAO = new UserDAO(userDataManager);
        Label title = new Label("Create  Account");
        TextField username = new TextField();
        TextField password = new TextField();
        Label result = new Label();
        Button create = shopButton("Create Account", "");
        Button back = shopButton("Back", "");

        create.setPrefHeight(55);
        back.setPrefHeight(55);
        create.setMaxWidth(380);
        back.setMaxWidth(220);

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
                stage.setScene(home(stage, "Account successfully made!"));
            } else {
                result.setText("Username already exists.");
            }
        });

        title.setText("REGISTER");
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: white;");

        username.setPromptText("Username");
        password.setPromptText("Password");
        username.setMaxWidth(380);
        password.setMaxWidth(380);
        username.setPrefHeight(55);
        password.setPrefHeight(55);
        username.setStyle("-fx-font-size: 22px;");
        password.setStyle("-fx-font-size: 22px;");
        result.setStyle("-fx-font-size: 18px; -fx-text-fill: red;");

        VBox fieldSection = new VBox(15, username, password, create, result);
        fieldSection.setAlignment(Pos.CENTER);

        VBox registerPanel = new VBox(30, title, fieldSection, back);
        registerPanel.setAlignment(Pos.CENTER);
        registerPanel.setPadding(new Insets(30));
        registerPanel.setMaxWidth(430);
        registerPanel.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");

        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-font-family: 'Pixelify Sans';");
        Image bgImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/titlescreen/nighttime.gif").toExternalForm());
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        layout.setBackground(new Background(bg));
        layout.getChildren().add(registerPanel);
        AnchorPane.setTopAnchor(registerPanel, 160.0);
        AnchorPane.setLeftAnchor(registerPanel, 40.0);
        AnchorPane.setRightAnchor(registerPanel, 40.0);

        return new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Scene logInPage(Stage stage) {
        UserDAO userDAO = new UserDAO(userDataManager);
        Label title = new Label("Log In");
        TextField username = new TextField();
        TextField password = new TextField();
        Label result = new Label();
        Button logIn = shopButton("Log In", "");
        Button back = shopButton("Back", "");

        logIn.setPrefHeight(55);
        back.setPrefHeight(55);
        logIn.setMaxWidth(380);
        back.setMaxWidth(220);

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
                currentUsername = user;
                stage.setScene(town(stage, currentUsername));
            } else {
                result.setText("Invalid username or password.");
            }
        });

        title.setText("LOG IN");
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: white;");

        username.setPromptText("Username");
        password.setPromptText("Password");
        username.setMaxWidth(380);
        password.setMaxWidth(380);
        username.setPrefHeight(55);
        password.setPrefHeight(55);
        username.setStyle("-fx-font-size: 22px;");
        password.setStyle("-fx-font-size: 22px;");
        result.setStyle("-fx-font-size: 18px; -fx-text-fill: red;");

        VBox fieldSection = new VBox(15, username, password, logIn, result);
        fieldSection.setAlignment(Pos.CENTER);

        VBox loginPanel = new VBox(30, title, fieldSection, back);
        loginPanel.setAlignment(Pos.CENTER);
        loginPanel.setPadding(new Insets(30));
        loginPanel.setMaxWidth(430);
        loginPanel.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");

        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-font-family: 'Pixelify Sans';");
        Image bgImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/titlescreen/nighttime.gif").toExternalForm());
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        layout.setBackground(new Background(bg));
        layout.getChildren().add(loginPanel);
        AnchorPane.setTopAnchor(loginPanel, 160.0);
        AnchorPane.setLeftAnchor(loginPanel, 40.0);
        AnchorPane.setRightAnchor(loginPanel, 40.0);

        return new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
    }
    private Scene dungeonStart (Stage stage) {
        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-font-family: 'Pixelify Sans';");
        Image bgImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/dungeon/dungeon.gif").toExternalForm());
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitHeight(SCENE_HEIGHT);
        bgView.setPreserveRatio(true);
        bgView.fitWidthProperty().bind(layout.widthProperty());
        bgView.fitHeightProperty().bind(layout.heightProperty());
        bgView.setPreserveRatio(false);

        Label titleLabel = sceneTitle(dungeonTitle);
        anchorTitle(titleLabel);

        int healthValue = townController.getInventoryNumber("health", 100);
        HBox healthContainer = playerHealthContainer(playerHealthBar(healthValue), playerHealthLabel(healthValue));
        anchorHealthContainer(healthContainer);

        Label bodyLabel = new Label(bodyText);
        Label conclusionLabel = new Label(conclusionText);
        Label promptLabel = new Label(userPrompt);

        bodyLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
        conclusionLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
        promptLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: yellow;");
        bodyLabel.setWrapText(true);
        conclusionLabel.setWrapText(true);
        promptLabel.setWrapText(true);

        Button backButton = shopButton(goBack, "");
        Button forwardButton = shopButton(pressOn, "");

        backButton.setOnAction(e -> stage.setScene(town(stage, currentUsername)));
        forwardButton.setOnAction(e -> stage.setScene(dungeonFight(stage)));

        VBox dungeonPanel = new VBox(20, bodyLabel, conclusionLabel, promptLabel, backButton, forwardButton);
        dungeonPanel.setAlignment(Pos.CENTER);
        dungeonPanel.setPadding(new Insets(28, 18, 28, 18));
        dungeonPanel.setPrefSize(350, 360);
        dungeonPanel.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");

        AnchorPane.setTopAnchor(dungeonPanel, 190.0);
        AnchorPane.setLeftAnchor(dungeonPanel, 40.0);
        AnchorPane.setRightAnchor(dungeonPanel, 40.0);

        layout.getChildren().addAll(bgView, titleLabel, healthContainer, dungeonPanel);
        return new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
    }
    private Scene dungeonFight (Stage stage) {
        //PREFIGHT SET-UP
        EnemyController systemControl = new EnemyController();
        Enemies commonEnemy = systemControl.enemyRandomizer();
        RPGBattleSystem battleSystem = new RPGBattleSystem(commonEnemy, inventoryDAO, currentUsername);
        Label battleTracker = new Label();
        Label deathLabel = new Label("YOU DIED");
        Label winLabel = new Label("YOU WIN");
        Image laserImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/dungeon/dungeon-laser.gif").toExternalForm());
        Image bgImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/dungeon/dungeon.gif").toExternalForm());
        ImageView bgView = new ImageView(bgImage);
        bgView.fitWidthProperty().bind(stage.widthProperty());
        bgView.fitHeightProperty().bind(stage.heightProperty());
        bgView.setPreserveRatio(false);

        //TEXT SECTION
        Label encounterStartLabel = new Label("A wild ");
        encounterStartLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label encounterEnemyLabel = new Label(commonEnemy.getEnemyName());
        encounterEnemyLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: yellow;");

        Label encounterEndLabel = new Label(" has appeared! Your life is in danger!");
        encounterEndLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        HBox encounterLabel = new HBox(encounterStartLabel, encounterEnemyLabel, encounterEndLabel);
        Label playerHealth = new Label("Player HP: " + battleSystem.getPlayerHP());
        Label enemyHealth = new Label(commonEnemy.getEnemyName() + "HP: " + battleSystem.getEnemyHP());

        // Health bar UI
        int healthValue = townController.getInventoryNumber("health", 100);
        ProgressBar healthBar = playerHealthBar(healthValue);
        Label healthPercentLabel = playerHealthLabel(healthValue);
        HBox healthContainer = playerHealthContainer(healthBar, healthPercentLabel);
        anchorHealthContainer(healthContainer);

        AnimatedSprite heroSprite = heroSpriteCanvas();
        Canvas hero = heroSprite.getCanvas();
        VBox heroBox = new VBox(hero);
        heroBox.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(heroBox, -40.0);
        AnchorPane.setTopAnchor(heroBox, 100.0);
        AnchorPane.setBottomAnchor(heroBox, 40.0);

        AnimatedSprite enemySprite = enemySpriteCanvas(commonEnemy.getSpritePath());
        Canvas enemy = enemySprite.getCanvas();
        int enemyStartingHP = systemControl.enemyStartingHP(commonEnemy);
        ProgressBar enemySpriteHealthBar = new ProgressBar(1.0);
        enemySpriteHealthBar.setPrefWidth(150);
        enemySpriteHealthBar.setPrefHeight(16);
        enemySpriteHealthBar.setStyle("-fx-accent: red;");
        Label enemySpriteHealthLabel = new Label((int) ((battleSystem.getEnemyHP() / (double) enemyStartingHP) * 100) + "%");
        enemySpriteHealthLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        HBox enemySpriteHealthContainer = new HBox(6, enemySpriteHealthBar, enemySpriteHealthLabel);
        enemySpriteHealthContainer.setAlignment(Pos.CENTER);
        VBox enemyBox = new VBox(12, enemySpriteHealthContainer, enemy);
        enemyBox.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(enemyBox, 40.0);
        AnchorPane.setTopAnchor(enemyBox, 100.0);
        AnchorPane.setBottomAnchor(enemyBox, 40.0);

        //ACTION BUTTON SECTION
        Button attackButton = shopButton(userAttack, "");
        Button defendButton = shopButton(userDefend, "");
        Button escapeButton = shopButton(userEscape, "");
        Button potionButton = shopButton("Healing Potions:", String.valueOf(townController.getInventoryNumber("healing_potions", 0)));

        //POLISH/FORMATTING
        attackButton.setPrefWidth(150);
        defendButton.setPrefWidth(150);
        escapeButton.setPrefWidth(150);
        attackButton.setPrefHeight(60);
        defendButton.setPrefHeight(60);
        escapeButton.setPrefHeight(60);
        potionButton.setPrefWidth(270);
        potionButton.setPrefHeight(60);


            attackButton.setOnAction(e -> {
            encounterLabel.setVisible(false);
            attackButton.setDisable(true);
            defendButton.setDisable(true);
            escapeButton.setDisable(true);

            heroSprite.play(3, 0, 8);
            enemySprite.play(0, 4, 4);

            int playerDMG = battleSystem.playerAttack();
            enemyHealth.setText(commonEnemy.getEnemyName() + "HP: " + battleSystem.getEnemyHP());
            enemySpriteHealthBar.setProgress(Math.max(0, battleSystem.getEnemyHP() / (double) enemyStartingHP));
            enemySpriteHealthLabel.setText((int) ((battleSystem.getEnemyHP() / (double) enemyStartingHP) * 100) + "%");
            battleTracker.setVisible(true);
            battleTracker.setText("You've dealt " + playerDMG + "!");

            if(battleSystem.enemyDefeatedTF()) {
                townController.addBattlesWon();
                PauseTransition attackFinishDelay = new PauseTransition(Duration.millis(150 * 8));
                attackFinishDelay.setOnFinished(event -> {
                    heroSprite.play(0, 0, IDLE_FRAMES);
                    enemySprite.play(4, 0, 3);
                    bgView.setImage(laserImage);
                    PauseTransition laserDelay = new PauseTransition(Duration.millis(2500));
                    laserDelay.setOnFinished(winEvent -> {
                        bgView.setImage(bgImage);
                        enemyBox.setVisible(false);
                        winLabel.setVisible(true);
                        winLabel.toFront();
                        PauseTransition winDelay = new PauseTransition(Duration.seconds(3));
                        winDelay.setOnFinished(returnEvent -> stage.setScene(town(stage, currentUsername)));
                        winDelay.play();
                    });
                    laserDelay.play();
                });
                attackFinishDelay.play();
                return;
            }

            PauseTransition enemyTurnDelay = new PauseTransition(Duration.seconds(1));
            enemyTurnDelay.setOnFinished(event -> {
                enemySprite.play(2, 0, 8, -7);
                heroSprite.play(0, 0, IDLE_FRAMES);

                int enemyDMG = battleSystem.enemyAttack();
                playerHealth.setText("Player HP: " + battleSystem.getPlayerHP());
                healthBar.setProgress(battleSystem.getPlayerHP() / 100.0);
                healthPercentLabel.setText(battleSystem.getPlayerHP() + "%");
                battleTracker.setText(battleTracker.getText() + "  " + commonEnemy.getEnemyName() + " dealt " + enemyDMG + "!");

                PauseTransition resetDelay = new PauseTransition(Duration.seconds(1));
                resetDelay.setOnFinished(resetEvent -> {
                    if(battleSystem.playerDefeatedTF()) {
                        showDeathThenReturn(stage, battleSystem, deathLabel);
                        return;
                    }

                    heroSprite.play(0, 0, IDLE_FRAMES);
                    enemySprite.play(0, 0, 4);
                    attackButton.setDisable(false);
                    defendButton.setDisable(false);
                    escapeButton.setDisable(false);
                });
                resetDelay.play();
            });
            enemyTurnDelay.play();
        });

        defendButton.setOnAction(e -> {
            encounterLabel.setVisible(false);
            attackButton.setDisable(true);
            defendButton.setDisable(true);
            escapeButton.setDisable(true);

            battleSystem.activateDefend();
            heroSprite.play(6, 0, 8);
            enemySprite.play(2, 0, 8, -7);

            PauseTransition defendDelay = new PauseTransition(Duration.seconds(1));
            defendDelay.setOnFinished(event -> {

                int enemyDMG = battleSystem.enemyAttack();
                playerHealth.setText("Player HP: " + battleSystem.getPlayerHP());
                healthBar.setProgress(battleSystem.getPlayerHP() / 100.0);
                healthPercentLabel.setText(battleSystem.getPlayerHP() + "%");
                enemyHealth.setText(commonEnemy.getEnemyName() + "HP: " + battleSystem.getEnemyHP());
                enemySpriteHealthBar.setProgress(Math.max(0, battleSystem.getEnemyHP() / (double) enemyStartingHP));
                enemySpriteHealthLabel.setText((int) ((battleSystem.getEnemyHP() / (double) enemyStartingHP) * 100) + "%");
                battleTracker.setVisible(true);
                battleTracker.setText("You defend yourself against the oncoming attack... " + commonEnemy.getEnemyName() + " dealt " + enemyDMG + "!");

                PauseTransition resetDelay = new PauseTransition(Duration.seconds(1));
                resetDelay.setOnFinished(resetEvent -> {
                    if(battleSystem.playerDefeatedTF()) {
                        showDeathThenReturn(stage, battleSystem, deathLabel);
                        return;
                    }

                    heroSprite.play(0, 0, IDLE_FRAMES);
                    enemySprite.play(0, 0, 4);
                    attackButton.setDisable(false);
                    defendButton.setDisable(false);
                    escapeButton.setDisable(false);
                });
                resetDelay.play();
            });
            defendDelay.play();
        });

        escapeButton.setOnAction(e -> {
            encounterLabel.setVisible(false);
            battleTracker.setVisible(true);
            int rngRoll = battleSystem.escapeChance();
            if (battleSystem.escapeSuccess(rngRoll)) {
                stage.setScene(dungeonStart(stage));
            } else {
                int enemyDMG = battleSystem.enemyAttack();
                playerHealth.setText("Player HP: " + battleSystem.getPlayerHP());
                healthBar.setProgress(battleSystem.getPlayerHP() / 100.0);
                healthPercentLabel.setText(battleSystem.getPlayerHP() + "%");
                enemyHealth.setText(commonEnemy.getEnemyName() + "HP: " + battleSystem.getEnemyHP());
                enemySpriteHealthBar.setProgress(Math.max(0, battleSystem.getEnemyHP() / (double) enemyStartingHP));
                enemySpriteHealthLabel.setText((int) ((battleSystem.getEnemyHP() / (double) enemyStartingHP) * 100) + "%");
                battleTracker.setText("You failed to run away! " + commonEnemy.getEnemyName() + " dealt " + enemyDMG + "!");
            }
            if(battleSystem.playerDefeatedTF()) {
                showDeathThenReturn(stage, battleSystem, deathLabel);
            }
        });
        potionButton.setOnAction(e -> {
            townController.useHealingPotion();
            int updatedHealth = townController.getInventoryNumber("health", 100);
            battleSystem.setPlayerHP(updatedHealth);
            playerHealth.setText("Player HP: " + battleSystem.getPlayerHP());
            healthBar.setProgress(updatedHealth / 100.0);
            healthPercentLabel.setText(updatedHealth + "%");
            potionButton.setGraphic(shopButton("Healing Potions:", String.valueOf(townController.getInventoryNumber("healing_potions", 0))).getGraphic());
        });
        VBox textSection = new VBox(10, playerHealth, enemyHealth);
        textSection.setAlignment(Pos.CENTER);
        encounterLabel.setAlignment(Pos.CENTER);
        encounterLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setTopAnchor(encounterLabel, 120.0);
        AnchorPane.setLeftAnchor(encounterLabel, 0.0);
        AnchorPane.setRightAnchor(encounterLabel, 0.0);
        battleTracker.setVisible(false);
        battleTracker.setAlignment(Pos.CENTER);
        battleTracker.setMaxWidth(Double.MAX_VALUE);
        battleTracker.setWrapText(false);
        AnchorPane.setTopAnchor(battleTracker, 120.0);
        AnchorPane.setLeftAnchor(battleTracker, 0.0);
        AnchorPane.setRightAnchor(battleTracker, 0.0);
        playerHealth.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        enemyHealth.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        battleTracker.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
        deathLabel.setVisible(false);
        deathLabel.setStyle("-fx-font-size: 120px; -fx-font-weight: bold; -fx-text-fill: red;");
        deathLabel.setAlignment(Pos.CENTER);
        deathLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setTopAnchor(deathLabel, 300.0);
        AnchorPane.setLeftAnchor(deathLabel, 0.0);
        AnchorPane.setRightAnchor(deathLabel, 0.0);
        winLabel.setVisible(false);
        winLabel.setStyle("-fx-font-size: 120px; -fx-font-weight: bold; -fx-text-fill: green;");
        winLabel.setAlignment(Pos.CENTER);
        winLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setTopAnchor(winLabel, 300.0);
        AnchorPane.setLeftAnchor(winLabel, 0.0);
        AnchorPane.setRightAnchor(winLabel, 0.0);

        HBox buttonSection = new HBox(18, attackButton, defendButton, escapeButton, potionButton);
        buttonSection.setAlignment(Pos.CENTER);

        Label titleLabel = sceneTitle("BATTLE");
        anchorTitle(titleLabel);

        VBox fightPanel = new VBox(20, textSection, buttonSection);
        fightPanel.setAlignment(Pos.BOTTOM_CENTER);
        fightPanel.setPadding(new Insets(0, 35, 25, 35));
        fightPanel.setStyle("-fx-background-color: transparent;");
        AnchorPane.setLeftAnchor(fightPanel, 0.0);
        AnchorPane.setRightAnchor(fightPanel, 0.0);
        AnchorPane.setBottomAnchor(fightPanel, 0.0);

        AnchorPane fightLayout = new AnchorPane(titleLabel, healthContainer, encounterLabel, battleTracker, deathLabel, winLabel, heroBox, enemyBox, fightPanel);
        fightLayout.setStyle("-fx-font-family: 'Pixelify Sans';");
        fightLayout.getChildren().add(0, bgView);

        return new Scene(fightLayout, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private void showDeathThenReturn(Stage stage, RPGBattleSystem battleSystem, Label deathLabel) {
        deathLabel.setVisible(true);
        deathLabel.toFront();

        PauseTransition deathDelay = new PauseTransition(Duration.seconds(3));
        deathDelay.setOnFinished(event -> {
            battleSystem.setPlayerHP(100);
            stage.setScene(town(stage, currentUsername));
        });
        deathDelay.play();
    }

    public Scene town(Stage stage, String username) {
        currentUsername = username;
        townController.setCurrentUsername(username);
        return townUI(stage, "");
    }


    private HBox inventoryItemLabel(String labelText, String itemName) {
        Label nameLabel = new Label(labelText + ": ");
        nameLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        Label itemLabel = new Label(itemName);
        itemLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: " + townController.itemColor(itemName) + ";");

        HBox row = new HBox(nameLabel, itemLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Scene townUI(Stage stage, String shopMessage) {
        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-font-family: 'Pixelify Sans';");
        Image bgImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/animatedtown-crop-2.gif").toExternalForm());
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        layout.setBackground(new Background(bg));

        Label title = sceneTitle("THE VILLAGE");
        anchorTitle(title);

        int healthValue = townController.getInventoryNumber("health", 100);
        ProgressBar healthBar = playerHealthBar(healthValue);
        Label healthLabel = playerHealthLabel(healthValue);
        HBox healthContainer = playerHealthContainer(healthBar, healthLabel);
        anchorHealthContainer(healthContainer);

        Canvas hero = heroSpriteCanvas().getCanvas();

        VBox heroBox = new VBox(hero);
        heroBox.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(heroBox, -40.0);
        AnchorPane.setTopAnchor(heroBox, 120.0);
        AnchorPane.setBottomAnchor(heroBox, 40.0);

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

        Label shopError = new Label(shopMessage);
        shopError.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");

        Label battlesText = new Label("Battle's Won -");
        battlesText.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");

        Label battlesValue = new Label(String.valueOf(townController.getCurrentBattlesWon()));
        battlesValue.setStyle("-fx-font-size: 26px; -fx-text-fill: yellow;");

        HBox battlesWonLabel = new HBox(8, battlesText, battlesValue);
        battlesWonLabel.setAlignment(Pos.CENTER);

        Button armorButton = townController.getInventoryText("armor", "Bronze").equals("Diamond")
                ? shopButton("Armor", "Maxed Out")
                : shopButton("Armor Upgrade -", String.valueOf(townController.getArmorPrice()));
        Button swordButton = townController.getInventoryText("sword", "Bronze").equals("Diamond")
                ? shopButton("Sword", "Maxed Out")
                : shopButton("Sword Upgrade -", String.valueOf(townController.getSwordPrice()));
        Button healingButton = shopButton("Healing -", String.valueOf(townController.getHealingPrice()));
        final Button[] armorButtonRef = {armorButton};
        final Button[] swordButtonRef = {swordButton};
        final Button[] healingButtonRef = {healingButton};
        Button backButton = shopButton("Back", "");

        VBox shopPanel = new VBox(18, shopTitle, shopError, battlesWonLabel, armorButton, swordButton, healingButton, backButton);
        shopPanel.setAlignment(Pos.TOP_CENTER);
        shopPanel.setPrefSize(350, 460);
        shopPanel.setMaxHeight(460);
        shopPanel.setPadding(new Insets(28, 18, 18, 18));
        shopPanel.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");
        AnchorPane.setTopAnchor(shopPanel, 140.0);
        AnchorPane.setLeftAnchor(shopPanel, 520.0);
        shopPanel.setVisible(false);

        HBox swordLabel = inventoryItemLabel("Sword", townController.getInventoryText("sword", "Bronze"));
        HBox armorLabel = inventoryItemLabel("Armor", townController.getInventoryText("armor", "Bronze"));


        Button inventoryBackButton = shopButton("Back", "");
        VBox.setMargin(inventoryBackButton, new Insets(30, 0, 0, 0));

        Separator sep1 = new Separator();
        Separator sep2 = new Separator();

        // Healing Potion Button for inventory panel
        Button potionButton = shopButton("Healing Potions:", String.valueOf(townController.getInventoryNumber("healing_potions", 0)));
        potionButton.setOnAction(e -> {
            townController.useHealingPotion();
            int updatedHealth = townController.getInventoryNumber("health", 100);
            healthBar.setProgress(updatedHealth / 100.0);
            healthLabel.setText(updatedHealth + "%");
            potionButton.setGraphic(shopButton("Healing Potions:", String.valueOf(townController.getInventoryNumber("healing_potions", 0))).getGraphic());
        });

        VBox inventoryPanel = new VBox(12, swordLabel, sep1, armorLabel, sep2, potionButton, inventoryBackButton);
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
        armorButton.setOnAction(e -> {
            if (townController.buyArmor()) {
                armorLabel.getChildren().set(1, new Label(townController.getInventoryText("armor", "Bronze")));
                ((Label) armorLabel.getChildren().get(1)).setStyle("-fx-font-size: 28px; -fx-text-fill: " + townController.itemColor(townController.getInventoryText("armor", "Bronze")) + ";");
                battlesValue.setText(String.valueOf(townController.getCurrentBattlesWon()));

                Button newArmorButton = townController.getInventoryText("armor", "Bronze").equals("Diamond")
                        ? shopButton("Armor", "Maxed Out")
                        : shopButton("Armor Upgrade -", String.valueOf(townController.getArmorPrice()));
                newArmorButton.setOnAction(armorButtonRef[0].getOnAction());
                shopPanel.getChildren().set(3, newArmorButton);
                armorButtonRef[0] = newArmorButton;
            }
        });
        swordButton.setOnAction(e -> {
            if (townController.buySword()) {
                swordLabel.getChildren().set(1, new Label(townController.getInventoryText("sword", "Bronze")));
                ((Label) swordLabel.getChildren().get(1)).setStyle("-fx-font-size: 28px; -fx-text-fill: " + townController.itemColor(townController.getInventoryText("sword", "Bronze")) + ";");
                battlesValue.setText(String.valueOf(townController.getCurrentBattlesWon()));

                Button newSwordButton = townController.getInventoryText("sword", "Bronze").equals("Diamond")
                        ? shopButton("Sword", "Maxed Out")
                        : shopButton("Sword Upgrade -", String.valueOf(townController.getSwordPrice()));
                newSwordButton.setOnAction(swordButtonRef[0].getOnAction());
                shopPanel.getChildren().set(4, newSwordButton);
                swordButtonRef[0] = newSwordButton;
            }
        });
        healingButton.setOnAction(e -> {
            String message = townController.buyHealingPotion();
            if (message.isEmpty()) {
                potionButton.setGraphic(shopButton("Healing Potions:", String.valueOf(townController.getInventoryNumber("healing_potions", 0))).getGraphic());
                battlesValue.setText(String.valueOf(townController.getCurrentBattlesWon()));

                Button newHealingButton = shopButton("Healing -", String.valueOf(townController.getHealingPrice()));
                newHealingButton.setOnAction(healingButtonRef[0].getOnAction());
                shopPanel.getChildren().set(5, newHealingButton);
                healingButtonRef[0] = newHealingButton;
            } else {
                shopError.setText(message);
                Timeline clearError = new Timeline(new KeyFrame(Duration.seconds(3), event -> shopError.setText("")));
                clearError.play();
            }
        });
        backButton.setOnAction(e -> {
            shopPanel.setVisible(false);
            mainPanel.setVisible(true);
        });
        inventoryBackButton.setOnAction(e -> {
            inventoryPanel.setVisible(false);
            mainPanel.setVisible(true);
        });

        layout.getChildren().addAll(title, healthContainer, heroBox, mainPanel, shopPanel, inventoryPanel);
        return new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
    }




    private AnimatedSprite heroSpriteCanvas() {
        Image heroImage = new Image(getClass().getResourceAsStream("/org/turnbasedtitans/project2/characters/HeroKnight.png"));
        return new AnimatedSprite(heroImage, FRAME_WIDTH, FRAME_HEIGHT, SPRITE_SCALE, 150, 0, 0, IDLE_FRAMES);
    }

    private AnimatedSprite enemySpriteCanvas(String spritePath) {
        final double enemyFrameWidth = 384.0 / 8;
        final double enemyFrameHeight = 256.0 / 5.0;
        final double enemyScale = Math.min((FRAME_WIDTH * SPRITE_SCALE) / enemyFrameWidth, (FRAME_HEIGHT * SPRITE_SCALE) / enemyFrameHeight);

        if (!spritePath.startsWith("/")) {
            spritePath = "/" + spritePath;
        }

        InputStream enemyStream = getClass().getResourceAsStream(spritePath);
        if (enemyStream == null) {
            throw new IllegalArgumentException("Enemy sprite not found: " + spritePath);
        }

        Image enemyImage = new Image(enemyStream);
        return new AnimatedSprite(enemyImage, enemyFrameWidth, enemyFrameHeight, enemyScale, 150, 0, 0, 4);
    }

    private class AnimatedSprite {
        private final Image image;
        private final Canvas canvas;
        private final GraphicsContext graphics;
        private final double frameWidth;
        private final double frameHeight;
        private final double scale;
        private final double frameSpeed;
        private Timeline animation;

        private AnimatedSprite(Image image, double frameWidth, double frameHeight, double scale, double frameSpeed, int row, int startFrame, int frameCount) {
            this.image = image;
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
            this.scale = scale;
            this.frameSpeed = frameSpeed;
            this.canvas = new Canvas(frameWidth * scale, frameHeight * scale);
            this.graphics = canvas.getGraphicsContext2D();
            this.graphics.setImageSmoothing(false);
            play(row, startFrame, frameCount);
        }

        private Canvas getCanvas() {
            return canvas;
        }

        private void play(int row, int startFrame, int frameCount) {
            play(row, startFrame, frameCount, 0);
        }

        private void play(int row, int startFrame, int frameCount, double sourceYOffset) {
            if (animation != null) {
                animation.stop();
            }

            final int[] currentFrame = {0};
            drawFrame(row, startFrame, sourceYOffset);

            animation = new Timeline(new KeyFrame(Duration.millis(frameSpeed), e -> {
                currentFrame[0] = (currentFrame[0] + 1) % frameCount;
                drawFrame(row, startFrame + currentFrame[0], sourceYOffset);
            }));
            animation.setCycleCount((row == 3 || row == 4) ? frameCount : Animation.INDEFINITE);
            animation.play();
        }

        private void drawFrame(int row, int frame, double sourceYOffset) {
            graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphics.drawImage(
                    image,
                    frame * frameWidth,
                    row * frameHeight + sourceYOffset,
                    frameWidth,
                    frameHeight,
                    0,
                    0,
                    frameWidth * scale,
                    frameHeight * scale
            );
        }
    }

    private Label sceneTitle(String text) {
        Label title = new Label(text);
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: white;");
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        return title;
    }

    private void anchorTitle(Label title) {
        AnchorPane.setTopAnchor(title, 20.0);
        AnchorPane.setLeftAnchor(title, 0.0);
        AnchorPane.setRightAnchor(title, 0.0);
    }

    private ProgressBar playerHealthBar(int healthValue) {
        ProgressBar healthBar = new ProgressBar(healthValue / 100.0);
        healthBar.setPrefHeight(25);
        healthBar.setMaxWidth(Double.MAX_VALUE);
        healthBar.setStyle("-fx-accent: #16d715;");
        HBox.setHgrow(healthBar, Priority.ALWAYS);
        return healthBar;
    }

    private Label playerHealthLabel(int healthValue) {
        Label healthLabel = new Label(healthValue + "%");
        healthLabel.setStyle("-fx-font-size: 20px;");
        return healthLabel;
    }

    private HBox playerHealthContainer(ProgressBar healthBar, Label healthLabel) {
        HBox healthContainer = new HBox(6, healthBar, healthLabel);
        healthContainer.setAlignment(Pos.CENTER);
        return healthContainer;
    }

    private void anchorHealthContainer(HBox healthContainer) {
        AnchorPane.setTopAnchor(healthContainer, 85.0);
        AnchorPane.setLeftAnchor(healthContainer, 15.0);
        AnchorPane.setRightAnchor(healthContainer, 15.0);
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

    public Scene home(Stage stage) {
        return home(stage, "");
    }

    public Scene home(Stage stage, String message) {
        int spacing = 15;

        Label title = new Label("RPG Battle Quest");
        Label result = new Label(message);
        Button create = shopButton("Create Account", "");
        Button login = shopButton("Log In", "");

        create.setOnAction(e -> stage.setScene(registerPage(stage)));
        login.setOnAction(e -> stage.setScene(logInPage(stage)));

        title.setText("RPG BATTLE QUEST");
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: white;");
        result.setStyle("-fx-font-size: 18px; -fx-text-fill: yellow;");

        VBox homePage = new VBox(spacing, title, result, create, login);
        homePage.setPadding(new Insets(30));
        homePage.setAlignment(Pos.CENTER);
        homePage.setMaxWidth(350);
        homePage.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");

        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-font-family: 'Pixelify Sans';");
        Image bgImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/titlescreen/nighttime.gif").toExternalForm());
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        layout.setBackground(new Background(bg));
        layout.getChildren().add(homePage);
        AnchorPane.setTopAnchor(homePage, 190.0);
        AnchorPane.setLeftAnchor(homePage, 40.0);
        AnchorPane.setRightAnchor(homePage, 40.0);

        return new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
    }
}