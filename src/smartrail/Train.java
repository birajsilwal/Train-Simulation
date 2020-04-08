package smartrail;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**@author Biraj Silwal and Christopher James Shelton **/

public class Train implements Runnable{

        private int trainId;
        private Station source;
        private Station destination;
        private int speed;
        private Boolean travelRight;
        private Rail rail;
        protected LinkedBlockingQueue<Message> inbox;
        protected LinkedList<Rail> path;

        public Train(){
                inbox = new LinkedBlockingQueue<>();
                travelRight = false;
        }
        public Train(Station source, Station destination, Boolean canChangeDirection) {
                source = source;
                destination = destination;
                canChangeDirection = canChangeDirection;
                inbox = new LinkedBlockingQueue<>();
                path = new LinkedList<>();
        }
        public void setStartRail(Rail r){
                rail = r;
        }
        public synchronized void moveTrain(LinkedList<Rail> p) {
                // TODO: if there is a valid path, then move the train
                path = p;
                // moving train to other track
                if(travelRight) {
                        TravelMessage m = new TravelMessage(rail, rail.right);
                        rail.right.receiveMessage(m);
                }else{
                        TravelMessage m = new TravelMessage(rail, rail.left);
                        rail.left.receiveMessage(m);
                }
                // otherwise return false
                // need positional data i.e. x y

                // animation timer () create separate class display class

                // have to talk to station
                // abstract messaging class
                // message type

        }
        private synchronized void updateLocation(Rail newRail, Rail oldRail){
                //Move to the new rail
                rail = newRail;
                //Let the rail know that I am on him
                newRail.hasTheTrain = true;
                oldRail.hasTheTrain = false;
                //Am I on the intended rail?
                //yes, then stop the traveling

                //No, make a new message and send it to the next rail
        }
        public synchronized void receiveMessage(Message m){
                System.out.println("Train: New Message");
                inbox.add(m);
                notifyAll();
        }
        public synchronized void processMessage() {
                while (inbox.isEmpty()) {
                        try {
                                //sleep()  ??
                                wait();
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
                System.out.println("Train Processing a message");

                try{//Is it a seek message?
                        SeekMessage m = (SeekMessage)inbox.remove();
                        notifyAll();
                        if (m.seekPath && !m.validPath) {//Just received a new destination station
                                m.stationSent = null;
                                System.out.println("Train: We have received a new target");
                                rail.receiveMessage(m);
                        } else if (m.validPath && !m.seekPath) {//we have found a valid path to travel from A->B
                                //Time to move
                                moveTrain(m.path);
                                if(m.travelingRight){
                                        travelRight = true;
                                }
                                //Generate a moving message to a rail, there is a response, the rain moves
                                System.out.println("Train: We have found a valid path and its time to move");
                        }
                }catch(Exception e){//Then it must be a travel Message
                        //System.out.println(inbox.peek().getClass());
                        TravelMessage m = (TravelMessage)inbox.remove();
                        if(m.validDestination){
                                updateLocation(m.newRail, m.oldRail);
                        }
                        else{
                                System.out.println("Its hopeless, and we are going to die out here");
                        }
                }

        }


        private void printPath(Message m){
                for(Rail r: m . path){
                        System.out.println(r);
                }
        }
        // setter src and dest
        /**@return source gives us the location of source*/
        public Station getSource() { return source; }

        /**@return destination gives us the location of destination*/
        public Station getDestination() { return destination; }

        /**@return trainId gives us the unique id of the train*/
        public int getTrainId() { return this.trainId; }

        /**@return track returns the track*/
        public Rail getRail() { return this.rail; }


        @Override
        public void run() {
                System.out.println("Train has started");
//                while (! Thread . interrupted ()) {
//                        processMessage();
//                }
                processMessage();
        }
}
