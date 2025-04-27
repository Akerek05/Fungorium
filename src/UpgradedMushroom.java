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
    public UpgradedMushroom(Tekton position, int pid, int id) {
        this.position = position;
        this.playerID = pid;
        this.id = id;
    }

    /**
     * Spóra szórása több szomszédos Tektonra BFS algoritmussal.
     *
     * @param tekton Kiindulási pont a terjesztéshez
     * @param rnd A spóra fajtáját valósítja meg (-1 esetén random)
     */
    public void spreadSpore(Tekton tekton, int rnd, int calories) {
        String error = "Error! Could not spread Spore to Tekton:"+tekton.id+" from Mushroom:"+id;
        if (sporeSpawnTime > 4) {
            Set<Tekton> visited = new HashSet<>();
            Queue<Tekton> queue = new LinkedList<>();

            queue.add(tekton);
            visited.add(tekton);

            while (!queue.isEmpty()) {
                Tekton current = queue.poll();

                for (Tekton neighbour : current.neighbours) {
                    if (!visited.contains(neighbour)) {
                        neighbour.addSpore(playerID, rnd, calories);
                        visited.add(neighbour);
                        queue.add(neighbour);
                        return;
                    }
                }
                System.out.println(error);
            }
        }
        else
            System.out.println(error);

    }

    /**
     * Kiírja a paramétereket
     * @return szöveg
     */
    public String toString() {
        return id + ": Owner: "+ playerID+ ", Position: "+ position.id+ ", Timer: "+ sporeSpawnTime
                +", HP: "+lifeTime+", Resources: "+resources + ", Type: Upgraded";
    }
}
