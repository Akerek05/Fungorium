public class NoCutSpore extends Spore {
    public void consumed(Insect insect) {
        Logger.enter("consumed", "");
        insect.addPoints(calories);
        insect.setCutting(false); // Vágás kikapcsolása
        insect.setBuffTimer(2);   // Vágás kikapcsolásának időtartama: 2 időegység.
        tekton.removeSpore(this);
        Logger.exit("consumed", "");
    }
    public NoCutSpore() {
        Logger.enter("NoCutSpore", "");
        Logger.exit("NoCutSpore", "");
    }
}