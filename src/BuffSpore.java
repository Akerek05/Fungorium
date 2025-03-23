public class BuffSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", ""+insect);
        insect.addPoints(calories);
        insect.setActionPoints(3);      //Action pontok 2-re állítása - Rovar erősítése
        insect.setBuffTimer(1);         //Erősítés időtartama: 1 időegység
        tekton.removeSpore(this);
        Logger.exit("consumed", ""+insect);
    }
    public BuffSpore(Tekton t1) {
        Logger.enter("BuffSpore", "");
        tekton = t1;
        Logger.exit("BuffSpore", "");
    }
}
