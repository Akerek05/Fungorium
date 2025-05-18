import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * GUI eseményeit kezeli le, összeköti a user-t a játék logikájával.
 * Felelős a gombnyomásokért, a menü kezeléséért, körök váltásáért,
 * megjelenítés frissítéséért.
 */
public class Controller {

    //Játék kezdetét jelző flag
    boolean gamestarted=false;
    /**
     * A körök maximális száma.
     */
    protected int MAX_TURNS = 100;
    /**
     * A jelenlegi kör száma
     */
    protected int currentTurn = 0;

    /**
     * A játékosokat azonosító számok listája – vezérli, hogy melyik játékos
     * következik a GUI-n.
     */
    protected List<Integer> player_ids;

    /**
     * Számláló, hogy mennyi idő múlva történik meg a tekton törés.
     */
    protected int breakTektonCounter = 0;

    /**
     * A töréshez szükséges határérték.
     */
    protected int TEKTON_BREAK_NUM = 30;

    /**
     * Kiválasztott (aktuális) játékos gombája.
     * Pontos szerepe a játéklogika részletesebb kidolgozásától függ.
     * Lehet az aktuális játékos fő/aktív gombája, vagy a GUI-n kiválasztott elem.
     * Megjegyzés: A Java konvenciók szerint a változónév 'playerMushroom' lenne.
     */
    protected Mushroom PlayerMushroom = null;

    //Kiválasztott tektonokat tároló lista
    protected ArrayList<Tekton> selectedTektons = new ArrayList<>();
    /**
     * Kiválasztott (aktuális) játékos rovarja.
     * Hasonlóan a PlayerMushroom-hoz.
     * Megjegyzés: A Java konvenciók szerint a változónév 'playerInsect' lenne.
     */
    protected Insect PlayerInsect = null;

    /// Belső állapot a játékosok sorrendjének kezeléséhez
    private int currentPlayerIndex = 0;

    /**
     * Láthatóság
     */
    protected GameWindow gameWindow;

    /**
     * Map logikája
     */
    protected Map map;

    /**
     * Menü megjelenítése
     */
    protected MenuWindow menuWindow;

    /**
     * Alapértelmezett konstruktor.
     * Inicializálja a Controller állapotát.
     */
    public Controller(Map map) {
        //Játékos azonosítókat tároló lista létrehozása
        this.player_ids = new ArrayList<>();
        //Térkép beállítása kapott paraméter alapján
        this.map = map;

        //Menüablak létrehozása
        this.menuWindow = new MenuWindow(this);
        //Kijelölt tektonokat tároló lista létrehozása
        this.selectedTektons = new ArrayList<>();

        //Menü beállítása
        showMenu();

    }

    /**
     * Következő játékos ID-jét adja vissza és lépteti a sort.
     * @return A soron következő játékos ID-ja.
     */
    public int nextPlayer() {
        return currentPlayerIndex++;
    }

    /**
     * Játék indítása, kezdeti beállítások (játékosok száma).
     * @param playerCount A játékosok száma.
     */
    public void startGame(int playerCount) {
        //Esetleg meglévő ablak törlése
        if (this.gameWindow != null) {
            this.gameWindow.dispose();
        }
        //Játékosok számának ellenőrzése
        if (playerCount <= 0) {
            System.err.println("Hiba: A játékosok száma pozitív egész kell legyen.");
            return;
        }
        //Térkép adatainak alaphelyzetbe állítása(törlése)
        map.wipeMap();
        //Játék indítása
        map.startGame(playerCount);
        //Térkép inícializálása
        map.mapInit();
        //Térkép frissítése
        map.update();
        //Jelzés a felhasználó felé a játék indításáról
        System.out.println("Játék indítása " + playerCount + " játékossal.");

        //Játékosok azanosítójának alaphelyzetbe állítása
        player_ids.clear();

        //Játékosok azanosítójának beállítása
        for (int i = 0; i < playerCount; i++) {
            player_ids.add(i); // Játékos ID-k pl. 0, 1, 2...
        }

        // Az első játékos kezd (ID: player_ids.get(0))
        currentPlayerIndex = 0;
        // Tektontörés visszaszámláló alaphelyzetbe állítása
        breakTektonCounter = 0;
        // Új játékablak létrehozása
        this.gameWindow = new GameWindow(this);
        //Menüablak törlése
        menuWindow.dispose();

        //Térkép frissítése
        map.update();

        //Játék futását jelző flag beállítása
        gamestarted = true;
    }

