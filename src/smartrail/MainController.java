package smartrail;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static smartrail.Constants.*;

/**
 * Driver for the program.
 * Initializes the objects and creates a train system based on the input from the user.
 * When running the program use java -jar SmartRail.jar _nameOfTrack.txt_
 *
 * The train is going to start on the root station of the track wherever it may lay,
 * most likely in the top left coordinate.
 *
 * Use mouse input to choose a track for the train to travel to.
 *
 * The train can only move from left/right to right/left and it will not make
 * any hard turns on a switch if it were to cross one.
 *
 * When you are done playing, close the window and close the program.
 */
public class MainController extends Application {
    private Display display; //The GUI object
    private Pane pane;
    private BorderPane borderPane;
    private Group group;
    private AnchorPane mainPane;
    private FileLoader fileLoader; //Reads in the .txt file provided
    private Train train = new Train(); //The star of the show, there's only one

    /**
     * The driver method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param primaryStage required for the javafx GUI
     * @throws Exception just in case
     *
     * Makes the train and passes it to the FileLoader to make the track
     * The track is stored in root
     * The train is given a starting location and then the threads are started
     *
     * The objects are then passed to the initGUI() for reference purposes
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Train train = new Train();
        fileLoader = new FileLoader(train);
        fileLoader.readInTrack(train);

        Station root = fileLoader.getRailSystem();

        train.setStartRail(root);

        Thread trainThread = new Thread(train);
        trainThread.start();

        startThreads(root);
        initGUI(root, primaryStage,train);
    }

    /*
    Recurses through the railSystem and starts any connected piece of the track
     */
    private void startThreads(Rail root){
        Thread newThread = new Thread(root);
        root.running = true;
        newThread.start();
        if(root.right != null && !root.right.running){ startThreads(root.right);}
        if(root.rightSwitch != null && !root.rightSwitch.running){ startThreads(root.rightSwitch);}
        if(root.left != null && !root.left.running){ startThreads(root.left);}
        if(root.leftSwitch != null && !root.leftSwitch.running){ startThreads(root.leftSwitch);}

    }
    /*
    A method to show any of the threads that are running
     */
    private void printThreads() {
        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
        System.out.println("Threads running " + threads.size());
        Set keySet = threads.keySet();
        for(Object th :keySet){
            System.out.println(th);
        }
    }

    /* GUI starts from here */
    private synchronized void initGUI(Rail root, Stage primaryStage, Train t) {
        pane = new Pane();
        borderPane = new BorderPane();
        mainPane = new AnchorPane();
        group = new Group();

        borderPane.setCenter(pane);
//        borderPane.setPadding(new Insets(50, 50, 0,50));
        Scene scene = new Scene(borderPane, widthOfMainPane, heightOfMainPane);
        primaryStage.setTitle("SmartRail ");
        primaryStage.setScene(scene);
//        display = new Display(pane, root, fileLoader.getStation(), fileLoader.getSwitches(),train);
        display = new Display(pane,root,t);
        display.start();
        primaryStage.show();

    }
}