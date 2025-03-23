/**
 * Gomba entitás, amely spórát képes szórni, fonalat növeszteni, és fejleszthető.
 */
public class Mushroom implements TurnControl {
    protected int sporeSpawnTime;
    protected int playerID;
    protected int lifetime;
    protected int resources;
    protected Tekton position;
    protected String id;

    /**
     * Gomba fejlesztése egy új, erősebb változatra.
     */
    public void upgradeMushroom() {
        Logger.enter("upgradeMushroom", "");
        if(!Logger.askUser("Is there enough resource for upgrade?")){}
        position.addUpgradedBody();
        Logger.exit("upgradeMushroom", "");
    }

    /**
     * Spóra szórása egy szomszédos Tektonra.
     *
     * @param tekton A cél Tekton
     */
    public void spreadSpore(Tekton tekton) {
        Logger.enter("spreadSpore", ""+tekton);
        if(Logger.askUser("Is SporeSpawnTimer at least 4")) {
            if (position.getNeighbours().contains(tekton)) {
                tekton.addSpore();
            }
        }
        Logger.exit("spreadSpore", ""+tekton);
    }

    /**
     * Fonal növesztése két Tekton között.
     *
     * @param tekton1 Az egyik Tekton
     * @param tekton2 A másik Tekton
     */
    public void growString(Tekton tekton1, Tekton tekton2) {
        Logger.enter("growString", tekton1+","+tekton2);
        tekton1.addString(tekton2);
        tekton2.stringNeighbours.add(tekton1);
        Logger.exit("growString", "");
    }

    /**
     * Konstruktor, amely elhelyezi a gombát egy adott Tektonra.
     *
     * @param position A pozíció Tekton
     */
    public Mushroom(Tekton position) {
        Logger.enter("Mushroom ctor", "");
        this.position = position;
        Logger.exit("Mushroom ctor", "");
    }

    /**
     * Gomba eltávolítása a Tektonról.
     */
    @Override
    public void die(){
        Logger.enter("die","");
        position.removeBody(this);
        Logger.exit("die","");
    }

    @Override
    public void timeElapsed() {}
}
