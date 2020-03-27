/**@author Biraj Silwal and Christopher James Shelton **/
package smarttrain;

public class Train {

        private int trainId;
        private Station source;
        private Station destination;
        private int speed;
        private Boolean canChangeDirection;
        private Track track;

        public Train(Station source, Station destination, Boolean canChangeDirection) {
                this.source = source;
                this.destination = destination;
                this.canChangeDirection = canChangeDirection;

        }

        public void moveTrain() {
                // TODO: if there is a valid path, then move the train
                // otherwise return false
        }

        /**@return source gives us the location of source*/
        public Station getSource() { return source; }

        /**@return destination gives us the location of destination*/
        public Station getDestination() { return destination; }

        /**@return trainId gives us the unique id of the train*/
        public int getTrainId() { return this.trainId; }

        /**@return track returns the track*/
        public Track getTrack() { return this.track; }


}
