import jdk.jshell.ImportSnippet;

import java.sql.Array;
import java.util.*;

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

    private void update() {
        for (Tekton tekton : tektons) {
            for (Insect insect : tekton.arrayOfInsect) {
                if (!containsInsectById(insects, insect.id)) {
                    insects.add(insect);
                }
            }

            for (Spore spore : tekton.arrayOfSpore) {
                if (!containsSporeById(spores, spore.id)) {
                    spores.add(spore);
                }
            }

            for (Mushroom mushroom : tekton.arrayOfMushroom) {
                if (!containsMushroomById(mushrooms, mushroom.id)) {
                    mushrooms.add(mushroom);
                }
            }

            for (ShroomString shroomString : tekton.arrayOfString) {
                if (!containsShroomStringById(shroomStrings, shroomString.id)) {
                    shroomStrings.add(shroomString);
                }
            }
        }
    }
    private boolean containsInsectById(List<Insect> list, int id) {
        for (Insect i : list) {
            if (i.id == id) return true;
        }
        return false;
    }

    private boolean containsSporeById(List<Spore> list, int id) {
        for (Spore s : list) {
            if (s.id == id) return true;
        }
        return false;
    }

    private boolean containsMushroomById(List<Mushroom> list, int id) {
        for (Mushroom m : list) {
            if (m.id == id) return true;
        }
        return false;
    }

    private boolean containsShroomStringById(List<ShroomString> list, int id) {
        for (ShroomString s : list) {
            if (s.id == id) return true;
        }
        return false;
    }



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

    private Spore getSpore(int sporeID){
        for (Spore spore : spores) {
            if (spore.id == sporeID) {
                return spore;
            }
        }
        return null;
    }

    private Insect getInsect(int insectID){
        for (Insect insect : insects) {
            if (insect.id == insectID) {
                return insect;
            }
        }
        return null;
    }

    private ShroomString getShroomString(int shroomStringID){
        for (ShroomString shroomString : shroomStrings) {
            if (shroomString.id == shroomStringID) {
                return shroomString;
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
    public Integer tryParseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public Effect tryParseEffect(String text) {
        try {
            return Effect.valueOf(text);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public boolean tryParseBoolean(String text) {
        try {
            return Boolean.parseBoolean(text);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    public void addScore(int playerID, int score){
        scores.add(playerID,score);
    }
    public boolean isInTektons (int id) {
        for (Tekton tekton : tektons) {
            if (tekton.id == id) {
                return true;
            }
        }
        return false;
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
                    if(command.length == 1){
                        System.out.println("Too few arguments");
                        break;
                    };
                    String errorString = "Error! Could not create ";
                    switch (command[1].toUpperCase()) {
                        case "INSECT":
                            if(command.length == 8) {
                                Integer playerID = tryParseInt(command[2]);
                                Integer tektonID = tryParseInt(command[3]);
                                Integer actionPoints = tryParseInt(command[4]);
                                Integer resources = tryParseInt(command[5]);
                                Integer buffTimer = tryParseInt(command[6]);
                                String effect = command[7].toUpperCase();
                                Effect effectstatus = tryParseEffect(effect);
                                if (playerID != null && tektonID != null && actionPoints != null
                                        && resources != null && buffTimer != null && effectstatus != null && isInTektons(tektonID)) {
                                    Insect i1 = new Insect(playerID, this.getTekton(tektonID), actionPoints, resources, buffTimer, effectstatus);
                                    this.getTekton(tektonID).accept(i1);
                                    update();
                                } else {
                                    System.out.println(errorString + "Insect");
                                }
                            }
                            else
                                System.out.println(errorString + "Insect");
                            break;
                        case "MUSHROOM":
                            if(command.length == 7) {
                                Integer playerID = tryParseInt(command[2]);
                                Integer tektonID = tryParseInt(command[3]);
                                Integer sporeSpawnTimer = tryParseInt(command[4]);
                                Integer lifeTime = tryParseInt(command[5]);
                                Integer resources = tryParseInt(command[6]);
                                if (playerID != null && tektonID != null && sporeSpawnTimer != null) {
                                    Mushroom m1 = new Mushroom(playerID, this.getTekton(tektonID), sporeSpawnTimer, lifeTime, resources);
                                    this.getTekton(tektonID).arrayOfMushroom.add(m1);
                                    update();
                                } else {
                                    System.out.println(errorString + "Mushroom");
                                }
                            }
                            else
                                System.out.println(errorString + "Mushroom");
                            break;
                        case "STRING":
                            if (command.length == 9){
                                Integer mushroomID = tryParseInt(command[2]);
                                Integer startTektonID = tryParseInt(command[3]);
                                Integer endTektonID = tryParseInt(command[4]);
                                Boolean growing = tryParseBoolean(command[5]);
                                Integer lifetime = tryParseInt(command[6]);
                                Boolean isCut = tryParseBoolean(command[7]);
                                Boolean isConnected = tryParseBoolean(command[8]);
                                if (mushroomID != null && startTektonID != null && endTektonID != null &&
                                    growing != null && lifetime != null && isCut != null && isConnected != null &&
                                        isInTektons(startTektonID) && isInTektons(endTektonID) ) {
                                    ShroomString s1 = new ShroomString(this.getMushroom(mushroomID), this.getTekton(startTektonID), this.getTekton(endTektonID), growing, lifetime, isCut, isConnected);
                                    this.getTekton(startTektonID).addSpecialString(this.getTekton(endTektonID),this.getMushroom(mushroomID),s1);
                                    update();

                                }
                                else {
                                    System.out.println(errorString + "String");
                                }
                            }
                            else
                                System.out.println(errorString + "String");
                            break;

                        case "SPORE":
                            if(command.length == 6) {
                                Integer playerID = tryParseInt(command[3]);
                                Integer tektonID = tryParseInt(command[4]);
                                Integer rand = tryParseInt(command[5]);
                                if (tektonID != null && playerID != null && rand != null && isInTektons(tektonID)) {
                                    Spore s1;
                                    switch (command[2].toUpperCase()) {
                                        case "BASIC":
                                            s1 = new Spore(this.getTekton(tektonID), playerID, rand);
                                            this.getTekton(tektonID).arrayOfSpore.add(s1);
                                            update();
                                            break;
                                        case "BUFF":
                                            s1 = new BuffSpore(this.getTekton(tektonID), playerID, rand);
                                            this.getTekton(tektonID).arrayOfSpore.add(s1);
                                            update();
                                            break;
                                        case "DEBUFF":
                                            s1 = new DebuffSpore(this.getTekton(tektonID), playerID, rand);
                                            this.getTekton(tektonID).arrayOfSpore.add(s1);
                                            update();
                                            break;
                                        case "BIRTH":
                                            s1 = new BirthSpore(this.getTekton(tektonID), playerID, rand);
                                            this.getTekton(tektonID).arrayOfSpore.add(s1);
                                            update();
                                            break;
                                        case "KILLER":
                                            s1 = new KillerSpore(this.getTekton(tektonID), playerID, rand);
                                            this.getTekton(tektonID).arrayOfSpore.add(s1);
                                            update();
                                            break;
                                        case "NOCUT":
                                            s1 = new NoCutSpore(this.getTekton(tektonID), playerID, rand);
                                            this.getTekton(tektonID).arrayOfSpore.add(s1);
                                            update();
                                            break;
                                        case "PARALYZE":
                                            s1 = new ParalyzeSpore(this.getTekton(tektonID), playerID, rand);
                                            this.getTekton(tektonID).arrayOfSpore.add(s1);
                                            update();
                                            break;
                                        default:
                                            System.out.println(errorString + "Spore");
                                            break;
                                    }
                                }
                                else {
                                    System.out.println(errorString + "Spore");
                                }
                            }
                            else
                                System.out.println(errorString + "Spore");
                            break;

                        case "TEKTON":
                            if (command.length == 3){
                                Tekton t1;
                                switch (command[2].toUpperCase()) {
                                    case "BASIC":
                                        t1 = new Tekton();
                                        this.tektons.add(t1);
                                        break;
                                    case "ALLSTRINGSLIVE":
                                        t1 = new AllStringsLiveTekton();
                                        this.tektons.add(t1);
                                        break;
                                    case "MULTIPLESTRING":
                                        t1 = new MultipleStringTekton();
                                        this.tektons.add(t1);
                                        break;
                                    case "STRINGCUTTER":
                                        t1 = new StringCutterTekton();
                                        this.tektons.add(t1);
                                        break;
                                    case "UNLIVABLE":
                                        t1 = new UnlivableTekton();
                                        this.tektons.add(t1);
                                        break;
                                    default:
                                        System.out.println(errorString + "Tekton");
                                        break;
                                }
                            }
                            else
                                System.out.println(errorString + "Tekton");
                            break;
                        default:
                            System.out.println(errorString + command[1]+ " (Unknown Object)");
                            break;
                    }
                    break;
                case "STATUS":
                    System.out.println("Players: ");
                    for (int i = 0; i<= scores.size()-1; i++){
                        System.out.println("Player:"+i+", Score: "+scores.get(i));
                    }
                    System.out.println("Tektons: ");
                    for (Tekton tekton : tektons) {
                        System.out.println("\t"+tekton.toString());
                    }
                    System.out.println("Insects: ");
                    for (Insect insect : insects) {
                        System.out.println("\t"+insect.toString());
                    }
                    System.out.println("Mushrooms: ");
                    for (Mushroom mushroom : mushrooms) {
                        System.out.println("\t"+mushroom.toString());
                    }
                    System.out.println("Strings: ");
                    for (ShroomString shroomString : shroomStrings) {
                        System.out.println("\t"+shroomString.toString());
                    }
                    System.out.println("Spores:");
                    for (Spore spore : spores) {
                        System.out.println("\t"+spore.toString());
                    }
                    break;

                //Paraméterezés: EAT <SPORE> <insectID> <sporeID>
                //               EAT <INSECT> <stringID> <insectID>
                case "EAT":
                    if (command.length == 4) {
                        if (command[1].toUpperCase().equals("SPORE")) {
                            Integer insectID = tryParseInt(command[2]);
                            Integer sporeID = tryParseInt(command[3]);
                            Tekton t = getInsect(insectID).tekton;

                            if (!t.arrayOfSpore.contains(getSpore(sporeID))) {
                                System.out.println("Error! Could not eat Spore: " + sporeID + " by Insect: " + insectID);
                            } else {
                                getInsect(insectID).eatSpore(getSpore(sporeID));
                                update();
                            }
                        } else if (command[1].toUpperCase().equals("INSECT")) {
                            int stringID = Integer.parseInt(command[2]);
                            int insectID = Integer.parseInt(command[3]);
                            Tekton t = getShroomString(stringID).disTek;
                            if (!t.arrayOfInsect.contains(getInsect(insectID)) || getInsect(insectID).effectType != Effect.PARALYZE) {
                                System.out.println("Error! Could not eat Insect: " + insectID + " by String: " + stringID);
                            } else {
                                System.out.println("INSECT EVES");
                                getShroomString(stringID).eatInsect(getInsect(insectID));
                                update();
                            }
                        }
                    }
                    break;

                case "CUT":
                    if (command.length == 3){
                        Integer stringID = tryParseInt(command[1]);
                        Integer insectID = tryParseInt(command[2]);
                        Tekton startT = getShroomString(stringID).startTek;
                        Tekton endT = getShroomString(stringID).disTek;
                        if (startT.arrayOfInsect.contains(getInsect(insectID))){
                            System.out.println("StartTekton vágás");
                            getInsect(insectID).cutString(getShroomString(stringID));
                            update();
                        }
                        else if (endT.arrayOfInsect.contains(getInsect(insectID)))
                        {
                            System.out.println("EndTekton vágás");
                            getInsect(insectID).cutString(getShroomString(stringID));
                            update();
                        }
                        else{
                            System.out.println("Error! Could not cut String: "+stringID+" by Insect: "+insectID);
                        }
                    }
                    break;

                case "GROW":
                    System.out.println("GROW");
                    if(command.length == 1){
                        System.out.println("Too few arguments");
                        break;
                    };

                        switch (command[1].toUpperCase()) {
                            case "MUSHROOM":
                                if(command.length == 3) {
                                    Integer stringID = tryParseInt(command[2]);
                                    shroomStrings.get(stringID).growMushroom();
                                    update();
                                }
                                break;
                            case "STRING":
                                if(command.length == 5) {
                                    Integer mushroomID = tryParseInt(command[2]);
                                    Integer StartTekID = tryParseInt(command[3]);
                                    Integer EndTekID = tryParseInt(command[4]);
                                    if (tektons.get(StartTekID).getNeighbours().contains(tektons.get(EndTekID))) {
                                        mushrooms.get(mushroomID).growString(tektons.get(StartTekID), tektons.get(EndTekID));
                                        update();
                                    } else
                                        System.out.println("Error! Could not grow String from Mushroom: " + mushroomID + " by Tekton: " + StartTekID + " and EndTekID: " + EndTekID);
                                }
                                break;
                        }

                    break;

                case "SPREAD":
                    System.out.println("SPREAD");
                    if(command.length < 4){
                        System.out.println("Too few arguments");
                        break;
                    };
                    Integer shroomID = tryParseInt(command[1]);
                    Integer tektonID = tryParseInt(command[2]);
                    if(this.isInTektons(tektonID)){
                        if(this.containsMushroomById(mushrooms,shroomID)){
                            mushrooms.get(shroomID).spreadSpore(tektons.get(tektonID),tryParseInt(command[3]));
                            update();
                        }
                        else{
                            System.out.println("Error! Could not spread spore  by Mushroom: "+shroomID +"at Tekton: "+tektonID);
                        }
                    }
                    else{
                        System.out.println("Error! Could not spread spore  by Mushroom: "+shroomID +"at Tekton: "+tektonID);
                    }
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
                    if(command.length == 3) {
                        Integer tekton1 = tryParseInt(command[1]);
                        Integer tekton2 = tryParseInt(command[2]);
                        tektons.get(tekton1).addNeighbour(tektons.get(tekton2));
                        update();
                    }
                    break;

                default:
                    System.out.println("Ismeretlen parancs: " + command[0]);
            }
    }
}
