package tesztelo;

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
 * @version 1.0
 * @date 2025-04-22
 */
public class Menu {
    static ParancsFeldolgozo parancsFeldolgozo = new ParancsFeldolgozo();
    public static void main(String[] args) {
        parancsFeldolgozo.print("asd");
    }
}
