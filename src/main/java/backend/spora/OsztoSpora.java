package backend.spora;

import backend.interfaces.hatasKifejtes;
import backend.rovar.Rovar;
import backend.tekton.Tekton;

import java.util.Random;

public class OsztoSpora extends Spora implements hatasKifejtes {
    
    public OsztoSpora() {
        super(0, "oszto");
    }

    public void hatasKifejtes(Rovar rovar){
       Tekton jelenlegi = rovar.getTekton();
        Random r = new Random();
        Rovar ujRovar = new Rovar(jelenlegi, r.nextInt(1000));

    }
    public void torles(){

    }

}
