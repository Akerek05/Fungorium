/**
 * Egy speciális Tekton típus, amely lehetővé teszi több fonal egyidejű kezelését.
 */
public class MultipleStringTekton extends Tekton {
    /**
     * Fonal hozzáadása, jelenleg üres implementációval.
     */
    public void addString() {}

    /**
     * Kiírja a tekton típusát, valamint szomszédjait
     * @return összefűzött szöveg
     */
    public String toString(){return "MultipleStringTekton";};

    /**
     * A tekton halálát valósítja meg
     */
    public void die(){};
}
