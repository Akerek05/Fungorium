import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsectCommandPanel extends BasicCommandPanel {

    /**
     * Gomb a rovar mozgatására.
     */
    protected JButton moveButton;

    /**
     * Gomb a spóra elfogyasztására.
     */
    protected JButton eatButton;

    /**
     * Gomb a fonal elvágására.
     */
    protected JButton cutButton;

    // Nincs szükség külön Controller referenciára, ha az ősosztály (BasicCommandPanel) már tárolja
    // és protected láthatóságú, vagy van gettere. Jelenleg az ős tárolja.

    /**
     * Konstruktor az InsectCommandPanel számára.
     * @param controller A játék vezérlője, amit az ősosztálynak adunk tovább.
     */
    public InsectCommandPanel(Controller controller) {
        super(controller); // Az ősosztály konstruktorának hívása a Controllerrel

        // Cím a panelnek
        JLabel titleLabel = new JLabel("Rovar Parancsok");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Térköz a cím alatt

        draw(); // Meghívjuk a mi felüldefiniált draw metódusunkat
        // (ami az ősét is hívja, ha úgy van implementálva)
    }

    /**
     * Kirajzolja (létrehozza és hozzáadja a panelhez) a rovarspecifikus gombokat,
     * valamint az ősosztály által biztosított gombokat (pl. "Kör vége").
     */
    @Override
    public void draw() {
        this.removeAll();
        // Először az ősosztály gombjait rajzoljuk ki (pl. endTurnButton)
        super.draw();

        // Rovar specifikus gombok létrehozása
        moveButton = new JButton("Mozgás");
        eatButton = new JButton("Spóraevés");
        cutButton = new JButton("Fonálvágás");

        // Gombok stílusának egységesítése (opcionális)
        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        Dimension buttonMaxSize = new Dimension(Integer.MAX_VALUE, new JButton("Minta").getPreferredSize().height + 5);

        moveButton.setFont(buttonFont);
        moveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        moveButton.setMaximumSize(buttonMaxSize);

        eatButton.setFont(buttonFont);
        eatButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        eatButton.setMaximumSize(buttonMaxSize);

        cutButton.setFont(buttonFont);
        cutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cutButton.setMaximumSize(buttonMaxSize);


        // Eseménykezelők hozzáadása a rovarspecifikus gombokhoz
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("InsectCommandPanel: Mozgás gomb megnyomva.");
                controller.checkSelected();
                if (controller.selectedTektons.size() == 1) {
                    Tekton target = controller.selectedTektons.get(0);
                    controller.move(controller.PlayerInsect, target);
                } else {
                    System.out.println("Hibás mezőválasztás! Válassz ki pontosan egy célmezőt.");
                }
                controller.resetSelectedTektons(); // FONTOS: kijelölések törlése
            }
        });


        eatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("InsectCommandPanel: Spóraevés gomb megnyomva.");
                controller.eatSpore(controller.PlayerInsect);
            }
        });

        cutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("InsectCommandPanel: Fonálvágás gomb megnyomva.");
                controller.checkSelected();
                if (controller.selectedTektons.size() == 1) {
                    Tekton target = controller.selectedTektons.get(0); // Placeholder
                    controller.cut(controller.PlayerInsect, target);

                }
                controller.checkSelected();
            }
        });

        // Rovar specifikus gombok hozzáadása a panelhez
        add(moveButton);
        add(Box.createRigidArea(new Dimension(0, 5))); // Térköz
        add(eatButton);
        add(Box.createRigidArea(new Dimension(0, 5))); // Térköz
        add(cutButton);

        // A panel frissítése, hogy az új komponensek megjelenjenek (általában nem szükséges,
        // ha a konstruktor végén adjuk hozzá, de nem árt).
        this.revalidate();
        this.repaint();
    }

    // Getterek a gombokhoz, ha külső osztályoknak (pl. teszteknek vagy a GameWindow-nak
    // speciális célból) el kellene érniük őket. A protected láthatóság miatt
    // a leszármazottak egyébként is elérik.
    public JButton getMoveButton() { return moveButton; }
    public JButton getEatButton() { return eatButton; }
    public JButton getCutButton() { return cutButton; }


}
