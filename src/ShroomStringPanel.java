import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * A pályán lévő fonalak megjelenítéséért szolgáló panel.
 * Ősosztálya: BasicPanel.
 */
public class ShroomStringPanel extends BasicPanel {

    private ShroomString stringData; // A megjelenítendő fonal adatai

    /**
     * Konstruktor a ShroomStringPanel számára.
     * @param stringData A ShroomString objektum, amit ez a panel megjelenít.
     *                   Ez tartalmazza a fonal végpontjait a panel saját
     *                   koordináta-rendszerében.
     */
    public ShroomStringPanel(ShroomString stringData) {
        super(); // BasicPanel konstruktorának hívása
        this.stringData = stringData;
        // Fontos, hogy a panel átlátszó legyen, hogy csak a fonal látszódjon,
        // és ne takarja ki a mögötte lévő elemeket (pl. a pályát vagy más paneleket).
        setOpaque(false);
    }

    /**
     * Kirajzolja a fonalat a pályán (a panel saját területén belül).
     * Ezt a metódust a Swing hívja meg a repaint() kérésre.
     * A specifikáció szerinti public void draw() metódus itt inkább
     * egy állapotfrissítő/újrarajzolást kérő metódus lehetne.
     * A tényleges rajzolás a paintComponent-ben történik.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Meghívja a BasicPanel paintComponent-jét (pl. kijelöléshez)

        if (stringData == null || stringData.startTek == null || stringData.disTek == null) {
            return; // Nincs mit rajzolni
        }

        Graphics2D g2d = (Graphics2D) g.create(); // Létrehozunk egy másolatot a Graphics objektumról

        // Rajzolási attribútumok beállítása
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Szebb vonalak
        g2d.setColor(Color.RED); // Alapértelmezett szín piros
        g2d.setStroke(new BasicStroke(2f)); // Vonalvastagság

        // Vonal kirajzolása a megadott pontok között
        // A pontok a panel (0,0) pontjához képest relatívak.
        g2d.draw(new Line2D.Double(
                stringData.startPointPanelCoords.x,
                stringData.startPointPanelCoords.y,
                stringData.endPointPanelCoords.x,
                stringData.endPointPanelCoords.y
        ));

        // Ha az ősosztály icon attribútumát textúraként használnánk a vonalhoz,
        // az bonyolultabb lenne (pl. TexturePaint használata).
        // Jelenleg az icon-t nem használjuk a fonal rajzolásához.

        g2d.dispose(); // Felszabadítjuk a Graphics másolatot
    }

    /**
     * A specifikációban kért public draw() metódus.
     * Mivel a tényleges rajzolás a paintComponent-ben történik,
     * ez a metódus itt leginkább egy újrarajzolási kérést indíthat,
     * vagy frissítheti a stringData-t, ha az megváltozott.
     */
    @Override
    public void draw() {
        // Ha a stringData megváltozhatna kívülről, itt lehetne frissíteni.
        // Például: this.stringData = getUpdatedStringDataFromModel();
        repaint(); // Újrarajzolás kérése, ami a paintComponent() hívását eredményezi
    }

    /**
     * Frissíti a panelen megjelenített fonal adatait.
     * @param stringData Az új ShroomString objektum.
     */
    public void updateStringData(ShroomString stringData) {
        this.stringData = stringData;
        draw(); // Újrarajzolás az új adatokkal
    }

    public ShroomString getStringData() {
        return stringData;
    }


}
