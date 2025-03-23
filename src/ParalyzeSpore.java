/**
 * Spóra, amely megbénítja a rovart 1 időegységre, azaz lenullázza akciópontjait.
 */
public class ParalyzeSpore extends Spore {

    /**
     * A spóra elfogyasztása után a rovar cselekvésképtelenné válik egy körre.
     *
     * @param insect A cél rovar
     */
    public void consumed(Insect insect) {
        Logger.enter("consumed", "" + insect);
        insect.addPoints(calories);
        insect.setActionPoints(0);   // Akciópont lenullázása
        insect.setBuffTimer(1);      // Hatás időtartama: 1 időegység
        tekton.removeSpore(this);
        Logger.exit("consumed", "" + insect);
    }

    /**
     * Konstruktor új ParalyzeSpore létrehozásához.
     *
     * @param t1 A tekton, amelyen a spóra megjelenik
     */
    public ParalyzeSpore(Tekton t1){
        Logger.enter("ParalyzeSpore", "");
        tekton = t1;
        Logger.exit("ParalyzeSpore", "");
    }
}
