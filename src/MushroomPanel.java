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
    private Controller controller; // Referencia a Controllerre az akciókhoz

    /**
     * Konstruktor a MushroomPanel számára.
     * @param mushroom Az Mushroom objektum, amit ez a panel megjelenít.
     * @param icon A gombához tartozó kép/ikon (opcionális, a BasicPanel kezeli).
     * @param controller A játék vezérlője az akciók indításához.
     */
    public MushroomPanel(Mushroom mushroom, BufferedImage icon, Controller controller) {
        super(icon); // BasicPanel konstruktorának hívása az ikonnal
        this.mushroomData = mushroom;
        this.controller = controller;

        setOpaque(false); // Átlátszó háttér, hogy a Tekton mező háttere látszódjon alatta

        // JPopupMenu (kontextus menü) inicializálása
        list = new JPopupMenu();
        initializeContextMenu();

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
                    System.out.println(mushroomData.getName() + " kiválasztva: " + isSelected());
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

        // Kezdeti állapot beállítása (a BasicPanel paintComponent gondoskodik a rajzolásról)
        // A draw() itt inkább az állapotfrissítést jelenti, ha lenne mit.
        // Mivel nincs külön JLabel vagy más komponens a MushroomPanel-en belül,
        // a draw() hívása itt kevésbé kritikus, mint az InsectPanelnél volt az AP label miatt.
        // A tooltipet beállíthatjuk itt:
        updateTooltip();
    }

    /**
     * Konstruktor ikon és controller nélkül (kevésbé praktikus, de a teljesség kedvéért).
     * @param mushroom Az Mushroom objektum, amit ez a panel megjelenít.
     */
    public MushroomPanel(Mushroom mushroom) {
        this(mushroom, null, null);
    }

    /**
     * Inicializálja a kontextus menü (JPopupMenu) elemeit.
     */
    private void initializeContextMenu() {
        if (mushroomData == null) return; // Ne csináljon semmit, ha nincs gomba adat

        JMenuItem spreadItem = new JMenuItem("Spórázás...");
        spreadItem.addActionListener(e -> {
            if (controller != null) controller.requestSpread(mushroomData);
        });

        JMenuItem growStringItem = new JMenuItem("Fonálnövesztés...");
        growStringItem.addActionListener(e -> {
            if (controller != null) controller.requestGrowString(mushroomData);
        });

        JMenuItem upgradeItem = new JMenuItem("Fejlesztés");
        upgradeItem.addActionListener(e -> {
            if (controller != null) {
                controller.requestUpgrade(mushroomData);
                updateTooltip(); // Frissítjük a tooltipet a fejlesztés után
                repaint();      // Újrarajzolás, ha a fejlesztés vizuális változással jár
            }
        });

        JMenuItem detailsItem = new JMenuItem("Részletek");
        detailsItem.addActionListener(e -> showMushroomDetails());

        list.add(spreadItem);
        list.add(growStringItem);
        list.add(upgradeItem);
        list.addSeparator(); // Elválasztó
        list.add(detailsItem);
    }

    /**
     * Kirajzolja a gombát a pályán (ezt az ősosztály `paintComponent` végzi az ikonnal),
     * és frissíti az esetleges belső állapotokat (pl. tooltip).
     * Mivel a MushroomPanel-en nincs saját rajzolandó komponens (mint az InsectPanel-en az AP label),
     * ennek a metódusnak a fő feladata az állapot szinkronizálása és a tooltip frissítése.
     */
    @Override
    public void draw() {
        // A BasicPanel paintComponent() felelős a vizuális megjelenítésért.
        // Itt frissíthetjük a tooltipet vagy más, nem közvetlenül rajzolt adatot.
        updateTooltip();

        // Ha a gomba adat (pl. szint) változik, ami befolyásolja az ikont vagy a kijelölést,
        // akkor a BasicPanel setIcon() vagy setSelected() metódusait kellene használni,
        // amik automatikusan repaint()-et hívnak.
        // Ha csak a mushroomData belső állapota változott, ami a tooltipet érinti:
        repaint(); // Biztosítja, hogy a tooltip frissítése után, ha szükséges, a panel újrarajzolódjon.
    }

    /**
     * Frissíti a panelen megjelenített gomba adatait.
     * @param mushroom Az új Mushroom objektum.
     */
    public void updateMushroomData(Mushroom mushroom) {
        this.mushroomData = mushroom;
        // Újra kell építeni a kontextus menüt, ha az a gomba adataitól függ
        // (pl. elérhető akciók változnak a gomba típusa/szintje alapján)
        // A jelenlegi implementációban a menüelemek fixek, de az akcióik
        // az új mushroomData-ra fognak vonatkozni.
        // Ha a menü struktúrája változna, akkor:
        // list.removeAll();
        // initializeContextMenu();
        draw(); // Frissíti pl. a tooltipet és újrarajzol
    }

    /**
     * Frissíti a panel tooltipjét a gomba aktuális adataival.
     */
    private void updateTooltip() {
        if (mushroomData != null) {
            setToolTipText(mushroomData.getName());
        } else {
            setToolTipText("Ismeretlen gomba");
        }
    }

    /**
     * Megjeleníti a gomba részleteit egy dialógusablakban.
     */
    private void showMushroomDetails() {
        if (mushroomData != null) {
            JOptionPane.showMessageDialog(this,
                    "Gomba neve: " + mushroomData.getName() + "\n" +
                            "Típus: " + mushroomData.getType() + "\n" +
                            "Szint: " + mushroomData.getLevel(),
                    "Gomba Részletei",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Getter a JPopupMenu-hez
    public JPopupMenu getContextMenu() { // Átneveztem 'list'-ről
        return list;
    }

    // Getter a gomba adataihoz
    public Mushroom getMushroomData() {
        return mushroomData;
    }

    // Egyszerű main metódus a MushroomPanel önálló teszteléséhez
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame("MushroomPanel Teszt");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setLayout(new FlowLayout());

            Controller testController = new Controller();

            // Készítünk egy dummy ikont
            BufferedImage testIcon1 = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = testIcon1.createGraphics();
            g2d.setColor(Color.MAGENTA);
            g2d.fillOval(5, 15, 40, 30); // Laposabb gomba
            g2d.setColor(Color.PINK);
            g2d.fillRect(20, 5, 10, 15); // Szár
            g2d.dispose();

            Mushroom testMushroom1 = new Mushroom("Varázs", 1);
            MushroomPanel panel1 = new MushroomPanel(testMushroom1, testIcon1, testController);
            panel1.setSelected(true); // Teszteljük a kijelölést

            Mushroom testMushroom2 = new Mushroom("Harci", 3);
            // Ikon nélküli panel tesztelése (az ősosztály alapértelmezett rajzát használja)
            MushroomPanel panel2 = new MushroomPanel(testMushroom2, null, testController);


            testFrame.add(panel1);
            testFrame.add(panel2);

            testFrame.pack();
            testFrame.setSize(testFrame.getWidth() + 100, testFrame.getHeight()+50);
            testFrame.setLocationRelativeTo(null);
            testFrame.setVisible(true);
        });
    }
}
