package smartrail;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Display extends AnimationTimer {

    private FlowPane flowPane = new FlowPane();
    private FileLoader fileLoader;
    private Pane pane;

    Display(Pane pane, FileLoader fileLoader) {
        this.pane = pane;
        this.fileLoader = fileLoader;
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
    public void drawStation(int xCor, int yCor) {
        Rectangle station = new Rectangle(60, 50);
        String imagePath = ("Image/station.png");
        Image image = new Image(imagePath);
        station.setX(xCor);
        station.setY(yCor);
        station.setFill(new ImagePattern(image));
        pane.getChildren().add(station);
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

    @Override
    public void handle(long now) {

    }
}
