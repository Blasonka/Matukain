package backend.jateklogika;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.io.Serializable;

import backend.felhasznalo.Felhasznalo;
import backend.felhasznalo.Gombasz;
import backend.felhasznalo.Rovarasz;
import backend.gomba.Gomba;
import backend.gomba.Gombatest;
import backend.rovar.Rovar;
import backend.spora.Spora;
import backend.tekton.FelszivodosTekton;
import backend.tekton.FonalMegtartoTekton;
import backend.tekton.MaxEgyFonalTekton;
import backend.tekton.Tekton;
import backend.tekton.TobbFonalTekton;
import backend.tekton.GombatestNelkuliTekton;
import backend.gomba.Gombafonal;


/**
 * gameLogic
 *
 * @class gameLogic
 *
 * @brief A szkeleton fázis inicializálását megvalósító osztály
 *
 * @details
 * Inicializáló osztály, mely 1 fő feladattal rendelkezik:
 * Példányosítja mind a tektonokat, játékosokat, rovarakat és a gombákat is
 *
 * @see gameLogic
 *
 * @note Szkeleton fázishoz tartozik, a metódusok még hiányosak lehetnek
 *
 * @author Bence338
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public class gameLogic implements Serializable {
    int korSzamlalo;
    private List<Tekton> map;
    private List<Gombasz> gombaszok = new ArrayList<>();
    private List<Rovarasz> rovaraszok = new ArrayList<>();
    public boolean veletlenEsemenyekEngedelyezve = true;
    public double toresEsely = 0.0; // Initialized to 0
    private List<Gombafonal> fonalak = new ArrayList<>();
    public int fonalelet = 0; // Initialized to 0
    private List<Rovar> rovarok = new ArrayList<>();

    /**
     * Default constructor
     */
    public gameLogic() {
        this.map = new ArrayList<>();
    }

    /**
     * Simple getters and setters for later tasks
     */
    public int getKorszamlalo() {
        return korSzamlalo;
    }

    public void setKorszamlalo(int szam) {
        korSzamlalo = szam;
    }

    public void setMap(List<Tekton> m) {
        map = m;
    }

    public List<Tekton> getMapTekton() {
        return map;
    }

    public List<Gombasz> getGombaszok() {
        return gombaszok;
    }

    public List<Rovarasz> getRovaraszok() {
        return rovaraszok;
    }

    public List<Gombafonal> getFonalak() {
        return fonalak;
    }

    public List<Rovar> getRovarok() {
        return rovarok;
    }

    public void setFonalakElete(int fonalelet) {
        this.fonalelet = fonalelet;
        for (Gombafonal fonal : fonalak) {
            fonal.setPusztulasSzamlalo(fonalelet);
        }
    }

    /**
     * Létrehoz 2 gombászt és 2 rovarászt a beírt nevekből.
     * @param names Tömb a 4 stringből amit beírtak névnek
     */
    public void createUsers(String[] names) {
        if (names == null || names.length != 4) {
            throw new IllegalArgumentException("Exactly 4 names are required");
        }

        gombaszok.clear();
        rovaraszok.clear();

        // Create 2 Gombasz users
        gombaszok.add(new Gombasz(0, names[0]));
        gombaszok.add(new Gombasz(1, names[1]));

        // Create 2 Rovarasz users
        rovaraszok.add(new Rovarasz(2, names[2]));
        rovaraszok.add(new Rovarasz(3, names[3]));

        System.out.println("Users created with names: " + names[0] + ", " + names[1] + ", " + names[2] + ", " + names[3]);
    }

    /**
     * Lerakja a megfelelő tektonra a megfelő entitást
     * @param playerIndex A jelenlegi játékos indexe (0 - 3)
     * @return prompt amit ki lehet írni
     */
    public String promptForInitialPlacement(int playerIndex) {
        if (playerIndex < 2) {
            Gombasz gombasz = gombaszok.get(playerIndex);
            return gombasz.getNev() + ": Kattintson egy tektonra";
        } else {
            Rovarasz rovarasz = rovaraszok.get(playerIndex - 2);
            return rovarasz.getNev() + ": Kattintson egy tektonra";
        }
    }

    /**
     * Frissíti a tekton koordinátáit.
     * @param tektonIndex a tektonnak az indexe
     * @param x x kordináta
     * @param y y koordináta
     */
    public void updateTektonCoordinates(int tektonIndex, int x, int y) {
        if (tektonIndex >= 0 && tektonIndex < map.size()) {
            Tekton tekton = map.get(tektonIndex);
            tekton.setKoordinataX(x);
            tekton.setKoordinataY(y);
        }
    }

    /**
     * Tektont azonosít be a koordinátája alapján
     * @param x X koordináta
     * @param y Y koordináta
     * @return Az adott koordinátákkal ellátott Tekton
     */
    public Tekton getTektonBasedOnCords(int x, int y) {
        for (Tekton t : map) {
            if (t.getKoordinataX() == x && t.getKoordinataY() == y) {
                return t;
            }
        } return null;
    }

    /**
     * Tektontörés esetén létrehoz egy új tektont
     * @return Az új tekton
     */
    public Tekton createNewTekton() {
        int ujID = 0;
        for (Tekton t : map) {
            if (t.getID() > ujID) {
                ujID = t.getID();
            }
        } Tekton uj = switch(new Random().nextInt(5)) {
            case 0 -> new FelszivodosTekton(ujID + 1, 2, 2);
            case 1 -> new GombatestNelkuliTekton(ujID + 1, 3, 3);
            case 2 -> new MaxEgyFonalTekton(ujID + 1, 4, 4);
            case 3 -> new TobbFonalTekton(ujID + 1, 5, 5);
            case 4 -> new FonalMegtartoTekton(ujID + 1, 6, 6);
            default -> null;
        };
        map.add(uj);
        return uj;
    }

    /**
     * Megkeresi a legnagyobb tárolt gomba ID-t
     * @return a legnagyobb ID
     */
    public int getGombaID() {
        int id = 0;
        for (Gombasz g : gombaszok) {
            for (Gomba gomba : g.getGombak()) {
                if (gomba.getID() > id) {
                    id = gomba.getID();
                }
            }
        } return id;
    }

    /**
     * Megkeresi a legnagyobb tárolt gombatest ID-t
     * @return a legnagyobb ID
     */
    public int getGombatestID() {
        int id = 0;
        for (Gombasz g : gombaszok) {
            for (Gomba gomba : g.getGombak()) {
                for (Gombatest test : gomba.getGombatest()) {
                    if (test.getID() > id) {
                        id = test.getID();
                    }
                }
            }
        } return id;
    }

    /**
     * Megkeresi a legnagyobb tárolt rovar ID-t
     * @return a legnagyobb ID
     */
    public int getRovarID() {
        int id = 0;
        for (Rovar r : rovarok) {
            if (r.getID() > id) {
                id = r.getID();
            }
        } return id;
    }

    /**
     * meghívja a Tekton tores() metódusát, egy tekton kettétörését valósítja meg
     * @param t a tektonok listája
     * @note A tores() metódus a Tekton osztályban van implementálva
     */
    public void tektonTores(List<Tekton> t) {
        Random rand = new Random();

        for (int i = 0; i < t.size(); i++) {
            Tekton current = t.get(i);

            if (rand.nextDouble() < toresEsely) {
                int ujId = current.getID() + 500;
                int ujX = current.getKoordinataX() - 1;
                int ujY = current.getKoordinataY();

                Tekton ujTekton = current.klonoz(ujId, ujX, ujY);
                current.tores();

                if (ujTekton != null) {
                    current.addSzomszed(ujTekton);
                    ujTekton.addSzomszed(current);
                    t.add(ujTekton);
                    System.out.println("Új tekton létrehozva ID: " + ujTekton.getID() + " hol (" + ujTekton.getKoordinataX() + ", " + ujTekton.getKoordinataY() + ")");
                }
            }
        }
    }

    /**
     * Csökkenti a fonalak élettartamát, és eltávolítja azokat, amelyek elpusztultak
     */
    public void csokkentFonalakElete() {
        Iterator<Gombafonal> iterator = fonalak.iterator();
        while (iterator.hasNext()) {
            Gombafonal fonal = iterator.next();
            boolean elpusztult = fonal.csokkentPusztulasSzamlalo();
            if (elpusztult) {
                System.out.println("Gombafonal ID " + fonal.getId() + " elpusztult.");
                iterator.remove(); // Remove threads that have expired
            }
        }
    }

    /**
     * Gombászok körének végrehajtása
     * @note A gombászok körében a gombák növesztése és spórák lövése történhet
     */
    public void gombaszKor() {
        System.out.println("--- Gombászok köre ---");
        for (Gombasz g : gombaszok) {
            g.setHatralevoAkciopont(5); // Reset to 5, not 0, as per game logic (adjust if 0 is intended)

            while (g.getHatralevoAkciopont() > 0) {
                if (g.getHatralevoAkciopont() >= 1) {
                    // g.sporaLoves(null);
                    g.setHatralevoAkciopont(g.getHatralevoAkciopont() - 1);
                    System.out.println(g.getNev() + " szórt egy spórát.");
                }

                if (g.getHatralevoAkciopont() >= 1) {
                    Gomba gomba = new Gomba(0);
                    g.getGombak().add(gomba);
                    g.setHatralevoAkciopont(g.getHatralevoAkciopont() - 1);
                    System.out.println(g.getNev() + " gombatestet növesztett.");
                }

                if (g.getHatralevoAkciopont() >= 1) {
                    // .fonalNovesztes(null,null);
                    g.setHatralevoAkciopont(g.getHatralevoAkciopont() - 1);
                    System.out.println(g.getNev() + " gombafonalat növesztett.");
                }

                break;
            }
        }
    }

    public void rovaraszKor() {
        // Implement as needed
    }

    /**
     * Kör végrehajtása
     * @note A kör végrehajtásához szükséges metódusok meghívása
     */
    public void kor() {
        System.out.println("Kör kezdete: #" + korSzamlalo);

        gombaszKor();        // 1. Gombászok körei
        rovaraszKor();       // 2. Rovarászok körei
        tektonTores(map);    // 3. Tekton törések
        korSzamlalo++;       // 4. Körszámláló növelése

        System.out.println("Kör vége. Új körszám: " + korSzamlalo);
    }

    /**
     * pont hozzáadása a játékhoz
     * @param f jelenlegi játékos
     */
    public void pontOsztas(Felhasznalo f) {
        System.out.println("Pont hozzáadva. gameLogic->Felhasznalo");
    }

    /**
     * Inicializálja a játékot
     */
    public void jatekKezdes() {
        // This method will be called after createUsers to initialize the game state
        List<Gombasz> gombaszok2 = new ArrayList<>(); // Use existing gombaszok
        List<Rovarasz> rovaraszok = new ArrayList<>(); // Use existing rovaraszok
        Random rand = new Random();
        Tekton[] tektonok = new Tekton[3];

        for (int i = 0; i < 3; i++) {
            int randomIndex = rand.nextInt(5);
            switch (randomIndex) {
                case 0 -> tektonok[i] = new FelszivodosTekton(1, 2, 2);
                case 1 -> tektonok[i] = new GombatestNelkuliTekton(2, 3, 3);
                case 2 -> tektonok[i] = new MaxEgyFonalTekton(3, 4, 4);
                case 3 -> tektonok[i] = new TobbFonalTekton(4, 5, 5);
                case 4 -> tektonok[i] = new FonalMegtartoTekton(5, 6, 6);
            }
            map.add(tektonok[i]);
        }

        // Optionally add initial entities (set to 0 or empty if not needed)
        korSzamlalo = 0;

        System.out.println("Játék inicializálva. Játékosok, tektonok, rovarok, gombák létrehozva.");
    }

    /**
     * kihírdeti a győztest a végén
     * @param f játékosok listája
     */
    public void jatekOsszegzes(List<Felhasznalo> f) {
        System.out.println("gameLogic->gameLogic");
    }

    /**
     * Hozzáad egy rovarászt a rovarászok listájához
     * @param r
     */
    public void addRovarasz(Rovarasz r) {
        rovaraszok.add(r);
    }

    /**
     * Hozzáad egy gombászt a gombászok listájához
     * @param g
     */
    public void addGombasz(Gombasz g) {
        gombaszok.add(g);
    }

    /**
     * Hozzáad egy tekton objektumot a tektonok listájához
     * @param t
     */
    public void addTekton(Tekton t) {
        map.add(t);
    }

    /**
     * Visszaadja az adott ID-jú tekton objektumot
     * @param id
     * @return a tekton objektum, ha megtalálható, egyébként null
     */
    public Tekton getTekton(int id) {
        for (Tekton t : map) {
            if (t.getID() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * Visszaadja az adott ID-jú gombászt
     * @param id
     * @return a Gombász objektum, ha megtalálható, egyébként null
     */
    public Gombasz getGombasz(int id) {
        for (Gombasz gombasz : gombaszok) {
            if (gombasz.getID() == id) {
                return gombasz;
            }
        }
        return null;
    }

    /**
     * Visszaadja az adott ID-jú rovarászt
     * @param id
     * @return a Rovarász objektum, ha megtalálható, egyébként null
     */
    public Rovarasz getRovarasz(int id) {
        for (Rovarasz rovarasz : rovaraszok) {
            if (rovarasz.getID() == id) {
                return rovarasz;
            }
        }
        return null;
    }

    /**
     * egy kör szimulálása
     * @note A kör szimulálásához szükséges metódusok meghívása
     */
    public void simulateRound() {
        int regi = korSzamlalo;
        korSzamlalo++;
        for (Tekton t : map) {
            if (t.getGomba() != null) {
                t.hatasKifejtes(t.getGomba());
            }
            for (Rovar rovar : getRovarok()) {
                rovar.sporaManager();
            }
        }
        csokkentFonalakElete();
    }
}