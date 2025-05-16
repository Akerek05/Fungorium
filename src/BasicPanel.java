import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Absztrakt osztály, ami a többi megjelenítendő panelnek az alapját képzi.
 * Tartalmazza a megjelenítéshez szükséges grafikus dolgokat,
 * például a képeket/ikonokat, illetve azt, hogy az adott panel
 * ki van-e jelölve.
 */
public abstract class BasicPanel extends JPanel {
    /**
     * A x koordnitaja a kepernyon
     */
    private int x;
    /**
     * Az y koordinataja a kepernyon
     */
    private int y;
    /**
     * Az adott panelhez tartozó ikon/kép.
     */
    protected BufferedImage icon;

    /**
     * Jelzi, hogy a panel jelenleg ki van-e választva.
     * Alapértelmezetten false.
     */
    protected boolean selected;

    /**
     * Alapértelmezett konstruktor.
     * Inicializálja a panel alapállapotát (pl. nincs kiválasztva).
     */
    public BasicPanel() {
        super(); // JPanel ősosztály konstruktorának hívása
        this.selected = false; // Alapértelmezetten nincs kiválasztva
        // Az ikont a leszármazott osztály vagy egy setter állíthatja be
    }

    /**
     * Konstruktor, amely lehetővé teszi az ikon kezdeti beállítását.
     * @param icon A panelhez tartozó BufferedImage ikon.
     */
    public BasicPanel(BufferedImage icon) {
        this(); // Meghívja az alapértelmezett konstruktort
        this.icon = icon;
    }

    /**
     * Alapértelmezett kirajzolási logika előkészítése vagy elindítása a panel tartalmára.
     * Ezt a metódust a származtatott osztályok felülírják a saját típusuknak
     * megfelelő tartalom konfigurálásához vagy specifikus rajzolási műveletek
     * elvégzéséhez (amelyek hatással lehetnek a paintComponent-re).
     *
     * Fontos megjegyzés: A tényleges Swing rajzolás a paintComponent metódusban történik.
     * Ez a 'draw' metódus inkább egy koncepcionális "rajzold ki magad" vagy "állítsd be a rajzoláshoz"
     * utasítás, amit a leszármazottaknak kell implementálniuk.
     */
    public abstract void draw();


    public void draw(TektonPanel tektonPanel) {

    }
    /**
     * Felüldefiniált metódus a JPanel-ből a komponens tényleges kirajzolásához.
     * Ez a metódus felelős az ikon és a kijelölés vizuális megjelenítéséért.
     * A leszármazott osztályok kiegészíthetik ezt további egyedi rajzolással.
     * @param g A Graphics kontextus, amire rajzolni kell.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Fontos az ősosztály paintComponent metódusának hívása

        // Ikon kirajzolása, ha van
        if (icon != null) {
            // Egyszerű kirajzolás a bal felső sarokba.
            // A pozícionálás és méretezés finomítható (pl. középre, skálázva stb.)
            g.drawImage(icon, 0, 0, this);
        }


        // Kijelölés jelzése (pl. egy kerettel)
        if (selected) {
            g.setColor(Color.BLUE); // Válasszunk egy jól látható színt a kijelöléshez
            // Rajzoljunk egy keretet a panel széleire
            // A vonalvastagság vagy stílus tovább finomítható
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    // --- Getters and Setters ---

    /**
     * Visszaadja a panel ikonját.
     * @return A panelhez tartozó BufferedImage, vagy null, ha nincs beállítva.
     */
    public BufferedImage getIcon() {
        return icon;
    }

    /**
     * Beállítja a panel ikonját.
     * A panel újrarajzolását kéri, hogy a változás megjelenjen.
     * @param icon Az új BufferedImage ikon.
     */
    public void setIcon(BufferedImage icon) {
        this.icon = icon;
        repaint(); // Újrarajzolás kérése, hogy az új ikon megjelenjen
    }

    /**
     * Visszaadja, hogy a panel ki van-e jelölve.
     * @return true, ha a panel ki van jelölve, egyébként false.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Beállítja a panel kijelöltségi állapotát.
     * A panel újrarajzolását kéri, hogy a változás megjelenjen.
     * @param selected true, ha a panelt kijelöltnek kell tekinteni, egyébként false.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint(); // Újrarajzolás kérése, hogy a kijelölés állapota frissüljön
    }
}