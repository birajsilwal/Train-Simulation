package smartrail;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display extends AnimationTimer{

    private FlowPane flowPane = new FlowPane();
    private Pane pane;
    private AnchorPane mainPane;
    private Rail root;
    private Stage primaryStage;

    Display(Rail r, Stage pStage) {
        primaryStage = pStage;
        //this.pane = pane;
        root = r;
        run();
    }

    private void render() {
        boolean running = true;
        while (running) {
            try {
                wait();//informAll to wake me up
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Call the different render methods here to draw them
            recFinder(root);
        }
    }

    public void recFinder(Rail root) {
        //If already visited, don't go there
        Station tempStation = new Station(0, null, null);
        Switch tempSwitch = new Switch(0, null, null);
        if (root.getClass().isInstance(tempStation)) {
            drawStation(root.startPoint.xcoor, root.startPoint.ycoor);
        }
//        }else if(root.getClass().isInstance(tempSwitch)){
//            drawSwitch(root);
//        }
        if (root.right != null) recFinder(root.right);
        if (root.left != null) recFinder(root.left);
        if (root.rightSwitch != null) recFinder(root.rightSwitch);
        if (root.leftSwitch != null) recFinder(root.leftSwitch);
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
        //Test for right or left will be
        station.setX(xCor);
        station.setY(yCor);
        station.setFill(new ImagePattern(image));
        pane.getChildren().add(station);
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

    }

    public void run() {
//        pane = new Pane();
//        mainPane = new AnchorPane();
//        mainPane.getChildren().add(pane);
//
//        Scene scene = new Scene(mainPane, Constants.widthOfMainPane, Constants.heightOfMainPane);
//        primaryStage.setTitle(" ");
//        primaryStage.setScene(scene);
//        this.start();
//        primaryStage.show();
        boolean run = true;
        while (Thread.currentThread().isAlive()) {
//            render();
        }
    }
}
