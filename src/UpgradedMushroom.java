import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Fejlesztett gomba, amely spórákat képes terjeszteni nemcsak szomszédos,
 * hanem több Tektonon keresztül is (BFS szerint).
 */
public class UpgradedMushroom extends Mushroom {

    /**
     * Minden paraméteres konstruktor
     * @param position tekton
     * @param pid playerid
     */
    public UpgradedMushroom(Tekton position, int pid) {
        super(position,pid);
    }

    /**
     * Minden paraméteres konstruktor
     * @param position tekton
     * @param pid playerid
     * @param lifetime élettartam vagy pont
     * @param resources mennyi nyersanyaga van
     * @param sporeSpawnTime spóra szórása lehet-e
     */
    public UpgradedMushroom(int pid, Tekton position,  int sporeSpawnTime, int lifetime, int resources) {
        super(pid,position,sporeSpawnTime,lifetime,resources);
    }

    /**
     * Spóra szórása több szomszédos Tektonra BFS algoritmussal.
     *
     * @param tekton Kiindulási pont a terjesztéshez
     * @param rnd A spóra fajtáját valósítja meg (-1 esetén random)
     */
    public void spreadSpore(Tekton tekton, int rnd) {
        Logger.enter("spreadSpore", "" + tekton);
        if (Logger.askUser("Is SporeSpawnTimer at least 4")) {
            Set<Tekton> visited = new HashSet<>();
            Queue<Tekton> queue = new LinkedList<>();

            queue.add(tekton);
            visited.add(tekton);

            while (!queue.isEmpty()) {
                Tekton current = queue.poll();

                for (Tekton neighbour : current.neighbours) {
                    if (!visited.contains(neighbour)) {
                        //neighbour.addSpore();
                        visited.add(neighbour);
                        queue.add(neighbour);
                    }
                }
            }
        }
        Logger.exit("spreadSpore", "" + tekton);
    }
}
