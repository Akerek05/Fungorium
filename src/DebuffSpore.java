public class DebuffSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", "");
        insect.addPoints(calories);
        insect.setActionPoints(1);       //Action pontok 1-re állítása - Rovar gyengítése
        insect.setBuffTimer(1);         //Gyengítés időtartama: 1 időegység
        tekton.removeSpore(this);
        Logger.exit("consumed", "");
    }
    public DebuffSpore() {
        Logger.enter("DebuffSpore", "");
        Logger.exit("DebuffSpore", "");
    }
}