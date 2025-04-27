package spora;

import rovar.Rovar;
import tekton.Tekton;
import interfaces.*;

import java.util.Random;

import static tesztelo.Menu.parancsFeldolgozo;

public class OsztoSpora extends Spora implements hatasKifejtes {
    
    public OsztoSpora(int sz, int id) {
        super(sz, "oszto", id);
    }

    public void hatasKifejtes(Rovar rovar){
       Tekton jelenlegi = rovar.getTekton();
        Random r = new Random();
        Rovar ujRovar = new Rovar(jelenlegi, r.nextInt(1000));

        parancsFeldolgozo.print("Spóra (" + getID() + ") hatására Rovar (" + rovar.getID() + ") osztódott: Rovar (" + rovar.getID() + ") + Rovar (" + (rovar.getID() + 1) + ")\n");
    }
    public void torles(){

    }

}
