package smartrail;
import java.util.LinkedList;

public class Message {
    Rail stationSent;
    Rail stationTarget;
    boolean validPath;
    boolean seekPath;
    boolean travelingRight;
    LinkedList<Rail> path;//The path that the train follows

    public Message(){
        path = new LinkedList<>();
    }
    public void addToPath(Rail rail){
        path.add(rail);
    }
    public void clearPath(){
        path = null;
    }
    public void setStationSent(Rail station) {
        stationSent = station;
    }
    public void setStationTarget(Rail station){
        stationTarget = station;
    }

    public boolean isSeekPath() { return true; }

    public boolean isValidPath() { return true; }
}


// station, train, rail, switch all have to implement runnable
