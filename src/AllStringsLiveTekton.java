public class AllStringsLiveTekton extends Tekton {
    /**
     * Idő teléséért felelős függvény
     */
    public void timeElapsed(){
        for (int i = arrayOfString.size() - 1; i >= 0 ; i--) {
            ShroomString string = arrayOfString.get(i);
            string.getDecayDamage();
            if (string.lifeTime <= 0) {
                string.die();
            }
        }
        for (int i = arrayOfMushroom.size() - 1; i >= 0 ; i-- ) {
            Mushroom mushroom = arrayOfMushroom.get(i);
            mushroom.timeElapsed();
            if (mushroom.lifeTime <= 0) {
                mushroom.die();
            }
        }
        for (Insect insect : arrayOfInsect){
            insect.timeElapsed();
        }
    };

    /**
     * Kiírja a tekton típusát, valamint szomszédjait
     * @return összefűzött szöveg
     */
    public String toString(){
        String type = "AllStringsLive";

        String  output= id+ ":";
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
