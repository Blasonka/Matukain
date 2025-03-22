package gomba;

import tekton.Tekton;

import java.util.List;

/**
 * A gombát reprezentáló osztály
 */
public class Gomba {

    private List<Gombafonal> gombafonalak;
    private Gombatest gombatest;
    private Tekton tekton;

    /**
     * Gomba konstruktora
     * @param gombafonalak a gomba fonalai
     * @param gombatest a gomba testét reprezentáló objektum
     * @param tekton a tekton, amin a gomba található
     */
    public Gomba(List<Gombafonal> gombafonalak, Gombatest gombatest, Tekton tekton) {
        this.gombafonalak = gombafonalak;
        this.gombatest = gombatest;
        this.tekton = tekton;
    }

    /**
     * Visszaadja, hogy két tekton közt van-e fonal
     * @return true, ha van fonal, false egyébként
     */
    public boolean fonalOsszekoti(Tekton tekton1, Tekton tekton2) {
        System.out.println("Gomba->fonalOsszekoti()");
        return true;
    }

    /**
     * Fonal hozzáadása
     * @param gombafonal a hozzáadandó fonal
     */
    public void addFonal(Gombafonal gombafonal) {
        gombafonalak.add(gombafonal);
        System.out.println("Gomba->addFonal()");
    }


}
