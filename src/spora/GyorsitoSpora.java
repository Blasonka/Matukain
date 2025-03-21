package spora;

/**
 * Osztály gyorsító hatású spórák tárolására
 * Őse az absztrak Spora osztály
 */
public class GyorsitoSpora extends Spora {
    /**
     * Gyorsító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public GyorsitoSpora(int sz) {
        super(sz);
        System.out.println("\t>GyorsitoSpora->GyorsitoSpora()");
    }
    /// GyorsitoSpora törlése
    public void torles() {
        System.out.println("\t\t>GyorsitoSpora->torles()");
    }
    /// GyorsitoSpora osztály rovarra nézett gyorsító hatásának kifejtése
    public void hatasKifejtes() {
        System.out.println("\t\t>GyorsitoSpora->hatasKifejtes()");
    }
}
