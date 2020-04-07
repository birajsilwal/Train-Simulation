package smartrail;

import java.util.concurrent.LinkedBlockingQueue;

public class Rail implements Runnable{

    protected int name;
    protected LinkedBlockingQueue<Message> inbox;
    protected Rail left;
    protected Rail right;
    protected Rail leftSwitch;
    protected Rail rightSwitch;
    protected Point startPoint;
    protected Point endPoint;
    protected Train train;
    protected boolean running;

    public Rail(int n,Point p,Train t){
        name = n;
        left = null;
        right = null;
        leftSwitch = null;
        rightSwitch = null;
        inbox = new LinkedBlockingQueue<Message>();
        startPoint = p;
        endPoint = null;
        train = t;
        running = false;
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
    public synchronized void receiveMessage(Message m){
        System.out.println(this + " received message");
        inbox.add(m);
        notifyAll();
        //this.processMessage();
    }
    public synchronized void processMessage() {
        while (inbox.isEmpty()) {
            try {
                //sleep()  ??
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this + " processing message");
        notifyAll();
        Message m = inbox.remove();
        //Who is it from
        if (m.stationSent == null) {//it was from the train
            if (left == null) {
                m.travelingRight = true;
            }
        }
        if (m.seekPath) {
            //You have not found the place you want to be and the message needs to keep going
            if (m.travelingRight) {
                m.addToPath(this);
                m.stationSent = this;
                right.receiveMessage(m);
                if (rightSwitch != null) {
                    rightSwitch.receiveMessage(m);
                }
            } else {
                m.addToPath(this);
                m.stationSent = this;
                left.receiveMessage(m);
                if (leftSwitch != null) {
                    leftSwitch.receiveMessage(m);
                }
            }
        }
    }

    @Override
    public String toString() {
        if (endPoint != null) {
            return name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]->[" + endPoint.xcoor + "," + startPoint.ycoor + "]";
        } else {
            return name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]-> null";
        }
    }

    @Override
    public void run() {
        System.out.println(this + " is running");
//        while (! Thread . interrupted ()) {
//            processMessage();
//        }
        processMessage();
    }

    // define position of the track x and y and pass to the train

    // able to send the message


}
