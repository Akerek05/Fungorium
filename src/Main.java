import com.sun.source.tree.WhileLoopTree;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Map mainMap = new Map();
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Controller controller = new Controller(mainMap);
        if(controller.gamestarted){controller.oneRound();}

        // Wait for the user to start the game in the GUI
        // Then run the game loop in a separate thread to avoid freezing the GUI
        new Thread(() -> {
            while (true) {
                if (controller.gamestarted) {
                    controller.oneRound();
                }

                try {
                    Thread.sleep(100); // Avoid busy loop
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //controller.showMenu();
        /*while(true){
            System.out.print("> "); //Parancs bemenet
            String ln = br.readLine();
            mainMap.command(ln);
            if (ln.toUpperCase().equals("ENDGAME")) {
                return;
            }
        }*/
    }
}
