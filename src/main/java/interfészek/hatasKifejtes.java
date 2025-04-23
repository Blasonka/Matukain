package interfészek;
import rovar.Rovar;

/**
 * Interfész a rovar hatás kifejtéséhez.
 */
public interface hatasKifejtes {

    public int hatas1 =0;
    public int hatas2 =1;
    public int hatas3 =2;
    public int hatas4 =3;
    public int hatas5 =4;


    /**
     * Applies the effect of the spore on the given Rovar.
     * @param rovar amire kifejti majd a hatását.
     */
    void hatasKifejtes(Rovar rovar);
}
