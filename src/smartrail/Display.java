package smartrail;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Display {

    FlowPane flowPane = new FlowPane();

    public Rectangle drawTrain() {
        Rectangle train1 = new Rectangle(70, 30);
        String imagePath = ("Image/trainLeft.png");
        Image image = new Image(imagePath);
        train1.setFill(new ImagePattern(image));
        return train1;
    }

    /* later on we need to make config file so that we do not
     have to create every track and stations manually.*/
    public Rectangle drawStation() {
        Rectangle station1l = new Rectangle(60, 50);
        String imagePath = ("Image/station.png");
        Image image = new Image(imagePath);
        station1l.setFill(new ImagePattern(image));
        return station1l;
    }

    /**@param rectangle is set as a train. This method moves rectangle from x to y*/
    public void moveTrain(Rectangle rectangle) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(11));
        translateTransition.setToX(500);
        translateTransition.setToY(3);
        translateTransition.setNode(rectangle);
        translateTransition.play();
    }

    /**@return track. This method draws track */
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
}
