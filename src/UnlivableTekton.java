/**
 * Egy speciális Tekton típus, amelyen nem lehet gombát növeszteni.
 */
public class UnlivableTekton extends Tekton {

    /**
     * Gomba növesztésének tiltása ezen a mezőn.
     */
    public void growBody() {
        throw new IllegalArgumentException("");
    }

    public String toString(){
        String type = "Unlivable";

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
