package felhasznalo;

import gomba.Gomba;
import tekton.Tekton;
import gomba.Gombafonal;


import java.util.ArrayList;
import java.util.List;

import static tesztelo.Menu.parancsFeldolgozo;

/**
 * Gombász osztály
 *
 * @class Gombasz
 *
 * @brief A gombászokat reprezentáló osztály
 *
 * @details
 * Gombász osztály, mely a felhasználó osztályból származik.
 * Az osztály feladata a gombász játékos gombáinak tárolása, azokkal műveletek végzése.
 *
 * @see felhasznalo.Felhasznalo
 * @see gomba.Gomba
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 * @author JGeri
 * @version 2.0 - Prototípus
 * @date 2025-04-23
 */
public class Gombasz extends Felhasznalo {
    /**
     * Gombász gombáit tároló lista
     * @var List<Gomba> gombak
     * @brief gombák listája
     */
    List<Gomba> gombak;
    /**
     * Gombasz konstruktora
     * @param name - a felhasználó neve
     */
    public Gombasz(int id, String name) {
        super(id, name, 6);
        gombak = new ArrayList<>();
    }
    /**
     * Megállapítja melyik gombához tartozik a kilőtt gombafonal
     * @param fonal a fonal amihez tulajdonos kerestetik
     * @return a keresett gomba / null, ha nincs találat
     */
    public Gomba melyikGomba(Gombafonal fonal) {
        System.out.println(">Gombasz->melyikGomba()");
        for (Gomba g : gombak) {
            /*
            if (g.vannekiilyenfonala(fonal)) {
                return g;
            }*/
        } return null;
    }
    /**
     * Gombafonal növesztése két tekton között
     * @param t1 egyik tekton
     * @param t2 másik tekton
     * @note Ha ugyanarra a tektonra akarja elhelyezni, akkor t2 lehet null, vagy t1=t2
     */
    /*
    public void fonalNovesztes(Tekton t1, Tekton t2) {
        System.out.println(">Gombasz->FonalNovesztes()");
        System.out.println(">A fonalnövesztést az (1). számú gomba fogja végezni");
        gombak.get(0).fonalNovesztes(t1, t2);
    }
    */

    /**
     * Spóra lövése egy kiválasztott tektonra
     * @param t1 a tekton amire a spóra kerül
     */
    /*
    public void sporaLoves(Tekton t1) {
        System.out.println(">Gombasz->sporaLoves()");
        System.out.println(">A spóralövést az (1). számú gomba gombateste fogja végezni");
        gombak.get(0).getGombatest().sporaLoves(t1);
    }
*/
    /**
     * Gombák listájának lekérdezése
     * @return gombák listája
     */
    public List<Gomba> getGombak() {
        return gombak;
    }

    public void addGomba(Gomba gomba) {
        parancsFeldolgozo.print("Gombász (" + getID() + ") gombak értéke megváltozott: ");
        for (int i = 0; i < gombak.size(); i++) parancsFeldolgozo.print("Gomba (" + gombak.get(i).getID() + ")" + (i == gombak.size() - 1 ? " " : ", "));
        parancsFeldolgozo.print("-> ");
        gombak.add(gomba);
        for (int i = 0; i < gombak.size(); i++) parancsFeldolgozo.print("Gomba (" + gombak.get(i).getID() + ")" + (i == gombak.size() - 1 ? "\n" : ", "));
    }
}
