import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Feltételezett importok a nem definiált osztályokhoz:
// import com.example.game.Controller;
// import com.example.game.model.Mushroom; // Ha a CommandPaneleknek kell
// import com.example.game.model.Insect;  // Ha a CommandPaneleknek kell
// import com.example.ui.MapPanel;
// import com.example.ui.StatusBarPanel;
// import com.example.ui.MenuWindow;
// import com.example.ui.MushroomCommandPanel;
// import com.example.ui.InsectCommandPanel;
// import com.example.ui.BasicCommandPanel; // Ha a Mushroom/Insect panelek ebből származnak

/**
 * A fő játékképernyőt jeleníti meg ez az osztály.
 * Megjeleníti a térképet, a parancs paneleket, státuszbárt.
 * Ősosztálya: JFrame.
 */
public class GameWindow extends JFrame {

    private int currentPlayerId;
    private Controller controller; // Feltételezzük, hogy a Controller osztály létezik

    // GUI Komponensek (feltételezzük, hogy ezek az osztályok léteznek)
    private MapPanel mapPanel;
    private JPanel commandPanelContainer; // Konténer a váltogatható parancspaneleknek
    private CardLayout commandPanelLayout;
    private StatusBarPanel statusBarPanel;

    private MushroomCommandPanel mushroomCommandPanel;
    private InsectCommandPanel insectCommandPanel;
    // Ha a BasicCommandPanel-ből származnak, akkor azok tartalmazzák az endTurnButton-t.

    // Konstansok a CardLayout panelek azonosításához
    private static final String MUSHROOM_CMD_PANEL_ID = "MUSHROOM_COMMAND_PANEL";
    private static final String INSECT_CMD_PANEL_ID = "INSECT_COMMAND_PANEL";

    /**
     * GameWindow konstruktor.
     * @param controller A játék vezérlő objektuma.
     */
    public GameWindow(Controller controller) {
        this.controller = controller;
        // Kezdeti currentPlayerId beállítása a controllerből
        // Feltételezzük, hogy a Controllernek van egy metódusa az aktuális játékos ID lekérdezésére.
        // Pl. this.currentPlayerId = controller.getCurrentPlayerId();
        // Mivel a Controller osztály itt nincs definiálva, egy placeholder értéket használunk:
        this.currentPlayerId = 1; // Placeholder, ezt a controller.getCurrentPlayerId() adná

        setTitle("Játék Ablak"); // Vagy a játék konkrét címe
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768); // Ajánlott méret
        setLocationRelativeTo(null); // Ablak középre igazítása

        initComponents();
        layoutComponents();
        attachActionListenersToCommandPanels(); // Eseménykezelők csatolása (pl. "Kör vége")

