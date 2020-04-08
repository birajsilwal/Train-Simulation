package smartrail;

import java.util.concurrent.LinkedBlockingQueue;

public class Station extends Rail{

//    private int name;
//    private LinkedBlockingQueue<Message> inbox;
//    private Rail left;
//    private Rail right;
//    private Train train;
    private Boolean isSource;
    private Boolean isDestination;

    public Station(int n, Point p,Train t) {
        super(n,p,t);
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

    private Message createMessage(){
        SeekMessage m = new SeekMessage();
        m.setStationSent(this);
        m.stationTarget= this;
        m.seekPath = true;
        m.validPath = false;
        return m;
    }
    public synchronized void selectedAsTarget(){
        //When selected, create a message and send it to the train
        Message m = createMessage();
        train.receiveMessage(m);
        System.out.println("Exit selectAsTarget");
    }

    @Override
    public synchronized void receiveMessage(Message m){
//        System.out.println(this + " received message");
        inbox.add(m);
        notifyAll();
//        processMessage();
    }
    @Override
    public synchronized void processMessage() {
        while (inbox.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SeekMessage tempM = new SeekMessage();
        if(inbox.peek().getClass().isInstance(tempM)) {
//            System.out.println(this + " has a seek message");
            SeekMessage m = (SeekMessage) inbox.remove();

            notifyAll();
            //Who is it from
            if (m.seekPath) {
                //You have found the place you want to be
                if (m.stationTarget.startPoint.xcoor == startPoint.xcoor && m.stationTarget.startPoint.ycoor == startPoint.ycoor) {
                    //System.out.println("You found me, sending a message back to the train");
                    m.addToPath(this);
                    m.seekPath = false;
                    m.validPath = true;
                    train.receiveMessage(m);
                }
                //You are looking for a path from here
                else if (m.seekPath && !m.validPath) {
                    if (right != null) {
                        m.travelingRight = true;
                        m.stationSent = this;
                        m.addToPath(this);
                        right.receiveMessage(m);
                    } else {
                        m.stationSent = this;
                        m.addToPath(this);
                        left.receiveMessage(m);
                    }
                }
                //You have not found the destination and you need to tell the train
                else {
                    System.out.println("You are at " + this + " and it is not a valid path");
                    m.validPath = false;
                    m.seekPath = false;
                    m.clearPath();
                    train.receiveMessage(m);
                }
            }
        }
        else{
            TravelMessage m = (TravelMessage)inbox.remove();
            if(!hasTheTrain && m.newRail == this){
                System.out.println("Yay! You made it to the station");
                m.validDestination = true;
                m.arrivedAtDestination = true;
            }else{
                System.out.println("This is not the right station!");
                m.validDestination = false;
            }
            train.receiveMessage(m);

        }
    }
//    @Override
//    public void run() {
////        System.out.println(this + " is running");
//        while (Thread.currentThread().isAlive()) {
//            processMessage();
//        }
//    }
    @Override
    public String toString(){
        if (right != null) {
            return "Station: "+name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]->[" + right.startPoint.xcoor + "," + right.startPoint.ycoor + "]";
        } else {
            return "Station: "+name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]-> the abyss";
        }
    }
}
