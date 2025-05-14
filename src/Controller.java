import java.util.List;
import java.util.ArrayList;

// ----- Helyőrző osztályok a fordíthatóságért -----
// Ezeket a projekt tényleges modell osztályaival kell helyettesíteni.


// ----- Helyőrző osztályok vége -----

/**
 * GUI eseményeit kezeli le, összeköti a user-t a játék logikájával.
 * Felelős a gombnyomásokért, a menü kezeléséért, körök váltásáért,
 * megjelenítés frissítéséért.
 */
public class Controller {

    /**
     * A körök maximális száma.
     */
    protected int MAX_TURNS;

    /**
     * A játékosokat azonosító számok listája – vezérli, hogy melyik játékos
     * következik a GUI-n.
     */
    protected List<Integer> player_ids;

    /**
     * Számláló, hogy mennyi idő múlva történik meg a tekton törés.
     */
    protected int breakTektonCounter;

    /**
     * A töréshez szükséges határérték.
     */
    protected int TEKTON_BREAK_NUM;

    /**
     * Kiválasztott (aktuális) játékos gombája.
     * Pontos szerepe a játéklogika részletesebb kidolgozásától függ.
     * Lehet az aktuális játékos fő/aktív gombája, vagy a GUI-n kiválasztott elem.
     * Megjegyzés: A Java konvenciók szerint a változónév 'playerMushroom' lenne.
     */
    protected Mushroom PlayerMushroom;

    /**
     * Kiválasztott (aktuális) játékos rovarja.
     * Hasonlóan a PlayerMushroom-hoz.
     * Megjegyzés: A Java konvenciók szerint a változónév 'playerInsect' lenne.
     */
    protected Insect PlayerInsect;

    // Belső állapot a játékosok sorrendjének kezeléséhez
    private int currentPlayerIndex;

    // Referenciák a GUI elemekre és a játékmodellre (ezek a specifikációban nem szerepeltek,
    // de egy valós Controllernek szüksége lenne rájuk)
    // private GameView gameView;
    // private GameLogic gameLogic;


    /**
     * Alapértelmezett konstruktor.
     * Inicializálja a Controller állapotát.
     */
    public Controller() {
        this.player_ids = new ArrayList<>();
        this.currentPlayerIndex = 0;
        // Alapértelmezett értékek beállítása (ezeket a játék indításakor felül lehet írni)
        this.MAX_TURNS = 100; // Példa érték
        this.TEKTON_BREAK_NUM = 20; // Példa érték
        this.breakTektonCounter = 0;
    }

