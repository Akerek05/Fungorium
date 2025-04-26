/**
 * Spóra, amely megbénítja a rovart 1 időegységre, azaz lenullázza akciópontjait.
 */
public class ParalyzeSpore extends Spore {

    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param rand random
     */
    public ParalyzeSpore(Tekton tekton, int playerID, int rand) {
        super(tekton, playerID, rand);
    }

    /**
     * A spóra elfogyasztása után a rovar cselekvésképtelenné válik egy körre.
     *
     * @param insect A cél rovar
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        insect.effectType = Effect.PARALYZE;
        insect.buffTimer = 3;
        insect.actionPoints = 1;
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){
        String type = "Paralyze";
        return id + ": Type: " + type +", Position: "+ tekton.id + ", Owner: " + playerid + ", Calories:"+ calories;};
}
