/**
 * Alapértelmezett spóraosztály, amely pontokat ad elfogyasztáskor.
 * Többféle specializált spóra származik belőle.
 */
public class Spore {
    protected int calories;
    protected Tekton tekton;
    protected String id;

    /**
     * Elfogyasztási művelet – erőforrás növelése és spóra eltávolítása.
     *
     * @param insect A rovar, amely elfogyasztja
     */
    public void consumed(Insect insect) {
        Logger.enter("consumed", "" + insect);
        insect.addPoints(calories);
        tekton.removeSpore(this);
        Logger.exit("consumed", "" + insect);
    }

    /**
     * Spóra létrehozása adott Tektonhoz.
     *
     * @param t1 A cél Tekton
     */
    public Spore(Tekton t1){
        Logger.enter("Spore", "");
        tekton = t1;
        Logger.exit("Spore", "");
    }

    public Spore(){}
}
