package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TektonComponent osztály
 *
 * @class TektonComponent
 *
 * @brief A tekton-szigetek komponensét reprezentáló osztály
 *
 * @details
 * Osztály a tekton-szigetek tárolására és kezelésére
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class TektonComponent {
    /**
     * @var Tile[] tiles
     * @brief A szigetet alkotó csempék tömbje
     */
    private Tile[] tiles;
    /**
     * @var int xOffset
     * @brief A sziget X koordinátájának eltolása
     */
    private int xOffset;
    /**
     * @var int yOffset
     * @brief A sziget Y koordinátájának eltolása
     */
    private int yOffset;
    /**
     * @var int gridSize
     * @brief A sziget mérete
     */
    private int gridSize;
    /**
     * @var int tileSize
     * @brief A csempe mérete
     */
    private int tileSize;
    /**
     * @var int breakCount
     * @brief A sziget törési számát tároló változó
     */
    private int breakCount = 0; // Tracks how many times the island has been broken
    /**
     * @var List<Integer> szomszedok
     * @brief A sziget szomszédos szigeteit tároló lista
     */
    public List<Integer> szomszedok = new ArrayList<>();
    /**
     * @var int sporeCount
     * @brief A sziget spóráinak számát tároló változó
     */
    private int sporeCount = 0; // Tracks the spore count for the entire island

    /**
     * TektonComponent osztály konstruktora
     *
     * @param tiles A szigetet alkotó csempék tömbje
     * @param xOffset A sziget X koordinátájának eltolása
     * @param yOffset A sziget Y koordinátájának eltolása
     * @param gridSize A sziget mérete
     * @param tileSize A csempe mérete
     */
    public TektonComponent(Tile[] tiles, int xOffset, int yOffset, int gridSize, int tileSize) {
        this.tiles = tiles;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.gridSize = gridSize;
        this.tileSize = tileSize;
    }

    /**
     * Visszaadja a sziget törési számát
     *
     * @return A sziget törési száma
     */
    public int getBreakCount() {
        return breakCount;
    }

    /**
     * Növeli a sziget törési számát
     */
    public void incrementBreakCount() {
        this.breakCount++;
    }

    /**
     * Kirajzolja a csempéket a képernyőre.
     *
     * @param g Az objektum, amelyre a kirajzolás történik
     *
     * @details
     * Minden csempe pozíciója a rács X és Y koordinátájától, valamint az eltolástól (offset)
     * függ. Ha a csempe vagy annak képe null, akkor azt nem rajzolja ki.
     */
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

    /**
     * Kezeli a csempe kattintás eseményét.
     *
     * @param mouseX Az egér X koordinátája
     * @param mouseY Az egér Y koordinátája
     *
     * @details
     * Ellenőrzi, hogy a kattintás a sziget területén belül történt-e, és ha igen,
     * akkor növeli a spóra számot és frissíti a csempe képet.
     */
    public void handleTileClick(int mouseX, int mouseY) {
        int relativeX = (mouseX / tileSize) - xOffset;
        int relativeY = (mouseY / tileSize) - yOffset;

        if (relativeX >= 0 && relativeX < gridSize && relativeY >= 0 && relativeY < tiles.length / gridSize && sporeCount<=2) {
            sporeCount++;

            String imagePath = switch (sporeCount) {
                case 1 -> "/textures/tekton_1spore.png";
                case 2 -> "/textures/tekton_2spores.png";
                case 3 -> "/textures/tekton_3spores.png";
                default -> new Random().nextInt(2) == 0 ? "/textures/tekton1.png" : "/textures/tekton2.png";
            };

            try (java.io.InputStream inputStream = getClass().getResourceAsStream(imagePath)) {
                if (inputStream != null) {
                    BufferedImage islandImage = ImageIO.read(inputStream);
                    for (Tile tile : tiles) {
                        tile.image = islandImage;
                    }
                } else {
                    System.err.println("Failed to load image: " + imagePath);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Elhelyezi az első entitást a szigeten.
     *
     * @param playerIndex A játékos indexe
     * @param gamePanel A GamePanel objektum
     *
     * @return Az elhelyezett entitás
     */
    public Entity placeInitialEntity(int playerIndex, GamePanel gamePanel) {
        int centerX = (xOffset + gridSize / 2) * tileSize; // Center of the island, adjusted for entity size
        int centerY = (yOffset + (tiles.length / gridSize) / 2) * tileSize;

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

    /**
     * Visszaadja az X irányú eltolást.
     *
     * @return Az X offset értéke
     */
    public int getXOffset() {
        return xOffset;
    }

    /**
     * Visszaadja az Y irányú eltolást.
     *
     * @return Az Y offset értéke
     */
    public int getYOffset() {
        return yOffset;
    }

    /**
     * Visszaadja a rács szélességét (oszlopok számát).
     *
     * @return A rács szélessége (gridSize)
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Visszaadja az aktuálisan használt csempék tömbjét.
     *
     * @return A tömb, amely a csempéket tartalmazza
     */
    public Tile[] getTiles() {
        return tiles;
    }

    /**
     * Beállítja a csempék tömbjét.
     *
     * @param tiles A tömb, amely az új csempéket tartalmazza
     */
    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    /**
     * Beállítja a rács szélességét (oszlopok számát).
     *
     * @param gridSize Az új rácsszélesség
     */
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Visszaadja egy csempe méretét pixelben.
     *
     * @return A csempe mérete
     */
    public int getTileSize() {
        return tileSize;
    }

    // New methods to calculate actual width and height
    /**
     * Visszaadja a rács oszlopainak számát.
     *
     * @return A rács szélessége (oszlopok száma)
     */
    public int getGridWidth() {
        return gridSize; // Number of columns
    }

    /**
     * Visszaadja a rács sorainak számát.
     *
     * @return A rács magassága (sorok száma)
     */
    public int getGridHeight() {
        return tiles.length / gridSize; // Number of rows
    }

}