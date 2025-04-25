/**
 * Egy speciális Tekton típus, amely lehetővé teszi több fonal egyidejű kezelését.
 */
public class MultipleStringTekton extends Tekton {
    /**
     * Fonal hozzáadása, jelenleg üres implementációval.
     */
    // TODO
    public void addString() {}

    /**
     * Kiírja a tekton típusát, valamint szomszédjait
     * @return összefűzött szöveg
     */
    public String toString(){
        String type = "MultipleString";

        String  output= id+ ": ";
        output += "Neighbours: ";
        for(Tekton t : neighbours){
            output += t.id + ", ";
        }
        if(neighbours.isEmpty()) output += ", ";

        output += "StringNeighbours: ";
        for (Tekton t : stringNeighbours){
            output += t.id + ", ";
        }
        if(stringNeighbours.isEmpty()) output += ", ";

        output+= "Type: "+type;
        return output;
    };
}
