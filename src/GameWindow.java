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
    private Controller controller;

    private StatusPanel statusBarPanel;
    private MushroomCommandPanel mushroomCommandPanel;
    private InsectCommandPanel insectCommandPanel;
    private TektonPanel tektonPanel;

    /**
     * GameWindow konstruktor.
     * @param controller A játék vezérlő objektuma.
     */
    public GameWindow(Controller controller) {

    }

    /**
     * Újrarajzolja a térképet és a játékelemeket.
     * Akkor hívódik, ha változás történt az állapotban.
     */
    public void reDraw() {

    }

    /**
     * Játék végi állapotot megjelenítő dialógus vagy képernyő kirajzolása.
     * Végrehajtja a grafikus komponensek leállítását.
     */
    public void endGame() {

    }

    public void goToMenu() {

    }

    /**
     * Kirajzolja a Gombákhoz tartozó CommandPanelt.
     */
    public void showMushroomCMD() {

    }

    /**
     * Kirajzolja a Rovarokhoz tartozó CommandPanelt.
     */
    public void showInsectCMD() {

    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
}
