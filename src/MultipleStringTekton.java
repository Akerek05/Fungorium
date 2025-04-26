/**
 * Egy speciális Tekton típus, amely lehetővé teszi több fonal egyidejű kezelését.
 */
public class MultipleStringTekton extends Tekton {
    /**
     * Fonal hozzáadása, jelenleg üres implementációval.
     */
    public void addString(Tekton t2, Mushroom m1) {

        ShroomString s1 = new ShroomString(t2, this, m1);
        arrayOfString.add(s1);
        t2.arrayOfString.add(s1);

        // Csak ha még nem azok
        if(!this.stringNeighbours.contains(t2)){
            this.stringNeighbours.add(t2);
            t2.stringNeighbours.add(this);
        }
    }

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
