package frontend.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public GombaszPanel() {
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

        // Eseménykezelők hozzáadása (egyelőre csak konzolra íratunk)
        sporanovesztesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Spóranövesztés gomb megnyomva!");
            }
        });

        gombanovesztesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Gombanövesztés gomb megnyomva!");
            }
        });

        fonalnovesztesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Fonalnövesztés gomb megnyomva!");
            }
        });

        // Gombok hozzáadása a panelhez
        add(sporanovesztesButton);
        add(gombanovesztesButton);
        add(fonalnovesztesButton);

        setVisible(false); // Kezdetben láthatatlan
    }
}