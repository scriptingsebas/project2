import database.DatabaseManager;
import database.UserDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SceneFactory {
    private static final int SCENE_WIDTH = 430;
    private static final int SCENE_HEIGHT = 720;


    public static Scene create(SceneType type, Stage stage, UserDAO userDAO) {
        return switch (type) {
            case HOME -> home(stage, userDAO) ;
            case REGISTERPG -> registerPage(stage, userDAO) ;
            case LOGIN -> logInPage(stage, userDAO) ;
        };
    }
    private static Scene home(Stage stage, UserDAO userDAO) {
        int spacing = 13;
        Label title = new Label("RPG BATTLE QUEST");
        Button create = new Button("Create Account");
        Button login = new Button("Login");

        create.setOnAction(e -> {
            stage.setScene(registerPage(stage, userDAO));
        });

        login.setOnAction(e -> {
            stage.setScene(logInPage(stage, userDAO));
        });

        VBox homePage = new VBox(spacing, title, create, login);
        homePage.setPadding(new Insets(30));
        homePage.setAlignment(Pos.CENTER);

        return new Scene(homePage, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private static Scene registerPage(Stage stage, UserDAO userDAO) {
        Label title = new Label("Create  Account");
        TextField username = new TextField();
        TextField password = new TextField();
        Label result = new Label();
        Button create = new Button("Create Account");
        Button back = new Button("Back");

        back.setOnAction(e -> {
            stage.setScene(home(stage, userDAO));
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

    private static Scene logInPage(Stage stage, UserDAO userDAO) {
        Label title = new Label("Log In");
        TextField username = new TextField();
        TextField password = new TextField();
        Label result = new Label();
        Button logIn = new Button("log In");
        Button back = new Button("Back");

        back.setOnAction(e -> {
            stage.setScene(home(stage, userDAO));
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
}