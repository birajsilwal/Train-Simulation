package smartrail;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Map;

import static smartrail.Constants.*;

public class MainController extends Application {
    private Display display;
    private Pane pane;
    private AnchorPane mainPane;
    private FileLoader fileLoader;
    private Train train = new Train();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Train train = new Train();
        FileLoader fl = new FileLoader(train);
        fl.readInTrack(train);
        //fl.readTrack - reading config here
        Station root = fl.getRailSystem();
        train.setStartRail(root);
        Rail thisStation = root;

        Thread trainThread = new Thread(train);
        trainThread.start();

        startThreads(root);

        while(thisStation.right != null) {
            thisStation = thisStation.right;
        }
        Station s = (Station)thisStation;
        s.selectedAsTarget();
        train.processMessage();
        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();

        initGUI(primaryStage);
        //printRails(root);
    }


    private void startThreads(Rail root){
        Thread newThread = new Thread(root);
        root.running = true;
        newThread.start();
        if(root.right != null && !root.right.running){ startThreads(root.right);}
        if(root.left != null && !root.left.running){ startThreads(root.left);}
//        if(!root.rightSwitch.running){ startThreads(root.rightSwitch);
//        if(!root.leftSwitch.running){ startThreads(root.leftSwitch);

    }

    private void printRails(Rail root){
        System.out.println(root);
        if(root.right != null){
            printRails(root.right);
        }
    }


    /* GUI starts from here */
    private void initGUI(Stage primaryStage) {
        pane = new Pane();
        mainPane = new AnchorPane();
//        fileLoader = new FileLoader(train);
        display = new Display(pane, fileLoader);

        mainPane.getChildren().add(pane);

        Scene scene = new Scene(mainPane, Constants.widthOfMainPane, Constants.heightOfMainPane);
        primaryStage.setTitle(" ");
        primaryStage.setScene(scene);
        display.start();
        primaryStage.show();

    }
}