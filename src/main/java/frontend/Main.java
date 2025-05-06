package frontend;

import frontend.components.GamePanel;

import javax.sound.sampled.*;
import javax.swing.*;

public class Main {
    public static void musicPlayer() {
        new Thread(() -> {
            try {
                // Load the audio file
                AudioInputStream originalStream = AudioSystem.getAudioInputStream(
                        Main.class.getResourceAsStream("/resources/fungorium.wav"));

                // Define target format (16-bit signed is widely supported)
                AudioFormat targetFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        44100,
                        16, // bits per sample
                        2,  // channels (stereo)
                        4,  // frame size (2 bytes per sample * 2 channels)
                        44100,
                        false // little endian
                );

                // Convert the stream
                AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, originalStream);

                // Get the clip
                Clip clip = AudioSystem.getClip();
                clip.open(convertedStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                // Keep program running
                Thread.sleep(Long.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();

        while (!mainWindow.vanemeg) {
            try {
                Thread.sleep(100); // Check every 100ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        JFrame frame2 = new JFrame("Fungorium");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setUndecorated(true);
        frame2.setResizable(false);
        frame2.setSize(1280, 720);
        musicPlayer();
        GamePanel gamePanel = new GamePanel();
        frame2.add(gamePanel);

        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);


    }
}
