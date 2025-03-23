/**
 * Egy spóra típus, amely gyengíti a rovart elfogyasztás után.
 */
public class DebuffSpore extends Spore {

    /**
     * A spóra elfogyasztásának hatása.
     * Csökkenti az akciópontokat 1-re, és beállítja a buff időtartamát 1-re.
     *
     * @param insect A rovar, amely elfogyasztja a spórát
     */
    public void consumed(Insect insect) {
        Logger.enter("consumed", ""+insect);
        insect.addPoints(calories);
        insect.setActionPoints(1);       // Rovar gyengítése
        insect.setBuffTimer(1);         // 1 időegységre gyengít
        tekton.removeSpore(this);
        Logger.exit("consumed", ""+insect);
    }

    /**
     * Konstruktor a DebuffSpore példányhoz.
     *
     * @param t1 A Tekton, amelyen a spóra megjelenik
     */
    public DebuffSpore(Tekton t1) {
        Logger.enter("DebuffSpore", "");
        tekton = t1;
        Logger.exit("DebuffSpore", "");
    }
}
