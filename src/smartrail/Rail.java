package smartrail;

import java.util.concurrent.LinkedBlockingQueue;

public class Rail {

    private int name;
    private LinkedBlockingQueue<String> inbox;
    private Rail left;
    private Rail right;

    public Rail(int name, Rail left, Rail right){
        this.name = name;
        this.left = left;
        this.right = right;
        this.inbox = new LinkedBlockingQueue<String>();
    }

    public void recieiveMessage(String m){
        inbox.add(m);
    }

    // define position of the track x and y and pass to the train

    // able to send the message


}
