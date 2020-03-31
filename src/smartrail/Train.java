/**@author Biraj Silwal and Christopher James Shelton **/
package smartrail;

public class Train {

        private int trainId;
        private Station source;
        private Station destination;
        private int speed;
        private Boolean canChangeDirection;
        private Rail rail;

        public Train(Station source, Station destination, Boolean canChangeDirection) {
                this.source = source;
                this.destination = destination;
                this.canChangeDirection = canChangeDirection;
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

        // setter src and dest

        /**@return source gives us the location of source*/
        public Station getSource() { return source; }

        /**@return destination gives us the location of destination*/
        public Station getDestination() { return destination; }

        /**@return trainId gives us the unique id of the train*/
        public int getTrainId() { return this.trainId; }

        /**@return track returns the track*/
        public Rail getRail() { return this.rail; }


}
