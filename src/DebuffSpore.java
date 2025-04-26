/**
 * Egy spóra típus, amely gyengíti a rovart elfogyasztás után.
 */
public class DebuffSpore extends Spore {

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param rand random
     */
    public DebuffSpore(Tekton tekton, int playerID, int rand) {
        super(tekton, playerID, rand);
    }

    /**
     * A spóra elfogyasztásának hatása.
     * Csökkenti az akciópontokat 1-re, és beállítja a buff időtartamát 1-re.
     *
     * @param insect A rovar, amely elfogyasztja a spórát
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        insect.effectType = Effect.DEBUFF;
        insect.buffTimer = 3;
        insect.actionPoints = 2;
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){
        String type = "DeBuff";
        return id + ": Type: " + type +", Position: "+ tekton.id + ", Owner: " + playerid + ", Calories:"+ calories;};
}
