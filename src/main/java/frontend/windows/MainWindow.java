package frontend.windows;

import frontend.components.panels.MenuPanel;
import backend.jateklogika.gameLogic;

import javax.swing.*;
import java.awt.*;

/**
 * MainWindow osztály
 *
 * @class MainWindow
 *
 * @brief A fő ablakot reprezentáló osztály
 *
 * @details
 * Az ablak, amely a játék főmenüjét megjeleníti.
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class MainWindow {
    /**
     * @var JFrame frame
     * @brief A fő ablakot reprezentáló JFrame objektum
     */
    JFrame frame = new JFrame("Fungorium");
    /**
     * @var gameLogic logic
     * @brief A játék logikáját kezelő objektum
     */
    private gameLogic logic;

    /**
     * MainWindow osztály konstruktora
     *
     * @param logic A játék logikáját kezelő objektum
     */
    public MainWindow(gameLogic logic) {
        this.logic = logic;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize(1280, 720);

        JLayeredPane layeredPane = new JLayeredPane();

        ImageIcon background = new ImageIcon(MainWindow.class.getResource("/background.png"));
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
            JFrame menuFrame = new JFrame("Fungorium");
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setUndecorated(true);
            menuFrame.setResizable(false);
            menuFrame.setSize(1280, 720);

            // Pass the gameLogic instance to MenuPanel
            MenuPanel menuPanel = new MenuPanel(menuFrame, logic);
            menuFrame.setContentPane(menuPanel);
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setVisible(true);

            frame.dispose();
        });
        panel.add(startButton);

        int panelWidth = 400;
        int panelHeight = 300;
        int panelX = ((frame.getWidth() - panelWidth) / 2);
        int panelY = ((frame.getHeight() - panelHeight) / 2) + 150;
        panel.setBounds(panelX, panelY, panelWidth, panelHeight);

        layeredPane.add(backgroundLabel, Integer.valueOf(0));
        layeredPane.add(panel, Integer.valueOf(1));

        layeredPane.setLayout(null);

        frame.setContentPane(layeredPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * @brief A fő ablak bezárása
     */
    public void dispose() {
        frame.dispose();
    }
}