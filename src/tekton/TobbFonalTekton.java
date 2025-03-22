package tekton;

public class TobbFonalTekton extends Tekton{
    public TobbFonalTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        System.out.println("\t>TobbFonalTekton->TobbFonalTekton()");
    }

    @Override
    public void hatasKifejtes(){
        System.out.println("\t>TobbFonalTekton->hatasKifejtes()");
    }
}