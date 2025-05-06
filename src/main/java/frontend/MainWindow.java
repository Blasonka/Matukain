package frontend;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame = new JFrame("Fungorium");
    boolean vanemeg = false;

    public MainWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize(1280, 720);

        // Create a JLayeredPane with GridBagLayout
        JLayeredPane layeredPane = new JLayeredPane();

        // Background image
        ImageIcon background = new ImageIcon(MainWindow.class.getResource("/resources/background.png"));
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // Create a JPanel for the title and button
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false); // Make the panel transparent

        // Title label
        JLabel title = new JLabel("Fungorium", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(48f));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment in the panel
        panel.add(title);

        // Add spacing between title and button
        panel.add(Box.createVerticalStrut(10));

        // Start button
        JButton startButton = new JButton("Start");
        startButton.setFont(startButton.getFont().deriveFont(24f));
        startButton.setFont(startButton.getFont().deriveFont(Font.ITALIC));
        startButton.setForeground(new Color(255, 255, 255, 200));
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment in the panel
        startButton.addActionListener(e -> {
            vanemeg = true;
            frame.dispose();
        });
        panel.add(startButton);

        // Set bounds for the panel to ensure it is centered
        int panelWidth = 400; // Example width for the panel
        int panelHeight = 300; // Example height for the panel
        int panelX = ((frame.getWidth() - panelWidth) / 2);
        int panelY = ((frame.getHeight() - panelHeight) / 2)+150;
        panel.setBounds(panelX, panelY, panelWidth, panelHeight);

        // Add background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0)); // Background layer
        layeredPane.add(panel, Integer.valueOf(1)); // Panel layer

        // Remove GridBagLayout since JLayeredPane does not handle it well
        layeredPane.setLayout(null);

        // Add the layered pane to the frame
        frame.setContentPane(layeredPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
