package smartrail;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.LinkedList;

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

        makeRailLists(root,root);
        drawStation();
        drawSwitches();
        setRail();
        selectedStations = new LinkedList<>();
    }
    /*
    Creates three objects that can be iterated through and displayed
     */
    private void makeRailLists(Rail r,Rail last) {
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

        if (r.right != null && r.right != last) makeRailLists(r.right, r);
        if (r.left != null && r.left != last) makeRailLists(r.left, r);
        if (r.rightSwitch != null &&
                r.rightSwitch != last) makeRailLists(r.rightSwitch, r);
        if (r.leftSwitch != null &&
                r.leftSwitch != last) makeRailLists(r.leftSwitch, r);
    }

    public void setRail() {
        for (Rail rail : rails) {
            System.out.println("rails " + rail);

            Image image = new Image("Image/track.png");

            Rectangle rectangle = new Rectangle(70, 50);
            rectangle.setFill(new ImagePattern(image));
            rectangle.setTranslateX((rail.getStartPoint().xcoor + 1) * 70);
            rectangle.setTranslateY((rail.getStartPoint().ycoor + 1) * 70);
            rectangle.setArcWidth(20);

            pane.getChildren().add(rectangle);
        }
    }

    private void drawStation() {
        for (Station station : stations) {
            System.out.println("Station " + station);
            pane.getChildren().addAll(setStation(station));
        }
    }

    private Rectangle setStation(Station station) {
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
            setSourceDestination(station);
            rectangle.setStroke(Color.DARKGREEN);
            rectangle.setStrokeWidth(5);
        });

        return rectangle;
    }

    private void drawSwitches() {
        for(Switch sw : switches) {
            Point switchStartPoint = sw.startPoint;
            Point switchEndPoint = sw.endPoint;
            System.out.println("This is switch's start point: " + switchStartPoint);
            System.out.println("This is switch's end point: " + switchEndPoint);

            drawSwitchTrack(switchStartPoint, switchEndPoint);
        }
    }

    public void drawSwitchTrack(Point switchStartPoint, Point switchEndPoint) {
        Line switchTrack = new Line();
//        Rectangle switchTrack = new Rectangle();
        switchTrack.setStartX((switchStartPoint.xcoor) * 70);
        switchTrack.setStartY((switchStartPoint.ycoor + 1) * 70);

        switchTrack.setEndX((switchEndPoint.xcoor + 1) * 70);
        switchTrack.setEndY((switchEndPoint.ycoor + 1) * 70);

        switchTrack.setStroke(Color.RED);
        switchTrack.setStrokeWidth(10);

        pane.getChildren().add(switchTrack);
    }

    public void setSourceDestination(Station station) {
        System.out.println("This is the clicked station: " + station);
        int finalDestinationX = station.startPoint.xcoor;
        int finalDestinationY = station.startPoint.ycoor;

        station.selectedAsTarget();//Checks to see if the path is valid

        int startXCoor = train.getCurrentLocation().startPoint.xcoor;
        int startYCoor = train.getCurrentLocation().startPoint.ycoor;

        int startXTranslation = (train.getCurrentLocation().startPoint.xcoor + 1) * 70;
        int startYTranslation = (train.getCurrentLocation().startPoint.ycoor + 1) * 70;

        int endX = (station.startPoint.xcoor) * 70;
        int endY = (station.startPoint.ycoor);

        String imagePath = ("Image/trainLeft.png");
        Image image = new Image(imagePath);
        Rectangle trainRectangle = new Rectangle(60, 30);
        trainRectangle.setFill(new ImagePattern(image));

        trainRectangle.setLayoutX(startXTranslation);
        trainRectangle.setLayoutY(startYTranslation);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(10));

        Path path = new Path();
        LinkedList<Rail> pathFinal = train.getPath();
        System.out.println("This is the whole path: " + pathFinal);

        path.getElements().add(new MoveTo(0, 0));
        if (endY > startYCoor) {
            for (Rail currRail : pathFinal) {
                if (currRail instanceof Switch) {
                    path.getElements().add(new LineTo((currRail.startPoint.xcoor ) * 70, currRail.startPoint.ycoor));
                    path.getElements().add(new LineTo(currRail.endPoint.xcoor * 70, currRail.endPoint.ycoor * 70));
                    path.getElements().add(new LineTo(finalDestinationX * 70, finalDestinationY * 70));
                    System.out.println("endX: " + finalDestinationX);
                    System.out.println("endY: " + finalDestinationY);
                }
            }
        } else {
            path.getElements().add(new LineTo(endX, endY));
        }

        pane.getChildren().addAll(trainRectangle);

        pathTransition.setNode(trainRectangle);
        pathTransition.setPath(path);
        pathTransition.play();
    }


    @Override
    public void handle(long now) {

    }

}