    /**
     * Következő játékos ID-jét adja vissza és lépteti a sort.
     * @return A soron következő játékos ID-ja.
     * @throws IllegalStateException Ha nincsenek játékosok inicializálva.
     */
    public int nextPlayer() {
        if (player_ids == null || player_ids.isEmpty()) {
            System.err.println("Hiba: Nincsenek játékosok a játékban a nextPlayer() hívásakor.");
            // Döntéstől függően: vagy hibát dob, vagy egy speciális értéket ad vissza
            // A specifikáció szerint int-et ad vissza, így nem dobunk kivételt itt alapból.
            // Lehet, hogy a hívó félnek kell ellenőriznie ezt az állapotot.
            return -1; // Jelzi a hibát vagy a játék végét
        }

        // Az aktuális játékos ID-jének lekérése az index alapján
        int currentPlayerId = player_ids.get(currentPlayerIndex);

        // Az index léptetése a következő játékosra, körkörösen
        currentPlayerIndex = (currentPlayerIndex + 1) % player_ids.size();

        // Itt lehetne frissíteni a PlayerMushroom és PlayerInsect attribútumokat
        // az új, soron következő játékos entitásaival, ha ez a koncepció.
        // E.g., this.PlayerMushroom = gameLogic.getMushroomForPlayer(player_ids.get(currentPlayerIndex));
        // this.PlayerInsect = gameLogic.getInsectForPlayer(player_ids.get(currentPlayerIndex));

        System.out.println("Következő játékos (ID: " + player_ids.get(currentPlayerIndex) + ") előkészítve. Az előző volt: " + currentPlayerId);
        return currentPlayerId; // Az előző (most befejező) játékos ID-ját adja vissza, vagy a soron következőt?
        // A "Következő játékos ID-jét adja vissza" arra utal, hogy az új játékosét.
        // Módosítom, hogy az ÚJ játékos ID-ját adja vissza:
        // return player_ids.get(currentPlayerIndex); // Ez lenne az új játékos
        // A fenti sorrenddel az az aktuális játékos, aki BEFEJEZTE a körét.
        // Ha az a cél, hogy az ÚJ játékos ID-ját adja vissza:
        // currentPlayerIndex = (currentPlayerIndex + 1) % player_ids.size();
        // return player_ids.get(currentPlayerIndex);
        // Maradok az eredeti logikánál, ami szerint az épp aktuális (még cselekvés előtt álló) játékos indexe van a currentPlayerIndex-ben
        // a metódus hívása UTÁN. Tehát a metódusnak az aktuálisan sorra kerülő játékost kell visszaadnia.
        // Újraértelmezve: a nextPlayer() hívásakor az aktuális player index léptetődik, és az ÚJ aktuális player ID-je tér vissza.
        // Korrigált logika:
        // Integer idToReturn = player_ids.get(currentPlayerIndex);
        // currentPlayerIndex = (currentPlayerIndex + 1) % player_ids.size();
        // return idToReturn; -> Ez azt jelenti, hogy a nextPlayer() hívása UTÁN a currentPlayerIndex már a következőre mutat.

        // Egyszerűbb, ha a currentPlayerIndex mindig az aktuálisan soron lévő játékosra mutat.
        // A nextPlayer() hívása lépteti ezt.
        // Tehát:
        // 1. Lekérdezzük az aktuális játékos ID-t.
        // 2. Léptetjük az indexet.
        // 3. Visszaadjuk a lekérdezett ID-t (aki most fejezte be a körét, vagy akinek a köre elkezdődik).
        // A "Következő játékos ID-jét adja vissza" megfogalmazás szerint a hívás után az ÚJ játékos ID-je kell.
        // Tehát a léptetés UTÁN kell lekérdezni.

        // Legtisztább:
        // currentPlayerIndex = (currentPlayerIndex + 1) % player_ids.size();
        // int nextPlayerId = player_ids.get(currentPlayerIndex);
        // System.out.println("Soron következő játékos: " + nextPlayerId);
        // return nextPlayerId;
        // Vagy ha az oneRound kezeli a ciklust, és a nextPlayer csak a soron következőt adja, de nem léptet,
        // akkor a léptetést a hívónak kell végezni.
        // A specifikáció alapján a `nextPlayer` maga léptet és visszaadja a következőt.

        // Maradjunk az egyszerűbb körkörös léptetésnél, ahol a `currentPlayerIndex` mindig
        // a következő játékosra mutat a hívás előtt.
        // A hívás kiválasztja, léptet, és visszaadja azt, aki sorra került.
        int playerIDForThisTurn = player_ids.get(currentPlayerIndex);
        System.out.println("Soron: Játékos " + playerIDForThisTurn);
        // A következő híváshoz előkészítjük a következő indexet:
        currentPlayerIndex = (currentPlayerIndex + 1) % player_ids.size();
        return playerIDForThisTurn;
    }

    /**
     * Játék indítása, kezdeti beállítások (játékosok száma).
     * @param playerCount A játékosok száma.
     */
    public void startGame(int playerCount) {
        if (playerCount <= 0) {
            System.err.println("Hiba: A játékosok száma pozitív egész kell legyen.");
            return;
        }
        System.out.println("Játék indítása " + playerCount + " játékossal.");
        player_ids.clear();
        for (int i = 0; i < playerCount; i++) {
            player_ids.add(i); // Játékos ID-k pl. 0, 1, 2...
        }
        currentPlayerIndex = 0; // Az első játékos kezd (ID: player_ids.get(0))
        breakTektonCounter = 0;
        // Itt történhet a játéktábla inicializálása, játékosok entitásainak létrehozása stb.
        // gameLogic.initializeBoard(playerCount);
        // updateView(); // GUI frissítése
    }

