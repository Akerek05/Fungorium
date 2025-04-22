/**
 * A játékban szereplő rovar entitás.
 * Tud mozogni, spórát fogyasztani, és fonalat vágni.
 */
public class Insect implements TurnControl {
    public String id;
    protected int resources;
    protected int actionPoints;
    protected int playerID;
    protected int buffTimer;
    protected Tekton tekton;
    public Effect effectType = Effect.DEFAULT;

    /**
     * Rovar mozgatása egyik Tektonról a másikra.
     *
     * @param tekton2 A cél Tekton
     */
    public void moveToTekton(Tekton tekton2) {
        if(actionPoints > 0) {
            tekton.moveInsect(this, tekton2);
        }
    }

    /**
     * Rovar konstruktor adott Tektonra helyezéssel.
     *
     * @param tekton A kezdeti pozíció
     */
    public Insect(Tekton tekton, int pid) {
        this.playerID = pid;
        this.tekton = tekton;
        this.actionPoints = 3;
        this.buffTimer = 0;
        this.resources = 0;
        this.effectType = effectType.DEFAULT;
    }

    /**
     *Rovar konstruktora minden adatra
     *
     *
     */
    public Insect (int playerID, Tekton tekton, int actionPoints, int buffTimer,int resources, Effect effectType) {
        this.playerID = playerID;
        this.tekton = tekton;
        this.actionPoints = actionPoints;
        this.buffTimer = buffTimer;
        this.resources = resources;
        this.effectType = effectType;
    }

    public void upgradeInsect() {}

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
        spore.consumed(this);
    }

    /**
     * Fonal elvágása paraméter átállítása.
     *
     * @param cutting A fonalvágás tulajdonásga
     *                Ez alapjan allitja be az effectType-t
     *                ha cutting akkor NOCUT -> DEFAULT
     *                ha !cutting akkor nem NOCUTBOL -> NOCUT
     *                vagy semmi
     */
    public void setCutting(boolean cutting) {
        if (cutting && effectType == Effect.NOCUT) {
            effectType = Effect.DEFAULT;
        }
        else if (!cutting) {
            effectType = effectType.NOCUT;
        }
    }

    /**
     * A rovar action pontjának átállítása
     *
     * @param points A leendő action pont
     */
    public void setActionPoints(int points) {
        actionPoints = points;
    }

    public int getActionPoints(){ return actionPoints;}

    /**
     * Fonal elvágása rovar által.
     *
     * @param string A vágandó fonal
     */
    public void cutString(ShroomString string) {
        if (this.effectType != Effect.NOCUT) {
            string.die();
            string.timeElapsed();
        }

    }

    public void setBuffTimer(int t){ buffTimer = t;}

    /**
     * A rovar halálát reprezentáló metódus.
     * Eltávolítja a rovart a Tektonról.
     */
    @Override
    public void die(){
        tekton.removeInsect(this);
    }

    @Override
    public void timeElapsed() {
        switch (effectType) {
            case Effect.DEFAULT:
                this.actionPoints = 3;
                return;
            case Effect.BUFF:
                this.actionPoints = 5;
                break;
            case Effect.DEBUFF:
                this.actionPoints = 2;
                break;
            case Effect.PARALYZE:
                this.actionPoints = 0;
                break;

        }
        buffTimer--;
        if (buffTimer <= 0) {
            effectType = Effect.DEFAULT;
        }
    }

    @Override
    public String toString() {
        String out = "";
        return out + id + ": Owner: " + playerID;
    }
}
