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
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param actionPoints actionpoints
     */
    public ParalyzeSpore(Tekton tekton, int playerID, int actionPoints) {
        super(tekton, playerID, actionPoints);
    }
}
