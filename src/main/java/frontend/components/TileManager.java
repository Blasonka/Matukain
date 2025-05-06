package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class TileManager {
    GamePanel gp;
    Tile[] tiles;
    private Tile[] tiles1;
    private Tile[] tiles2;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[50];
        getTileImage();
    }
    public void getTileImage(){
        Random random = new Random();
        try {
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = new Tile();
                int randomNumber = random.nextInt(2);
                if (randomNumber == 0) {
                    tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/resources/textures/tekton1.png"));
                } else {
                    tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/resources/textures/tekton2.png"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        int gridSize = (int) Math.sqrt(tiles.length / 2); // Négyzetes elrendezéshez
        int x = 0;
        int y = 0;

        // Első sziget
        int xOffset = 5; // Példa: 5 csempe eltolás jobbra
        int yOffset = 3; // Példa: 3 csempe eltolás lefelé

        for (int i = 0; i < tiles.length / 2; i++) {
            g.drawImage(tiles[i].image, (x + xOffset) * gp.tileSize, (y + yOffset) * gp.tileSize, gp.tileSize, gp.tileSize, null);

            x++;
            if (x >= gridSize) { // Ha elérjük a sor végét, lépünk a következő sorra
                x = 0;
                y++;
            }
        }

        // Második sziget
        x = 0;
        y = 0;
        xOffset = 13; // Példa: 10 csempe eltolás jobbra
        yOffset = 5; // Példa: 6 csempe eltolás lefelé

        for (int i = tiles.length / 2; i < tiles.length; i++) {
            g.drawImage(tiles[i].image, (x + xOffset) * gp.tileSize, (y + yOffset) * gp.tileSize, gp.tileSize, gp.tileSize, null);

            x++;
            if (x >= gridSize) { // Ha elérjük a sor végét, lépünk a következő sorra
                x = 0;
                y++;
            }
        }
    }
}
