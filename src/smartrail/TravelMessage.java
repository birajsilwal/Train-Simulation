package smartrail;

public class TravelMessage extends Message {

    Rail newRail;
    Rail oldRail;
    boolean validDestination;
    boolean arrivedAtDestination;

    public TravelMessage(Rail nRail, Rail oRail){
        super();
        newRail = nRail;
        oldRail = oRail;
        validDestination = false;
        arrivedAtDestination = false;
    }

}
