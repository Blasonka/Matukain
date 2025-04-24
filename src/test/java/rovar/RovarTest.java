package rovar;

import felhasznalo.Gombasz;
import felhasznalo.Rovarasz;
import gomba.Gomba;
import gomba.Gombafonal;
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
    private Rovar rovar;
    private Tekton rovarTekton;
    private Tekton tekton1;
    private Tekton tekton2;
    private Gombafonal gombafonal;
    private Gomba gomba;
    private Gombasz gombasz;
    private gameLogic jatekLogika;
    private Spora spora;
    private OsztoSpora rovarOsztoSpora;
    private Rovarasz rovarasz;

    @BeforeEach
    public void setUp() {
        rovarTekton = new TobbFonalTekton(1, 0, 0);
        rovar = new Rovar(rovarTekton);
        tekton1 = new TobbFonalTekton(2, 1, 1);
        tekton2 = new TobbFonalTekton(3, 2, 2);
        gombafonal = new Gombafonal(rovarTekton, tekton2);
        gomba = new Gomba(tekton1);
        gombasz = new Gombasz(10, 0, 0);
        jatekLogika = new gameLogic();
        spora = new OsztoSpora(false);
        rovarOsztoSpora = new OsztoSpora(false);
        rovarasz = new Rovarasz(10, 0, 0);

        rovar.setVaghate(true);
        rovar.setTekton(rovarTekton);
        rovar.setSebesseg(2);
        rovar.setRovarHandler(rovarasz);
    }

    // fonalElvagas tesztek
    @Test
    public void fonalElvagas_vaghateFalse_doesNothing() {
        rovar.setVaghate(false);
        rovar.fonalElvagas(gombafonal, jatekLogika);
        assertFalse(gombafonal.isElpusztult());
        assertEquals(10, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void fonalElvagas_tektonNotMatching_doesNothing() {
        gombafonal = new Gombafonal(tekton1, tekton2);
        rovar.fonalElvagas(gombafonal, jatekLogika);
        assertFalse(gombafonal.isElpusztult());
        assertEquals(10, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void fonalElvagas_tektonMatching_fonalNotFound_callsPontLevonas() {
        jatekLogika.addGombasz(gombasz);
        gombasz.addGomba(gomba);
        rovar.fonalElvagas(gombafonal);
        assertFalse(gombafonal.isElpusztult());
        assertEquals(8, rovarasz.getHatralevoAkciopont()); // sebesseg = 2
    }

    @Test
    public void fonalElvagas_tektonMatching_fonalFound_callsElpusztulAndPontLevonas() {
        jatekLogika.addGombasz(gombasz);
        gombasz.addGomba(gomba);
        gomba.addGombafonal(gombafonal);
        rovar.fonalElvagas(gombafonal);
        assertTrue(gombafonal.isElpusztult());
        assertEquals(8, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void fonalElvagas_emptyGombaszList_callsPontLevonas() {
        rovar.fonalElvagas(gombafonal);
        assertFalse(gombafonal.isElpusztult());
        assertEquals(8, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void fonalElvagas_nullFonal_doesNothing() {
        rovar.fonalElvagas(null, jatekLogika);
        assertEquals(10, rovarasz.getHatralevoAkciopont());
    }

    // elfogyaszt tesztek
    @Test
    public void elfogyaszt_normalSpora_addsToListAndCallsPontLevonas() {
        rovar.elfogyaszt(spora);
        assertTrue(rovar.getElfogyasztottSporak().contains(spora));
        assertEquals(8, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void elfogyaszt_rovarOsztoSpora_doesNotAddToList_callsPontLevonas() {
        rovar.elfogyaszt(rovarOsztoSpora);
        assertFalse(rovar.getElfogyasztottSporak().contains(rovarOsztoSpora));
        assertEquals(8, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void elfogyaszt_nullSpora_doesNothing() {
        rovar.elfogyaszt(null);
        assertTrue(rovar.getElfogyasztottSporak().isEmpty());
        assertEquals(10, rovarasz.getHatralevoAkciopont());
    }

    // attesz tesztek
    @Test
    public void attesz_szomszedosTekton_updatesTektonAndCallsPontLevonas() {
        rovarTekton.addSzomszed(tekton1);
        rovar.attesz(tekton1);
        assertEquals(tekton1, rovar.getTekton());
        assertEquals(8, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void attesz_nemSzomszedosTekton_doesNothing() {
        rovar.attesz(tekton1);
        assertEquals(rovarTekton, rovar.getTekton());
        assertEquals(10, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void attesz_nullTekton_doesNothing() {
        rovar.attesz(null);
        assertEquals(rovarTekton, rovar.getTekton());
        assertEquals(10, rovarasz.getHatralevoAkciopont());
    }

    @Test
    public void attesz_nullRovarTekton_doesNothing() {
        rovar.setTekton(null);
        rovar.attesz(tekton1);
        assertNull(rovar.getTekton());
        assertEquals(10, rovarasz.getHatralevoAkciopont());
    }

    // sporaManager tesztek
    @Test
    public void sporaManager_emptyList_doesNothing() {
        rovar.sporaManager();
        assertTrue(rovar.getElfogyasztottSporak().isEmpty());
    }

    @Test
    public void sporaManager_expiredSpora_removesSpora() {
        Spora lejartSpora = new Spora(true);
        rovar.getElfogyasztottSporak().add(lejartSpora);
        rovar.sporaManager();
        assertFalse(rovar.getElfogyasztottSporak().contains(lejartSpora));
    }

    @Test
    public void sporaManager_validSpora_callsHatasKifejtes() {
        rovar.getElfogyasztottSporak().add(spora);
        rovar.sporaManager();
        assertTrue(rovar.getElfogyasztottSporak().contains(spora));
        // hatasKifejtes dummy, nincs mit ellenőrizni
    }

    @Test
    public void sporaManager_mixedSporas_handlesCorrectly() {
        Spora lejartSpora = new Spora(true);
        rovar.getElfogyasztottSporak().add(lejartSpora);
        rovar.getElfogyasztottSporak().add(spora);
        rovar.sporaManager();
        assertFalse(rovar.getElfogyasztottSporak().contains(lejartSpora));
        assertTrue(rovar.getElfogyasztottSporak().contains(spora));
    }
}
