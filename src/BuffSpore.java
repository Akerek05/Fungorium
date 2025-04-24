/**
 * A spóra egyik típusa, amely megnöveli a rovar akciópontjait ideiglenesen.
 */
public class BuffSpore extends Spore {

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param rand random
     */
    public BuffSpore(Tekton tekton, int playerID, int rand) {
        super(tekton, playerID, rand);
    }

    /**
     * A spóra elfogyasztásának hatása egy rovarra.
     * Megnöveli az akciópontokat 3-ra és beállítja a buff időtartamot 1-re.
     *
     * @param insect A rovar, amely elfogyasztja a spórát
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        insect.effectType = Effect.BUFF;
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){
        String type = "Buff";
        return id + ": Type: " + type +", Position: "+ tekton.id + ", Owner: " + playerid + ", Calories:"+ calories;};
}