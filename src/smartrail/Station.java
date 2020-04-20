/**@author Biraj Silwal and Christopher James Shelton **/

package smartrail;

/*this class is responsible for managing stations*/
public class Station extends Rail {

    public Station(int n, Point point,Train t) {
        super(n,point,t);
    }

    /**@return start location, as a point object, of a station*/
    public Point getLocation(){
        return startPoint;
    }

    public void setLeft(Rail rail){
        left = rail;
    }

    public void setRight(Rail rail){
        right = rail;
    }

    private Message createMessage() {
        SeekMessage seekMessage = new SeekMessage();
        seekMessage.setStationSent(this);
        seekMessage.stationTarget= this;
        seekMessage.seekPath = true;
        seekMessage.validPath = false;
        return seekMessage;
    }

    protected synchronized void selectedAsTarget() {
        //When selected, create a message and send it to the train
        Message message = createMessage();
        train.receiveMessage(message);
    }

    @Override
    protected synchronized void receiveMessage(Message message) {
        inbox.add(message);
        notifyAll();
    }

    @Override
    public synchronized void processMessage() {
        while (inbox.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(inbox.peek() instanceof SeekMessage) {
            SeekMessage m = (SeekMessage) inbox.remove();

            notifyAll();
            //Who is it from
            if (m.seekPath) {
                //You have found the place you want to be
                if (m.stationTarget.startPoint.xcoor == startPoint.xcoor && m.stationTarget.startPoint.ycoor == startPoint.ycoor) {
                    System.out.println("You found me, sending a message back to the train" + this);
                    m.addToPath(this);
                    m.seekPath = false;
                    m.validPath = true;
                    train.receiveMessage(m);
                }
                //You are looking for a path from here
                else if (m.seekPath && !m.validPath && m.path.size() == 0) {
                    if (right != null) {
                        m.travelingRight = true;
                        m.stationSent = this;
                        m.addToPath(this);
                        right.receiveMessage(m);
                    } else if(left != null){ //if(right != null && !m.travelingRight) {
                        m.stationSent = this;
                        m.addToPath(this);
                        left.receiveMessage(m);
                    }
                }
                //You have not found the destination and you need to tell the train
                else {
                    System.out.println("You are at " + this + " and it is not a valid path");
                    m.validPath = false;
                    m.seekPath = false;
                    //m.clearPath();
                    train.receiveMessage(m);
                }
            }
        }
        else {
            TravelMessage m = (TravelMessage)inbox.remove();
            if(!hasTheTrain && m.newRail == this){
                System.out.println("Yay! You made it to the station");
                m.validDestination = true;
                m.arrivedAtDestination = true;
            } else {
                System.out.println("This is not the right station!");
                m.validDestination = false;
            }

            train.receiveMessage(m);

        }
    }

    @Override
    public String toString() {
        if (right != null) {
            return "Station: "+name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]->[" + right.startPoint.xcoor + "," + right.startPoint.ycoor + "]";
        } else {
            return "Station: "+name + " [" + startPoint.xcoor + "," + startPoint.ycoor + "]-> the abyss";
        }
    }
}
