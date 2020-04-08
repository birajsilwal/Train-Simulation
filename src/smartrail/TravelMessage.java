package smartrail;

public class TravelMessage extends Message {

    Rail newRail;
    Rail oldRail;
    boolean validDestination;

    public TravelMessage(Rail nRail, Rail oRail){
        super();
        newRail = nRail;
        oldRail = oRail;
        validDestination = false;
    }

}
