import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A játékban használt térképet reprezentáló osztály,
 * amely Tektonokat tárol és szomszédsági kapcsolatokat kezel.
 */
public class Map {
    public List<ShroomString> shroomStrings = new ArrayList<ShroomString>();
    public List<Mushroom> mushrooms = new ArrayList<Mushroom>();
    public List<Insect> insects = new ArrayList<Insect>();
    public List<Spore> spores = new ArrayList<Spore>();
    public List<Tekton> tektons = new ArrayList<Tekton>();
    public List<Integer> scores = new ArrayList<Integer>();


    /**
     * Új Tekton hozzáadása a térképhez, és szomszédság frissítése.
     */
    /*public void addTekton() {
        tektons.add(new Tekton());
        if (tektons.size() != 1) {
            addNeighbours();
        }
    }*/

    /**
     * Frissíti a szomszédsági kapcsolatokat minden Tekton között.
     */
    private void addNeighbours() {
        for (Tekton tekton : tektons) {
            for (Tekton neighbour : tektons) {
                if (tekton != neighbour) {
                    tekton.neighbours.add(neighbour);
                }
            }
        }
    }

    private void endGame(){
        List<Integer> order = new ArrayList<Integer>(scores);

        scores.sort(Collections.reverseOrder());
        for (int i = 0; i < scores.size(); i++) {
            int value = scores.get(i);
            int playerId = order.indexOf(value); // csak az első előfordulást adja vissza
            System.out.println((i + 1) + ". hely: " + playerId + "|Pontszám: " + value);
        }
    }

    public void command(String inputCommand) {
        if (inputCommand == null || inputCommand.isEmpty())
            return; //nem megyünk el a switch case-ig, csak akkor, ha a beolvasott sor nem üres.

        String[] command = inputCommand.trim().split("\\s+"); //szóközöknél eldarabolja a stringet - több szóköz esetén is jól működik.

        switch (command[0].toUpperCase()) {
            case "LOAD":
                System.out.println("LOAD");
                break;

            case "SAVE":
                System.out.println("SAVE");
                break;

            case "CREATE":
                System.out.println("CREATE");
                break;

            case "STATUS":
                System.out.println("STATUS");
                break;

            case "EAT":
                System.out.println("EAT");
                break;

            case "CUT":
                System.out.println("CUT");
                break;

            case "GROW":
                System.out.println("GROW");
                break;

            case "SPREAD":
                System.out.println("SPREAD");
                break;

            case "ENDGAME":
                System.out.println("ENDGAME");
                this.endGame();
                break;

            case "BREAKTEKTON":
                System.out.println("BREAKTEKTON");
                break;

            case "UPGRADE":
                System.out.println("UPGRADE");
                break;

            case "TIMEELAPSED":
                System.out.println("TIMEELAPSED");
                break;

            case "ADDNEIGHBOURS":
                System.out.println("ADDNEIGHBOURS");
                break;

            default:
                System.out.println("Ismeretlen parancs: " + command[0]);
        }
    }
}
