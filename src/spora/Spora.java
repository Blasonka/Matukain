package spora;

/**
 * Absztrakt osztály Sporák adatainak tárolására
 */
public abstract class Spora {
    /// Spóra élettartamát nyilvántartó számláló
    protected int szamlalo;
    /**
     * Spora osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public Spora(int sz) {
        szamlalo = sz;
        System.out.println(">Spora->Spora()");
    }
    /// Spóra törlése
    public void torles() {
        System.out.println(">Spora->torles()");
    }
    /// Spóra hatásának kifejtése, absztrakt metódus
    public abstract void hatasKifejtes();
}
