package felhasznalo;

import interfaces.Jatekos;

/**
 * Felhasznalo osztály
 *
 * @class Felhasznalo
 *
 * @brief A felhasználókat reprezentáló osztály
 *
 * @details
 * Felhasználó osztály, melyből a különböző felhasználók (rovarász, gombész) származnak.
 * Az osztály feladata a felhasználók pontjainak tárolása.
 *
 * @see interfaces.Jatekos
 * @see felhasznalo.Rovarasz
 * @see felhasznalo.Gombasz
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 * @version 2.0 - Prototípus, interface bevezetése
 * @date 2025-04-22
 */
public abstract class Felhasznalo implements Jatekos {
    /**
     * A játékos egyedi azonosítója
     * @var int ID
     * @brief A felhasználót egyértelműen azonosító szám
     */
    protected int ID;
    /**
     * A felhasználó pontjainak számát tároló tagváltozó
     * @var int pontokSzama
     * @brief A felhasználó pontjainak száma
     */
    int pontokSzama;
    /**
     * A körben még felhasználható akciópontok számát tároló tagváltozó
     * @var int hatralevoAkciopont
     * @brief A még felhasználható akciópontok száma
     */
    int hatralevoAkciopont;
    /**
     * A felhasználó nevét tároló változó
     * @var String nev
     * @brief az a név, amellyel a felhasználó megjelenik a játékban
     */
    String nev;

    /**
     * Felhasználó konstruktora
     * @param id a felhasználó azonosítója
     * @param n a felhasználó neve
     * @param p a felhasználó pontjainak száma
     */
    public Felhasznalo(int id, String n, int p) {
        ID = id;
        nev = n;
        pontokSzama = p;
    }

    /**
     * Felhasználói azonosító lekérdezése
     * @return ID
     */
    public int getID() {return ID;}
    /**
     * Kezdő akciópontok számának lekérdezése
     * @return akcioPontok
     */
    public int getAlapAkciopont() {
        return alapAkciopont;
    }
    /**
     * Határolévő akciópontok számának lekérdezése
     * @return hatralevoAkciopont
     */
    public int getHatralevoAkciopont() {
        return hatralevoAkciopont;
    }
    /**
     * Pontok számának lekérdezése
     * @return pontokSzama
     */
    public int getPontokSzama() {
        return pontokSzama;
    }
    /**
     * Nevek számának lekérdezése
     * @return nev
     */
    public String getNev() {
        return nev;
    }
    /**
     * Határolévő akciópontok számának beállítása
     * @param hatralevoAkciopont beállítandó még határolévő akciópont
     */
    public void setHatralevoAkciopont(int hatralevoAkciopont) {
        this.hatralevoAkciopont = hatralevoAkciopont;
    }
    /**
     * Pontok számának beállítása
     * @param pontokSzama beállítandó pontok száma
     */
    public void setPontokSzama(int pontokSzama) {
        this.pontokSzama = pontokSzama;
    }
    /**
     * Játékos nevének beállítása
     * @param n beállítandó név
     */
    public void setNev(String n) {
        nev = n;
    }
}
