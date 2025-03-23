/**
 * A játékban szereplő rovar entitás.
 * Tud mozogni, spórát fogyasztani, és fonalat vágni.
 */
public class Insect implements TurnControl {

    protected int resources;
    protected int actionPoints;
    protected int playerID;
    protected int buffTimer;
    protected boolean canCut;
    protected Tekton tekton;

    /**
     * Rovar mozgatása egyik Tektonról a másikra.
     *
     * @param tekton2 A cél Tekton
     */
    public void moveToTekton(Tekton tekton2) {
        Logger.enter("moveToTekton", "");
        if(Logger.askUser("Is actionPoint != 0?")){
            setActionPoints(0);
        }
        tekton.moveInsect(this, tekton2);
        Logger.exit("moveToTekton", "");
    }

    /**
     * Rovar konstruktor adott Tektonra helyezéssel.
     *
     * @param tekton A kezdeti pozíció
     */
    public Insect(Tekton tekton) {
        Logger.enter("Insect ctor", "");
        this.tekton = tekton;
        Logger.exit("Insect ctor", "");
    }

    public void upgradeInsect() {}

    /**
     * Pontok (erőforrás) hozzáadása a rovarhoz.
     *
     * @param points Az adott pontszám
     */
    public void addPoints(int points) {
        Logger.enter("addPoints", "" + points);
        resources += points;
        Logger.exit("addPoints", "" + resources);
    }

    /**
     * Spóra elfogyasztása rovar által.
     *
     * @param spore A spóra objektum
     */
    public void eatSpore(Spore spore) {
        Logger.enter("eatSpore", ""+spore);
        spore.consumed(this);
        Logger.exit("eatSpore", ""+spore);
    }

    /**
     * Fonal elvágása paraméter átállítása.
     *
     * @param cutting A fonalvágás tulajdonásga
     */
    public void setCutting(boolean cutting) {
        Logger.enter("setCutting", "" + cutting);
        canCut = cutting;
        Logger.exit("setCutting", ""+canCut);
    }

    /**
     * A rovar action pontjának átállítása
     *
     * @param points A leendő action pont
     */
    public void setActionPoints(int points) {
        Logger.enter("setActionPoint", ""+points);
        actionPoints = points;
        Logger.exit("setActionPoint", ""+actionPoints);
    }

    public int getActionPoints(){ return actionPoints;}

    /**
     * Fonal elvágása rovar által.
     *
     * @param string A vágandó fonal
     */
    public void cutString(ShroomString string) {
        Logger.enter("cutString", ""+string);
        if(!Logger.askUser("Can the insect cut?")){
            setCutting(false);
        }
        string.die();
        string.timeElapsed();
        Logger.exit("cutString", ""+string);
    }

    public void setBuffTimer(int t){ buffTimer = t;}

    /**
     * A rovar halálát reprezentáló metódus.
     * Eltávolítja a rovart a Tektonról.
     */
    @Override
    public void die(){
        Logger.enter("die", "");
        tekton.removeInsect(this);
        Logger.exit("die", "");
    }

    @Override
    public void timeElapsed() {}
}
