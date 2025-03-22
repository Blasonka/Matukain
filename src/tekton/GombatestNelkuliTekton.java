package tekton;

class GombatestNelkuliTekton extends Tekton {
    public GombatestNelkuliTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        System.out.println("\t>GombatestNelkuliTekton->GombatestNelkuliTekton()");
    }

    @Override
    public void hatasKifejtes(){
        System.out.println("\t>GombatestNelkuliTekton->GombatestNelkuliTekton()");
    }
}