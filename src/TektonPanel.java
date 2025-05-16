import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Egy mezőt jelenít meg. Feladata a mezők állapotának vizuális megjelenítése
 * (tektontípustól függően más-más szín).
 * Ősosztálya: BasicPanel.
 */
public class TektonPanel extends BasicPanel {

    /**
     * A szín határozza meg, hogy a tekton típusa milyen.
     * Ezt a logikai Tekton objektumból fogjuk lekérni.
     */
    protected Color color; // Ezt a tektonData.getDisplayColor() fogja beállítani

    /**
     * A tekton x és y koordinátája a gameWindow-on való megjelenítésbeli helyéhez.
     * Ezeket a JPanel getX() és getY() metódusai biztosítják.
     * Ha logikai rácskoordinátákról van szó, azokat a 'tektonData'-ban kell tárolni.
     */
    // protected int x; // A JPanel.getX() helyettesíti
    // protected int y; // A JPanel.getY() helyettesíti

    /**
     * A tekton szélessége és magassága.
     * Feltételezzük, hogy négyzet alakú. A JPanel getWidth()/getHeight() adja a méretet.
     * Ezt a konstruktorban beállítjuk.
     */
    protected int size;

    /**
     * Azt jelzi, hogy ki van-e választva.
     * Ezt a BasicPanel `selected` attribútuma és metódusai kezelik.
     */
    // protected boolean isSelected; // A BasicPanel.selected helyettesíti

    private Tekton tektonData; // A panelhez tartozó logikai Tekton objektum

    // Lista a Tektonon lévő elemek paneljeinek (pl. SporePanel, MushroomPanel, InsectPanel)
    // Ezeket a TektonPanel fölé kellene rajzolni, vagy a TektonPanel rajzolná őket
    // a saját paintComponent-jében. Most csak egy placeholder.
    private List<Component> containedItemPanels;


