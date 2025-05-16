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

        // Egér eseménykezelő a kontextus menü megjelenítéséhez (jobb klikk)
        // és a panel kiválasztásához (bal klikk)
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) { // Jobb klikk
                    list.show(e.getComponent(), e.getX(), e.getY());
                } else if (SwingUtilities.isLeftMouseButton(e)) { // Bal klikk
                    // Itt lehetne logikát hozzáadni a panel "kiválasztásához"
                    // Például a Controller értesítése, hogy ez a gomba lett kiválasztva
                    // és a BasicPanel selected állapotának beállítása.
                    setSelected(!isSelected()); // Toggle selection
                    System.out.println(mushroomData.id + " kiválasztva: " + isSelected());
                    // A Controller értesítése, ha szükséges
                    // if (controller != null) controller.selectMushroom(mushroomData, isSelected());
                }
            }

            // A maybeShowPopup külön kezelése a cross-platform jobb klikkhez
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    list.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * Kirajzolja a gombát a pályán (ezt az ősosztály `paintComponent` végzi az ikonnal),
     * és frissíti az esetleges belső állapotokat (pl. tooltip).
     * Mivel a MushroomPanel-en nincs saját rajzolandó komponens (mint az InsectPanel-en az AP label),
     * ennek a metódusnak a fő feladata az állapot szinkronizálása és a tooltip frissítése.
     */
    @Override
    public void draw() {

    }

    // Getter a gomba adataihoz
    public Mushroom getMushroomData() {
        return mushroomData;
    }
}
