package frontend;

import frontend.components.MenuPanel;
import frontend.windows.GameWindow;
import frontend.windows.MainWindow;

import javax.sound.sampled.*;
import javax.swing.*;

public class Main {
    public static Clip musicPlayer() {
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

            return clip; // Return the clip for external control
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();

    }
}