    /**
     * Főmenü megjelenítése.
     */
    public void showMenu() {
        System.out.println("Főmenü megjelenítése...");
        // Pl. gameView.showMainMenu();
    }

    /**
     * Egy teljes kör lefutása. Ebben van a Playerek sorrendje.
     */
    public void oneRound() {
        if (player_ids.isEmpty()) {
            System.out.println("Nincsenek játékosok, a kör nem indul el.");
            return;
        }
        System.out.println("--- Új kör kezdődik ---");
        for (int i = 0; i < player_ids.size(); i++) {
            int currentPlayerId = nextPlayer(); // Meghatározza és lépteti a soron következő játékost
            System.out.println("Játékos " + currentPlayerId + " következik.");

            // A specifikáció szerint: "a jelenlegi player gombáin vagy rovarain megy végig
            // és állitja be hogy most melyik van soron"
            // Ez a logika itt valósulna meg, pl. az aktív játékos entitásainak
            // kiválasztásával/aktiválásával a GUI számára, vagy az AI döntéshozatalával.
            // Példa:
            // this.PlayerMushroom = gameLogic.getActiveMushroomForPlayer(currentPlayerId);
            // this.PlayerInsect = gameLogic.getActiveInsectForPlayer(currentPlayerId);
            // gameView.setActivePlayer(currentPlayerId, PlayerMushroom, PlayerInsect);

            // Itt kellene a játékosnak lehetőséget adni a cselekvésre.
            // Ez várhat GUI interakcióra vagy AI logikát futtathat.
            // waitForPlayerActionsOrRunAI(currentPlayerId);
        }

        // Kör végi események
        handleEndOfRoundTasks();
        System.out.println("--- Kör vége ---");

        // Ellenőrizzük, hogy elértük-e a maximális körszámot
        // Ezt a MAX_TURNS-höz képest kellene egy körszámlálóval figyelni, ami itt hiányzik.
        // Tegyük fel, van egy `currentTurnNumber` attribútum.
        // if (currentTurnNumber >= MAX_TURNS) {
        //     endGame();
        // }
    }

    /**
     * Kör végi teendők, pl. Tekton törés.
     */
    private void handleEndOfRoundTasks() {
        breakTektonCounter++;
        if (breakTektonCounter >= TEKTON_BREAK_NUM) {
            System.out.println("!!! TEKTON TÖRÉS ESEMÉNY !!!");
            // Itt implementálódna a Tekton törés logikája
            // gameLogic.triggerTektonBreak();
            breakTektonCounter = 0; // Számláló nullázása
        }
        // updateView();
    }


    /**
     * Már elkezdett játék betöltése.
     * @param filePath A mentett játékfájl elérési útvonala.
     */
    public void load(String filePath) {
        System.out.println("Játék betöltése innen: " + filePath);
        // Implementáció: fájl beolvasása, játékállapot (pálya, játékosok, körök stb.) visszaállítása.
        // gameLogic.loadState(filePath);
        // this.player_ids = gameLogic.getPlayerIds();
        // this.currentPlayerIndex = gameLogic.getCurrentPlayerIndex();
        // ... stb.
        // updateView();
    }

    /**
     * Jelenlegi játék elmentése.
     * @param filePath A fájl elérési útvonala, ahova menteni kell.
     */
    public void save(String filePath) {
        System.out.println("Játék mentése ide: " + filePath);
        // Implementáció: aktuális játékállapot kiírása fájlba.
        // gameLogic.saveState(filePath, player_ids, currentPlayerIndex, ...);
    }

    /**
     * Játék befejezése.
     */
    public void endGame() {
        System.out.println("Játék vége!");
        // Eredmények megjelenítése, GUI lezárása vagy menübe való visszatérés.
        // gameView.showEndGameScreen(gameLogic.getResults());
    }

    // --- Játékos által kezdeményezett akciók ---

