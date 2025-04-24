package spora;

import rovar.Rovar;
import tekton.Tekton;
import interfaces.*;

import java.util.Random;

public class OsztoSpora extends Spora implements hatasKifejtes {
    
    public OsztoSpora() {
        super(0, "oszto");
        System.out.println("\t>LassitoSpora->LassitoSpora()");
    }

    public void hatasKifejtes(Rovar rovar){
       Tekton jelenlegi = rovar.getTekton();
        Random r = new Random();
        Rovar ujRovar = new Rovar(jelenlegi, r.nextInt(1000));


        System.out.println("Új rovar jött létre a spóra hatására.");
    }
    public void torles(){

    }

}
