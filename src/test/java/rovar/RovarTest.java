package rovar;

import felhasznalo.Gombasz;
import felhasznalo.Rovarasz;
import gomba.Gomba;
import gomba.Gombafonal;
import gomba.Gombatest;
import jateklogika.gameLogic;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import spora.OsztoSpora;
import spora.Spora;
import tekton.Tekton;
import tekton.TobbFonalTekton;

import java.util.List;

import static org.junit.Assert.*;


public class RovarTest {
    private gameLogic jatekLogika;
    private Rovarasz rovarasz;
    private Gombasz gombasz;
    private Rovar rovar;
    private Gomba gomba;
    private Gombatest gombatest;
    private Tekton t0, t1, t2;
    private Gombafonal fonal;

    @BeforeEach
    void setUp() {
        jatekLogika = new gameLogic();
        rovarasz = new Rovarasz("Gyuri");
        gombasz = new Gombasz(10);
        rovar = new Rovar();
        gomba = new Gomba();
        t0 = new Tekton(0, 1);
        t1 = new Tekton(2, 3);
        t2 = new Tekton(4, 5);
        jatekLogika.addRovarasz(rovarasz);
        jatekLogika.addGombasz(gombasz);
        rovarasz.addRovar(rovar);
        gombasz.addGomba(gomba);
    }

    @Test
    void testFonalElvagas() {
        t0.addSzomszed(t1);
        fonal = new Gombafonal(t0, t1);
        gomba.addGombafonal(fonal);
        rovar.setTekton(t0);
        rovar.fonalElvagas(fonal);
        assertTrue(fonal.isElpusztult());
        assertEquals(8, rovarasz.getHatralevoAkciopont()); // sebesseg = 2
        // Hibahely: rossz tekton
        rovar.setTekton(t2);
        fonal = new Gombafonal(t0, t1);
        gomba.addGombafonal(fonal);
        rovar.fonalElvagas(fonal);
        assertFalse(fonal.isElpusztult());
    }

    @Test
    void testSporaElfogyasztasa() {
        t0.addSzomszed(t1);
        BenitoSpora spora = new BenitoSpora(false);
        t1.addSpora(spora);
        rovar.setTekton(t1);
        rovar.elfogyaszt(spora);
        assertTrue(rovar.getElfogyasztottSporak().contains(spora));
        assertFalse(t1.getSporak().contains(spora));
        assertEquals(8, rovarasz.getHatralevoAkciopont());
        // Hibahely: null spóra
        rovar.elfogyaszt(null);
        assertEquals(8, rovarasz.getHatralevoAkciopont());
    }

    @Test
    void testRovarAttelepitese() {
        t0.addSzomszed(t1);
        fonal = new Gombafonal(t0, t1);
        gomba.addGombafonal(fonal);
        gombatest = new Gombatest(t0);
        gomba.addGombatest(gombatest);
        rovar.setTekton(t0);
        rovar.attesz(t1);
        assertEquals(t1, rovar.getTekton());
        assertEquals(8, rovarasz.getHatralevoAkciopont());
        // Hibahely: nem szomszédos tekton
        rovar.setTekton(t0);
        rovar.attesz(t2);
        assertEquals(t0, rovar.getTekton());
    }

    @Test
    void testBenitoSporaHatasKifejtese() {
        t0.addSzomszed(t1);
        fonal = new Gombafonal(t0, t1);
        gomba.addGombafonal(fonal);
        gombatest = new Gombatest(t0);
        gomba.addGombatest(gombatest);
        BenitoSpora spora = new BenitoSpora(false);
        t1.addSpora(spora);
        rovar.setTekton(t1);
        rovar.elfogyaszt(spora);
        jatekLogika.simulateRound();
        rovar.attesz(t0);
        assertEquals(t1, rovar.getTekton()); // Nem mozoghat
        assertEquals(0, rovar.getSebesseg());
        // Hibahely: mozgás mégis sikerül
        rovar.setSebesseg(2);
        rovar.attesz(t0);
        assertEquals(t0, rovar.getTekton());
    }

