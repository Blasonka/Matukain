package frontend.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import backend.felhasznalo.Gombasz;

import static frontend.components.GamePanel.state;

/**
 * GombaszPanel osztály
 * @class GombaszPanel
 * @brief A gombász akcióit tartalmazó panel
 * @details
 * A gombász számára elérhető akciókat (Spóranövesztés, Gombanövesztés, Fonalnövesztés) tartalmazó panel.
 * @version 1.0
 * @date 2025-05-17
 */
public class GombaszPanel extends JPanel {
    private JButton sporanovesztesButton;
    private JButton gombanovesztesButton;
    private JButton fonalnovesztesButton;
    private GameController controller;
    private Gombasz gombasz;

    public GombaszPanel(Gombasz gombasz) {
        this.gombasz = gombasz;
        setLayout(new GridLayout(3, 1, 10, 10)); // 3 sor, 1 oszlop, 10px távolság
        setPreferredSize(new Dimension(150, 150));
        setBackground(new Color(100, 150, 100)); // Zöldes háttér

        // Gombok inicializálása
        sporanovesztesButton = new JButton("Spóranövesztés");
        gombanovesztesButton = new JButton("Gombanövesztés");
        fonalnovesztesButton = new JButton("Fonalnövesztés");

        // Gombok stílusa
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
        sporanovesztesButton.setFont(buttonFont);
        gombanovesztesButton.setFont(buttonFont);
        fonalnovesztesButton.setFont(buttonFont);

        // Eseménykezelők hozzáadása a GameController metódusaihoz
        sporanovesztesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = GameState.SPORANOVESZTES;
                controller.handleSporanoveszt();
            }
        });

        gombanovesztesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = GameState.GOMBANOVESZTES;
                controller.handleGombanoveszt();
            }
        });

        fonalnovesztesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = GameState.FONALNOVESZTES;
                //controller.handleFonalnoveszt();
            }
        });

        // Gombok hozzáadása a panelhez
        add(sporanovesztesButton);
        add(gombanovesztesButton);
        add(fonalnovesztesButton);

        setVisible(false); // Kezdetben láthatatlan
    }

    // Setter a GameController-hoz, hogy az eseményeket kezelhesse
    public void setController(GameController controller) {
        this.controller = controller;
    }
}

