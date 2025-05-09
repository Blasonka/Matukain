package frontend.windows;

import frontend.Main;
import frontend.components.GamePanel;
import frontend.components.Statbar;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow {
    public static Clip musicClip; // Reference to the music clip
    private boolean isMuted = false; // Track mute state

    public GameWindow() {
        JFrame frame2 = new JFrame("Fungorium");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setUndecorated(true);
        frame2.setResizable(false);
        frame2.setSize(1280, 720);

        // Start music and store the clip reference
        musicClip = Main.musicPlayer();

        // Create a JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, frame2.getWidth(), frame2.getHeight());

        // Add GamePanel to the background layer
        GamePanel gamePanel = new GamePanel();
        gamePanel.setBounds(0, 0, frame2.getWidth(), frame2.getHeight());
        layeredPane.add(gamePanel, Integer.valueOf(0)); // Background layer

        // Add mute button to the top layer
        JButton muteButton = new JButton();
        muteButton.setIcon(new ImageIcon(GameWindow.class.getResource("/resources/icons/sound.png")));
        muteButton.setBorderPainted(false);
        muteButton.setContentAreaFilled(false);
        muteButton.setFocusPainted(false);
        muteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        muteButton.setBounds(frame2.getWidth() - 70, 10, 50, 50); // Absolute positioning

        muteButton.addActionListener(e -> {
            if (musicClip.isRunning()) {
                musicClip.stop(); // Mute the audio
                muteButton.setIcon(new ImageIcon(GameWindow.class.getResource("/resources/icons/mute.png")));

            } else {
                musicClip.start(); // Unmute the audio
                muteButton.setIcon(new ImageIcon(GameWindow.class.getResource("/resources/icons/sound.png")));
            }
            gamePanel.requestFocusInWindow(); // Ensure the GamePanel regains focus
        });


        layeredPane.add(muteButton, Integer.valueOf(1)); // Top layer



        // Add the layered pane to the frame
        frame2.setContentPane(layeredPane);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
    }
}