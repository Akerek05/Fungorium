import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A játékban használt térképet reprezentáló osztály,
 * amely Tektonokat tárol és szomszédsági kapcsolatokat kezel.
 */
public class Map {
    public List<ShroomString> shroomStrings = new ArrayList<ShroomString>();
    public List<Mushroom> mushrooms = new ArrayList<Mushroom>();
    public List<Insect> insects = new ArrayList<Insect>();
    public List<Spore> spores = new ArrayList<Spore>();
    public List<Tekton> tektons = new ArrayList<Tekton>();
    public List<Integer> scores = new ArrayList<Integer>();


    /**
     * Új Tekton hozzáadása a térképhez, és szomszédság frissítése.
     */
    /*public void addTekton() {
        tektons.add(new Tekton());
        if (tektons.size() != 1) {
            addNeighbours();
        }
    }*/

    /**
     * Frissíti a szomszédsági kapcsolatokat minden Tekton között.
     */
    private void addNeighbours() {
        for (Tekton tekton : tektons) {
            for (Tekton neighbour : tektons) {
                if (tekton != neighbour) {
                    tekton.neighbours.add(neighbour);
                }
            }
        }
    }

    private Tekton getTekton(int tektonID){
        for (Tekton tekton : tektons) {
            if (tekton.id == tektonID) {
                return tekton;
            }
        }
        return null;
    }

    private Mushroom getMushroom(int mushroomID){
        for (Mushroom mushroom : mushrooms) {
            if (mushroom.id == mushroomID) {
                return mushroom;
            }
        }
        return null;
    }

    private void endGame(){
        List<Integer> order = new ArrayList<Integer>(scores);

        scores.sort(Collections.reverseOrder());
        for (int i = 0; i < scores.size(); i++) {
            int value = scores.get(i);
            int playerId = order.indexOf(value); // csak az első előfordulást adja vissza
            System.out.println((i + 1) + ". hely: " + playerId + "|Pontszám: " + value);
        }
    }

