package tesztelo;

import felhasznalo.Felhasznalo;
import felhasznalo.Gombasz;
import felhasznalo.Rovarasz;
import gomba.Gomba;
import gomba.Gombafonal;
import gomba.Gombatest;
import jateklogika.gameLogic;
import rovar.Rovar;
import spora.Spora;
import tekton.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Parancsfeldolgozó osztály
 *
 * @class PranacsFeldolgozo
 *
 * @brief A prototípus fázisbeli működés parancsfeldolgozó részét megvalósító osztály
 *
 * @details
 * Parancsefeldolgozó osztály, mely a kapott parancsokat végrehajtva obejktumokat hoz létre,
 * módosítja azok állapotát. A parancsok
 *
 * @see tesztelo.ParancsFeldolgozo
 *
 * @author todortoth
 * @version 1.0 - Manuális mód, parancsok fele elkészítésre vár
 * @date 2025-04-22
 */
public class ParancsFeldolgozo {
    /**
     * A standard kimenetre elhelyezett adatok mentésére szolgál
     * @var StringBuffer kimenet
     * @brief A print() metódus menti el ide azt, amit a System.out.println parancs segítségével a kimenetre tesz
     */
    StringBuffer kimenet = new StringBuffer();

    /**
     * A Menu osztály beolvaso változójának referenciája
     * @see tesztelo.Menu
     */
    Scanner beolvaso;

    /**
     * A Menu osztály jatekLogika változójának referenciája
     * @see tesztelo.Menu
     */
    gameLogic jatekLogika;

    /**
     * A ParancsFeldolgozo osztály konstruktora, mely a tagváltozókat beállítja
     * @param be beolvaso
     * @param logika jatekLogika
     */
    ParancsFeldolgozo(Scanner be, gameLogic logika) {
        beolvaso = be;
        jatekLogika = logika;
    }

    /**
     * Print metódus
     * A paraméterként kapott stringet elmenti az osztály StringBuffer kimenet változójába,
     * emellett elhelyezi a standard kimenetre
     * @param ki kimenetnek szánt string
     */
    public void print(String ki) {
        kimenet.append(ki);
        System.out.println(ki);
    }

