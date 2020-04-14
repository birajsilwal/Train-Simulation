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

public class MainController extends Application {
    private Display display;
    private Pane pane;
    private BorderPane borderPane;
    private Group group;
    private AnchorPane mainPane;
    private FileLoader fileLoader;
    private Train train = new Train();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Train train = new Train();
        fileLoader = new FileLoader(train);
        fileLoader.readInTrack(train);
        //fileLoader.readTrack - reading config here


        Station root = fileLoader.getRailSystem();

        train.setStartRail(root);


        Thread trainThread = new Thread(train);
        trainThread.start();

        startThreads(root);
        initGUI(root, primaryStage,train);
//        while(thisStation.right != null) {
//            System.out.println(thisStation);
//            if(thisStation.rightSwitch != null){
//                thisStation = thisStation.rightSwitch;
//            }else{
//                thisStation = thisStation.right;
//            }
//        }
//        Station s = (Station)thisStation;
//        s.selectedAsTarget();

        //initGUI(root, primaryStage);
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