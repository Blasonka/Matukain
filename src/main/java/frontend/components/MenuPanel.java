package frontend.components;

import javax.swing.*;
import java.awt.*;
import frontend.windows.*;

public class MenuPanel extends JPanel {
    private JTextField[] nameFields;
    private JButton startButton;
    private JFrame frame;

    public MenuPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1280, 720));
        setBackground(new Color(0, 0, 1, 0)); // Transparent background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        nameFields = new JTextField[5];
        String[] labels = {
                "Adja meg a gombász nevét", "Adja meg a gombász nevét",
                "Adja meg a rovarász nevét", "Adja meg a rovarász nevét",
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
                    frame.dispose();
                    new GameWindow();
                });
            }
        }
    }

    public String[] getNames() {
        String[] names = new String[4];
        for (int i = 0; i < 4; i++) {
            names[i] = nameFields[i].getText().trim();
        }
        return names;
    }
}