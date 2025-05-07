package frontend.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;


public class GamePanel extends JPanel implements Runnable, KeyListener {
    int originalTileSize = 16;
    int scale = 3;
    int tileSize = originalTileSize * scale; // 48x48
    int maxScreenCol = 26; // 1248 / 48
    int maxScreenRow = 15; // 720 / 48
    int screenWidth = tileSize * maxScreenCol; // 768
    int screenHeight = tileSize * maxScreenRow; // 576
    TileManager tileM = new TileManager(this);

    MouseHandler mouseHandler = new MouseHandler(this);
    Thread gameThread;

    RovarEntity rovarEntity = new RovarEntity(this, mouseHandler);

    public GamePanel(){
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setMinimumSize(new Dimension(screenWidth, screenHeight));
        setMaximumSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.blue);
        setDoubleBuffered(true);
        startGameThread();
        addMouseListener(mouseHandler);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow(); // Ensure the panel has focus
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            try {
                Thread.sleep(1000 / 60); // 24 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update(){
        rovarEntity.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        rovarEntity.draw(g2);

        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            System.out.println("S key pressed");
            if (!tileM.islands.isEmpty()) {
                tileM.islandOszto(tileM.islands.get(0));
            } else {
                System.out.println("No islands available");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No action needed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed
    }
}
