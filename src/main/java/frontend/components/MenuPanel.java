package frontend.components;

import javax.swing.*;
import java.awt.*;
import backend.jateklogika.gameLogic;
import backend.felhasznalo.Gombasz;
import backend.felhasznalo.Rovarasz;
import frontend.windows.GameWindow;

/**
 * MenuPanel osztály
 *
 * @class MenuPanel
 *
 * @brief A játék főmenüjét reprezentáló osztály
 *
 * @details
 * A MenuPanel osztály a játék főmenüjét valósítja meg, ahol a felhasználók
 * megadhatják a nevüket és elindíthatják a játékot.
 *
 * @note A főmenü grafikus felületét valósítja meg.
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class MenuPanel extends JPanel {
    /**
     * @var JTextField[] nameFields
     * @brief A felhasználók neveit tároló szövegmezők tömbje
     */
    private JTextField[] nameFields;
    /**
     * @var JButton startButton
     * @brief A játék indításáért felelős gomb
     */
    private JButton startButton;
    /**
     * @var JFrame frame
     * @brief A főmenü kerete
     */
    private JFrame frame;
    /**
     * @var gameLogic logic
     * @brief A játék logikáját kezelő objektum
     */
    private gameLogic logic;

    /**
     * MenuPanel osztály konstruktora
     * @param f A főmenü kerete
     * @param l A játék logikáját kezelő objektum
     */
    public MenuPanel(JFrame f, gameLogic l) {
        this.frame = f;
        this.logic = l;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1280, 720));
        setBackground(new Color(0, 0, 1, 0)); // Transparent background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        nameFields = new JTextField[5];
        String[] labels = {
                "Adja meg a gombasz nevet", "Adja meg a gombasz nevet",
                "Adja meg a rovarasz nevet", "Adja meg a rovarasz nevet",
                null
        };

        for (int i = 0; i < 5; i++) {
            if (i < 4) {
                if (labels[i] != null) {
                    JLabel label = new JLabel(labels[i]);
                    label.setForeground(Color.WHITE);
                    label.setFont(label.getFont().deriveFont(18f));
                    gbc.gridx = 0;
                    gbc.gridy = i * 2;
                    add(label, gbc);
                }

                nameFields[i] = new JTextField(20);
                nameFields[i].setFont(nameFields[i].getFont().deriveFont(18f));
                nameFields[i].setBackground(Color.WHITE);
                gbc.gridx = 0;
                gbc.gridy = i * 2 + 1;
                add(nameFields[i], gbc);
            } else {
                startButton = new JButton("Start");
                startButton.setFont(startButton.getFont().deriveFont(24f));
                startButton.setForeground(Color.WHITE);
                startButton.setBackground(new Color(0, 128, 0));
                startButton.setFocusPainted(false);
                startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                gbc.gridx = 0;
                gbc.gridy = i * 2 + 1;
                add(startButton, gbc);

                startButton.addActionListener(e -> {
                    String[] names = getNames();

                    // Duplikált név ellenőrzés
                    java.util.Set<String> nameSet = new java.util.HashSet<>();
                    boolean hasDuplicate = false;
                    for (String name : names) {
                        if (!nameSet.add(name)) {
                            hasDuplicate = true;
                            break;
                        }
                    }
                    if (hasDuplicate) {
                        JOptionPane.showMessageDialog(this, "Ket jatekosnak nem lehet ugyanaz a neve!", "Hiba", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    logic.createUsers(names);
                    logic.jatekKezdes(); // Initialize the game after creating users
                    frame.dispose();
                    new GameWindow(logic); // Start the game window
                });
            }
        }
    }

    /**
     * Visszaadja a felhasználók neveit
     * @return A felhasználók neveit tartalmazó tömb
     */
    public String[] getNames() {
        String[] names = new String[4];
        for (int i = 0; i < 4; i++) {
            names[i] = nameFields[i].getText().trim();
            if (names[i].isEmpty()) {
                names[i] = "Player" + (i + 1);
            }
        }
        return names;
    }
}

