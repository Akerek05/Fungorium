import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Absztrakt osztály, ami a többi parancs panelnek az alapját képezi.
 * Tartalmaz egy gombot a kör befejezéséhez és kezeli az ahhoz tartozó eseményeket.
 */
public abstract class BasicCommandPanel extends JPanel {

    /**
     * Gomb, amellyel a játékos jelezheti, hogy befejezte a körét.
     */
    protected JButton endTurnButton;

    /**
     * A játék vezérlő objektuma.
     */
    protected Controller controller;

    /**
     * Alapértelmezett konstruktor.
     * Létrehozza a panelt, beállítja az elrendezést és kirajzolja a komponenseket.
     */
    public BasicCommandPanel() {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        draw();
    }

    /**
     * Konstruktor, amely beállítja a vezérlőt.
     * Létrehozza a panelt, beállítja az elrendezést és kirajzolja a komponenseket.
     * @param controller A játék vezérlő objektuma.
     */
    public BasicCommandPanel(Controller controller) {
        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        draw();
    }

    /**
     * Létrehozza és hozzáadja a "Kör vége" gombot a panelhez,
     * valamint beállítja az eseménykezelőjét.
     */
    public void draw() {
        endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Kör vége gomb megnyomva.");
                controller.setTurnEnded(true); // Flag beállítása a kör végének jelzésére a vezérlőben
                controller.resetSelectedTektons(); // A kiválasztott tektonok törlése a kör végén
            }
        });

        this.add(endTurnButton);
    }

    /**
     * Visszaadja a "Kör vége" gombot.
     * @return A "Kör vége" gomb.
     */
    public JButton getEndTurnButton() {
        return endTurnButton;
    }
}