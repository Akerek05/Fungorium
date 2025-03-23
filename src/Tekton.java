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
    /// Tekton törése
    /// Ekkor töröljük a tektonról a rovarokat, gombát, spórákat, fonalakat és fonalas szomszédokat
    /// És létrehoz egy új tektont

    public Tekton() {
        Logger.enter("Tekton ctor", "");
        Logger.exit("Tekton ctor", "");
    }
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
        Tekton newTekton = new Tekton();
        Logger.exit("breakTekton", "");
        return newTekton;
    }
    /// Egy rovar tektonra lépése és felvéttele az arraysOfInsectbe
    public void accept(Insect insect) {
        Logger.enter("accept", ""+insect);
        arrayOfInsect.add(insect);
        Logger.exit("accept", ""+insect);
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
    public void addString(Tekton t2) {
        Logger.enter("addString", ""+t2);
        ShroomString s1 = new ShroomString(this,t2);
        arrayOfString.add(s1);
        t2.arrayOfString.add(s1);
        if (s1.startTek.equals(this) ){
            stringNeighbours.add(s1.disTek);
            t2.stringNeighbours.add(s1.disTek);

        }
        else if (s1.disTek.equals(this)){
            stringNeighbours.add(s1.startTek);
            t2.stringNeighbours.add(s1.startTek);
        };


        Logger.exit("addString", ""+t2);
    }
    public void addSpore() {
        Logger.enter("addSpore", "");
        Random random = new Random();
        int type = random.nextInt(6);
        switch (type) {
            case 0 -> arrayOfSpore.add(new KillerSpore(this));
            case 1 -> arrayOfSpore.add(new NoCutSpore(this));
            case 2 -> arrayOfSpore.add(new DebuffSpore(this));
            case 3 -> arrayOfSpore.add(new ParalyzeSpore(this));
            case 4 -> arrayOfSpore.add(new BuffSpore(this));
            case 5 -> {arrayOfSpore.add(new Spore(this));}
        }
        Logger.exit("addSpore", "");
    }
    public void moveInsect(Insect insect, Tekton tekton) {
        Logger.enter("moveInsect", ""+insect+ ","+tekton);
        tekton.accept(insect);
        this.removeInsect(insect);
        Logger.exit("moveInsect", ""+insect+ ","+tekton);
    }
    public void removeInsect(Insect insect) {
        Logger.enter("removeInsect", ""+insect);
        arrayOfInsect.remove(insect);
        Logger.exit("removeInsect", ""+insect);
    }
    public void removeString(ShroomString string) {
        Logger.enter("removeString", ""+string);
        arrayOfString.remove(string);
        Logger.exit("removeString", ""+string);
    }
    public void removeBody(Mushroom mushroom) {
        Logger.enter("removeBody", ""+mushroom);
        arrayOfMushroom.remove(mushroom);
        Logger.exit("removeBody", ""+mushroom);
    }
    public void removeSpore(Spore spore) {
        Logger.enter("removeSpore", ""+spore);
        arrayOfSpore.remove(spore);
        Logger.exit("removeSpore", ""+spore);
    }
    public void addUpgradedBody() {
        Logger.enter("addUpgradedBody", "");
        arrayOfMushroom.add(new UpgradedMushroom(this));
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
    public Spore getSpore(){
        return arrayOfSpore.get(0);
    }
    public void setArrayOfSpore(Spore spore){
        arrayOfSpore.clear();
        arrayOfSpore.add(spore);
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
