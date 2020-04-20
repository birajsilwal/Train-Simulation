/**@author Biraj Silwal and Christopher James Shelton **/

package smartrail;

public class SeekMessage extends Message {
    Rail stationSent;
    Rail stationTarget;
    boolean validPath;
    boolean seekPath;

    public SeekMessage(){
        super();
        //path = new LinkedList<>();
    }

    public SeekMessage(SeekMessage m){
        super();
        for(Rail r: m.path){
            path.add(r);
        }
        travelingRight = m.travelingRight;
        validPath = m.validPath;
        seekPath = m.seekPath;
        stationSent = m.stationSent;
        stationTarget = m.stationTarget;
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
