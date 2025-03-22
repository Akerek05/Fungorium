public class Testing {
    static void TektonBreak(){
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

    }
    static void spreadSpore(){
        //initialization
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        map.tektons.get(0).growBody();
        //calling function
        Mushroom mushroom = map.tektons.get(0).getMushroom().get(0);
        mushroom.spreadSpore(map.tektons.get(1));
    }



    static void insectMove(){
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        map.tektons.get(0).accept(new Insect(map.tektons.get(0)));
        map.tektons.get(0).getInsect().get(0).moveToTekton(map.tektons.get(1));
    }

    static void stringCut(){
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        map.tektons.get(0).addString(new ShroomString(map.tektons.get(0),map.tektons.get(1)));
        map.tektons.get(0).accept(new Insect(map.tektons.get(0)));
        map.tektons.get(0).getInsect().get(0).cutString(map.tektons.get(0).getShroomString().get(0));
    }

    static void upgradeMushroom(){
        Map map = new Map();
        map.addTekton();
        map.tektons.get(0).growBody();
        map.tektons.get(0).getMushroom().get(0).upgradeMushroom();
    }


    public static void main(String[] args){

        //TektonBreak();
        //insectMove();
        //stringCut();
        //spreadSpore();
        //upgradeMushroom();
    }

}
