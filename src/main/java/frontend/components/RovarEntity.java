package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class RovarEntity extends Entity implements Runnable {
    GamePanel gp;
    MouseHandler mouseHandler;
    Coordinate defaultCoordinate;
    BufferedImage playerImage;
    Thread animThread;
    int currentIsland = 0;
    //Rovar rovar;

    public RovarEntity(GamePanel gp, MouseHandler mouseHandler) {
        this.gp = gp;
        this.mouseHandler = mouseHandler;
        setDefaultValues();
        defaultCoordinate = mouseHandler.coordinate;
        getPlayerImage();
    }
    public void setDefaultValues() {
        /*Random random = new Random();
        int r = random.nextInt(0, gp.tileM.islands.size());>*/
        x = (gp.tileM.islands.get(0).getXOffset() * gp.tileSize + (gp.tileM.islands.get(0).getGridSize() * gp.tileSize) / 2)-24;
        y = (gp.tileM.islands.get(0).getYOffset() * gp.tileSize + (gp.tileM.islands.get(0).getGridSize() * gp.tileSize) / 2)-24;
        mouseHandler.coordinate.x=x;
        mouseHandler.coordinate.y=y;
        speed = 15;
    }
    public void startAnimThread(){
        animThread = new Thread(this);
        animThread.start();
    }

    @Override
    public void run() {
        while (animThread != null) {
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
            if (x == mouseHandler.coordinate.getX() && y == mouseHandler.coordinate.getY()) {
                animThread = null;
            }
            try {
                Thread.sleep(1000/60); // 24 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (mouseHandler.coordinate.getX() != x || mouseHandler.coordinate.getY() != y) {
            if (currentIsland != mouseHandler.selectedIsland) {
                if (currentIsland != mouseHandler.selectedIsland &&
                        gp.tileM.islands.get(currentIsland).szomszedok.contains(mouseHandler.selectedIsland)) {

                    System.out.println("Island changed: " + currentIsland + " -> " + mouseHandler.selectedIsland);
                    currentIsland = mouseHandler.selectedIsland;
                    startAnimThread();
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        //g.setColor(Color.red);
        //g.fillRect(x, y, gp.tileSize, gp.tileSize);
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
