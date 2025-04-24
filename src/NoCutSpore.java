/**
 * Spóra, amely ideiglenesen letiltja a rovar fonalvágó képességét.
 */
public class NoCutSpore extends Spore {

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param rand random
     */
    public NoCutSpore(Tekton tekton, int playerID, int rand) {
        super(tekton, playerID, rand);
    }

    /**
     * A spóra elfogyasztása után a rovar nem tud fonalat vágni egy ideig.
     *
     * @param insect A rovar, amely elfogyasztja a spórát
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        insect.effectType = Effect.NOCUT;
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){
        String type = "NoCut";
        return id + ": Type: " + type +", Position: "+ tekton.id + ", Owner: " + playerid + ", Calories:"+ calories;};
}
