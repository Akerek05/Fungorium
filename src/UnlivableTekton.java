/**
 * Egy speciális Tekton típus, amelyen nem lehet gombát növeszteni.
 */
public class UnlivableTekton extends Tekton {

    /**
     * Gomba növesztésének tiltása ezen a mezőn.
     */
    @Override
    public void growBody() {
        Logger.enter("growBodyOnU", "");
        Logger.exit("growBody", "");
        return;
    }
}
