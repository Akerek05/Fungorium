import com.sun.jdi.connect.Transport;

public class Testing {
    static void tektonBreak(){
        System.out.println("tektonBreak teszt.");
        //initialization
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        map.tektons.get(0).growBody();
        map.tektons.get(0).accept(new Insect(map.tektons.get(0)));
        map.tektons.get(0).accept(new Insect(map.tektons.get(0)));
        map.tektons.get(0).addSpore();
        map.tektons.get(0).addString(new ShroomString(map.tektons.get(0),map.tektons.get(1)));
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
        map.tektons.get(0).growBody();
        //calling function
        Mushroom mushroom = map.tektons.get(0).getMushroom().get(0);
        mushroom.spreadSpore(map.tektons.get(1));
        System.out.println();
    }

    static void insectMove(){
        System.out.println("insectMove teszt.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        map.tektons.get(0).accept(new Insect(map.tektons.get(0)));
        map.tektons.get(0).getInsect().get(0).moveToTekton(map.tektons.get(1));
        System.out.println();
    }

    static void stringCut(){
        System.out.println("stringCut teszt.");
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        map.tektons.get(0).addString(new ShroomString(map.tektons.get(0),map.tektons.get(1)));
        map.tektons.get(0).accept(new Insect(map.tektons.get(0)));
        map.tektons.get(0).getInsect().get(0).cutString(map.tektons.get(0).getShroomString().get(0));
        System.out.println();
    }

    static void upgradeMushroom(){
        System.out.println("upgradeMushroom teszt.");
        Map map = new Map();
        map.addTekton();
        map.tektons.get(0).growBody();
        map.tektons.get(0).getMushroom().get(0).upgradeMushroom();
        System.out.println();
    }

    static void killerSporeConsume(){
        System.out.println("killerSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        map.tektons.get(0).accept(i1);
        map.tektons.get(0).setArrayOfSpore(new KillerSpore(map.tektons.get(0)));
        map.tektons.get(0).getSpore().consumed(i1);
        System.out.println();
    }

    static void paralyzeSporeConsume(){
        System.out.println("paralyzeSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        map.tektons.get(0).accept(i1);
        map.tektons.get(0).setArrayOfSpore(new ParalyzeSpore(map.tektons.get(0)));
        map.tektons.get(0).getSpore().consumed(i1);
        System.out.println();
    }

    static void noCutSporeConsume(){
        System.out.println("noCutSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        map.tektons.get(0).accept(i1);
        map.tektons.get(0).setArrayOfSpore(new NoCutSpore(map.tektons.get(0)));
        map.tektons.get(0).getSpore().consumed(i1);
        System.out.println();
    }

    static void buffSporeConsume(){
        System.out.println("buffSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        map.tektons.get(0).accept(i1);
        map.tektons.get(0).setArrayOfSpore(new BuffSpore(map.tektons.get(0)));
        map.tektons.get(0).getSpore().consumed(i1);
        System.out.println();
    }

    static void debuffSporeConsume(){
        System.out.println("debuffSporeConsume teszt.");
        Map map = new Map();
        map.addTekton();
        Insect i1 = new Insect(map.tektons.get(0));
        map.tektons.get(0).accept(i1);
        map.tektons.get(0).setArrayOfSpore(new DebuffSpore(map.tektons.get(0)));
        map.tektons.get(0).getSpore().consumed(i1);
        System.out.println();
    }

    public static void main(String[] args){
        //tektonBreak();
        //insectMove();
        //stringCut();
        //spreadSpore();
        //upgradeMushroom();
        //killerSporeConsume();
        //paralyzeSporeConsume();
        noCutSporeConsume();
        debuffSporeConsume();
        buffSporeConsume();
    }
}
