public abstract class Tekton {
    private int id;
    private int koordinataX;
    private int koordinataY;
    private List<Spora> sporak;

    public Tekton(int id, int koordinataX, int koordinataY) {
        this.id = id;
        this.koordinataX = koordinataX;
        this.koordinataY = koordinataY;
        sporak = new ArrayList<>();
        System.out.println("Tekton->Tekton()");
    }

    public void tores(){
        System.out.println("Tekton->tores()");
    }

    public void addSpora(Spora spora){
        System.out.println("Tekton->addSpora(Spora)");
    }

    public void removeSpora(Spora spora){
        System.out.println("Tekton->removeSpora(Spora)");
    }

    public void szomszedosTekton(){
        System.out.println("Tekton->szomszedosTekton()");
    }

    public boolean szabadTekton(){
        System.out.println("Tekton->szabadTekton()");
        return true;
    }

    public void gombaNovesztes(){
        System.out.println("Tekton->gombaNovesztes()");
    }

    public abstract void hatasKifejtes();
}