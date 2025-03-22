public class Spore {
    protected int calories;
    protected Tekton tekton;

    public void consumed(Insect insect) {
        Logger.enter("consumed", "");
        insect.addPoints(calories);
        tekton.removeSpore(this);
        Logger.exit("consumed", "");
    }
}
