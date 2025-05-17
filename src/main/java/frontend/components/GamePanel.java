package frontend.components;

import backend.jateklogika.gameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
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
    private JPanel actionPanelContainer; // Konténer a gombász és rovarász panelek számára
    private JPanel gameArea;

    public GamePanel(gameLogic logic) {
        this.logic = logic;
        tileM = new TileManager(this, logic);
        mouseHandler = new MouseHandler(this);
        controller = new GameController(logic, this);
        statbar = new Statbar();
        gombaszPanel = new GombaszPanel();
        rovaraszPanel = new RovaraszPanel();

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
            }
        };
        gameArea.setPreferredSize(new Dimension(screenWidth - 150, screenHeight - 50)); // 150px a paneleknek
        add(gameArea, BorderLayout.CENTER);

        // Akciópanelek konténere (függőleges elrendezés)
        actionPanelContainer = new JPanel();
        actionPanelContainer.setLayout(new BoxLayout(actionPanelContainer, BoxLayout.Y_AXIS));
        actionPanelContainer.add(gombaszPanel);
        actionPanelContainer.add(Box.createRigidArea(new Dimension(0, 10))); // Távolság a panelek között
        actionPanelContainer.add(rovaraszPanel);
        add(actionPanelContainer, BorderLayout.EAST);

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.blue);
        setDoubleBuffered(true);

        setFocusable(true);
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