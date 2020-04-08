package smartrail;

/*class is used to mark the beginning coordinates of object*/
public class Point{
    int xcoor;
    int ycoor;

    public Point(int x, int y) {
        xcoor = x;
        ycoor = y;
    }
    @Override
    public String toString(){
        return "["+ xcoor + "," + ycoor + "]";
    }
}
