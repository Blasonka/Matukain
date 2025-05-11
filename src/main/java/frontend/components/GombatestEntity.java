package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class GombatestEntity extends Entity{
    GamePanel gp;
    MouseHandler mouseHandler;
    BufferedImage playerImage;
    int currentIsland = 1;
    int state = 0; //0=kis gomba, 1=kifejett gomba

    public GombatestEntity(GamePanel gp, MouseHandler mouseHandler) {
        this.gp = gp;
        this.mouseHandler = mouseHandler;
        setDefaultValues(currentIsland);
        getPlayerImage();
    }

    public void setDefaultValues(int islandIndex) {
        x = (gp.tileM.islands.get(islandIndex).getXOffset() * gp.tileSize + (gp.tileM.islands.get(islandIndex).getGridSize() * gp.tileSize) / 2)-24;
        y = (gp.tileM.islands.get(islandIndex).getYOffset() * gp.tileSize + (gp.tileM.islands.get(islandIndex).getGridSize() * gp.tileSize) / 2)-24-48;
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
