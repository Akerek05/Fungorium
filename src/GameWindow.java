import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * A fő játékképernyőt jeleníti meg ez az osztály.
 * Megjeleníti a térképet, a parancs paneleket, státuszbárt.
 * Ősosztálya: JFrame.
 */
public class GameWindow extends JFrame {
    /// Jelenlegi jatekos id-ja
    private int currentPlayerId;
    /// A controller
    private Controller controller;

    /// itt van a tektonpanelek es basicpanelek megjelenitese
    private JPanel gamePanel;
    private StatusPanel statusBarPanel;
    private MushroomCommandPanel mushroomCommandPanel;
    private InsectCommandPanel insectCommandPanel;
    protected ArrayList<TektonPanel> tektonPanels;

    /**
     * GameWindow konstruktor.
     * @param controller A játék vezérlő objektuma.
     */
    public GameWindow(Controller controller) {
        this.controller = controller;
        setTitle("Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Képernyő közepére

        // Komponensek inicializálása
        statusBarPanel = new StatusPanel(controller);
        mushroomCommandPanel = new MushroomCommandPanel(controller);
        insectCommandPanel = new InsectCommandPanel(controller);
        tektonPanels = new ArrayList<>();

        // Fő panel BorderLayout-tal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Felső státuszbár
        mainPanel.add(statusBarPanel, BorderLayout.NORTH);

        // Játékterület (pl. térkép vagy game logic)
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.LIGHT_GRAY); // teszteléshez
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        gamePanel.setLayout(null); // szabad elhelyezés

        drawGamePanel();


        // Alsó command panelek CardLayout-tal
        JPanel commandPanelContainer = new JPanel(new CardLayout());
        commandPanelContainer.add(mushroomCommandPanel, "MUSHROOM");
        commandPanelContainer.add(insectCommandPanel, "INSECT");
        mainPanel.add(commandPanelContainer, BorderLayout.SOUTH);

        this.setContentPane(mainPanel);
        this.setVisible(true);

        // Alapértelmezésben mutatjuk a gombapanelt
        showMushroomCMD();
    }

    /**
     * a tektonhoz visszaadja a hozza tartozo TektonPanel-t.
     * @param tekton
     * @return
     */
    private TektonPanel getPanelForTekton(Tekton tekton) {
        for (TektonPanel tp : tektonPanels) {
            if (tp.getTektonData() == tekton)
                return tp;
        }
        return null;
    }

    /**
     * A kep atmeretezesere
     *
     * @param originalImage
     * @param scale
     * @return
     */
    public static BufferedImage resizeTo(BufferedImage originalImage, int scale) {
        int newWidth = originalImage.getWidth() / scale;
        int newHeight = originalImage.getHeight() / scale;

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose(); // Clean up resources

        return resizedImage;
    }
    /**
     * Újrarajzolja a térképet és a játékelemeket.
     * Akkor hívódik, ha változás történt az állapotban.
     */
    public void reDraw() {
        drawGamePanel();
        statusBarPanel.draw();
        insectCommandPanel.draw();
        mushroomCommandPanel.draw();
    }
    /**
     * GamePanel kirajzolasa/ frissitese
     */

