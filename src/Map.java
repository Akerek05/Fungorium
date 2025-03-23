import java.util.ArrayList;
import java.util.List;

/**
 * A játékban használt térképet reprezentáló osztály,
 * amely Tektonokat tárol és szomszédsági kapcsolatokat kezel.
 */
public class Map {
    public List<Tekton> tektons = new ArrayList<Tekton>();

    /**
     * Új Tekton hozzáadása a térképhez, és szomszédság frissítése.
     */
    public void addTekton() {
        tektons.add(new Tekton());
        if(tektons.size() != 1) {
            addNeighbours();
        }
    }

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
}
