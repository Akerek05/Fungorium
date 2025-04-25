/**
 * Gombafonalat reprezentáló osztály, amely két Tekton között húzódik.
 */
public class ShroomString implements TurnControl {
    protected boolean growing = true;
    protected int lifeTime = 100;
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
    public ShroomString(Mushroom parentSrhoom, Tekton startTek, Tekton disTek, boolean growing
            ,int lifeTime, boolean isCut, boolean isConnected) {
        this.parentSrhoom = parentSrhoom;
        this.startTek = startTek;
        this.disTek = disTek;
        this.growing = growing;
        this.lifeTime = lifeTime;
        this.isCut = isCut;
        this.isConnected = isConnected;

        this.id = stringCount++;
    }

    public void timeElapsed() {
        if (isCut) {
            lifeTime-= 40;
        }
        if (!isConnected) {
            lifeTime -= 10;
        }
        if (lifeTime <= 0)
        {
            die();
        }
    }

    /**
     * A fonal "halála" – eltávolítás a kapcsolódó Tektonokról.
     */

    public void die() {
        startTek.removeString(this);
        disTek.removeString(this);
    }

    public String toString() {
        return id+": ParentShroom: "+ parentSrhoom.id+ ", Start: "+startTek.id+ ", End: "+disTek.id
                + ", Growing: " + growing +", LifeTime: "+lifeTime
                + ", isCut: "+isCut+", isConnected: "+isConnected;
    }

    /**
     * Gomba növesztése a fonal végén lévő Tektonra.
     */
    public void growMushroom() {
        String error = "Error! Could not grow mushroom by String:" + id + "at " + startTek.id + " " + disTek.id + " Tektons.";
        if (!growing) {
            try {
                disTek.growBody(this.parentSrhoom.playerID);
            } catch (Exception e) {
                System.out.println(error);
            }
            return;
        }
        System.out.println(error);
    }

    public void eatInsect(Insect insect){
        if (insect.effectType == Effect.PARALYZE) {
            insect.die();
            growMushroom();
        }
    }

    public void cut(){
        isCut = true;
        isConnected = false;
    }

    public void getDamaged(int i) {
        lifeTime -= i;
    }

    public void getDecayDamage() {
        if (isCut) {
            lifeTime -= 30;
        }
    }
}
