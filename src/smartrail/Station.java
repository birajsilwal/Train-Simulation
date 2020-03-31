package smartrail;

public class Station {

    private Boolean isSource;
    private Boolean isDestination;
    //Maybe change this to a Point object to make it more compact
    private double xcorStation;
    private double ycorStation;

    public Station() {
        this.isSource = false;
        this.isDestination = false;
    }

    public double getXcorStation() { return xcorStation; }

    public double getYcorStation() { return ycorStation; }

}
