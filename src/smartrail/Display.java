package smartrail;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import sun.awt.image.ImageWatched;
import sun.swing.plaf.synth.DefaultSynthStyle;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Display extends AnimationTimer {

    private FlowPane flowPane = new FlowPane();
    private Pane pane;
    private AnchorPane mainPane;
    private Rail rail;
    private LinkedList<Station> selectedStations;
    private Train train;
    private LinkedList<Switch> switches;
    private LinkedList<Rail> rails;
    private LinkedList<Station> stations;

    Display(Pane p, Rail root, Train t){
        train = t;
        pane = p;
        rail = root;
        stations = new LinkedList<>();
        switches = new LinkedList<>();
        rails = new LinkedList<>();

        recFinder(root,root);
        drawStation();
        drawSwitches();
        setRail();
        selectedStations = new LinkedList<>();
    }

    public void recFinder(Rail r,Rail last) {
        /*this is for the station*/
        if(r instanceof Station){
            stations.add((Station)r);
        }

        /*this is for the switch*/
        else if(r instanceof Switch){ switches.add((Switch)r);}

        /*this is for the rail*/
        else {
            rails.add(r);
        }

        if (r.right != null && r.right != last) recFinder(r.right, r);
        if (r.left != null && r.left != last) recFinder(r.left, r);
        if (r.rightSwitch != null && r.rightSwitch != last) recFinder(r.rightSwitch, r);
        if (r.leftSwitch != null && r.leftSwitch != last) recFinder(r.leftSwitch, r);
    }

    public void setRail() {
        for (Rail rail : rails) {
            System.out.println("rails " + rail);

            Image image = new Image("Image/track.png");

            Rectangle rectangle = new Rectangle(70, 40);
            rectangle.setFill(new ImagePattern(image));
            rectangle.setTranslateX((rail.getStartPoint().xcoor + 1) * 70);
            rectangle.setTranslateY((rail.getStartPoint().ycoor + 1) * 70);
            rectangle.setArcWidth(20);

            pane.getChildren().add(rectangle);
        }
    }


    public Rectangle drawTrain() {
        Rectangle train1 = new Rectangle(70, 30);
        String imagePath = ("Image/trainLeft.png");
        Image image = new Image(imagePath);
        train1.setFill(new ImagePattern(image));
        return train1;
    }

    /* later on we need to make config file so that we do not
     have to create every track and stations manually.*/
//    public void drawStation(List<Station> stations) {
//        for (Station station : stations) {
//            System.out.println("Station " + station);
//            pane.getChildren().addAll(setStation(station));
//        }
//    }
    public void drawStation() {
        for (Station station : stations) {
            System.out.println("Station " + station);
            pane.getChildren().addAll(setStation(station));
        }
    }

    public Rectangle setStation(Station station) {
        String imagePath = ("Image/station.png");
        Image image = new Image(imagePath);
        Rectangle rectangle = new Rectangle(60, 60);
        rectangle.setFill(new ImagePattern(image));

        // if its a right sided station then add 1 to its xcoor
        if (station.startPoint.xcoor > 0) {
            rectangle.setTranslateX((station.startPoint.xcoor + 1) * 70);
        } else {
            rectangle.setTranslateX(station.startPoint.xcoor * 70);
        }
        rectangle.setTranslateY((station.startPoint.ycoor + 1) * 70);

        rectangle.setOnMouseClicked(event -> {
            selectedStations.add(station);
            System.out.println("selected stations: " + selectedStations);
//            if (selectedStations.size() == 2) {
//                try{
//                    setSourceDestination(selectedStations);
//                }catch(Exception e){
//                    System.out.println("things happened, and I do not like it ");
//                }
//
//                selectedStations.clear();
//            }
            setSourceDestination(station);
            rectangle.setStroke(Color.DARKGREEN);
            rectangle.setStrokeWidth(5);
        });

        return rectangle;
    }

//    public void drawSwitches(LinkedList<Switch> switches) {
//        for(Switch sw : switches) {
//            System.out.println("Switchs " + switches);
//            pane.getChildren().addAll(setSwitch(sw));
//        }
//    }
    public void drawSwitches() {
        for(Switch sw : switches) {
            System.out.println("Switchs " + switches);
            pane.getChildren().addAll(setSwitch(sw));
        }
    }

    public Circle setSwitch(Switch sw) {
        Circle circle = new Circle(10);
        circle.setFill(Color.BLACK);
        circle.setTranslateX(sw.startPoint.xcoor * (70 + (rails.size())));
        circle.setTranslateY((sw.startPoint.ycoor + 1)  * 80);
        return circle;
    }

//    /**
//     * @param selectedStations is set as a train. This method moves rectangle from x to y
//     */
//    public synchronized void setSourceDestination(LinkedList<Station> selectedStations) throws InterruptedException {
//
//        Station source = selectedStations.getFirst();
//        Station destination = selectedStations.getLast();
//
//        train.setStartRail(source);
//        Thread.currentThread().join(1000);
//        source.selectedAsTarget();//Checks to see if the path is valid
//
//        int startX = (source.startPoint.xcoor + 1) * 70;
//        int startY = (source.startPoint.ycoor + 1) * 70;
//
//        int endX = (destination.startPoint.xcoor - 1) * 70;
//        int endY = (destination.startPoint.ycoor);
//
//        String imagePath = ("Image/trainLeft.png");
//        Image image = new Image(imagePath);
//        Rectangle train = new Rectangle(60, 30);
//        train.setFill(new ImagePattern(image));
//
//        train.setLayoutX(startX);
//        train.setLayoutY(startY);
//
//        TranslateTransition translateTransition = new TranslateTransition();
//        translateTransition.setDuration(Duration.seconds(10));
//
//        translateTransition.setToX(endX);
//        translateTransition.setToY(endY);
//        System.out.println("To: " + endX + " " + endY);
//
//        pane.getChildren().addAll(train);
//        translateTransition.setNode(train);
//        translateTransition.play();
//        start();
//    }
    public void setSourceDestination(Station s) {

        s.selectedAsTarget();//Checks to see if the path is valid

        int startX = (train.getCurrentLocation().startPoint.xcoor + 1) * 70;
        int startY = (train.getCurrentLocation().startPoint.ycoor + 1) * 70;

        int endX = (s.startPoint.xcoor) * 70;
        int endY = (s.startPoint.ycoor);

        String imagePath = ("Image/trainLeft.png");
        Image image = new Image(imagePath);
        Rectangle train = new Rectangle(60, 30);
        train.setFill(new ImagePattern(image));

        train.setLayoutX(startX);
        train.setLayoutY(startY);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(10));

        translateTransition.setToX(endX);
        translateTransition.setToY(endY);
        System.out.println("To: " + endX + " " + endY);

        pane.getChildren().addAll(train);
        translateTransition.setNode(train);
        translateTransition.play();
        start();
    }
    /**
     * @return track. This method draws track
     */
    public Rectangle drawTrack() {
        Rectangle track = new Rectangle();
        for (int i = 0; i < 5; i++) {
            track = new Rectangle(60, 50);
            String imagePath = ("Image/track.png");
            Image image = new Image(imagePath);
            track.setFill(new ImagePattern(image));
            flowPane.getChildren().add(track);
            track.relocate(30, 30);
        }
        return track;
    }

    @Override
    public void handle(long now) {
//        drawStation();

    }

}
