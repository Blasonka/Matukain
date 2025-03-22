package tekton;

/**
 * Osztály olyan Tektonok adatainak tárolására, melyeken nem nőhet gombatest
 */
class GombatestNelkuliTekton extends Tekton {
    /**
     * GombatestNelkulTekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public GombatestNelkuliTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        System.out.println("\t>GombatestNelkuliTekton->GombatestNelkuliTekton()");
    }

    /// Kifejti a Tekton hatását, azaz nem nőhet rajta gombatest
    @Override
    public void hatasKifejtes(){
        System.out.println("\t>GombatestNelkuliTekton->GombatestNelkuliTekton()");
    }
}