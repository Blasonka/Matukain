package tekton;

/**
 * Osztály olyan Tektonok adatainak tárolására, melyeken adott idő után felszívódnak a gombafonalak
 */
public class FelszivodosTekton extends Tekton{
    /// Számláló, mely alapján meghatározható, hogy mikor szívódjon fel a gombafonal
    private int szamlalo;

    /**
     * FelszivodosTekton osztály konstruktora, a számlálót 0-ra állítja
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public FelszivodosTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        szamlalo = 0;
        System.out.println("Létrejött egy FelszivodosTekton");
    }

    /**
     * Kifejti a Tekton hatását, azaz egy gombafonal adott idő után felszívódik
     */
    @Override
    public void hatasKifejtes(){
        System.out.println("FelszivodosTekton hatás kifejtve, azaz egy gombafonal adott idő után felszívódik");
    }
}