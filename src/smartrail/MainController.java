package smartrail;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.LinkedList;

import static smartrail.Constants.*;

public class MainController extends Application {
    Display display = new Display();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileLoader fl = new FileLoader();
        Station root = fl.getRailSystem();

        //initGUI(primaryStage);
        printRails(root);
    }
    private void printRails(Rail root){
        System.out.println(root);
        if(root.right != null){
            printRails(root.right);
        }
    }
    /* GUI starts from here */
    private void initGUI(Stage primaryStage) {
        Pane pane = new Pane();
        pane.setPadding(new Insets(20));

        Label title = new Label("");

        Rectangle train1 = display.drawTrain();
        Rectangle station1l = display.drawStation();
        Rectangle station1r = display.drawStation();
        Rectangle track = display.drawTrack();

        title.relocate(100,10);
        station1l.relocate(50,50);
        station1r.relocate(600,50);
        train1.relocate(100,50);
        track.relocate(100,10);

        display.moveTrain(train1);

        pane.getChildren().add(title);
        pane.getChildren().add(train1);
        pane.getChildren().add(station1l);
        pane.getChildren().add(station1r);
        pane.getChildren().add(track);

        Scene scene = new Scene(pane,widthOfMainPane, heightOfMainPane);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}