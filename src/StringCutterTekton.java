/**
 * Speciális Tekton, amelyen idővel különleges események történhetnek,
 * például automatikus fonalvágás – jelenleg nincs implementálva.
 */
public class StringCutterTekton extends Tekton {

    /**
     * Minden kör végén végrehajtódó művelet – jelenleg üres.
     */
    public void timeElapsed() {
        for (int i = arrayOfString.size() - 1; i >= 0 ; i--) {
            ShroomString string = arrayOfString.get(i);
            string.getDamaged(10);
            string.timeElapsed();
            if (string.lifeTime <= 0) {
                string.die();
            }
        }
        for (int i = arrayOfMushroom.size() - 1; i >= 0 ; i-- ) {
            Mushroom mushroom = arrayOfMushroom.get(i);
            mushroom.timeElapsed();
            if (mushroom.lifeTime <= 0) {
                mushroom.die();
            }
        }
        for (Insect insect : arrayOfInsect){
            insect.timeElapsed();
        }
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){
        String type = "StringCutter";

        String  output= id+ ": ";
        output += "Neighbours: ";
        for(Tekton t : neighbours){
            output += t.id + ", ";
        }
        if(neighbours.isEmpty()) output += ", ";

        output += "StringNeighbours: ";
        for (Tekton t : stringNeighbours){
            output += t.id + ", ";
        }
        if(stringNeighbours.isEmpty()) output += ", ";

        output+= "Type: "+type;
        return output;
    };
}
