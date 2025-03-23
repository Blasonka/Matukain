package tesztelo;

import felhasznalo.Gombasz;
import gomba.Gomba;
import gomba.Gombafonal;
import gomba.Gombatest;
import jateklogika.gameLogic;
import rovar.Rovar;
import spora.GyorsitoSpora;
import tekton.Tekton;
import tekton.TobbFonalTekton;

import java.io.IOException;
import java.util.Scanner;

/**
 * Tesztelo osztály
 *
 * @class Tesztelo
 *
 * @brief A szkeleton fázis tesztelését megvalósító osztály
 *
 * @details
 * Tesztelő osztály, mely két fő feladattal rendelkezik:
 * (1) megjeleníti a felhasználó számára a menüt, melyből kiválaszthatja a vizsgálni kívánt menüpontot
 * (2) ennek a menüpontnak a tényleges tesztelését el is végzi Unit-teszt formájában
 *
 * @see felhasznalo.Rovarasz
 * @see felhasznalo.Gombasz
 *
 * @note Szkeleton fázishoz tartozik, a metódusok teljesen implementálva vannak
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public class Tesztelo {
    public static Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        int valasztas = -1;
        while (true) {
            System.out.println("Az alábbi menüpontok közül válassz!\n" +
                    "(0) Kilépés\n" +
                    "(1) Gomba létrehozása és inicializálása\n" +
                    "(2) Gombafonal növesztése\n" +
                    "(3) Spóraszórás egy tektonra\n" +
                    "(4) Rovar mozgatása egyik tektonról másikra\n" +
                    "(5) Rovar spóra elfogyasztása\n" +
                    "(6) Rovar gombafonal vágása\n" +
                    "(7) Tekton törés és kapcsolatok frissítése");

            if (!scanner.hasNextLine()) {
                System.out.println(">Bemenet hiányzik! Program vége.");
                break;
            }
            try {
                valasztas = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(">Nem érvényes számformátum!\n");
                continue;
            }


            switch (valasztas) {
                case 0:
                    scanner.close();
                    return;
                case 1:
                    clearScreen();
                    Tekton t1 = new TobbFonalTekton(0, 0, 0);
                    Gombatest g1 = new Gombatest();
                    Gomba gomba1 = new Gomba(t1);
                    gomba1.setGombatest(g1);
                    break;
                case 2:
                    clearScreen();
                    Tekton t2_1 = new TobbFonalTekton(0, 0, 0);
                    Tekton t2_2 = new TobbFonalTekton(1, 1, 1);
                    Gombatest gt1 = new Gombatest();
                    Gomba g2_1 = new Gomba(t2_1);
                    g2_1.setGombatest(gt1);
                    Gombasz gombasz = new Gombasz(0, 10, 0);
                    gombasz.getGombak().add(g2_1);
                    g2_1.fonalNovesztes(t2_1, t2_2);
                    break;
                case 3:
                    clearScreen();
                    Tekton t3_1 = new TobbFonalTekton(0, 0, 0);
                    Tekton t3_2 = new TobbFonalTekton(1, 1, 1);Gombatest gt3_1 = new Gombatest();
                    Gomba g3_1 = new Gomba(t3_1);
                    g3_1.setGombatest(gt3_1);
                    Gombasz gombasz3 = new Gombasz(0, 10, 0);
                    gombasz3.getGombak().add(g3_1);
                    gombasz3.sporaLoves(t3_2);
                    break;
                case 4:
                    clearScreen();
                    Tekton t2 = new TobbFonalTekton(1, 0, 0);
                    Tekton t3 = new TobbFonalTekton(2, 0, 0);
                    Rovar r1 = new Rovar(t2);
                    r1.attesz(t3);
                    break;
                case 5:
                    clearScreen();
                    Rovar r2 = new Rovar(null);
                    GyorsitoSpora spora = new GyorsitoSpora(3);
                    r2.elfogyaszt(spora);
                    break;
                case 6:
                    clearScreen();
                    gameLogic gl = new gameLogic();
                    gl.jatekKezdes();
                    Rovar r3 = new Rovar(null);
                    Tekton t4 = new TobbFonalTekton(3, 0, 0);
                    Tekton t5 = new TobbFonalTekton(4, 0, 0);
                    Gombafonal fonal = new Gombafonal(t4, t5);
                    Gomba g2 = new Gomba(t4);
                    g2.addFonal(fonal);
                    gl.getGombaszok().get(0).getGombak().add(g2);
                    r3.fonalElvagas(fonal, gl.getGombaszok());
                    break;
                case 7:
                    gameLogic gl7 = new gameLogic();
                    gl7.jatekKezdes();
                    gl7.tektonTores(gl7.getMapTekton());
                    break;
                default:
                    System.out.println(">Nem létező menüpont!\n");
                    break;
            }
            System.out.flush();
        }
    }
}
