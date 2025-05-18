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

    /**
     * A context menu associated with the panel for displaying insects on the field.
     * This menu is displayed on a right-click and provides options such as viewing insect details.
     */
    protected JPopupMenu list;
    /**
     * Represents the insect data associated with the current instance of the panel.
     * This variable holds the reference to an {@code Insect} object, which includes
     * information such as the insect's ID, player ownership, action points, effect type,
     * and other related properties relevant to the game logic. The data is used for
     * rendering the insect's details on the panel and for managing gameplay functionality.
     */
    private Insect insectData;
    /**
     * Indicates whether the insect is selected or not.
     * This variable is used to manage the selection state of the insect within the panel.
     */
    protected boolean selected = false;

    /**
     * Constructs an {@code InsectPanel} with the specified {@code Insect} object and icon.
     * The panel provides a graphical representation of an insect along with a context menu
     * for additional actions such as viewing the insect's details.
     *
     * @param insect The {@code Insect} object whose data is represented by this panel.
     * @param icon The {@code BufferedImage} icon to be displayed on the panel.
     */
    public InsectPanel(Insect insect, BufferedImage icon) {
        super(icon);
        this.insectData = insect;

        setOpaque(false);
        setLayout(null); // nem kell semmilyen layout, mert kézzel rajzolunk

        list = new JPopupMenu();
        JMenuItem detailsItem = new JMenuItem("Details...");
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

    /**
     * Constructs an {@code InsectPanel} with the specified {@code Insect} object.
     * The panel provides a graphical representation of an insect and initializes
     * with a default configuration.
     *
     **/
    public InsectPanel(Insect insect) {
        this(insect, null);
    }

    /**
     * Redraws the panel to reflect any changes in the visual representation.
     *
     * This method invokes the {@code repaint} method, triggering the panel to
     * refresh its appearance. It is typically called to ensure the panel updates
     * after modifications to its state or when external events necessitate a
     * refresh of the display.
     */
    @Override
    public void draw() {
        repaint();
    }

    /**
     * Paints the component by drawing its state, including the icon and selected border if applicable.
     * This method overrides the paintComponent method of the parent class to provide custom drawing logic.
     *
     * The method first invokes the superclass implementation to ensure the component is properly cleared and
     * other default*/
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

    /**
     * Displays a dialog showing the details of the insect associated with this panel.
     *
     * This method presents a message dialog containing information about the insect,
     * such as the player ID, insect ID, action points, and effect type. The information
     * is retrieved from the {@code insectData} field. The dialog is only shown if
     * {@code insectData} is not null.
     *
     * Message Details:
     * - Player ID: Identifier of the player owning the insect.
     * - Insect ID: Unique identifier for the insect.
     * - Action Points: Number of action points available for the insect.
     * - Effect: The specific effect type associated with the insect.
     *
     * The dialog is presented as an information message dialog with a title "Insect Details."
     *
     * Preconditions:
     * - {@code insectData} should not be null to display the details.
     *
     * Postconditions:
     * - A dialog is shown to the user, or nothing happens if {@code insectData} is null.
     */
    private void showInsectDetails() {
        if (insectData != null) {
            JOptionPane.showMessageDialog(this,
                            "Player id: " + insectData.playerID + "\n" +
                                    "Insect id: " + insectData.id + "\n" +
                            "Action Points: " + insectData.getActionPoints() + "\n" +
                            "Effect: " + insectData.effectType,
                    "Insect Details",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Retrieves the context menu associated with the insect panel.
     *
     * @return The {@code JPopupMenu} instance representing the context menu for this panel.
     */
    public JPopupMenu getContextMenu() {
        return list;
    }

    /**
     * Retrieves the data of the insect associated with this panel.
     *
     * @return The {@code Insect} instance representing the insect's data
     *         used in this panel. Returns the {@code insectData} field.
     */
    public Insect getInsectData() {
        return insectData;
    }
}
