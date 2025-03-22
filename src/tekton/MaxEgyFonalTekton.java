package tekton;

/**
 * Osztály olyan Tektonok adatainak tárolására, melyeken maximum egy fonal nőhet
 */
public class MaxEgyFonalTekton extends Tekton{
    /**
     * MaxEgyFonalTekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public MaxEgyFonalTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        System.out.println("\t>MaxEgyFonalTekton->MaxEgyFonalTekton()");
    }

    /// Kifejti a Tekton hatását, azaz maximum egy fonal nőhet rajta
    @Override
    public void hatasKifejtes(){
        System.out.println("\t>MaxEgyFonalTekton->MaxEgyFonalTekton()");
    }
}