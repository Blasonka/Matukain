package rovar;

import felhasznalo.Gombasz;
import felhasznalo.Rovarasz;
import gomba.Gomba;
import gomba.Gombafonal;
import jateklogika.gameLogic;
import spora.Spora;
import tekton.Tekton;
import tesztelo.ParancsFeldolgozo;

import java.util.ArrayList;
import java.util.List;

/**
 * Rovar osztály
 *
 * @class Rovar
 *
 * @brief A rovarokat reprezentáló osztály
 *
 * @details
 * Ez az osztály felelős a rovarok reprezentálásáért, azokkal kapcsolatos műveletek végzéséért.
 * Az osztály tartalmazza a tektont, ahol a rovar van, a rovar telítettségét, az elfogyasztott spórákat tároló listát, valamint a rovarokkal kapcsolatos műveleteket.
 *
 * @see felhasznalo.Rovarasz
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author JGeri
 * @version 1.0
 * @date 2025-03-22
 */
public class Rovar {
    /**
     * Rovar tartózkodási helye.
     * @var Tekton tekton
     * @brief Az a tekton, ahol a rovar tartózkodik.
     */
    private Tekton tekton;

    /**
     * Rovar telítettsége.
     * @var int telitettseg
     * @brief A rovar telítettségi szintje számokban megadva.
     */
    private int telitettseg;

    /**
     * Elfogyasztott spórák.
     * @var List<Spora> elfogyasztottSporak
     * @brief Az elfogyasztott spórákat tároló lista.
     */
    private List<Spora> elfogyasztottSporak;
    /*
    *
    *ÚJ RÉSZ!!! Spórához kelleltt!
    *
    */
    private int sebesseg;
    private boolean vaghate;
    public Tekton getTekton(){
        return tekton;
    }
    /**
     * Default constructor
     * @brief Inicializálja a kezdő tektont, valamint alapértelmezett értéket ad a telitettsegnek és az elfogyasztottSporaknak.
     * @param tekton a kezdő tekton.
     */
    public Rovar(Tekton tekton) {
        this.tekton = tekton;
        this.telitettseg = 3;
        this.elfogyasztottSporak = new ArrayList<>();
        System.out.println("Rovar.init()");
    }

    /**
     * Gombafonal elvágása
     * @brief A rovar elvágja az adott gombafonalat.
     * @param fonal a fonal, amit el kell vágni.
     */
    public void fonalElvagas(Gombafonal fonal) {
        if (!vaghate) {
            return;
        }

        Tekton t1 = fonal.getHatar1();
        Tekton t2 = fonal.getHatar2();

        if (t1 != tekton && t2 != tekton) {
            return;
        }

        fonal.elragas();
    }

    /**
     * Spóra elfogyasztása
     * @brief A rovar elfogyaszt egy adott spórát.
     * @param spora a spóra, amit elfogyaszt.
     */
    public void elfogyaszt(Spora spora){
        System.out.println("Rovar->elfogyaszt(spora)");
        telitettseg=3;
        elfogyasztottSporak.add(spora);
        System.out.println("A spóra elfogyasztva.");
    }

    /**
     * Rovar áttelepítése
     * @brief A rovar áttelepül a következő tektonra.
     * @param kovetkezo a következő tekton.
     */
    public void attesz(Tekton kovetkezo){
        System.out.println("Rovar->attesz(t1, t2)");
        System.out.print("\t");
        boolean szomszedos = tekton.szomszedosTekton(kovetkezo);
        if (szomszedos) {
            setTekton(kovetkezo);
            System.out.println("A rovar áttelepült a következő tektonra.");
        } else {
            System.out.println("A két tekton nem szomszédos, nem lehet áttenni a rovart.");
        }
    }

    /**
     * Spóra eltávolítása
     * @brief Az adott spóra eltávolítása a rovarból.
     * @param spora a spóra, amit eltávolít.
     */
    public void removeSpora(Spora spora){
        System.out.println("Rovar->removeSpora(spora)");
        elfogyasztottSporak.remove(spora);
        System.out.println("A spóra törölve lett.");
    }

    /**
     * Beállítja a rovar tektonját, amin van.
     * @param tekton a beállítandó tekton.
     */
    public void setTekton(Tekton tekton) {
        this.tekton = tekton;
    }

    /**
     * Visszaadja a rovar telítettségét
     * @return a rovar telítettsége
     */
    public int getTelitettseg() {
        return telitettseg;
    }

    /**
     * Beállítja a rovar telítettségét
     * @param telitettseg a rovar telítettsége
     */
    public void setTelitettseg(int telitettseg) {
        this.telitettseg = telitettseg;
    }
    
    public void setVaghate(boolean vaghate) {
        this.vaghate = vaghate;
    }

    public List<Spora> getElfogyasztottSporak() {
        return elfogyasztottSporak;
    }

    private void resetEffects(){
        sebesseg = 2;
        vaghate = true;
    }

    public void sporaManager(){
        resetEffects();
        for (Spora s : elfogyasztottSporak) {
            if (s.getSzamlalo() <= 0) {
                elfogyasztottSporak.remove(s);
            } else {
                s.hatasKifejtes(this);
            }
        }
    }
    private void pontLevonas(){

    }
    public void setRovarHandler(Rovarasz rsz){
        rsz.addRovar(this);
    }
}
