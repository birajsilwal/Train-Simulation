package smartrail;

import java.util.concurrent.LinkedBlockingQueue;

public class Switch extends Rail{
//    protected int name;
//    protected LinkedBlockingQueue<Message> inbox;
//    protected Rail left;
//    protected Rail right;
//    protected Point startPoint;
//    protected Point endPoint;

    public Switch(int n,Point p,Train t){
        super(n,p,t);
//        name = n;
//        left = null;
//        right = null;
//        inbox = new LinkedBlockingQueue<Message>();
//        startPoint = p;
    }

    public void setEndPoint(Point p){
        endPoint = p;
    }

    @Override
    public String toString(){
        return name + " " + startPoint + " " + endPoint;
    }
}
