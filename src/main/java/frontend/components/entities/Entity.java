package frontend.components.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Entity osztály
 *
 * @class Entity
 *
 * @brief Az entitásokat reprezentáló osztály
 *
 * @details
 * Osztály az entitások tárolására
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */

public class Entity {
    /**
     * @var int x
     * @brief X koordinátát tárolja
     *
     * @var int y
     * @brief Y koordinátát tárolja
     *
     * @var int speed
     * @brief Sebességet tárolja
     */
    public int x;
    public int y;
    int speed;

    /**
     * @brief frissíti az entitás állapotát
     */
    public void update(){

    }

    /**
     * @brief kirajzolja az entitást
     * @param g a Graphics objektum, amire rajzolni kell
     */
    public void draw(Graphics g){

    }

    /**
     * @brief beállítja az enntitás grafikus megjelenését
     * @param b
     */
    public void getPlayerImage(BufferedImage b) {

    }
}
