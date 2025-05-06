package frontend.components;

import java.awt.*;

public class TektonComponent {
    private Tile[] tiles;
    private int xOffset;
    private int yOffset;
    private int gridSize;
    private int tileSize;

    public TektonComponent(Tile[] tiles, int xOffset, int yOffset, int gridSize, int tileSize) {
        this.tiles = tiles;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.gridSize = gridSize;
        this.tileSize = tileSize;
    }

    public void draw(Graphics g) {
        int x = 0;
        int y = 0;

        for (Tile tile : tiles) {
            g.drawImage(tile.image, (x + xOffset) * tileSize, (y + yOffset) * tileSize, tileSize, tileSize, null);

            x++;
            if (x >= gridSize) { // Move to the next row when reaching the end of the current row
                x = 0;
                y++;
            }
        }
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
}