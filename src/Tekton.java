import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A játéktér egyik mezője, amely rovarokat, gombákat, spórákat és fonalakat tárolhat.
 * Képes ezek kezelésére és szomszédos mezőkkel való kapcsolattartásra.
 */

public class Tekton implements TurnControl, Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public static int tektonCount = 0;
    protected List<ShroomString> arrayOfString = new ArrayList<>();
    protected List<Mushroom> arrayOfMushroom = new ArrayList<>();
    protected List<Insect> arrayOfInsect = new ArrayList<>();
    protected List<Spore> arrayOfSpore = new ArrayList<>();
    protected List<Tekton> neighbours = new ArrayList<>();
    protected List<Tekton> stringNeighbours = new ArrayList<>();

    /**
     * Alapértelmezett konstruktor új Tekton példányhoz.
     */
    public Tekton() {
        this.id = tektonCount++;
    }


    public static void resetTid() {
        tektonCount = 0;
    }

    /**
     * Beállítja a kettőt szomszédnak
     * @param tekton másik tekton
     */
    public void addNeighbour(Tekton tekton){
        this.neighbours.add(tekton);
        tekton.neighbours.add(this);
    }
    /**
     * Törli a jelenlegi Tekton tartalmát (rovarokat, gombát, spórákat, fonalakat),
     * majd létrehoz egy új, üres Tekton objektumot.
     *
     * @return az újonnan létrehozott Tekton példány
     * @param rnd Az új tekton értékét jelöli, (-1 esetben random)
     */
    public Tekton breakTekton(int rnd) {
        for (int i = arrayOfInsect.size() - 1; i >= 0; i--) {
            arrayOfInsect.get(i).die();
        }
        if (!arrayOfMushroom.isEmpty()) {
            arrayOfMushroom.get(0).die();
        }
        for (int i = arrayOfString.size() - 1; i >= 0; i--) {
            arrayOfString.get(i).die();
        }
        arrayOfSpore.clear();

        for (Tekton tekton : stringNeighbours) {
            tekton.stringNeighbours.remove(this);
        }

        stringNeighbours.clear();

        if(rnd == -1){
            Random rand = new Random();
            rnd = rand.nextInt(1,5);
        }

        Tekton newTekton = null;
        switch (rnd){
            case 1:
                newTekton = new Tekton();
                break;
            case 2:
                newTekton = new AllStringsLiveTekton();
                break;

            case 3:
                newTekton = new MultipleStringTekton();
                break;

            case 4:
                newTekton = new UnlivableTekton();
                break;
        }

        this.addNeighbour(newTekton);
        return newTekton;

    }

    /**
     * Rovar hozzáadása ehhez a Tektonhoz.
     *
     * @param insect A rovar objektum
     */
    public void accept(Insect insect) {
        arrayOfInsect.add(insect);
        insect.tekton = this;
    }

    /**
     * Gomba növesztése erre a Tektonra (csak ha még nincs rajta).
     */
    public void growBody(int pid) {
        if (arrayOfMushroom.isEmpty()) {
            arrayOfMushroom.add(new Mushroom(this, pid));
            return;
        }
        throw new IllegalArgumentException("");
    }

    /**
     * Gombafonal létrehozása két Tekton között, kapcsolódó szomszédság beállítása.
     *
     * @param t2 A másik Tekton, amelyhez a fonalat hozzákötjük
     */
    public void addString(Tekton t2, Mushroom m1) {
        // Ha már létezik
        for (ShroomString s : arrayOfString) {
            if ((s.startTek.equals(this) && s.disTek.equals(t2)) ||
                    (s.startTek.equals(t2) && s.disTek.equals(this))) {
                throw new IllegalArgumentException("");
            }
        }

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
     * Véletlenszerű típusú spóra generálása és hozzáadása ehhez a Tektonhoz.S
     */
    public void addSpore(int pid, int rnd, int calories) {
        if (rnd > 7 || rnd < 1) {
            Random random = new Random();
            rnd = random.nextInt(7) + 1;
        }
        switch (rnd) {
            case 1 -> arrayOfSpore.add(new Spore(this, pid, calories));
            case 2 -> arrayOfSpore.add(new BirthSpore(this, pid, calories));
            case 3 -> arrayOfSpore.add(new KillerSpore(this, pid, calories));
            case 4 -> arrayOfSpore.add(new NoCutSpore(this, pid, calories));
            case 5 -> arrayOfSpore.add(new DebuffSpore(this, pid, calories)) ;
            case 6 -> arrayOfSpore.add(new ParalyzeSpore(this, pid, calories));
            case 7 -> arrayOfSpore.add(new BuffSpore(this, pid, calories));
        }
    }

    /**
     * Egy rovar áthelyezése erről a Tektonról egy másikra.
     *
     * @param insect A mozgatandó rovar
     * @param tekton A cél Tekton
     */
    public void moveInsect(Insect insect, Tekton tekton) {
        if(this.stringNeighbours.contains(tekton) && tekton.stringNeighbours.contains(this)){
            tekton.accept(insect);
            this.removeInsect(insect);
            return;
        }
        throw new IllegalArgumentException("");
    }

    /** Rovar eltávolítása a Tektonról. */
    public void removeInsect(Insect insect) {
        arrayOfInsect.remove(insect);
    }

    /** Fonal eltávolítása a Tektonról. */
    public void removeString(ShroomString string) {
        arrayOfString.remove(string);
    }

    /** Gomba eltávolítása a Tektonról. */
    public void removeBody(Mushroom mushroom) {
        arrayOfMushroom.remove(mushroom);
    }

    /** Spóra eltávolítása a Tektonról. */
    public void removeSpore(Spore spore) {
        arrayOfSpore.remove(spore);
    }

    /**
     * Egy fejlesztett gomba hozzáadása a Tektonhoz, a meglévő gomba lecserélésével.
     */
    public void addUpgradedBody() {
        Mushroom mushroom = arrayOfMushroom.get(0);
        UpgradedMushroom upgradedMushroom = new UpgradedMushroom(this, mushroom.playerID, mushroom.id);
        upgradedMushroom.lifeTime = 200;
        upgradedMushroom.sporeSpawnTime = mushroom.sporeSpawnTime;
        upgradedMushroom.resources = mushroom.resources;
        arrayOfMushroom.add(upgradedMushroom);

        mushroom.die();
    }

    /**
     * Egy új rovarat hoz létre az adott tektonra
     * @param pid playerid
     */
    public void addInsect(int pid) {
        Insect insect = new Insect(this, pid);
        arrayOfInsect.add(insect);
    }

    /**
     * A Tekton "halála", minden rajta lévő objektum törlése.
     */
    public void die() {
        arrayOfString.clear();
        arrayOfMushroom.clear();
        arrayOfInsect.clear();
        arrayOfSpore.clear();
    }

    /**
     * A Tekton timeElapsed-je, meghivja a rajta levo fonalak gombak es rovarak timeelapsed-t,
     * ha a gomba vagy fonal lifeTimeja <= 0 akkor meghal.
     */
    public void timeElapsed() {
        for (int i = arrayOfString.size() - 1; i >= 0 ; i--) {
            ShroomString string = arrayOfString.get(i);
            string.timeElapsed();
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
    }

    public String toString(){
        String type = "Basic";

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
