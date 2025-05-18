package frontend.components;

import backend.jateklogika.gameLogic;

import javax.swing.*;
import java.awt.*;

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
                statbar.updateRound(logic.getKorszamlalo() + 1);
                statbar.updatePlayerRound(currentPlayerIndex);
                statbar.updateActionPoints(5);

                // Gombász és Rovarász panelek láthatóvá tétele
                gamePanel.getGombaszPanel().setVisible(true);
                gamePanel.getRovaraszPanel().setVisible(true);
            }
        } else if (gamePanel.state == GameState.FONALNOVESZTES) {
            TektonComponent selectedIslandObj = gamePanel.tileM.islands.get(selectedIsland);
            if (gamePanel.getFirstSelectedIsland() == null) {
                gamePanel.setFirstSelectedIsland(selectedIslandObj);
                JOptionPane.showMessageDialog(gamePanel, "Jelolj ki egy masodik szigetet!");
            } else if (gamePanel.getSecondSelectedIsland() == null && selectedIslandObj != gamePanel.getFirstSelectedIsland()) {
                gamePanel.setSecondSelectedIsland(selectedIslandObj);
                // A fonal logikáját a handleFonalnoveszt-be helyezzük
                handleFonalnoveszt(); // Frissítve, hogy itt is meghívjuk a logikát
            }
        } else {
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            RovarEntity rovarIsland = null;
            for (RovarEntity rovar : gamePanel.rovarEntities) {
                if (rovar.currentIsland == selectedIsland) {
                    rovarIsland = rovar;
                    break;
                }
            }
            island.handleTileClick(mouseX, mouseY, rovarIsland);
        }
        gamePanel.repaint();
    }

    public void update() {
        if (gamePanel.mouseHandler.clicked) {
            handleClick(gamePanel.mouseHandler.selectedIsland, gamePanel.mouseHandler.coordinate.getX(), gamePanel.mouseHandler.coordinate.getY());
        }
        for (RovarEntity rovar : gamePanel.rovarEntities) {
            rovar.update();
        }
        for (GombatestEntity gomba : gamePanel.gombatestEntities) {
            gomba.update();
        }
    }

    // Gomb eseménykezelők a GameState alapján
    public void handleSporanoveszt() {
        if (gamePanel.state == GameState.SPORANOVESZTES) {
            if (gamePanel.getStatbar().getActionPoints() >= 1) {

                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                JOptionPane.showMessageDialog(gamePanel, "Sporanovesztes mod aktivalva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void handleGombanoveszt() {
        if (gamePanel.state == GameState.GOMBANOVESZTES) {
            if (gamePanel.getStatbar().getActionPoints() >= 1) {
                gamePanel.state = GameState.GOMBANOVESZTES;
                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                JOptionPane.showMessageDialog(gamePanel, "Gombanovesztes mod aktivalva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void handleFonalnoveszt() {
        if (gamePanel.state == GameState.FONALNOVESZTES) {
            if (gamePanel.getFirstSelectedIsland() == null || gamePanel.getSecondSelectedIsland() == null) {
                JOptionPane.showMessageDialog(gamePanel, "Kerlek, jelolj ki ket szigetet!");
            } else if (gamePanel.getFirstSelectedIsland() == gamePanel.getSecondSelectedIsland()) {
                JOptionPane.showMessageDialog(gamePanel, "Kerlek, valassz ket kulonbozo szigetet!");
            } else {
                Graphics2D g = (Graphics2D) gamePanel.gameArea.getGraphics();
                if (g != null) {
                    Graphics2D  g2= (Graphics2D) g;
                    gamePanel.drawThreads(g2, gamePanel.getFirstSelectedIsland(), gamePanel.getSecondSelectedIsland());
                    g2.dispose();
                }
                // Hozzáadjuk a fonalat (a két sziget indexeit tároljuk)
                int island1Index = gamePanel.tileM.islands.indexOf(gamePanel.getFirstSelectedIsland());
                int island2Index = gamePanel.tileM.islands.indexOf(gamePanel.getSecondSelectedIsland());
                gamePanel.addThread(island1Index, island2Index);
                gamePanel.state = GameState.DEFAULT; // Visszaállítjuk az alapértelmezett állapotot
                gamePanel.repaint(); // Frissítjük a felületet a fonal rajzolásához
                JOptionPane.showMessageDialog(gamePanel, "Fonal sikeresen letrehozva!");
                gamePanel.clearSelectedIslands(); // Töröljük a kijelöléseket a következő körre
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void handleMozgatas() {
        if (gamePanel.state == GameState.MOZGATAS) {
            if (gamePanel.getStatbar().getActionPoints() >= 1) {

                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                JOptionPane.showMessageDialog(gamePanel, "Mozgatas mod aktivalva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void handleSporaeves() {
        if (gamePanel.state == GameState.SPORAEVES) {
            if (gamePanel.getStatbar().getActionPoints() >= 1) {

                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                JOptionPane.showMessageDialog(gamePanel, "Sporaeves mod aktivalva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void handleFonalelvagas() {
        if (gamePanel.state == null || gamePanel.state == GameState.FONALELVAGAS) {
            if (gamePanel.getStatbar().getActionPoints() >= 1) {

                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                JOptionPane.showMessageDialog(gamePanel, "Fonalelvagas mod aktivalva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }
}