    /**
     * Interpret metódus
     * A paraméterként kapott stringet dolgozza fel a bemeneti nyelv parancsai szerint
     * Minden parancs saját függvénnyel rendelkezik, melyek meghívásra kerülnek
     * @param cmd parancs string
     */
    public void interpret(String cmd) {
        String[] command = cmd.split(" ");
        try {
            switch (command[0]) {
                case "/addro":
                    addro(Integer.parseInt(command[1]), command[2], Integer.parseInt(command[3]));
                    break;
                case "/addgo":
                    addgo(Integer.parseInt(command[1]), command[2], Integer.parseInt(command[3]));
                    break;
                case "/addg":
                    addg(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "/addr":
                    addr(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "/addt":
                    addt(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]), command[4].charAt(0));
                    break;
                case "/adds":
                    adds(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[4]), command[5].charAt(0));
                    break;
                case "/addf":
                    addf(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[4]), Integer.parseInt(command[5]), command[7].charAt(0));
                    break;
                case "/addgt":
                    addgt(Integer.parseInt(command[1]), Integer.parseInt(command[2]) == 1, Integer.parseInt(command[3]), Integer.parseInt(command[5]), Integer.parseInt(command[6]));
                    break;
                case "/random":
                    rand(command[1].charAt(0), Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "/save":
                    save(command[1], (command.length == 3 ? command[2].charAt(1) : '0'));
                    break;
                case "/load":
                    load(command[1]);
                    break;
                case "/help":
                    help();
                    break;
                case "/move":
                    move(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[4]));
                    break;
                case "/cut":
                    cut(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "/eat":
                    eat(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "/consume":
                    consume(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    break;
                case "/list":
                    list();
                    break;
                case "/lista":
                    lista();
                    break;
                case "/trigger_tores":
                    trigger_tores(Integer.parseInt(command[1]));
                    break;
                case "/sporagombatesttel":
                    sporagombatesttel(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[5]), Integer.parseInt(command[6]), command[7].charAt(0));
                    break;
                case "/simulate_round":
                    simulate_round();
                    break;
                case "/fonal_novesztes":
                    fonal_novesztes(Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[5]), Integer.parseInt(command[6]), command[7].charAt(0));
                default:
                    System.out.println("Érvénytelen parancs! (/help a parancsok listájához)");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("A parancs valamely paraméterének formátuma nem megfelelő!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("A parancs paramétereinek száma nem megfelelő!");
        }
    }

    /**
     * Rovarász felhasználó hozzáadása
     * A rovarászhoz tartozó rovarokat külön parancs segítségével lehet hozzáadni
     * @param id játékos ID-ja
     * @param nev játékos neve
     * @param pontszam játékos kezdő pontszáma
     */
    private void addro(int id, String nev, int pontszam) {
        // TODO Rovarasz megfelelő paraméterezése
        jatekLogika.jatekosok.add(new Rovarasz("Rovarasz" + id));
    }

    /**
     * Gombász felhasználó hozzáadása
     * A hozzzá tartozó gombákat külön parancs adja hozzá
     * @param id játékos azonosítója
     * @param nev játékos neve
     * @param pontszam játékos kezdő pontszáma
     */
    private void addgo (int id, String nev, int pontszam) {
        // TODO Gombasz megfelelő paraméterezése
        jatekLogika.jatekosok.add(new Gombasz("Gombasz" + id));
    }

    /**
     * Gomba hozzáadása
     * Egy gomba pontosan egy felhasználóhoz tartozik, de egy gombához több gombatest tartozhat.
     * Ezeket külön kell hozzáadni.
     * @param id azonosító
     * @param tektonID melyik tektonon található a gomba
     * @param jatekosID melyik felhasználóhoz tartozik a gomba
     */
    private void addg(int id, int tektonID, int jatekosID) {
        // TODO Gomba ID változójának hozzáadása
        // Azzal a feltételezéssel élve, hogy a tektonID és a jatekosID megfeleltethető az elfoglalt helyével (a tesztekben elvileg igaz)
        try {
            ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().add(new Gomba());
        }  catch (IndexOutOfBoundsException e) {
            System.out.println("Gomba felvétele sikertelen!\n" +
                    "Lehetséges hibahelyek: Gombász ID nem létezik, tekton ID nem létezik");
        }
    }

    /**
     * Rovar hozzáadása
     * @param id azonosító
     * @param tektonID melyik tektonon található a gomba
     * @param jatekosID melyik felhasználóhoz tartozik a gomba
     */
    private void addr(int id, int tektonID, int jatekosID) {
        // TODO Rovar ID változójának hozzáadása
        // Azzal a feltételezéssel élve, hogy a tektonID és a jatekosID megfeleltethető az elfoglalt helyével (a tesztekben elvileg igaz)
        try {
            ((Rovarasz) jatekLogika.jatekosok.get(jatekosID)).getRovarok().add(new Rovar(jatekLogika.getMapTekton().get(tektonID), id));
        }  catch (IndexOutOfBoundsException e) {
            System.out.println("Rovar felvétele sikertelen!\n" +
                    "Lehetséges hibahelyek: Rovarász ID nem létezik, tekton ID nem létezik");
        }
    }

    /**
     * Tekton hozzáadása
     * @param id tekton id-ja
     * @param x tekton x koordinátája
     * @param y tekton y koordinátája
     * @param type tekton típusa [L (életbentartó), M (egyfonal) F (felszívódós), T (többfonalas) G (gombatest nélküli)]
     */
    private void addt(int id, int x, int y, char type) {
        // TODO Tekton szomszédosság lekezelése
        switch (type) {
            case 'L':
                // TODO Életbentrartó tekton implementálása
                // jatekLogika.getMapTekton().add(new EletbenTartoTekon(id, x, y));
                break;
            case 'M':
                jatekLogika.getMapTekton().add(new MaxEgyFonalTekton(id, x, y));
                break;
            case 'F':
                jatekLogika.getMapTekton().add(new FelszivodosTekton(id, x, y));
                break;
            case 'T':
                jatekLogika.getMapTekton().add(new TobbFonalTekton(id, x, y));
                break;
            case 'G':
                jatekLogika.getMapTekton().add(new GombatestNelkuliTekton(id, x, y));
                break;
            default:
                System.out.println("Érvénytelen Tektontípus! (/help a megadható értékek listájához)");
                break;
        }
    }

    /**
     * Spóra felvétele
     * Az ilyen módon történő felvétel NEM fogyasztja a felhasználó akciópontjait
     * @param id azonosító
     * @param tektonID tekton, amelyen a spóra megtalálható
     * @param gombaID gomba, amelyhez a spóra tartozik
     * @param jatekosID játékos, akihez a spóra tartozik
     * @param type spóra típusa [O (osztó), L (lassító), G (gyorsító), B (bénító), V (vágásgátló)]
     */
    private void adds(int id, int tektonID, int gombaID, int jatekosID, char type) {
        try {
            switch (type) {
                case 'O':
                    // TODO RovarOsztóSpóra implementálása
                    // jatekLogika.getMapTekton().get(tektonID).sporak.add(new RovarOsztoSpora(id, ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID)));
                    break;
                case 'L':
                    // jatekLogika.getMapTekton().get(tektonID).sporak.add(new LassitoSpora(id, ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID)));
                    break;
                case 'G':
                    // jatekLogika.getMapTekton().get(tektonID).sporak.add(new GyorsitoSpora(id, ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID)));
                    break;
                case 'B':
                    // jatekLogika.getMapTekton().get(tektonID).sporak.add(new BenitoSpora(id, ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID)));
                    break;
                case 'V':
                    // jatekLogika.getMapTekton().get(tektonID).sporak.add(new VagasGatloSpora(id, ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID)));
                    break;
                default:
                    System.out.println("Érvénytelen Spóratípus! (/help a megadható értékek listájához)");
                    break;
            }
        }  catch (IndexOutOfBoundsException e) {
            System.out.println("Spóra felvétele sikertelen!\n" +
                    "Lehetséges hibahelyek: Gombász ID nem létezik, Gomba ID nem létezik, tekton ID nem létezik");
        }
    }

    /**
     * Gombafonal felvétele
     * Az így felvett gombafonalak NEM fogyasztják a felhasználó akciópontjait
     * @param id gombafonal ID-ja
     * @param tektonID1 a gombafonalat határoló tekton ID-ja
     * @param tektonID2 a gombafonalat határoló másik tekton ID-ja (ha a gombafonal egy tektonon van, akkor azonos tektonID1-el)
     * @param gombatestID a gombafonalat "lehelyező" gombatest azonosítója
     * @param jatekosID a játékos azonosítója, akihez a gombafonal tartozik
     * @param type a gombafonal típusa [S (sima), H (húsevő)]
     */
    private void addf(int id, int tektonID1, int tektonID2, int gombatestID, int jatekosID, char type) {
        // TODO gombafonalak új tárolási módjának implementálása
        try {
            switch (type) {
                case 'S':
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getFonalak().add(
                    // new Gombafonal(id, jatekLogika.getMapTekton().get(tektonID1), jatekLogika.getMapTekton().get(tektonID2),
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getgombatestek().get(gombatestID)));
                    break;
                case 'H':
                    // TODO HusevoGombafonal létrehozása
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getFonalak().add(
                    // new HusevoGombafonal(id, jatekLogika.getMapTekton().get(tektonID1), jatekLogika.getMapTekton().get(tektonID2),
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getgombatestek().get(gombatestID)));
                    break;
                default:
                    System.out.println("Érvénytelen Gombafonaltípus! (/help a megadható értékek listájához)");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Gombafonal felvétele sikertelen!\n" +
                    "Lehetséges hibahelyek: Gombász ID nem létezik, Gomba ID nem létezik," +
                    "Gombatest ID nem létezik, Tekton ID nem létezik");
        }
    }

    /**
     * Gombatest felvétele
     * @param id gombatest azonosítója
     * @param fejlett gombatest fejlettségi szintje
     * @param tektonID azon tekton azonosítója, amin a gombatest van
     * @param gombaID gombatesthez tartozó gomba azonosítója
     */
    private void addgt(int id, boolean fejlett, int tektonID, int gombaID, int gombaszID) {
        // TODO gombatestek új tárolási módjának implementálása
        // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getgombatestek().add(
        // new Gombatest(id, jatekLogika.getMapTekton().get(tektonID), fejlett);
    }

    /**
     * Randomizálás ki- és bekapcsolása
     * @param val a randomizálás ki és bekapcsolása [E: be, D: ki]
     * @param tores a tektontörés valószínűsége (0 és 1 között)
     * @param megmaradas a gombafonal elrágás utáni megmaradásának ideje
     */
    private void rand(char val, int tores, int megmaradas) {
        // TODO gameLogic-ba ennek megfelelő publikus globális változók implementálása, amit aztán a többi osztály (tekton, gombafonal) figyelni tud
        switch (val) {
            case 'D':
                // jatekLogika.tekton = tores;
                // jateklogika.fonal = megmaradas;
                break;
            case 'E':
                // jatekLogika.visszadefaultra
            default:
                System.out.println("Érvénytelen érték! (/help a megadható értékek listájához)");
        }
    }

    /**
     * Mentés elvégzése
     * A gameLogic osztályt menti szerializáció segítségével
     * @param pathToFile a fájl elérési útja
     * @param k opcionálisan megadható kapcsoló: -k esetén a kimenetet menti el és nem a játékállást
     */
    private void save(String pathToFile, char k) {
        // TODO gameLogic felkészítése a mentésre
        try {
            if (k == 'k') {
                BufferedWriter bfr = new BufferedWriter(new FileWriter(pathToFile));
                bfr.write(kimenet.toString());
                bfr.close();
                System.out.println("Kimenet elmentve: " + pathToFile);
            } else {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathToFile));
                out.writeObject(jatekLogika);
                out.close();
                System.out.println("Játékállás elmentve: " + pathToFile);
            }
        } catch (IOException e) {
            System.err.println("Mentés sikertelen!");
        }
    }

    /**
     * Tetszőleges, korábban elmentett játékállás betöltése
     * @param pathToFile a fájl elérési útja
     */
    private void load(String pathToFile) {
        // TODO gameLogic felkészítése a betöltésre
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathToFile));
            jatekLogika = (gameLogic) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("A megadott helyen a fálj nem található!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Beolvasási hiba!");
        }
    }

    /**
     * Parancsok leírása
     * Tartalmazza a paracsok listáját, az egyes parancsok rövid jeletését, a kapcsolók lehetséges értékeit
     */
    private void help() {
        // TODO Rovar parancsok játékosID hozzáadása
        // TODO sporagombatesttel gomba, gombatest és játékos hozzáadása
        // TODO fonal_novesztes játékosID hozzáadása
        System.out.println("parancsok:\n" +
                "rovarász hozzáadása:\t/addro [ID] [Név] [Pontszám]\n" +
                "gombász hozzáadása:\t/addgo [ID] [Név] [Pontszám]\n" +
                "gomba hozzáadása:\t/addg [ID] [TektonID] [JátékosID]\n" +
                "rovar hozzáadása:\t/addr [ID] [TektonID] [JátékosID]\n" +
                "tekton hozzáadása:\t/addt [ID] [X Koordináta] [Y Koordináta] [Típus: L (életbentartó), M (egyfonal) F (felszívódós), T (többfonalas) G (gombatest nélküli)]\n" +
                "spóra hozzáadása:\t/adds [ID] [TektonID] [GombaID] [FelhasználóID] [Típus: O (osztó), L (lassító), G (gyorsító), B (bénító), V (vágásgátló)]\n" +
                "fonal hozzáadása:\t/addf [ID] [TektonID1] [TektonID2] [GombaID] [GombatestID] [JátékosID] [Típus: S (sima), H (húsevő)]\n" +
                "gombatest hozzáadása:\t/addgt [ID] [Fejlettség (0: Sima, 1: Fejlett)] [TektonID] [GombaID] [JátékosID]\n" +
                "randomizálás:\t/random [E: be, D:ki] [Tektontörés valószínűsége (0-1)] [Gombafonal elrágás utáni megmaradásának ideje (kör)]\n" +
                "játékállás mentése:\t/save [PATH/TO/FILE] [-k: kimenet mentése (opcionális)]\n" +
                "játékállás betöltése:\t/load [PATH/TO/FILE]\n" +
                "rovarmozgatás:\t/move [RovarID] [JátékosID] [JelenlegiTektonID] [CéltektonID]\n" +
                "fonalvágás:\t/cut [RovarID] [JátékosID] [FonalID]\n" +
                "spóraevés:\t/eat [RovarID] [JátékosID] [SpóraID]\n" +
                "rovarevés:\t/consume [FonalID] [RovarID]\n" +
                "térkép listázása:\t/list\n" +
                "felhasználók listája:\t/lista\n" +
                "egy tekton kettétörése:\t/trigger_tores [TektonID]\n" +
                "spóralövés:\t/sporagombatesttel [ID] [TektonID] [GombaID] [GombatestID] [FelhasználóID] [Típus (ld. /adds)]\n" +
                "kör szimulálása:\t/simulate_round\n" +
                "fonalnövesztés:\t/fonal_novesztes [ID] [GombatestID] [JátékosID] [TektonID] [TektonID] [Típus (ld /addf)]\n" +
                "gombatestnövesztés: /gombatestnovesztes [ID] [SpóraID1] [SpóraID2] [SpóraID3] [TektonID] [FelhasznaloID]");
    }

    /**
     * Rovar mozgatása
     * @param rovarID mozgatandó rovar
     * @param rovaraszID játékos akihez a rovar tartozik
     * @param jelenlegiTektonID jelenlegi tekton
     * @param TektonID tekton amire áttenni szeretnénk a rovart
     */
    private void move(int rovarID, int rovaraszID, int jelenlegiTektonID, int TektonID) {
        try {
            // TODO attesz-ben valahogy rovar megadása
            List<Rovar> rovarok = ((Rovarasz) jatekLogika.jatekosok.get(rovaraszID)).getRovarok();
            List<Tekton> tektonok = jatekLogika.getMapTekton();
            Tekton ujTekton = null;
            for (Tekton t : tektonok) {
                if (t.getID() == jelenlegiTektonID) {
                    ujTekton = t;
                    break;
                }
            }
            for (Rovar r : rovarok) {
                if (r.getID()==rovarID) {
                    r.attesz(ujTekton);
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Rovar mozgatása sikertelen!\n" +
                    "Lehetséges hibahelyek: Rovarász ID nem létezik, Rovar ID nem létezik, Tekton ID nem létezik");
        }
    }

    /**
     * Gombafonal elvágása
     * Megkeresi a gombafonalak között a gombafonalat
     * @param rovarID rovar ID ami vágni akar
     * @param rovaraszID rovarász ID akihez a gombafonal tartozik
     * @param fonalID fonal ID amit a rovar el akar vágni
     */
    private void cut(int rovarID, int rovaraszID, int fonalID) {
        try {
            Gombafonal keresett = null;
            for (Tekton t : jatekLogika.getMapTekton()) {
                for (Gombafonal f : t.getGomba().getGombafonalak()) {
                    /*
                    // TODO gombafonal ID paramétere
                    if (f.id == fonalID) {
                        keresett = f;
                    }
                    */
                }
            }
            // TODO rovarásznál fonalvágásnál vágás végző rovart megadni
            if (keresett != null){
                List<Rovar> rovarok = ((Rovarasz) jatekLogika.jatekosok.get(rovaraszID)).getRovarok();

                for (Rovar r : rovarok) {
                    if (r.getID()==rovarID) {
                        r.fonalElvagas(keresett);
                        break;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Fonal elrágása sikertelen!\n" +
                    "Lehetséges hibahelyek: Rovarász ID nem létezik, Rovar ID nem létezik, Fonal ID nem létezik");
        }
    }

    /**
     * Spóra elfogyasztása
     * @param rovarID fogyasztást végző rovar azonosítója
     * @param rovaraszID rovar játékosa
     * @param sporaID fogyasztást elszenvedő spóra id-ja
     */
    private void eat(int rovarID, int rovaraszID, int sporaID) {
        try {
            Spora keresett = null;
            for (Tekton t : jatekLogika.getMapTekton()) {
                for (Spora s : t.getSporak()) {
                /*
                if (s.id == sporaID) {
                    keresett = s;
                }
                */
                }
            }
            if (keresett != null) {
                List<Rovar> rovarok = ((Rovarasz) jatekLogika.jatekosok.get(rovaraszID)).getRovarok();

                for (Rovar r : rovarok) {
                    if (r.getID()==rovarID) {
                        r.elfogyaszt(keresett);
                        break;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Spóra elfogyasztása sikertelen!\n" +
                    "Lehetséges hibahelyek: Rovarász ID nem létezik, Rovar ID nem létezik, Spóra ID nem létezik");
        }
    }

    /**
     * Bénított rovar elfogyasztása
     * @param fonalID húsevőgombafonal id-ja
     * @param rovarID bénított rovar
     */
    private void consume(int fonalID, int rovarID) {
        // TODO Husevo gombafonal elkészítése
    }

    /**
     * Játék aktuális állapotát kilistázó metódust hívja meg
     */
    private void list() {
        // TODO minden listázása gameLogic-ban
    }

    /**
     * Felhasználókat és hozzájuk tartozó pontszámokat listázza ki
     */
    private void lista() {
        // TODO felhasználók listázása gameLogic-ban
    }

    /**
     * Egyetlen egy tekton kettétörését megindító metódus
     * @param tektonID a törendő tekton
     */
    private void trigger_tores(int tektonID) {
        // TODO metódus implementálása játéklogikában
        // jatekLogika.tores(jatekLogika.getMapTekton().get(tektonID));
    }

    /**
     * Spóra felvétele
     * Az ilyen módon történő felvétel NEM fogyasztja a felhasználó akciópontjait
     * @param id azonosító
     * @param tektonID tekton, amelyen a spóra megtalálható
     * @param gombaID gomba, amelyhez a spóra tartozik
     * @param jatekosID játékos, akihez a spóra tartozik
     * @param type spóra típusa [O (osztó), L (lassító), G (gyorsító), B (bénító), V (vágásgátló)]
     */
    private void sporagombatesttel(int id, int tektonID, int gombaID, int gombatestID, int jatekosID, char type) {
        try {
            switch (type) {
                case 'O':
                    // TODO RovarOsztóSpóra implementálása
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getgombatestek().get(gombatestID).sporaszoras(jatekLogika.getMapTekton().get(tektonID));
                    break;
                case 'L':
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getgombatestek().get(gombatestID).sporaszoras(jatekLogika.getMapTekton().get(tektonID));
                    break;
                case 'G':
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getgombatestek().get(gombatestID).sporaszoras(jatekLogika.getMapTekton().get(tektonID));
                    break;
                case 'B':
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getgombatestek().get(gombatestID).sporaszoras(jatekLogika.getMapTekton().get(tektonID));
                    break;
                case 'V':
                    // ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID).getgombatestek().get(gombatestID).sporaszoras(jatekLogika.getMapTekton().get(tektonID));
                    break;
                default:
                    System.out.println("Érvénytelen Spóratípus! (/help a megadható értékek listájához)");
                    break;
            }
        }  catch (IndexOutOfBoundsException e) {
            System.out.println("Spóra felvétele sikertelen!\n" +
                    "Lehetséges hibahelyek: Gombász ID nem létezik, Gomba ID nem létezik, tekton ID nem létezik");
        }
    }

    /**
     * Kör szimulálása
     */
    private void simulate_round() {
        // TODO kör szimulálása játéklogikában
        //jateklogika.kor()
    }

    /**
     * Fonal elhelyezése két tekton között, vagy egy tektonra a játékos által
     * @param id fonal azonosítója
     * @param gombatestID gombatest azonosítója
     * @param jatekosID játékos azonosítója
     * @param tektonID1 a gombafonalat határoló tekton ID-ja
     * @param tektonID2 a gombafonalat határoló másik tekton ID-ja (ha a gombafonal egy tektonon van, akkor azonos tektonID1-el)
     * @param type a gombafonal típusa [S (sima), H (húsevő)]
     */
    private void fonal_novesztes(int id, int gombatestID, int jatekosID, int tektonID1, int tektonID2, char type) {
        try {
            Gombatest keresett = null;
            for (Tekton t : jatekLogika.getMapTekton()) {
            /*
            for (Gombatest g : t.getGomba().getGombatestek()) {
                    // TODO gombatest ID paramétere
                    if (g.id == gombatestID) {
                        keresett = g;
                    }
            } */
            }
            if (keresett != null) {
                List<Gomba> gombak = ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak();
                for (Gomba r : gombak) {
                    if (r.getID()==id) {
                        r.fonalNovesztes(jatekLogika.getMapTekton().get(tektonID1), jatekLogika.getMapTekton().get(tektonID2), keresett);
                        break;
                    }
                }
            }
        }  catch (IndexOutOfBoundsException e) {
            System.out.println("Fonal felvétele sikertelen!\n" +
                    "Lehetséges hibahelyek: Gombász ID nem létezik, Gombatest ID nem létezik, tekton ID nem létezik");
        }
    }

    /**
     * Gombatest növesztése spórákból
     * @param id gombatest azonosítója
     * @param sporaID1 spóra 1
     * @param sporaID2 spóra 2
     * @param sporaID3 spóra 3
     * @param tektonID tekton, amire a gombatest kerülni fog
     * @param felhasznaloID felhasználó, akié a gombatest lesz
     */
    private void gombatest_novesztes(int id, int sporaID1, int sporaID2, int sporaID3, int tektonID, int felhasznaloID) {
        try {
            // TODO gombanövesztéshez felhasználó, spóra1-2-3 hozzáadása
            jatekLogika.getMapTekton().get(tektonID).gombaNovesztes();
        }  catch (IndexOutOfBoundsException e) {
            System.out.println("Gombatest növesztése sikertelen!\n" +
                    "Lehetséges hibahelyek: Gombász ID nem létezik, spóra ID nem létezik, tekton ID nem létezik");
        }
    }
}
