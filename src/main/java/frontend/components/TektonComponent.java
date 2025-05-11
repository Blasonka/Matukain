package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TektonComponent {
    private Tile[] tiles;
    private int xOffset;
    private int yOffset;
    private int gridSize;
    private int tileSize;
    private int breakCount = 0; // Tracks how many times the island has been broken
    public List<Integer> szomszedok = new ArrayList<>();

    public boolean tmpSpora = true;

    public TektonComponent(Tile[] tiles, int xOffset, int yOffset, int gridSize, int tileSize) {
        this.tiles = tiles;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.gridSize = gridSize;
        this.tileSize = tileSize;
    }

    public int getBreakCount() {
        return breakCount;
    }

    public void incrementBreakCount() {
        this.breakCount++;
    }

    public void draw(Graphics g) {
        int x = 0;
        int y = 0;

        for (Tile tile : tiles) {
            if (tile != null && tile.image != null) { // Null check for tile and its image
                g.drawImage(tile.image, (x + xOffset) * tileSize, (y + yOffset) * tileSize, tileSize, tileSize, null);
            }
            x++;
            if (x >= gridSize) { // Move to the next row when reaching the end of the current row
                x = 0;
                y++;
            }
        }
    }

    public void handleTileClick(int mouseX, int mouseY) {
        int relativeX = (mouseX / tileSize) - xOffset;
        int relativeY = (mouseY / tileSize) - yOffset;

        if (relativeX >= 0 && relativeX < gridSize && relativeY >= 0 && relativeY < tiles.length / gridSize) {
            int tileIndex = relativeY * gridSize + relativeX;
            if (tileIndex >= 0 && tileIndex < tiles.length) {
                Tile clickedTile = tiles[tileIndex];
                if (tmpSpora) {
                    clickedTile.incrementSporeCount();
                } else {
                    clickedTile.decrementSporeCount();
                }

                try {
                    String imagePath = switch (clickedTile.getSporeCount()) {
                        case 1 -> "/textures/tekton_1spore.png";
                        case 2 -> "/textures/tekton_2spores.png";
                        case 3 -> "/textures/tekton_3spores.png";
                        default -> new Random().nextInt(2) == 0 ? "/textures/tekton1.png" : "/textures/tekton2.png";
                    };
                    System.out.println("Loading image: " + imagePath);
                    java.io.InputStream inputStream = getClass().getResourceAsStream(imagePath);
                    if (inputStream == null) {
                        System.err.println("Failed to load image: " + imagePath + " - Resource not found");
                        clickedTile.image = null;
                    } else {
                        clickedTile.image = ImageIO.read(inputStream);
                        if (clickedTile.image == null) {
                            System.err.println("ImageIO.read returned null for: " + imagePath);
                        }
                    }
                } catch (java.io.IOException e) {
                    System.err.println("Error loading image for spore count " + clickedTile.getSporeCount() + ": " + e.getMessage());
                    e.printStackTrace();
                    clickedTile.image = null;
                }
            }
        } else {
            System.out.println("Click outside tile bounds: relativeX=" + relativeX + ", relativeY=" + relativeY);
        }
    }

    public Entity placeInitialEntity(int playerIndex, GamePanel gamePanel) {
        int centerX = (xOffset + gridSize / 2) * tileSize - 24; // Center of the island, adjusted for entity size
        int centerY = (yOffset + (tiles.length / gridSize) / 2) * tileSize - 24;

        Entity entity = null;
        if (playerIndex < 2) {

            GombatestEntity gombaEntity = new GombatestEntity(gamePanel, gamePanel.mouseHandler);
            gombaEntity.x = centerX;
            gombaEntity.y = centerY - 48;
            gombaEntity.state = 0;

            gamePanel.gombatestEntities.add(gombaEntity);
            entity = gombaEntity;
            System.out.println("Placed mushroom at (" + centerX + ", " + centerY + ") for player " + playerIndex);
        } else {
            RovarEntity rovarEntity = new RovarEntity(gamePanel, gamePanel.mouseHandler);
            rovarEntity.x = centerX;
            rovarEntity.y = centerY;
            rovarEntity.currentIsland = gamePanel.tileM.islands.indexOf(this);

            gamePanel.rovarEntities.add(rovarEntity);
            entity = rovarEntity;
            System.out.println("Placed bug at (" + centerX + ", " + centerY + ") for player " + playerIndex);
        }

        return entity;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public int getGridSize() {
        return gridSize;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getTileSize() {
        return tileSize;
    }

    // New methods to calculate actual width and height
    public int getGridWidth() {
        return gridSize; // Number of columns
    }

    public int getGridHeight() {
        return tiles.length / gridSize; // Number of rows
    }

}