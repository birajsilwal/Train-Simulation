package smartrail;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class Display extends AnimationTimer {

    private FlowPane flowPane = new FlowPane();
    private Pane pane;
    private AnchorPane mainPane;
    private Rail rail;

    Display(Pane pane, Rail r, List<Station> stations) {
        this.pane = pane;
        rail = r;
        drawStation(stations);

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
            pane.getChildren().add(setStation(station));
        }
    }


    public Rectangle setStation(Station station) {
        String imagePath = ("Image/station.png");
        Image image = new Image(imagePath);
        Rectangle rectangle = new Rectangle(60, 60);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setTranslateX(station.startPoint.xcoor * 100);
        rectangle.setTranslateY(station.startPoint.ycoor * 50);
        return rectangle;
    }

    /**
     * @param rectangle is set as a train. This method moves rectangle from x to y
     */
    public void moveTrain(Rectangle rectangle) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(11));
        translateTransition.setToX(500);
        translateTransition.setToY(3);
        translateTransition.setNode(rectangle);
        translateTransition.play();
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
