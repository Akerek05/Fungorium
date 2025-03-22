public class Insect implements TurnControl{
    protected int resources;
    protected int actionPoints;
    protected int playerID;
    protected int buffTimer;
    protected boolean canCut;

    protected Tekton tekton;

    public void moveToTekton(Tekton tekton) {
        //Rovar mozgatása két tekton között
        Logger.enter("moveToTekton", "");

        Logger.exit("moveToTekton", "");
    }
    public Insect(Tekton tekton) {
        this.tekton = tekton;
    }
    public void upgradeInsect() {}
    public void addPoints(int points) {
        resources += points;
    }
    public void eatSpore(Spore spore) {
        //Spóra elfogyasztása rovar által
        Logger.enter("eatSpore", "");
        spore.consumed(this); //Spóra megevése
        Logger.exit("eatSpore", "");
    }
    public void setCutting(boolean cutting) {
        //Rovar fonalvágó képességének beállítása
        Logger.enter("setCutting", "");
        canCut = cutting;
        Logger.exit("setCutting", "");

    }
    public void setActionPoints(int points) {
        //Rovar akciópontjainak átállítása
        Logger.enter("setActionPoint", "");
        actionPoints = points;
        Logger.exit("setActionPoint", "");
    }
    public int getActionPoints(){ return actionPoints;}

    public void cutString(ShroomString string) {
        Logger.enter("cutString", "");
        string.gotCut();
        string.timeElapsed();
        Logger.exit("cutString", "");
    }




    public void setBuffTimer(int t){ buffTimer = t;}

    @Override
    public void die(){
        tekton.removeInsect(this);
    }
    @Override
    public void timeElapsed() {}
}
