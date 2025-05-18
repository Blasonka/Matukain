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
 * @version 1.1
 * @date 2025-05-18
 */
public class RovarEntity extends Entity implements Runnable {
    Rovar rovar;
    GamePanel gp;
    MouseHandler mouseHandler;
    BufferedImage playerImage;
    Thread animThread;
    int currentIsland = 0; // Visszaállítva int-re
    private List<int[]> currentPath;
    private int currentPathIndex = 0;

    public RovarEntity(Rovar r, GamePanel gp, MouseHandler mouseHandler) {
        this.rovar = r;
        this.gp = gp;
        this.mouseHandler = mouseHandler;
        getPlayerImage();
        speed = 5;
    }

    public void startAnimThread() {
        if (animThread == null || !animThread.isAlive()) {
            animThread = new Thread(this);
            animThread.start();
        }
    }

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
        }
    }

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

    public void draw(Graphics2D g) {
        g.drawImage(playerImage, x, y, gp.tileSize, gp.tileSize, null);
    }

    public void getPlayerImage() {
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/rovar.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

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
}