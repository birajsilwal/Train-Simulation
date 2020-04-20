/**@author Biraj Silwal and Christopher James Shelton **/

package smartrail;

/*this class is extended from the Message class*/
public class TravelMessage extends Message {

    Rail newRail;
    Rail oldRail;
    boolean validDestination;
    boolean arrivedAtDestination;

    /*this method sends message from old rail to the new rail*/
    public TravelMessage(Rail nRail, Rail oRail){
        super();
        newRail = nRail;
        oldRail = oRail;
        validDestination = false;
        arrivedAtDestination = false;
    }

}
