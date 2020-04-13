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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.List;

public class Display extends AnimationTimer {

    private FlowPane flowPane = new FlowPane();
    private Pane pane;
    private AnchorPane mainPane;
    private Rail rail;
    private LinkedList<Station> selectedStations;

    Display(Pane pane, Rail r, List<Station> stations, List<Switch> switches) {
        this.pane = pane;
        rail = r;
        drawStation(stations);
        drawSwitches(switches);
        selectedStations = new LinkedList<>();

    }

//    public void recFinder(Rail railManager) {
//
//        //If already visited, don't go there
//        if (railManager instanceof Station) {
//            drawStation(railManager.startPoint);
//        }
////        }else if(railManager.getClass().isInstance(tempSwitch)){
////            drawSwitch(railManager);
////        }
//        if (railManager.right != null) recFinder(railManager.right);
//        if (railManager.left != null) recFinder(railManager.left);
//        if (railManager.rightSwitch != null) recFinder(railManager.rightSwitch);
//        if (railManager.leftSwitch != null) recFinder(railManager.leftSwitch);
//    }

    public Rectangle drawTrain() {
        Rectangle train1 = new Rectangle(70, 30);
        String imagePath = ("Image/trainLeft.png");
        Image image = new Image(imagePath);
        train1.setFill(new ImagePattern(image));
        return train1;
    }

    /* later on we need to make config file so that we do not
     have to create every track and stations manually.*/
    public void drawStation(List<Station> stations) {
        for (Station station : stations) {
            pane.getChildren().addAll(setStation(station));
        }
    }


    public Rectangle setStation(Station station) {
        String imagePath = ("Image/station.png");
        Image image = new Image(imagePath);
        Rectangle rectangle = new Rectangle(60, 60);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setTranslateX(station.startPoint.xcoor * 70);
        rectangle.setTranslateY((station.startPoint.ycoor + 1) * 70);

        rectangle.setOnMouseClicked(event -> {
            selectedStations.add(station);
            System.out.println("selected stations: " + selectedStations);
            if (selectedStations.size() == 2) {
                setSourceDestination(selectedStations);
                selectedStations.clear();
            }
            rectangle.setStroke(Color.DARKGREEN);
            rectangle.setStrokeWidth(5);
        });

        return rectangle;
    }

    public void drawSwitches(List<Switch> switches) {
        for(Switch sw : switches) {
            pane.getChildren().addAll(setSwitch(sw));
        }
    }

    public Circle setSwitch(Switch sw) {
        Circle circle = new Circle(10);
        circle.setFill(Color.BLACK);
        circle.setTranslateX(sw.startPoint.xcoor * 70);
        circle.setTranslateY((sw.startPoint.ycoor + 1)  * 80);
        return circle;
    }

    /**
     * @param selectedStations is set as a train. This method moves rectangle from x to y
     */
    public synchronized void setSourceDestination(LinkedList<Station> selectedStations) {

        Station source = selectedStations.getFirst();
        Station destination = selectedStations.getLast();

        int startX = (source.startPoint.xcoor + 1) * 70;
        int startY = (source.startPoint.ycoor + 1) * 70;

        int endX = (destination.startPoint.xcoor - 1) * 70;
        int endY = (destination.startPoint.ycoor);

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
