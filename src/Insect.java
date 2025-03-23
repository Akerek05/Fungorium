public class Insect implements TurnControl{
    protected int resources;
    protected int actionPoints;
    protected int playerID;
    protected int buffTimer;
    protected boolean canCut;



    protected Tekton tekton;

    public void moveToTekton(Tekton tekton2) {
        //Rovar mozgatása két tekton között
        Logger.enter("moveToTekton", "");
        if(Logger.askUser("Is actionPoint != 0?")){
            setActionPoints(0);
        }
        tekton.moveInsect(this, tekton2);
        Logger.exit("moveToTekton", "");
    }
    public Insect(Tekton tekton) {
        Logger.enter("Insect ctor", "");
        this.tekton = tekton;
        Logger.exit("Insect ctor", "");
    }
    public void upgradeInsect() {}
    public void addPoints(int points) {
        Logger.enter("addPoints", "" + points);
        resources += points;
        Logger.exit("addPoints", "" + resources);
    }
    public void eatSpore(Spore spore) {
        //Spóra elfogyasztása rovar által
        Logger.enter("eatSpore", ""+spore);
        spore.consumed(this); //Spóra megevése
        Logger.exit("eatSpore", ""+spore);
    }
    public void setCutting(boolean cutting) {
        //Rovar fonalvágó képességének beállítása
        Logger.enter("setCutting", "" + cutting);
        canCut = cutting;
        Logger.exit("setCutting", ""+canCut);

    }
    public void setActionPoints(int points) {
        //Rovar akciópontjainak átállítása
        Logger.enter("setActionPoint", ""+points);
        actionPoints = points;
        Logger.exit("setActionPoint", ""+actionPoints);
    }
    public int getActionPoints(){ return actionPoints;}



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

    @Override
    public void die(){
        Logger.enter("die", "");
        tekton.removeInsect(this);
        Logger.exit("die", "");
    }
    @Override
    public void timeElapsed() {}
}
