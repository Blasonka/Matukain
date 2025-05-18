package frontend.components;

import javax.swing.*;
import java.awt.*;

import static frontend.Main.loadCustomFont;

/**
 * Statbar osztály
 *
 * @class Statbar
 *
 * @brief A játék statisztikáit megjelenítő osztály
 *
 * @details
 * Osztály a játék statisztikáinak megjelenítésére
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class Statbar extends JPanel {
    /**
     * @var JLabel roundLabel
     * @brief A kör számát megjelenítő JLabel
     */
    private JLabel roundLabel;
    /**
     * @var JLabel playerRoundLabel
     * @brief A játékos körét megjelenítő JLabel
     */
    private JLabel playerRoundLabel;
    /**
     * @var JLabel actionPointsLabel
     * @brief Az akciópontokat megjelenítő JLabel
     */
    private JLabel actionPointsLabel;

    int akciopont;

    /**
     * Statbar osztály konstruktora
     * @brief Inicializálja a statisztikákat megjelenítő panelt
     */
    public Statbar() {
        setLayout(new GridLayout(1, 3)); // Layout with 1 row and 3 columns
        setPreferredSize(new Dimension(1280, 50)); // Set the preferred size of the panel
        setBackground(new Color(50, 50, 50)); // Background color

        // Initialize labels
        roundLabel = new JLabel("Round: 0", SwingConstants.CENTER);
        playerRoundLabel = new JLabel("Player Round: 0", SwingConstants.CENTER);
        actionPointsLabel = new JLabel("Action Points: 0", SwingConstants.CENTER);

        // Set font and color for labels
        Font labelFont = loadCustomFont("src/main/resources/fonts/Minecraft.ttf", 18f);
        roundLabel.setFont(labelFont);
        playerRoundLabel.setFont(labelFont);
        actionPointsLabel.setFont(labelFont);

        roundLabel.setForeground(Color.WHITE);
        playerRoundLabel.setForeground(Color.WHITE);
        actionPointsLabel.setForeground(Color.WHITE);

        // Add labels to the panel
        add(roundLabel);
        add(playerRoundLabel);
        add(actionPointsLabel);
        akciopont =5;
    }

    // Method to update the round number
    /**
     * @param round A kör számát tároló változó
     * @brief Frissíti a kör számát
     */
    public void updateRound(int round) {
        roundLabel.setText("Round: " + round);
    }

    // Method to update the player's round
    /**
     * @param playerName A játékos nevét tároló változó
     * @brief Frissíti a játékos nevét
     */
    public void updatePlayerRound(String playerName) {
        playerRoundLabel.setText("Current Player: " + playerName);
    }

    // Method to update the action points
    /**
     * @param actionPoints Az akciópontokat tároló változó
     * @brief Frissíti az akciópontokat
     */
    public void updateActionPoints(int actionPoints) {
        actionPointsLabel.setText("Action Points: " + actionPoints);
    }

    // Method to update the current player's action points
    /**
     * @param actionPoints Az aktuális játékos akciópontjai
     * @brief Frissíti a jelenlegi játékos akciópontjait
     */
    public void updateCurrentPlayerActionPoints(int actionPoints) {
        actionPointsLabel.setText("Current Player Action Points: " + actionPoints);
    }

    public int getActionPoints() {
        return  akciopont;
    }
}
