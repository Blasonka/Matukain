package tekton;

import felhasznalo.Gombasz;
import rovar.*;
import spora.*;
import gomba.*;

import java.util.ArrayList;
import java.util.List;

import tesztelo.ParancsFeldolgozo;

import static tesztelo.Menu.jatekLogika;
import static tesztelo.Menu.parancsFeldolgozo;

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
 * @note Prototípus állapotban van, a grafikus részek nincsenek implementálva.
 *
 * @author Monostori
 * @version 2.0
 * @date 2025-04-27
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

    /**
     * Klonozza a Tekton objektumot
     */
    public abstract Tekton klonoz(int ujID, int ujX, int ujY);

    /**
     * Szomszédos tekton hozzáadása
     * @param szomszed tekton, amit hozzáadunk a szomszédok listájához
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
            List<Gombafonal> fonalak = new ArrayList<>();
            for (Gombafonal fonal : gomba.getGombafonalak()) {
                if (fonal.getHatar1() == this || fonal.getHatar2() == this) {
                    fonalak.add(fonal);
                }
            } for (Gombafonal fonal : fonalak) {
                gomba.removeFonal(fonal);
                parancsFeldolgozo.print("Tektontörés hatására Fonal (" + fonal.getID() + ") eltűnt\n");
            }
        } int maxID = 0;
        for (Tekton tekton : jatekLogika.getMapTekton()) {
            if (tekton.getID() > maxID) {
                maxID = tekton.getID();
            }
        };
        parancsFeldolgozo.print("Tekton (" + id + ") eltört: Tekton (" + id + ") + Tekton (" + (maxID + 1) + ")\n");
    }

    /**
     * Spóra hozzáadása
     * @param spora spórát hozzáadja a tektonhoz
     */
    public void addSpora(Spora spora){
        parancsFeldolgozo.print("Tekton (" + this.getID() + ") sporak értéke megváltozott: ");
        for (int i=0; i<sporak.size(); i++){
            parancsFeldolgozo.print("Spóra (" + sporak.get(i).getID() + ")" + (i == sporak.size() - 1 ? " " : ", "));
        }
        parancsFeldolgozo.print("->");
        sporak.add(spora);
        for (int i=0; i<sporak.size(); i++){
            parancsFeldolgozo.print(" Spóra (" + sporak.get(i).getID() + ")" + (i == sporak.size() - 1 ? "\n" : ","));
        }
    }

   

    /**
     * Spóra törlése
     * @param spora törli az adott spórát
     */
    public void removeSpora(Spora spora){
        spora.torles();
        sporak.remove(spora);
    }

    /**
     * Meghatározza a szomszédos tektonokat
     * @param szomszed a tekton, amiről megmondja, hogy szomszédosak-e
     * @return true, ha szomszédosak, false egyébként
     */
    public boolean szomszedosTekton(Tekton szomszed){
        double tavolsag = Math.sqrt(Math.pow(this.koordinataX - szomszed.getKoordinataX(), 2) + Math.pow(this.koordinataY - szomszed.getKoordinataY(), 2));
        if (tavolsag < 5){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Visszaadja, hogy az adott tekton szabad-e
     * @return true, ha szabad, false egyébként
     */
    public boolean szabadTekton(){
        if (gomba != null){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Gombát növeszt a tektonra
     */
    public void gombaNovesztes(int id, Gombasz gombasz){
        sporak.clear();
        gomba = new Gomba(id);
        parancsFeldolgozo.print("Gomba (" +  id + ") elhelyezve Tekton (" + getID() + ") Gombász (" + gombasz.getID() + ") által\n");
        gombasz.addGomba(gomba);
    }

    /**
     * Tekton hatásának kifejtése, absztrakt metódus
     * @param gomba, a gomba, amelyre a hatás kifejtésre kerül
     */
    public abstract void hatasKifejtes(Gomba gomba);
}