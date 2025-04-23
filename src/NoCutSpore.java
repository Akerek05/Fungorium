/**
 * Spóra, amely ideiglenesen letiltja a rovar fonalvágó képességét.
 */
public class NoCutSpore extends Spore {

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param actionPoints actionpoints
     */
    public NoCutSpore(Tekton tekton, int playerID, int actionPoints) {
        super(tekton, playerID, actionPoints);
    }

    /**
     * A spóra elfogyasztása után a rovar nem tud fonalat vágni egy ideig.
     *
     * @param insect A rovar, amely elfogyasztja a spórát
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        //insect.setCutting(false);  // Vágás kikapcsolása
        insect.setBuffTimer(2);    // Vágás kikapcsolásának időtartama: 2 időegység
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){return "NoCutSpore";};
}
