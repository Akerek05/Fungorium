/**
 * Egy halálos spóra, amely megöli a rovart, ha az elfogyasztja.
 */
public class KillerSpore extends Spore {

    /**
     * A rovar elpusztul, miután elfogyasztja a spórát.
     *
     * @param insect A cél rovar
     */
    public void consumed(Insect insect) {
        Logger.enter("consumed", ""+insect);
        insect.addPoints(calories);
        insect.die();               // Rovar megölése
        tekton.removeSpore(this);
        Logger.exit("consumed", ""+insect);
    }

    /**
     * Konstruktor a KillerSpore létrehozásához.
     *
     * @param t1 A tekton, amelyre elhelyezzük
     */
    public KillerSpore(Tekton t1){
        Logger.enter("KillerSpore", "");
        tekton = t1;
        Logger.exit("KillerSpore", "");
    }
}
