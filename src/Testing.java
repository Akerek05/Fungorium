import com.sun.jdi.connect.Transport;

public class Testing {
    static void tektonBreak(){
        System.out.println("tektonBreak teszt.");
        //initialization
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        Mushroom m1 = new Mushroom(map.tektons.get(0));
        Insect i1 = new Insect(map.tektons.get(0));
        ShroomString s1= new ShroomString(map.tektons.get(0), map.tektons.get(1));

        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfMushroom.add(m1);
        map.tektons.get(0).arrayOfString.add(s1);
        map.tektons.get(1).arrayOfString.add(s1);
        map.tektons.get(1).stringNeighbours.add(map.tektons.get(0));
        map.tektons.get(0).stringNeighbours.add(map.tektons.get(1));

        //calling function
        map.tektons.get(0).breakTekton();
        System.out.println();
    }

    static void spreadSpore(){
        System.out.println("spreadSpore teszt.");
        //initialization
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        Mushroom m1 = new Mushroom(map.tektons.get(0));
        map.tektons.get(0).arrayOfMushroom.add(m1);

        //calling function
        m1.spreadSpore(map.tektons.get(1));
        System.out.println();
    }

    static void insectMove(){
        System.out.println("insectMove teszt.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        i1.moveToTekton(map.tektons.get(1));
        System.out.println();
    }

    static void stringCut(){
        System.out.println("stringCut teszt.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        ShroomString s1 = new ShroomString(map.tektons.get(0), map.tektons.get(1));
        map.tektons.get(0).addString(map.tektons.get(1));
        i1.cutString(s1);
        System.out.println();
    }

    static void upgradeMushroom(){
        System.out.println("upgradeMushroom teszt.");
        Map map = new Map();
        map.addTekton();
        Mushroom m1 = new Mushroom(map.tektons.get(0));
        map.tektons.get(0).arrayOfMushroom.add(m1);
        m1.upgradeMushroom();
        System.out.println();
    }

    static void defaultSporeConsume(){
        System.out.println("defaultSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        Spore s1 = new Spore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        map.tektons.get(0).arrayOfInsect.get(0).eatSpore(s1);
        System.out.println();
    }

    static void killerSporeConsume(){
        System.out.println("killerSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        KillerSpore s1 = new KillerSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        map.tektons.get(0).arrayOfInsect.get(0).eatSpore(s1);
        System.out.println();
    }

    static void paralyzeSporeConsume(){
        System.out.println("paralyzeSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        ParalyzeSpore s1 = new ParalyzeSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        map.tektons.get(0).arrayOfInsect.get(0).eatSpore(s1);
        System.out.println();
    }

    static void noCutSporeConsume(){
        System.out.println("noCutSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        NoCutSpore s1 = new NoCutSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        map.tektons.get(0).arrayOfInsect.get(0).eatSpore(s1);
        System.out.println();
    }

    static void buffSporeConsume(){
        System.out.println("buffSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        BuffSpore s1 = new BuffSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        map.tektons.get(0).arrayOfInsect.get(0).eatSpore(s1);
        System.out.println();
    }

    static void debuffSporeConsume(){
        System.out.println("debuffSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        DebuffSpore s1 = new DebuffSpore(map.tektons.get(0));
        map.tektons.get(0).arrayOfInsect.add(i1);
        map.tektons.get(0).arrayOfSpore.add(s1);
        map.tektons.get(0).arrayOfInsect.get(0).eatSpore(s1);
        System.out.println();
    }
    //Spóra szórás fejlesztett gombán
    static void SpreadSporeWithUpgraded(){
        System.out.println("SpreadSporeWithUpgraded teszt.");
        //Initialzation
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
    //grow string
    static void GrowStringTest(){
        System.out.println("GrowString teszt.");
        Map map = new Map();
        map.tektons.add(new Tekton());
        map.tektons.add(new Tekton());
        map.tektons.get(0).arrayOfMushroom.add(new Mushroom(map.tektons.get(0)));
        map.tektons.get(0).arrayOfMushroom.get(0).growString(map.tektons.get(0),map.tektons.get(1));
        System.out.println();
    }

    //grow mushroom
    static void GrowMushroom(){
        System.out.println("GrowMushroom teszt.");
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
    //grow string on multistring
    static void GrowStringOnMultipleTekton(){
        System.out.println("GrowStringOnMultiple teszt.");
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

    //grow mushroom on lifeless tekton
    static void GrowMushroomOnUnlivableTekton(){
        System.out.println("GrowMushroomOnLifelessTekton teszt.");
        Map map = new Map();
        Tekton t1 = new UnlivableTekton();
        Tekton t2 = new Tekton();
        map.tektons.add(t1);
        map.tektons.add(t2);
        ShroomString s1 = new ShroomString(t1,t2);
        t1.arrayOfString.add(s1);
        t1.growBody();
        System.out.println(t1.getMushroom());
        System.out.println();
    }

    public static void main(String[] args){
        tektonBreak();
        insectMove();
        stringCut();
        spreadSpore();
        upgradeMushroom();
        defaultSporeConsume();
        killerSporeConsume();
        paralyzeSporeConsume();
        noCutSporeConsume();
        buffSporeConsume();
        debuffSporeConsume();
        SpreadSporeWithUpgraded();
        GrowMushroomOnUnlivableTekton();
        GrowMushroom();
        GrowStringTest();
        GrowStringOnMultipleTekton();
    }
}
