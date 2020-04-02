package smartrail;



public class Message {
    private Point stationSent;
    private Point stationTarget;
    private String purpose;
    private boolean validPath;
    private boolean seekPath;

    public Message(){

    }

    public String setPurpose() { return purpose; }

    public Point setStationSent(double xCor, double yCor) {

    }

    public boolean isSeekPath() { return true; }

    public boolean isValidPath() { return true; }
}


// station, train, rail, switch all have to implement runnable
