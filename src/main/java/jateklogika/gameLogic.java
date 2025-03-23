package jateklogika;
import java.util.List;
import felhasznalo.Felhasznalo;
import tekton.Tekton;
/**
 * Osztály a játék szabályainak megvalósításához
 * Szüksége van a Tekton,felhasználó osztályokra
 * 
 *  */ 
public class gameLogic {
    int korSzamlalo;
    List<Tekton> map; 
    /**
     * Default konstruktor
     */
    gameLogic() {}
    /**
     * Meghívja a tektonnak a tores() függvényét, ha a tektonnak ketté kell törnie
     * @param t adja meg a Tektonok listáját
     */
    public void tektonTores(List<Tekton> t)
    {
      for(int i=0; i< t.size();i++)
      {
        System.out.println("Jateklogika->Tekton->tores()");
      }

    }
    /**
     * Az aktuális játékosnak ad pontot a tevékenysége alapján
     * @param f-től kapja meg az aktuális játékost
     */
    public void pontOsztas(Felhasznalo f) 
    {
        System.out.println("Pont hozzáadva. gameLogic->Felhasznalo");
    }
    /**
     * Inicializálja a játékteret, és véletlenszerűen sorrended
     * rak a felhsznaálók között
     * @param g adja meg a gombász felhasználókat
     * @param r adja meg a rovarász felhasználókat
     */
    public void jatekKezdes(List<Felhasznalo> g,List<Felhasznalo> r) 
    {
        System.out.println("gameLogic->1 játékos");
    }
    
    /**
     * A végén kihírdeti a helyezéseket 
     * @param f a felhasznalok listáját kapja meg
     */
    public void jatekOsszegzes(List<Felhasznalo> f) 
    {
        System.out.println("gameLogic->gameLogic");
    }

}
