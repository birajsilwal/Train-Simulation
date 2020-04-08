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
    boolean hasTheTrain;

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
        hasTheTrain = false;
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
        inbox.add(m);
        notifyAll();
    }
    public synchronized void processMessage() {
        while (inbox.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(this + " processing message");
        notifyAll();
        //Is it a seek message?
        SeekMessage tempM = new SeekMessage();
        if(inbox.peek().getClass().isInstance(tempM)) {
//            System.out.println(this + " has a seek message");

            SeekMessage m = (SeekMessage) inbox.remove();
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
        }else{
//            System.out.println(this + "Just got a travel message");
            TravelMessage m = (TravelMessage)inbox.remove();
            if(!hasTheTrain && m.newRail == this){
               // System.out.println("Yay! You found the right rial, move on me");
                m.validDestination = true;
            }else{
                System.out.println("I am not the rail you are looking for, I will let the train know");
            }
            train.receiveMessage(m);
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
//        System.out.println(this + " is running");
        while (Thread.currentThread().isAlive()) {
                        processMessage();
                }
    }

    // define position of the track x and y and pass to the train

    // able to send the message


}
