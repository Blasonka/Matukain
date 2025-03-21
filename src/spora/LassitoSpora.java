package spora;

/**
 * Osztály lassító hatású spórák tárolására
 * Őse az absztrak Spora osztály
 */
public class LassitoSpora extends Spora {
    /**
     * Lassító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public LassitoSpora(int sz) {
        super(sz);
        System.out.println("\t>LassitoSpora->LassitoSpora()");
    }
    /// LassítóSpora törlése
    public void torles() {
        System.out.println("\t\t>LassitoSpora->torles()");
    }
    /// LassítóSpora osztály rovarra nézett lassító hatásának kifejtése
    public void hatasKifejtes() {
        System.out.println("\t\t>LassitoSpora->hatasKifejtes()");
    }
}
