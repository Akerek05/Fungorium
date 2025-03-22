public class Spore {
    protected int calories;
    protected Tekton tekton;
    /// Csak a loggernek
    protected String id;
    public void consumed(Insect insect) {
        Logger.enter("consumed", "");
        insect.addPoints(calories);
        tekton.removeSpore(this);
        Logger.exit("consumed", "");
    }
    public Spore(Tekton t1){
        Logger.enter("Spore", "");
        tekton = t1;
        Logger.exit("Spore", "");
    }

    public Spore(){

    }
}
