package frontend.components;

import backend.jateklogika.gameLogic;

public class GameController {
    private gameLogic logic;
    private GamePanel gamePanel;
    private int currentPlayerIndex = 0;

    public GameController(gameLogic logic, GamePanel gamePanel) {
        this.logic = logic;
        this.gamePanel = gamePanel;
        gamePanel.addMouseListener(gamePanel.mouseHandler);
        logic.promptForInitialPlacement(currentPlayerIndex);
    }

    // Getter és setter metódusok, ha szükséges
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int index) {
        this.currentPlayerIndex = index;
    }



    // Eseménykezelő metódusok
    public void handleClick(int selectedIsland, int mouseX, int mouseY) {
        if (currentPlayerIndex < 4) {
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            island.placeInitialEntity(currentPlayerIndex, gamePanel);
            currentPlayerIndex++;
            gamePanel.mouseHandler.returnFalse();

            if (currentPlayerIndex < 4) {
                logic.promptForInitialPlacement(currentPlayerIndex);
            } else {
                System.out.println("Kezdőthet a játék!");
                for (RovarEntity rovar : gamePanel.rovarEntities) {
                    rovar.startAnimThread();
                }
                // Statbar inicializálása a játék kezdetén
                Statbar statbar = gamePanel.getStatbar();
                statbar.updateRound(1); // Első kör
                statbar.updatePlayerRound(1); // Első játékos kör
                statbar.updateActionPoints(10); // Kezdeti akciópontok (pl. 10)
            }
        } else {
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            island.handleTileClick(mouseX, mouseY, true);
        }
        gamePanel.repaint();
    }


    public void update() {
        if (gamePanel.mouseHandler.clicked) {
            handleClick(gamePanel.mouseHandler.selectedIsland, 0, 0); // mouseX, mouseY itt redundáns lehet, ha csak az island kell
        }
        for (RovarEntity rovar : gamePanel.rovarEntities) {
            rovar.update();
        }
        for (GombatestEntity gomba : gamePanel.gombatestEntities) {
            gomba.update();
        }
    }
}