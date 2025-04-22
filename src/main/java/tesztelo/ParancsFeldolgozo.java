package tesztelo;

import felhasznalo.Gombasz;
import felhasznalo.Rovarasz;
import gomba.Gomba;
import gomba.Gombafonal;
import jateklogika.gameLogic;
import rovar.Rovar;
import tekton.*;

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
 * @version 1.0 - Manuális mód
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
        // TODO Rovarasz megfelelő paraméterei
        jatekLogika.jatekosok.add(new Rovarasz(0, 0, 0));
    }

    /**
     * Gombász felhasználó hozzáadása
     * A hozzzá tartozó gombákat külön parancs adja hozzá
     * @param id játékos azonosítója
     * @param nev játékos neve
     * @param pontszam játékos kezdő pontszáma
     */
    private void addgo (int id, String nev, int pontszam) {
        // TODO Gombasz megfelelő paraméterei
        jatekLogika.jatekosok.add(new Gombasz(0, 0, 0));
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
            ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().add(new Gomba(jatekLogika.getMapTekton().get(tektonID)));
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
            ((Rovarasz) jatekLogika.jatekosok.get(jatekosID)).getRovarak().add(new Rovar(jatekLogika.getMapTekton().get(tektonID)));
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
        }
    }

    /**
     * Spóra felvétele
     * @param id azonosító
     * @param tektonID tekton, amelyen a spóra megtalálható
     * @param gombaID gomba, amelyhez a spóra tartozik
     * @param jatekosID játékos, akihez a spóra tartozik
     * @param type spóra típusa [O (osztó), L (lassító), G (gyorsító), B (bénító), V (vágásgátló)]
     */
    private void adds (int id, int tektonID, int gombaID, int jatekosID, char type) {
        // TODO spórák tárolásának implementálása
        Gomba g = ((Gombasz) jatekLogika.jatekosok.get(jatekosID)).getGombak().get(gombaID);
        switch(type) {
            case 'O':
                // TODO RovarOsztóSpóra implementálása
                break;
            case 'L':
                break;
            case 'G':
                break;
            case 'B':
                break;
            case 'V':
                break;
        }
    }
}
