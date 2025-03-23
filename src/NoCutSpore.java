public class NoCutSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", ""+insect);
        insect.addPoints(calories);
        insect.setCutting(false); // Vágás kikapcsolása
        insect.setBuffTimer(2);   // Vágás kikapcsolásának időtartama: 2 időegység.
        tekton.removeSpore(this);
        Logger.exit("consumed", ""+insect);
    }
    public NoCutSpore(Tekton t1) {
        Logger.enter("NoCutSpore", "");
        tekton = t1;
        Logger.exit("NoCutSpore", "");
    }
}