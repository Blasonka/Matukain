package tekton;


public class MaxEgyFonalTekton extends Tekton{
    public MaxEgyFonalTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        System.out.println("\t>MaxEgyFonalTekton->MaxEgyFonalTekton()");
    }

    @Override
    public void hatasKifejtes(){
        System.out.println("\t>MaxEgyFonalTekton->MaxEgyFonalTekton()");
    }
}