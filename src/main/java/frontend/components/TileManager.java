package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileManager {
    GamePanel gp;
    public List<TektonComponent> islands;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.islands = new ArrayList<>();
        createIslands(5); // Example: Create 5 islands
    }

    private void createIslands(int numberOfIslands) {
        Random random = new Random();
        for (int i = 0; i < numberOfIslands; i++) {
            Tile[] tiles = new Tile[25]; // Each island has 25 tiles
            initializeTiles(tiles);

            int gridSize = (int) Math.sqrt(tiles.length); // Square layout
            int xOffset, yOffset;

            // Find a valid position that does not overlap
            do {
                xOffset = random.nextInt(gp.maxScreenCol - gridSize - 1); // Leave 1-tile margin
                yOffset = random.nextInt(gp.maxScreenRow - gridSize - 1); // Leave 1-tile margin
            } while (isOverlapping(xOffset, yOffset, gridSize));

            islands.add(new TektonComponent(tiles, xOffset, yOffset, gridSize, gp.tileSize));
        }
    }

    private boolean isOverlapping(int xOffset, int yOffset, int gridSize) {
        for (TektonComponent island : islands) {
            int islandRight = island.getXOffset() + island.getGridSize() + 1;
            int islandBottom = island.getYOffset() + island.getGridSize() + 1;
            int newRight = xOffset + gridSize + 1;
            int newBottom = yOffset + gridSize + 1;

            if (xOffset < islandRight && newRight > island.getXOffset() &&
                    yOffset < islandBottom && newBottom > island.getYOffset()) {
                return true; // Overlap detected
            }
        }
        return false; // No overlap
    }

    private void initializeTiles(Tile[] tiles) {
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
        for (TektonComponent island : islands) {
            island.draw(g);
        }
    }
}