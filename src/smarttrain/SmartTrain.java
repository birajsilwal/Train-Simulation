package smarttrain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SmartTrain {

    public SmartTrain(){
        //Read in the config file

        //get a list of all of the components

        //make the track

        //While loop---------------------
        //Make a move

        //See if the move makes sense

        //move the train
        //End of while loop-------------
    }
    private void readInTrack(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/simple.txt"));
            String line = null;
            while((line = in.readLine())!=null && !line.equals("\n")&&
                    !line.equals(" ")) {
                String[] arr = line.split(" ");
                for (String str : arr) {
                    str = str.replaceAll("\\s+", "");
                }
                System.out.println();
//                for (int i = 0; i < Integer.parseInt(arr[2]); i++) {
//                    oldSock.add(new Tile(line.charAt(0), Integer.parseInt(arr[1]), 0, 0));
//                }
            }
        }catch(FileNotFoundException e){
            System.out.println("That is not a good file");
        }catch (Exception e1){
            System.out.println("Something else went wrong");
        }
    }
    public static void main(String[] args) {
        SmartTrain train = new SmartTrain();
    }
}