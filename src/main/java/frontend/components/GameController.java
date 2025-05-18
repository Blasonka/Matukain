package frontend.components;

import backend.jateklogika.gameLogic;

import javax.swing.*;
import java.awt.*;

public class GameController {
    private gameLogic logic;
    private GamePanel gamePanel;
    private int currentPlayerIndex = 0;
    private boolean selectingRovar = false; // Nyomon követi, hogy rovart vagy fonalat kell kijelölni

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
                JOptionPane.showMessageDialog(gamePanel, "Jelölj ki egy második szigetet!");
            } else if (gamePanel.getSecondSelectedIsland() == null && selectedIslandObj != gamePanel.getFirstSelectedIsland()) {
                gamePanel.setSecondSelectedIsland(selectedIslandObj);
                // A fonal logikáját a handleFonalnoveszt-be helyezzük
                handleFonalnoveszt(); // Frissítve, hogy itt is meghívjuk a logikát
            }
        } else if (gamePanel.state == GameState.FONALELVAGAS) {
            if (selectingRovar) {
                // Keresünk rovart a kijelölt szigeten
                RovarEntity rovar = null;
                for (RovarEntity r : gamePanel.rovarEntities) {
                    if (r.currentIsland == selectedIsland) {
                        rovar = r;
                        break;
                    }
                }
                if (rovar != null) {
                    gamePanel.setSelectedRovar(rovar);
                    selectingRovar = false; // Most már fonalat kell kijelölni
                    JOptionPane.showMessageDialog(gamePanel, "Jelölj ki egy fonalat az elvágáshoz!");
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "Ezen a szigeten nincs rovar!");
                }
            } else if (gamePanel.getSelectedRovar() != null) {
                // Keresünk fonalat a kijelölt sziget közelében
                int rovarIslandIndex = gamePanel.tileM.islands.indexOf(gamePanel.tileM.islands.get(selectedIsland));
                for (int[] thread : gamePanel.threads) {
                    int island1Index = thread[0];
                    int island2Index = thread[1];
                    if ((island1Index == rovarIslandIndex || island2Index == rovarIslandIndex) && gamePanel.getSelectedThread() == null) {
                        gamePanel.setSelectedThread(thread);
                        break;
                    }
                }
                if (gamePanel.getSelectedThread() != null) {
                    // Ellenőrizzük, hogy a fonal egyik vége megegyezik-e a rovar szigetével
                    int rovarIslandIndex2 = gamePanel.tileM.islands.indexOf(gamePanel.tileM.islands.get(gamePanel.getSelectedRovar().currentIsland));
                    int threadIsland1 = gamePanel.getSelectedThread()[0];
                    int threadIsland2 = gamePanel.getSelectedThread()[1];
                    if (threadIsland1 == rovarIslandIndex2 || threadIsland2 == rovarIslandIndex2) {
                        gamePanel.removeThread(threadIsland1, threadIsland2);
                        JOptionPane.showMessageDialog(gamePanel, "Fonal sikeresen elvágva!");
                    } else {
                        JOptionPane.showMessageDialog(gamePanel, "A rovar szigete nem egyezik a fonal egyik végével!");
                    }
                    gamePanel.clearSelections();
                    gamePanel.state = GameState.DEFAULT; // Visszaállítjuk az alapértelmezett állapotot
                    gamePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "Nincs kijelölhető fonal a közelben!");
                }
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
                JOptionPane.showMessageDialog(gamePanel, "Spóranövesztés mód aktiválva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleGombanoveszt() {
        if (gamePanel.state == GameState.GOMBANOVESZTES) {
            if (gamePanel.getStatbar().getActionPoints() >= 1) {
                gamePanel.state = GameState.GOMBANOVESZTES;
                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                JOptionPane.showMessageDialog(gamePanel, "Gombanövesztés mód aktiválva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleFonalnoveszt() {
        if (gamePanel.state == GameState.FONALNOVESZTES) {
            if (gamePanel.getFirstSelectedIsland() == null || gamePanel.getSecondSelectedIsland() == null) {
                JOptionPane.showMessageDialog(gamePanel, "Kérlek, jelölj ki két szigetet!");
            } else if (gamePanel.getFirstSelectedIsland() == gamePanel.getSecondSelectedIsland()) {
                JOptionPane.showMessageDialog(gamePanel, "Kérlek, válassz két különböző szigetet!");
            } else {
                Graphics2D g = (Graphics2D) gamePanel.gameArea.getGraphics();
                if (g != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    gamePanel.drawThreads(g2, gamePanel.getFirstSelectedIsland(), gamePanel.getSecondSelectedIsland());
                    g2.dispose();
                }
                // Hozzáadjuk a fonalat (a két sziget indexeit tároljuk)
                int island1Index = gamePanel.tileM.islands.indexOf(gamePanel.getFirstSelectedIsland());
                int island2Index = gamePanel.tileM.islands.indexOf(gamePanel.getSecondSelectedIsland());
                gamePanel.addThread(island1Index, island2Index);
                gamePanel.state = GameState.DEFAULT; // Visszaállítjuk az alapértelmezett állapotot
                gamePanel.repaint(); // Frissítjük a felületet a fonal rajzolásához
                JOptionPane.showMessageDialog(gamePanel, "Fonal sikeresen létrehozva!");
                gamePanel.clearSelectedIslands(); // Töröljük a kijelöléseket a következő körre
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleMozgatas() {
        if (gamePanel.state == GameState.MOZGATAS) {
            if (gamePanel.getStatbar().getActionPoints() >= 1) {
                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                JOptionPane.showMessageDialog(gamePanel, "Mozgatás mód aktiválva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleSporaeves() {
        if (gamePanel.state == GameState.SPORAEVES) {
            if (gamePanel.getStatbar().getActionPoints() >= 1) {
                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                JOptionPane.showMessageDialog(gamePanel, "Sporaevés mód aktiválva!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleFonalelvagas() {
        if (gamePanel.state == GameState.FONALELVAGAS) {
            System.out.println("1");
            if (gamePanel.getStatbar().getActionPoints() >= 1) {
                System.out.println("2");
                gamePanel.getStatbar().updateActionPoints(gamePanel.getStatbar().getActionPoints() - 1);
                gamePanel.clearSelections(); // Töröljük az előző kijelöléseket
                selectingRovar = true; // Először rovart kell kijelölni
                JOptionPane.showMessageDialog(gamePanel, "Fonalelvágás mód aktiválva! Jelölj ki egy rovart!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }
}