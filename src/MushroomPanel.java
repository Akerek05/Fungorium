import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage; // Az ikonhoz

/**
 * A játékban megjelenített gombákhoz tartozó grafikus felület,
 * amely a játékos számára popup menükön keresztül lehetővé teszi
 * az adott gomba műveleteinek végrehajtását (pl. terjesztés, fejlesztés).
 * Vizuálisan jelöli, hogy egy adott Tekton mezőn milyen gomba található,
 * és reagál az eseményekre.
 * Ősosztálya: BasicPanel.
 */
public class MushroomPanel extends BasicPanel {

    /**
     * A tulajdonságok listája (kontextus menü), amelyek a gombához tartoznak.
     */
    protected JPopupMenu list; // A specifikáció 'list'-nek nevezi, de 'contextMenu' jobb lenne
    private Mushroom mushroomData; // A panelhez tartozó gomba adatai
    protected boolean selected = false;
    /**
     * Konstruktor a MushroomPanel számára.
     * @param mushroom Az Mushroom objektum, amit ez a panel megjelenít.
     * @param icon A gombához tartozó kép/ikon (opcionális, a BasicPanel kezeli).
     */
    public MushroomPanel(Mushroom mushroom, BufferedImage icon) {
        super(icon); // BasicPanel konstruktorának hívása az ikonnal
        this.mushroomData = mushroom;

        setOpaque(false); // Átlátszó háttér, hogy a Tekton mező háttere látszódjon alatta

        // JPopupMenu (kontextus menü) inicializálása
        list = new JPopupMenu();

        if (selected) {

        } else {

        }

        setLayout(null); // nem kell semmilyen layout, mert kézzel rajzolunk

        list = new JPopupMenu();
        JMenuItem detailsItem = new JMenuItem("Details...");
        detailsItem.addActionListener(e -> showMushroomDetails());
        list.add(detailsItem);

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
                if (e.isPopupTrigger()) {
                    list.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        draw();
    }

    /**
     * Kirajzolja a gombát a pályán (ezt az ősosztály `paintComponent` végzi az ikonnal),
     * és frissíti az esetleges belső állapotokat (pl. tooltip).
     * Mivel a MushroomPanel-en nincs saját rajzolandó komponens (mint az InsectPanel-en az AP label),
     * ennek a metódusnak a fő feladata az állapot szinkronizálása és a tooltip frissítése.
     */
    @Override
    public void draw() {
        repaint();
    }

    /**
     * Custom paintComponent method to handle painting of the panel, including the icon
     * and selection indicator. If an icon exists, it is drawn in the upper-right corner
     * of the panel with a fixed margin. If the panel is marked as selected, a blue
     * border is drawn around it.
     *
     * @param g The Graphics object used to draw the component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (icon != null) {
            int iconSize = Math.min(getWidth(), getHeight()) / 2; // pl. 40x40, ha panel 80x80
            int x = getWidth() - iconSize - 2; // jobb felső sarok, 2px margó
            int y = 2;
            g.drawImage(icon, x, y, iconSize, iconSize, this);
        }

        if (selected) {
            g.setColor(Color.BLUE);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    /**
     * Displays a dialog with detailed information about the mushroom associated with the panel.
     * The information includes:
     * - Player ID
     * - Mushroom ID
     * - Mushroom Type (class)
     * - Mushroom HP (lifeTime)
     * - Spore spawn time
     *
     * If no mushroom data is available (mushroomData is null), the method does nothing.
     * The information is displayed in a JOptionPane with an informational message type.
     */
    private void showMushroomDetails() {
        if (mushroomData != null) {
            JOptionPane.showMessageDialog(this,
                    "Player id: " + mushroomData.playerID + "\n" +
                            "Mushroom id: " + mushroomData.id + "\n" +
                            "Type: " +  mushroomData.getClass()+ "\n" +
                            "HP: " + mushroomData.lifeTime+ "\n"+
                            "Spore Spread: "+ mushroomData.sporeSpawnTime+ "\n",
                    "Mushroom Details",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Retrieves the mushroom data associated with the panel.
     *
     * @return The mushroom object contained in this panel, or null if no mushroom data is available.
     */
    public Mushroom getMushroomData() {
        return mushroomData;
    }
}
