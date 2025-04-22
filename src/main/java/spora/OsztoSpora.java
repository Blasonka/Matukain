package spora;

import rovar.Rovar;
import tekton.Tekton;

public class OsztoSpora extends Spora implements HatasKifejtes {
    
    public OsztoSpora(int sz, HatasKifejtes h, String s) {
        super(sz, h, s);
        System.out.println("\t>LassitoSpora->LassitoSpora()");
    }

    
    @Override
    public void hatasKifejtes(Rovar rovar){
       Tekton jelenlegi = rovar.getTekton();
        Rovar ujRovar = new Rovar(jelenlegi);

        
        jelenlegi.addRovar(ujRovar); 

        System.out.println("Új rovar jött létre a spóra hatására.");
    }


}
