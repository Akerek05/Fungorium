public class Mushroom implements TurnControl{
    protected int sporeSpawnTime;
    protected int playerID;
    protected int lifetime;
    protected int resources;
    protected Tekton position;
    public void upgradeMushroom(Tekton tekton) {}
    public void spreadSpore(Tekton tekton) {
        tekton.addSpore();
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
