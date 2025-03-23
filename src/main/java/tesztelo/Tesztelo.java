package tesztelo;

import felhasznalo.Gombasz;
import spora.GyorsitoSpora;

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
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); int valasztas = -1;
        while (valasztas != 0) {
            System.out.println("Az alábbi menüpontok közül válassz!\n" +
                    "(0) Kilépés\n" +
                    "(1) Gomba létrehozása és inicializálása\n" +
                    "(2) Gombafonal növesztése\n" +
                    "(3) Spóraszórás egy tektonról másikra\n" +
                    "(4) Rovar mozgatása egyik tektonról másikra\n" +
                    "(5) Rovar spóra elfogyasztása\n" +
                    "(6) Rovar gombafonal vágása\n" +
                    "(7) Tekton törés és kapcsolatok frissítése\n");
            if (sc.hasNextInt()) {
                valasztas = sc.nextInt();
                switch (valasztas) {
                    case 0:
                        break;
                    case 1:
                        break;
                    default:
                        System.out.println(">Nem létező menüpont!\n");
                        break;
                }
            }
        }
    }
}
