package smartrail;

import java.util.concurrent.LinkedBlockingQueue;

/**@author Biraj Silwal and Christopher James Shelton **/

public class Train implements Runnable{

        private int trainId;
        private Station source;
        private Station destination;
        private int speed;
        private Boolean canChangeDirection;
        private Rail rail;
        protected LinkedBlockingQueue<Message> inbox;

        public Train(){
                inbox = new LinkedBlockingQueue<>();
        }
        public Train(Station source, Station destination, Boolean canChangeDirection) {
                source = source;
                destination = destination;
                canChangeDirection = canChangeDirection;
                inbox = new LinkedBlockingQueue<>();
        }
        public void setStartRail(Rail r){
                rail = r;
        }
        public void moveTrain() {
                // TODO: if there is a valid path, then move the train
                // moving train to other track
                // otherwise return false
                // need positional data i.e. x y

                // animation timer () create separate class display class

                // have to talk to station
                // abstract messaging class
                // message type

        }

        public synchronized void receiveMessage(Message m){
                System.out.println("Train: New Message");
                inbox.add(m);
                notifyAll();
                //this.processMessage();
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
                Message m = inbox.remove();
                notifyAll();
                if (m.seekPath && !m.validPath) {//Just received a new destination station
                        m.stationSent = null;
                        System.out.println("Train: We have received a new target");
                        rail.receiveMessage(m);
                } else if (m.validPath && !m.seekPath) {//we have found a valid path to travel from A->B
                        //Time to move
                        //Generate a moving message to a rail, there is a response, the rain moves
                        System.out.println("Train: We have found a valid path and its time to move");
                        //printPath(m);
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
