package rovar;

import gomba.Gombafonal;
import spora.Spora;
import tekton.Tekton;

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

    /**
     * Default constructor
     * @brief Inicializálja a kezdő tektont, valamint alapértelmezett értéket ad a telitettsegnek és az elfogyasztottSporaknak.
     * @param tekton a kezdő tekton.
     */
    public Rovar(Tekton tekton) {
        this.tekton = tekton;
        this.telitettseg = 3;
        this.elfogyasztottSporak = new ArrayList<>();
    }

    public void rovarMozgas(Tekton tekton){}

    public void fonalElvagas(Gombafonal fonal){}

    public void elfogyaszt(Spora spora){}

    public void attesz(Tekton t1, Tekton t2){}
}
