import java.io.Serializable;

/**
 * A játékban szereplő rovar entitás.
 * Tud mozogni, spórát fogyasztani, és fonalat vágni.
 */

public class Insect implements TurnControl, Serializable {
    private static final long serialVersionUID = 1L;
    public static int insectCount = 0;
    public int id = 0;
    protected int resources = 0;
    protected int actionPoints = 3;
    protected int playerID;
    protected int buffTimer = 0;
    protected Tekton tekton;
    public Effect effectType = Effect.DEFAULT;

    /**
     * Rovar konstruktor adott Tektonra helyezéssel.
     *
     * @param tekton A kezdeti pozíció
     * @param pid playerid
     */
    public Insect(Tekton tekton, int pid) {
        this.playerID = pid;
        this.tekton = tekton;

        this.id = insectCount++;
    }

    /**
     *Rovar konstruktora minden adatra
     *
     *
     */
    public Insect (int playerID, Tekton tekton, int actionPoints, int resources, int buffTimer, Effect effectType) {
        this.playerID = playerID;
        this.tekton = tekton;
        this.actionPoints = actionPoints;
        this.buffTimer = buffTimer;
        this.resources = resources;
        this.effectType = effectType;

        this.id = insectCount++;
    }

    /**
     * Rovar mozgatása egyik Tektonról a másikra.
     *
     * @param tekton2 A cél Tekton
     */
    public void moveToTekton(Tekton tekton2) {
        String error = "Error! Could not move Insect:"+ id + " to Tekton:" + tekton2.id;
        if(actionPoints > 0 && effectType != Effect.PARALYZE) {
            try {
                tekton.moveInsect(this, tekton2);
            } catch (Exception e) {
                System.out.println(error);
                return;
            }
            actionPoints--;
            return;
        }
        System.out.println(error);
    }

    public static void resetIid() {
        insectCount = 0;
    }

    /**
     * Pontok (erőforrás) hozzáadása a rovarhoz.
     *
     * @param points Az adott pontszám
     */
    public void addPoints(int points) {
        resources += points;
    }

    /**
     * Spóra elfogyasztása rovar által.
     *
     * @param spore A spóra objektum
     */
    public void eatSpore(Spore spore) {
        if (actionPoints > 0) {
            spore.consumed(this);
            actionPoints--;
            return;
        }
        System.out.println("Error! Could not eat Spore:"+spore.id+" at Tekton:"+tekton.id);
    }

    /**
     * A rovar action pontjának átállítása
     *
     * @param points A leendő action pont
     */
    public void setActionPoints(int points) {
        actionPoints = points;
    }

    /**
     * Fonal elvágása rovar által.
     *
     * @param string A vágandó fonal
     */
    public void cutString(ShroomString string) {
        if (this.effectType != Effect.NOCUT && actionPoints > 0) {
            string.cut();
            actionPoints--;
            return;
        }
        System.out.println("Error! Could not cut String: "+string.id+" by Insect: "+id);
    }

    /**
     * Az effect idejét állítja be
     * @param t hátralévő idő
     */
    public void setBuffTimer(int t){ buffTimer = t;}

    /**
     * A rovar halálát reprezentáló metódus.
     * Eltávolítja a rovart a Tektonról.
     */
    public void die(){
        tekton.removeInsect(this);
    }

    /**
     * Az idő telése
     */
    public void timeElapsed() {
        switch (effectType) {
            case DEFAULT:
                this.actionPoints = 3;
                return;
            case BUFF:
                this.actionPoints = 5;
                break;
            case DEBUFF:
                this.actionPoints = 2;
                break;
            case PARALYZE:
                this.actionPoints = 0;
                break;

        }
        buffTimer--;
        if (buffTimer <= 0) {
            effectType = Effect.DEFAULT;
            this.actionPoints = 3;
        }
    }

    /**
     * Kiírja a paramétereket
     * @return szöveg
     */
    public String toString() {
        return id + ": Owner: " + playerID + ", Position: "+ tekton.id + ", ActionPoints: "+ actionPoints+ ", Timer: "+buffTimer
                +", effectType: " + effectType;
    }
}
