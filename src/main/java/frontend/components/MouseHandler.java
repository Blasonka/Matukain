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
        int islandId = 0;

        for (TektonComponent island : tileManager.islands) {
            int islandStartX = island.getXOffset();
            int islandStartY = island.getYOffset();
            int islandEndX = islandStartX + island.getGridSize();
            int islandEndY = islandStartY + island.getGridSize();

            if (mouseX >= islandStartX && mouseX < islandEndX &&
                    mouseY >= islandStartY && mouseY < islandEndY) {
                coordinate.x = (islandStartX + islandEndX) * gamePanel.tileSize / 2;
                coordinate.y = (islandStartY + islandEndY) * gamePanel.tileSize / 2;
                foundIsland = true;
                System.out.println("Island found! ID: " + islandId + ", Center set to: " + coordinate.x + ", " + coordinate.y);
                break;
            }
            islandId++;
        }

        if (!foundIsland) {
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