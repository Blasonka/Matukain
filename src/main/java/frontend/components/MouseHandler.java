package frontend.components;

import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private GamePanel gamePanel;
    private TileManager tileManager;
    Coordinate coordinate = new Coordinate(0, 0);
    boolean clicked = false;

    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tileManager = gamePanel.tileM; // Reference to TileManager
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        int mouseX = e.getX() / gamePanel.tileSize;
        int mouseY = e.getY() / gamePanel.tileSize;

        boolean foundIsland = false;
        int islandId = 0; // To track the index of the island

        for (TektonComponent island : tileManager.islands) {
            int islandStartX = island.getXOffset();
            int islandStartY = island.getYOffset();
            int islandEndX = islandStartX + island.getGridSize() - 1;
            int islandEndY = islandStartY + island.getGridSize() - 1;

            if (mouseX >= islandStartX && mouseX < islandEndX &&
                    mouseY >= islandStartY && mouseY < islandEndY) {
                // Set coordinate to the center of the island
                coordinate.x = (islandStartX + islandEndX) * gamePanel.tileSize / 2;
                coordinate.y = (islandStartY + islandEndY) * gamePanel.tileSize / 2;
                foundIsland = true;
                System.out.println("Island found! ID: " + islandId + ", Center set to: " + coordinate.x + ", " + coordinate.y);
                break;
            }
            islandId++; // Increment the index for the next island
        }

        if (!foundIsland) {
            // Do not change the coordinate if no island is found
            System.out.println("No island found at clicked position.");
        }

        clicked = true;
    }

    public void returnFalse() {
        clicked = false;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {}
}