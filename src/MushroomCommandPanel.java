import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MushroomCommandPanel extends BasicCommandPanel {

    /**
     * Gomb a gombaspórák terjesztésére.
     */
    protected JButton spreadButton;

    /**
     * Gomb a fonál növesztésére két Tekton között.
     */
    protected JButton growStringButton;

    /**
     * Gomb a gomba fejlesztésére.
     */
    protected JButton upgradeButton;

    /**
     * Gomb a rovar megevéséhez (shroomstringgel).
     */
    protected JButton eatButton; // eatInsectButton lenne talán beszédesebb

    /**
     * Gomb a gombatest növesztésére.
     */
    protected JButton growBodyButton;

    // A Controller referenciát az ősosztály (BasicCommandPanel) tárolja.

    /**
     * Konstruktor a MushroomCommandPanel számára.
     * @param controller A játék vezérlője, amit az ősosztálynak adunk tovább.
     */
    public MushroomCommandPanel(Controller controller) {
        super(controller); // Az ősosztály konstruktorának hívása a Controllerrel

        // Cím a panelnek
        JLabel titleLabel = new JLabel("Gomba Parancsok");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Térköz a cím alatt

        draw(); // Meghívjuk a mi felüldefiniált draw metódusunkat
    }

    /**
     * Kirajzolja (létrehozza és hozzáadja a panelhez) a gombaspecifikus gombokat,
     * valamint az ősosztály által biztosított gombokat (pl. "Kör vége").
     */
    @Override
    public void draw() {
        this.removeAll();
        // Először az ősosztály gombjait rajzoljuk ki (pl. endTurnButton)
        super.draw();

        // Gomba specifikus gombok létrehozása
        spreadButton = new JButton("Spórázás");
        growStringButton = new JButton("Fonálnövesztés");
        upgradeButton = new JButton("Fejlesztés");
        eatButton = new JButton("Rovar Elkapása"); // Nevét pontosítottam
        growBodyButton = new JButton("Testnövesztés");

        // Gombok stílusának egységesítése (opcionális)
        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        Dimension buttonMaxSize = new Dimension(Integer.MAX_VALUE, new JButton("Minta").getPreferredSize().height + 5);

        JButton[] mushroomButtons = {spreadButton, growStringButton, upgradeButton, eatButton, growBodyButton};
        for (JButton btn : mushroomButtons) {
            btn.setFont(buttonFont);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(buttonMaxSize);
        }

        // Eseménykezelők hozzáadása a gombaspecifikus gombokhoz
        spreadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MushroomCommandPanel: Spórázás gomb megnyomva.");
                // A cél Tekton-t a GUI-n kellene kiválasztani.
                controller.checkSelected();
                if (controller.selectedTektons.size() == 1) {
                    Tekton target = controller.selectedTektons.get(0); // Placeholder
                    int amount = -1; // Placeholder spóra mennyiség
                    controller.spread(controller.PlayerMushroom, target, amount);
                }
                controller.resetSelectedTektons();
            }
        });

        growStringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MushroomCommandPanel: Fonálnövesztés gomb megnyomva.");
                // Két cél Tekton-t kellene a GUI-n kiválasztani.
                controller.checkSelected();
                if(controller.selectedTektons.size() == 2)
                {
                    Tekton target1 = controller.selectedTektons.get(0); // Placeholder
                    Tekton target2 = controller.selectedTektons.get(1); // Placeholder
                    controller.growString(controller.PlayerMushroom,target1, target2);

                }
                controller.resetSelectedTektons();
            }
        });

        upgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MushroomCommandPanel: Fejlesztés gomb megnyomva.");
                controller.upgradeMushroom(controller.PlayerMushroom);
            }
        });

        eatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MushroomCommandPanel: Rovar Elkapása gomb megnyomva.");
                // A cél Rovar-t és a kapcsolódó Tekton(oka)t a GUI-n kellene kiválasztani.
                controller.checkSelected();
                if(controller.selectedTektons.size() == 2) {
                    Tekton source = controller.selectedTektons.get(0); // Placeholder: fonál forrása
                    Tekton insectLocation = controller.selectedTektons.get(1); // Placeholder: rovar helye
                    Insect targetInsect = insectLocation.arrayOfInsect.get(0); // Placeholder
                    controller.eatInsect(source, insectLocation, targetInsect);
                }
                controller.resetSelectedTektons();
            }
        });

        growBodyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MushroomCommandPanel: Testnövesztés gomb megnyomva.");
                //controller.checkSelected();
                if (controller.selectedTektons.size() == 1) {
                    Tekton target = controller.selectedTektons.get(0); // Placeholder
                    controller.growBody(controller.PlayerMushroom, target);
                }
                controller.resetSelectedTektons();
            }
        });

        // Gomba specifikus gombok hozzáadása a panelhez
        // A sorrend a specifikáció szerinti attribútum sorrend alapján
        add(spreadButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(growStringButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(upgradeButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(eatButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(growBodyButton);

        this.revalidate();
        this.repaint();
    }

    // Getterek a gombokhoz (ha szükséges)
    public JButton getSpreadButton() { return spreadButton; }
    public JButton getGrowStringButton() { return growStringButton; }
    public JButton getUpgradeButton() { return upgradeButton; }
    public JButton getEatButton() { return eatButton; }
    public JButton getGrowBodyButton() { return growBodyButton; }

}
