public class BuffSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", "");
        insect.addPoints(calories);
        insect.setActionPoints(3);      //Action pontok 2-re állítása - Rovar erősítése
        insect.setBuffTimer(1);         //Erősítés időtartama: 1 időegység
        tekton.removeSpore(this);
        Logger.exit("consumed", "");
    }
    public BuffSpore() {
        Logger.enter("BuffSpore", "");
        Logger.exit("BuffSpore", "");
    }
}
