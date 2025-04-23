/**
 * Egy spóra típus, amely gyengíti a rovart elfogyasztás után.
 */
public class DebuffSpore extends Spore {

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param actionPoints actionpoints
     */
    public DebuffSpore(Tekton tekton, int playerID, int actionPoints) {
        super(tekton, playerID, actionPoints);
    }

    /**
     * A spóra elfogyasztásának hatása.
     * Csökkenti az akciópontokat 1-re, és beállítja a buff időtartamát 1-re.
     *
     * @param insect A rovar, amely elfogyasztja a spórát
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        insect.setActionPoints(1);       // Rovar gyengítése
        insect.setBuffTimer(1);         // 1 időegységre gyengít
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){return "DebuffSpore";};
}
