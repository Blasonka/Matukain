package frontend.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import backend.jateklogika.gameLogic;

/**
 * @class GamePanel
 * @brief A játék fő megjelenítő panelje, amely kezeli a kirajzolást, eseményeket és frissítéseket.
 *
 * @details
 * A GamePanel felelős a játék grafikus megjelenítéséért, a billentyűzet- és egéresemények kezeléséért,
 * az entitások frissítéséért és animációjáért.
 *
 * @note A játék fő komponense, amely JPanel-ből származik.
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    /**
     * @var int originalTileSize
     * @brief Az eredeti csempe mérete (16x16)
     */
    int originalTileSize = 16;
    /**
     * @var int scale
     * @brief A csempe méretének skálázása (3x)
     */
    int scale = 3;
    /**
     * @var int tileSize
     * @brief A csempe mérete a skálázás után (48x48)
     */
    int tileSize = originalTileSize * scale; // 48x48
    /**
     * @var int maxScreenCol
     * @brief A maximális oszlopok száma a képernyőn (26)
     */
    int maxScreenCol = 26; // 1248 / 48
    /**
     * @var int maxScreenRow
     * @brief A maximális sorok száma a képernyőn (15)
     */
    int maxScreenRow = 15; // 720 / 48
    /**
     * @var int screenWidth
     * @brief A képernyő szélessége (1248)
     */
    int screenWidth = tileSize * maxScreenCol; // 1248
    /**
     * @var int screenHeight
     * @brief A képernyő magassága (720)
     */
    int screenHeight = tileSize * maxScreenRow; // 720
    /**
     * @var TileManager tileM
     * @brief A csempekezelő objektum
     */
    TileManager tileM;
    /**
     * @var MouseHandler mouseHandler
     * @brief Az egérkezelő objektum
     */
    MouseHandler mouseHandler;
    /**
     * @var Thread gameThread
     * @brief A játék szál
     */
    Thread gameThread;
    /**
     * @var gameLogic logic
     * @brief A játék logikáját kezelő objektum
     */
    gameLogic logic;
    /**
     * @var List<GombatestEntity> gombatestEntities
     * @brief A gombatest entitások listája
     */
    List<GombatestEntity> gombatestEntities = new ArrayList<>();
    /**
     * @var List<RovarEntity> rovarEntities
     * @brief A rovar entitások listája
     */
    List<RovarEntity> rovarEntities = new ArrayList<>();
    /**
     * @var int currentPlayerIndex
     * @brief Az aktuális játékos indexe
     */
    int currentPlayerIndex = 0;

    GameController controller;

    Statbar statbar;
    /**
     * @brief A GamePanel konstruktora
     * @param logic A játék logikáját kezelő objektum
     */
    public GamePanel(gameLogic logic) {
        this.logic = logic;
        tileM = new TileManager(this, logic);
        mouseHandler = new MouseHandler(this);
        controller = new GameController(logic, this);
        statbar = new Statbar(); // Statbar inicializálása

        setLayout(new BorderLayout()); // BorderLayout használata
        add(statbar, BorderLayout.NORTH); // Statbar a tetejére
        add(new JPanel(), BorderLayout.CENTER);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.blue);
        setDoubleBuffered(true);
        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * @brief A játék szálának indítása
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * @brief A játék fő ciklusa. 60 FPS frissítési ciklussal fut.
     */
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
    /**
     * Frissíti a játék logikáját.
     * @details Kezeli az entitások kezdeti elhelyezését és animációját.
     */
    public void update() {
        controller.update();
    }

    /**
     * @brief Kirajzolja a játék elemeit.
     * @param g A Graphics objektum, amire rajzolni kell
     */
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

    /**
     * Kezeli a lenyomott billentyűket.
     * @param e A billentyűesemény
     */
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
                //island.tmpSpora = !island.tmpSpora;
            }
        }
    }

    /**
     * Kezeli a billentyűfelengedést.
     * @param e A billentyűesemény
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Legépelt karakter kezelése.
     * @param e A billentyűesemény
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    public Statbar getStatbar() {
        return  statbar;
    }
}