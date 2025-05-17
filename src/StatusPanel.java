import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A játék állapotának kijelzésére szolgáló panel,
 * ami információkat jelenít meg a játékos számára.
 */
public class StatusPanel extends JPanel {

    /**
     * Label, ami kiírja a játékos ID-jét.
     * Átneveztem PlayerIdLabel-re a jobb egyértelműségért.
     */
    protected JLabel playerIdLabel; // Eredeti specifikáció: PlayerLabel

    /**
     * Label, ami kiírja a játékos pontszámát.
     * Átneveztem playerScoreLabel-re a jobb egyértelműségért.
     */
    protected JLabel playerScoreLabel; // Eredeti specifikáció: Score

    /**
     * Gomb, amivel ki lehet lépni a játékból.
     */
    protected JButton endButton;

    /**
     * Gomb, amivel menteni lehet a játék állását.
     */
    protected JButton saveButton;

    private Controller controller; // Referencia a játék vezérlőjére

    /**
     * StatusPanel konstruktor.
     * @param controller A játék vezérlő objektuma.
     */
    public StatusPanel(Controller controller) {
        this.controller = controller;

        // Panel alapbeállításai
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5)); // Balra igazított elemek, térközzel
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(new Color(224, 224, 224)); // Világosszürke háttér

        initComponents();
        addComponentsToPanel();
        attachActionListeners();

        // Kezdeti értékek beállítása (opcionális, a controllerből is jöhet)
        updatePlayerId(controller != null ? controller.getCurrentPlayerIndex() : 0);
        updateScore(controller != null ? controller.getCurrentPlayerScore() : 0);
    }

    /**
     * Inicializálja a GUI komponenseket.
     */
    private void initComponents() {
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        Font buttonFont = new Font("Arial", Font.BOLD, 11);
        Dimension buttonSize = new Dimension(90, 25);

        playerIdLabel = new JLabel("Játékos: -");
        playerIdLabel.setFont(labelFont);

        playerScoreLabel = new JLabel("Pontszám: -");
        playerScoreLabel.setFont(labelFont);

        endButton = new JButton("Kilépés");
        endButton.setFont(buttonFont);
        endButton.setPreferredSize(buttonSize);
        endButton.setToolTipText("Kilépés a játékból.");

        saveButton = new JButton("Mentés");
        saveButton.setFont(buttonFont);
        saveButton.setPreferredSize(buttonSize);
        saveButton.setToolTipText("Játékállás mentése.");
    }

    /**
     * Hozzáadja a komponenseket a panelhez.
     */
    private void addComponentsToPanel() {
        add(playerIdLabel);
        add(Box.createHorizontalStrut(20)); // Térköz
        add(playerScoreLabel);

        // Gombok jobb oldalra igazítása egy külön panellel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false); // Átlátszó, hogy a StatusPanel háttere látszódjon
        buttonPanel.add(saveButton);
        buttonPanel.add(endButton);

        // Hozzáadunk egy "rugót", hogy a gombok a jobb szélre kerüljenek
        // Ehhez a fő panel elrendezését módosítani kellene BoxLayout-ra,
        // vagy a buttonPanel-t a BorderLayout.EAST-be tenni, ha a StatusPanel BorderLayout-ot használna.
        // FlowLayout esetén a gombok egyszerűen a többi elem után következnek.
        // Ha a gombokat mindenképp jobbra akarjuk, a StatusPanel elrendezését kell átgondolni.
        // Most maradjunk a FlowLayout-nál, a gombok a labelek után jönnek.

        // Alternatíva: BorderLayout a StatusPanelnek
        //setLayout(new BorderLayout());
        //JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        //infoPanel.setOpaque(false);
        //infoPanel.add(playerIdLabel);
        //infoPanel.add(Box.createHorizontalStrut(20));
        //infoPanel.add(playerScoreLabel);
        //add(infoPanel, BorderLayout.WEST);
        //add(buttonPanel, BorderLayout.EAST);

        // Maradva az eredeti FlowLayout-nál, a gombokat is hozzáadjuk
        add(Box.createHorizontalGlue()); // Ez megpróbálja kitolni a következő elemeket, de FlowLayout-ban nem így működik
        // Inkább csak egyszerűen hozzáadjuk őket.
        add(saveButton);
        add(endButton);
    }


    /**
     * Eseménykezelőket csatol a gombokhoz.
     */
    private void attachActionListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("StatusPanel: Mentés gomb megnyomva.");
                if (controller != null) {
                    // Egy egyszerűbb mentés, a fájlnevet a Controller generálhatja,
                    // vagy egy fix nevet használunk.
                    // Egy komplexebb megoldás JFileChooser-t használna.
                    String defaultSaveName = "jatekmentes_" + System.currentTimeMillis() + ".sav";
                    controller.save(defaultSaveName);
                }
            }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("StatusPanel: Kilépés gomb megnyomva.");
                if (controller != null) {
                    controller.endGame();
                } else {
                    // Ha nincs controller, alapértelmezett kilépés
                    System.exit(0);
                }
            }
        });
    }

    // Metódusok a labelek frissítésére
    public void updatePlayerId(int playerId) {
        playerIdLabel.setText("Játékos: " + playerId);
    }

    public void updateScore(int score) {
        playerScoreLabel.setText("Pontszám: " + score);
    }

    // Getterek a komponensekhez (ha szükséges, pl. teszteléshez)
    public JLabel getPlayerIdLabel() { return playerIdLabel; }
    public JLabel getPlayerScoreLabel() { return playerScoreLabel; }
    public JButton getEndButton() { return endButton; }
    public JButton getSaveButton() { return saveButton; }


   //public void draw() {
   //    updatePlayerId(controller.getCurrentPlayerIndex());
   //    updateScore(controller.getCurrentPlayerScore());

   //    repaint();
   //}

    public void draw() {
        int currentPlayerId = controller.getCurrentPlayerIndex();
        int currentScore = controller.getCurrentPlayerScore(); // vagy más megfelelő getter
        playerIdLabel.setText("Játékos: " + currentPlayerId);
        playerScoreLabel.setText("Pontszám: " + currentScore);
    }

}
