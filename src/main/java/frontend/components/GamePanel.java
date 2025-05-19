package frontend.components;

import backend.jateklogika.gameLogic;
import backend.felhasznalo.Gombasz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @enum GameState
 * @brief A játék állapotait reprezentáló enum
 */
enum GameState {SPORANOVESZTES, GOMBANOVESZTES, FONALNOVESZTES, MOZGATAS, SPORAEVES, FONALELVAGAS, DEFAULT}

/**
 * GamePanel osztály
 *
 * @class GamePanel
 *
 * @brief A játék grafikus megjelenítéséért felelős osztály
 *
 * @details
 * Az osztály felelős a játék grafikus megjelenítéséért és a felhasználói interakciók kezeléséért.
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class GamePanel extends JPanel implements Runnable {
    /**
     * @var GameState state
     * @brief A játék állapotát tároló változó
     */
    static GameState state;
    /**
     * @var int originalTileSize
     * @brief Az eredeti csempe mérete
     */
    int originalTileSize = 16;
    /**
     * @var int scale
     * @brief A csempe méretének skálázása
     */
    int scale = 3;
    /**
     * @var int tileSize
     * @brief A csempe mérete a skálázás után
     */
    int tileSize = originalTileSize * scale; // 48x48
    /**
     * @var int maxScreenCol
     * @brief A maximális oszlopok száma a képernyőn
     */
    int maxScreenCol = 26; // 1248 / 48
    /**
     * @var int maxScreenRow
     * @brief A maximális sorok száma a képernyőn
     */
    int maxScreenRow = 15; // 720 / 48
    /**
     * @var int screenWidth
     * @brief A képernyő szélessége
     */
    int screenWidth = tileSize * maxScreenCol; // 1248
    /**
     * @var int screenHeight
     * @brief A képernyő magassága
     */
    int screenHeight = tileSize * maxScreenRow + 50; // 720 + 50 (Statbar magasság)
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
     * @brief A játék szálát reprezentáló Thread objektum
     */
    Thread gameThread;
    /**
     * @var gameLogic logic
     * @brief A játék logikáját kezelő objektum
     */
    gameLogic logic;
    /**
     * @var List<GombatestEntity> gombatestEntities
     * @brief A gombatestek listája
     */
    List<GombatestEntity> gombatestEntities = new ArrayList<>();
    /**
     * @var List<RovarEntity> rovarEntities
     * @brief A rovarok listája
     */
    List<RovarEntity> rovarEntities = new ArrayList<>();
    /**
     * @var int currentPlayerIndex
     * @brief Az aktuális játékos indexe
     */
    public int currentPlayerIndex = 0;
    /**
     * @var GameController controller
     * @brief A játék vezérlője
     */
    GameController controller;
    /**
     * @var Statbar statbar
     * @brief A statisztikai sávot reprezentáló objektum
     */
    private Statbar statbar;
    /**
     * @var GombaszPanel gombaszPanel
     * @brief A GombaszPanel objektum, amely a backend Gombasz objektumot jeleníti meg
     */
    private GombaszPanel gombaszPanel;
    /**
     * @var RovaraszPanel rovaraszPanel
     * @brief A RovaraszPanel objektum, amely a backend Rovarasz objektumot jeleníti meg
     */
    private RovaraszPanel rovaraszPanel;
    /**
     * @var JPanel actionPanelContainer
     * @brief Az akciópanelek konténere
     */
    private JPanel actionPanelContainer;
    /**
     * @var JPanel gameArea
     * @brief A játékterület panelje
     */
    JPanel gameArea;
    /**
     * @var Gombasz gombasz
     * @brief A backend Gombasz objektum
     */
    private Gombasz gombasz;

    /**
     * @var TektonComponent firstSelectedIsland
     * @var TektonComponent secondSelectedIsland
     * @brief Tárolja a kijelölt szigeteket a fonalnövesztéshez
     */
    private TektonComponent firstSelectedIsland = null;
    private TektonComponent secondSelectedIsland = null;
    /**
     * @var List<int[]> threads
     * @brief Tárolja a fonalakat (párokat a szigetek indexeivel)
     */
    List<int[]> threads = new ArrayList<>();

    /**
     * @var RovarEntity selectedRovar
     * @var int[] selectedThread
     * @brief Tárolja a kijelölt rovart és fonalat a fonalelvágáshoz.
     */
    private RovarEntity selectedRovar = null;
    private int[] selectedThread = null;

    /**
     * GamePanel osztály konstruktora
     *
     * @param logic A játék logikáját kezelő objektum
     * @param gombasz A backend Gombasz objektum
     */
    public GamePanel(gameLogic logic, Gombasz gombasz) {
        this.logic = logic;
        this.gombasz = gombasz;
        tileM = new TileManager(this, logic);
        mouseHandler = new MouseHandler(this);
        controller = new GameController(logic, this);
        statbar = new Statbar();
        statbar.setController(controller);
        gombaszPanel = new GombaszPanel(gombasz);
        rovaraszPanel = new RovaraszPanel();

        // Beállítjuk a controllert a panelek számára
        gombaszPanel.setController(controller);
        rovaraszPanel.setController(controller);

        setLayout(new BorderLayout());
        add(statbar, BorderLayout.NORTH);

        // Játékterület panel
        gameArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.BLUE);
                Graphics2D g2 = (Graphics2D) g;
                // Fonalak rajzolása a threads listából
                drawAllThreads(g2);
                tileM.draw(g2);
                for (GombatestEntity gomba : gombatestEntities) {
                    gomba.draw(g2);
                }
                for (RovarEntity rovar : rovarEntities) {
                    rovar.draw(g2);
                }

            }
        };
        gameArea.setPreferredSize(new Dimension(screenWidth - 150, screenHeight - 50)); // 150px a paneleknek
        add(gameArea, BorderLayout.CENTER);

        // Akciópanelek konténere (függőleges elrendezés)
        actionPanelContainer = new JPanel();
        actionPanelContainer.setLayout(new BoxLayout(actionPanelContainer, BoxLayout.Y_AXIS));
        actionPanelContainer.add(gombaszPanel);
        actionPanelContainer.add(rovaraszPanel);
        add(actionPanelContainer, BorderLayout.EAST);

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.blue);
        setDoubleBuffered(true);

        setFocusable(true);
    }

    /**
     * Publikus metódus egy adott szigetpár fonalának rajzolására
     */
    public void drawThreads(Graphics2D g2, TektonComponent island1, TektonComponent island2) {
        // Beállítjuk a rajzolási stílust
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(10));
        g2.setColor(new Color(144, 238, 144));

        // Számítjuk a szigetek középpontjait
        int island1CenterX = (island1.getXOffset() + island1.getGridWidth() / 2) * tileSize + tileSize / 2;
        int island1CenterY = (island1.getYOffset() + island1.getGridHeight() / 2) * tileSize + tileSize / 2;
        int island2CenterX = (island2.getXOffset() + island2.getGridWidth() / 2) * tileSize + tileSize / 2;
        int island2CenterY = (island2.getYOffset() + island2.getGridHeight() / 2) * tileSize + tileSize / 2;

        // Találjuk meg a szigetek szélén lévő pontokat
        int[] startPoint = tileM.findIslandEdgePoint(new boolean[maxScreenRow][maxScreenCol], island1, island2);
        int[] endPoint = tileM.findIslandEdgePoint(new boolean[maxScreenRow][maxScreenCol], island2, island1);

        if (startPoint == null || endPoint == null) {
            System.out.println("Could not find valid edge points for path.");
            g2.setStroke(originalStroke);
            return;
        }

        // Rajzoljuk a vonalat a sziget1 középpontjától a széléig
        int startEdgeX = startPoint[0] * tileSize + tileSize / 2;
        int startEdgeY = startPoint[1] * tileSize + tileSize / 2;
        g2.drawLine(island1CenterX, island1CenterY, startEdgeX, startEdgeY);

        // Keresünk utat a szigetek szélei között a tengeren keresztül
        List<int[]> path = tileM.findPath(new boolean[maxScreenRow][maxScreenCol], startPoint[0], startPoint[1], endPoint[0], endPoint[1]);
        if (path.isEmpty()) {
            System.out.println("No path found between points.");
            g2.setStroke(originalStroke);
            return;
        }

        // Rajzoljuk az utat a tengeren keresztül
        int prevX = startEdgeX;
        int prevY = startEdgeY;
        for (int i = 1; i < path.size(); i++) {
            int currX = path.get(i)[0] * tileSize + tileSize / 2;
            int currY = path.get(i)[1] * tileSize + tileSize / 2;
            g2.drawLine(prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }

        // Rajzoljuk a vonalat a sziget2 szélétől a középpontjáig
        int endEdgeX = endPoint[0] * tileSize + tileSize / 2;
        int endEdgeY = endPoint[1] * tileSize + tileSize / 2;
        g2.drawLine(prevX, prevY, endEdgeX, endEdgeY);
        g2.drawLine(endEdgeX, endEdgeY, island2CenterX, island2CenterY);

        g2.setStroke(originalStroke);
    }

    /**
     * Privát metódus az összes fonal rajzolására a threads listából
     * @param g2 A Graphics2D objektum, amelyre rajzolunk
     */
    private void drawAllThreads(Graphics2D g2) {
        for (int[] thread : threads) {
            int island1Index = thread[0];
            int island2Index = thread[1];
            TektonComponent island1 = tileM.islands.get(island1Index);
            TektonComponent island2 = tileM.islands.get(island2Index);
            drawThreads(g2, island1, island2);
        }
    }
    /**
     * Publikus metódus a szigetpárok eltávolítására
     */
    public void removeThread(int island1Index, int island2Index) {
        threads.removeIf(thread -> (thread[0] == island1Index && thread[1] == island2Index) ||
                (thread[0] == island2Index && thread[1] == island1Index));
        repaint();
    }

    /**
     * @brief Visszaadja az első kijelölt szigetet
     * @return Az első kijelölt sziget
     */
    public TektonComponent getFirstSelectedIsland() {
        return firstSelectedIsland;
    }

    /**
     * @brief Beállítja az első kijelölt szigetet
     * @param island Az első kijelölt sziget
     */
    public void setFirstSelectedIsland(TektonComponent island) {
        this.firstSelectedIsland = island;
    }

    /**
     * @brief Visszaadja a második kijelölt szigetet
     * @return A második kijelölt sziget
     */
    public TektonComponent getSecondSelectedIsland() {
        return secondSelectedIsland;
    }

    /**
     * @brief Beállítja a második kijelölt szigetet
     * @param island A második kijelölt sziget
     */
    public void setSecondSelectedIsland(TektonComponent island) {
        this.secondSelectedIsland = island;
    }

    /**
     * @brief törli a kijelölt szigeteket
     */
    public void clearSelectedIslands() {
        this.firstSelectedIsland = null;
        this.secondSelectedIsland = null;
    }

    /**
     * @brief Visszaadja a kijelölt rovart
     * @return A kijelölt rovar
     */
    public RovarEntity getSelectedRovar() {
        return selectedRovar;
    }

    /**
     * @brief Beállítja a kijelölt rovart
     * @param rovar A kijelölt rovar
     */
    public void setSelectedRovar(RovarEntity rovar) {
        this.selectedRovar = rovar;
    }

    /**
     * @brief Visszaadja a kijelölt fonalat
     * @return A kijelölt fonal
     */
    public int[] getSelectedThread() {
        return selectedThread;
    }

    /**
     * @brief Beállítja a kijelölt fonalat
     * @param thread A kijelölt fonal
     */
    public void setSelectedThread(int[] thread) {
        this.selectedThread = thread;
    }

    /**
     * @brief Törli a kijelölt rovart és fonalat
     */
    public void clearSelections() {
        this.selectedRovar = null;
        this.selectedThread = null;
    }

    /**
     * @brief fonal hozzáadása a fonalak listájához
     */
    public void addThread(int island1Index, int island2Index) {
        threads.add(new int[]{island1Index, island2Index});
    }

    /**
     * Frissíti az akciópanelek láthatóságát az aktuális játékos alapján
     */
    public void updateActionPanelsForCurrentPlayer(int currentPlayerIndex) {
        if (currentPlayerIndex < 2) {
            gombaszPanel.setVisible(true);
            rovaraszPanel.setVisible(false);
        } else {
            gombaszPanel.setVisible(false);
            rovaraszPanel.setVisible(true);
        }
    }

    /**
     * @brief A játék fő futási ciklusa.
     */
    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            try {
                Thread.sleep(1000 / 60); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @brief Frissíti a játék állapotát és újrarajzolja a játékterületet.
     */
    public void update() {
        controller.update();
        gameArea.repaint();
    }

    /**
     * @brief visszaadja a játék állapotát
     * @return A játék állapota
     */
    public Statbar getStatbar() {
        return statbar;
    }

    /**
     * @return A gombasz panelt
     */
    public GombaszPanel getGombaszPanel() {
        return gombaszPanel;
    }

    /**
     * @return A rovarasz panelt
     */
    public RovaraszPanel getRovaraszPanel() {
        return rovaraszPanel;
    }
}
