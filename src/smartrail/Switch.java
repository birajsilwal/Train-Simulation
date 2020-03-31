package smartrail;

import java.util.concurrent.LinkedBlockingQueue;

public class Switch {
    private int name;
    private LinkedBlockingQueue<Message> inbox;
    private Rail left;
    private Rail right;
    private Point startPoint;

    public Switch(int n,Point p){
        name = n;
        left = null;
        right = null;
        inbox = new LinkedBlockingQueue<Message>();
        startPoint = p;
    }
    public void setLeft(Rail l){
        left = l;
    }
    public void setRight(Rail r){
        right = r;
    }
    @Override
    public String toString(){
        return name + " " + startPoint.xcoor + " " + startPoint.ycoor;
    }
}
