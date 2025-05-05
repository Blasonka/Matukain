package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RovarEntity extends Entity implements Runnable {
    GamePanel gp;
    MouseHandler mouseHandler;
    Coordinate defaultCoordinate;
    BufferedImage playerImage;
    Thread animThread;
    //Rovar rovar;

    public RovarEntity(GamePanel gp, MouseHandler mouseHandler) {
        this.gp = gp;
        this.mouseHandler = mouseHandler;
        setDefaultValues();
        defaultCoordinate = mouseHandler.coordinate;
        getPlayerImage();
    }
    public void setDefaultValues() {
        x = 0;
        y = 0;
        speed = 3;
    }
    public void startAnimThread(){
        animThread = new Thread(this);
        animThread.start();
    }

    @Override
    public void run() {
        while (animThread != null) {
            if (x<mouseHandler.coordinate.getX()) {
                x +=1;
            } else if (x>mouseHandler.coordinate.getX()) {
                x -= 1;
            }
            if (y<mouseHandler.coordinate.getY()) {
                y +=1;
            } else if (y>mouseHandler.coordinate.getY()) {
                y -= 1;
            }
            if (x==mouseHandler.coordinate.getX() && y==mouseHandler.coordinate.getY()) {
                animThread = null;
            }
            try {
                Thread.sleep(1000 / 60); // 24 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (mouseHandler.coordinate.getX()!=x || mouseHandler.coordinate.getY()!=y) {
            startAnimThread();
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
