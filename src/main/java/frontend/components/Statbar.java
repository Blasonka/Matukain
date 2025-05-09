package frontend.components;

import javax.swing.*;
import java.awt.*;

public class Statbar extends JPanel {
    private JLabel roundLabel;
    private JLabel playerRoundLabel;
    private JLabel actionPointsLabel;

    public Statbar() {
        setLayout(new GridLayout(1, 3)); // Layout with 1 row and 3 columns
        setPreferredSize(new Dimension(1280, 50)); // Set the preferred size of the panel
        setBackground(new Color(50, 50, 50)); // Background color

        // Initialize labels
        roundLabel = new JLabel("Round: 0", SwingConstants.CENTER);
        playerRoundLabel = new JLabel("Player Round: 0", SwingConstants.CENTER);
        actionPointsLabel = new JLabel("Action Points: 0", SwingConstants.CENTER);

        // Set font and color for labels
        Font labelFont = new Font("SansSerif", Font.BOLD, 18);
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
    }

    // Method to update the round number
    public void updateRound(int round) {
        roundLabel.setText("Round: " + round);
    }

    // Method to update the player's round
    public void updatePlayerRound(int playerRound) {
        playerRoundLabel.setText("Player Round: " + playerRound);
    }

    // Method to update the action points
    public void updateActionPoints(int actionPoints) {
        actionPointsLabel.setText("Action Points: " + actionPoints);
    }
}