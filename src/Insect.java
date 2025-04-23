/**
 * A játékban szereplő rovar entitás.
 * Tud mozogni, spórát fogyasztani, és fonalat vágni.
 */

public class Insect implements TurnControl {
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
        if(actionPoints > 0) {
            tekton.moveInsect(this, tekton2);
        }
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
        spore.consumed(this);
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
        if (this.effectType != Effect.NOCUT) {
            string.die();
            string.timeElapsed();
        }

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
        }
    }

    /**
     * Kiírja a paramétereket
     * @return szöveg
     */
    public String toString() {
        String out = "";
        return out + id + ": Owner: " + playerID;
    }
}
