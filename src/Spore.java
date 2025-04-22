import java.util.Random;

/**
 * Alapértelmezett spóraosztály, amely pontokat ad elfogyasztáskor.
 * Többféle specializált spóra származik belőle.
 */
public class Spore {
    public int id;
    protected int calories;
    protected Tekton tekton;
    protected int playerid;

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
     * @param pid playerid
     * @param rand kiválasztja, hogy random-e a kalórai érték (-1) esetén random
     */
    public Spore(Tekton t1, int pid, int rand){
        tekton = t1;
        playerid = pid;
        if(rand == -1){
            Random r = new Random();
            calories = r.nextInt(200);
        }
        else
            calories = rand;
    }
}
