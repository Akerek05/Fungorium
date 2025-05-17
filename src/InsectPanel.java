import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * A pályán lévő rovarok megjelenítésére szolgáló panel.
 * Ősosztálya: BasicPanel.
 */
public class InsectPanel extends BasicPanel {

    protected JPopupMenu list;
    private Insect insectData;

    public InsectPanel(Insect insect, BufferedImage icon) {
        super(icon);
        this.insectData = insect;

        setOpaque(false);
        setLayout(null); // nem kell semmilyen layout, mert kézzel rajzolunk

        list = new JPopupMenu();
        JMenuItem detailsItem = new JMenuItem("Részletek...");
        detailsItem.addActionListener(e -> showInsectDetails());
        list.add(detailsItem);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    list.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        draw();
    }

    public InsectPanel(Insect insect) {
        this(insect, null);
    }

    @Override
    public void draw() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (icon != null) {
            int iconSize = Math.min(getWidth(), getHeight()) / 2; // pl. 40x40, ha panel 80x80
            int x = getWidth() - iconSize - 2; // jobb felső sarok, 2px margó
            int y = 2;
            g.drawImage(icon, x, y, iconSize, iconSize, this);
        }

        if (selected) {
            g.setColor(Color.BLUE);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    private void showInsectDetails() {
        if (insectData != null) {
            JOptionPane.showMessageDialog(this,
                    "Rovar id-je: " + insectData.id + "\n" +
                            "Akciópontok: " + insectData.getActionPoints() + "\n" +
                            "Játékos id-je: " + insectData.playerID,
                    "Rovar Részletei",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public JPopupMenu getContextMenu() {
        return list;
    }

    public Insect getInsectData() {
        return insectData;
    }
}
