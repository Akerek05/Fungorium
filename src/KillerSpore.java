public class KillerSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", "");
        insect.addPoints(calories);
        insect.die();               //Rovar megölése
        tekton.removeSpore(this);
        Logger.exit("consumed", "");
    }
    public KillerSpore(Tekton t1){;
        Logger.enter("KillerSpore", "");
        tekton = t1;
        Logger.exit("KillerSpore", "");
    }
}