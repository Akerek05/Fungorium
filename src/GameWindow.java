import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A fő játékképernyőt jeleníti meg ez az osztály.
 * Megjeleníti a térképet, a parancs paneleket, státuszbárt.
 * Ősosztálya: JFrame.
 */
public class GameWindow extends JFrame {

    private int currentPlayerId;
    private Controller controller;

    private JPanel gamePanel;
    private StatusPanel statusBarPanel;
    private MushroomCommandPanel mushroomCommandPanel;
    private InsectCommandPanel insectCommandPanel;
    protected ArrayList<TektonPanel> tektonPanels;

    /**
     * GameWindow konstruktor.
     * @param controller A játék vezérlő objektuma.
     */
    public GameWindow(Controller controller) {
        this.controller = controller;
        setTitle("Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Képernyő közepére

        // Komponensek inicializálása
        statusBarPanel = new StatusPanel(controller);
        mushroomCommandPanel = new MushroomCommandPanel(controller);
        insectCommandPanel = new InsectCommandPanel(controller);
        tektonPanels = new ArrayList<>();

        // Fő panel BorderLayout-tal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Felső státuszbár
        mainPanel.add(statusBarPanel, BorderLayout.NORTH);

        // Játékterület (pl. térkép vagy game logic)
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.LIGHT_GRAY); // teszteléshez
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        // Alsó command panelek CardLayout-tal
        JPanel commandPanelContainer = new JPanel(new CardLayout());
        commandPanelContainer.add(mushroomCommandPanel, "MUSHROOM");
        commandPanelContainer.add(insectCommandPanel, "INSECT");
        mainPanel.add(commandPanelContainer, BorderLayout.SOUTH);

        this.setContentPane(mainPanel);
        this.setVisible(true);

        // Alapértelmezésben mutatjuk a gombapanelt
        showMushroomCMD();
    }

    /**
     * Újrarajzolja a térképet és a játékelemeket.
     * Akkor hívódik, ha változás történt az állapotban.
     */
    public void reDraw() {
        for(TektonPanel tektonPanel : tektonPanels) {
            tektonPanel.draw();
        }
        statusBarPanel.draw();
        mushroomCommandPanel.draw();
        insectCommandPanel.draw();
    }

    /**
     * Játék végi állapotot megjelenítő dialógus vagy képernyő kirajzolása.
     * Végrehajtja a grafikus komponensek leállítását.
     */
    public void endGame() {
        //TODO: kiírja a pontokat sorba
        goToMenu();
    }

    public void goToMenu() {
        controller.showMenu();
    }

    private void showCard(String name) {
        Container parent = mushroomCommandPanel.getParent();
        if (parent instanceof JPanel) {
            CardLayout layout = (CardLayout) ((JPanel) parent).getLayout();
            layout.show((JPanel) parent, name);
        }
    }

    public void showMushroomCMD() {
        showCard("MUSHROOM");
    }

    public void showInsectCMD() {
        showCard("INSECT");
    }

    public ArrayList<TektonPanel> getTektonPanels(){
        return tektonPanels;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
    public void selectTektonPanel(TektonPanel tektonPanel) {}
}
