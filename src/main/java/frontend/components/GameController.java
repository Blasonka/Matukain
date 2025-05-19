package frontend.components;

import backend.gomba.Gombatest;
import backend.interfaces.Jatekos;
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
    private int mouseX;
    private int mouseY;

    public GameController(gameLogic logic, GamePanel gamePanel) {
        this.logic = logic;
        this.gamePanel = gamePanel;
        gamePanel.addMouseListener(gamePanel.mouseHandler);
        JOptionPane.showMessageDialog(gamePanel, logic.promptForInitialPlacement(currentPlayerIndex));
    }

    public void handleClick(int selectedIsland, int mouseX, int mouseY) {
        this.selectedIsland = selectedIsland;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if (initialPlacementPhase) {
            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
            island.placeInitialEntity(currentPlayerIndex, gamePanel);
            currentPlayerIndex++;
            gamePanel.mouseHandler.returnFalse();

            if (currentPlayerIndex < 4) {
                JOptionPane.showMessageDialog(gamePanel, logic.promptForInitialPlacement(currentPlayerIndex));
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Kezdodhet a jatek!");
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
                    JOptionPane.showMessageDialog(gamePanel, "Jelolj ki egy fonalat az elvagashoz!");
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "Ezen a szigeten nincs sajat rovarod!");
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
                        JOptionPane.showMessageDialog(gamePanel, "Fonal sikeresen elvagva!");
                        decreaseActionPointsForCurrentPlayer();
                    } else {
                        JOptionPane.showMessageDialog(gamePanel, "A rovar szigete nem egyezik a fonal egyik vegevel!");
                    }
                    gamePanel.clearSelections();
                    gamePanel.state = GameState.DEFAULT;
                    gamePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "Nincs kijelolheto fonal a kozelben!");
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
                    JOptionPane.showMessageDialog(gamePanel, "Jelolj ki egy szigetet a mozgatashoz!");
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "Ezen a szigeten nincs sajat rovarod!");
                }
            } else if (selectingIsland && selectedRovar != null) {
                int currentIslandIndex = selectedRovar.currentIsland;
                int targetIslandIndex = selectedIsland;
                if (currentIslandIndex == targetIslandIndex) {
                    JOptionPane.showMessageDialog(gamePanel, "A rovar és a kijelolt sziget ugyanaz!");
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
                            JOptionPane.showMessageDialog(gamePanel, "Rovar mozgas elinditva!");
                            selectedRovar.startAnimThread();
                            decreaseActionPointsForCurrentPlayer();
                        } else {
                            JOptionPane.showMessageDialog(gamePanel, "Nem talalhato utvonal a szigetek kozott!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(gamePanel, "Nincs fonal a rovar szigete és a kijelolt sziget kozott!");
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
                JOptionPane.showMessageDialog(gamePanel, "Csak gombasz jatekos hajthat vegre gombanovesztest!");
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
                JOptionPane.showMessageDialog(gamePanel, "Ezen a szigeten mar van gomba!");
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
                JOptionPane.showMessageDialog(gamePanel, "Hiba tortent a sporak ellenorzese kozben!");
                selectedIslandForGombanoveszt = null;
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
                return;
            }

            if (sporeCount < 3) {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg spora a szigeten! Legalább 3 spora szukseges.");
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
                JOptionPane.showMessageDialog(gamePanel, "Hiba tortent a sporak eltavolitasa kozben!");
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
                JOptionPane.showMessageDialog(gamePanel, "Hiba tortent a sziget visszaallitasa kozben!");
                selectedIslandForGombanoveszt = null;
                gamePanel.state = GameState.DEFAULT;
                gamePanel.repaint();
                return;
            }

            // GombatestEntity létrehozása és elhelyezése
            int centerX = (island.getXOffset() + island.getGridWidth() / 2) * gamePanel.tileSize;
            int centerY = (island.getYOffset() + island.getGridHeight() / 2) * gamePanel.tileSize;

            java.util.List<backend.gomba.Gomba> gombak = gamePanel.logic.getGombasz(currentPlayerIndex).getGombak();
            backend.gomba.Gomba utolsoGomba = gombak.get(gombak.size() - 1);
            java.util.List<Gombatest> gombatestek = utolsoGomba.getGombatest();
            int ujGombatestId = gombatestek.isEmpty() ? 1 : gombatestek.get(gombatestek.size() - 1).getID() + 1;
            Gombatest ujGombatest = new Gombatest(ujGombatestId, island.tekton);
            utolsoGomba.addGombatest(ujGombatest);

            int ownerIndex = currentPlayerIndex; // 0: első játékos, 1: második játékos
            GombatestEntity gombaEntity = new GombatestEntity(utolsoGomba, gamePanel, gamePanel.mouseHandler, ownerIndex, gamePanel.tileM.islands.indexOf(island));
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
            handleSporanoveszt();
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
                    JOptionPane.showMessageDialog(gamePanel, "Csak a sajat gombadat tudod kivalasztani ezen a szigeten!");
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
                showGameResults();
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
            JOptionPane.showMessageDialog(gamePanel, "Elfogytak az akciopontjaid! Kovetkezo jatekos jon.");
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
        logic.getPlayerByIndex(currentPlayerIndex).addPontokSzama(2);
    }

    // Gomb eseménykezelők a GameState alapján
    public void handleSporanoveszt() {
        if (gameOver) return;
        if (gamePanel.state == GameState.SPORANOVESZTES) {
            int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
            if (ap >= 2) {
                // 1. Gombatest kiválasztása
                if (selectingGomba) {
                    GombatestEntity gomba = null;
                    for (GombatestEntity g : gamePanel.gombatestEntities) {
                        if (g.getOwnerIndex() == currentPlayerIndex && g.island == selectedIsland) {
                            gomba = g;
                            break;
                        }
                    }
                    if (gomba != null) {
                        selectedGomba = gomba;
                        selectingGomba = false;
                        selectingIsland = true;
                        // Szomszédos tektonok kigyűjtése az új szomszedosTekton metódussal
                        backend.gomba.Gombatest backendGombatest = null;
                        backend.felhasznalo.Gombasz gombasz = (backend.felhasznalo.Gombasz) logic.getPlayerByIndex(currentPlayerIndex);
                        for (backend.gomba.Gomba g : gombasz.getGombak()) {
                            for (backend.gomba.Gombatest gt : g.getGombatest()) {
                                if (gt.getTekton().getID() == gomba.island) {
                                    backendGombatest = gt;
                                    break;
                                }
                            }
                            if (backendGombatest != null) break;
                        }
                        if (backendGombatest != null) {
                            JOptionPane.showMessageDialog(gamePanel, "Valaszd ki, melyik szomszedos tektonra szorod a sporat!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(gamePanel, "Ezen a tektonon nincs sajat gombatest!");
                        selectingGomba = false;
                        gamePanel.state = GameState.DEFAULT;
                    }
                // 2. Szomszédos tekton kiválasztása
                } else if (selectingIsland && selectedGomba != null) {
                    backend.gomba.Gombatest backendGombatest = null;
                    backend.felhasznalo.Gombasz gombasz = (backend.felhasznalo.Gombasz) logic.getPlayerByIndex(currentPlayerIndex);
                    for (backend.gomba.Gomba g : gombasz.getGombak()) {
                        for (backend.gomba.Gombatest gt : g.getGombatest()) {
                            if (gt.getTekton().getID() == selectedGomba.island) {
                                backendGombatest = gt;
                                break;
                            }
                        }
                        if (backendGombatest != null) break;
                    }
                    if (backendGombatest != null) {
                        backend.tekton.Tekton celTekton = gamePanel.tileM.islands.get(selectedIsland).tekton;
                        java.util.List<backend.tekton.Tekton> allTektons = logic.getAllTektons();
                        boolean szomszedos = backendGombatest.getTekton().szomszedosTekton(celTekton, allTektons);
                        if (szomszedos) {
                            // Spóra szórása a kiválasztott szomszédos tektonra
                            // (Backend logika: pl. celTekton.addSpora() vagy hasonló)
                            TektonComponent island = gamePanel.tileM.islands.get(selectedIsland);
                            island.handleTileClick(mouseX, mouseY, null);
                            JOptionPane.showMessageDialog(gamePanel, "Spora sikeresen elszorva a tektonra");
                            backendGombatest.sporaSzoras(celTekton);
                            decreaseActionPointsForCurrentPlayer();
                            checkAndAdvanceTurn();
                        } else {
                            JOptionPane.showMessageDialog(gamePanel, "Csak szomszedos tektonra szorhatsz sporat!");
                        }
                    }
                    selectingIsland = false;
                    selectedGomba = null;
                    gamePanel.state = GameState.DEFAULT;
                } else {
                    // Első lépés: gombatest kiválasztás
                    selectingGomba = true;
                    JOptionPane.showMessageDialog(gamePanel, "Valassz ki egy sajat gombatestet!");
                }
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void handleGombanoveszt() {
        if (gameOver) return;
        if (gamePanel.state == GameState.GOMBANOVESZTES) {
            int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
            if (ap >= 2) {
                // Csak üzenetet jelenítünk meg, a szigetkijelölés a handleClick-ben történik
                JOptionPane.showMessageDialog(gamePanel, "Valassz ki egy szigetet a gombanoveszteshez!");
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
                gamePanel.state = GameState.DEFAULT;
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void handleFonalnoveszt() {
        if (gamePanel.getFirstSelectedIsland() == null) {
            gamePanel.setFirstSelectedIsland(gamePanel.tileM.islands.get(selectedIsland));
            JOptionPane.showMessageDialog(gamePanel, "Jelolj ki egy masodik szigetet!");
        } else if (gamePanel.getSecondSelectedIsland() == null) {
            TektonComponent secondIsland = gamePanel.tileM.islands.get(selectedIsland);
            if (secondIsland == gamePanel.getFirstSelectedIsland()) {
                JOptionPane.showMessageDialog(gamePanel, "Kerlek, valassz ket kulonbozo szigetet!");
                return;
            }
            // Ellenőrzés: legalább az egyik tektonon van-e saját gombatest vagy fonal
            TektonComponent firstIsland = gamePanel.getFirstSelectedIsland();
            TektonComponent[] islands = new TektonComponent[] { firstIsland, secondIsland };
            boolean found = false;
            backend.felhasznalo.Gombasz gombasz = (backend.felhasznalo.Gombasz) logic.getPlayerByIndex(currentPlayerIndex);
            backend.gomba.Gombatest kapcsolodoGombatest = null;
            for (TektonComponent island : islands) {
                int tektonId = island.tekton.getID();
                // Gombatestek vizsgálata
                for (backend.gomba.Gomba g : gombasz.getGombak()) {
                    for (backend.gomba.Gombatest gt : g.getGombatest()) {
                        if (gt.getTekton().getID() == tektonId) {
                            found = true;
                            kapcsolodoGombatest = gt;
                            break;
                        }
                    }
                    if (found) break;
                    // Gombafonalak vizsgálata
                    for (backend.gomba.Gombafonal gf : g.getGombafonalak()) {
                        if (gf.getHatar1().getID() == tektonId || gf.getHatar2().getID() == tektonId) {
                            found = true;
                            kapcsolodoGombatest = gf.getTest();
                            break;
                        }
                    }
                    if (found) break;
                }
                if (found) break;
            }
            if (!found) {
                JOptionPane.showMessageDialog(gamePanel, "Csak akkor noveszthetsz fonalat, ha a ket kivalasztott tekton kozul legalabb az egyiken van mar gombatested vagy fonalad!");
                gamePanel.clearSelectedIslands();
                gamePanel.state = GameState.DEFAULT;
                return;
            }
            // Fonal létrehozása
            int island1Index = gamePanel.tileM.islands.indexOf(firstIsland);
            int island2Index = gamePanel.tileM.islands.indexOf(secondIsland);
            backend.tekton.Tekton t1 = firstIsland.tekton;
            backend.tekton.Tekton t2 = secondIsland.tekton;
            backend.gomba.Gombafonal ujFonal = new backend.gomba.Gombafonal(0, t1, t2, kapcsolodoGombatest);
            // Megkeressük, melyik gombához tartozik a kapcsolodoGombatest, és oda adjuk hozzá a fonalat
            for (backend.gomba.Gomba g : gombasz.getGombak()) {
                if (g.getGombatest().contains(kapcsolodoGombatest)) {
                    g.addFonal(ujFonal);
                    break;
                }
            }
            // Frontend listába is hozzáadjuk
            gamePanel.addThread(island1Index, island2Index);
            Graphics2D g = (Graphics2D) gamePanel.gameArea.getGraphics();
            if (g != null) {
                Graphics2D g2 = (Graphics2D) g;
                gamePanel.drawThreads(g2, firstIsland, secondIsland);
                g2.dispose();
            }
            decreaseActionPointsForCurrentPlayer();
            gamePanel.state = GameState.DEFAULT;
            gamePanel.repaint();
            JOptionPane.showMessageDialog(gamePanel, "Fonal sikeresen letrehozva!");
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
                logic.getPlayerByIndex(currentPlayerIndex).addPontokSzama(2);
                gamePanel.clearSelections();
                selectingRovar = true;
                selectingIsland = false;
                selectedRovar = null;
                JOptionPane.showMessageDialog(gamePanel, "Mozgatas mod aktivalva! Jelolj ki egy rovart!");
                checkAndAdvanceTurn();
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
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
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void handleFonalelvagas() {
        if (gameOver) return;
        if (gamePanel.state == GameState.FONALELVAGAS) {
            int ap = logic.getPlayerActionPointsByIndex(currentPlayerIndex);
            if (ap >= 2) {
                gamePanel.getStatbar().updateCurrentPlayerActionPoints(ap - 2);
                logic.getPlayerByIndex(currentPlayerIndex).addPontokSzama(2);
                gamePanel.clearSelections();
                selectingRovar = true;
                JOptionPane.showMessageDialog(gamePanel, "Fonalelvagas mod aktivalva! Jelolj ki egy rovart!");
                checkAndAdvanceTurn();
            } else {
                JOptionPane.showMessageDialog(gamePanel, "Nincs eleg akciopontod!");
            }
        } else {
            JOptionPane.showMessageDialog(gamePanel, "Nem megfelelo allapotban vagy!");
        }
    }

    public void showGameResults() {
        // Eredmények összegyűjtése
        java.util.List<backend.felhasznalo.Felhasznalo> players = new java.util.ArrayList<>();
        players.addAll(logic.getGombaszok());
        players.addAll(logic.getRovaraszok());
        StringBuilder sb = new StringBuilder();
        int maxPoints = Integer.MIN_VALUE;
        java.util.List<backend.felhasznalo.Felhasznalo> winners = new java.util.ArrayList<>();
        sb.append("Jatek vege!\nPontszamok:\n");
        for (backend.felhasznalo.Felhasznalo p : players) {
            sb.append(p.getNev()).append(": ").append(p.getPontokSzama()).append(" pont\n");
            if (p.getPontokSzama() > maxPoints) {
                maxPoints = p.getPontokSzama();
                winners.clear();
                winners.add(p);
            } else if (p.getPontokSzama() == maxPoints) {
                winners.add(p);
            }
        }
        if (winners.size() == 1) {
            sb.append("\nGyoztes: ").append(winners.get(0).getNev()).append("!");
        } else {
            sb.append("\nDontetlen a kovetkezo jatekosok kozott: ");
            for (int i = 0; i < winners.size(); i++) {
                sb.append(winners.get(i).getNev());
                if (i < winners.size() - 1) sb.append(", ");
            }
            sb.append("!");
        }
        JOptionPane.showMessageDialog(gamePanel, sb.toString(), "Jatek vege", JOptionPane.INFORMATION_MESSAGE);
    }
}