    @Test
    void testGyorsitoSporaHatasKifejtese() {
        t0.addSzomszed(t1);
        t1.addSzomszed(t2);
        fonal = new Gombafonal(t0, t1);
        gomba.addGombafonal(fonal);
        Gombafonal fonal2 = new Gombafonal(t1, t2);
        gomba.addGombafonal(fonal2);
        gombatest = new Gombatest(t1);
        gomba.addGombatest(gombatest);
        GyorsitoSpora spora = new GyorsitoSpora(false);
        t0.addSpora(spora);
        rovar.setTekton(t0);
        rovar.elfogyaszt(spora);
        jatekLogika.simulateRound();
        assertEquals(3, rovar.getSebesseg());
        rovar.attesz(t1);
        rovar.attesz(t0);
        rovar.attesz(t1);
        rovar.attesz(t2);
        rovar.attesz(t1);
        rovar.attesz(t0);
        assertEquals(t0, rovar.getTekton());
        assertTrue(rovarasz.getHatralevoAkciopont() >= 0);
        // Hibahely: akciópontok elfogynak
        rovar.setSebesseg(2);
        rovar.setTekton(t0);
        rovar.attesz(t1);
        rovar.attesz(t0);
        rovar.attesz(t1);
        rovar.attesz(t2);
        assertTrue(rovarasz.getHatralevoAkciopont() < 0 || rovar.getTekton() != t2);
    }

    @Test
    void testLassitoSporaHatasKifejtese() {
        t0.addSzomszed(t1);
        t1.addSzomszed(t2);
        fonal = new Gombafonal(t0, t1);
        gomba.addGombafonal(fonal);
        Gombafonal fonal2 = new Gombafonal(t1, t2);
        gomba.addGombafonal(fonal2);
        gombatest = new Gombatest(t1);
        gomba.addGombatest(gombatest);
        LassitoSpora spora = new LassitoSpora(false);
        t0.addSpora(spora);
        rovar.setTekton(t0);
        rovar.elfogyaszt(spora);
        jatekLogika.simulateRound();
        assertEquals(1, rovar.getSebesseg());
        rovar.attesz(t1);
        rovar.attesz(t0);
        rovar.attesz(t1);
        assertEquals(t0, rovar.getTekton()); // Nem tud tovább lépni
        // Hibahely: túl sok lépés
        rovar.setSebesseg(2);
        rovar.setTekton(t0);
        rovar.attesz(t1);
        rovar.attesz(t0);
        rovar.attesz(t1);
        assertEquals(t1, rovar.getTekton());
    }

    @Test
    void testVagasGatloSporaHatasKifejtese() {
        t0.addSzomszed(t1);
        fonal = new Gombafonal(t0, t1);
        gomba.addGombafonal(fonal);
        gombatest = new Gombatest(t0);
        gomba.addGombatest(gombatest);
        VagasGatloSpora spora = new VagasGatloSpora(false);
        t1.addSpora(spora);
        rovar.setTekton(t1);
        rovar.elfogyaszt(spora);
        jatekLogika.simulateRound();
        rovar.fonalElvagas(fonal);
        assertFalse(fonal.isElpusztult());
        assertFalse(rovar.isVaghate());
        // Hibahely: fonal mégis elvágódik
        rovar.setVaghate(true);
        rovar.fonalElvagas(fonal);
        assertTrue(fonal.isElpusztult());
    }

    @Test
    void testRovarokOsztodasa() {
        t0.addSzomszed(t1);
        gombatest = new Gombatest(t0);
        gomba.addGombatest(gombatest);
        RovarOsztoSpora spora = new RovarOsztoSpora(false);
        t1.addSpora(spora);
        rovar.setTekton(t1);
        rovar.elfogyaszt(spora);
        jatekLogika.simulateRound();
        assertEquals(2, rovarasz.getRovarok().size());
        assertEquals(t1, rovarasz.getRovarok().get(1).getTekton());
        // Hibahely: nem osztódik
        rovar.getElfogyasztottSporak().clear();
        rovarasz.getRovarok().clear();
        rovarasz.addRovar(rovar);
        jatekLogika.simulateRound();
        assertEquals(1, rovarasz.getRovarok().size());
    }

    @Test
    void testMozgasFonalvagasSporaLoves() {
        t0.addSzomszed(t1);
        t1.addSzomszed(t2);
        fonal = new Gombafonal(t0, t1);
        gomba.addGombafonal(fonal);
        gombatest = new Gombatest(t0);
        gomba.addGombatest(gombatest);
        rovar.setTekton(t2);
        rovar.attesz(t1);
        rovar.fonalElvagas(fonal);
        gombatest.sporaLoves(t1);
        assertEquals(t1, rovar.getTekton());
        assertTrue(fonal.isElpusztult());
        assertFalse(t1.getSporak().isEmpty());
        assertTrue(t1.getSporak().get(0) instanceof VagasGatloSpora);
        // Hibahely: mozgás, vágás vagy spóralövés sikertelen
        rovar.setTekton(t2);
        rovar.attesz(t0); // Nem szomszédos
        assertEquals(t2, rovar.getTekton());
        rovar.setVaghate(false);
        fonal = new Gombafonal(t0, t1);
        rovar.fonalElvagas(fonal);
        assertFalse(fonal.isElpusztult());
    }

}
