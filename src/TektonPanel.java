import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
/**
 * Egy mezőt jelenít meg. Feladata a mezők állapotának vizuális megjelenítése
 * (tektontípustól függően más-más szín).
 * Ősosztálya: BasicPanel.
 */
public class TektonPanel extends JPanel{
    public static int globalTekton = 1;

    private ArrayList<BasicPanel> containedItemPanels;
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
    protected int x; // A JPanel.getX() helyettesíti
    protected int y; // A JPanel.getY() helyettesíti

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
    protected int isSelected = 0; // A BasicPanel.selected helyettesíti

    private Tekton tektonData; // A panelhez tartozó logikai Tekton objektum



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
        if (this.tektonData != null) {
            if (tektonData instanceof UnlivableTekton)
                this.color = new Color(0,0,155);
            else if (tektonData instanceof AllStringsLiveTekton)
                this.color = new Color(0,255,0);
            else if (tektonData instanceof MultipleStringTekton)
                this.color = new Color(255,255,0);
            else if (tektonData instanceof StringCutterTekton)
                this.color = new Color(155,0,0);
            else
                this.color = new Color(255,255,255);
        }

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
                    setSelected();
                    System.out.println("TektonPanel (" + tektonData + ") kiválasztva: " + isSelected);
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

    public void setSelected() {
        if(isSelected == 0)
            isSelected = globalTekton++;
        else{
            isSelected = 0;
            globalTekton--;
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
    public void draw() {
        // Szín frissítése, ha a tektonData típusa megváltozhatna
        if (this.tektonData != null) {
            if (tektonData instanceof UnlivableTekton)
                this.color = new Color(0,0,155);
            else if (tektonData instanceof AllStringsLiveTekton)
                this.color = new Color(0,255,0);
            else if (tektonData instanceof MultipleStringTekton)
                this.color = new Color(255,255,0);
            else if (tektonData instanceof StringCutterTekton)
                this.color = new Color(155,0,0);
            else
                this.color = new Color(255,255,255);
        }

        for (BasicPanel panel : containedItemPanels) {
            panel.draw(this);
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
        if (isSelected != 0) {
            g2d.setColor(Color.YELLOW); // Vagy más kijelölő szín
            g2d.setStroke(new BasicStroke(3)); // Vastagabb vonal a kijelöléshez
            g2d.drawRect(1, 1, getWidth() - 3, getHeight() - 3); // Kicsit beljebb
        }

        g2d.dispose();
    }

    /**
     * Hozzáad egy elemet (pl. SporePanel, MushroomPanel) a TektonPanelhez.
     * Ez egy egyszerűsített megközelítés, OverlayLayout vagy JLayeredPane lenne jobb.
     * @param component A hozzáadandó komponens.
     */
    public void addItemPanel(BasicPanel component) {
        if (component != null) {
            if (getLayout() == null || !(getLayout() instanceof OverlayLayout)) {
                setLayout(null); // fontos, hogy manuálisan pozicionálhassunk
            }

            if (component instanceof InsectPanel) {
                component.setBounds(getWidth() - 34, 4, 30, 30); // jobb felső
            } else if (component instanceof MushroomPanel) {
                component.setBounds(4, 4, 30, 30); // bal felső
            } else if (component instanceof SporePanel) {
                component.setBounds(getWidth() - 34, getHeight() - 34, 30, 30); // jobb alsó
            } else if (component instanceof ShroomStringPanel) {
                // ha lenne ilyen vizuális megjelenítés
                component.setBounds(getWidth() / 2 - 15, getHeight() / 2 - 15, 30, 30); // középre
            }

            add(component);
            containedItemPanels.add(component);
            revalidate();
            repaint();
        }
    }

    public void removeItemPanel(BasicPanel component) {
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
