import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * GUI eseményeit kezeli le, összeköti a user-t a játék logikájával.
 * Felelős a gombnyomásokért, a menü kezeléséért, körök váltásáért,
 * megjelenítés frissítéséért.
 */
public class Controller {

    /**
     * A körök maximális száma.
     */
    boolean gamestarted=false;
    protected int MAX_TURNS = 100;

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
    protected int TEKTON_BREAK_NUM = 20;

    /**
     * Kiválasztott (aktuális) játékos gombája.
     * Pontos szerepe a játéklogika részletesebb kidolgozásától függ.
     * Lehet az aktuális játékos fő/aktív gombája, vagy a GUI-n kiválasztott elem.
     * Megjegyzés: A Java konvenciók szerint a változónév 'playerMushroom' lenne.
     */
    protected Mushroom PlayerMushroom = null;

    protected ArrayList<Tekton> selectedTektons = new ArrayList<>();
    /**
     * Kiválasztott (aktuális) játékos rovarja.
     * Hasonlóan a PlayerMushroom-hoz.
     * Megjegyzés: A Java konvenciók szerint a változónév 'playerInsect' lenne.
     */
    protected Insect PlayerInsect = null;

    // Belső állapot a játékosok sorrendjének kezeléséhez
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
        this.player_ids = new ArrayList<>();
        this.map = map;


        this.menuWindow = new MenuWindow(this);
        this.selectedTektons = new ArrayList<>();

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
        if (this.gameWindow != null) {
            this.gameWindow.dispose();
        }
        if (playerCount <= 0) {
            System.err.println("Hiba: A játékosok száma pozitív egész kell legyen.");
            return;
        }
        map.startGame(playerCount);
        map.mapInit();
        map.update();
        map.command("STATUS");
        System.out.println("Játék indítása " + playerCount + " játékossal.");
        player_ids.clear();


        for (int i = 0; i < playerCount; i++) {
            player_ids.add(i); // Játékos ID-k pl. 0, 1, 2...
        }
        currentPlayerIndex = 0; // Az első játékos kezd (ID: player_ids.get(0))
        breakTektonCounter = 0;
        this.gameWindow = new GameWindow(this);
        menuWindow.dispose();


        /*for(Tekton tekton : map.tektons){
            TektonPanel newTektonPanel = new TektonPanel(tekton, 2);
            gameWindow.tektonPanels.add(newTektonPanel);
            for (Mushroom mushroom : tekton.arrayOfMushroom) {
                try {
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/mushroomtrans.png"));
                    newTektonPanel.addItemPanel(new MushroomPanel(mushroom, image));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            for (Insect insect : tekton.arrayOfInsect) {
                try {
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/insecttrans.png"));
                    newTektonPanel.addItemPanel(new InsectPanel(insect, image));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            for (Spore spore : tekton.arrayOfSpore) {
                try {
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/sporetrans.png"));
                    newTektonPanel.addItemPanel(new SporePanel(spore, image));
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            for (ShroomString string : tekton.arrayOfString) {
                try {
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/stringtrans.png"));
                    newTektonPanel.addItemPanel(new ShroomStringPanel(string, image));
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                }
            }
        }*/