    /**
     * Konstruktor a TektonPanel számára.
     * @param tektonData A logikai Tekton objektum, amit ez a panel megjelenít.
     * @param size A Tekton panel kívánt mérete (szélesség és magasság).
     */
    public TektonPanel(Tekton tektonData, int size) {
        super(); // BasicPanel konstruktorának hívása
        this.tektonData = tektonData;
        this.size = size;

        // Méret beállítása
        setPreferredSize(new Dimension(this.size, this.size));
        // A szín beállítása a TektonType alapján
        this.color = (this.tektonData != null) ? this.tektonData.getDisplayColor() : Color.LIGHT_GRAY;

        // Alapértelmezetten átlátszó, hogy a szín érvényesüljön
        // De ha a BasicPanel már setOpaque(false)-ra van állítva, és itt színt rajzolunk, akkor
        // a setOpaque(true) kell, vagy a BasicPanel ne legyen átlátszó.
        // Most feltételezzük, hogy a BasicPanel nem rajzol hátteret, ha a leszármazott színt ad meg.
        setOpaque(true); // Hogy a háttérszín látszódjon

        this.containedItemPanels = new ArrayList<>();

        // Egér eseménykezelő a kiválasztáshoz
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Itt lehetne a kiválasztási logikát kezelni,
                    // pl. egy Controller értesítése.
                    // Most egyszerűen váltogatjuk a 'selected' állapotot.
                    setSelected(!isSelected());
                    System.out.println("TektonPanel (" + tektonData + ") kiválasztva: " + isSelected());
                    // Ha a selectTekton() metódusnak kellene itt lefutnia:
                    // selectTekton(); // Bár a metódus neve inkább getter-re utal
                }
            }
        });

        // Tooltip beállítása
        if (this.tektonData != null) {
            setToolTipText(this.tektonData.toString());
        }
    }

    /**
     * Visszaadja a panelhez tartozó logikai Tekton objektumot.
     * @return A Tekton objektum.
     */
    public Tekton selectTekton() {
        // A metódus neve ("selectTekton") lehet félrevezető, ha csak egy getter.
        // Ha a kiválasztási logikát is itt kellene végezni, akkor a mouseClicked-ben
        // kellene a `selected` állapotot beállítani és esetleg eseményt váltani.
        // Jelenleg getterként funkcionál.
        return this.tektonData;
    }

    /**
     * Kirajzolja a tektont a típusának megfelelő színnel és a rajta
     * található elemek rétegeivel együtt.
     * A tényleges rajzolás a paintComponent-ben történik.
     * Ez a metódus újrarajzolási kérést indít.
     */
    @Override
    public void draw() {
        // Szín frissítése, ha a tektonData típusa megváltozhatna
        if (this.tektonData != null) {
            this.color = this.tektonData.getDisplayColor();
            setToolTipText(this.tektonData.toString()); // Tooltip frissítése is
        }
        repaint(); // Újrarajzolás kérése
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Meghívja a BasicPanel paintComponent-jét

        Graphics2D g2d = (Graphics2D) g.create();

        // Tekton háttérszínének rajzolása
        g2d.setColor(this.color);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Rács vonalak rajzolása (opcionális, a jobb vizuális elkülönítésért)
        g2d.setColor(this.color.darker());
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);


        // Kijelölés jelzése (ha a BasicPanel nem kezelné specifikusan)
        if (isSelected()) {
            g2d.setColor(Color.YELLOW); // Vagy más kijelölő szín
            g2d.setStroke(new BasicStroke(3)); // Vastagabb vonal a kijelöléshez
            g2d.drawRect(1, 1, getWidth() - 3, getHeight() - 3); // Kicsit beljebb
        }

        // Itt lehetne a "rajta található elemek rétegeinek" rajzolása.
        // Ha ezek külön JPanel-ek (pl. SporePanel, MushroomPanel), akkor azokat
        // a TektonPanel-hez kellene hozzáadni (pl. OverlayLayout-tal, vagy JLayeredPane-nel
        // a GameWindow szintjén).
        // Ha itt, a paintComponent-ben rajzolnánk őket:
        // for (Component itemPanel : containedItemPanels) {
        //     // Pozícionálni és kirajzolni az itemPanel-t a g2d kontextusra
        //     // Ez bonyolultabb, ha azok is JPanel-ek.
        //     // Egyszerűbb, ha az itemPanel-ek a TektonPanel-en vannak elhelyezve egy LayoutManagerrel.
        // }
        // Most feltételezzük, hogy a "rétegek" kezelése a GameWindow vagy egy magasabb szintű
        // konténer feladata, ami ezeket a TektonPanel fölé helyezi.

        g2d.dispose();
    }

    /**
     * Hozzáad egy elemet (pl. SporePanel, MushroomPanel) a TektonPanelhez.
     * Ez egy egyszerűsített megközelítés, OverlayLayout vagy JLayeredPane lenne jobb.
     * @param component A hozzáadandó komponens.
     */
    public void addItemPanel(Component component) {
        if (component != null) {
            // Ahhoz, hogy az itemPanel-ek a TektonPanel-en belül látszódjanak,
            // és a TektonPanel háttérszíne ne takarja el őket, ha az itemPanel is setOpaque(false),
            // vagy a TektonPanel-nek megfelelő LayoutManager kell.
            // Egy egyszerű OverlayLayout:
            if (getLayout() == null || !(getLayout() instanceof OverlayLayout)) {
                setLayout(new OverlayLayout(this));
            }
            add(component);
            containedItemPanels.add(component);
            revalidate();
            repaint();
        }
    }

    public void removeItemPanel(Component component) {
        if (component != null) {
            remove(component);
            containedItemPanels.remove(component);
            revalidate();
            repaint();
        }
    }


    // Getter a Tekton adatokhoz
    public Tekton getTektonData() {
        return tektonData;
    }

    // Getter/Setter a színhez (ha kívülről is állítható lenne, függetlenül a TektonType-tól)
    public Color getTektonColor() {
        return color;
    }

    public void setTektonColor(Color color) {
        this.color = color;
        repaint();
    }


}