    /**
     * Főmenü megjelenítése.
     */
    public void showMenu() {
        // Menüablak létrehozásA
        menuWindow = new MenuWindow(this);

        //Menüablak láthatóságának beállítása
        menuWindow.setVisible(true);
    }

    //Kör végét jelző flag
    private boolean turnEnded = false;

    //Kör végét jelző flag átállító függvénye
    public void setTurnEnded(boolean turnEnded) {
        this.turnEnded = turnEnded;
    }


    /**
     * Egy teljes kör lefutása. Ebben van a Playerek sorrendje.
     */
    public void oneRound() {
        //Jelenlegi kört tároló számláló növelése
        currentTurn++;
        //Ellenőrizzük, hogy vannak-e játékosok, ha nincsenek, akkor nem indul el a kör
        if (player_ids.isEmpty()) {
            System.out.println("Nincsenek játékosok, a kör nem indul el.");
            return;
        }
        //Új kör kezdésének jelzése a felhasználó felé
        System.out.println("--- Új kör kezdődik ---");
        //Járékos által jelenleg kijelölt/használt rovar
        PlayerInsect = null;
        //Gombász játékosok ciklusa(ők vannak az első felében tárolva a tömbben)
        for (int i = 0; i < player_ids.size() / 2; i++) {
            //Jelenlegi játékos indexének beállítása
            currentPlayerIndex = i;
            gameWindow.setCurrentPlayerId(currentPlayerIndex);

            // Alaphelyzetbe állítjuk a kör végét jelző flag-et
            turnEnded = false;

            //Végigmegyünk az összes játékban lévő gombán
            for(int j = map.mushrooms.size() - 1; j >= 0; j--) {
                //A ciklusban a jelenlegi indexen lévő gomba beállítása
                Mushroom mushroom = map.mushrooms.get(j);
                //Hogyha a jelenlegi gomba az éppen soron lévő játékoshoz tartozik, akkor:
                if(mushroom.playerID == currentPlayerIndex) {
                    //Jelezzük a felhasználonak, hogy mely játékos következik melyik gombályával
                    System.out.println("Játékos " + currentPlayerIndex + " következik." + "Gomba: " + mushroom.id);
                    JOptionPane.showMessageDialog(null, "Player: "+ currentPlayerIndex + " is next with mushroom: "+ mushroom.id);
                    //Soron lévő játékos jelenlegi gombályának beállítása
                    PlayerMushroom = mushroom;
                    // Gomba parancsok megjelenítése
                    gameWindow.showMushroomCMD();
                    // Játékablak újrarajzolása
                    gameWindow.reDraw();
                    //Ameddig a flag nem jelez
                    while (!turnEnded) {
                        // Várunk a gombra... (GUI események kezelése)
                        try {
                            //Ha nem indult el a játék visszatér a függvény
                            if (!gamestarted){
                                return;
                            }
                            Thread.sleep(100); // Kicsit várunk, hogy ne pörögjön feleslegesen
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //Kör végét jelző flag beállítása
                    turnEnded = false;
                }
            }

        }
        //Jelenlegi játékos gombájának törlése
        PlayerMushroom = null;

        //Rovarász játékosok cilusa(második felében vannak tárolva a listában)
        for (int i = player_ids.size() / 2; i < player_ids.size(); i++) {
            //Jelenlegi játékos indexének beállítása
            currentPlayerIndex = i;
            gameWindow.setCurrentPlayerId(currentPlayerIndex);

            //Kör végét jelző flag alaphelyzetbe állítása
            turnEnded = false;

            //Térképen lévő összes rovaron végigmegyünk
            for (int j = map.insects.size() - 1; j >= 0; j--) {
                //Ciklusban a jelenlegi indexen lévő rovar beállítása
                Insect insect = map.insects.get(j);
                //Ha a jelenlegi rovar a soron lévő játékosé, akkor
                if (insect.playerID == currentPlayerIndex) {
                    //Jelezzük a felhasználonak, hogy mely játékos következik melyik rovarral
                    System.out.println("Játékos " + currentPlayerIndex + " következik." + "Rovar: " + insect.id);
                    JOptionPane.showMessageDialog(null, "Player: "+ currentPlayerIndex + " is next with insect: "+ insect.id);
                    //Soron lévő játékos jelenlegi rovarának beállítása
                    PlayerInsect = insect;
                    // Rovar parancsok megjelenítése
                    gameWindow.showInsectCMD();
                    // Játékablak újrarajzolása
                    gameWindow.reDraw();
                    //Amíg nincs vége a körnek
                    while (!turnEnded) {
                        try {
                            //Ha nincsen elindítva a játék visszatér a függvény
                            if (!gamestarted){
                                return;
                            }

                            Thread.sleep(100);
                            if (insect == null) {
                                turnEnded = true;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //Kör végét jelző flag beállítása
                    turnEnded = false;
                }
            }
            //Soron lévő játékos rovarának alaphelyzetbe állítása
            PlayerInsect = null;
        }

        // Kör végi események (maradnak)

        //Jelezzük a felhasználó felé, hogy a körnek vége van
        System.out.println("--- Kör vége ---");
        JOptionPane.showMessageDialog(null, "Round ended");
        //Tektontörés visszaszámláló növelése
        breakTektonCounter++;
        //Térkép számára jelezzük a kör végét
        map.command("TIMEELAPSED 2");
        //Térkép frissítése
        map.update();
        //Elérte-e a tektonszámláló a megszabott határt, ha igen
        if (breakTektonCounter >= TEKTON_BREAK_NUM) {
            //Jelezzük a felhasználó számára, hogy tektontörés történt
            System.out.println("TEKTON TÖRÉS");
            //Legutolsó tekton kiválasztása
            Tekton t = map.tektons.get(map.tektons.size() - 1);
            //Tekton törése
            breakTekton(t);
            //Tektontörés számláló alaphelyzetbe állítása
            breakTektonCounter = 0;
            //Játékablak újrarajzolása
            gameWindow.reDraw();
        }

        //Ha elértük a maximális körszámot, vége a játéknak
        if (currentTurn >= MAX_TURNS) {
            endGame();
        }

        //Jelenlegi helyzet kiírása STATUS paranccsal
        map.command("STATUS");
    }

    /**
     * Már elkezdett játék betöltése.
     * @param filePath A mentett játékfájl elérési útvonala.
     */
    public void load(String filePath) {

        JOptionPane.showMessageDialog(null, "Loaded game from: " + filePath);

        if (this.gameWindow != null) {
            this.gameWindow.dispose();
        }
        currentTurn = 0;
        map.wipeMap();
        map.loadMap(filePath);
        map.update();

        for (int i = 0; i < map.scores.size(); i++) {
            player_ids.add(i); // Játékos ID-k pl. 0, 1, 2...
        }
        this.gameWindow = new GameWindow(this);
        menuWindow.dispose();

        map.update();
        gamestarted = true;
    }

    /**
     * A tekton törését hívja meg
     * @param tekton Törendő tekton
     */
    public void breakTekton(Tekton tekton) {

        map.tektons.add(tekton.breakTekton(-1));
        map.update();
        gameWindow.reDraw();
    }

    /**
     * Jelenlegi játék elmentése.
     * @param filePath A fájl elérési útvonala, ahova menteni kell.
     */
    public void save(String filePath) {
        JOptionPane.showMessageDialog(null, "Saved game as: " + filePath);
        map.saveMap(filePath);
    }

    /**
     * Játék befejezése.
     */
    public void endGame() {
        JOptionPane.showMessageDialog(null, "Game over!");
        map.endGame();
        gameWindow.endGame();
        currentPlayerIndex = 0;
        currentTurn = 0;
        gamestarted = false;

    }

    /**
     * Spórázás a pályán.
     * @param mushroom A spórázó gomba.
     * @param tekton A cél Tekton.
     * @param amount A spórák mennyisége (a specifikációban int, de a gomba típusa is meghatározhatja).
     */
    public void spread(Mushroom mushroom, Tekton tekton, int amount) {
        mushroom.spreadSpore(tekton, 6, -1);
        map.update();
        gameWindow.reDraw();
    }

    /**
     * Fonálnövesztés két Tekton között.
     * @param mushroom A gomba, amelyik a fonalat növeszti.
     * @param tekton1 Az egyik Tekton.
     * @param tekton2 A másik Tekton.
     */
    public void growString(Mushroom mushroom, Tekton tekton1, Tekton tekton2) {

        mushroom.growString(tekton1, tekton2);
        map.update();
        gameWindow.reDraw();
    }

    /**
     * Gombatestnövesztés a pályán.
     * @param mushroom A gomba, amelyik testet növeszt.
     * @param tekton A Tekton, amin a testnövesztés történik.
     */
    public void growBody(Mushroom mushroom, Tekton tekton) {

        for(int i = 0; i< map.shroomStrings.size(); i++) {
            if(map.shroomStrings.get(i).parentSrhoom == mushroom && map.shroomStrings.get(i).disTek == tekton) {
                map.shroomStrings.get(i).growMushroom();
            }
        }
        map.update();
        gameWindow.reDraw();

    }

    /**
     * Gombák fejlesztése.
     * @param mushroom A fejlesztendő gomba.
     */
    public void upgradeMushroom(Mushroom mushroom) {
        Tekton position = mushroom.position;
        mushroom.upgradeMushroom();
        PlayerMushroom = position.arrayOfMushroom.get(0);
        map.update();
        gameWindow.reDraw();
    }

    /**
     * Egy fonál megeszi a rovart a pályán.
     * @param sourceTekton A Tekton, amelyről a "támadó" fonál indul, vagy ahol a fonál releváns része van.
     * @param insectTekton A Tekton, amin a rovar tartózkodik.
     * @param insect A megtámadott rovar.
     */
    public void eatInsect(Tekton sourceTekton, Tekton insectTekton, Insect insect) {
        System.out.println("Akció: Fonál megeszi a rovart - Fonál forrás: " + sourceTekton +
                ", Rovar helye: " + insectTekton + ", Rovar: " + insect);
        for(int i = 0; i< map.shroomStrings.size(); i++) {
            if(map.shroomStrings.get(i).parentSrhoom == PlayerMushroom
                    && map.shroomStrings.get(i).startTek == sourceTekton && map.shroomStrings.get(i).disTek == insectTekton) {
                map.shroomStrings.get(i).eatInsect(insect);
            }
        }
        map.update();
        gameWindow.reDraw();
    }

    /**
     * Rovar mozgatása.
     * @param insect A mozgatandó rovar.
     * @param targetTekton A cél Tekton.
     */
    public void move(Insect insect, Tekton targetTekton) {
        System.out.println("Akció: Rovar mozgatása - Rovar: " + insect + ", Cél: " + targetTekton);
        insect.moveToTekton(targetTekton);

        map.update();
        gameWindow.reDraw();
    }

    /**
     * Rovar megeszi a spórát az adott Tektonon, amin áll.
     * @param insect A spórát evő rovar. A Tekton implicit (a rovar aktuális pozíciója).
     */
    public void eatSpore(Insect insect) {

        insect.eatSpore(insect.tekton.arrayOfSpore.get(0));
        System.out.println("Akció: Rovar spórát eszik - Rovar: " + insect.effectType);
        map.update();
        gameWindow.reDraw();
    }

    /**
     * Rovar elvágja az adott fonalat.
     * @param insect A fonalat vágó rovar.
     * @param tekton A Tekton, amelyhez a vágandó fonál kapcsolódik (vagy az egyik végpontja).
     *               A fonál pontos azonosítása a játéklogikától függ.
     */
    public void cut(Insect insect, Tekton tekton) {
        System.out.println("Akció: Rovar fonalat vág - Rovar: " + insect + ", Fonál célpontja: " + tekton);

        for(int i = 0; i< map.shroomStrings.size(); i++) {
            if((map.shroomStrings.get(i).disTek == tekton && map.shroomStrings.get(i).startTek == insect.tekton)
            || (map.shroomStrings.get(i).startTek == tekton && map.shroomStrings.get(i).disTek == insect.tekton)) {
                insect.cutString(map.shroomStrings.get(i));
            }
        }
        map.update();
        gameWindow.reDraw();
    }

    /**
     * Vissza adja a currentPlayerIndex-t.
     * @return
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Vissza adja a currentPlayerScore-t.
     * @return
     */
    public int getCurrentPlayerScore() {
        return map.scores.get(currentPlayerIndex);
    }
    /**
     * Megkeresi a kiálasztott tektonokat és hozzáadja a listához.
     */
    public void checkSelected(){
        for(int i=1; i<=TektonPanel.globalTekton; i++){
            for(TektonPanel tektonPanel : gameWindow.tektonPanels){
                if(tektonPanel.isSelected == i){
                    selectedTektons.add(tektonPanel.getTektonData());
                }
            }
        }
    }

    /**
     * Torli a kivalasztott tektonok listaja-t a selectedTektons-t
     */
    public void resetSelectedTektons(){
        for (TektonPanel tektonPanel: gameWindow.tektonPanels){
                tektonPanel.isSelected = 0;
        }

        TektonPanel.globalTekton = 1;
        selectedTektons.clear();
    }
}