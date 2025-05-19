package frontend.components.panels;

import frontend.GameState;
import frontend.components.controllers.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static frontend.components.panels.GamePanel.state;

/**
 * RovaraszPanel osztály
 * @class RovaraszPanel
 * @brief A rovarász akcióit tartalmazó panel
 * @details
 * A rovarász számára elérhető akciókat (Mozgatás, Spóraevés, Fonalelvágás) tartalmazó panel.
 * @version 1.0
 * @date 2025-05-17
 */
public class RovaraszPanel extends JPanel {
    private JButton mozgatasButton;
    private JButton sporaevesButton;
    private JButton fonalelvagasButton;
    private GameController controller;

    public RovaraszPanel() {
        setLayout(new GridLayout(3, 1, 10, 10)); // 3 sor, 1 oszlop, 10px távolság
        setPreferredSize(new Dimension(150, 150));
        setBackground(new Color(150, 100, 100)); // Vöröses háttér

        // Gombok inicializálása
        mozgatasButton = new JButton("Mozgatás");
        sporaevesButton = new JButton("Spóraevés");
        fonalelvagasButton = new JButton("Fonalelvágás");

        // Gombok stílusa
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        mozgatasButton.setFont(buttonFont);
        sporaevesButton.setFont(buttonFont);
        fonalelvagasButton.setFont(buttonFont);

        // Eseménykezelők hozzáadása (egyelőre csak konzolra íratunk)
        mozgatasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = GameState.MOZGATAS;
                controller.handleMozgatas();
            }
        });

        sporaevesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = GameState.SPORAEVES;
                controller.handleSporaeves();
            }
        });

        fonalelvagasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = GameState.FONALELVAGAS;
                controller.handleFonalelvagas();
            }
        });

        // Gombok hozzáadása a panelhez
        add(mozgatasButton);
        add(sporaevesButton);
        add(fonalelvagasButton);

        setVisible(false); // Kezdetben láthatatlan
    }
    public void setController(GameController controller) {
        this.controller = controller;
    }
}