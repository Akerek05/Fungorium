import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class BasicCommandPanel extends JPanel {

    protected JButton endTurnButton;
    protected Controller controller;

    public BasicCommandPanel() {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        draw();
    }

    public BasicCommandPanel(Controller controller) {

        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        draw();
    }

    public void draw() {
        endTurnButton = new JButton("Kör vége");
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Kör vége gomb megnyomva.");
                controller.setTurnEnded(true); // Flag beállítása

            }
        });

        this.add(endTurnButton);
    }

    public JButton getEndTurnButton() {
        return endTurnButton;
    }
}