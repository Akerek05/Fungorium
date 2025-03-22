import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tekton implements TurnControl{

    protected List<ShroomString> arrayOfString = new ArrayList<>();
    protected List<Mushroom> arrayOfMushroom = new ArrayList<>();
    protected List<Insect> arrayOfInsect = new ArrayList<>();
    protected List<Spore> arrayOfSpore = new ArrayList<>();
    protected List<Tekton> neighbours = new ArrayList<Tekton>();
    protected List<Tekton> stringNeighbours= new ArrayList<Tekton>();
    /// Csak a loggerhez id
    protected String id;
    /// Tekton törése
    /// Ekkor töröljük a tektonról a rovarokat, gombát, spórákat, fonalakat és fonalas szomszédokat
    /// És létrehoz egy új tektont
    public Tekton breakTekton() {
        Logger.enter("breakTekton", "");
        /// Rovarok törlése
        for (int i = arrayOfInsect.size() - 1; i >= 0; i--)  {
            arrayOfInsect.get(i).die();
        }
        /// Gomba törlése
        if (!arrayOfMushroom.isEmpty()){
            arrayOfMushroom.get(0).die();
        }
        for (int i = arrayOfString.size() - 1; i >= 0; i--)  {
            arrayOfString.get(i).die();
        }
        /// Spóra törlése
        arrayOfSpore.clear();
        /// Fonalas szomszédok törlése
        stringNeighbours.clear();
        /// Új tekton létrehozása
        Logger.enter("Create", "Tekton");
        Tekton newTekton = new Tekton();
        Logger.exit("Create", "Tekton");
        Logger.exit("breakTekton", "");
        return newTekton;
    }
    /// Egy rovar tektonra lépése és felvéttele az arraysOfInsectbe
    public void accept(Insect insect) {
        Logger.enter("accept", insect.id);
        arrayOfInsect.add(insect);
        Logger.exit("accept", insect.id);
    }
    /// Gombatest növesztése a tektonra, csak egy gomba lehet egy tektonon
    public void growBody() {
        Logger.enter("growBody", "");
        if (arrayOfMushroom.isEmpty()) {
            arrayOfMushroom.add(new Mushroom(this));
        }
        Logger.exit("growBody", "");
    }
    /// Fonalhozzáadása a tektonhoz
    /// A
    public void addString(ShroomString shroomString) {
        Logger.enter("addString", "");
        arrayOfString.add(shroomString);
        if (shroomString.startTek.equals(this) ){
            stringNeighbours.add(shroomString.disTek);
        }
        else if (shroomString.disTek.equals(this)){
            stringNeighbours.add(shroomString.startTek);
        };


        Logger.exit("addString", "");
    }



    public void addSpore() {
        Logger.enter("addSpore", "");
        Random random = new Random();
        int type = random.nextInt(6);
        switch (type) {
            case 0 -> arrayOfSpore.add(new KillerSpore());
            case 1 -> arrayOfSpore.add(new NoCutSpore());
            case 2 -> arrayOfSpore.add(new DebuffSpore());
            case 3 -> arrayOfSpore.add(new ParalyzeSpore());
            case 4 -> arrayOfSpore.add(new BuffSpore());
            case 5 -> {arrayOfSpore.add(new Spore());}
        }
        Logger.exit("addSpore", "");
    }
    public void moveInsect(Insect insect, Tekton tekton) {
        Logger.enter("moveInsect", "");
        tekton.accept(insect);
        this.removeInsect(insect);
        Logger.exit("moveInsect", "");
    }
    public void removeInsect(Insect insect) {
        Logger.enter("removeInsect", "");
        arrayOfInsect.remove(insect);
        Logger.exit("removeInsect", "");
    }
    public void removeString(ShroomString string) {
        Logger.enter("removeString", "");
        arrayOfString.remove(string);
        Logger.exit("removeString", "");
    }
    public void removeBody(Mushroom mushroom) {
        Logger.enter("removeBody", "");
        arrayOfMushroom.remove(mushroom);
        Logger.exit("removeBody", "");
    }
    public void removeSpore(Spore spore) {
        Logger.enter("removeSpore", "");
        arrayOfSpore.remove(spore);
        Logger.exit("removeSpore", "");
    }
    public void addUpgradedBody() {
        Logger.enter("addUpgradedBody", "");
        Logger.enter("create","g2: UpgradedMushroom");
        arrayOfMushroom.add(new UpgradedMushroom(this));
        Logger.exit("create", "g2: UpgradedMushroom");
        arrayOfMushroom.get(0).die();
        Logger.exit("addUpgradedBody", "");
    }

    public List<Insect> getInsect(){
        return  arrayOfInsect;
    }
    public List<Mushroom> getMushroom(){
        return arrayOfMushroom;
    }
    public List<ShroomString> getShroomString(){
        return arrayOfString;
    }
    public List<Tekton> getNeighbours(){
        return neighbours;
    }

    @Override
    public void die(){
        Logger.enter("die", "");
        arrayOfString.clear();
        arrayOfMushroom.clear();
        arrayOfInsect.clear();
        arrayOfSpore.clear();
        Logger.exit("die", "");
    }
    @Override
    public void timeElapsed() {}
}
