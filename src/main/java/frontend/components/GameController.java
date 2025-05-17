package frontend.components;

import backend.jateklogika.gameLogic;

import javax.swing.*;

public class GameController {
    private gameLogic logic;
    private GamePanel gamePanel;
    private int currentPlayerIndex = 0;

    public GameController(gameLogic logic, GamePanel gamePanel) {
        this.logic = logic;
        this.gamePanel = gamePanel;
        gamePanel.addMouseListener(gamePanel.mouseHandler);
        JOptionPane.showMessageDialog(gamePanel, logic.promptForInitialPlacement(currentPlayerIndex));
    }

    public void handleClick(int selectedIsland, int mouseX, int mouseY) {
        if (currentPlayerIndex < 4) {
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            island.placeInitialEntity(currentPlayerIndex, gamePanel);
            currentPlayerIndex++;
            gamePanel.mouseHandler.returnFalse();

            if (currentPlayerIndex < 4) {
                JOptionPane.showMessageDialog(gamePanel, logic.promptForInitialPlacement(currentPlayerIndex));
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Kezdothet a jatek!");
                for (RovarEntity rovar : gamePanel.rovarEntities) {
                    rovar.startAnimThread();
                }
                // Statbar inicializálása
                Statbar statbar = gamePanel.getStatbar();
                statbar.updateRound(logic.getKorszamlalo());
                statbar.updatePlayerRound(currentPlayerIndex);
                statbar.updateActionPoints(5);

                // Gombász és Rovarász panelek láthatóvá tétele
                gamePanel.getGombaszPanel().setVisible(true);
                gamePanel.getRovaraszPanel().setVisible(true);
            }
        } else {
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            RovarEntity rovarIsland = null;
            for (RovarEntity rovar : gamePanel.rovarEntities) {
                if (rovar.currentIsland == selectedIsland) {
                    rovarIsland = rovar;
                    break;
                }
            } island.handleTileClick(mouseX, mouseY, rovarIsland);
        }
        gamePanel.repaint();
    }

    public void update() {
        if (gamePanel.mouseHandler.clicked) {
            handleClick(gamePanel.mouseHandler.selectedIsland, 0, 0);
        }
        for (RovarEntity rovar : gamePanel.rovarEntities) {
            rovar.update();
        }
        for (GombatestEntity gomba : gamePanel.gombatestEntities) {
            gomba.update();
        }
    }
}
