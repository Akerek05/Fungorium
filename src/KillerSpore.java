public class KillerSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", "");
        insect.addPoints(calories);
        insect.die();               //Rovar megölése
        tekton.removeSpore(this);
        Logger.exit("consumed", "");
    }
    public KillerSpore(){
        Logger.enter("KillerSpore", "");
        Logger.exit("KillerSpore", "");
    }
}