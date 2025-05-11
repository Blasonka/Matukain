package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GombatestEntity extends Entity {
    GamePanel gp;
    MouseHandler mouseHandler;
    BufferedImage playerImage;
    int state = 0; // 0 = kis gomba, 1 = kifejlett gomba

    public GombatestEntity(GamePanel gp, MouseHandler mouseHandler) {
        this.gp = gp;
        this.mouseHandler = mouseHandler;
        getPlayerImage();
    }

    public void update() {
        draw(gp.getGraphics());
    }

    public void draw(Graphics2D g) {
        getPlayerImage();
        g.drawImage(playerImage, x, y, gp.tileSize, gp.tileSize, null);
    }

    public void getPlayerImage() {
        try {
            if (state == 0) {
                playerImage = ImageIO.read(getClass().getResourceAsStream("/mushroom1.png"));
            } else if (state == 1) {
                playerImage = ImageIO.read(getClass().getResourceAsStream("/mushroom2.png"));
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
}