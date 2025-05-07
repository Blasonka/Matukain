package frontend.components;

import frontend.components.Tile;

import java.awt.*;

public class TektonComponent {
    private Tile[] tiles;
    private int xOffset;
    private int yOffset;
    private int gridSize; // Represents the number of columns
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