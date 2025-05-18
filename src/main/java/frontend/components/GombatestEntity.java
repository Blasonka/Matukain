package frontend.components;

import backend.gomba.Gomba;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * GombatestEntity osztály
 *
 * @class GombatestEntity
 *
 * @brief A gombatest entitásokat reprezentáló osztály
 *
 * @details
 * Osztály a gombatestek tárolására
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class GombatestEntity extends Entity {
    /**
     * @var Gomba gomba
     * @brief Az entitáshoz tartozó frontend oldali párja
     */
    Gomba gomba;
    /**
     * @var GamePanel gp
     * @brief A GamePanel objektum, amely a játék grafikus megjelenítéséért felelős
     */
    GamePanel gp;
    /**
     * @var MouseHandler mouseHandler
     * @brief A MouseHandler objektum, amely az egér események kezeléséért felelős
     */
    MouseHandler mouseHandler;
    /**
     * @var BufferedImage playerImage
     * @brief A gombatest grafikus megjelenését tároló BufferedImage objektum
     */
    BufferedImage playerImage;
    /**
     * @var int state
     * @brief A gombatest állapotát tároló változó (0 = kis gomba, 1 = kifejlett gomba)
     */
    int state = 0; // 0 = kis gomba, 1 = kifejlett gomba

    /**
     * A gombatest tulajdonos gombász indexe (0 = első, 1 = második)
     */
    private int ownerIndex = 0;

    /**
     * GombatestEntity osztály konstruktora
     * @param g Gomba
     * @param gp GamePanel
     * @param mouseHandler Egér
     */
    public GombatestEntity(Gomba g, GamePanel gp, MouseHandler mouseHandler, int ownerIndex) {
        this.gomba = g;
        this.gp = gp;
        this.mouseHandler = mouseHandler;
        this.ownerIndex = ownerIndex;
        getPlayerImage();
    }

    /**
     * Frissíti a gombatest állapotát
     */
    public void update() {
        draw(gp.getGraphics());
    }

    /**
     * Kirajzolja a gombatestet a megadott Graphics2D objektumra
     * @param g a Graphics2D objektum, amire rajzolni kell
     */
    public void draw(Graphics2D g) {
        getPlayerImage();
        g.drawImage(playerImage, x, y, gp.tileSize, gp.tileSize, null);
    }

    /**
     * Betölti a gombatest entitás aktuális állapotához tartozó képet.
     * @note Ha a kép betöltése nem sikerül, akkor hibaüzenetet ír ki a konzolra.
     */
    public void getPlayerImage() {
        try {
            if (ownerIndex == 1) {
                if (state == 0) {
                    playerImage = ImageIO.read(getClass().getResourceAsStream("/mushroom1b.png"));
                } else if (state == 1) {
                    playerImage = ImageIO.read(getClass().getResourceAsStream("/mushroom2b.png"));
                }
            } else {
                    // Első játékos: az állapot alapján
                if (state == 0) {
                    playerImage = ImageIO.read(getClass().getResourceAsStream("/mushroom1.png"));
                } else if (state == 1) {
                    playerImage = ImageIO.read(getClass().getResourceAsStream("/mushroom2.png"));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
}


