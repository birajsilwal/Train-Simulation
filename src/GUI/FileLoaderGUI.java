package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

// ignore this class for now

public class FileLoaderGUI {


    File file = new File("simple.txt");
    BufferedReader bufferedReader;

    {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
