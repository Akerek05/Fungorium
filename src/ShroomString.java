/**
 * Gombafonalat reprezentáló osztály, amely két Tekton között húzódik.
 */
public class ShroomString implements TurnControl {
    private boolean growing;
    private int lifeTime;
    protected Tekton startTek;
    protected Tekton disTek;
    protected Mushroom parentSrhoom;

    /**
     * Konstruktor új gombafonal létrehozásához két Tekton között.
     *
     * @param startTek A kezdő Tekton
     * @param disTek A cél Tekton
     */
    public ShroomString(Tekton startTek, Tekton disTek) {
        Logger.enter("ShroomString ctor", "");
        this.startTek = startTek;
        this.disTek = disTek;
        boolean growing = true;
        Logger.exit("ShroomString ctor", "");
    }

    @Override
    public void timeElapsed() {}

    /**
     * A fonal "halála" – eltávolítás a kapcsolódó Tektonokról.
     */
    @Override
    public void die() {
        Logger.enter("die", "");
        startTek.removeString(this);
        disTek.removeString(this);
        Logger.exit("die", "");
    }

    /**
     * Gomba növesztése a fonal végén lévő Tektonra.
     */
    public void growMushroom() {
        Logger.enter("growMushroom", "");
        if (Logger.askUser("Is the string mot growing?")) {
            disTek.growBody();
        }
        Logger.exit("growMushroom", "");
    }
}
