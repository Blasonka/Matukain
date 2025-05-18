package frontend.components;

import backend.rovar.Rovar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static frontend.components.GamePanel.state;

/**
 * RovarEntity osztály
 *
 * @class RovarEntity
 *
 * @brief A rovar entitásokat reprezentáló osztály
 *
 * @details
 * Osztály a rovarok tárolására
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class RovarEntity extends Entity implements Runnable {
    /**
     * @var Rovar rovar
     * @brief A backend-en található rovar, aminek ez a megfelelője
     */
    Rovar rovar;
    /**
     * @var GamePanel gp
     * @brief A GamePanel objektum, amely a játék grafikus megjelenítéséért felelős
     */
    GamePanel gp;
    /**
     * @var MouseHandler mouseHandler
     * @brief A MouseHandler objektum, amely az egér események kezeléséért felelős
     */
    MouseHandler mouseHandler;
    /**
     * @var BufferedImage playerImage
     * @brief A rovar grafikus megjelenését tároló BufferedImage objektum
     */
    BufferedImage playerImage;
    /**
     * @var Thread animThread
     * @brief A rovar animációs szálát tároló Thread objektum
     */
    Thread animThread;
    /**
     * @var int currentIsland
     * @brief Az aktuális sziget indexét tároló változó
     */
    int currentIsland = 0;
    /**
     * @var List<int[]> currentPath
     * @brief Az aktuális útvonalat tároló lista
     */
    private List<int[]> currentPath;
    /**
     * @var int currentPathIndex
     * @brief Az aktuális útvonal indexét tároló változó
     */
    private int currentPathIndex = 0;
    /**
     * A rovar tulajdonos rovarász indexe (0 = első, 1 = második)
     */
    private int ownerIndex = 0;

    /**
     * RovarEntity osztály konstruktora
     * @param r Rovar
     * @param gp GamePanel
     * @param mouseHandler Egér
     * @param ownerIndex A rovar gazda rovarász indexe (0 = első, 1 = második)
     * @brief Inicializálja a RovarEntity objektumot
     */
    public RovarEntity(Rovar r, GamePanel gp, MouseHandler mouseHandler, int ownerIndex) {
        this.rovar = r;
        this.gp = gp;
        this.mouseHandler = mouseHandler;
        this.ownerIndex = ownerIndex;
        getPlayerImage();
        speed = 5;
    }

    /**
     * @brief elindítja az animációs szálat
     */
    public void startAnimThread() {
        if (animThread == null || !animThread.isAlive()) {
            animThread = new Thread(this);
            animThread.start();
        }
    }

    /**
     * A rovar entitás mozgását vezérlő szál.
     *
     * @details
     * Ha van előre kiszámolt útvonal, akkor azt követi.
     * Ha nincs, akkor közvetlenül mozog a kijelölt célpontra.
     */
    @Override
    public void run() {
        while (animThread != null) {
            if (currentPath != null && !currentPath.isEmpty()) {
                followPath();
            } else {
                moveDirectlyToTarget();
            }

            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A rovar mozgását végzi az előre kiszámolt útvonal mentén.
     */
    private void followPath() {
        if (currentPathIndex < currentPath.size()) {
            int[] targetPoint = currentPath.get(currentPathIndex);
            int targetX = targetPoint[0] * gp.tileSize + gp.tileSize / 2 - 24;
            int targetY = targetPoint[1] * gp.tileSize + gp.tileSize / 2 - 24;

            if (x < targetX) {
                x += Math.min(speed, targetX - x);
            } else if (x > targetX) {
                x -= Math.min(speed, x - targetX);
            }

            if (y < targetY) {
                y += Math.min(speed, targetY - y);
            } else if (y > targetY) {
                y -= Math.min(speed, y - targetY);
            }

            if (Math.abs(x - targetX) < speed && Math.abs(y - targetY) < speed) {
                currentPathIndex++;
            }
        } else {
            currentPath = null;
            currentPathIndex = 0;
        }
    }

    /**
     * A rovar közvetlen mozgatása a célpontra, ha nincs útvonal.
     *
     * @details
     * Csak akkor történik meg, ha a jelenlegi sziget megegyezik a cél szigettel,
     * vagy azok szomszédosak.
     */
    private void moveDirectlyToTarget() {
        if (state != GameState.MOZGATAS) return;
        if (currentIsland == mouseHandler.selectedIsland ||
                gp.tileM.islands.get(currentIsland).szomszedok.contains(mouseHandler.selectedIsland)) {
            if (x < mouseHandler.coordinate.getX()) {
                x += Math.min(speed, mouseHandler.coordinate.getX() - x);
            } else if (x > mouseHandler.coordinate.getX()) {
                x -= Math.min(speed, x - mouseHandler.coordinate.getX());
            }
            if (y < mouseHandler.coordinate.getY()) {
                y += Math.min(speed, mouseHandler.coordinate.getY() - y);
            } else if (y > mouseHandler.coordinate.getY()) {
                y -= Math.min(speed, y - mouseHandler.coordinate.getY());
            } currentIsland = mouseHandler.selectedIsland;
        }
    }

    /**
     * Frissíti a rovar entitás állapotát.
     *
     * @details
     * Ellenőrzi, hogy a rovar mozgása szükséges-e, és ha igen, akkor végrehajtja azt.
     */
    public void update() {
        if (state != GameState.MOZGATAS) return;
        if (mouseHandler.clicked && (mouseHandler.coordinate.getX() != x || mouseHandler.coordinate.getY() != y)) {
            if (currentIsland != mouseHandler.selectedIsland) {
                if (gp.tileM.islands.get(currentIsland).szomszedok.contains(mouseHandler.selectedIsland)) {
                    TektonComponent currentIslandObj = gp.tileM.islands.get(currentIsland);
                    TektonComponent targetIslandObj = gp.tileM.islands.get(mouseHandler.selectedIsland);

                    currentPath = gp.tileM.drawPathAvoidingIslands(gp.getGraphics(), currentIslandObj, targetIslandObj, true);
                    currentPathIndex = 0;

                    gp.tileM.draw(gp.getGraphics());

                    System.out.println("Island changed: " + currentIsland + " -> " + mouseHandler.selectedIsland);
                    currentIsland = mouseHandler.selectedIsland;
                }
            }
        }
    }

    /**
     * Kirajzolja a rovar entitást a megadott Graphics2D objektumra.
     *
     * @param g a Graphics2D objektum, amire rajzolni kell
     */
    public void draw(Graphics2D g) {
        g.drawImage(playerImage, x, y, gp.tileSize, gp.tileSize, null);
    }

    /**
     * Betölti a rovar entitás grafikus megjelenését.
     *
     * @details
     * A kép betöltése a rovar entitás aktuális állapotához tartozik.
     */
    public void getPlayerImage() {
        try {
            if (ownerIndex == 1) {
                playerImage = ImageIO.read(getClass().getResourceAsStream("/rovar2.png"));
            } else {
                playerImage = ImageIO.read(getClass().getResourceAsStream("/rovar.png"));
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    /**
     * A rovar által végzett műveletek meghívásához
     * @return entitás backend-oldali megfelelője
     */
    public Rovar getRovar() {
        return rovar;
    }
}
