/**@author Biraj Silwal and Christopher James Shelton **/

package smartrail;

/*this class inherited from Rail class.*/
public class Switch extends Rail{

    public Switch(int n,Point p,Train t){
        super(n,p,t);
    }

    /*this method sets the final destination of the */
    public void setEndPoint(Point p){
        endPoint = p;
    }

    @Override
    public String toString(){
        return "Switch " + name + " " + startPoint + " " + endPoint;
    }

}
