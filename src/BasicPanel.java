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
     * Konstruktor, amely lehetővé teszi az ikon kezdeti beállítását.
     * @param icon A panelhez tartozó BufferedImage ikon.
     */
    public BasicPanel(BufferedImage icon) {
        super(); // JPanel ősosztály konstruktorának hívása
        this.selected = false; // Alapértelmezetten nincs kiválasztva
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
        super.paintComponent(g);

        if (icon != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int targetSize = Math.min(panelWidth, panelHeight) - 4; // kis margó
            int x = (panelWidth - targetSize) / 2;
            int y = (panelHeight - targetSize) / 2;

            g.drawImage(icon, x, y, targetSize, targetSize, this);
        }

        if (selected) {
            g.setColor(Color.BLUE);
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