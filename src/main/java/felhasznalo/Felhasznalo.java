package felhasznalo;

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
 * @see felhasznalo.Rovarasz
 * @see felhasznalo.Gombasz
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public abstract class Felhasznalo {
    /**
     * A felhasználó pontjainak számát tároló tagváltozó
     * @var int pontokSzama
     * @brief A felhasználó pontjainak száma
     */
    protected int pontokSzama;
    /**
     * A körben még felhasználható akciópontok számát tároló tagváltozó
     * @var int hatralevoAkciopont
     * @brief A még felhasználható akciópontok száma
     */
    protected int hatralevoAkciopont;
    /**
     * Az alap akciópontok számát tároló tagváltozó
     * @var int alapAkciopont
     * @brief Az alap akciópontok száma
     */
    protected int alapAkciopont;
    /**
     * Felhasználó konstruktora
     * @param p a felhasználó pontjainak száma
     * @param a a felhasználó alap akciópontjainak száma
     * @param h a felhasználó körben felhasználható akciópontjainak száma
     */
    public Felhasznalo(int p, int a, int h) {
        pontokSzama = p;
        alapAkciopont = a;
        hatralevoAkciopont = h;
        System.out.println(">Felhasznalo->Felhasznalo()");
    }
    /**
     * Kezdő akciópontok számának lekérdezése
     * @return akcioPontok
     */
    public int getAlapAkciopont() {
        System.out.println("\t>Felhasznalo->getAlapAkciopont(): " + alapAkciopont);
        return alapAkciopont;
    }
    /**
     * Határolévő akciópontok számának lekérdezése
     * @return hatralevoAkciopont
     */
    public int getHatralevoAkciopont() {
        System.out.println("\t>Felhasznalo->getHatralevoAkciopont(): " + hatralevoAkciopont);
        return hatralevoAkciopont;
    }
    /**
     * Pontok számának lekérdezése
     * @return pontokSzama
     */
    public int getPontokSzama() {
        System.out.println("\t>Felhasznalo->getPontokSzama(): " + pontokSzama);
        return pontokSzama;
    }
    /**
     * Alap akciópontok számának beállítása
     * @param alapAkciopont beállítandó alap akciópont
     */
    public void setAlapAkciopont(int alapAkciopont) {
        this.alapAkciopont = alapAkciopont;
        System.out.println("\t>Felhasznalo->setAlapAkciopont(" + alapAkciopont + ")");
    }
    /**
     * Határolévő akciópontok számának beállítása
     * @param hatralevoAkciopont beállítandó még határolévő akciópont
     */
    public void setHatralevoAkciopont(int hatralevoAkciopont) {
        this.hatralevoAkciopont = hatralevoAkciopont;
        System.out.println("\t>Felhasznalo->setHatralevoAkciopont(" + hatralevoAkciopont + ")");
    }
    /**
     * Pontok számának beállítása
     * @param pontokSzama beállítandó pontok száma
     */
    public void setPontokSzama(int pontokSzama) {
        this.pontokSzama = pontokSzama;
        System.out.println("\t>Felhasznalo->setPontokSzama(" + pontokSzama + ")");
    }
}
