package spora;

import rovar.Rovar;
import tekton.Tekton;
import interfészek.*;

public class OsztoSpora extends Spora implements hatasKifejtes {
    
    public OsztoSpora(int sz, hatasKifejtes h, String s) {
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
