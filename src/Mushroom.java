/**
 * Gomba entitás, amely spórát képes szórni, fonalat növeszteni, és fejleszthető.
 */
public class Mushroom implements TurnControl {
    protected int sporeSpawnTime = 0;
    protected int playerID;
    protected int lifetime = 100;
    protected int resources = 0;
    protected Tekton position;
    public int id;
    public static int mushroomCount = 0;

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
     * @param lifetime élettartam vagy pont
     * @param resources mennyi nyersanyaga van
     * @param sporeSpawnTime spóra szórása lehet-e
     */
    public Mushroom(int pid, Tekton position, int sporeSpawnTime, int lifetime, int resources) {
        this.position = position;
        this.playerID = pid;
        this.lifetime = lifetime;
        this.resources = resources;
        this.sporeSpawnTime = sporeSpawnTime;

        this.id = mushroomCount++;
    }

    /**
     * Gomba fejlesztése egy új, erősebb változatra.
     */
    public void upgradeMushroom() {
        Logger.enter("upgradeMushroom", "");
        if(Logger.askUser("Is there enough resource for upgrade?")) {
            position.addUpgradedBody();
        }

        Logger.exit("upgradeMushroom", "");
    }

    /**
     * Spóra szórása egy szomszédos Tektonra.
     *
     * @param tekton A cél Tekton
     * @param rnd A random szórásért felelős
     */
    public void spreadSpore(Tekton tekton, int rnd) {
        if(sporeSpawnTime > 4) {
            if (position.getNeighbours().contains(tekton)) {
                tekton.addSpore(playerID, rnd);
            }
        }
    }

    /**
     * Fonal növesztése két Tekton között.
     *
     * @param tekton1 Az egyik Tekton
     * @param tekton2 A másik Tekton
     */
    public void growString(Tekton tekton1, Tekton tekton2) {
        tekton1.addString(tekton2);
        tekton2.stringNeighbours.add(tekton1);
    }

    /**
     * Gomba eltávolítása a Tektonról.
     */
    public void die(){
        position.removeBody(this);
    }

    public void timeElapsed() {}

    public String toString() {
        return "Mushroom";
    }
}
