package backend.tekton;

import backend.gomba.Gomba;
import backend.gomba.Gombafonal;
import backend.spora.Spora;

import java.util.ArrayList;
import java.util.List;

import static backend.tesztelo.Menu.parancsFeldolgozo;

/**
 * Tekton osztály
 *
 * @class Tekton
 *
 * @brief A Tektonokat reprezentáló osztály
 *
 * @details
 * Absztrakt osztály Tektonok adatainak tárolására
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author Monostori
 * @version 1.0
 * @date 2025-03-21
 */
public abstract class Tekton {
    /**
     * @var int id
     * @brief Tekton azonosítóját tárolja
     */
    private int id;

    /**
     * @var int koordinataX
     * @brief Tekton X koordinátáját tárolja
     */
    private int koordinataX;

    /**
     * @var int koordinataY
     * @brief Tekton Y koordinátáját tárolja
     */
    private int koordinataY;

    /**
     * @var List<Spora> sporak
     * @brief Tektonon lévő spórákat tárolja egy listában
     */
    private List<Spora> sporak;

    /**
    private List<Rovar> rovarok;
    */

    private List<Tekton> szomszedok;

    /**
     * @var Gomba gomba
     * @brief Tektonon lévő gombát tárolja
     */
    private Gomba gomba;



    /**
     * Tekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public Tekton(int id, int koordinataX, int koordinataY) {
        this.id = id;
        this.koordinataX = koordinataX;
        this.koordinataY = koordinataY;
        sporak = new ArrayList<>();
        gomba = null;
        szomszedok = new ArrayList<>();
    }

    public abstract Tekton klonoz(int ujID, int ujX, int ujY);

    /**
     *
     * Új rész!!!!!!!!!
     *
     */
    public void addSzomszed(Tekton szomszed) {
        szomszedok.add(szomszed);
    }
    public List<Tekton> getSzomszedok() {
        return szomszedok;
    }

    /**
     * Visszaadja a tekton ID-ját
     * @return id, a tekton ID-ja
     */
    public int getID() {
        return id;
    }

    /**
     * Visszaadja a tekton X koordinátáját
     * @return koordinataX, a tekton X koordinátája
     */
    public int getKoordinataX() {
        return koordinataX;
    }

    /**
     * Visszaadja a tekton Y koordinátáját
     * @return koordinataY, a tekton Y koordinátája
     */
    public int getKoordinataY() {
        return koordinataY;
    }

    /**
     * Visszaadja a tektonon lévő spórákat
     * @return sporak, a tektonon lévő spórák listája
     */
    public List<Spora> getSporak() {
        return sporak;
    }

    /**
     * Visszaadja a tektonon lévő gombát, vagy null-t, ha nincs rajta gomba
     * @return gomba, a tektonon lévő gomba
     */
    public Gomba getGomba() {
        return gomba;
    }

    public void setGomba(Gomba gomba) {
        this.gomba = gomba;
    }

    /**
     * Tekton törése
     */
    public void tores(){
        if (gomba != null) {
            for (Gombafonal fonal : gomba.getGombafonalak()) {
                if (fonal.getHatar1() == this || fonal.getHatar2() == this){
                    gomba.removeFonal(fonal);
                }
            }
        }
        System.out.println("A tekton törölve lett");
    }

    /**
     * Spóra hozzáadása
     * @param spora spórát hozzáadja a tektonhoz
     */
    public void addSpora(Spora spora){
        // Ellenőrizzük, hogy a parancsFeldolgozo nem null-e, csak akkor írjunk ki, ha van
        if (parancsFeldolgozo != null) {
            parancsFeldolgozo.print("Tekton (" + this.getID() + ") sporak értéke megváltozott: ");
            for (int i=0; i<sporak.size()-1; i++){
                parancsFeldolgozo.print(sporak.get(i).getID() + ", ");
            }
            if (!sporak.isEmpty())
                parancsFeldolgozo.print(sporak.get(sporak.size()-1).getID() + " ->");
        }
        sporak.add(spora);
        if (parancsFeldolgozo != null) {
            for (int i=0; i<sporak.size()-1; i++){
                parancsFeldolgozo.print(" " + sporak.get(i).getID() + ",");
            }
            if (!sporak.isEmpty())
                parancsFeldolgozo.print(" " + sporak.get(sporak.get(sporak.size()-1).getID()));
        }
    }

