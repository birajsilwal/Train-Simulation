package smartrail;

public class Station {

    private Boolean isSource;
    private Boolean isDestination;
    private double xcorStation;
    private double ycorStation;

    public Station() {
        this.isSource = false;
        this.isDestination = false;
    }

    public double getXcorStation() { return xcorStation; }

    public double getYcorStation() { return ycorStation; }

}
