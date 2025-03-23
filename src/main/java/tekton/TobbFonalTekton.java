package tekton;

/**
 * Osztály olyan Tektonok adatainak tárolására, melyeken több gomba fonalai is nőhetnek
 */

public class TobbFonalTekton extends Tekton{
    /**
     * TobbFonalTekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public TobbFonalTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        System.out.println("Létrejött egy TobbFonalTekton");
    }

    /**
     * Kifejti a Tekton hatását, azaz több gomba fonalai is nőhetnek rajta
     */
    @Override
    public void hatasKifejtes(){
        System.out.println("TobbFonalTekton hatás kifejtve, azaz több gomba fonalai is nőhetnek rajta");
    }
}