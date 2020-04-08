package smartrail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class FileLoader {

    private List<LinkedList<Rail>> railSystem;
    private List<Station> stations;
    private List<Switch> switches;
    private Station rootStation;//We will have to increase this to an array later to deal with parallel tracks

    public FileLoader(Train t){
        //Read in the config file
        readInTrack(t);
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
    private void readInTrack(Train train){
        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/simple_switch.txt"));
            String line = null;
            railSystem = new LinkedList<>();
            stations = new LinkedList<>();
            switches = new LinkedList<>();
            int railNum = 0;
            int stationNum = 0;
            int switchNum = 0;

            while((line = in.readLine())!=null && !line.equals("\n")&&
                    !line.equals(" ")) {
                String[] arr = line.split(" ");
                for (String str : arr) {
                    str = str.replaceAll("\\s+", "");
                }
                switch (arr[0]){
                    case "track":
                        //Holds a line of rails to be converted into Rails
                        LinkedList<smartrail.Point> rail = new LinkedList<>();
                        if(arr.length == 4){
                            //add the coordinates to tracks
                            // S _ S
                            rail.add(new smartrail.Point(Integer.parseInt(arr[1]),Integer.parseInt(arr[2])));
                        }else{
                            int startingX = Integer.parseInt(arr[1]);
                            int xLength = (Integer.parseInt(arr[3])-startingX)/Integer.parseInt(arr[5]);
                            // S _  _  _  _ S
                            for(int i=0;i<Integer.parseInt(arr[5]);i++){
                                rail.add(new smartrail.Point(startingX,Integer.parseInt(arr[2])));
                                startingX += xLength;
                            }
                        }
                        //Make a bunch of independent Rails
                        LinkedList<Rail> railTemp = new LinkedList<>();
                        for(int i=0;i<rail.size();i++){
                            railTemp.add(new Rail(railNum,rail.get(i),train));
                            railNum++;
                        }
                        if(railTemp.size() > 1){
                            //join the rails together
                            //Set the left neighbors
                            for(int i=1;i<railTemp.size();i++){
                                railTemp.get(i).setLeft(railTemp.get(i-1));
                            }
                            //Set the right neighbors
                            for(int i=0;i<railTemp.size()-1;i++){
                                railTemp.get(i).setRight(railTemp.get(i+1));
                                railTemp.get(i).setEndPoint(railTemp.get(i+1).getStartPoint());
                            }
                        }
                        railSystem.add(railTemp);
                        break;
                    case"station":
                        stations.add(new Station(stationNum,
                                new Point(Integer.parseInt(arr[1]),
                                        Integer.parseInt(arr[2])),train));
                        // draw here - option 1 - fileloader would need reference to display
                        stationNum++;
                        break;
                    case"switch":
                        switches.add(new Switch(switchNum,
                                new Point(Integer.parseInt(arr[1]),
                                        Integer.parseInt(arr[2])),train));
                        switchNum++;
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
        rootStation = null;
//        for(LinkedList<Rail> rails : railSystem){
//            for(Rail rail : rails) {
//                System.out.println(rail);
//            }
//        }
//        for(Station s : stations){
//            System.out.println("Station: " + s);
//        }
        //Look through the Stations and see if any of the tracks will match up

        //Make the stations connect where the coordinates match
        for(Station s : stations){
            for(LinkedList<Rail> rails : railSystem){
                if(rails.get(0).getStartPoint().xcoor == s.getLocation().xcoor &&
                        rails.get(0).getStartPoint().ycoor == s.getLocation().ycoor){
                    if(rootStation == null){
                        rootStation = s;
                    }
                    s.setRight(rails.get(0));
                    rails.get(0).setLeft(s);
                }
            }
        }
        //Connect the stations that are 1 away to the right of the track
        for(Station s : stations){
            for(LinkedList<Rail> rails : railSystem){
                if(rails.getLast().getStartPoint().xcoor == s.getLocation().xcoor-1 &&
                        rails.getLast().getStartPoint().ycoor == s.getLocation().ycoor){
                    s.setLeft(rails.getLast());
                    rails.getLast().setEndPoint(s.getLocation());
                    rails.getLast().setRight(s);
                }
            }
        }
        //Set the switches
        for(LinkedList<Rail> rails : railSystem){
            for(Rail r:rails) {
                for (Switch s : switches) {
                    if (r.startPoint.xcoor == s.startPoint.xcoor && r.startPoint.ycoor == s.startPoint.ycoor){
                        System.out.println("Startpoint " + r.startPoint);
                        r.rightSwitch = s;
                        s.left = r;
                    }
                    else if(s.right == null && r.startPoint.xcoor == s.startPoint.xcoor+1 && r.startPoint.ycoor > s.startPoint.ycoor){
                        System.out.println("Endpoint " + r);
                        s.right = r;
                        r.leftSwitch = s;
                    }
                }
            }
        }
    }
    protected Station getRailSystem(){
        return rootStation;
    }
}
