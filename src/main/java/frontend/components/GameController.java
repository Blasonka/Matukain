package frontend.components;

import backend.jateklogika.gameLogic;
import backend.tekton.Tekton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GameController {
    private gameLogic logic;
    private GamePanel gamePanel;
    private int currentPlayerIndex = 0;
    private boolean selectingRovar = false; // Nyomon követi, hogy rovart vagy fonalat kell kijelölni
    private boolean selectingIsland = false; // Nyomon követi, hogy szigetet kell kijelölni a mozgatáshoz
    int selectedIsland; // Kijelölt sziget
    private RovarEntity selectedRovar = null; // Kijelölt rovar a mozgatáshoz

    private boolean selectingGomba = false;
    private GombatestEntity selectedGomba = null;

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
        this.selectedIsland = selectedIsland;
        if (initialPlacementPhase) {
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            island.placeInitialEntity(currentPlayerIndex, gamePanel);
            currentPlayerIndex++;
            gamePanel.mouseHandler.returnFalse();

            if (currentPlayerIndex < 4) {
                JOptionPane.showMessageDialog(gamePanel, logic.promptForInitialPlacement(currentPlayerIndex));
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Kezdődhet a játék!");
                initialPlacementPhase = false;
                currentPlayerIndex = 0;
                logic.setPlayerActionPointsByIndex(currentPlayerIndex, 6);
                gamePanel.updateActionPanelsForCurrentPlayer(currentPlayerIndex);
                Statbar statbar = gamePanel.getStatbar();
                statbar.updateRound(logic.getKorszamlalo() + 1);
                statbar.updatePlayerRound(logic.getPlayerNameByIndex(currentPlayerIndex));
                statbar.updateCurrentPlayerActionPoints(logic.getPlayerActionPointsByIndex(currentPlayerIndex));
            }
        } else if (gamePanel.state == GameState.FONALNOVESZTES) {
            handleFonalnoveszt();
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
                    selectingRovar = false;
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
                    gamePanel.state = GameState.DEFAULT;
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
                        java.util.List<int[]> path = gamePanel.tileM.drawPathAvoidingIslands(
                                gamePanel.getGraphics(), currentIslandObj, targetIslandObj, true);
                        if (path != null && !path.isEmpty()) {
                            selectedRovar.setPath(path);
                            selectedRovar.setCurrentIsland(targetIslandIndex);
                            JOptionPane.showMessageDialog(gamePanel, "Rovar mozgás elindítva!");
                            selectedRovar.startAnimThread();
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
            // Csak a gombász játékosok végezhetnek gombanövesztést
            int gombaszIndex = currentPlayerIndex;
            if (gombaszIndex > 1) {
                JOptionPane.showMessageDialog(gamePanel, "Csak gombász játékos hajthat végre gombanövesztést!");
                gamePanel.state = GameState.DEFAULT;
                return;
            }

            // Sziget kiválasztása gombanövesztéshez
            selectedIslandForGombanoveszt = selectedIsland;
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
                selectedIslandForGombanoveszt = null;
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
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
                selectedIslandForGombanoveszt = null;
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
                return;
            }

            if (sporeCount < 3) {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég spóra a szigeten! Legalább 3 spóra szükséges.");
                selectedIslandForGombanoveszt = null;
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
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
                selectedIslandForGombanoveszt = null;
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
                return;
            }

            // Sziget csempéinek visszaállítása alapállapotra
            try {
                InputStream inputStream = getClass().getResourceAsStream("/textures/tekton1.png");
                if (inputStream == null) {
                    inputStream = getClass().getResourceAsStream("/textures/tekton2.png");
                }
                if (inputStream != null) {
                    BufferedImage islandImage = ImageIO.read(inputStream);
                    for (Tile tile : island.getTiles()) {
                        if (tile != null) {
                            tile.image = islandImage;
                        }
                    }
                    inputStream.close();
                } else {
                    System.err.println("Failed to load default tekton image for resetting spores!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(gamePanel, "Hiba történt a sziget visszaállítása közben!");
                selectedIslandForGombanoveszt = null;
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
                return;
            }

            // GombatestEntity létrehozása és elhelyezése
            int centerX = (island.getXOffset() + island.getGridWidth() / 2) * gamePanel.tileSize;
            int centerY = (island.getYOffset() + island.getGridHeight() / 2) * gamePanel.tileSize;

            backend.gomba.Gomba ujGomba = gamePanel.logic.getGombasz(0).addGomba(new backend.gomba.Gomba(gamePanel.logic.getGombaID() + 1));
            ujGomba.addGombatest(new backend.gomba.Gombatest(gamePanel.logic.getGombatestID(), island.tekton));

            GombatestEntity gombaEntity = new GombatestEntity(ujGomba, gamePanel, gamePanel.mouseHandler, 0, gamePanel.tileM.islands.indexOf(island));
            gombaEntity.x = centerX;
            gombaEntity.y = centerY - 48;
            gombaEntity.state = 0;


            gamePanel.gombatestEntities.add(gombaEntity);
            decreaseActionPointsForCurrentPlayer();
            JOptionPane.showMessageDialog(gamePanel, "Gomba sikeresen novesztve a szigeten!");
            selectedIslandForGombanoveszt = null;
            gamePanel.state = GameState.DEFAULT;
            gamePanel.repaint();
            checkAndAdvanceTurn();
        } else if (GamePanel.state == GameState.SPORANOVESZTES) {
            if (selectingGomba) {
                GombatestEntity gomba = null;
                for (GombatestEntity g : gamePanel.gombatestEntities) {
                    if (g.island == selectedIsland && g.getOwnerIndex() == currentPlayerIndex) {
                        gomba = g;
                        break;
                    }
                } if (gomba != null) {
                    selectedGomba = gomba;
                    selectingIsland = true;
                    selectingGomba = false;
                    JOptionPane.showMessageDialog(gamePanel, "Valaszd ki a tektont!");
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "Ezen a tektonon nincs gombad.");
                    selectingGomba = false;
                    GamePanel.state = GameState.DEFAULT;
                }
            } else if (selectingIsland && selectedGomba != null) {
                TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
                RovarEntity rovarIsland = null;
                for (RovarEntity rovar : gamePanel.rovarEntities) {
                    if (rovar.currentIsland == selectedIsland && rovar.getOwnerIndex() + 2 == currentPlayerIndex) {
                        rovarIsland = rovar;
                        break;
                    }
                }
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
                selectingGomba = false;
                selectingIsland = false;
                selectedGomba = null;
                gamePanel.state = GameState.DEFAULT;
                JOptionPane.showMessageDialog(gamePanel, "Spora sikeresen elhelyezve.");
                decreaseActionPointsForCurrentPlayer();
                checkAndAdvanceTurn();
            }
        } else if (gamePanel.state == GameState.SPORAEVES) {
            if (selectingIsland) {
                TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
                RovarEntity rovarIsland = null;
                for (RovarEntity rovar : gamePanel.rovarEntities) {
                    if (rovar.currentIsland == selectedIsland && rovar.getOwnerIndex() + 2 == currentPlayerIndex) {
                        rovarIsland = rovar;
                        break;
                    }
                } if (rovarIsland == null) {
                    JOptionPane.showMessageDialog(gamePanel, "Ezen a tektonon nincs rovarod.");
                    selectingIsland = false;
                    gamePanel.state = GameState.DEFAULT;
                    return;
                }
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
                selectingGomba = false;
                selectingIsland = false;
                selectedGomba = null;
                gamePanel.state = GameState.DEFAULT;
                JOptionPane.showMessageDialog(gamePanel, "Spora sikeresen elfogyasztva.");
                decreaseActionPointsForCurrentPlayer();
                checkAndAdvanceTurn();
            }
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
            // --- 20% eséllyel tekton törés frontend oldalon ---
            java.util.Random rnd = new java.util.Random();
            if (rnd.nextDouble() < 0.5) {
                List<TektonComponent> islands = gamePanel.tileM.islands;
                if (!islands.isEmpty()) {
                    int idx = rnd.nextInt(islands.size());
                    TektonComponent torendo = islands.get(idx);
                    gamePanel.tileM.islandOszto(torendo);
                    //JOptionPane.showMessageDialog(gamePanel, "Egy tekton széttört! (ID: " + torendo.tekton.getID() + ")", "Tekton törés", JOptionPane.WARNING_MESSAGE);
                }
            }
            // --- vége ---
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
                JOptionPane.showMessageDialog(gamePanel, "Valassz ki egy gombat!");
                selectingGomba = true;
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
            int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
            if (ap >= 2) {
                // Csak üzenetet jelenítünk meg, a szigetkijelölés a handleClick-ben történik
                JOptionPane.showMessageDialog(gamePanel, "Válassz ki egy szigetet a gombanövesztéshez!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs elég akciópontod!");
                gamePanel.state = GameState.DEFAULT;
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelő állapotban vagy!");
        }
    }

    public void handleFonalnoveszt() {
        // Ellenőrzés: csak akkor lehessen fonalat növeszteni, ha a két kiválasztott tekton közül legalább az egyiken van már a játékosnak gombatestje vagy fonala
        if (gamePanel.getFirstSelectedIsland() == null) {
            gamePanel.setFirstSelectedIsland(gamePanel.tileM.islands.get(selectedIsland));
            JOptionPane.showMessageDialog(gamePanel, "Jelölj ki egy második szigetet!");
        } else if (gamePanel.getSecondSelectedIsland() == null) {
            TektonComponent secondIsland = gamePanel.tileM.islands.get(selectedIsland);
            if (secondIsland == gamePanel.getFirstSelectedIsland()) {
                JOptionPane.showMessageDialog(gamePanel, "Kérlek, válassz két különböző szigetet!");
                return;
            }
            // Ellenőrzés: legalább az egyik tektonon van-e saját gombatest vagy fonal
            TektonComponent firstIsland = gamePanel.getFirstSelectedIsland();
            TektonComponent[] islands = new TektonComponent[] { firstIsland, secondIsland };
            boolean found = false;
            backend.felhasznalo.Gombasz gombasz = (backend.felhasznalo.Gombasz) logic.getPlayerByIndex(currentPlayerIndex);
            for (TektonComponent island : islands) {
                int tektonId = island.tekton.getID();
                // Gombatestek vizsgálata
                for (backend.gomba.Gomba g : gombasz.getGombak()) {
                    for (backend.gomba.Gombatest gt : g.getGombatest()) {
                        if (gt.getTekton().getID() == tektonId) {
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                    // Gombafonalak vizsgálata
                    for (backend.gomba.Gombafonal gf : g.getGombafonalak()) {
                        if (gf.getHatar1().getID() == tektonId || gf.getHatar2().getID() == tektonId) {
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
                if (found) break;
            }
            if (!found) {
                JOptionPane.showMessageDialog(gamePanel, "Csak akkor növeszthetsz fonalat, ha a két kiválasztott tekton közül legalább az egyiken van már gombatested vagy fonalad!");
                gamePanel.clearSelectedIslands();
                gamePanel.state = GameState.DEFAULT;
                return;
            }
            // Ha minden OK, fonal növesztése
            gamePanel.setSecondSelectedIsland(secondIsland);
            Graphics2D g = (Graphics2D) gamePanel.gameArea.getGraphics();
            if (g != null) {
                Graphics2D g2 = (Graphics2D) g;
                gamePanel.drawThreads(g2, firstIsland, secondIsland);
                g2.dispose();
            }
            int island1Index = gamePanel.tileM.islands.indexOf(firstIsland);
            int island2Index = gamePanel.tileM.islands.indexOf(secondIsland);
            gamePanel.addThread(island1Index, island2Index);
            decreaseActionPointsForCurrentPlayer();
            gamePanel.state = GameState.DEFAULT;
            gamePanel.repaint();
            JOptionPane.showMessageDialog(gamePanel, "Fonal sikeresen létrehozva!");
            gamePanel.clearSelectedIslands();
            checkAndAdvanceTurn();
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
                JOptionPane.showMessageDialog(gamePanel, "Valaszd ki a tektont!");
                selectingIsland = true;
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
