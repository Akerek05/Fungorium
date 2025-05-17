import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout; // Egy egyszerű elrendezéshez
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Absztrakt osztály, ami a többi commandpanelnek az alapját képzi.
 * Olyan eseményeket tartalmaz, amelyek nem közvetlenül tartoznak sem a gombához,
 * sem a rovarhoz, például körváltás.
 * A BasicCommandPanel felelős az általános, nem entitás-specifikus
 * parancsokhoz tartozó gombok megjelenítéséért és alapvető kezeléséért.
 */
public abstract class BasicCommandPanel extends JPanel {

    /**
     * Gomb, amivel a játékos jelezheti, hogy befejezte a körét.
     */
    protected JButton endTurnButton;
    protected Controller controller;

    /**
     * Alapértelmezett konstruktor.
     * Létrehozza a panelt és inicializálja az alapvető komponenseket.
     */
    public BasicCommandPanel() {
        super(); // JPanel ősosztály konstruktorának hívása
        // Alapértelmezett elrendezés beállítása (opcionális, de gyakori)
        setLayout(new FlowLayout(FlowLayout.CENTER));
        draw(); // Gombok kirajzolása
    }

    /**
     * Konstrukor controller
     * @param controller
     */
    public BasicCommandPanel(Controller controller) {
        super();
        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        draw(); // Gombok kirajzolása
    }

    /**
     * Kirajzolja (létrehozza és hozzáadja a panelhez) a gombokat,
     * amiket a felhasználó használhat az események elindításához.
     * Ebben az alaposztályban az "Kör vége" gombot inicializálja.
     * Leszármazott osztályok felüldefiniálhatják vagy kiegészíthetik ezt a metódust
     * további gombok hozzáadásával.
     */
    public void draw() {
        endTurnButton = new JButton("Kör vége");
        // Itt lehetne hozzáadni az ActionListener-t, ha az alaposztályban
        // definiált lenne a körváltás logikája, vagy egy absztrakt metódus hívása
        // pl. endTurnButton.addActionListener(e -> handleEndTurn());

        this.add(endTurnButton);
    }

    /**
     * Egy példa arra, hogyan kezelhetnénk a kör vége eseményt.
     * Ezt a metódust az endTurnButton ActionListener-je hívhatná meg.
     * Az absztrakt osztály miatt ezt lehet, hogy a leszármazottaknak kellene
     * konkrétan implementálniuk, vagy egy központi játékvezérlőnek kellene jelezni.
     *
     * Példa ActionListener hozzáadására a draw() metódusban vagy a konstruktorban:
     *
     * endTurnButton.addActionListener(new ActionListener() {
     *     @Override
     *     public void actionPerformed(ActionEvent e) {
     *         // Itt történne a körváltással kapcsolatos logika
     *         // Például egy esemény kiváltása vagy egy kontroller metódus hívása
     *         System.out.println("Kör vége gomb megnyomva.");
     *         // onTurnEnded(); // Egy potenciális absztrakt metódus hívása
     *     }
     * });
     *
     * // Ha lenne egy ilyen absztrakt metódus:
     * // protected abstract void onTurnEnded();
     */

    // Getter a gombhoz, ha külső osztályoknak (pl. teszteknek) szükségük van rá
    // A protected láthatóság miatt a leszármazottak közvetlenül is elérhetik.
    public JButton getEndTurnButton() {
        return endTurnButton;
    }
}
