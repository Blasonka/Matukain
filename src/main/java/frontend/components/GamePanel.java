package frontend.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import backend.jateklogika.gameLogic;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    int originalTileSize = 16;
    int scale = 3;
    int tileSize = originalTileSize * scale; // 48x48
    int maxScreenCol = 26; // 1248 / 48
    int maxScreenRow = 15; // 720 / 48
    int screenWidth = tileSize * maxScreenCol; // 1248
    int screenHeight = tileSize * maxScreenRow; // 720
    TileManager tileM;
    MouseHandler mouseHandler;
    Thread gameThread;
    gameLogic logic;
    List<GombatestEntity> gombatestEntities = new ArrayList<>();
    List<RovarEntity> rovarEntities = new ArrayList<>();
    int currentPlayerIndex = 0;

    public GamePanel(gameLogic logic) {
        this.logic = logic;
        tileM = new TileManager(this, logic);
        mouseHandler = new MouseHandler(this);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setMinimumSize(new Dimension(screenWidth, screenHeight));
        setMaximumSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.blue);
        setDoubleBuffered(true);
        addMouseListener(mouseHandler);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();


        if (currentPlayerIndex < 4) {
            logic.promptForInitialPlacement(currentPlayerIndex);
        }

        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            try {
                Thread.sleep(1000 / 60); // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (currentPlayerIndex < 4 && mouseHandler.clicked) {
            int selectedIsland = mouseHandler.selectedIsland;

            TektonComponent island = tileM.islands.get(selectedIsland);
            island.placeInitialEntity(currentPlayerIndex, this);


            currentPlayerIndex++;
            mouseHandler.returnFalse();


            if (currentPlayerIndex < 4) {
                logic.promptForInitialPlacement(currentPlayerIndex);
            } else {
                System.out.println("All initial entities placed. Game can proceed.");

                for (RovarEntity rovar : rovarEntities) {
                    rovar.startAnimThread();
                }
            }
        }


        for (RovarEntity rovar : rovarEntities) {
            rovar.update();
        }
        for (GombatestEntity gomba : gombatestEntities) {
            gomba.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (GombatestEntity gomba : gombatestEntities) {
            gomba.draw(g2);
        }
        for (RovarEntity rovar : rovarEntities) {
            rovar.draw(g2);
        }

        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) {
            int index = keyCode - KeyEvent.VK_0;
            if (index < tileM.islands.size() && tileM.islands.get(index).getBreakCount() <= 2) {
                System.out.println("Key " + index + " pressed");
                tileM.islandOszto(tileM.islands.get(index));
            } else {
                System.out.println("No island available at index " + index);
            }
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            System.out.println("Gombatest fejlodott");
            for (GombatestEntity gomba : gombatestEntities) {
                gomba.state = 1;
                gomba.getPlayerImage();
            }
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            System.out.println("Spóra mód váltva");
            for (TektonComponent island : tileM.islands) {
                island.tmpSpora = !island.tmpSpora;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}