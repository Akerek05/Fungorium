/**
 * Egy speciális Tekton típus, amelyen nem lehet gombát növeszteni.
 */
public class UnlivableTekton extends Tekton {

    /**
     * Gomba növesztésének tiltása ezen a mezőn.
     */
    public void growBody() {
        return;
    }

    public String toString(){
        return "UnlivableTekton";
    }
}
