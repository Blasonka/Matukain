package frontend.components;

import backend.jateklogika.gameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

enum GameState {SPORANOVESZTES, GOMBANOVESZTES, FONALNOVESZTES, MOZGATAS, SPORAEVES, FONALELVAGAS, DEFAULT}

public class GamePanel extends JPanel implements Runnable {
    static GameState state;

    int originalTileSize = 16;
    int scale = 3;
    int tileSize = originalTileSize * scale; // 48x48
    int maxScreenCol = 26; // 1248 / 48
    int maxScreenRow = 15; // 720 / 48
    int screenWidth = tileSize * maxScreenCol; // 1248
    int screenHeight = tileSize * maxScreenRow + 50; // 720 + 50 (Statbar magasság)
    TileManager tileM;
    MouseHandler mouseHandler;
    Thread gameThread;
    gameLogic logic;
    List<GombatestEntity> gombatestEntities = new ArrayList<>();
    List<RovarEntity> rovarEntities = new ArrayList<>();
    public int currentPlayerIndex = 0;
    GameController controller;
    private Statbar statbar;
    private GombaszPanel gombaszPanel;
    private RovaraszPanel rovaraszPanel;
    private JPanel actionPanelContainer;
    JPanel gameArea;

    // Tárolja a kijelölt szigeteket a fonalnövesztéshez
    private TektonComponent firstSelectedIsland = null;
    private TektonComponent secondSelectedIsland = null;
    // Tárolja a fonalakat (párokat a szigetek indexeivel)
    private List<int[]> threads = new ArrayList<>();

    public GamePanel(gameLogic logic) {
        this.logic = logic;
        tileM = new TileManager(this, logic);
        mouseHandler = new MouseHandler(this);
        controller = new GameController(logic, this);
        statbar = new Statbar();
        gombaszPanel = new GombaszPanel();
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
                tileM.draw(g2);
                for (GombatestEntity gomba : gombatestEntities) {
                    gomba.draw(g2);
                }
                for (RovarEntity rovar : rovarEntities) {
                    rovar.draw(g2);
                }
                // Fonalak rajzolása a threads listából
                drawAllThreads(g2);
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

    // Publikus metódus egy adott szigetpár fonalának rajzolására
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

    // Privát metódus az összes fonal rajzolására a threads listából
    private void drawAllThreads(Graphics2D g2) {
        for (int[] thread : threads) {
            int island1Index = thread[0];
            int island2Index = thread[1];
            TektonComponent island1 = tileM.islands.get(island1Index);
            TektonComponent island2 = tileM.islands.get(island2Index);
            drawThreads(g2, island1, island2);
        }
    }

    // Getter és setter metódusok a kijelölt szigetekhez
    public TektonComponent getFirstSelectedIsland() {
        return firstSelectedIsland;
    }

    public void setFirstSelectedIsland(TektonComponent island) {
        this.firstSelectedIsland = island;
    }

    public TektonComponent getSecondSelectedIsland() {
        return secondSelectedIsland;
    }

    public void setSecondSelectedIsland(TektonComponent island) {
        this.secondSelectedIsland = island;
    }

    public void clearSelectedIslands() {
        this.firstSelectedIsland = null;
        this.secondSelectedIsland = null;
    }

    public void addThread(int island1Index, int island2Index) {
        threads.add(new int[]{island1Index, island2Index});
    }

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

    public void update() {
        controller.update();
        gameArea.repaint();
    }

    public Statbar getStatbar() {
        return statbar;
    }

    public GombaszPanel getGombaszPanel() {
        return gombaszPanel;
    }

    public RovaraszPanel getRovaraszPanel() {
        return rovaraszPanel;
    }
}