    /**
     * Spóra törlése
     * @param spora törli az adott spórát
     */
    public void removeSpora(Spora spora){
        spora.torles();
        sporak.remove(spora);
        System.out.println("Az adott spóra törölve lett a tektonról");
    }

    /**
     * Meghatározza a szomszédos tektonokat
     * @param szomszed a tekton, amiről megmondja, hogy szomszédosak-e
     * @return true, ha szomszédosak, false egyébként
     */
    public boolean szomszedosTekton(Tekton szomszed){
        System.out.println("Tekton->szomszedosTekton(szomszed)");
        System.out.println("A két tekton szomszédos?(Y/N)");
        String valasz = ""; // = Tesztelo.scanner.nextLine();
        if (valasz.equals("Y")) {
            System.out.println("A két tekton szomszédos");
            return true;
        } else {
            System.out.println("A két tekton nem szomszédos");
            return false;
        }

    }

    /**
     * Két tekton szomszédosságának eldöntése: akkor szomszédosak, ha nincs köztük másik tekton
     */
    public boolean szomszedosTekton(Tekton masik, List<Tekton> osszesTekton) {
        // Két tekton akkor szomszédos, ha nincs köztük másik tekton a vonal mentén
        // Egyszerűsített: ha a két tekton közötti szakaszra nem esik rá egy harmadik tekton (két végpont kivételével)
        int x1 = this.koordinataX;
        int y1 = this.koordinataY;
        int x2 = masik.koordinataX;
        int y2 = masik.koordinataY;
        for (Tekton t : osszesTekton) {
            if (t == this || t == masik) continue;
            int tx = t.koordinataX;
            int ty = t.koordinataY;
            // Ellenőrizzük, hogy a t pont rajta van-e a két tekton közötti szakaszon
            // Feltételezzük, hogy egész koordináták, és a pont a szakaszon van, ha kollineáris és a szakasz végpontjai között van
            int dx1 = x2 - x1;
            int dy1 = y2 - y1;
            int dx2 = tx - x1;
            int dy2 = ty - y1;
            int cross = dx1 * dy2 - dy1 * dx2;
            if (cross != 0) continue; // Nem kollineáris
            // Ellenőrizzük, hogy a t pont a szakasz végpontjai között van-e
            if (Math.min(x1, x2) < tx && tx < Math.max(x1, x2) && Math.min(y1, y2) < ty && ty < Math.max(y1, y2)) {
                return false; // Van közte másik tekton
            }
        }
        return true;
    }

    /**
     * Visszaadja, hogy az adott tekton szabad-e
     * @return true, ha szabad, false egyébként
     */
    public boolean szabadTekton(){
        if (gomba != null){
            System.out.println("A tekton nem szabad");
            return false;
        }
        else {
            System.out.println("A tekton szabad");
            return true;
        }
    }

    /**
     * Gombát növeszt a tektonra
     */
    public void gombaNovesztes(){
        gomba = new Gomba(0);
        System.out.println("Új gomba nőtt erre a tektonra");
    }

    /**
     * Tekton hatásának kifejtése, absztrakt metódus
     * @param gomba, a gomba, amelyre a hatás kifejtésre kerül
     */
    public abstract void hatasKifejtes(Gomba gomba);

    public void setKoordinataX(int x) {
        this.koordinataX =x;
    }

    public void setKoordinataY(int y) {
        this.koordinataY = y;
    }
}

