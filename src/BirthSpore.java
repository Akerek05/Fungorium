public class BirthSpore extends Spore{
    /**
     * Minden paraméteres konstruktor
     * @param tekton position
     * @param playerID playerid
     * @param actionPoints actionpoints
     */
    public BirthSpore(Tekton tekton, int playerID, int actionPoints) {
        super(tekton, playerID, actionPoints);
    }

    /**
     * Az elfogyasztás felülírása
     * @param insect A rovar, amely elfogyasztja
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        Insect newInsect = new Insect(insect.playerID, insect.tekton, insect.actionPoints, insect.resources, insect.buffTimer, insect.effectType);
        newInsect.moveToTekton(insect.tekton);
        tekton.removeSpore(this);
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){return "BirthSpore";};


}
