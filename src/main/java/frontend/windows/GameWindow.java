package frontend.windows;

import frontend.Main;
import frontend.components.GamePanel;
import backend.jateklogika.gameLogic;

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
     * GameWindow osztály konstruktora
     *
     * @param logic A játék logikáját kezelő objektum
     */
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

    /**
     * Visszaadja a játékpanel objektumot.
     *
     * @return A példány, amely a jelenlegi játékpanelt képviseli
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }
}