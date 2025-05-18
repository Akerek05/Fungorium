import java.io.Serializable;

/**
 * Gomba entitás, amely spórát képes szórni, fonalat növeszteni, és fejleszthető.
 */
public class Mushroom implements TurnControl, Serializable {
    private static final long serialVersionUID = 1L;
    protected int sporeSpawnTime = 0;
    protected int playerID;
    protected int lifeTime = 100;
    protected int resources = 0;
    protected Tekton position;
    public int id;
    public static int mushroomCount = 0;

    /**
     * Default konstruktor -- CSAK AZ UPGRADED MUSHROOMNAK
     */
    public Mushroom(){}

    public static void resetMid() {
        mushroomCount = 0;
    }

    /**
     * Konstruktor, amely elhelyezi a gombát egy adott Tektonra.
     *
     * @param position A pozíció Tekton
     * @param pid playerID
     */

    public Mushroom(Tekton position, int pid) {
        this.position = position;
        this.playerID = pid;

        this.id = mushroomCount++;
    }

    /**
     * Minden paraméteres konstruktor
     * @param position tekton
     * @param pid playerid
     * @param lifeTime élettartam vagy pont
     * @param resources mennyi nyersanyaga van
     * @param sporeSpawnTime spóra szórása lehet-e
     */
    public Mushroom(int pid, Tekton position, int sporeSpawnTime, int lifeTime, int resources) {
        this.position = position;
        this.playerID = pid;
        this.lifeTime = lifeTime;
        this.resources = resources;
        this.sporeSpawnTime = sporeSpawnTime;

        this.id = mushroomCount++;
    }

    /**
     * Gomba fejlesztése egy új, erősebb változatra.
     */
    public void upgradeMushroom() {
        if(resources > 10) {
            position.addUpgradedBody();
            resources -= 10;
            return;
        }
        System.out.println("Error! Could not upgrade mushroom");
    }

    /**
     * Spóra szórása egy szomszédos Tektonra.
     *
     * @param tekton A cél Tekton
     * @param rnd A random szórásért felelős
     */
    public void spreadSpore(Tekton tekton, int rnd, int calories) {
        if(sporeSpawnTime > 4) {
            if (position.neighbours.contains(tekton)) {
                tekton.addSpore(playerID, rnd, calories);
                sporeSpawnTime-=4;
                return;
            }
        }
        System.out.println("Error! Could not spread Spore to Tekton:"+tekton.id+" from Mushroom:"+id);
    }

    /**
     * Fonal növesztése két Tekton között.
     *
     * @param tekton1 Az egyik Tekton
     * @param tekton2 A másik Tekton
     */
    public void growString(Tekton tekton1, Tekton tekton2) {
        String error = "Error! Could not grow MushroomString by Mushroom:"+id+" at "+tekton1.id+" and "+tekton2.id+" Tektons.";
        boolean connectionCheck = false;

        for (ShroomString string : tekton1.arrayOfString) {
            if ((string.startTek.equals(tekton1) || string.disTek.equals(tekton1)) &&
                    string.parentSrhoom.equals(this) && string.isConnected && !string.growing) {
                connectionCheck = true;
                break;
            }
        }


        if ((tekton1.equals(this.position) || connectionCheck) &&
                tekton1.neighbours.stream().anyMatch(t -> t.id == tekton2.id)) {
            try {
                tekton2.addString(tekton1, this);
            } catch (Exception e) {
                System.out.println(error);
            }
            return;
        }
        System.out.println(error);
    }

    /**
     * Gomba eltávolítása a Tektonról.
     */
    public void die(){
        position.removeBody(this);
    }

    public void timeElapsed() {
        resources += 10;
        sporeSpawnTime += 2;
        lifeTime -= 10;
        if (lifeTime <= 0) {
            this.die();
        }
    }

    /**
     * Kiírja a paramétereket
     * @return szöveg
     */
    public String toString() {
        return id + ": Owner: "+ playerID+ ", Position: "+ position.id+ ", Timer: "+ sporeSpawnTime
        +", HP: "+lifeTime+", Resources: "+resources + ", Type: Basic";
    }
}
