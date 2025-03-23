public class ShroomString implements TurnControl{
    private boolean growing;
    private int lifeTime;
    protected Tekton startTek;
    protected Tekton disTek;

    protected Mushroom parentSrhoom;

    public ShroomString(Tekton startTek, Tekton disTek) {
        Logger.enter("ShroomString ctor", "");
        this.startTek = startTek;
        this.disTek = disTek;
        boolean growing = true;
        //startTek.addString(this);
        //disTek.addString(this);
        Logger.exit("ShroomString ctor", "");
    }

    @Override
    public void timeElapsed() {}
    @Override
    public void die() {
        Logger.enter("die", "");
        startTek.removeString(this);
        disTek.removeString(this);
        Logger.exit("die","");
    }
    public void growMushroom() {
        Logger.enter("growMushroom", "");
        if (Logger.askUser("Is the string mot growing?")) {
            disTek.growBody();
        }
        Logger.exit("growMushroom", "");
    }
}

