import database.DatabaseManager;
import database.UserDAO;
import database.InventoryDAO;
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
import javafx.scene.image.ImageView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Main extends Application {
    private DatabaseManager userDataManager;
    private UserDAO userDAO;
    private InventoryDAO inventoryDAO;
    private String currentUsername;

    private static final int SCENE_WIDTH = 430;
    private static final int SCENE_HEIGHT = 720;
    private static final int prefWidth = 200;
    // Sprite animation settings
    private static final double FRAME_WIDTH = 100;
    private static final double FRAME_HEIGHT = 55;
    private static final int IDLE_FRAMES = 8;
    private static final double SPRITE_SCALE = 7;

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


    @Override
    //main start of the program don't touch.
    public void start(Stage stage) {
        Font.loadFont(getClass().getResourceAsStream("/org/turnbasedtitans/project2/fonts/PixelifySans.ttf"), 12);
        userDataManager = new DatabaseManager();
        userDAO = new UserDAO(userDataManager);
        inventoryDAO = new InventoryDAO(userDataManager.getConnection());

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
        stage.setScene(home(stage));
        stage.show();
    }


    private Scene home(Stage stage) {
        int spacing = 15;

        Label title = new Label("RPG Battle Quest");
        Label result = new Label();
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

    private Scene home(Stage stage, String message) {
        Scene scene = home(stage);
        AnchorPane layout = (AnchorPane) scene.getRoot();
        VBox homePage = (VBox) layout.getChildren().get(0);
        Label result = (Label) homePage.getChildren().get(1);
        result.setText(message);
        return scene;
    }

    private Scene registerPage(Stage stage) {
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
                stage.setScene(town(stage));
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
    private int getCurrentBattlesWon() {
        if (currentUsername == null) {
            return 0;
        }

        try (java.sql.ResultSet inventory = inventoryDAO.getInventory(currentUsername)) {
            if (inventory.next()) {
                return inventory.getInt("battles_won");
            }
        } catch (Exception e) {
            System.err.println("Load battles won failed: " + e.getMessage());
        }

        return 0;
    }

    private String getInventoryText(String columnName, String defaultValue) {
        if (currentUsername == null) {
            return defaultValue;
        }

        try (java.sql.ResultSet inventory = inventoryDAO.getInventory(currentUsername)) {
            if (inventory.next()) {
                return inventory.getString(columnName);
            }
        } catch (Exception e) {
            System.err.println("Load inventory failed: " + e.getMessage());
        }

        return defaultValue;
    }

    private int getInventoryNumber(String columnName, int defaultValue) {
        if (currentUsername == null) {
            return defaultValue;
        }

        try (java.sql.ResultSet inventory = inventoryDAO.getInventory(currentUsername)) {
            if (inventory.next()) {
                return inventory.getInt(columnName);
            }
        } catch (Exception e) {
            System.err.println("Load inventory failed: " + e.getMessage());
        }

        return defaultValue;
    }

    private int getSwordPrice() {
        if (currentUsername == null) {
            return 2;
        }

        String insertSql = "INSERT OR IGNORE INTO shop(username) VALUES(?)";
        String selectSql = "SELECT sword_price FROM shop WHERE username = ?";

        try (java.sql.PreparedStatement insertStmt = userDataManager.getConnection().prepareStatement(insertSql)) {
            insertStmt.setString(1, currentUsername);
            insertStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Create shop row failed: " + e.getMessage());
        }

        try (java.sql.PreparedStatement selectStmt = userDataManager.getConnection().prepareStatement(selectSql)) {
            selectStmt.setString(1, currentUsername);
            java.sql.ResultSet result = selectStmt.executeQuery();
            if (result.next()) {
                return result.getInt("sword_price");
            }
        } catch (Exception e) {
            System.err.println("Load sword price failed: " + e.getMessage());
        }

        return 2;
    }

    private int getArmorPrice() {
        if (currentUsername == null) {
            return 1;
        }

        String insertSql = "INSERT OR IGNORE INTO shop(username) VALUES(?)";
        String selectSql = "SELECT armor_price FROM shop WHERE username = ?";

        try (java.sql.PreparedStatement insertStmt = userDataManager.getConnection().prepareStatement(insertSql)) {
            insertStmt.setString(1, currentUsername);
            insertStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Create shop row failed: " + e.getMessage());
        }

        try (java.sql.PreparedStatement selectStmt = userDataManager.getConnection().prepareStatement(selectSql)) {
            selectStmt.setString(1, currentUsername);
            java.sql.ResultSet result = selectStmt.executeQuery();
            if (result.next()) {
                return result.getInt("armor_price");
            }
        } catch (Exception e) {
            System.err.println("Load armor price failed: " + e.getMessage());
        }

        return 1;
    }

    private int getHealingPrice() {
        if (currentUsername == null) {
            return 1;
        }

        String insertSql = "INSERT OR IGNORE INTO shop(username) VALUES(?)";
        String selectSql = "SELECT healing_price FROM shop WHERE username = ?";

        try (java.sql.PreparedStatement insertStmt = userDataManager.getConnection().prepareStatement(insertSql)) {
            insertStmt.setString(1, currentUsername);
            insertStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Create shop row failed: " + e.getMessage());
        }

        try (java.sql.PreparedStatement selectStmt = userDataManager.getConnection().prepareStatement(selectSql)) {
            selectStmt.setString(1, currentUsername);
            java.sql.ResultSet result = selectStmt.executeQuery();
            if (result.next()) {
                return result.getInt("healing_price");
            }
        } catch (Exception e) {
            System.err.println("Load healing price failed: " + e.getMessage());
        }

        return 1;
    }

    private void buySword(Stage stage) {
        if (currentUsername == null) {
            return;
        }

        String currentSword = getInventoryText("sword", "Bronze");
        if (currentSword.equals("Diamond")) {
            return;
        }

        int swordPrice = getSwordPrice();
        int battlesWon = getCurrentBattlesWon();

        if (battlesWon < swordPrice) {
            return;
        }

        String nextSword = currentSword.equals("Bronze") ? "Iron" : "Diamond";
        String inventorySql = "UPDATE inventory SET sword = ? WHERE username = ?";
        String shopSql = "UPDATE shop SET sword_price = sword_price + 2 WHERE username = ?";

        try (java.sql.PreparedStatement inventoryStmt = userDataManager.getConnection().prepareStatement(inventorySql);
             java.sql.PreparedStatement shopStmt = userDataManager.getConnection().prepareStatement(shopSql)) {
            inventoryStmt.setString(1, nextSword);
            inventoryStmt.setString(2, currentUsername);
            inventoryStmt.executeUpdate();

            shopStmt.setString(1, currentUsername);
            shopStmt.executeUpdate();

            stage.setScene(town(stage));
        } catch (Exception e) {
            System.err.println("Buy sword failed: " + e.getMessage());
        }
    }

    private void buyArmor(Stage stage) {
        if (currentUsername == null) {
            return;
        }

        String currentArmor = getInventoryText("armor", "Bronze");
        if (currentArmor.equals("Diamond")) {
            return;
        }

        int armorPrice = getArmorPrice();
        int battlesWon = getCurrentBattlesWon();

        if (battlesWon < armorPrice) {
            return;
        }

        String nextArmor = currentArmor.equals("Bronze") ? "Iron" : "Diamond";
        String inventorySql = "UPDATE inventory SET armor = ? WHERE username = ?";
        String shopSql = "UPDATE shop SET armor_price = armor_price + 1 WHERE username = ?";

        try (java.sql.PreparedStatement inventoryStmt = userDataManager.getConnection().prepareStatement(inventorySql);
             java.sql.PreparedStatement shopStmt = userDataManager.getConnection().prepareStatement(shopSql)) {
            inventoryStmt.setString(1, nextArmor);
            inventoryStmt.setString(2, currentUsername);
            inventoryStmt.executeUpdate();

            shopStmt.setString(1, currentUsername);
            shopStmt.executeUpdate();

            stage.setScene(town(stage));
        } catch (Exception e) {
            System.err.println("Buy armor failed: " + e.getMessage());
        }
    }

    private void buyHealingPotion(Stage stage) {
        if (currentUsername == null) {
            return;
        }

        int currentPotions = getInventoryNumber("healing_potions", 0);
        if (currentPotions >= 3) {
            stage.setScene(town(stage, "Max 3 potions."));
            return;
        }

        int healingPrice = getHealingPrice();
        int battlesWon = getCurrentBattlesWon();

        if (battlesWon < healingPrice) {
            return;
        }

        String inventorySql = "UPDATE inventory SET healing_potions = healing_potions + 1 WHERE username = ?";

        try (java.sql.PreparedStatement inventoryStmt = userDataManager.getConnection().prepareStatement(inventorySql)) {
            inventoryStmt.setString(1, currentUsername);
            inventoryStmt.executeUpdate();

            stage.setScene(town(stage));
        } catch (Exception e) {
            System.err.println("Buy healing potion failed: " + e.getMessage());
        }
    }

    private String itemColor(String itemName) {
        if (itemName.equals("Bronze")) {
            return "#CD7F32";
        }
        if (itemName.equals("Iron")) {
            return "white";
        }
        if (itemName.equals("Diamond")) {
            return "#00BFFF";
        }
        return "white";
    }

    private HBox inventoryItemLabel(String labelText, String itemName) {
        Label nameLabel = new Label(labelText + ": ");
        nameLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        Label itemLabel = new Label(itemName);
        itemLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: " + itemColor(itemName) + ";");

        HBox row = new HBox(nameLabel, itemLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Scene town(Stage stage) {
        return town(stage, "");
    }

    private Scene town(Stage stage, String shopMessage) {
        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-font-family: 'Pixelify Sans';");
        Image bgImage = new Image(getClass().getResource("/org/turnbasedtitans/project2/village/animatedtown-crop-2.gif").toExternalForm());
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

        Image heroImage = new Image(getClass().getResourceAsStream("/org/turnbasedtitans/project2/characters/HeroKnight.png"));
        Canvas hero = new Canvas(FRAME_WIDTH * SPRITE_SCALE, FRAME_HEIGHT * SPRITE_SCALE);
        GraphicsContext heroGraphics = hero.getGraphicsContext2D();
        heroGraphics.setImageSmoothing(false);
        heroGraphics.drawImage(heroImage, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, 0, 0, FRAME_WIDTH * SPRITE_SCALE, FRAME_HEIGHT * SPRITE_SCALE);

        final int[] heroFrame = {0};
        Timeline heroAnimation = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            heroFrame[0] = (heroFrame[0] + 1) % IDLE_FRAMES;
            heroGraphics.clearRect(0, 0, hero.getWidth(), hero.getHeight());
            heroGraphics.drawImage(heroImage,
                    heroFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT,
                    0, 0, FRAME_WIDTH * SPRITE_SCALE, FRAME_HEIGHT * SPRITE_SCALE);
        }));
        heroAnimation.setCycleCount(Animation.INDEFINITE);
        heroAnimation.play();

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

        Label battlesValue = new Label(String.valueOf(getCurrentBattlesWon()));
        battlesValue.setStyle("-fx-font-size: 26px; -fx-text-fill: yellow;");

        HBox battlesWonLabel = new HBox(8, battlesText, battlesValue);
        battlesWonLabel.setAlignment(Pos.CENTER);

        Button armorButton = getInventoryText("armor", "Bronze").equals("Diamond")
                ? shopButton("Armor", "Maxed Out")
                : shopButton("Armor Upgrade -", String.valueOf(getArmorPrice()));
        Button swordButton = getInventoryText("sword", "Bronze").equals("Diamond")
                ? shopButton("Sword", "Maxed Out")
                : shopButton("Sword Upgrade -", String.valueOf(getSwordPrice()));
        Button healingButton = shopButton("Healing -", String.valueOf(getHealingPrice()));
        Button backButton = shopButton("Back", "");

        VBox shopPanel = new VBox(18, shopTitle, shopError, battlesWonLabel, armorButton, swordButton, healingButton, backButton);
        shopPanel.setAlignment(Pos.TOP_CENTER);
        shopPanel.setPrefSize(350, 420);
        shopPanel.setMaxHeight(420);
        shopPanel.setPadding(new Insets(28, 18, 18, 18));
        shopPanel.setStyle("-fx-background-color: rgba(10, 73, 108, 0.75); -fx-border-color: #0A496C; -fx-border-width: 3px;");
        AnchorPane.setTopAnchor(shopPanel, 160.0);
        AnchorPane.setLeftAnchor(shopPanel, 520.0);
        shopPanel.setVisible(false);

        HBox swordLabel = inventoryItemLabel("Sword", getInventoryText("sword", "Bronze"));
        HBox armorLabel = inventoryItemLabel("Armor", getInventoryText("armor", "Bronze"));

        Label potionLabel = new Label("Healing Potions: " + getInventoryNumber("healing_potions", 0));
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
        armorButton.setOnAction(e -> buyArmor(stage));
        swordButton.setOnAction(e -> buySword(stage));
        healingButton.setOnAction(e -> buyHealingPotion(stage));
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
