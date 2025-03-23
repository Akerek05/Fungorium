public class ParalyzeSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", ""+insect);
        insect.addPoints(calories);
        insect.setActionPoints(0);  // Action point lenullázása - Rovar lebénítása
        insect.setBuffTimer(1);     // Bénítás időtartamának beállítása: 1 időegység.
        tekton.removeSpore(this);
        Logger.exit("consumed", ""+insect);
    }
    public ParalyzeSpore(Tekton t1){
        Logger.enter("ParalyzeSpore", "");
        tekton = t1;
        Logger.exit("ParalyzeSpore", "");
    }
}

