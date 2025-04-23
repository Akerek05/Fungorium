/**
 * Egy halálos spóra, amely megöli a rovart, ha az elfogyasztja.
 */
public class KillerSpore extends Spore {

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param actionPoints actionpoints
     */
    public KillerSpore(Tekton tekton, int playerID, int actionPoints) {
        super(tekton, playerID, actionPoints);
    }

    /**
     * A rovar elpusztul, miután elfogyasztja a spórát.
     *
     * @param insect A cél rovar
     */
    public void consumed(Insect insect) {
        Logger.enter("consumed", "" + insect);
        insect.addPoints(calories);
        insect.die();               // Rovar megölése
        tekton.removeSpore(this);
        Logger.exit("consumed", "" + insect);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){return "KillerSpore";};
}
