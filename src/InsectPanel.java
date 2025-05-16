import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage; // Az ikonhoz

/**
 * A pályán lévő rovarok megjelenítésére szolgáló panel.
 * Felelős a rovarok akciópontjainak megjelenítéséért.
 * Ősosztálya: BasicPanel.
 */
public class InsectPanel extends BasicPanel {

    //Rovar akciópontjai
    protected JLabel actionPointsLabel;

    //Rovar tulajdonságai (pl jobb klikkes menü, hover menü...)
    protected JPopupMenu list; // A 'list' név lehet félrevezető, 'contextMenu' jobb lenne

    private Insect insectData; // A panelhez tartozó rovar adatai

    /**
     * Konstruktor az InsectPanel számára.
     * @param insect Az Insect objektum, amit ez a panel megjelenít.
     * @param icon A rovarhoz tartozó kép/ikon (opcionális, a BasicPanel kezeli).
     */
    public InsectPanel(Insect insect, BufferedImage icon) {
        super(icon); // BasicPanel konstruktorának hívása az ikonnal
        this.insectData = insect;

        // Panel alapbeállításai
        setLayout(new BorderLayout()); // Hogy a JLabel-t el tudjuk helyezni
        setOpaque(false); // Legyen átlátszó, ha a BasicPanel háttérszínét nem akarjuk felülírni,
        // vagy ha az ikon tartalmaz átlátszóságot.
        // Ha az ikon téglalap alakú és kitölti a panelt, akkor ez nem lényeges.

        // Akciópontokat megjelenítő JLabel inicializálása
        actionPointsLabel = new JLabel();
        actionPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        actionPointsLabel.setFont(new Font("Arial", Font.BOLD, 10));
        actionPointsLabel.setForeground(Color.WHITE); // Fehér szöveg (jól mutat sötét ikonon)
        // Egy kis háttér a labelnek a jobb olvashatóságért, ha az ikon színes
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        labelPanel.setOpaque(false); // A label panel legyen átlátszó
        labelPanel.add(actionPointsLabel);
        // Elhelyezzük a labelt a panel aljára
        add(labelPanel, BorderLayout.SOUTH);


        // JPopupMenu (kontextus menü) inicializálása
        list = new JPopupMenu();
        // Menüelemek hozzáadása (példa)
        JMenuItem detailsItem = new JMenuItem("Részletek...");
        detailsItem.addActionListener(e -> showInsectDetails());
        list.add(detailsItem);

        // Egér eseménykezelő a kontextus menü megjelenítéséhez (jobb klikk)
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) { // Jobb klikk ellenőrzése
                    list.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // Kezdeti kirajzolás/adatfrissítés
        draw();
    }

    /**
     * Konstruktor az InsectPanel számára ikon nélkül.
     * @param insect Az Insect objektum, amit ez a panel megjelenít.
     */
    public InsectPanel(Insect insect) {
        this(insect, null); // Meghívja a másik konstruktort null ikonnal
    }


    /**
     * Kirajzolja a rovart a pályán (az ikon által az ősosztályban),
     * és frissíti a kijelzett akciópontokat.
     */
    @Override
    public void draw() {
        // Az ősosztály paintComponent metódusa felelős az ikon kirajzolásáért.
        // A draw() itt inkább az állapotfrissítésről szól (pl. JLabel tartalma).

        if (insectData != null) {
            actionPointsLabel.setText("AP: " + insectData.getActionPoints());
            // A BasicPanel repaint() metódusát kellene hívni, ha vizuális változás van
            // (pl. ikon csere, kijelölés), de a JLabel frissítése automatikusan újrarajzolja magát.
        } else {
            actionPointsLabel.setText("N/A");
        }
        // Ha a BasicPanel `draw` metódusa is csinál valamit, itt hívhatjuk:
        // super.draw();

        // Szükség esetén explicit repaint, ha az ikon vagy a kijelölés is változott
        // és azt nem a setterek (setIcon, setSelected) váltották ki.
        repaint();
    }

    /**
     * Frissíti a panelen megjelenített rovar adatait.
     * @param insect Az új Insect objektum.
     */
    public void updateInsectData(Insect insect) {
        this.insectData = insect;
        draw(); // Újrarajzolás az új adatokkal (főleg az AP label frissítése)
    }

    /**
     * Példa metódus a rovar részleteinek megjelenítésére (a kontextus menüből hívva).
     */
    private void showInsectDetails() {
        if (insectData != null) {
            JOptionPane.showMessageDialog(this,
                    "Rovar id-je: " + insectData.id + "\n" +
                            "Akciópontok: " + insectData.getActionPoints() + "\n" +
                            "Játékos id-je:" + insectData.playerID,
                    "Rovar Részletei",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Getter a JLabelhez, ha külső osztálynak szüksége van rá
    public JLabel getActionPointsLabel() {
        return actionPointsLabel;
    }

    // Getter a JPopupMenu-hez
    public JPopupMenu getContextMenu() { // Átneveztem 'list'-ről 'contextMenu'-re
        return list;
    }

    // Getter a rovar adataihoz
    public Insect getInsectData() {
        return insectData;
    }


}