package falhasznalo;

import spora.Spora;
import tekton.Tekton;

/**
 * Rovarasz osztály, mely a felhasználó osztályból származik.
 * Az osztály feladata a rovarász játékos rovarjainak tárolása, azokkal műveletek végzése.
 */
public class Rovarasz extends Felhasznalo {
    /// A rovarász rovarait tároló lista
    //List<Rovar> rovarak;

    /**
     * Rovar mozgatása
     * @param t2 a tekton amire mozogni fog a rovar
     */
    public void rovarMozgas(Tekton t2) {
        System.out.println(">Rovarasz->rovarMozgas(t2)");
    }

    /**
     * A rovar egy tetszőlegesen választott spórát elfogyaszt
     * @param sp1 az elfogyasztandó spóra
     */
    public void elfogyaszt(Spora sp1) {
        System.out.println(">Rovarasz->elfogyaszt(sp1)");
    }

    /**
     * A rovar egy tetszőlegesen választott fonalat megsemmisít
     */
    public void fonalElvagas() {
        System.out.println(">Rovarasz->fonalElvagas()");
    }
}
