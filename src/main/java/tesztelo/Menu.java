package tesztelo;

import jateklogika.gameLogic;

import java.util.Scanner;

/**
 * Menu osztály
 *
 * @class Menu
 *
 * @brief A prototípus fázisbeli működést megvalósító osztály
 *
 * @details
 * Menu osztály, mely két fő opcióval rendelkezik:
 * (1) manuális mód
 * (2) teszt mód
 *
 * @see tesztelo.ParancsFeldolgozo
 *
 * @author todortoth
 * @version 1.0 - Manuális mód
 * @date 2025-04-22
 */
public class Menu {
    /**
     * A program futásához elengedhetetlen információkat tartalmazó osztály
     * @var gameLogic jatekLogika
     * @brief a rovarászok, gombászok, tektonok listáját tartalmazó elem
     */
    static gameLogic jatekLogika = new gameLogic();

    /**
     * A standard bemenetről történő beolvasásért felelős változó
     * @var Scanner beolvaso
     * @brief A módválasztáshoz (és az 1-es mód esetében a parnacsbeolvasáshoz) szükséges
     */
    static Scanner beolvaso = new Scanner(System.in);

    /**
     * A parancsok értelmezését végző osztály egy objektuma
     * @var ParancsFeldolgozo parancsFeldolgozo
     * @brief Bemeneti nyelven megadott parancsok értelmezését végzi
     */
    static ParancsFeldolgozo parancsFeldolgozo;

    public static void main(String[] args) {
        int valasztas;
        System.out.println("****MATUKAIN PROTOTÍPUS****");
        while (true) {
            System.out.println("Add meg mit szeretnél csinálni:\n" +
                    "(0) Kilépés\n" +
                    "(1) Manuális mód\n" +
                    "(2) Teszt mód");
            try {
                valasztas = Integer.parseInt(beolvaso.next());
                switch (valasztas) {
                    case 0:
                        beolvaso.close();
                        return;
                    case 1:
                        System.out.println("A program várja a parancsokat!\n" +
                                "(/help a parancsok listájáért)");
                        parancsFeldolgozo = new ParancsFeldolgozo(beolvaso, jatekLogika);
                        while (beolvaso.hasNext()) {
                            parancsFeldolgozo.interpret(beolvaso.nextLine());
                        }
                        break;
                    case 2:
                        // TODO
                        break;
                    default:
                        System.out.println("Érvénytelen választás!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nem megfelelő számformátum!");
            }
        }
    }
}
