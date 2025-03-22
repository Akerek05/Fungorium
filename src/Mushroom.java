public class Mushroom implements TurnControl{
    protected int sporeSpawnTime;
    protected int playerID;
    protected int lifetime;
    protected int resources;
    protected Tekton position;
    /// Csak a loggernek
    protected String id;
    public void upgradeMushroom() {
        Logger.enter("upgradeMushroom", "");
        if(!Logger.askUser("Is there enough resource for upgrade?")){

        }
        position.addUpgradedBody();
        Logger.exit("upgradeMushroom", "");
    }
    public void spreadSpore(Tekton tekton) {
        Logger.enter("spreadSpore", "");
        if(Logger.askUser("Is SporeSpawnTimer at least 4")) {
            if (position.getNeighbours().contains(tekton))
            {
                tekton.addSpore();
            }
        }
        Logger.exit("spreadSpore", "");
    }
    public void growString() {

    }
    public Mushroom(Tekton position) {
        this.position = position;
    }
    @Override
    public void die(){
        Logger.enter("die","");
        position.removeBody(this);
        Logger.exit("die","");
    }
    @Override
    public void timeElapsed() {}
}
