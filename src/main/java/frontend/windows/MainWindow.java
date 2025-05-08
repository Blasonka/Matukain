package frontend.windows;

import frontend.components.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame = new JFrame("Fungorium");

    public MainWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize(1280, 720);

        JLayeredPane layeredPane = new JLayeredPane();

        ImageIcon background = new ImageIcon(MainWindow.class.getResource("/resources/background.png"));
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel title = new JLabel("Fungorium", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(48f));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(10));

        JButton startButton = new JButton("Start");
        startButton.setFont(startButton.getFont().deriveFont(24f));
        startButton.setFont(startButton.getFont().deriveFont(Font.ITALIC));
        startButton.setForeground(new Color(255, 255, 255, 200));
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            GameWindow gameWindow = new GameWindow();
            /*
            JFrame menuFrame = new JFrame("Fungorium");
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setUndecorated(true);
            menuFrame.setResizable(false);
            menuFrame.setSize(1280, 720);

            MenuPanel menuPanel = new MenuPanel(menuFrame);
            menuFrame.setContentPane(menuPanel);
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setVisible(true);
            */
            frame.dispose();
        });
        panel.add(startButton);

        int panelWidth = 400;
        int panelHeight = 300;
        int panelX = ((frame.getWidth() - panelWidth) / 2);
        int panelY = ((frame.getHeight() - panelHeight) / 2)+150;
        panel.setBounds(panelX, panelY, panelWidth, panelHeight);

        layeredPane.add(backgroundLabel, Integer.valueOf(0));
        layeredPane.add(panel, Integer.valueOf(1));

        layeredPane.setLayout(null);

        frame.setContentPane(layeredPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}