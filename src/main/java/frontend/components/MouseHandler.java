package frontend.components;

import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private GamePanel gamePanel;
    private TileManager tileManager;
    Coordinate coordinate = new Coordinate(0, 0);
    boolean clicked = false;
    public int selectedIsland = 0;

    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tileManager = gamePanel.tileM;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (TektonComponent island : gamePanel.tileM.islands) {
            int islandX = island.getXOffset() * island.getTileSize();
            int islandY = island.getYOffset() * island.getTileSize();
            int islandWidth = island.getGridWidth() * island.getTileSize();
            int islandHeight = island.getGridHeight() * island.getTileSize();


            if (mouseX >= islandX && mouseX < islandX + islandWidth &&
                    mouseY >= islandY && mouseY < islandY + islandHeight) {
                coordinate.x = (islandX + islandWidth / 2) - 24;
                coordinate.y = (islandY + islandHeight / 2) - 24;


                selectedIsland = gamePanel.tileM.islands.indexOf(island);
                System.out.println("Island clicked: " + selectedIsland);


                if (gamePanel.currentPlayerIndex < 4) {

                    island.placeInitialEntity(gamePanel.currentPlayerIndex, gamePanel);
                    clicked = true;
                } else {

                    island.handleTileClick(mouseX, mouseY);
                }

                gamePanel.repaint();
                break;
            }
        }
    }

    public void returnFalse() {
        clicked = false;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        ;
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {}
}