package gomba;

import tekton.Tekton;

/**
 * A gombafonalakat reprezentáló osztály
 */
public class Gombafonal {
    private Tekton hatar1;
    private Tekton hatar2;

    /**
     * Létrehoz egy gombafonalat a megadott két tekton között
     * @param hatar1 az egyik végpontja a gombafonalnak
     * @param hatar2 a másik végpontja a gombafonalnak
     */
    public Gombafonal(Tekton hatar1, Tekton hatar2) {
        this.hatar1 = hatar1;
        this.hatar2 = hatar2;
        System.out.println("Gombafonal létrejött");
    }
    /**
     * Visszaadja a gombafonal egyik végpontját
     * @return a gombafonal egyik végpontja
     */
    public Tekton hatar1() {
        return hatar1;
    }

    /**
     * Visszaadja a gombafonal másik végpontját
     * @return a gombafonal másik végpontja
     */
    public Tekton hatar2() {
        return hatar2;
    }

}
