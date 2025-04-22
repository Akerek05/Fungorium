/**
 * A spóra egyik típusa, amely megnöveli a rovar akciópontjait ideiglenesen.
 */
public class BuffSpore extends Spore {

    /**
     * A spóra elfogyasztásának hatása egy rovarra.
     * Megnöveli az akciópontokat 3-ra és beállítja a buff időtartamot 1-re.
     *
     * @param insect A rovar, amely elfogyasztja a spórát
     */
    public void consumed(Insect insect) {
        Logger.enter("consumed", "" + insect);
        insect.addPoints(calories);
        insect.setActionPoints(3);      // Action pontok 3-ra állítása - rovar erősítése
        insect.setBuffTimer(1);         // Erősítés időtartama: 1 időegység
        tekton.removeSpore(this);
        Logger.exit("consumed", "" + insect);
    }

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param actionPoints actionpoints
     */
    public BuffSpore(Tekton tekton, int playerID, int actionPoints) {
        super(tekton, playerID, actionPoints);
    }
}