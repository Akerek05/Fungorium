/**
 * Speciális Tekton, amelyen idővel különleges események történhetnek,
 * például automatikus fonalvágás – jelenleg nincs implementálva.
 */
public class StringCutterTekton extends Tekton {

    /**
     * Minden kör végén végrehajtódó művelet – jelenleg üres.
     */
    public void timeElapsed() {}

    /**
     *A spóra paramétereit írja le
     * @return szöveg
     */
    public String toString(){return "StringCutterTekton";};
}