    public void command(String inputCommand) {
        if (inputCommand == null || inputCommand.isEmpty())
            return; //nem megyünk el a switch case-ig, csak akkor, ha a beolvasott sor nem üres.

        String[] command = inputCommand.trim().split("\\s+"); //szóközöknél eldarabolja a stringet - több szóköz esetén is jól működik.
            switch (command[0].toUpperCase()) {
                case "LOAD":
                    System.out.println("LOAD");
                    break;

                case "SAVE":
                    System.out.println("SAVE");
                    break;

                case "CREATE":
                    switch (command[1].toUpperCase()) {
                        case "INSECT":
                            if(command.length > 6) {
                                int playerID = Integer.parseInt(command[2]);
                                int tektonID = Integer.parseInt(command[3]);
                                int actionPoints = Integer.parseInt(command[4]);
                                int resources = Integer.parseInt(command[5]);
                                int buffTimer = Integer.parseInt(command[6]);
                                String effect = command[7].toUpperCase();
                                Effect effectstatus = Effect.valueOf(effect);
                                Insect i1 = new Insect(playerID, this.getTekton(tektonID), actionPoints, resources, buffTimer, effectstatus);
                                this.insects.add(i1);
                                this.getTekton(tektonID).accept(i1);
                                System.out.println("INSECT CREATED");
                            }
                            else
                                System.out.println("Kevés paraméter!");
                            break;
                        case "MUSHROOM":
                            if(command.length > 5) {
                                int playerID = Integer.parseInt(command[2]);
                                int tektonID = Integer.parseInt(command[3]);
                                int sporeSpawnTimer = Integer.parseInt(command[4]);
                                int lifeTime = Integer.parseInt(command[5]);
                                int resources = Integer.parseInt(command[6]);
                                Mushroom m1 = new Mushroom(playerID, this.getTekton(tektonID),sporeSpawnTimer, lifeTime, resources);
                                this.mushrooms.add(m1);
                                System.out.println("MUSHROOM CREATED");
                            }
                            else
                                System.out.println("Kevés paraméter!");
                            break;
                        case "STRING":
                            if (command.length > 7){
                                int mushroomID = Integer.parseInt(command[2]);
                                int startTektonID = Integer.parseInt(command[3]);
                                int endTektonID = Integer.parseInt(command[4]);
                                boolean growing = Boolean.parseBoolean(command[5]);
                                int lifetime = Integer.parseInt(command[6]);
                                boolean isCut = Boolean.parseBoolean(command[7]);
                                boolean isConnected = Boolean.parseBoolean(command[8]);
                                ShroomString s1 = new ShroomString(this.getMushroom(mushroomID),this.getTekton(startTektonID),this.getTekton(endTektonID),growing,lifetime,isCut,isConnected);
                                this.shroomStrings.add(s1);
                                System.out.println("STRING CREATED");
                            }
                            else
                                System.out.println("Kevés paraméter!");
                            break;
                        case "SPORE":
                            if(command.length>3) {
                                switch (command[2].toUpperCase()) {
                                    case "BASIC":
                                        int tektonID = Integer.parseInt(command[4]);
                                        int playerID = Integer.parseInt(command[3]);
                                        int rand = Integer.parseInt(command[5]);
                                        Spore s1 = new Spore(this.getTekton(tektonID),playerID,rand);
                                        this.spores.add(s1);
                                        System.out.println("BASIC SPORE CREATED");
                                        break;
                                    case "BUFF":
                                        break;
                                    case "DEBUFF":
                                        break;
                                    case "BIRTH":
                                        break;
                                    case "KILLER":
                                        break;
                                    case "NOCUT":
                                        break;
                                    case "PARALYZE":
                                        break;
                                }
                                System.out.println("SPORE CREATED");
                            }
                            else
                                System.out.println("Kevés paraméter!");
                            break;
                        case "TEKTON":
                            if (command.length > 2){
                                switch (command[2].toUpperCase()) {
                                    case "BASIC":
                                        Tekton t1 = new Tekton();
                                        this.tektons.add(t1);
                                        System.out.println("TEKTON CREATED");
                                        break;
                                    case "ALLSTRINGSLIVE":
                                        break;
                                    case "MULTIPLESTRING":
                                        break;
                                    case "STRINGCUTTER":
                                        break;
                                    case "UNLIVABLE":
                                        break;
                                    default:
                                        System.out.println("Imseretlen tektontípus.");
                                        break;
                                }
                            }
                            else
                                System.out.println("Kevés paraméter!");
                            break;
                        default:
                            System.out.println("Error! Could not create <" + command[1] + ">:(<PlayerID: " + command[2] + ">), at Tekton:<" + command[3] + ">");
                            break;
                    }
                    break;
                case "STATUS":
                    System.out.println("Tektonok: ");
                    for (Tekton tekton : tektons) {
                        System.out.println("\t"+tekton.toString());
                    }
                    System.out.println("Rovarok: ");
                    for (Insect insect : insects) {
                        System.out.println("\t"+insect.toString());
                    }
                    System.out.println("Mushrooms: ");
                    for (Mushroom mushroom : mushrooms) {
                        System.out.println("\t"+mushroom.toString());
                    }
                    System.out.println("ShroomsStrings: ");
                    for (ShroomString shroomString : shroomStrings) {
                        System.out.println("\t"+shroomString.toString());
                    }
                    System.out.println("Spores: ");
                    for (Spore spore : spores) {
                        System.out.println("\t"+spore.toString());
                    }
                    break;

                case "EAT":
                    System.out.println("EAT");
                    break;

                case "CUT":
                    System.out.println("CUT");
                    break;

                case "GROW":
                    System.out.println("GROW");
                    break;

                case "SPREAD":
                    System.out.println("SPREAD");
                    break;

                case "ENDGAME":
                    System.out.println("ENDGAME");
                    this.endGame();
                    break;

                case "BREAKTEKTON":
                    System.out.println("BREAKTEKTON");
                    break;

                case "UPGRADE":
                    System.out.println("UPGRADE");
                    break;

                case "TIMEELAPSED":
                    System.out.println("TIMEELAPSED");
                    break;

                case "ADDNEIGHBOURS":
                    System.out.println("ADDNEIGHBOURS");
                    break;

                default:
                    System.out.println("Ismeretlen parancs: " + command[0]);
            }
    }
}
