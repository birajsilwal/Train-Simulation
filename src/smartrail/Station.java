package smartrail;

import java.util.concurrent.LinkedBlockingQueue;

public class Station extends Rail{

//    private int name;
//    private LinkedBlockingQueue<Message> inbox;
//    private Rail left;
//    private Rail right;
    private Boolean isSource;
    private Boolean isDestination;

    public Station(int n, Point p) {
        super(n,p);
//        name = n;
//        left = null;
//        right = null;
//        inbox = new LinkedBlockingQueue<Message>();
        isSource = false;
        isDestination = false;
    }

    public Point getLocation(){
        return startPoint;
    }
    public void setLeft(Rail rail){
        left = rail;
    }
    public void setRight(Rail rail){
        right = rail;
    }
    @Override
    public String toString(){
        if (right != null) {
            return "Station: "+name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]->[" + right.startPoint.xcoor + "," + right.startPoint.ycoor + "]";
        } else {
            return "Station: "+name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]-> the abyss";
        }
    }
}
