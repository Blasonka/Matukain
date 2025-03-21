public class FelszivodosTekton extends Tekton{
    private int szamlalo;

    public FelszivodosTekton(int id, int koordinataX, int koordinataY){
        szamlalo = 0;
        super(id, koordinataX, koordinataY);
        System.out.println("\t>FelszivodosTekton->FelszivodosTekton()");
    }

    @Override
    public void hatasKifejtes(){
        System.out.println("\t>FelszivodosTekton->hatasKifejtes()");
    }
}