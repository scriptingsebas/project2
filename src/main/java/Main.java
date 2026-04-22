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
    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 300;
    private static final int prefWidth = 200;
    private static final String TITLETEXT = "Farhenheit -> Celcius";
    private static final String INPUTERTEXT = "Enter °F";
    private static final String BUTTONTEXT = "Convert";
    private static final String RESULTTEXT = "HOLYSMOKESITWORKED!";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        int spacing= 12;
        Label TITLETEX =  new Label(TITLETEXT);
        TextField INPUTER = new TextField();
        INPUTER.setPromptText(INPUTERTEXT);
        Label RESULTS = new Label();

        Button convert = new Button(BUTTONTEXT);
        convert.setOnAction(e -> {
            convert.setText("Converted");
            String input = INPUTER.getText();
            try{
                double temperature = Double.parseDouble(input);
            }catch (NumberFormatException ex){
                RESULTS.setText("INVALID INPUT MONK");
            }
        });

        VBox mainChasis = new VBox(spacing,TITLETEX,INPUTER,convert,RESULTS);
        mainChasis.setPadding(new Insets(30));
        mainChasis.setAlignment(Pos.CENTER);
        INPUTER.setPrefWidth(prefWidth);

        Scene scene = new Scene(mainChasis, SCENE_WIDTH, SCENE_HEIGHT);

        stage.setTitle("Temperature" + " Converter");
        stage.setScene(scene);
        stage.show();
    }
}
