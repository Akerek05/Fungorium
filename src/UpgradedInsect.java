/**
 * Fejlesztett rovar osztály, speciális viselkedéssel.
 */
public class UpgradedInsect extends Insect {

    /**
     * Konstruktor a fejlesztett rovar létrehozásához.
     *
     * @param tekton A Tekton, amelyen a rovar kezdetben tartózkodik
     */
    public UpgradedInsect(Tekton tekton) {
        super(tekton);
    }

    /**
     * Mozgás felülírása – jelenleg tiltott vagy speciális logikával bővíthető.
     */
    public void moveToTekton(Tekton tekton) {}
}
