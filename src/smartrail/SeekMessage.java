package smartrail;
import java.util.LinkedList;

public class SeekMessage extends Message {
    Rail stationSent;
    Rail stationTarget;
    boolean validPath;
    boolean seekPath;
    //LinkedList<Rail> path;//The path that the train follows

    public SeekMessage(){
        super();
        //path = new LinkedList<>();
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

}


// station, train, rail, switch all have to implement runnable
