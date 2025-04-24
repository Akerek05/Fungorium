/**
 * Egy halálos spóra, amely megöli a rovart, ha az elfogyasztja.
 */
public class KillerSpore extends Spore {

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param rand random
     */
    public KillerSpore(Tekton tekton, int playerID, int rand) {
        super(tekton, playerID, rand);
    }

    /**
     * A rovar elpusztul, miután elfogyasztja a spórát.
     *
     * @param insect A cél rovar
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        insect.die();               // Rovar megölése
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){
        String type = "Killer";
        return id + ": Type: " + type +", Position: "+ tekton.id + ", Owner: " + playerid + ", Calories:"+ calories;};
}
