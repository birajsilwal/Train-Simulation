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
import java.util.Set;

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
        FileLoader fileLoader = new FileLoader(train);
        fileLoader.readInTrack(train);
        //fileLoader.readTrack - reading config here


        Station station = fileLoader.getRailSystem();


        train.setStartRail(station);
        Rail thisStation = station;
        initGUI(station, primaryStage);

        Thread trainThread = new Thread(train);
        trainThread.start();

        startThreads(station);

        while(thisStation.right != null) {
            if(thisStation.rightSwitch != null){
                thisStation = thisStation.rightSwitch;
            }else{
                thisStation = thisStation.right;
            }
        }
        System.out.println(thisStation);
        Station s = (Station)thisStation;
        s.selectedAsTarget();
//        train.processMessage();

        //printThreads();
        //printRails(station);
    }


    private void startThreads(Rail root){
        Thread newThread = new Thread(root);
        root.running = true;
        newThread.start();
        if(root.right != null && !root.right.running){ startThreads(root.right);}
        if(root.rightSwitch != null && !root.rightSwitch.running){ startThreads(root.rightSwitch);}
        if(root.left != null && !root.left.running){ startThreads(root.left);}
        if(root.leftSwitch != null && !root.leftSwitch.running){ startThreads(root.leftSwitch);}
//        if(!root.rightSwitch.running){ startThreads(root.rightSwitch);
//        if(!root.leftSwitch.running){ startThreads(root.leftSwitch);

    }
    private void printThreads() {
        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
        System.out.println("Threads running " + threads.size());
        Set keySet = threads.keySet();
        for(Object th :keySet){
            System.out.println(th);
        }
    }
    private void printRails(Rail root){
        System.out.println(root);
        if(root.right != null){
            printRails(root.right);
        }
    }


    /* GUI starts from here */
    private void initGUI(Rail root, Stage primaryStage) {
        pane = new Pane();
        mainPane = new AnchorPane();
        fileLoader = new FileLoader(train);
        display = new Display(root, primaryStage);
        //Thread guiThread = new Thread(display);
        //guiThread.start();
        mainPane.getChildren().add(pane);

        Scene scene = new Scene(mainPane, Constants.widthOfMainPane, Constants.heightOfMainPane);
        primaryStage.setTitle(" ");
        primaryStage.setScene(scene);
        display.start();
        primaryStage.show();

    }
}