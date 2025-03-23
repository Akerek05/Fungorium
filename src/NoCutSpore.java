/**
 * Spóra, amely ideiglenesen letiltja a rovar fonalvágó képességét.
 */
public class NoCutSpore extends Spore {

    /**
     * A spóra elfogyasztása után a rovar nem tud fonalat vágni egy ideig.
     *
     * @param insect A rovar, amely elfogyasztja a spórát
     */
    public void consumed(Insect insect) {
        Logger.enter("consumed", "" + insect);
        insect.addPoints(calories);
        insect.setCutting(false);  // Vágás kikapcsolása
        insect.setBuffTimer(2);    // Vágás kikapcsolásának időtartama: 2 időegység
        tekton.removeSpore(this);
        Logger.exit("consumed", "" + insect);
    }

    /**
     * Konstruktor egy új NoCutSpore létrehozásához.
     *
     * @param t1 A Tekton, amelyen a spóra megjelenik
     */
    public NoCutSpore(Tekton t1) {
        Logger.enter("NoCutSpore", "");
        tekton = t1;
        Logger.exit("NoCutSpore", "");
    }
}
