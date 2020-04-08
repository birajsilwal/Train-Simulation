package smartrail;

import java.util.LinkedList;

public class Message {
    LinkedList<Rail> path;
    boolean travelingRight;

    public Message(){
        path = new LinkedList<>();
        travelingRight = false;
    }
}
