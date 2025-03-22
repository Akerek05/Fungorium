public class Testing {
    static void TektonBreak(){
        //initialization
        Map map = new Map();
        map.addTekton();
        map.addTekton();
        map.tektons.get(0).growBody();
        map.tektons.get(0).accept(new Insect(map.tektons.get(0)));
        map.tektons.get(0).addSpore();
        map.tektons.get(0).addString(new ShroomString(map.tektons.get(0),map.tektons.get(1)));
        //calling function
        map.tektons.get(0).breakTekton();

    }
    public static void main(String[] args){
        TektonBreak();
    }
}
