import java.io.Serializable;
import java.util.Random;

/**
 * Alapértelmezett spóraosztály, amely pontokat ad elfogyasztáskor.
 * Többféle specializált spóra származik belőle.
 */
public class Spore implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public static void resetSpid() {
        sporeCount = 0;
    }

    /**
     * Elfogyasztási művelet – erőforrás növelése és spóra eltávolítása.
     *
     * @param insect A rovar, amely elfogyasztja
     */
    public void consumed(Insect insect) {
        insect.addPoints(calories);
        tekton.removeSpore(this);
        insect.buffTimer = 3;
    }

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){
        String type = "Basic";
        return id+ ": Type: " + type +", Position: "+ tekton.id + ", Owner: " + playerid + ", Calories:"+ calories;};


}
