package smartrail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class FileLoader {

    private List<Point> tracks;
    private List<Point> stations;
    private List<Point> switches;

    /*Nested class used to mark the beginning of the objects*/
    class Point{
        int x;
        int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public FileLoader(){
        //Read in the config file
        readInTrack();
        int i=0;
        for(Point p : tracks){
            System.out.println("Track: " +i+ "["+ p.x + " " + p.y+"]");
            i++;
        }
        i=0;
        for(Point p : stations){
            System.out.println("Station: " +i+ "["+ p.x + " " + p.y+"]");
            i++;
        }

        //make the track
        makeTrack();
        //While loop---------------------
        //Make a move

        //See if the move makes sense

        //move the train
        //End of while loop-------------
    }
    /*
    Reads in the file and makes LinkedLists for the tracks, stations, and switches
     */
    private void readInTrack(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/simple.txt"));
            String line = null;
            tracks = new LinkedList<>();
            stations = new LinkedList<>();
            switches = new LinkedList<>();

            while((line = in.readLine())!=null && !line.equals("\n")&&
                    !line.equals(" ")) {
                String[] arr = line.split(" ");
                for (String str : arr) {
                    str = str.replaceAll("\\s+", "");
                }
                switch (arr[0]){
                    case "track":
                        if(arr.length == 4){
                            //add the coordinates to tracks
                            // S _ S
                            tracks.add(new Point(Integer.parseInt(arr[1]),Integer.parseInt(arr[2])));
                        }else{
                            int startingX = Integer.parseInt(arr[1]);
                            int xLength = (Integer.parseInt(arr[3])-startingX)/Integer.parseInt(arr[5]);
                            // S _  _  _  _ S
                            for(int i=0;i<Integer.parseInt(arr[5]);i++){
                                tracks.add(new Point(startingX,Integer.parseInt(arr[2])));
                                startingX += xLength;
                            }
                        }
                        break;
                    case"station":
                        stations.add(new Point(Integer.parseInt(arr[1]),Integer.parseInt(arr[2])));
                        break;
                    case"switch":
                        switches.add(new Point(Integer.parseInt(arr[1]),Integer.parseInt(arr[2])));
                        break;
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("That is not a good file");
        }catch (Exception e1){
            System.out.println("Something else went wrong");
        }
    }
    private void makeTrack(){
        //I have the list of stations
        //I have the list of tracks
        //I have the list of switches
    }
}
