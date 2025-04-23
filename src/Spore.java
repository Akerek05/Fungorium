import java.util.Random;

/**
 * Alapértelmezett spóraosztály, amely pontokat ad elfogyasztáskor.
 * Többféle specializált spóra származik belőle.
 */
public class Spore {
    public int id;
    public static int sporeCount = 0;
    protected int calories;
    protected Tekton tekton;
    protected int playerid;

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

        this.id = sporeCount++;
    }

    /**
     * Elfogyasztási művelet – erőforrás növelése és spóra eltávolítása.
     *
     * @param insect A rovar, amely elfogyasztja
     */
    public void consumed(Insect insect) {

        insect.addPoints(calories);
        tekton.removeSpore(this);

    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){return "Spore " + calories;};


}
