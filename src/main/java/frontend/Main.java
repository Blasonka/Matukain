package frontend;

import backend.jateklogika.gameLogic;
import frontend.windows.MainWindow;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @class Main
 * @brief A játék fő belépési pontja.
 *
 * @details
 * Ez az osztály felelős a játék inicializálásáért és a fő ablak megjelenítéséért.
 *
 * @note A játék grafikus részének belépési pontja.
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class Main {
    /**
     * Lejátsza a háttérzenét végtelenített lejátszással.
     */
    public static Clip musicPlayer() {
        try {
            // Load the audio file
            AudioInputStream originalStream = AudioSystem.getAudioInputStream(
                    Main.class.getResourceAsStream("/fungorium.wav"));

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

    /**
     * @brief Betölti a megadott fontot a fájlból.
     *
     * @param path A font fájl elérési útja.
     * @param size A betűméret.
     * @return A betöltött Font objektum.
     */
    public static Font loadCustomFont(String path, float size) {
        try {
            // Font betöltése a fájlból
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            // Opcionális: méret beállítása és font regisztrálása
            Font sizedFont = customFont.deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return sizedFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // Ha hiba történik, visszatérünk egy alapértelmezett fonttal
            return new Font("SansSerif", Font.PLAIN, (int)size);
        }
    }

    /**
     * @brief Betölti a fontokat és beállítja az UIManager-t.
     *
     * @details
     * Ez a metódus betölti a megadott fontot és beállítja az UIManager-t,
     * hogy az összes Swing komponens ezt a fontot használja.
     */
    public static void fontLoader(){
        // Font betöltése
        Font customFont = loadCustomFont("src/main/resources/fonts/Minecraft.ttf", 12f);

        // UIManager beállítások az összes Swing komponenshez
        UIManager.put("Button.font", customFont);
        UIManager.put("ToggleButton.font", customFont);
        UIManager.put("RadioButton.font", customFont);
        UIManager.put("CheckBox.font", customFont);
        UIManager.put("ColorChooser.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("Label.font", customFont);
        UIManager.put("List.font", customFont);
        UIManager.put("MenuBar.font", customFont);
        UIManager.put("Menu.font", customFont);
        UIManager.put("MenuItem.font", customFont);
        UIManager.put("RadioButtonMenuItem.font", customFont);
        UIManager.put("CheckBoxMenuItem.font", customFont);
        UIManager.put("PopupMenu.font", customFont);
        UIManager.put("OptionPane.font", customFont);
        UIManager.put("Panel.font", customFont);
        UIManager.put("ProgressBar.font", customFont);
        UIManager.put("ScrollPane.font", customFont);
        UIManager.put("Viewport.font", customFont);
        UIManager.put("TabbedPane.font", customFont);
        UIManager.put("Table.font", customFont);
        UIManager.put("TableHeader.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("PasswordField.font", customFont);
        UIManager.put("TextArea.font", customFont);
        UIManager.put("TextPane.font", customFont);
        UIManager.put("EditorPane.font", customFont);
        UIManager.put("TitledBorder.font", customFont);
        UIManager.put("ToolBar.font", customFont);
        UIManager.put("ToolTip.font", customFont);
        UIManager.put("Tree.font", customFont);
    }

    /**
     * @brief A játék indítási pontja.
     *
     * @details
     * Ez a metódus inicializálja a játékot és megjeleníti a fő ablakot.
     *
     * @param args Parancssori argumentumok.
     */
    public static void main(String[] args) {
        fontLoader();
        gameLogic logic = new gameLogic();
        MainWindow mainWindow = new MainWindow(logic);
    }
}