        map.update();
        gamestarted = true;

    }

    /**
     * Főmenü megjelenítése.
     */
    public void showMenu() {
        System.out.println("Főmenü megjelenítése...");
        menuWindow = new MenuWindow(this);
        menuWindow.setVisible(true);
    }

    /**
     * Egy teljes kör lefutása. Ebben van a Playerek sorrendje.
     */
    private boolean turnEnded = false;

    public void setTurnEnded(boolean turnEnded) {
        this.turnEnded = turnEnded;
    }

    public void oneRound() {
        currentTurn++;
        if (player_ids.isEmpty()) {
            System.out.println("Nincsenek játékosok, a kör nem indul el.");
            return;
        }
        System.out.println("--- Új kör kezdődik ---");

        for (int i = 0; i < player_ids.size() / 2; i++) {
            currentPlayerIndex = i;
            gameWindow.setCurrentPlayerId(currentPlayerIndex);

            System.out.println("Játékos " + currentPlayerIndex + " következik.");
            turnEnded = false; // Alaphelyzetbe állítjuk a flag-et
            for (int j = 0; j < player_ids.size()/2; j++){
                if(map.mushrooms.get(j).playerID == currentPlayerIndex){
                    PlayerMushroom = map.mushrooms.get(j);
                }
            }
            gameWindow.showMushroomCMD(); // Gomba parancsok megjelenítése
            gameWindow.reDraw();
            while (!turnEnded) {
                // Várunk a gombra... (GUI események kezelése)
                try {
                    Thread.sleep(100); // Kicsit várunk, hogy ne pörögjön feleslegesen
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = player_ids.size() / 2; i < player_ids.size(); i++) {
            currentPlayerIndex = i;

            gameWindow.setCurrentPlayerId(currentPlayerIndex);

            System.out.println("Játékos " + currentPlayerIndex + " következik.");
            turnEnded = false;
            for (int j = 0; j < player_ids.size()/2; j++){
                if(map.insects.get(j).playerID == currentPlayerIndex){
                    PlayerInsect = map.insects.get(j);
                }
            }
            gameWindow.showInsectCMD(); // Rovar parancsok megjelenítése
            gameWindow.reDraw();
            while (!turnEnded) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            turnEnded = false;
        }

        // Kör végi események (maradnak)
        System.out.println("--- Kör vége ---");
        breakTektonCounter++;
        map.command("TIMEELAPSED 2");
        if (breakTektonCounter >= TEKTON_BREAK_NUM) {
            System.out.println("TEKTON TÖRÉS");
            Random rand = new Random();
            int randomIndex = rand.nextInt(map.tektons.size());
            Tekton t = map.tektons.get(randomIndex);
            breakTekton(t);
            map.update();
            gameWindow.reDraw();
            breakTektonCounter = 0;
        }

        if (currentTurn >= MAX_TURNS) {
            endGame();
        }
    }

    /**
     * Már elkezdett játék betöltése.
     * @param filePath A mentett játékfájl elérési útvonala.
     */
    public void load(String filePath) {
        System.out.println("Játék betöltése innen: " + filePath);
        map.loadMap(filePath);
        map.update();
        gameWindow.reDraw();
    }

    /**
     * A tekton törését hívja meg
     * @param tekton Törendő tekton
     */
    public void breakTekton(Tekton tekton) {
        tekton.breakTekton(-1);
        map.update();
        gameWindow.reDraw();
    }

    /**
     * Jelenlegi játék elmentése.
     * @param filePath A fájl elérési útvonala, ahova menteni kell.
     */
    public void save(String filePath) {
        System.out.println("Játék mentése ide: " + filePath);
        map.saveMap(filePath);
    }

    /**
     * Játék befejezése.
     */
    public void endGame() {
        System.out.println("Játék vége!");
        map.endGame();
        gameWindow.endGame();

    }

    /**
     * Spórázás a pályán.
     * @param mushroom A spórázó gomba.
     * @param tekton A cél Tekton.
     * @param amount A spórák mennyisége (a specifikációban int, de a gomba típusa is meghatározhatja).
     */
    public void spread(Mushroom mushroom, Tekton tekton, int amount) {
        System.out.println("Akció: Spórázás - Gomba: " + mushroom + ", Cél: " + tekton + ", Mennyiség: " + amount);
        mushroom.spreadSpore(tekton, -1, -1);
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/icons/sporetrans.png"));
            Spore latestSpore = tekton.arrayOfSpore.get(tekton.arrayOfSpore.size() - 1); // utolsóként hozzáadott

            // Megkeressük a megfelelő TektonPanelt
            for (TektonPanel panel : gameWindow.getTektonPanels()) {
                if (panel.getTektonData() == tekton) {
                    SporePanel sporePanel = new SporePanel(latestSpore, image);
                    sporePanel.setBounds(panel.getWidth() - 34, panel.getHeight() - 34, 30, 30); // jobb alsó sarok
                    panel.addItemPanel(sporePanel);
                    panel.revalidate();
                    panel.repaint();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Hiba a spóra kép betöltésekor: " + e.getMessage());
        }
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
        System.out.println("Akció: Fonálnövesztés - Gomba: " + mushroom + ", Tektonok: " + tekton1 + ", " + tekton2);
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
        System.out.println("Akció: Gombatest növesztés - Gomba: " + mushroom + ", Cél: " + tekton);
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
        System.out.println("Akció: Gomba fejlesztése - Gomba: " + mushroom);
        mushroom.upgradeMushroom();
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

        // 🔍 1. Megkeressük a régi TektonPanelt
        Tekton oldTekton = insect.tekton;
        TektonPanel oldPanel = null;
        TektonPanel newPanel = null;

        for (TektonPanel tp : gameWindow.tektonPanels) {
            if (tp.getTektonData() == oldTekton) oldPanel = tp;
            if (tp.getTektonData() == targetTekton) newPanel = tp;
        }

        if (oldPanel == null || newPanel == null) {
            System.out.println("Hiba: nem található a TektonPanel.");
            return;
        }

        // 🔄 2. InsectPanel eltávolítása a régi TektonPanelről
        InsectPanel toRemove = null;
        for (Component c : oldPanel.getComponents()) {
            if (c instanceof InsectPanel) {
                InsectPanel ip = (InsectPanel) c;
                if (ip.getInsectData() == insect) {
                    toRemove = ip;
                    break;
                }
            }
        }
        if (toRemove != null) {
            oldPanel.removeItemPanel(toRemove);
        }

        // 🧠 3. Logikai lépés: áthelyezzük a rovart
        insect.moveToTekton(targetTekton);

        // 🎨 4. Új InsectPanel létrehozása
        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/icons/insecttrans.png"));
            InsectPanel newPanelInstance = new InsectPanel(insect, image);
            newPanel.addItemPanel(newPanelInstance);
        } catch (Exception e) {
            System.out.println("Nem sikerült a rovar képét betölteni: " + e.getMessage());
        }

        // 🧼 5. Újrarajzolás
        newPanel.revalidate();
        newPanel.repaint();
        oldPanel.revalidate();
        oldPanel.repaint();

        map.update();
        gameWindow.reDraw();
    }

    /**
     * Rovar megeszi a spórát az adott Tektonon, amin áll.
     * @param insect A spórát evő rovar. A Tekton implicit (a rovar aktuális pozíciója).
     */
    public void eatSpore(Insect insect) {
        System.out.println("Akció: Rovar spórát eszik - Rovar: " + insect);
        insect.eatSpore(insect.tekton.arrayOfSpore.get(0));
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
            if(map.shroomStrings.get(i).disTek == tekton) {
                insect.cutString(map.shroomStrings.get(i));
            }
        }
        map.update();
        gameWindow.reDraw();
    }
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    public int getCurrentPlayerScore() {
        return map.scores.get(currentPlayerIndex);
    }

    public void checkSelected(){
        for(TektonPanel tektonPanel : gameWindow.tektonPanels){
            if(tektonPanel.isSelected){
                selectedTektons.add(tektonPanel.getTektonData());
            }
        }
    }
    public void resetSelectedTektons(){
        for (TektonPanel tektonPanel: gameWindow.tektonPanels){
            if(tektonPanel.isSelected){
                tektonPanel.isSelected = false;
            }
        }
        selectedTektons.clear();
    }
}