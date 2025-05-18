package backend.interfaces;

import backend.felhasznalo.Felhasznalo;

/**
 * Játékos interface
 *
 * @brief játékosokat összefogó interface
 *
 * @details
 * A játékosok hátterét biztosító interface, megadja azokat a dolgokat,
 * amikkel minden felhasználónak rendelkezni kell
 *
 * @see Felhasznalo
 *
 * @author todortoth
 * @version 1.0
 * @date 2025-04-22
 */
public interface Jatekos {
    /**
     * Az alap akciópontok számát tároló tagváltozó
     * @var int alapAkciopont
     * @brief Az alap akciópontok száma
     */
    int alapAkciopont = 10;

    /**
     * Kezdő akciópontok számának lekérdezése
     * @return akcioPontok
     */
    int getAlapAkciopont();
    /**
     * Határolévő akciópontok számának lekérdezése
     * @return hatralevoAkciopont
     */
    int getHatralevoAkciopont();
    /**
     * Pontok számának lekérdezése
     * @return pontokSzama
     */
    int getPontokSzama();
    /**
     * Nevek számának lekérdezése
     * @return nev
     */
    String getNev();
    /**
     * Határolévő akciópontok számának beállítása
     * @param hatralevoAkcioPont beállítandó még határolévő akciópont
     */
    void setHatralevoAkciopont(int hatralevoAkcioPont);
    /**
     * Pontok számának beállítása
     * @param pontokSzama beállítandó pontok száma
     */
    void setPontokSzama(int pontokSzama);
    /**
     * Játékos nevének beállítása
     * @param nev beállítandó név
     */
    void setNev(String nev);

    default void addPontokSzama(int i){
        this.setPontokSzama(this.getPontokSzama() + i);
    }
}
