import java.io.Serializable;
import java.util.List;

/**
 * Gombafonalat reprezentáló osztály, amely két Tekton között húzódik.
 */
public class ShroomString implements TurnControl, Serializable {
    private static final long serialVersionUID = 1L;
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
     * Konstruktor új gombafonal létrehozásához két Tekton között. Beállítja paramétereket.
     * NEM ÁLLÍT STRINGNEIGHBOURT
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

    public static void resetSsid() {
        stringCount = 0;
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

        startTek.stringNeighbours.add(disTek);
        disTek.stringNeighbours.add(startTek);

        startTek.arrayOfString.add(this);
        disTek.arrayOfString.add(this);

        this.id = stringCount++;
    }

    /**
     * Segédfüggvény, ha meghal egy gomba a fonalai false
     */
    public void notConnected() {
        if(this.parentSrhoom == null) {
            this.isConnected = false;
        }
    }

    /**
     * Idő telése
     */
    public void timeElapsed() {
        growing=false;
        if (isCut) {
            lifeTime-= 40;
        }
        if (!isConnected) {
            lifeTime -= 10;
        }
        if(lifeTime <= 0) {
            die();
        }

    }

    /**
     * A fonal "halála" – eltávolítás a kapcsolódó Tektonokról.
     */
    public void die() {
        startTek.removeString(this);
        disTek.removeString(this);

        //TODO: SZOMSZÉDOKRA IS ISCONNECTED false
    }

    public String toString() {
        String num = "";
        if(parentSrhoom == null) {
            num = "NoParent";
        }
        else {
            num = ""+parentSrhoom.id;
        }

        return id+": ParentShroom: "+ num+ ", Start: "+startTek.id+ ", End: "+disTek.id
                + ", Growing: " + growing +", LifeTime: "+lifeTime
                + ", isCut: "+isCut+", isConnected: "+isConnected;
    }

    /**
     * Gomba növesztése a fonal végén lévő Tektonra.
     */
    public void growMushroom() {
        String error = "Error! Could not grow mushroom by String:" + id + " at Tekton:" + disTek.id;
        if (!growing && isConnected) {
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
        else
            System.out.println("Error! Could not eat Insect:" + insect.id + " by String:" + this.id);

    }

    public void cut(){
        isCut = true;
        isConnected = false;

        //TODO: SZOMSZÉDOKRA IS ISCONNECTED false
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
