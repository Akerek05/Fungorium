import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * A menüt megjelenítő osztály. Innen tud a játékos játékot indítani,
 * mentéseket betölteni, illetve megadni, hogy hány játékos fogja játszani a játékot.
 * Ősosztálya: JFrame.
 */
public class MenuWindow extends JFrame {

    /**
     * Label, ami jelzi, hogy hány játékos van a játékban.
     * A specifikációban 'playerCount' néven szerepel a JLabel, de ez ütközik
     * az int típusú 'playerCount' változóval. Átnevezem 'playerCountLabel'-re.
     */
    protected JLabel playerCountLabel;

    /**
     * A játékosok száma a játékban.
     */
    protected int playerCount; // Alapértelmezett érték

    /**
     * Egy karaktersorozat, ami a betöltendő fájlnak a neve (vagy teljes elérési útja).
     */
    protected String loadName;

    /**
     * Gomb, ami a játék indítására szolgál.
     */
    protected JButton startButton;

    /**
     * Gomb, ami egy mentés betöltésére szolgál.
     */
    protected JButton loadButton;

    private Controller controller; // Referencia a játék vezérlőjére
    private JSpinner playerCountSpinner; // Játékosok számának beállítására

    /**
     * MenuWindow konstruktor.
     * @param controller A játék vezérlő objektuma.
     */
    public MenuWindow(Controller controller) {
        this.controller = controller;
        this.playerCount = 2; // Alapértelmezett játékosszám

        setTitle("Játék Főmenü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // A főmenü bezárása bezárja az alkalmazást
        setResizable(false); // Nem átméretezhető menüablak

        initComponents();
        layoutComponents();
        attachActionListeners();

        pack(); // Méretezi az ablakot a tartalomhoz
        setLocationRelativeTo(null); // Ablak középre igazítása
    }

    /**
     * Inicializálja a GUI komponenseket.
     */
    private void initComponents() {
        playerCountLabel = new JLabel("Játékosok száma: " + playerCount);
        playerCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // JSpinner a játékosok számának beállításához (pl. 1-től 4-ig)
        SpinnerModel spinnerModel = new SpinnerNumberModel(
                this.playerCount, // Kezdeti érték
                1,  // Minimum érték
                4,  // Maximum érték
                1   // Lépésköz
        );
        playerCountSpinner = new JSpinner(spinnerModel);
        playerCountSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        // Listener a spinner értékének változására
        playerCountSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                MenuWindow.this.playerCount = (Integer) playerCountSpinner.getValue();
                playerCountLabel.setText("Játékosok száma: " + MenuWindow.this.playerCount);
            }
        });


        startButton = new JButton("Új Játék Indítása");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setToolTipText("Új játék kezdése a kiválasztott játékosszámmal.");

        loadButton = new JButton("Játék Betöltése");
        loadButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadButton.setToolTipText("Korábban mentett játékállás betöltése.");
    }

    /**
     * Elrendezi a főbb GUI komponenseket az ablakban.
     */
    private void layoutComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margó
        mainPanel.setBackground(new Color(240, 240, 240)); // Világos háttér

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Az elem kitölti a sort
        gbc.fill = GridBagConstraints.HORIZONTAL; // Horizontálisan kitölt
        gbc.insets = new Insets(5, 5, 10, 5); // Térköz az elemek között

        // Cím
        JLabel titleLabel = new JLabel("Gomba & Rovar Hadviselés");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(5, 5, 20, 5); // Nagyobb térköz a cím alatt
        mainPanel.add(titleLabel, gbc);

        // Játékosok száma beállító
        JPanel playerCountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        playerCountPanel.setOpaque(false); // Átlátszó háttér, hogy a mainPanel háttere látszódjon
        playerCountPanel.add(new JLabel("Válassz játékosszámot:"));
        playerCountPanel.add(playerCountSpinner);
        gbc.insets = new Insets(5, 5, 10, 5);
        mainPanel.add(playerCountPanel, gbc);

        // Játékosok számát jelző label (frissül a spinnerrel)
        // Ezt a labelt most nem muszáj külön megjeleníteni, ha a spinner elég informatív.
        // Ha mégis kell:
        // playerCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // mainPanel.add(playerCountLabel, gbc);

        // Gombok
        gbc.fill = GridBagConstraints.NONE; // Gombok ne nyúljanak szét
        gbc.anchor = GridBagConstraints.CENTER;
        startButton.setPreferredSize(new Dimension(200, 40));
        mainPanel.add(startButton, gbc);

        loadButton.setPreferredSize(new Dimension(200, 40));
        mainPanel.add(loadButton, gbc);

        // Kilépés gomb (opcionális, de hasznos)
        JButton exitButton = new JButton("Kilépés");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setPreferredSize(new Dimension(200, 40));
        exitButton.addActionListener(e -> System.exit(0));
        gbc.insets = new Insets(20, 5, 5, 5); // Nagyobb térköz a kilépés gomb előtt
        mainPanel.add(exitButton, gbc);


        add(mainPanel);
    }

    /**
     * Eseménykezelőket csatol a gombokhoz.
     */
    private void attachActionListeners() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MenuWindow: Start gomb megnyomva. Játékosok: " + playerCount);
                if (controller != null) {
                    controller.startGame(playerCount);
                    // A játék indítása után a menüablak elrejtése/bezárása
                    // és a GameWindow megjelenítése (ezt a Controller intézheti)
                    MenuWindow.this.setVisible(false);
                    MenuWindow.this.dispose(); // Vagy dispose(), ha már nem kell

                    // Itt kellene a GameWindow-t megjeleníteni
                    // Ezt ideális esetben a Controller teszi meg a startGame belsejében
                    // Pl. new GameWindow(controller).setVisible(true);
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MenuWindow: Load gomb megnyomva.");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Mentett játék betöltése");
                // Opcionálisan beállíthatunk egy alapértelmezett könyvtárat és fájlszűrőt
                // fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/MyGameSaves"));
                // fileChooser.setFileFilter(new FileNameExtensionFilter("Játékmentések (*.sav)", "sav"));

                int userSelection = fileChooser.showOpenDialog(MenuWindow.this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToLoad = fileChooser.getSelectedFile();
                    loadName = fileToLoad.getAbsolutePath(); // Teljes elérési út
                    System.out.println("  Kiválasztott fájl a betöltéshez: " + loadName);
                    if (controller != null) {
                        controller.load(loadName);
                        // Betöltés után a menüablak elrejtése/bezárása
                        // és a GameWindow megjelenítése (ezt a Controller intézheti)
                        MenuWindow.this.setVisible(false);
                        MenuWindow.this.dispose();

                        // Itt kellene a GameWindow-t megjeleníteni a betöltött állapottal
                        // Pl. new GameWindow(controller).setVisible(true); // A controller már betöltötte az állapotot
                    }
                } else {
                    System.out.println("  Betöltés megszakítva a felhasználó által.");
                }
            }
        });
    }

    // Getterek (ha szükségesek, pl. teszteléshez)
    public JLabel getPlayerCountLabel() { return playerCountLabel; }
    public int getPlayerCount() { return playerCount; }
    public String getLoadName() { return loadName; }
    public JButton getStartButton() { return startButton; }
    public JButton getLoadButton() { return loadButton; }

}
