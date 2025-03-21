package spora;

/**
 * Osztály vágás gátló hatású spórák tárolására
 * Őse az absztrak Spora osztály
 */
public class VagasGatloSpora extends Spora {
    /**
     * VagasGatloSpora osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public VagasGatloSpora(int sz) {
        super(sz);
        System.out.println(">VagasGatloSpora->VagasGatloSpora()");
    }
    /// VagasGatloSpora törlése
    public void torles() {
        System.out.println(">VagasGatloSpora->torles()");
    }
    /// VagasGatloSpora osztály rovarra nézett vágás gátló hatásának kifejtése
    public void hatasKifejtes() {
        System.out.println(">VagasGatloSpora->hatasKifejtes()");
    }
}
