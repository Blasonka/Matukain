package frontend.components;

import backend.jateklogika.gameLogic;

import javax.swing.*;
import java.awt.*;

public class GameController {
    private gameLogic logic;
    private GamePanel gamePanel;
    private int currentPlayerIndex = 0;
    private boolean selectingRovar = false; // Nyomon követi, hogy rovart vagy fonalat kell kijelölni
    private boolean selectingIsland = false; // Nyomon követi, hogy szigetet kell kijelölni a mozgatáshoz
    private RovarEntity selectedRovar = null; // Kijelölt rovar a mozgatáshoz
    private Integer selectedIslandForGombanoveszt = null; // Szigetkijelölés tárolása gombanövesztéshez
    private boolean initialPlacementPhase = true;
    private int currentRound = 1;
    private final int maxRounds = 10;
    private boolean gameOver = false;

    public GameController(gameLogic logic, GamePanel gamePanel) {
        this.logic = logic;
        this.gamePanel = gamePanel;
        gamePanel.addMouseListener(gamePanel.mouseHandler);
        JOptionPane.showMessageDialog(gamePanel, logic.promptForInitialPlacement(currentPlayerIndex));
    }

    public void handleClick(int selectedIsland, int mouseX, int mouseY) {
        if (initialPlacementPhase) {
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            island.placeInitialEntity(currentPlayerIndex, gamePanel);
            currentPlayerIndex++;
            gamePanel.mouseHandler.returnFalse();

            if (currentPlayerIndex < 4) {
                JOptionPane.showMessageDialog(gamePanel, logic.promptForInitialPlacement(currentPlayerIndex));
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Kezdothet a jatek!");
                initialPlacementPhase = false;
                currentPlayerIndex = 0; // gombasz starts the round
                logic.setPlayerActionPointsByIndex(currentPlayerIndex, 6); // Set first player's AP to 6
                gamePanel.updateActionPanelsForCurrentPlayer(currentPlayerIndex);
                Statbar statbar = gamePanel.getStatbar();
                statbar.updateRound(logic.getKorszamlalo() + 1);
                statbar.updatePlayerRound(logic.getPlayerNameByIndex(currentPlayerIndex));
                statbar.updateCurrentPlayerActionPoints(logic.getPlayerActionPointsByIndex(currentPlayerIndex));
            }
        } else if (gamePanel.state == GameState.FONALNOVESZTES) {
            if (gamePanel.getFirstSelectedIsland() == null) {
                gamePanel.setFirstSelectedIsland(gamePanel.tileM.islands.get(selectedIsland));
                JOptionPane.showMessageDialog(gamePanel, "Jelölj ki egy második szigetet!");
            } else if (gamePanel.getSecondSelectedIsland() == null) {
                TektonComponent secondIsland = gamePanel.tileM.islands.get(selectedIsland);
                if (secondIsland == gamePanel.getFirstSelectedIsland()) {
                    JOptionPane.showMessageDialog(gamePanel, "Kérlek, válassz két különböző szigetet!");
                } else {
                    // --- Block fonalnövesztés if there is a foreign mushroom on either island ---
                    TektonComponent firstIsland = gamePanel.getFirstSelectedIsland();
                    boolean foreignMushroomPresent = false;
                    for (GombatestEntity gomba : gamePanel.gombatestEntities) {
                        int gombaIslandIndex = gamePanel.tileM.islands.indexOf(firstIsland);
                        int gombaX = gomba.x / gamePanel.tileSize - firstIsland.getXOffset();
                        int gombaY = gomba.y / gamePanel.tileSize - firstIsland.getYOffset();
                        if (gombaX >= 0 && gombaX < firstIsland.getGridWidth() &&
                                gombaY >= 0 && gombaY < firstIsland.getGridHeight() &&
                                gomba.getOwnerIndex() != currentPlayerIndex) {
                            foreignMushroomPresent = true;
                            break;
                        }
                    }
                    for (GombatestEntity gomba : gamePanel.gombatestEntities) {
                        int gombaIslandIndex = gamePanel.tileM.islands.indexOf(secondIsland);
                        int gombaX = gomba.x / gamePanel.tileSize - secondIsland.getXOffset();
                        int gombaY = gomba.y / gamePanel.tileSize - secondIsland.getYOffset();
                        if (gombaX >= 0 && gombaX < secondIsland.getGridWidth() &&
                                gombaY >= 0 && gombaY < secondIsland.getGridHeight() &&
                                gomba.getOwnerIndex() != currentPlayerIndex) {
                            foreignMushroomPresent = true;
                            break;
                        }
                    }
                    if (foreignMushroomPresent) {
                        JOptionPane.showMessageDialog(gamePanel, "Csak a saját gombádról indíthatsz fonalat!");
                        gamePanel.clearSelectedIslands();
                        gamePanel.state = GameState.DEFAULT;
                        return;
                    }
                    // --- End block ---

                    gamePanel.setSecondSelectedIsland(secondIsland);
                    handleFonalnoveszt();
                }
            }
        } else if (gamePanel.state == GameState.FONALELVAGAS) {
            if (selectingRovar) {
                RovarEntity rovar = null;
                for (RovarEntity r : gamePanel.rovarEntities) {
                    if (r.currentIsland == selectedIsland && r.getOwnerIndex() + 2 == currentPlayerIndex) {
                        rovar = r;
                        break;
                    }
                }
                if (rovar != null) {
                    gamePanel.setSelectedRovar(rovar);
                    selectingRovar = false; // Most már fonalat kell kijelölni
                    JOptionPane.showMessageDialog(gamePanel, "Jelölj ki egy fonalat az elvágáshoz!");
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "Ezen a szigeten nincs saját rovarod!");
                }
            } else if (gamePanel.getSelectedRovar() != null) {
                int rovarIslandIndex = selectedIsland;
                for (int[] thread : gamePanel.threads) {
                    int island1Index = thread[0];
                    int island2Index = thread[1];
                    if ((island1Index == rovarIslandIndex || island2Index == rovarIslandIndex) && gamePanel.getSelectedThread() == null) {
                        gamePanel.setSelectedThread(thread);
                        break;
                    }
                }
                if (gamePanel.getSelectedThread() != null) {
                    int rovarIslandIndex2 = gamePanel.getSelectedRovar().currentIsland;
                    int threadIsland1 = gamePanel.getSelectedThread()[0];
                    int threadIsland2 = gamePanel.getSelectedThread()[1];
                    if (threadIsland1 == rovarIslandIndex2 || threadIsland2 == rovarIslandIndex2) {
                        gamePanel.removeThread(threadIsland1, threadIsland2);
                        JOptionPane.showMessageDialog(gamePanel, "Fonal sikeresen elvágva!");
                        decreaseActionPointsForCurrentPlayer();
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
        } else if (gamePanel.state == GameState.MOZGATAS) {
            if (selectingRovar) {
                RovarEntity rovar = null;
                for (RovarEntity r : gamePanel.rovarEntities) {
                    if (r.currentIsland == selectedIsland && r.getOwnerIndex() + 2 == currentPlayerIndex) {
                        rovar = r;
                        break;
                    }
                }
                if (rovar != null) {
                    selectedRovar = rovar;
                    selectingRovar = false;
                    selectingIsland = true;
                    JOptionPane.showMessageDialog(gamePanel, "Jelölj ki egy szigetet a mozgatáshoz!");
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "Ezen a szigeten nincs saját rovarod!");
                }
            } else if (selectingIsland && selectedRovar != null) {
                int currentIslandIndex = selectedRovar.currentIsland;
                int targetIslandIndex = selectedIsland;
                if (currentIslandIndex == targetIslandIndex) {
                    JOptionPane.showMessageDialog(gamePanel, "A rovar és a kijelölt sziget ugyanaz!");
                } else {
                    boolean hasThread = false;
                    for (int[] thread : gamePanel.threads) {
                        int island1Index = thread[0];
                        int island2Index = thread[1];
                        if ((island1Index == currentIslandIndex && island2Index == targetIslandIndex) ||
                                (island1Index == targetIslandIndex && island2Index == currentIslandIndex)) {
                            hasThread = true;
                            break;
                        }
                    }
                    if (hasThread) {
                        TektonComponent currentIslandObj = gamePanel.tileM.islands.get(currentIslandIndex);
                        TektonComponent targetIslandObj = gamePanel.tileM.islands.get(targetIslandIndex);
                        // Calculate path using TileManager
                        java.util.List<int[]> path = gamePanel.tileM.drawPathAvoidingIslands(
                                gamePanel.getGraphics(), currentIslandObj, targetIslandObj, true);
                        if (path != null && !path.isEmpty()) {
                            selectedRovar.setPath(path); // Use path-following instead of direct position set
                            selectedRovar.setCurrentIsland(targetIslandIndex);
                            JOptionPane.showMessageDialog(gamePanel, "Rovar mozgás elindítva!");
                            selectedRovar.startAnimThread(); // Ensure animation thread is started
                            decreaseActionPointsForCurrentPlayer();
                        } else {
                            JOptionPane.showMessageDialog(gamePanel, "Nem található útvonal a szigetek között!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(gamePanel, "Nincs fonal a rovar szigete és a kijelölt sziget között!");
                    }
                }
                selectedRovar = null;
                selectingRovar = false;
                selectingIsland = false;
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
            }
        } else if (gamePanel.state == GameState.GOMBANOVESZTES) {
            // Only allow gomba owner to interact
            int gombaszIndex = currentPlayerIndex;
            if (gombaszIndex > 1) {
                JOptionPane.showMessageDialog(gamePanel, "Csak gombász játékos hajthat végre gombanövesztést!");
                return;
            }
            handleGombanoveszt();
        } else {
            // Only allow gomba/gombatest interaction for owner
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            RovarEntity rovarIsland = null;
            for (RovarEntity rovar : gamePanel.rovarEntities) {
                if (rovar.currentIsland == selectedIsland && rovar.getOwnerIndex() + 2 == currentPlayerIndex) {
                    rovarIsland = rovar;
                    break;
                }
            }
            // Block interaction if there is a mushroom on this island not owned by the current player
            boolean foreignMushroomPresent = false;
            for (GombatestEntity gomba : gamePanel.gombatestEntities) {
                int gombaIslandIndex = gamePanel.tileM.islands.indexOf(island);
                int gombaX = gomba.x / gamePanel.tileSize - island.getXOffset();
                int gombaY = gomba.y / gamePanel.tileSize - island.getYOffset();
                if (gombaX >= 0 && gombaX < island.getGridWidth() &&
                        gombaY >= 0 && gombaY < island.getGridHeight() &&
                        gomba.getOwnerIndex() != currentPlayerIndex) {
                    foreignMushroomPresent = true;
                    break;
                }
            }
            if (foreignMushroomPresent) {
                JOptionPane.showMessageDialog(gamePanel, "Csak a saját gombádat tudod kiválasztani ezen a szigeten!");
                return;
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

    public void endPlayerTurn() {
        if (gameOver) return;
        currentPlayerIndex++;
        if (currentPlayerIndex >= 4) {
            currentPlayerIndex = 0;
            currentRound++;
            logic.setKorszamlalo(currentRound - 1); // backend round update
            if (currentRound > maxRounds) {
                gameOver = true;
                JOptionPane.showMessageDialog(gamePanel, "A játék véget ért!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        logic.resetPlayerActionPoints(currentPlayerIndex); // backend: reset AP for new player
        Statbar statbar = gamePanel.getStatbar();
        statbar.updateRound(currentRound);
        statbar.updatePlayerRound(logic.getPlayerNameByIndex(currentPlayerIndex));
        statbar.updateCurrentPlayerActionPoints(logic.getPlayerActionPointsByIndex(currentPlayerIndex));
        gamePanel.updateActionPanelsForCurrentPlayer(currentPlayerIndex);
    }

    // Call this after every action that spends action points
    public void checkAndAdvanceTurn() {
        int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
        if (ap <= 0) {
            JOptionPane.showMessageDialog(gamePanel, "Elfogytak az akciópontjaid! Következő játékos jön.");
            endPlayerTurn();
        } else {
            gamePanel.getStatbar().updateCurrentPlayerActionPoints(ap);
        }
    }

    // Helper to decrease action points by 2 after every action
    public void decreaseActionPointsForCurrentPlayer() {
        int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
        logic.setPlayerActionPointsByIndex(currentPlayerIndex, ap - 2);
        gamePanel.getStatbar().updateCurrentPlayerActionPoints(ap - 2);
    }

    // Gomb eseménykezelők a GameState alapján
    public void handleSporanoveszt() {
        if (gameOver) return;
        if (gamePanel.state == GameState.SPORANOVESZTES) {
            int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
            if (ap >= 2) {
                JOptionPane.showMessageDialog(gamePanel, "Spóranövesztés mód aktiválva!");
                checkAndAdvanceTurn();
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleGombanoveszt() {
        if (gameOver) return;
         if (gamePanel.state == GameState.GOMBANOVESZTES) {
             JOptionPane.showMessageDialog(gamePanel, "Válassz ki egy szigetet");
            int selectedIsland = gamePanel.mouseHandler.selectedIsland;
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);

            // Ellenőrizzük, hogy van-e már gomba a szigeten
            boolean hasMushroom = false;
            for (GombatestEntity gomba : gamePanel.gombatestEntities) {
                int gombaIslandIndex = gamePanel.tileM.islands.indexOf(island);
                int gombaX = gomba.x / gamePanel.tileSize - island.getXOffset();
                int gombaY = gomba.y / gamePanel.tileSize - island.getYOffset();
                if (gombaX >= 0 && gombaX < island.getGridWidth() &&
                        gombaY >= 0 && gombaY < island.getGridHeight() &&
                        gombaIslandIndex == selectedIsland) {
                    hasMushroom = true;
                    break;
                }
            }

            if (hasMushroom) {
                JOptionPane.showMessageDialog(gamePanel, "Ezen a szigeten már van gomba!");
                gamePanel.state = GameState.DEFAULT;
                return;
            }

            // Ellenőrizzük, hogy van-e 3 spóra a szigeten
            int sporeCount = 0;
            try {
                java.lang.reflect.Field sporeCountField = TektonComponent.class.getDeclaredField("sporeCount");
                sporeCountField.setAccessible(true);
                sporeCount = (int) sporeCountField.get(island);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(gamePanel, "Hiba történt a spórák ellenőrzése közben!");
                gamePanel.state = GameState.DEFAULT;
                return;
            }

            if (sporeCount < 3) {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég spóra a szigeten! Legalább 3 spóra szükséges.");
                gamePanel.state = GameState.DEFAULT;
                return;
            }

            // Spórák eltávolítása
            try {
                java.lang.reflect.Field sporeCountField = TektonComponent.class.getDeclaredField("sporeCount");
                sporeCountField.setAccessible(true);
                sporeCountField.set(island, 0);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(gamePanel, "Hiba történt a spórák eltávolítása közben!");
                gamePanel.state = GameState.DEFAULT;
                return;
            }

            // Sziget csempéinek visszaállítása alapállapotra (0 spóra)
            try {
                java.io.InputStream inputStream = getClass().getResourceAsStream("/textures/tekton1.png");
                if (inputStream == null) {
                    inputStream = getClass().getResourceAsStream("/textures/tekton2.png");
                }
                if (inputStream != null) {
                    java.awt.image.BufferedImage islandImage = javax.imageio.ImageIO.read(inputStream);
                    for (Tile tile : island.getTiles()) {
                        if (tile != null) {
                            tile.image = islandImage;
                        }
                    }
                } else {
                    System.err.println("Failed to load default tekton image for resetting spores!");
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(gamePanel, "Hiba történt a sziget visszaállítása közben!");
                gamePanel.state = GameState.DEFAULT;
                return;
            }

            // GombatestEntity létrehozása és elhelyezése
            int centerX = (island.getXOffset() + island.getGridWidth() / 2) * gamePanel.tileSize;
            int centerY = (island.getYOffset() + island.getGridHeight() / 2) * gamePanel.tileSize;

            backend.gomba.Gomba ujGomba = gamePanel.logic.getGombasz(0).addGomba(new backend.gomba.Gomba(gamePanel.logic.getGombaID() + 1));
            ujGomba.addGombatest(new backend.gomba.Gombatest(gamePanel.logic.getGombatestID(), island.tekton));

            GombatestEntity gombaEntity = new GombatestEntity(ujGomba, gamePanel, gamePanel.mouseHandler, 0);
            gombaEntity.x = centerX;
            gombaEntity.y = centerY - 48; // Az eredeti elhelyezés szerint -48 a gombák Y pozíciója
            gombaEntity.state = 0;

            gamePanel.gombatestEntities.add(gombaEntity);
            decreaseActionPointsForCurrentPlayer();
            JOptionPane.showMessageDialog(gamePanel, "Gomba sikeresen növesztve a szigeten!");
            gamePanel.state = GameState.DEFAULT;
            gamePanel.repaint();
            checkAndAdvanceTurn();
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleFonalnoveszt() {
        if (gameOver) return;
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
                int island1Index = gamePanel.tileM.islands.indexOf(gamePanel.getFirstSelectedIsland());
                int island2Index = gamePanel.tileM.islands.indexOf(gamePanel.getSecondSelectedIsland());
                gamePanel.addThread(island1Index, island2Index);
                decreaseActionPointsForCurrentPlayer();
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
                JOptionPane.showMessageDialog(gamePanel, "Fonal sikeresen létrehozva!");
                gamePanel.clearSelectedIslands();
                checkAndAdvanceTurn();
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleMozgatas() {
        if (gameOver) return;
        if (gamePanel.state == GameState.MOZGATAS) {
            int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
            if (ap >= 2) {
                gamePanel.getStatbar().updateCurrentPlayerActionPoints(ap - 2);
                gamePanel.clearSelections();
                selectingRovar = true;
                selectingIsland = false;
                selectedRovar = null;
                JOptionPane.showMessageDialog(gamePanel, "Mozgatás mód aktiválva! Jelölj ki egy rovart!");
                checkAndAdvanceTurn();
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleSporaeves() {
        if (gameOver) return;
        if (gamePanel.state == GameState.SPORAEVES) {
            int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
            if (ap >= 2) {
                gamePanel.getStatbar().updateCurrentPlayerActionPoints(ap - 2);
                JOptionPane.showMessageDialog(gamePanel, "Sporaevés mód aktiválva!");
                checkAndAdvanceTurn();
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleFonalelvagas() {
        if (gameOver) return;
        if (gamePanel.state == GameState.FONALELVAGAS) {
            int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
            if (ap >= 2) {
                gamePanel.getStatbar().updateCurrentPlayerActionPoints(ap - 2);
                gamePanel.clearSelections();
                selectingRovar = true;
                JOptionPane.showMessageDialog(gamePanel, "Fonalelvágás mód aktiválva! Jelölj ki egy rovart!");
                checkAndAdvanceTurn();
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }
}
