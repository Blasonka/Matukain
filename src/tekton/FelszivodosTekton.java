package tekton;

public class FelszivodosTekton extends Tekton{
    private int szamlalo;

    public FelszivodosTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        szamlalo = 0;
        System.out.println("\t>FelszivodosTekton->FelszivodosTekton()");
    }

    @Override
    public void hatasKifejtes(){
        System.out.println("\t>FelszivodosTekton->hatasKifejtes()");
    }
}