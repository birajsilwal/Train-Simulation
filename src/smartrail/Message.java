package smartrail;



public class Message {
    Point stationSent;
    Point stationTarget;
    String purpose; //"pathSeek"
    boolean validPath;
    boolean seekPath;
    boolean travelingRight;
    LinkedList<Rail> path;//The path that the train follows

    public Message(){

    }

    public String setPurpose() { return purpose; }

    public Point setStationSent(double xCor, double yCor) {

    }

    public boolean isSeekPath() { return true; }

    public boolean isValidPath() { return true; }
}


// station, train, rail, switch all have to implement runnable
