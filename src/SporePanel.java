import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * A pályán lévő spórák megjelenítésére szolgáló panel.
 * Ősosztálya: BasicPanel.
 */
public class SporePanel extends BasicPanel {

    private Spore sporeData; // A megjelenítendő spóra adatai

    /**
     * Konstruktor a SporePanel számára.
     * @param sporeData A Spore objektum, amit ez a panel megjelenít.
     * @param icon A spórához tartozó kép/ikon (opcionális, a BasicPanel kezeli).
     *             Ha null, akkor a panel egy alapértelmezett spórát rajzol.
     */
    public SporePanel(Spore sporeData, BufferedImage icon) {
        super(icon); // BasicPanel konstruktorának hívása
        this.sporeData = sporeData;
        setOpaque(false);
    }

    /**
     * Konstruktor a SporePanel számára ikon nélkül.
     * @param sporeData A Spore objektum, amit ez a panel megjelenít.
     */
    public SporePanel(Spore sporeData) {
        this(sporeData, null); // Meghívja a másik konstruktort null ikonnal
    }


    /**
     * Kirajzolja a spórát a panel területén belül.
     * Ha van ikon beállítva a BasicPanelben, akkor az ősosztály paintComponent metódusa
     * azt rajzolja. Ha nincs ikon, akkor ez a metódus rajzol egy alapértelmezett spóra alakzatot.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Meghívja a BasicPanel paintComponent-jét (ikon, kijelölés)

        // Csak akkor rajzolunk alapértelmezett spórát, ha nincs ikon beállítva az ősosztályban
        if (icon == null && sporeData != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Spóra mérete a panel méretéhez igazítva
            int diameter = Math.min(getWidth() - 4, getHeight() - 4); // Kis margó
            if (diameter < 2) diameter = 2; // Minimum méret

            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            // Spóra kirajzolása (pl. egy kis kör)
            g2d.fill(new Ellipse2D.Double(x, y, diameter, diameter));

            // Opcionális körvonal
            // g2d.setColor(sporeData.getSporeColor().darker());
            // g2d.draw(new Ellipse2D.Double(x, y, diameter, diameter));

            g2d.dispose();
        }
    }

    /**
     * A specifikációban kért public draw() metódus.
     * A tényleges rajzolás a paintComponent-ben történik. Ez a metódus
     * újrarajzolási kérést indíthat, vagy frissítheti a sporeData-t,
     * ha az megváltozott.
     */
    @Override
    public void draw() {
        repaint(); // Újrarajzolás kérése, ami a paintComponent() hívását eredményezi
    }

    /**
     * Frissíti a panelen megjelenített spóra adatait.
     * @param sporeData Az új Spore objektum.
     */
    public void updateSporeData(Spore sporeData) {
        this.sporeData = sporeData;
        draw(); // Újrarajzolás az új adatokkal
    }

    /**
     * A spora adatanak visszaadasa
     * @return
     */
    public Spore getSporeData() {
        return sporeData;
    }


}
