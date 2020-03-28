package smartrail;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static smartrail.Constants.*;

public class MainController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initGUI(primaryStage);
    }

    /* GUI starts from here */
    private void initGUI(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Label title = new Label("SmartRail");

        gridPane.add(title,0,0);

        Scene scene = new Scene(gridPane,widthOfMainPane, heightOfMainPane);
        primaryStage.setTitle("SmartRail");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}