import com.sun.source.tree.WhileLoopTree;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Map mainMap = new Map();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.print("> "); //Parancs bemenet
            String ln = br.readLine();
            mainMap.command(ln);
            if (ln.toUpperCase().equals("ENDGAME")) {
                return;
            }
        }

    }
}
