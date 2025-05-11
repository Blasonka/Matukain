package frontend.windows;

import frontend.Main;
import frontend.components.GamePanel;
import backend.jateklogika.gameLogic;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class GameWindow {
    public static Clip musicClip;
    private boolean isMuted = false;
    private GamePanel gamePanel;
    private JFrame frame;

    public GameWindow(gameLogic logic) {
        frame = new JFrame("Fungorium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize(1280, 720);

        musicClip = Main.musicPlayer();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        gamePanel = new GamePanel(logic);
        gamePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        layeredPane.add(gamePanel, Integer.valueOf(0));

        JButton muteButton = new JButton();
        muteButton.setIcon(new ImageIcon(GameWindow.class.getResource("/icons/sound.png")));
        muteButton.setBorderPainted(false);
        muteButton.setContentAreaFilled(false);
        muteButton.setFocusPainted(false);
        muteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        muteButton.setBounds(frame.getWidth() - 70, 10, 50, 50);

        muteButton.addActionListener(e -> {
            if (musicClip != null) {
                if (musicClip.isRunning()) {
                    musicClip.stop();
                    muteButton.setIcon(new ImageIcon(GameWindow.class.getResource("/icons/mute.png")));
                } else {
                    musicClip.start();
                    muteButton.setIcon(new ImageIcon(GameWindow.class.getResource("/icons/sound.png")));
                }
            }
            gamePanel.requestFocusInWindow();
        });

        layeredPane.add(muteButton, Integer.valueOf(1));

        frame.setContentPane(layeredPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}