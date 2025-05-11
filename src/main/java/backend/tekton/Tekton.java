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
        System.out.println("Tekton->Tekton()");
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
        for (Gombafonal fonal : gomba.getGombafonalak()) {
            if (fonal.getHatar1() == this || fonal.getHatar2() == this){
                gomba.removeFonal(fonal);
            }
        }
        System.out.println("A tekton törölve lett");
    }

    /**
     * Spóra hozzáadása
     * @param spora spórát hozzáadja a tektonhoz
     */
    public void addSpora(Spora spora){
        parancsFeldolgozo.print("Tekton (" + this.getID() + ") sporak értéke megváltozott: ");
        for (int i=0; i<sporak.size()-1; i++){
            parancsFeldolgozo.print(sporak.get(i).getID() + ", ");
        }
        parancsFeldolgozo.print(sporak.get(sporak.size()-1).getID() + " ->");
        sporak.add(spora);
        for (int i=0; i<sporak.size()-1; i++){
            parancsFeldolgozo.print(" " + sporak.get(i).getID() + ",");
        }
        parancsFeldolgozo.print(" " + sporak.get(sporak.get(sporak.size()-1).getID()));
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