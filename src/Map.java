import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    public List<Tekton> tektons = new ArrayList<Tekton>();
    public void addTekton() {
//        Random random = new Random();
//        int type = random.nextInt(4);
//        switch (type) {
//            case 0 -> tektons.add(new UnlivableTekton());
//            case 1 -> tektons.add(new StringCutterTekton());
//            case 2 -> tektons.add(new MultipleStringTekton());
//            case 3 -> tektons.add(new Tekton());
//        }
        tektons.add(new Tekton());
    if(tektons.size() != 1) {
        addNeighbours();
    }
    }


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
