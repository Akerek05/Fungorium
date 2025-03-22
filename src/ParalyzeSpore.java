public class ParalyzeSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", "");
        insect.addPoints(calories);
        insect.setActionPoints(0);  // Action point lenullázása - Rovar lebénítása
        insect.setBuffTimer(1);     // Bénítás időtartamának beállítása: 1 időegység.
        tekton.removeSpore(this);
        Logger.exit("consumed", "");
    }
}