    public void drawGamePanel() {
        /// gamePanel uritese
        gamePanel.removeAll();
        gamePanel.setLayout(null); // szabad elhelyezés

        int step = 100;             // rácslépés pixelben
        int size = 80;             // panel méret
        int count = controller.map.tektons.size();
        int cols = (int) Math.ceil(Math.sqrt(count)); // rács szélessége
        int rows = (int) Math.ceil((double) count / cols); // szükséges sorok
        /// Atfut az osszes tektonon, ami a map-ban van
        for (int i = 0; i < count; i++) {
            Tekton tekton = controller.map.tektons.get(i);
            int x = i % cols;
            int y = i / cols;
            /// Csinal minden tektonhoz egy panelt es hozzaadja a gamepanelhez
            TektonPanel panel = new TektonPanel(tekton, size);
            panel.setLayout(null); // fontos!
            panel.setBounds(x * step, y * step, size, size);
            tektonPanels.add(panel);
            gamePanel.add(panel);

            // Gomba - bal felső
            // az osszes gomban a tektonon
            for (Mushroom mushroom : tekton.arrayOfMushroom) {
                try {
                    // kep betoltese es MushroomPanel letrehozasa a gobabol
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/mushroomtrans.png"));
                    MushroomPanel mp = new MushroomPanel(mushroom, image);
                    mp.setBounds(4, 4, 30, 30); // bal felső
                    panel.add(mp);
                    // Ha a PlayerMushroom (a jatekos gombaja amivel most tud muveleteket vegezni), akkor bealtja, hogy kivan valasztva
                    if (mushroom.equals(controller.PlayerMushroom)){
                        mp.selected = true;
                    } else {
                        mp.selected = false;
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Rovar - jobb felső
            // az osszes rovaron a tektonon
            for (Insect insect : tekton.arrayOfInsect) {
                try {
                    // a kepe betoltese es InsectPanel letrehozasa a rovarbol
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/insecttrans.png"));
                    InsectPanel ip = new InsectPanel(insect, image);
                    ip.setBounds(size - 34, 4, 30, 30); // jobb felső
                    panel.add(ip);
                    // Ha a PlayerInsect (a jatekos rovarja amivel most tud muveleteket vegezni), akkor bealtja, hogy kivan valasztva
                    if (insect.equals(controller.PlayerInsect)){
                        ip.selected = true;
                    } else {
                        ip.selected = false;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Spóra - jobb alsó
            // az osszes sporaron a tektonon
            for (Spore spore : tekton.arrayOfSpore) {
                try {
                    // A spora kepenek betoltese es SporePanel letrehozasa a sporabol
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/sporetrans.png"));
                    SporePanel sp = new SporePanel(spore, image);
                    sp.setBounds(size - 34, size - 34, 30, 30); // jobb alsó
                    panel.add(sp);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        //A fonalak kirajzolasa
            JComponent stringLayer = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.GREEN);
                g2.setStroke(new BasicStroke(2));
                // a map osszes fonalara lefut
                for (ShroomString ss : controller.map.shroomStrings) {
                    // Ha elvan vagva, akkor piros, ha nem akkor zold
                    if (ss.isCut) {
                        g2.setColor(Color.RED);
                    } else {
                        g2.setColor(Color.GREEN);
                    }
                    // A fonal kezdo es veg tektonja
                    Tekton start = ss.startTek;
                    Tekton end = ss.disTek;
                    // TektonPanel kereses a tektonok alapjan
                    TektonPanel panel1 = getPanelForTekton(start);
                    TektonPanel panel2 = getPanelForTekton(end);
                    // A vonal grafikai kirajzolasa a ket tektonPanel kozott
                    if (panel1 != null && panel2 != null) {
                        int x1 = panel1.getX() + panel1.getWidth() / 2;
                        int y1 = panel1.getY() + panel1.getHeight() / 2;
                        int x2 = panel2.getX() + panel2.getWidth() / 2;
                        int y2 = panel2.getY() + panel2.getHeight() / 2;

                        g2.drawLine(x1, y1, x2, y2);
                    }
                }
            }
        };
        stringLayer.setBounds(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
        stringLayer.setOpaque(false);
        gamePanel.add(stringLayer);

        gamePanel.revalidate();
        gamePanel.repaint();
    }
    /**
     * Játék végi állapotot megjelenítő dialógus vagy képernyő kirajzolása.
     * Végrehajtja a grafikus komponensek leállítását.
     */
    public void endGame() {
        // Torol mindent a Gamewindowbol
        this.getContentPane().removeAll();
        this.getContentPane().revalidate();
        this.getContentPane().repaint();

        /// titlelabel a kiiras cimenek
        JLabel titleLabel = new JLabel("Game Over! Results:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Lista panel (eredmények)
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        // Játékosok lekérése és rendezése pontszám szerint
        List<Integer> player_ids = controller.player_ids; // Igazítsd a tényleges getterhez
        List<Integer> scores = new ArrayList<>();
        scores.addAll(controller.map.scores);
        scores.sort(Collections.reverseOrder());
        Integer player_id = -1;
        int rank = 1;
        /// score-k kiiratasa
        for (Integer score : scores) {
            for (Integer player : player_ids) {
                if (controller.map.scores.get(player).equals(score) && player_id == -1) {
                    // Egy sor a resultPanelhez amiben egy helyezett jatekos es a pontja van
                    JLabel label = new JLabel(rank + ". place: Player" + player + ", Points: " + score);
                    label.setFont(new Font("Arial", Font.PLAIN, 20));
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    resultPanel.add(label);
                    rank++;
                    player_id = player;
                }
            }
            player_ids.remove(player_id);
            player_id = -1;
        }
        getContentPane().add(resultPanel, BorderLayout.CENTER);
        goToMenu();
    }

    /**
     * A menu megnyitasa
     */
    public void goToMenu() {
        controller.showMenu();
    }

    /**
     * A mushroomCommandPanel es InsectCommandPanel cserejere
     * @param name
     */
    private void showCard(String name) {
        Container parent = mushroomCommandPanel.getParent();
        if (parent instanceof JPanel) {
            CardLayout layout = (CardLayout) ((JPanel) parent).getLayout();
            layout.show((JPanel) parent, name);
        }
    }

    /**
     * A mushroomCommandPanel elorehozatala
     */
    public void showMushroomCMD() {
        showCard("MUSHROOM");
        reDraw();
    }


    /**
     * A insectCommandPanel elorehozatala
     */
    public void showInsectCMD() {
        showCard("INSECT");
        reDraw();
    }

    /**
     * tektonPanelek visszaadasa
     * @return
     */
    public ArrayList<TektonPanel> getTektonPanels(){
        return tektonPanels;
    }

    /**
     * A currentPlayerId visszaadasa
     * @return
     */
    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    /**
     * A currentPlayerId beallitasara
     * @param currentPlayerId
     */
    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
        this.statusBarPanel.draw();
    }
}


