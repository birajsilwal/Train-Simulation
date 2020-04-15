package smartrail;

import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class FileLoader {

    protected List<LinkedList<Rail>> railSystem;
    protected LinkedList<Station> stations;
    protected LinkedList<Switch> switches;
    private Station rootStation;//We will have to increase this to an array later to deal with parallel tracks
    private Display display;
    private Pane pane;

    public FileLoader(Train train){
        //Read in the config file
        readInTrack(train);
        //make the track
        makeTrack();
        //display = new Display(pane, this);
    }
    /*
    Reads in the file and makes LinkedLists for the tracks, stations, and switches
     */
    public synchronized void readInTrack(Train train){
        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/simple_switch1.txt"));
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
            System.out.println("File not found exception in FileLoader.");
        }catch (Exception e1){
            System.out.println("Something else went wrong in FileLoader.");
        }
    }

    public synchronized void makeTrack(){
        rootStation = null;

        //Make the stations connect where the coordinates match
        for(Station station : stations){
            for(LinkedList<Rail> rails : railSystem){
                if(rails.get(0).getStartPoint().xcoor == station.getLocation().xcoor &&
                        rails.get(0).getStartPoint().ycoor == station.getLocation().ycoor){
                    if(rootStation == null){
                        rootStation = station;
                    }
                    station.setRight(rails.get(0));
                    rails.get(0).setLeft(station);
                }
            }
        }
        //Connect the stations that are 1 away to the right of the track
        for(Station station : stations){
            for(LinkedList<Rail> rails : railSystem){
                if(rails.getLast().getStartPoint().xcoor == station.getLocation().xcoor-1 &&
                        rails.getLast().getStartPoint().ycoor == station.getLocation().ycoor){
                    station.setLeft(rails.getLast());
                    rails.getLast().setEndPoint(station.getLocation());
                    rails.getLast().setRight(station);
                }
            }
        }
//        for(Station s:stations){
//            System.out.println("Station: " +s);
//        }
        //Set the switches
        for(LinkedList<Rail> rails : railSystem){
            for(Rail rail : rails) {
                for (Switch s : switches) {
                    if (rail.startPoint.xcoor == s.startPoint.xcoor && rail.startPoint.ycoor == s.startPoint.ycoor){
                        //System.out.println("Startpoint " + rail.startPoint);
                        rail.rightSwitch = s;
                        s.left = rail;
                    }
                    else if(s.right == null && rail.startPoint.xcoor == s.startPoint.xcoor+1 && rail.startPoint.ycoor > s.startPoint.ycoor){
                        //System.out.println("Endpoint " + rail);
                        s.endPoint = rail.startPoint;
                        s.right = rail;
                        rail.leftSwitch = s;
                    }
                }
            }
        }
//        for(LinkedList<Rail> rails: railSystem){
//            for(Rail r: rails){
//                System.out.println("rail " +r);
//            }
//        }
//        for(Switch s:switches){
//            System.out.println("Switch: " +s);
//        }
//        initGUI(railSystem, stations, switches);
        getSwitches1(switches);

    }

    public void getSwitches1(List<Switch> switchesList) {
//        System.out.println("This is the size of the switches: " + stations.size());
//        System.out.println("This is switches ll: " + switchesList);

    }

    public List<Station> getStation() {
        return stations;
    }

    public List<Switch> getSwitches() {
        return switches;
    }

    protected Station getRailSystem(){
        return rootStation;
    }
}
