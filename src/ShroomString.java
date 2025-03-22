public class ShroomString implements TurnControl{
    private boolean growing;
    private int lifeTime;
    protected Tekton startTek;
    protected Tekton disTek;

    public ShroomString(Tekton startTek, Tekton disTek) {
        this.startTek = startTek;
        this.disTek = disTek;
        boolean growing = true;
        startTek.addString(this);
        disTek.addString(this);
    }
    public void gotCut(){
        startTek.removeString(this);
        disTek.removeString(this);
    }

    @Override
    public void timeElapsed() {}
    @Override
    public void die() {}
    public void growMushroom() {
        Logger.enter("growMushroom", "");
        if (Logger.askUser("Is the string mot growing?")) {
            disTek.growBody();
        }
        Logger.exit("growMushroom", "");
    }
}

