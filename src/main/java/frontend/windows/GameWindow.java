package frontend.windows;

import frontend.Main;
import frontend.components.panels.GamePanel;
import frontend.components.panels.GombaszPanel;
import backend.jateklogika.gameLogic;
import backend.felhasznalo.Gombasz;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

/**
 * GameWindow osztály
 *
 * @class GameWindow
 *
 * @brief A játék ablakát reprezentáló osztály
 *
 * @details
 * Az ablak, amely a játékot megjeleníti és kezeli a zene lejátszását.
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class GameWindow {
    /**
     * @var Clip musicClip
     * @brief A zene lejátszásáért felelős Clip objektum
     */
    public static Clip musicClip;
    /**
     * @var boolean isMuted
     * @brief A zene némításának állapotát tároló változó
     */
    private boolean isMuted = false;
    /**
     * @var GamePanel gamePanel
     * @brief A játék grafikus megjelenítéséért felelős GamePanel objektum
     */
    private GamePanel gamePanel;
    /**
     * @var JFrame frame
     * @brief A játék ablakát reprezentáló JFrame objektum
     */
    private JFrame frame;
    /**
     * @var GombaszPanel gombaszPanel
     * @brief A GombaszPanel objektum, amely a backend Gombasz objektumot jeleníti meg
     */
    private GombaszPanel gombaszPanel;

    /**
     * GameWindow osztály konstruktora
     *
     * @param logic A játék logikáját kezelő objektum
     * @param gombasz A backend Gombasz objektum
     */
    public GameWindow(gameLogic logic, Gombasz gombasz) {
        frame = new JFrame("Fungorium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize(1280, 720);

        musicClip = Main.musicPlayer();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        gamePanel = new GamePanel(logic, gombasz);
        gamePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        layeredPane.add(gamePanel, Integer.valueOf(0));

        // Add GombaszPanel with backend Gombasz reference
        gombaszPanel = new GombaszPanel(gombasz);
        gombaszPanel.setBounds(20, 20, 150, 150); // Example position
        layeredPane.add(gombaszPanel, Integer.valueOf(2));

        JButton muteButton = new JButton();
        muteButton.setIcon(new ImageIcon(GameWindow.class.getResource("/icons/sound.png")));
        muteButton.setBorderPainted(false);
        muteButton.setContentAreaFilled(false);
        muteButton.setFocusPainted(false);
        muteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Set muteButton position to bottom left
        int muteButtonWidth = 50;
        int muteButtonHeight = 50;
        int muteButtonX = 20; // 20px from left
        int muteButtonY = frame.getHeight() - muteButtonHeight - 20; // 20px from bottom
        muteButton.setBounds(muteButtonX, muteButtonY, muteButtonWidth, muteButtonHeight);

        muteButton.addActionListener(e -> {
            if (musicClip != null) {
                if (musicClip.isRunning()) {
                    musicClip.stop();
                    muteButton.setIcon(new ImageIcon(GameWindow.class.getResource("/icons/mute.png")));
                } else {
                    // If the clip has reached the end, reset to the beginning
                    if (musicClip.getFramePosition() == musicClip.getFrameLength()) {
                        musicClip.setFramePosition(0);
                    }
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Ensure looping
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

    /**
     * Visszaadja a játékpanel objektumot.
     *
     * @return A példány, amely a jelenlegi játékpanelt képviseli
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
