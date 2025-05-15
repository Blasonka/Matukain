package frontend.components;

import java.awt.event.MouseListener;

/**
 * MouseHandler osztály
 *
 * @class MouseHandler
 *
 * @brief Az egér események kezelésére szolgáló osztály
 *
 * @details
 * A MouseHandler osztály kezeli az egér eseményeket, például a kattintásokat,
 * és frissíti a játék állapotát ennek megfelelően.
 *
 * @note Az egér események kezelésére készült.
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class MouseHandler implements MouseListener {
    /**
     * @var GamePanel gamePanel
     * @brief A GamePanel objektum, amely a játék grafikus megjelenítéséért felelős
     */
    private GamePanel gamePanel;
    /**
     * @var TileManager tileManager
     * @brief A TileManager objektum, amely a csempekezelésért felelős
     */
    private TileManager tileManager;
    /**
     * @var Coordinate coordinate
     * @brief A koordinátákat tároló objektum
     */
    Coordinate coordinate = new Coordinate(0, 0);
    /**
     * @var boolean clicked
     * @brief A kattintás állapotát tároló változó
     */
    boolean clicked = false;
    /**
     * @var int selectedIsland
     * @brief Az aktuálisan kiválasztott sziget indexét tároló változó
     */
    public int selectedIsland = 0;

    /**
     * MouseHandler osztály konstruktora
     * @param gamePanel A GamePanel objektum, amely a játék grafikus megjelenítéséért felelős
     */
    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tileManager = gamePanel.tileM;
    }

    /**
     * @brief Kezeli az egér kattintás eseményét
     * @param e Az egér esemény objektum
     */
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

    /**
     * Visszaállítja a kattintás állapotát hamisra.
     */
    public void returnFalse() {
        clicked = false;
    }

    /**
     * Egérgomb lenyomásának eseménykezelője.
     * @param e Az egér esemény objektuma
     */
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        ;
    }

    /**
     * Egérgomb felengedésének eseménykezelője.
     * @param e Az egér esemény objektuma
     */
    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    /**
     * Egér kurzor belépésének eseménykezelője.
     * @param e Az egér esemény objektuma
     */
    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}

    /**
     * Egér kurzor kilépésének eseménykezelője.
     * @param e Az egér esemény objektuma
     */
    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {}
}