package spora;
import rovar.Rovar;

/**
 * Interfész a rovar hatás kifejtéséhez.
 */
public interface HatasKifejtes {
    /**
     * Applies the effect of the spore on the given Rovar.
     * @param rovar amire kifejti majd a hatását.
     */
    void hatasKifejtes(Rovar rovar);
}