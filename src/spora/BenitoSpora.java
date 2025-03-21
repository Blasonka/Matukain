package spora;

/**
 * Osztály béníto hatású spórák tárolására
 * Őse az absztrak Spora osztály
 */
public class BenitoSpora extends Spora {
    /**
     * BenitoSpora osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public BenitoSpora(int sz) {
        super(sz);
        System.out.println("\t>BenitoSpora->BenitoSpora()");
    }
    /// BenitoSpora törlése
    public void torles() {
        System.out.println("\t\t>BenitoSpora->torles()");
    }
    /// BenitoSpora osztály rovarra nézett bénító hatásának kifejtése
    public void hatasKifejtes() {
        System.out.println("\t\t>BenitoSpora->hatasKifejtes()");
    }
}
