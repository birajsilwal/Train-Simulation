package smartrail;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**@author Biraj Silwal and Christopher James Shelton **/

public class Train implements Runnable{

        private int trainId;
        private Station source;
        private Station destination;
        private Boolean canChangeDirection;
        private int speed;
        private Boolean travelRight;
        protected boolean isTraveling;
        protected Rail rail;
        protected LinkedBlockingQueue<Message> inbox;
        protected LinkedList<Rail> path;
        protected boolean running;
        protected int possiblePaths;

        public Train() {
                inbox = new LinkedBlockingQueue<>();
                travelRight = false;
                isTraveling = false;
                running = false;
                possiblePaths = 0;
                rail = null;
        }

        public Train(Station s, Station d, Boolean ccd) {
                s = source;
                d = destination;
                ccd = canChangeDirection;
                inbox = new LinkedBlockingQueue<>();
                path = new LinkedList<>();
        }

        public synchronized void setStartRail(Rail r) {
                //System.out.println("Setting start rail to: "+ r);
                rail = r;
        }

        public synchronized void moveTrain(LinkedList<Rail> p) throws InterruptedException {
                // TODO: if there is a valid path, then move the train
                //System.out.println("We are in moveTrain, move right="+ travelRight);
                path = p;
                wait(1000);
                // moving train to other track
                if(travelRight) {
                        //System.out.println("We want to move right");
                        Rail destRail = path.removeLast();
                        TravelMessage m = new TravelMessage(destRail, rail);
                        destRail.receiveMessage(m);
                }else{
//                        System.out.println("We want to move left");
                        Rail destRail = path.removeLast();
                        TravelMessage m = new TravelMessage(path.removeLast(), rail);
                        destRail.receiveMessage(m);
                }
                // otherwise return false
                // need positional data i.e. x y

                // animation timer () create separate class display class

                // have to talk to station
                // abstract messaging class
                // message type
                //processMessage();//Put me back on wait

        }

        private synchronized void updateLocation(Rail newRail, Rail oldRail,boolean arrived) throws InterruptedException {
                //Move to the new rail
                rail = newRail;
                System.out.println("Train Location: "+ rail);
                //Let the rail know that I am on him
                rail.hasTheTrain = true;
                oldRail.hasTheTrain = false;
                path.remove(oldRail);
                //Am I on the intended rail?
                if(arrived){//yes, then stop the traveling and wait
                        System.out.println("Final Location: " + rail);
                        isTraveling = false;
                        //processMessage();
                }else{//Keep moving
                        moveTrain(path);
                }
        }

        public synchronized void receiveMessage(Message m){
//                System.out.println("Train: New Message");
                inbox.add(m);
                notifyAll();
                //processMessage();
        }

        public synchronized void processMessage() throws InterruptedException {
                while (inbox.isEmpty()) {
                        try {
                                //sleep()  ??
                                wait();
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
                //notifyAll();
//                System.out.println("Train Processing a message");

                //Is it a seek message?
                if(inbox.peek() instanceof SeekMessage) {

                        if(isTraveling){inbox.remove();}//Throw the messages away if I am traveling

                        SeekMessage m = (SeekMessage) inbox.remove();
                        notifyAll();
                        if (m.seekPath && !m.validPath) {//Just received a new destination station
                                m.stationSent = null;
                                System.out.println("Train: We have received a new target");
                                possiblePaths++;
                                //System.out.println("Trains current rail " +rail);
                                rail.receiveMessage(m);
                        } else if (m.validPath && !m.seekPath) {//we have found a valid path to travel from A->B
                                //Time to move
                                if (m.travelingRight) {
                                        travelRight = true;
                                }
                                //Generate a moving message to a rail, there is a response, the rain moves
                                System.out.println("Train: We have found a valid path and its time to move");
                                moveTrain(m.path);
                        }else if(!m.validPath && !m.seekPath){//We have a dead end path
                                possiblePaths--;
                                if(possiblePaths == 0){
                                        System.out.println("There are no valid paths to the Destination");
                                }
                        }
                }
                else if(inbox.peek() instanceof SplitMessage){
                        inbox.remove();
                        possiblePaths++;
                }
                else {
                        System.out.println("Train got a TravelMessage");
                        TravelMessage m = (TravelMessage) inbox.remove();

                        if (m.arrivedAtDestination) {
                                updateLocation(m.newRail, m.oldRail,true);
                        } else if (m.validDestination) {
                                updateLocation(m.newRail, m.oldRail,false);
                        } else {
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
        public Rail getCurrentLocation() { return rail; }

        /**@return destination gives us the location of destination*/
        public Station getDestination() { return destination; }

        /**@return trainId gives us the unique id of the train*/
        public int getTrainId() { return this.trainId; }

        /**@return track returns the track*/
        public Rail getRail() { return this.rail; }


        @Override
        public void run() {
                System.out.println("Train has started");
                while (Thread.currentThread().isAlive()) {
                        try {
                                processMessage();
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
                //processMessage();
        }

        public LinkedList<Rail> getPath() {
            return path;
        }

}
