public class BirthSpore extends Spore{
    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param rand random
     */
    public BirthSpore(Tekton tekton, int playerID, int rand) {
        super(tekton, playerID, rand);
    }

    /**
     * Az elfogyasztás felülírása
     * @param insect A rovar, amely elfogyasztja
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        insect.tekton.addInsect(insect.playerID);
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){
        String type = "Birth";
        return id + ": Type: " + type +", Position: "+ tekton.id + ", Owner: " + playerid + ", Calories:"+ calories;};


}
