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

    /**
     * Annak kiirasa hogy hanyadik kor van
     */
    protected JLabel roundLaber;

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
        Dimension buttonSize = new Dimension(120, 25);
        //playerIdLabel betu es meret beallitasai
        playerIdLabel = new JLabel("Player: -");
        playerIdLabel.setFont(labelFont);
        playerIdLabel.setPreferredSize(new Dimension(150, 25));
        //playerScoreLabel beallitasai
        playerScoreLabel = new JLabel("Points: -");
        playerScoreLabel.setFont(labelFont);
        //endButton beallitasai
        endButton = new JButton("End Game");
        endButton.setFont(buttonFont);
        endButton.setPreferredSize(buttonSize);
        endButton.setToolTipText("Exit from the game");
        //saveButton beallitasai
        saveButton = new JButton("Save Game");
        saveButton.setFont(buttonFont);
        saveButton.setPreferredSize(buttonSize);
        saveButton.setToolTipText("Save the game");

        roundLaber = new JLabel("Round: -");
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

        add(Box.createHorizontalGlue());
        add(saveButton);
        add(endButton);
        add(roundLaber);
    }

    /**
     * Eseménykezelőket csatol a gombokhoz.
     */
    private void attachActionListeners() {
        /**
         * saveButton-re, ha lenyomodik, akkor elmenteni a majd a map-beli allast
         */
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("StatusPanel: Mentés gomb megnyomva.");
                if (controller != null) {
                    // Egy egyszerűbb mentés, a fájlnevet a Controller generálhatja,
                    // vagy egy fix nevet használunk.
                    // Egy komplexebb megoldás JFileChooser-t használna.
                    String defaultSaveName = "savegame_" + System.currentTimeMillis() + ".sav";
                    controller.save(defaultSaveName);
                }
            }
        });
        /**
         * Az endGame button-re hallgato, ha lenyomodik, akkorvege a jateknak
         */
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

    /***
     * Metódusok a labelek frissítésére
     *
     * @param playerId
     */
    public void updatePlayerId(int playerId) {
        int idNum = -1;
        String itemName = "";
        if (controller.PlayerMushroom != null) {
            idNum = controller.PlayerMushroom.id;
            itemName = " MushroomId: ";
        } else if (controller.PlayerInsect != null) {
            idNum = controller.PlayerInsect.id;
            itemName = " InsectId: ";
        }
        if (idNum != -1) {
            playerIdLabel.setText("Player: " + playerId + itemName + idNum);
        } else {
            playerIdLabel.setText("Player: " + playerId);
        }
    }

    /**
     * Frissiti a kiirt score-t
     * @param score
     */
    public void updateScore(int score) {
        playerScoreLabel.setText("Points: " + score);
    }

    /**
     * A roundLaber szovegenek frissitese
     * @param round
     */
    public void updateRound(int round) {
        roundLaber.setText("Round: " + round);
    }
    //// Getterek a komponensekhez (ha szükséges, pl. teszteléshez)
    public JLabel getPlayerIdLabel() { return playerIdLabel; }
    public JLabel getPlayerScoreLabel() { return playerScoreLabel; }
    public JButton getEndButton() { return endButton; }
    public JButton getSaveButton() { return saveButton; }

    /**
     * Frissiti a kiirt dolgokat
     */
    public void draw() {
        int currentPlayerId = controller.getCurrentPlayerIndex();
        int currentScore = controller.getCurrentPlayerScore(); // vagy más megfelelő getter
        int round = controller.currentTurn;
        //playerIdLabel.setText("Játékos: " + currentPlayerId);
        updatePlayerId(currentPlayerId);
        updateRound(round);
        playerScoreLabel.setText("Points: " + currentScore);
    }
}
