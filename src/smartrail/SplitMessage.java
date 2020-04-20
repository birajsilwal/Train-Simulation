/**@author Biraj Silwal and Christopher James Shelton **/

package smartrail;

import java.util.LinkedList;

public class SplitMessage extends Message {
    protected LinkedList<Rail> path;
    public SplitMessage(LinkedList<Rail> p){
        super();
        path = new LinkedList<>();
        for(Rail r: p){
            path.add(r);
        }
    }

}
