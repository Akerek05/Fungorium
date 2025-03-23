import java.util.Scanner;

/**
 * Tesztosztály a játék fő funkcióinak ellenőrzésére manuálisan és logikai lépések követésével.
 */
public class Testing {

    /**
     * Teszteli a Tekton törését és a hozzá kapcsolódó elemek (rovar, gomba, fonal) eltávolítását.
     */
    static void tektonBreak() {
        System.out.println("tektonBreak test.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        Mushroom m1 = new Mushroom(map.tektons.get(0));
        Insect i1 = new Insect(map.tektons.get(0));
        ShroomString s1 = new ShroomString(map.tektons.get(0), map.tektons.get(1));

        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfMushroom.add(m1);
        map.tektons.get(0).arrayOfString.add(s1);
        map.tektons.get(1).arrayOfString.add(s1);
        map.tektons.get(1).stringNeighbours.add(map.tektons.get(0));
        map.tektons.get(0).stringNeighbours.add(map.tektons.get(1));

        map.tektons.get(0).breakTekton();
        System.out.println();
    }

    /**
     * Teszteli a gomba spóra szórását szomszédos Tektonra.
     */
    static void spreadSpore() {
        System.out.println("spreadSpore test.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        Mushroom m1 = new Mushroom(map.tektons.get(0));
        map.tektons.get(0).arrayOfMushroom.add(m1);
        m1.spreadSpore(map.tektons.get(1));
        System.out.println();
    }

    /**
     * Teszteli egy rovar mozgatását egyik Tektonról a másikra.
     */
    static void insectMove() {
        System.out.println("insectMove test.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        i1.moveToTekton(map.tektons.get(1));
        System.out.println();
    }

    /**
     * Teszteli, hogy a rovar képes-e fonalat elvágni.
     */
    static void stringCut() {
        System.out.println("stringCut test.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        ShroomString s1 = new ShroomString(map.tektons.get(0), map.tektons.get(1));
        map.tektons.get(0).addString(map.tektons.get(1));
        i1.cutString(s1);
        System.out.println();
    }

    /**
     * Teszteli a gomba fejlesztését UpgradedMushroom-ra.
     */
    static void upgradeMushroom() {
        System.out.println("upgradeMushroom test.");
        Map map = new Map();
        map.addTekton();
        Mushroom m1 = new Mushroom(map.tektons.get(0));
        map.tektons.get(0).arrayOfMushroom.add(m1);
        m1.upgradeMushroom();
        System.out.println();
    }

    /**
     * Teszteli az alapértelmezett Spore elfogyasztásának hatását.
     */
    static void defaultSporeConsume() {
        System.out.println("defaultSporeConsume test.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        Spore s1 = new Spore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        i1.eatSpore(s1);
        System.out.println();
    }

    /**
     * Teszteli a KillerSpore elfogyasztásának hatását (rovar halála).
     */
    static void killerSporeConsume() {
        System.out.println("killerSporeConsume test.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        KillerSpore s1 = new KillerSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        i1.eatSpore(s1);
        System.out.println();
    }

    /**
     * Teszteli a ParalyzeSpore bénító hatását.
     */
    static void paralyzeSporeConsume() {
        System.out.println("paralyzeSporeConsume test.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        ParalyzeSpore s1 = new ParalyzeSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        i1.eatSpore(s1);
        System.out.println();
    }

    /**
     * Teszteli a NoCutSpore hatását: vágás tiltása.
     */
    static void noCutSporeConsume() {
        System.out.println("noCutSporeConsume test.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        NoCutSpore s1 = new NoCutSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        i1.eatSpore(s1);
        System.out.println();
    }

    /**
     * Teszteli a BuffSpore hatását: akciópont növelése.
     */
    static void buffSporeConsume() {
        System.out.println("buffSporeConsume test.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        BuffSpore s1 = new BuffSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        i1.eatSpore(s1);
        System.out.println();
    }

    /**
     * Teszteli a DebuffSpore hatását: akciópont csökkentése.
     */
    static void debuffSporeConsume() {
        System.out.println("debuffSporeConsume test.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        DebuffSpore s1 = new DebuffSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        i1.eatSpore(s1);
        System.out.println();
    }

    /**
     * Teszteli az UpgradedMushroom BFS-alapú spóra szórását.
     */
    static void SpreadSporeWithUpgraded() {
        System.out.println("SpreadSporeWithUpgraded test.");
        Map map = new Map();
        map.tektons.add(new Tekton());
        map.tektons.add(new Tekton());
        map.tektons.add(new Tekton());
        map.tektons.get(0).neighbours.add(map.tektons.get(1));
        map.tektons.get(1).neighbours.add(map.tektons.get(0));
        map.tektons.get(1).neighbours.add(map.tektons.get(2));
        map.tektons.get(2).neighbours.add(map.tektons.get(1));
        map.tektons.get(0).arrayOfMushroom.add(new UpgradedMushroom(map.tektons.get(0)));
        map.tektons.get(0).arrayOfMushroom.get(0).spreadSpore(map.tektons.get(2));
        System.out.println();
    }

    /**
     * Teszteli, hogy fonal végén gomba nőhet-e.
     */
    static void GrowMushroom() {
        System.out.println("GrowMushroom test.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        ShroomString s1 = new ShroomString(map.tektons.get(0), map.tektons.get(1));
        map.tektons.get(1).stringNeighbours.add(map.tektons.get(0));
        map.tektons.get(0).stringNeighbours.add(map.tektons.get(1));
        map.tektons.get(0).arrayOfString.add(s1);
        map.tektons.get(1).arrayOfString.add(s1);
        map.tektons.get(0).growBody();
        System.out.println();
    }

    /**
     * Teszt: fonal növesztése MultipleStringTektonra.
     */
    static void GrowStringOnMultipleTekton() {
        System.out.println("GrowStringOnMultiple test.");
        Map map = new Map();
        Tekton t1 = new Tekton();
        Tekton t2 = new MultipleStringTekton();
        t1.neighbours.add(t1);
        t2.neighbours.add(t2);
        Mushroom m1 = new Mushroom(t1);
        t1.arrayOfMushroom.add(m1);
        m1.growString(t1, t2);
        System.out.println();
    }

    /**
     * Teszt: nem élhető Tektonon nőhet-e gomba.
     */
    static void GrowMushroomOnUnlivableTekton() {
        System.out.println("GrowMushroomOnLifelessTekton test.");
        Map map = new Map();
        Tekton t1 = new UnlivableTekton();
        Tekton t2 = new Tekton();
        map.tektons.add(t1);
        map.tektons.add(t2);
        ShroomString s1 = new ShroomString(t1, t2);
        t1.arrayOfString.add(s1);
        t1.growBody(); // Nem szabad történnie semminek
        System.out.println(t1.getMushroom());
        System.out.println();
    }

    /**
     * Teszt: gombafonal növesztése Mushroom által.
     */
    static void GrowStringTest() {
        System.out.println("GrowString test.");
        Map map = new Map();
        map.tektons.add(new Tekton());
        map.tektons.add(new Tekton());
        map.tektons.get(0).arrayOfMushroom.add(new Mushroom(map.tektons.get(0)));
        map.tektons.get(0).arrayOfMushroom.get(0).growString(map.tektons.get(0), map.tektons.get(1));
        System.out.println();
    }

    /**
     * A tesztprogram belépési pontja.
     */
    public static void main(String[] args) {
        boolean run = true;
        Scanner sc = new Scanner(System.in);
        while(run) {
            System.out.println("Choose a testing mode:" +
                    "\n(1) TektonBreak test \n(2) InsectMove test \n(3) StringCut test \n(4) SpreadSpore \n(5) UpgradeMushroom test" +
                    "\n(6) DefaultSporeConsume test \n(7) KillerSporeConsume test \n(8) ParalyzeSporeConsume test \n(9) NoCutSporeConsume test" +
                    "\n(10) BuffSporeConsume test \n(11) DebuffSporeConsume test \n(12) SpreadSporeWithUpgrade test \n(13) GrowMushroomOnUnlivableTekton test" +
                    "\n(14) GrowMushroom test \n(15) GrowString test \n(16) GrowStringOnMultipleTekton test \n(0) Exit");
            String input = sc.nextLine();
            switch (input) {
                case "1" :
                    tektonBreak();
                    break;
                case "2" :
                    insectMove();
                    break;
                case "3" :
                    stringCut();
                    break;
                case "4" :
                    spreadSpore();
                    break;
                case "5" :
                    upgradeMushroom();
                    break;
                case "6" :
                    defaultSporeConsume();
                    break;
                case "7":
                    killerSporeConsume();
                    break;
                case "8" :
                    paralyzeSporeConsume();
                    break;
                case "9" :
                    noCutSporeConsume();
                    break;
                case "10":
                    buffSporeConsume();
                    break;
                case "11" :
                    debuffSporeConsume();
                    break;
                case "12" :
                    SpreadSporeWithUpgraded();
                    break;
                case "13" :
                    GrowMushroomOnUnlivableTekton();
                    break;
                case "14" :
                    GrowMushroom();
                    break;
                case "15" :
                    GrowStringTest();
                    break;
                case "16" :
                    GrowStringOnMultipleTekton();
                    break;
                case "0" :
                    run = false;
                    break;
                default :
                    break;

            }
        }
    }
}