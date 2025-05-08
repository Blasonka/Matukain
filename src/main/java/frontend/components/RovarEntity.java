package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.*;

public class RovarEntity extends Entity implements Runnable {
    GamePanel gp;
    MouseHandler mouseHandler;
    Coordinate defaultCoordinate;
    BufferedImage playerImage;
    Thread animThread;
    int currentIsland = 0;
    private List<int[]> currentPath;
    private int currentPathIndex = 0;
    //Rovar rovar;

    public RovarEntity(GamePanel gp, MouseHandler mouseHandler) {
        this.gp = gp;
        this.mouseHandler = mouseHandler;
        setDefaultValues();
        defaultCoordinate = mouseHandler.coordinate;
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = (gp.tileM.islands.get(0).getXOffset() * gp.tileSize + (gp.tileM.islands.get(0).getGridSize() * gp.tileSize) / 2)-24;
        y = (gp.tileM.islands.get(0).getYOffset() * gp.tileSize + (gp.tileM.islands.get(0).getGridSize() * gp.tileSize) / 2)-24;
        mouseHandler.coordinate.x = x;
        mouseHandler.coordinate.y = y;
        speed = 5; // Reduced speed for smoother path following
    }

    public void startAnimThread(){
        if (animThread == null || !animThread.isAlive()) {
            animThread = new Thread(this);
            animThread.start();
        }
    }

    @Override
    public void run() {
        while (animThread != null) {
            if (currentPath != null && !currentPath.isEmpty()) {
                // Follow the path
                followPath();
            } else {
                // Move directly to target if no path is set
                moveDirectlyToTarget();
            }

            try {
                Thread.sleep(1000/60); // 24 FPS
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

            // Move towards the current path point
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

            // Check if we've reached the current path point
            if (Math.abs(x - targetX) < speed && Math.abs(y - targetY) < speed) {
                currentPathIndex++;
            }
        } else {
            // Path completed
            currentPath = null;
            currentPathIndex = 0;
        }
    }

    private void moveDirectlyToTarget() {
        // Check if the current island and selected island are neighbors
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
        }
    }

    public void update() {
        if (mouseHandler.coordinate.getX() != x || mouseHandler.coordinate.getY() != y) {
            if (currentIsland != mouseHandler.selectedIsland) {
                if (currentIsland != mouseHandler.selectedIsland &&
                        gp.tileM.islands.get(currentIsland).szomszedok.contains(mouseHandler.selectedIsland)) {

                    // Get the path between current island and target island
                    TektonComponent currentIslandObj = gp.tileM.islands.get(currentIsland);
                    TektonComponent targetIslandObj = gp.tileM.islands.get(mouseHandler.selectedIsland);

                    // Get the path between these islands
                    currentPath = gp.tileM.drawPathAvoidingIslands(gp.getGraphics(), currentIslandObj, targetIslandObj, true);
                    currentPathIndex = 0;

                    gp.tileM.draw(gp.getGraphics());

                    System.out.println("Island changed: " + currentIsland + " -> " + mouseHandler.selectedIsland);
                    currentIsland = mouseHandler.selectedIsland;
                    startAnimThread();
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(playerImage, x, y, gp.tileSize, gp.tileSize, null);
    }

    public void getPlayerImage() {
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/resources/rovar.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
}