        // Kezdetben az egyik parancspanel megjelenítése
        // Ez függhet a játék állapotától, vagy az első játékos típusától.
        // Alapértelmezetten a gomba parancspanelt mutatjuk, ha van.
        if (mushroomCommandPanel != null) {
            showMushroomCMD();
        } else if (insectCommandPanel != null) {
            showInsectCMD();
        }
        // Frissítjük a státuszbárt a kezdeti játékossal
        if (statusBarPanel != null) {
            // Feltételezzük, hogy a StatusBarPanel-nek van ilyen metódusa:
            // statusBarPanel.updatePlayerInfo(this.currentPlayerId);
        }
    }

    /**
     * Inicializálja a GUI komponenseket.
     */
    private void initComponents() {
        // Feltételezzük, hogy a MapPanel, StatusBarPanel, MushroomCommandPanel, InsectCommandPanel
        // osztályok léteznek és van konstruktoruk (esetleg Controller paraméterrel).

        mapPanel = new MapPanel(/*controller*/); // Ha a MapPanelnek szüksége van a controllerre
        statusBarPanel = new StatusBarPanel();

        commandPanelLayout = new CardLayout();
        commandPanelContainer = new JPanel(commandPanelLayout);
        commandPanelContainer.setBorder(BorderFactory.createEtchedBorder()); // Egy kis keret

        // Parancspanelek példányosítása
        // Feltételezzük, hogy ezek az osztályok léteznek és BasicCommandPanel-ből származnak
        // vagy legalábbis JPanel-ek és van getEndTurnButton() metódusuk.
        mushroomCommandPanel = new MushroomCommandPanel(/*controller*/);
        insectCommandPanel = new InsectCommandPanel(/*controller*/);

        // Parancspanelek hozzáadása a CardLayout konténerhez
        if (mushroomCommandPanel != null) {
            commandPanelContainer.add(mushroomCommandPanel, MUSHROOM_CMD_PANEL_ID);
        }
        if (insectCommandPanel != null) {
            commandPanelContainer.add(insectCommandPanel, INSECT_CMD_PANEL_ID);
        }
    }

    /**
     * Elrendezi a főbb GUI komponenseket az ablakban.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout(5, 5)); // 5px hézag a komponensek között

        if (mapPanel != null) {
            add(mapPanel, BorderLayout.CENTER);
        }
        if (commandPanelContainer != null) {
            // Egy wrapper panel a commandPanelContainer jobb oldali méretének fixálásához
            JPanel eastPanelWrapper = new JPanel(new BorderLayout());
            eastPanelWrapper.add(commandPanelContainer, BorderLayout.NORTH); // Hogy ne nyúljon szét vertikálisan
            eastPanelWrapper.setPreferredSize(new Dimension(280, 0)); // Preferált szélesség
            add(eastPanelWrapper, BorderLayout.EAST);
        }
        if (statusBarPanel != null) {
            add(statusBarPanel, BorderLayout.SOUTH);
        }
    }

    /**
     * Eseménykezelőket csatol a parancspanelek "Kör vége" gombjaihoz.
     * Feltételezi, hogy a MushroomCommandPanel és InsectCommandPanel (vagy az ősük,
     * a BasicCommandPanel) rendelkezik egy getEndTurnButton() metódussal.
     */
    private void attachActionListenersToCommandPanels() {
        ActionListener endTurnAction = e -> {
            System.out.println("GameWindow: Kör vége esemény érkezett.");
            // Itt kellene a Controllernek jelezni, hogy a játékos befejezte a körét.
            // controller.processEndTurn();
            // A Controller frissítené a játékállapotot, beleértve a currentPlayerId-t.
            // Majd a GameWindow frissülne az új adatokkal.
            // Példa:
            // this.currentPlayerId = controller.getCurrentPlayerId();
            // reDraw();
            // A controller dönthetné el, melyik parancspanel jelenjen meg ezután.
        };

        if (mushroomCommandPanel != null && mushroomCommandPanel.getEndTurnButton() != null) {
            mushroomCommandPanel.getEndTurnButton().addActionListener(endTurnAction);
        }
        if (insectCommandPanel != null && insectCommandPanel.getEndTurnButton() != null) {
            insectCommandPanel.getEndTurnButton().addActionListener(endTurnAction);
        }
    }

    /**
     * Újrarajzolja a térképet és a játékelemeket.
     * Akkor hívódik, ha változás történt az állapotban.
     */
    public void reDraw() {
        System.out.println("GameWindow: reDraw() hívva. Aktuális játékos: " + currentPlayerId);
        if (mapPanel != null) {
            mapPanel.repaint(); // Térkép újrarajzolása
        }
        if (statusBarPanel != null) {
            // Feltételezzük, hogy a StatusBarPanel-nek van ilyen metódusa:
            // statusBarPanel.updatePlayerInfo(this.currentPlayerId);
            // statusBarPanel.updateGameInfo(...); // Egyéb játékállapot infók
            statusBarPanel.repaint();
        }
        // A parancspanel konténer és a benne lévő aktuális panel újrarajzolása is hasznos lehet.
        if (commandPanelContainer != null) {
            commandPanelContainer.revalidate();
            commandPanelContainer.repaint();
        }
        System.out.println("GameWindow: Újrarajzolás befejezve.");
    }

    /**
     * Játék végi állapotot megjelenítő dialógus vagy képernyő kirajzolása.
     * Végrehajtja a grafikus komponensek leállítását.
     */
    public void endGame() {
        System.out.println("GameWindow: endGame() hívva.");

        // Grafikus komponensek "leállítása" (pl. gombok letiltása a parancspaneleken)
        // Ehhez a parancspaneleknek kellene egy disableActions() metódus, vagy a gombjaikhoz getter.
        // Pl.:
        // if (mushroomCommandPanel != null) mushroomCommandPanel.disableActions();
        // if (insectCommandPanel != null) insectCommandPanel.disableActions();

        JOptionPane.showMessageDialog(this,
                "A játéknak vége! Köszönjük, hogy játszottál!",
                "Játék Vége",
                JOptionPane.INFORMATION_MESSAGE);

        // Visszalépés a főmenübe
        goToMenu();
    }

    /**
     * Visszalépés a főmenübe: a MenuWindow aktiválása,
     * a jelenlegi ablak bezárása vagy elrejtése.
     * Láthatósága: public (a specifikáció szerint)
     */
    public void goToMenu() {
        System.out.println("GameWindow: Visszalépés a főmenübe (goToMenu() hívva).");
        // Feltételezzük, hogy a MenuWindow osztály létezik
        MenuWindow menu = new MenuWindow(/*controller*/); // Ha a menünek szüksége van a controllerre
        menu.setVisible(true);

        // Jelenlegi játékablak bezárása
        this.dispose();
    }

    /**
     * Kirajzolja a Gombákhoz tartozó CommandPanelt.
     */
    public void showMushroomCMD() {
        if (commandPanelLayout != null && commandPanelContainer != null && mushroomCommandPanel != null) {
            commandPanelLayout.show(commandPanelContainer, MUSHROOM_CMD_PANEL_ID);
            System.out.println("GameWindow: Gomba parancspanel (MushroomCommandPanel) megjelenítve.");
        } else {
            System.err.println("GameWindow Hiba: Nem lehet megjeleníteni a MushroomCommandPanel-t (nincs inicializálva?).");
        }
    }

    /**
     * Kirajzolja a Rovarokhoz tartozó CommandPanelt.
     */
    public void showInsectCMD() {
        if (commandPanelLayout != null && commandPanelContainer != null && insectCommandPanel != null) {
            commandPanelLayout.show(commandPanelContainer, INSECT_CMD_PANEL_ID);
            System.out.println("GameWindow: Rovar parancspanel (InsectCommandPanel) megjelenítve.");
        } else {
            System.err.println("GameWindow Hiba: Nem lehet megjeleníteni az InsectCommandPanel-t (nincs inicializálva?).");
        }
    }

    // Getter és Setter a currentPlayerId attribútumhoz (ha külsőleg kell módosítani/lekérdezni)
    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
        System.out.println("GameWindow: Aktuális játékos beállítva: " + this.currentPlayerId);
        // Státuszbár frissítése az új játékos ID-val
        if (statusBarPanel != null) {
            // Feltételezzük, hogy a StatusBarPanel-nek van ilyen metódusa:
            // statusBarPanel.updatePlayerInfo(this.currentPlayerId);
            statusBarPanel.repaint(); // Biztonság kedvéért
        }
    }

    // Egy egyszerű main metódus a GameWindow önálló teszteléséhez.
    // Mivel a többi osztály nincs itt definiálva, ez így nem lesz futtatható
    // anélkül, hogy azokat is létrehoznánk (akár dummy implementációval).
    /*
    public static void main(String[] args) {
        // Szükséges lenne egy dummy Controller, MapPanel, stb. implementáció a teszteléshez.
        // Példa:
        // Controller dummyController = new Controller() { /* ... dummy metódusok ... * / };
        // SwingUtilities.invokeLater(() -> {
        //     GameWindow window = new GameWindow(dummyController);
        //     window.setVisible(true);
        // });
    }
    */
}
