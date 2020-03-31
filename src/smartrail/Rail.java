package smartrail;

import java.util.concurrent.LinkedBlockingQueue;

public class Rail {

    protected int name;
    protected LinkedBlockingQueue<Message> inbox;
    protected Rail left;
    protected Rail right;
    protected Point startPoint;
    protected Point endPoint;

    public Rail(int n,Point p){
        name = n;
        left = null;
        right = null;
        inbox = new LinkedBlockingQueue<Message>();
        startPoint = p;
        endPoint = null;
    }
    public void setLeft(Rail l){
        left = l;
    }
    public void setRight(Rail r){
        right = r;
    }
    public void setEndPoint(Point p){
        endPoint = p;
    }
    public Point getStartPoint(){
        return startPoint;
    }
    public void recieiveMessage(Message m){
        inbox.add(m);
    }

    @Override
    public String toString() {
        if (endPoint != null) {
            return name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]->[" + endPoint.xcoor + "," + startPoint.ycoor + "]";
        } else {
            return name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]-> null";
        }
    }

    // define position of the track x and y and pass to the train

    // able to send the message


}
