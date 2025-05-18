package frontend.components;

import backend.gomba.Gomba;
import backend.gomba.Gombatest;
import backend.rovar.Rovar;
import backend.tekton.Tekton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static frontend.components.GamePanel.state;

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
     * @var Tekton tekton
     * @brief A Tekton, ami a backend-en kapcsolódik hozzá
     */
    Tekton tekton;
    /**
     * @var GamePanel gp
     * @brief A GamePanel objektum, amely a játék grafikus megjelenítéséért felelős
     */
    GamePanel gp;
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
    private int sporeCount = 0;

    /**
     * TektonComponent osztály konstruktora
     *
     * @param t A tekton, kapcoslódik hozzá
     * @param tiles A szigetet alkotó csempék tömbje
     * @param xOffset A sziget X koordinátájának eltolása
     * @param yOffset A sziget Y koordinátájának eltolása
     * @param gridSize A sziget mérete
     * @param tileSize A csempe mérete
     */
    public TektonComponent(Tekton t, GamePanel panel, Tile[] tiles, int xOffset, int yOffset, int gridSize, int tileSize) {
        this.tekton = t;
        this.gp = panel;
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
    public void handleTileClick(int mouseX, int mouseY, RovarEntity rovarIsland) {
        int relativeX = (mouseX / tileSize) - xOffset;
        int relativeY = (mouseY / tileSize) - yOffset;

        if (relativeX >= 0 && relativeX < gridSize && relativeY >= 0 && relativeY < tiles.length / gridSize) {
            boolean add = true;
            if (rovarIsland != null && sporeCount >= 0 && state == GameState.SPORAEVES) {
                //rovarIsland.getRovar().elfogyaszt(tekton.getSporak().get(0));
                sporeCount--;
                add = false;
            }
            else if (sporeCount <= 2 && state == GameState.SPORANOVESZTES) {
                sporeCount++;
            }

            String imagePath = switch (sporeCount) {
                case 1 -> "/textures/tekton_1spores.png";
                case 2 -> "/textures/tekton_2spores.png";
                case 3 -> "/textures/tekton_3spores.png";
                default -> new Random().nextInt(2) == 0 ? "/textures/tekton1.png" : "/textures/tekton2.png";
            };

            try (java.io.InputStream inputStream = getClass().getResourceAsStream(imagePath)) {
                if (inputStream != null) {
                    BufferedImage islandImage = ImageIO.read(inputStream);
                    Tile keresett = null;
                    try (java.io.InputStream inputStream2 = getClass().getResourceAsStream("/textures/tekton_" + (add ? (sporeCount - 1) : (sporeCount + 1)) + "spores.png");
                    java.io.InputStream inputStream3 = getClass().getResourceAsStream("/textures/tekton_" + sporeCount + "spores.png")) {
                        if (inputStream2 != null) {
                            BufferedImage islandImage2 = ImageIO.read(inputStream2);
                            for (Tile tile : tiles) {
                                if (compareImages(tile.image, islandImage2)) {
                                    keresett = tile;
                                }
                            }
                        } if (inputStream3 != null) {
                            BufferedImage islandImage3 = ImageIO.read(inputStream3);
                            for (Tile tile : tiles) {
                                if (compareImages(tile.image, islandImage3)) {
                                    keresett = tile;
                                }
                            }
                        }
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                    if (keresett != null) {
                        keresett.image = islandImage;
                    } else {
                        tiles[new Random().nextInt(tiles.length)].image = islandImage;
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
     * Metódus képek összehasonlítására a spórák lerakásához
     * Képméretet és pixelenként RGB kódot hasonlít össze
     *
     * @param img1 Egyik kép
     * @param img2 Másik kép
     * @return Igaz, ha a két kép megegyezik és hamis, ha eltérés van közöttük
     */
    public static boolean compareImages(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false;
        }

        for (int x = 0; x < img1.getWidth(); x++) {
            for (int y = 0; y < img1.getHeight(); y++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
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
            Gomba ujGomba = gp.logic.getGombasz(playerIndex).addGomba(new Gomba(gp.logic.getGombaID() + 1));
            ujGomba.addGombatest(new Gombatest(gp.logic.getGombatestID(), tekton));

            int gombaszIndex = playerIndex; // 0: első gombasz, 1: második gombasz
            GombatestEntity gombaEntity = new GombatestEntity(ujGomba, gamePanel, gamePanel.mouseHandler, gombaszIndex, gamePanel.tileM.islands.indexOf(this));
            gombaEntity.x = centerX;
            gombaEntity.y = centerY - 48;
            gombaEntity.state = 0;

            gamePanel.gombatestEntities.add(gombaEntity);
            entity = gombaEntity;
            System.out.println("Placed mushroom at (" + centerX + ", " + centerY + ") for player " + playerIndex);
        } else {
            Rovar ujrovar = gp.logic.getRovarasz(playerIndex).addRovar(new Rovar(tekton, gp.logic.getRovarID()));

            int rovaraszIndex = playerIndex - 2; // 0: első rovarász, 1: második rovarász
            RovarEntity rovarEntity = new RovarEntity(ujrovar, gamePanel, gamePanel.mouseHandler, rovaraszIndex);
            rovarEntity.x = centerX;
            rovarEntity.y = centerY;
            rovarEntity.setCurrentIsland(gamePanel.tileM.islands.indexOf(this)); // Visszaállítva int indexre

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


