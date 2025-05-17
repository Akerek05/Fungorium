import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * A fő játékképernyőt jeleníti meg ez az osztály.
 * Megjeleníti a térképet, a parancs paneleket, státuszbárt.
 * Ősosztálya: JFrame.
 */
public class GameWindow extends JFrame {

    private int currentPlayerId;
    private Controller controller;

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
        setTitle("Game Window");
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
        //FIX JÓ - KINDA
        //gamePanel.setLayout(null); // vagy megfelelő layout, pl. GridLayout, ha rács
        //for (int i = 0; i < controller.map.tektons.size(); i++) {
        //    Tekton tekton = controller.map.tektons.get(i);
        //    TektonPanel tektonPanel = new TektonPanel(tekton, 50); // 2 túl kicsi, próbáld 40-60 közé
        //    tektonPanel.setBounds(i * 60, i * 60, 50, 50);
        //    tektonPanels.add(tektonPanel);
        //    gamePanel.add(tektonPanel); // !!! ez a kulcs
        //}
        //
        //gamePanel.revalidate();
        //gamePanel.repaint();

        //int j = 0;
        //for(Tekton tekton : controller.map.tektons){
        //    TektonPanel newTektonPanel = new TektonPanel(tekton, 50);
        //    newTektonPanel.setBounds(j * 60, j * 60, 50, 50);
        //    tektonPanels.add(newTektonPanel);
        //    gamePanel.add(newTektonPanel);
        //    for (Mushroom mushroom : tekton.arrayOfMushroom) {
        //        try {
        //            BufferedImage image = ImageIO.read(getClass().getResource("/icons/mushroomtrans.png"));
        //            newTektonPanel.addItemPanel(new MushroomPanel(mushroom, image));
        //        } catch (Exception e) {
        //            System.out.println(e.getMessage());
        //        }
//
        //    }
        //    for (Insect insect : tekton.arrayOfInsect) {
        //        try {
        //            BufferedImage image = ImageIO.read(getClass().getResource("/icons/insecttrans.png"));
        //            newTektonPanel.addItemPanel(new InsectPanel(insect, image));
        //        } catch (Exception e) {
        //            System.out.println(e.getMessage());
        //        }
        //    }
        //    for (Spore spore : tekton.arrayOfSpore) {
        //        try {
        //            BufferedImage image = ImageIO.read(getClass().getResource("/icons/sporetrans.png"));
        //            newTektonPanel.addItemPanel(new SporePanel(spore, image));
        //        }
        //        catch (Exception e) {
        //            System.out.println(e.getMessage());
        //        }
        //    }
        //    for (ShroomString string : tekton.arrayOfString) {
        //        try {
        //            BufferedImage image = ImageIO.read(getClass().getResource("/icons/stringtrans.png"));
        //            newTektonPanel.addItemPanel(new ShroomStringPanel(string, image));
        //        } catch (Exception e) {
        //            System.out.println(e.getMessage());
        //
        //        }
        //    }
        //    j++;
        //}

        gamePanel.setLayout(null); // szabad elhelyezés

        int step = 100;             // rácslépés pixelben
        int size = 80;             // panel méret
        int count = controller.map.tektons.size();
        int cols = (int) Math.ceil(Math.sqrt(count)); // rács szélessége
        int rows = (int) Math.ceil((double) count / cols); // szükséges sorok

        for (int i = 0; i < count; i++) {
            Tekton tekton = controller.map.tektons.get(i);
            int x = i % cols;
            int y = i / cols;

            TektonPanel panel = new TektonPanel(tekton, size);
            panel.setLayout(null); // fontos!
            panel.setBounds(x * step, y * step, size, size);
            tektonPanels.add(panel);
            gamePanel.add(panel);

            // Gomba - bal felső
            for (Mushroom mushroom : tekton.arrayOfMushroom) {
                try {
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/mushroomtrans.png"));
                    MushroomPanel mp = new MushroomPanel(mushroom, image);
                    mp.setBounds(4, 4, 30, 30); // bal felső
                    panel.add(mp);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Rovar - jobb felső
            for (Insect insect : tekton.arrayOfInsect) {
                try {
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/insecttrans.png"));
                    InsectPanel ip = new InsectPanel(insect, image);
                    ip.setBounds(size - 34, 4, 30, 30); // jobb felső
                    panel.add(ip);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Spóra - jobb alsó
            for (Spore spore : tekton.arrayOfSpore) {
                try {
                    BufferedImage image = ImageIO.read(getClass().getResource("/icons/sporetrans.png"));
                    SporePanel sp = new SporePanel(spore, image);
                    sp.setBounds(size - 34, size - 34, 30, 30); // jobb alsó
                    panel.add(sp);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }


        gamePanel.revalidate();
        gamePanel.repaint();


        // Alsó command panelek CardLayout-tal
        JPanel commandPanelContainer = new JPanel(new CardLayout());
        commandPanelContainer.add(mushroomCommandPanel, "MUSHROOM");
        commandPanelContainer.add(insectCommandPanel, "INSECT");
        mainPanel.add(commandPanelContainer, BorderLayout.SOUTH);

        this.setContentPane(mainPanel);
        this.setVisible(true);

        //ÚJ
        JComponent stringLayer = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.GREEN);
                g2.setStroke(new BasicStroke(2));

                for (ShroomString ss : controller.map.shroomStrings) {
                    if (!ss.isConnected) continue;

                    Tekton start = ss.startTek;
                    Tekton end = ss.disTek;

                    TektonPanel panel1 = getPanelForTekton(start);
                    TektonPanel panel2 = getPanelForTekton(end);

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
        gamePanel.setComponentZOrder(stringLayer, 0); // Legyen legalul
        //ÚJ

        // Alapértelmezésben mutatjuk a gombapanelt
        showMushroomCMD();
    }

    //új
    private TektonPanel getPanelForTekton(Tekton tekton) {
        for (TektonPanel tp : tektonPanels) {
            if (tp.getTektonData() == tekton)
                return tp;
        }
        return null;
    }
    //új

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
        for(TektonPanel tektonPanel : tektonPanels) {
            tektonPanel.draw();
        }
        statusBarPanel.draw();
        insectCommandPanel.draw();
        mushroomCommandPanel.draw();
        gamePanel.repaint(); // újrarajzolja a vonalakat is

    }
    /**
     * GamePanel kirajzolasa/ frissitese
     */

    public void drawGamePanel() {
        gamePanel.removeAll();
    }
    /**
     * Játék végi állapotot megjelenítő dialógus vagy képernyő kirajzolása.
     * Végrehajtja a grafikus komponensek leállítását.
     */
    public void endGame() {
        //TODO: kiírja a pontokat sorba
        controller.map.endGame();
        goToMenu();
    }

    public void goToMenu() {
        controller.showMenu();
    }

    private void showCard(String name) {
        Container parent = mushroomCommandPanel.getParent();
        if (parent instanceof JPanel) {
            CardLayout layout = (CardLayout) ((JPanel) parent).getLayout();
            layout.show((JPanel) parent, name);
        }
    }

    public void showMushroomCMD() {
        showCard("MUSHROOM");
        reDraw();
    }

    

    public void showInsectCMD() {
        showCard("INSECT");
        reDraw();
    }

    public ArrayList<TektonPanel> getTektonPanels(){
        return tektonPanels;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
        this.statusBarPanel.draw();
    }
    public void selectTektonPanel(TektonPanel tektonPanel) {}
}
