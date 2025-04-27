import jdk.jshell.ImportSnippet;

import java.awt.geom.Area;
import java.io.*;
import java.sql.Array;
import java.util.*;

/**
 * A játékban használt térképet reprezentáló osztály,
 * amely Tektonokat tárol és szomszédsági kapcsolatokat kezel.
 */

public class Map implements Serializable {
    private static final long serialVersionUID = 1L;
    public List<Tekton> tektons = new ArrayList<Tekton>();
    public List<ShroomString> shroomStrings = new ArrayList<ShroomString>();
    public List<Mushroom> mushrooms = new ArrayList<Mushroom>();
    public List<Insect> insects = new ArrayList<Insect>();
    public List<Spore> spores = new ArrayList<Spore>();
    public List<Integer> scores = new ArrayList<Integer>();

    public void wipeMap(){
        Tekton.resetTid();
        tektons.clear();
        ShroomString.resetSsid();
        shroomStrings.clear();
        Mushroom.resetMid();
        mushrooms.clear();
        Insect.resetIid();
        insects.clear();
        Spore.resetSpid();
        spores.clear();
        scores.clear();

    }
    /**
     * Beallitja egy gomba osszes fonalara, az isConnected-t
     */
    public void saveMap(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Map map = (Map) in.readObject();
            this.scores = map.scores;
            this.tektons = map.tektons;
            this.shroomStrings = map.shroomStrings;
            this.mushrooms = map.mushrooms;
            this.insects = map.insects;
            this.spores = map.spores;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void setStringIsConnected (Mushroom mushroom){
        /// Ha nem lenne elvagva, akkor elvagjuk

        /// stringWithSameShroom: az osszes olyan fonal aminek a parentShrommja ugyaaz mint a cuttedStringnek
        List<ShroomString> stringWithSameShroom = new ArrayList<ShroomString>();
        /// reachedTektons: az osszes tekton, amelyiket elerjuk, nem pontosan, de nagyjabol, csak azok lesznek benne
        List<Tekton> reachedTektons = new ArrayList<Tekton>();
        /// kezdo Tektonja, az a tekton, amelyiken a kezdo gomba van
        reachedTektons.add(mushroom.position);
        /// feltolti a stringWithSameShroom-t az osszes olyan fonallal, aminek ugyanaz a parentShroomja
        for (ShroomString shroomString : shroomStrings) {
            if (shroomString.parentSrhoom.equals(mushroom)) {
                stringWithSameShroom.add(shroomString);
            }
        }
        /// amig reachedTektons nem ures, reachedTektons szellesegi bejaras, mindig csak az ujjonan felfedezettek vannak rajta
        while (!reachedTektons.isEmpty()) {
            /// Iteratorok, azert, mert kell majd törölni is azt a tektont amin voltam
            Iterator<Tekton> tektonIterator = reachedTektons.iterator();
            while (tektonIterator.hasNext()) {
                Tekton tekton = tektonIterator.next();
                Iterator<ShroomString> shroomStringIterator = stringWithSameShroom.iterator();
                /**
                 * törli a stringWithSameShroom-ból az összes olyan fonalat,aminek a disTek-je vagy startTek-je a tekton,
                 * igy ha végez akkor törli az összes olyan fonalat, amelyikig eljutottunk és
                 * igy a vegen csak azokat a fonalakat fogja tartalmayni amelyikig nem tudunk eljutni a gombatol
                 */
                while (shroomStringIterator.hasNext()) {

                    ShroomString shroomString = shroomStringIterator.next();
                    if (shroomString.disTek.equals(tekton) && !shroomString.isCut) {

                        reachedTektons.add(shroomString.startTek);
                        shroomString.isConnected = true;
                        shroomStringIterator.remove();
                    } else if (shroomString.startTek.equals(tekton) && !shroomString.isCut) {

                        reachedTektons.add(shroomString.disTek);
                        shroomString.isConnected = true;
                        shroomStringIterator.remove();
                    }
                }
                tektonIterator.remove();
            }
        }
        /// Beallitja a maradek fonalra, amelyekig nem ertunk el, hogy isConnected = false
        for (ShroomString shroomString : stringWithSameShroom) {
            shroomString.isConnected = false;
        }
    }



    private void update() {
        shroomStrings.clear();
        mushrooms.clear();
        insects.clear();
        spores.clear();
        Collections.fill(scores, 0);

        for (Tekton tekton : tektons) {
            for (Insect insect : tekton.arrayOfInsect) {
                insects.add(insect);
                scores.set(insect.playerID, scores.get(insect.playerID) + insect.resources);

            }

            spores.addAll(tekton.arrayOfSpore);

            for(ShroomString string : tekton.arrayOfString) {
                if(!shroomStrings.contains(string)) {
                    shroomStrings.add(string);
                    string.notConnected();
                }
            }

            for (Mushroom mushroom : tekton.arrayOfMushroom) {
                mushrooms.add(mushroom);
                scores.set(mushroom.playerID, scores.get(mushroom.playerID) + mushroom.resources);
            }
        }
    }


    /**
     * Map létrehozásáért felelős 5x5-ös tekton, scores fele mushroom, másik insect
     */
    private void mapInit(){
        if(!tektons.isEmpty() || scores.isEmpty()){
            System.out.println("Error! Map innit is not possible (tektons already on map or no players)");
            return;
        }

        /// Tektonok létrehozása
        for(int i = 0; i<25; i++) {
            Tekton t1;
            if (i == 4) t1 = new AllStringsLiveTekton();
            else if (i == 6) t1 = new MultipleStringTekton();
            else if (i == 7) t1 = new MultipleStringTekton();
            else if (i == 13) t1 = new StringCutterTekton();
            else if (i == 18) t1 = new StringCutterTekton();
            else if (i == 22) t1 = new UnlivableTekton();
            else t1 = new Tekton();
            tektons.add(t1);

            if (i > 0 && i != 5 && i != 10 && i != 15 && i != 20) addNeighbours(t1, tektons.get(i - 1));
            if (i >= 5) addNeighbours(t1, tektons.get(i - 5));
        }

        Random rnd = new Random();
        /// Gombák létrehozása
        for(int j = 0; j<scores.size()/2; j++){
            int pos = rnd.nextInt(0, 25);
            Tekton t1 = getTekton(pos);                             //Random tekton

            while(!t1.arrayOfMushroom.isEmpty() && t1.id!=22){       //Ismétli amíg nem talál jót
                pos = rnd.nextInt(0, 25);
                t1 = getTekton(pos);
            }

            Mushroom m1 = new Mushroom(j, t1, 0, 100, 0);
            t1.arrayOfMushroom.add(m1);                             //Elhelyezi
        }

        for(int j = scores.size()/2; j<scores.size(); j++){
            int pos = rnd.nextInt(0, 25);
            Tekton t1 = getTekton(pos);                 //Random tekton

            while(!t1.arrayOfInsect.isEmpty()){          //Ismétli amíg jót nem talál
                pos = rnd.nextInt(0, 25);
                t1 = getTekton(pos);
            }

            Insect i1 = new Insect(j, t1, 3, 0, 0, Effect.DEFAULT);
            t1.arrayOfInsect.add(i1);                   //Elhelyezi
        }
    }


    /**
     * Frissíti a szomszédsági kapcsolatokat minden Tekton között.
     */
    private void addNeighbours(Tekton t1, Tekton t2) {
        t1.addNeighbour(t2);
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

    /**
     * Játék indítása, a score értékeit 0-ra beállítja
     * @param num Ennyi játékos lesz
     */
    private void startGame(int num){
        for(int i = 0; i<num; i++){
            scores.add(0);
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

    public void command(String inputCommand) {
        if (inputCommand == null || inputCommand.isEmpty())
            return; //nem megyünk el a switch case-ig, csak akkor, ha a beolvasott sor nem üres.

        String[] command = inputCommand.trim().split("\\s+"); //szóközöknél eldarabolja a stringet - több szóköz esetén is jól működik.
            switch (command[0].toUpperCase()) {
                case "STARTGAME":
                    if(command.length == 2){
                        Integer num = tryParseInt(command[1]);
                        if(num != null){
                            startGame(num);
                            update();
                        }
                    }
                    else
                        System.out.println("Error! Could not start game (Invalid arguments)");
                    break;

                case "MAPINIT":
                    if(command.length == 1){
                        mapInit();
                        update();
                    }

                case "LOAD":
                    if(command.length == 2){
                        loadMap(command[1]);
                        update();
                    }
                    break;

                case "SAVE":
                    if(command.length == 2){
                        saveMap(command[1]);
                    }
                    break;

                case "CREATE":
                    if(command.length < 3){
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
                                if (playerID != null && playerID<1+scores.size() && tektonID != null && actionPoints != null
                                        && resources != null && buffTimer != null && effectstatus != null && getTekton(tektonID) != null) {
                                    Insect i1 = new Insect(playerID, this.getTekton(tektonID), actionPoints, resources, buffTimer, effectstatus);
                                    this.getTekton(tektonID).accept(i1);
                                    update();
                                } else {
                                    System.out.println(errorString + "Insect");
                                }
                            }
                            else
                                System.out.println(errorString + "Insect (Invalid argument count)");
                            break;
                        case "MUSHROOM":
                            if(command.length == 7) {
                                Integer playerID = tryParseInt(command[2]);
                                Integer tektonID = tryParseInt(command[3]);
                                Integer sporeSpawnTimer = tryParseInt(command[4]);
                                Integer lifeTime = tryParseInt(command[5]);
                                Integer resources = tryParseInt(command[6]);
                                if (playerID != null  && playerID<scores.size() && tektonID != null && sporeSpawnTimer != null) {
                                    Mushroom m1 = new Mushroom(playerID, this.getTekton(tektonID), sporeSpawnTimer, lifeTime, resources);
                                    this.getTekton(tektonID).arrayOfMushroom.add(m1);
                                    update();
                                } else {
                                    System.out.println(errorString + "Mushroom");
                                }
                            }
                            else
                                System.out.println(errorString + "Mushroom (Invalid argument count)");
                            break;
                        case "STRING":
                            if (command.length == 9){
                                Integer mushroomID = tryParseInt(command[2]);
                                Integer startTektonID = tryParseInt(command[3]);
                                Integer endTektonID = tryParseInt(command[4]);
                                Boolean growing = tryParseBoolean(command[5]);
                                Integer lifeTime = tryParseInt(command[6]);
                                Boolean isCut = tryParseBoolean(command[7]);
                                Boolean isConnected = tryParseBoolean(command[8]);
                                if (mushroomID != null && startTektonID != null && endTektonID != null &&
                                    growing != null && lifeTime != null && isCut != null && isConnected != null &&
                                        getTekton(startTektonID) != null && getTekton(endTektonID) != null ) {
                                    ShroomString s1 = new ShroomString(this.getMushroom(mushroomID), this.getTekton(startTektonID), this.getTekton(endTektonID), growing, lifeTime, isCut, isConnected);
                                    shroomStrings.add(s1);
                                    update();

                                }
                                else {
                                    System.out.println(errorString + "String");
                                }
                            }
                            else
                                System.out.println(errorString + "String (Invalid argument count)");
                            break;

                        case "SPORE":
                            if(command.length == 6) {
                                Integer playerID = tryParseInt(command[3]);
                                Integer tektonID = tryParseInt(command[4]);
                                Integer rand = tryParseInt(command[5]);
                                if (tektonID != null  && playerID != null && rand != null && getTekton(tektonID) != null) {
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
                                System.out.println(errorString + "Spore (Invalid argument count)");
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
                                System.out.println(errorString + "Tekton (Invalid argument count)");
                            break;
                        default:
                            System.out.println(errorString + command[1]+ " (Unknown Object)");
                            break;
                    }
                    break;
                case "STATUS":
                    System.out.println("Players:");
                    for (int i = 0; i<= scores.size()-1; i++){
                        System.out.println("Player:"+i+", Score: "+scores.get(i));
                    }
                    System.out.println("Tektons:");
                    for (Tekton tekton : tektons) {
                        System.out.println("    "+tekton.toString());
                    }
                    System.out.println("Insects:");
                    for (Insect insect : insects) {
                        System.out.println("    "+insect.toString());
                    }
                    System.out.println("Mushrooms:");
                    for (Mushroom mushroom : mushrooms) {
                        System.out.println("    "+mushroom.toString());
                    }
                    System.out.println("Strings:");
                    for (ShroomString shroomString : shroomStrings) {
                        System.out.println("    "+shroomString.toString());
                    }
                    System.out.println("Spores:");
                    for (Spore spore : spores) {
                        System.out.println("    "+spore.toString());
                    }
                    break;

                //Paraméterezés: EAT <SPORE> <insectID> <sporeID>
                //               EAT <INSECT> <stringID> <insectID>
                case "EAT":
                    if (command.length == 4) {
                        if (command[1].toUpperCase().equals("SPORE")) {
                            Integer insectID = tryParseInt(command[2]);
                            Integer sporeID = tryParseInt(command[3]);
                            Spore s = getSpore(sporeID);
                            Insect i = getInsect(insectID);

                            if(s== null || i== null){
                                System.out.println("Error! Could not eat Spore: " + sporeID + " by Insect: " + insectID);
                                break;
                            }

                            Tekton t = i.tekton;
                            if (!t.arrayOfSpore.contains(s)) {
                                System.out.println("Error! Could not eat Spore: " + sporeID + " by Insect: " + insectID);
                                break;
                            }

                            else {
                                i.eatSpore(s);
                                update();
                                break;
                            }

                        } else if (command[1].toUpperCase().equals("INSECT")) {
                            int stringID = Integer.parseInt(command[2]);
                            int insectID = Integer.parseInt(command[3]);
                            ShroomString s = getShroomString(stringID);
                            Insect i = getInsect(insectID);

                            if(s == null || i== null){
                                System.out.println("Error! Could not eat Insect: " + insectID + " by String: " + stringID);
                                break;
                            }

                            Tekton t = s.disTek;
                            if (!t.arrayOfInsect.contains(getInsect(insectID)) || t.arrayOfString.contains(getShroomString(stringID))) {
                                System.out.println("Error! Could not eat Insect: " + insectID + " by String: " + stringID);
                            } else {
                                System.out.println("INSECT EVES");
                                s.eatInsect(i);
                                update();
                            }
                        }
                    }
                    else
                        System.out.println("Error! Could not eat Spore (Invalid argument count)");

                    break;

                case "CUT":
                    if (command.length == 3){
                        Integer stringID = tryParseInt(command[1]);
                        Integer insectID = tryParseInt(command[2]);
                        Insect i = getInsect(insectID);
                        ShroomString s = getShroomString(stringID);
                        if(s == null || i== null){
                            System.out.println("Error! Could not cut String: "+stringID+" by Insect: "+insectID);
                            break;
                        }

                        Tekton startT = s.startTek;
                        Tekton endT = s.disTek;
                        if (startT.arrayOfInsect.contains(i)){

                            i.cutString(s);
                            update();
                        }
                        else if (endT.arrayOfInsect.contains(i))
                        {

                            i.cutString(s);
                            update();
                        }
                        else{
                            System.out.println("Error! Could not cut String: "+stringID+" by Insect: "+insectID);
                        }
                    }
                    else
                        System.out.println("Error! Could not cut String (Invalid argument count)");
                    break;

                case "GROW":
                    if(command.length == 1){
                        System.out.println("Too few arguments");
                        break;
                    };

                        switch (command[1].toUpperCase()) {
                            case "MUSHROOM":
                                if(command.length == 3) {
                                    Integer stringID = tryParseInt(command[2]);
                                    ShroomString s = getShroomString(stringID);

                                    if(s == null){
                                        System.out.println("Error! Could not grow Mushroom (Invalid argument count)");
                                        break;
                                    }

                                    s.growMushroom();
                                    update();
                                }
                                else
                                    System.out.println("Error! Could not grow Mushroom (Invalid argument count)");
                                break;
                            case "STRING":
                                if(command.length == 5) {
                                    Integer mushroomID = tryParseInt(command[2]);
                                    Integer StartTekID = tryParseInt(command[3]);
                                    Integer EndTekID = tryParseInt(command[4]);
                                    Mushroom m1 = getMushroom(mushroomID);
                                    Tekton t1 = getTekton(StartTekID);
                                    Tekton t2 = getTekton(EndTekID);

                                    if(m1==null || t1==null || t2==null){
                                        System.out.println("Error! Could not grow MushroomString by Mushroom:"+mushroomID+" at "+StartTekID+" and "+EndTekID+" Tektons.");
                                        break;
                                    }

                                    if (t1.neighbours.contains(t2)) {
                                        m1.growString(t1, t2);
                                        update();
                                    } else
                                        System.out.println("Error! Could not grow MushroomString by Mushroom:"+mushroomID+" at "+StartTekID+" and "+EndTekID+" Tektons.");
                                }
                                else
                                    System.out.println("Error! Could not grow String from Mushroom (Invalid argument count)");
                                break;
                        }

                    break;

                case "SPREAD":
                    if(command.length == 5){
                        Integer shroomID = tryParseInt(command[1]);
                        Integer tektonID = tryParseInt(command[2]);
                        Integer rnd = tryParseInt(command[3]);
                        Integer rnd2 = tryParseInt(command[4]);
                        Tekton t = tektons.get(tektonID);
                        Mushroom m = getMushroom(shroomID);

                        if(m == null || t == null){
                            System.out.println("Error! Could not spread spore by Mushroom: "+shroomID +"at Tekton:"+tektonID);
                            break;
                        }
                        m.spreadSpore(t, rnd, rnd2);
                        update();

                    }
                    else
                        System.out.println("Error! Could not spread spore (Invalid argument count)");
                    break;

                case "ENDGAME":
                    this.endGame();
                    break;

                case "BREAKTEKTON":
                    if(command.length == 3){
                        Integer tektonID = tryParseInt(command[1]);
                        Integer rnd = tryParseInt(command[2]);
                        Tekton t = getTekton(tektonID);
                        if(t != null){
                            tektons.add(t.breakTekton(rnd));
                            update();
                        }
                        else
                            System.out.println("Error! Could not break Tekton:"+tektonID);
                    }
                    else
                        System.out.println("Error! Could not break Tekton (Invalid argument count)");
                    update();
                    break;

                case "UPGRADE":
                    if(command.length == 2){
                        Integer mushroomID = tryParseInt(command[1]);
                        Mushroom m = getMushroom(mushroomID);
                        if(m != null){
                            m.upgradeMushroom();
                            update();
                            break;
                        }
                        System.out.println("Error! Could not upgrade Mushroom");
                    }
                    else
                        System.out.println("Error! Could not upgrade Mushroom (Invalid argument count)");
                    break;

                case "TIMEELAPSED":
                    if (command.length == 2) {
                        Integer time = tryParseInt(command[1]);         //COMMAND konvertálás int-re
                        for (int i=0; i<time; i++) {                //Lefut amennyi az int
                            for (Tekton tekton : tektons) {   //Tektonokon végigmegyünk
                                tekton.timeElapsed();
                                update();
                            }
                        }
                    }
                    else
                        System.out.println("Error! Could not elapse time (Invalid argument count)");
                    break;

                case "ADDNEIGHBOURS":
                    if(command.length == 3) {
                        Integer tekton1 = tryParseInt(command[1]);
                        Integer tekton2 = tryParseInt(command[2]);
                        Tekton t1 = getTekton(tekton1);
                        Tekton t2 = getTekton(tekton2);
                        if(t1 == null || t2 == null){
                            System.out.println("Could not add neighbours");
                            break;
                        }
                        addNeighbours(t1, t2);
                        update();
                    }
                    break;

                case "MOVE":
                    if(command.length == 3) {
                        Integer insectID = tryParseInt(command[1]);
                        Integer tektonID = tryParseInt(command[2]);
                        Tekton t1 = getTekton(tektonID);
                        Insect i = getInsect(insectID);

                        if(i == null || t1 == null){
                            System.out.println("Error! Could not move insect:"+insectID+" to Tekton:"+tektonID);
                            break;
                        }
                        i.moveToTekton(t1);
                        update();
                        break;
                    }

                default:
                    System.out.println("Unknown Command: " + command[0]);
            }
    }
}