    /**
     * Spórázás a pályán.
     * @param mushroom A spórázó gomba.
     * @param tekton A cél Tekton.
     * @param amount A spórák mennyisége (a specifikációban int, de a gomba típusa is meghatározhatja).
     */
    public void spread(Mushroom mushroom, Tekton tekton, int amount) {
        System.out.println("Akció: Spórázás - Gomba: " + mushroom + ", Cél: " + tekton + ", Mennyiség: " + amount);
        // Játéklogika hívása:
        // boolean success = gameLogic.performSpread(mushroom, tekton, amount);
        // if (success) updateView();
    }

    /**
     * Fonálnövesztés két Tekton között.
     * @param mushroom A gomba, amelyik a fonalat növeszti.
     * @param tekton1 Az egyik Tekton.
     * @param tekton2 A másik Tekton.
     */
    public void growString(Mushroom mushroom, Tekton tekton1, Tekton tekton2) {
        System.out.println("Akció: Fonálnövesztés - Gomba: " + mushroom + ", Tektonok: " + tekton1 + ", " + tekton2);
        // Játéklogika hívása:
        // boolean success = gameLogic.performGrowString(mushroom, tekton1, tekton2);
        // if (success) updateView();
    }

    /**
     * Gombatestnövesztés a pályán.
     * @param mushroom A gomba, amelyik testet növeszt.
     * @param tekton A Tekton, amin a testnövesztés történik.
     */
    public void growBody(Mushroom mushroom, Tekton tekton) {
        System.out.println("Akció: Gombatest növesztés - Gomba: " + mushroom + ", Cél: " + tekton);
        // Játéklogika hívása:
        // boolean success = gameLogic.performGrowBody(mushroom, tekton);
        // if (success) updateView();
    }

    /**
     * Gombák fejlesztése.
     * @param mushroom A fejlesztendő gomba.
     */
    public void upgradeMushroom(Mushroom mushroom) {
        System.out.println("Akció: Gomba fejlesztése - Gomba: " + mushroom);
        // Játéklogika hívása:
        // boolean success = gameLogic.performUpgradeMushroom(mushroom);
        // if (success) updateView();
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
        // Játéklogika hívása:
        // boolean success = gameLogic.performEatInsect(sourceTekton, insectTekton, insect);
        // if (success) updateView();
    }

    /**
     * Rovar mozgatása.
     * @param insect A mozgatandó rovar.
     * @param targetTekton A cél Tekton.
     */
    public void move(Insect insect, Tekton targetTekton) {
        System.out.println("Akció: Rovar mozgatása - Rovar: " + insect + ", Cél: " + targetTekton);
        // Játéklogika hívása:
        // boolean success = gameLogic.performMoveInsect(insect, targetTekton);
        // if (success) updateView();
    }

    /**
     * Rovar megeszi a spórát az adott Tektonon, amin áll.
     * @param insect A spórát evő rovar. A Tekton implicit (a rovar aktuális pozíciója).
     */
    public void eatSpore(Insect insect) {
        System.out.println("Akció: Rovar spórát eszik - Rovar: " + insect);
        // Játéklogika hívása (a rovar pozícióját a gameLogic-nak kell tudnia):
        // boolean success = gameLogic.performEatSpore(insect);
        // if (success) updateView();
    }

    /**
     * Rovar elvágja az adott fonalat.
     * @param insect A fonalat vágó rovar.
     * @param tekton A Tekton, amelyhez a vágandó fonál kapcsolódik (vagy az egyik végpontja).
     *               A fonál pontos azonosítása a játéklogikától függ.
     */
    public void cut(Insect insect, Tekton tekton) {
        System.out.println("Akció: Rovar fonalat vág - Rovar: " + insect + ", Fonál célpontja: " + tekton);
        // Játéklogika hívása:
        // boolean success = gameLogic.performCutString(insect, tekton);
        // if (success) updateView();
    }

    /**
     * Segédfüggvény a megjelenítés frissítésére (a Controller felelőssége).
     * Ezt a metódust a játékállapotot megváltoztató műveletek után kellene hívni.
     */
    protected void updateView() {
        System.out.println("(Controller) Megjelenítés frissítésének kérése...");
        // if (gameView != null) {
        //     gameView.refresh();
        // }
    }
}