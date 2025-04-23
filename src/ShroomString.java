/**
 * Gombafonalat reprezentáló osztály, amely két Tekton között húzódik.
 */
public class ShroomString implements TurnControl {
    private boolean growing = true;
    private int lifeTime = 100;
    protected Tekton startTek;
    protected Tekton disTek;
    protected Mushroom parentSrhoom;
    protected boolean isCut = false;
    protected boolean isConnected = true;
    public int id;
    public static int stringCount = 0;

    /**
     * Konstruktor új gombafonal létrehozásához két Tekton között.
     *
     * @param startTek A kezdő Tekton
     * @param disTek A cél Tekton
     * @param parentSrhoom A kezdő gomba
     */
    public ShroomString(Tekton startTek, Tekton disTek, Mushroom parentSrhoom) {
        this.startTek = startTek;
        this.disTek = disTek;
        this.parentSrhoom = parentSrhoom;

        this.id = stringCount++;
    }

    /**
     * MInden paraméteres konstruktor
     * @param parentSrhoom A kezdő gomba
     * @param startTek A kezdő tekton
     * @param disTek A cél tekton
     * @param lifeTime Élet
     * @param growing Nő
     * @param isCut Elvan-e vágva
     * @param isConnected Összefüggő-e
     */
    public ShroomString(Mushroom parentSrhoom, Tekton startTek, Tekton disTek, boolean growing, int lifeTime, boolean isCut, boolean isConnected) {
        this.parentSrhoom = parentSrhoom;
        this.startTek = startTek;
        this.disTek = disTek;
        this.growing = growing;
        this.lifeTime = lifeTime;
        this.isCut = isCut;
        this.isConnected = isConnected;

        this.id = stringCount++;
    }

    public void timeElapsed() {}

    /**
     * A fonal "halála" – eltávolítás a kapcsolódó Tektonokról.
     */

    public void die() {
        startTek.removeString(this);
        disTek.removeString(this);
    }

    public String toString() {
        return "String";
    }

    /**
     * Gomba növesztése a fonal végén lévő Tektonra.
     */
    public void growMushroom() {
        if (!growing) {
            disTek.growBody();
        }
    }

    public void eatInsect(){}

    public void getDamaged(int i) {}

    public void getDecayDamage() {}
}
