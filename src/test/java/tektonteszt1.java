import backend.gomba.Gomba;
import backend.gomba.Gombatest;
import backend.tekton.MaxEgyFonalTekton;
import org.junit.*;

import static org.junit.Assert.*;

public class tektonteszt1 {
    /*@Test
    public void tesztProba() {
        //Arrange
        //Assert
        assertTrue(true);
    }*/

    @Test
    public void tesztSzabadTekton(){
        //Arrange
        MaxEgyFonalTekton tekton = new MaxEgyFonalTekton(1, 0, 0);
        //Assert
        assertTrue(tekton.szabadTekton());
    }

    @Test
    public void tesztNemSzabadTekton(){
        //Arrange
        MaxEgyFonalTekton tekton = new MaxEgyFonalTekton(1, 0, 0);
        tekton.gombaNovesztes();
        //Assert
        assertFalse(tekton.szabadTekton());
    }

    @Test
    public void tesztAddSpora(){
        //Arrange
        MaxEgyFonalTekton tekton1 = new MaxEgyFonalTekton(1, 0, 0);
        MaxEgyFonalTekton tekton2 = new MaxEgyFonalTekton(2, 1, 1);
        tekton1.gombaNovesztes();
        tekton1.getGomba().setGombatest(new Gombatest());
        tekton1.getGomba().getGombatest().sporaLoves(tekton2);
        //Assert
        assertEquals(1, tekton2.getSporak().size());
    }

    @Test
    public void tesztRemoveSpora(){
        //Arrange
        MaxEgyFonalTekton tekton1 = new MaxEgyFonalTekton(1, 0, 0);
        MaxEgyFonalTekton tekton2 = new MaxEgyFonalTekton(2, 1, 1);
        Gomba gomba = new Gomba(tekton1);
        tekton1.gombaNovesztes();
        tekton1.getGomba().setGombatest(new Gombatest());
        tekton1.getGomba().getGombatest().sporaLoves(tekton2);
        tekton1.getGomba().getGombatest().sporaLoves(tekton2);
        tekton1.getGomba().getGombatest().sporaLoves(tekton2);
        tekton2.removeSpora(tekton2.getSporak().get(2));
        //Assert
        assertEquals(2, tekton2.getSporak().size());
    }
}

