package frontend.components.entities;

import backend.rovar.Rovar;
import frontend.components.panels.GamePanel;
import frontend.GameState;
import frontend.components.controllers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static frontend.components.panels.GamePanel.state;

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
 * @version 1.1
 * @date 2025-05-18
 */
public class RovarEntity extends Entity implements Runnable {
    Rovar rovar;
    GamePanel gp;
    MouseHandler mouseHandler;
    BufferedImage playerImage;
    Thread animThread;
    public int currentIsland = 0; // Visszaállítva int-re
    List<int[]> currentPath;
    int currentPathIndex = 0;
    int finalTargetX = -1;
    int finalTargetY = -1;
    /**
     * A rovar tulajdonos rovarász indexe (0 = első, 1 = második)
     */
    int ownerIndex = 0;

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
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = (gp.tileM.islands.get(0).getXOffset() * gp.tileSize + (gp.tileM.islands.get(0).getGridSize() * gp.tileSize) / 2)-24;
        y = (gp.tileM.islands.get(0).getYOffset() * gp.tileSize + (gp.tileM.islands.get(0).getGridSize() * gp.tileSize) / 2)-24;
        mouseHandler.coordinate.x = x;
        mouseHandler.coordinate.y = y;
        speed = 5; // Reduced speed for smoother path following
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
                System.out.println("Following path");
            } else {
                moveDirectlyToTarget();
                System.out.println("Moving directly to target");
            }

            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A rovar mozgását végzi az előre kiszámolt útvonal mentén.
     */
    private void followPath() {
        boolean moved = false;
        if (currentPathIndex < currentPath.size()) {
            int[] targetPoint = currentPath.get(currentPathIndex);
            int targetX = targetPoint[0] * gp.tileSize + gp.tileSize / 2 - 24;
            int targetY = targetPoint[1] * gp.tileSize + gp.tileSize / 2 - 24;

            if (x < targetX) {
                x += Math.min(speed, targetX - x);
                moved = true;
            } else if (x > targetX) {
                x -= Math.min(speed, x - targetX);
                moved = true;
            }

            if (y < targetY) {
                y += Math.min(speed, targetY - y);
                moved = true;
            } else if (y > targetY) {
                y -= Math.min(speed, y - targetY);
                moved = true;
            }

            if (x == targetX && y == targetY) {
                currentPathIndex++;
            }
        } else if (finalTargetX != -1 && finalTargetY != -1 && (x != finalTargetX || y != finalTargetY)) {
            // Move towards the final target (island center) after path
            if (x < finalTargetX) {
                x += Math.min(speed, finalTargetX - x);
                moved = true;
            } else if (x > finalTargetX) {
                x -= Math.min(speed, x - finalTargetX);
                moved = true;
            }
            if (y < finalTargetY) {
                y += Math.min(speed, finalTargetY - y);
                moved = true;
            } else if (y > finalTargetY) {
                y -= Math.min(speed, y - finalTargetY);
                moved = true;
            }
            if (x == finalTargetX && y == finalTargetY) {
                // Arrived at the center, stop animation
                currentPath = null;
                currentPathIndex = 0;
                finalTargetX = -1;
                finalTargetY = -1;
                animThread = null;
            }
        } else if (currentPath != null && currentPath.isEmpty()) {
            // Defensive: if path is empty but not null, clear it
            currentPath = null;
            currentPathIndex = 0;
        }
        if (moved) {
            gp.repaint(); // Minden mozgás után frissítjük a képernyőt
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
            }
            currentIsland = mouseHandler.selectedIsland;
            // Stop the animation thread if the target is reached
            if (Math.abs(x - mouseHandler.coordinate.getX()) < speed && Math.abs(y - mouseHandler.coordinate.getY()) < speed) {
                animThread = null;
            }
        }
    }

    /**
     * Frissíti a rovar entitás állapotát.
     *
     * Csak akkor lépteti a rovart, ha van aktuális útvonal (currentPath).
     * A path-ot a GameController állítja be, az update csak léptet.
     */
    public void update() {
        if (currentPath != null && !currentPath.isEmpty()) {
            followPath();
        }
        // Egyébként nem csinál semmit, a mozgás indítását a GameController végzi
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCurrentIsland(int islandIndex) {
        this.currentIsland = islandIndex;
    }

    // Setter for path to allow external movement control
    public void setPath(List<int[]> path) {
        this.currentPath = path;
        this.currentPathIndex = 0;
        // Set final target to the center of the destination island
        if (gp != null && gp.mouseHandler != null) {
            int islandIdx = gp.mouseHandler.selectedIsland;
            if (islandIdx >= 0 && islandIdx < gp.tileM.islands.size()) {
                TektonComponent targetIsland = gp.tileM.islands.get(islandIdx);
                this.finalTargetX = (targetIsland.getXOffset() + targetIsland.getGridWidth() / 2) * gp.tileSize + gp.tileSize / 2 - 24;
                this.finalTargetY = (targetIsland.getYOffset() + targetIsland.getGridHeight() / 2) * gp.tileSize + gp.tileSize / 2 - 24;
            } else {
                this.finalTargetX = -1;
                this.finalTargetY = -1;
            }
        } else {
            this.finalTargetX = -1;
            this.finalTargetY = -1;
        }
    }

    public int getOwnerIndex() {
        return ownerIndex;
    }

    /**
     * Ellenőrzi, hogy a megadott játékos index a tulajdonos-e.
     */
    public boolean isOwner(int playerIndex) {
        // Rovar ownerIndex: 0/1, playerIndex: 2/3
        return (playerIndex - 2) == ownerIndex;
    }
}
