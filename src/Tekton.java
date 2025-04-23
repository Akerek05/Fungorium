import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A játéktér egyik mezője, amely rovarokat, gombákat, spórákat és fonalakat tárolhat.
 * Képes ezek kezelésére és szomszédos mezőkkel való kapcsolattartásra.
 */
public class Tekton implements TurnControl {

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

    /**
     * Törli a jelenlegi Tekton tartalmát (rovarokat, gombát, spórákat, fonalakat),
     * majd létrehoz egy új, üres Tekton objektumot.
     *
     * @return az újonnan létrehozott Tekton példány
     * @param rnd Az új tekton értékét jelöli, (-1 esetben random)
     */
    public void breakTekton(int rnd) {
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
        stringNeighbours.clear();
        Tekton newTekton = new Tekton();

    }

    /**
     * Rovar hozzáadása ehhez a Tektonhoz.
     *
     * @param insect A rovar objektum
     */
    public void accept(Insect insect) {
        arrayOfInsect.add(insect);
    }

    /**
     * Gomba növesztése erre a Tektonra (csak ha még nincs rajta).
     */
    public void growBody() {
        if (arrayOfMushroom.isEmpty()) {
            //arrayOfMushroom.add(new Mushroom(this));
        }
    }

    /**
     * Gombafonal létrehozása két Tekton között, kapcsolódó szomszédság beállítása.
     *
     * @param t2 A másik Tekton, amelyhez a fonalat hozzákötjük
     */
    public void addString(Tekton t2) {
//        Logger.enter("addString", "" + t2);
//        ShroomString s1 = new ShroomString(this, t2);
//        arrayOfString.add(s1);
//        t2.arrayOfString.add(s1);
//
//        if (s1.startTek.equals(this)) {
//            stringNeighbours.add(s1.disTek);
//            t2.stringNeighbours.add(s1.disTek);
//        } else if (s1.disTek.equals(this)) {
//            stringNeighbours.add(s1.startTek);
//            t2.stringNeighbours.add(s1.startTek);
//        }
//        Logger.exit("addString", "" + t2);
    }

    /**
     * Véletlenszerű típusú spóra generálása és hozzáadása ehhez a Tektonhoz.
     */
    public void addSpore(int pid, int rnd) {
//        Logger.enter("addSpore", "");
//        Random random = new Random();
//        int type = random.nextInt(6);
//        switch (type) {
//            case 0 -> arrayOfSpore.add(new KillerSpore(this));
//            case 1 -> arrayOfSpore.add(new NoCutSpore(this));
//            case 2 -> arrayOfSpore.add(new DebuffSpore(this));
//            case 3 -> arrayOfSpore.add(new ParalyzeSpore(this));
//            case 4 -> arrayOfSpore.add(new BuffSpore(this));
//            case 5 -> arrayOfSpore.add(new Spore(this, 1, -1));
//        }
//        Logger.exit("addSpore", "");
    }

    /**
     * Egy rovar áthelyezése erről a Tektonról egy másikra.
     *
     * @param insect A mozgatandó rovar
     * @param tekton A cél Tekton
     */
    public void moveInsect(Insect insect, Tekton tekton) {
        tekton.accept(insect);
        this.removeInsect(insect);
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
        //arrayOfMushroom.add(new UpgradedMushroom(this));
        arrayOfMushroom.get(0).die();
    }

    /**
     * Egy új rovarat hoz létre az adott tektonra
     * @param pid playerid
     */
    public void addInsect(int pid) {}


    /** @return A Tektonon lévő rovarok listája */
    public List<Insect> getInsect() {
        return arrayOfInsect;
    }

    /** @return A Tektonon lévő gombák listája */
    public List<Mushroom> getMushroom() {
        return arrayOfMushroom;
    }

    /** @return A Tektonon lévő fonalak listája */
    public List<ShroomString> getShroomString() {
        return arrayOfString;
    }

    /** @return A Tekton szomszédos mezői */
    public List<Tekton> getNeighbours() {
        return neighbours;
    }

    /** @return A Tekton első spórája */
    public Spore getSpore() {
        return arrayOfSpore.get(0);
    }

    /** Beállítja az aktuális spórát (minden korábbit töröl). */
    public void setArrayOfSpore(Spore spore) {
        arrayOfSpore.clear();
        arrayOfSpore.add(spore);
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

    public void timeElapsed() {}

    public String toString() {
        String out = "Tekton";
        return out + id;
    }
}
