public class Mushroom implements TurnControl{
    protected int sporeSpawnTime;
    protected int playerID;
    protected int lifetime;
    protected int resources;
    protected Tekton position;
    public void upgradeMushroom(Tekton tekton) {}
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
        Logger.exit("die","");
        position.removeBody(this);
        Logger.exit("die","");
    }
    @Override
    public void timeElapsed() {}
}
