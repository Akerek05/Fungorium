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
     * Konstruktor fejlesztett gomba példányhoz.
     *
     * @param position A Tekton, amelyen a gomba elhelyezkedik
     */
    public UpgradedMushroom(Tekton position) {
        super(position);
    }

    /**
     * Spóra szórása több szomszédos Tektonra BFS algoritmussal.
     *
     * @param tekton Kiindulási pont a terjesztéshez
     */
    public void spreadSpore(Tekton tekton) {
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
                        neighbour.addSpore();
                        visited.add(neighbour);
                        queue.add(neighbour);
                    }
                }
            }
        }
        Logger.exit("spreadSpore", "" + tekton);
    }
}
