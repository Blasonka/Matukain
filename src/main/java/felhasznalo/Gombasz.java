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
        for (Gomba g : gombak) {
            /*
            if (g.vannekiilyenfonala(fonal)) {
                return g;
            }*/
        } return null;
    }

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
