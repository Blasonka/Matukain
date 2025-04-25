 package jateklogika;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.io.Serializable;

import felhasznalo.Felhasznalo;
import felhasznalo.Gombasz;
import felhasznalo.Rovarasz;
import gomba.Gomba;
import rovar.Rovar;
import tekton.FelszivodosTekton;
import tekton.FonalMegtartoTekton;
import tekton.MaxEgyFonalTekton;
import tekton.Tekton;
import tekton.TobbFonalTekton;
import tekton.GombatestNelkuliTekton;
import gomba.Gombafonal;

/**
 * gameLogic
 *
 * @class gameLogic
 *
 * @brief A szkeleton fázis inicializálását megvalósító osztály
 *
 * @details
 * Inicializáló osztály, mely 1 fő feladattal rendelkezik:
 * Példányosítja mind a tektonokat, játékosokat, rovarakat és a gombákat is
 *
 * @see gameLogic
 * 
 *
 * @note Szkeleton fázishoz tartozik, a metódusok még hiányosak lehetnek
 *
 * @author Bence338
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public class gameLogic implements Serializable {
    int korSzamlalo;
    List<Tekton> map;
    public static List<Gombasz> gombaszok;
    public static List<Rovarasz> rovarasz;
    public List<Gombasz> jatekosok = new ArrayList<>();
    public List<Rovarasz> rovarjatekosok = new ArrayList<>();
    public boolean veletlenEsemenyekEngedelyezve = true;
    public double toresEsely;
    public List<Gombafonal> fonalak = new ArrayList<>();
    public int fonalelet;
    /**
     * Default konstruktor
     */
    public gameLogic()
    {
        this.map=new ArrayList<>();
    }
    /**
      * Sima getterek és setterek a későbbi feladatokhoz
      */
      public int getKorszamlalo()
      {
        return korSzamlalo;
      }
      public void setKorszamlalo(int szam)
      {
        korSzamlalo=szam;
      }
      public void setMap(List<Tekton> m) 
      {
        map=m;
      }
      public List<Tekton> getMapTekton() 
      {
        return  map;
      }
      public List<Gombasz> getJatekosok() {
        return jatekosok;
      }
      public List<Rovarasz> getRovarjatekosok() { return rovarjatekosok;}
      public List<Gombafonal> getFonalak() {return fonalak; }
    public void setFonalakElete(int fonalelet) {
        this.fonalelet = fonalelet;
        for (Gombafonal fonal : fonalak) {
            fonal.setPusztulasSzamlalo(fonalelet);
        }
    }



    /**
     * Gombászok lekérdezése
     * @details statikus metódus, mely így elérhető lesz a Rovarasz osztály számára,
     * aki ezáltal a Rovar osztályban megtalálhatja a megfelelő gombafonalat
     * @return gombaszok
     */
    public static List<Gombasz> getGombaszok() {
        return gombaszok;
    }
    /**
     * Meghívja a tektonnak a tores() függvényét, ha a tektonnak ketté kell törnie
     * @param t adja meg a Tektonok listáját
     */
    public void tektonTores(List<Tekton> t) {
        Random rand = new Random();

        
        for (int i = 0; i < t.size(); i++) {
            Tekton current = t.get(i);

            
            if (rand.nextDouble() < toresEsely) {

                int ujId = current.getID() +500;
                int ujX = current.getKoordinataX() -1;
                int ujY = current.getKoordinataY();

                Tekton ujTekton = current.klonoz(ujId, ujX, ujY);
                current.tores();


                if (ujTekton != null) {

                    current.addSzomszed(ujTekton);
                    ujTekton.addSzomszed(current);


                    t.add(ujTekton);
                    System.out.println("Új tekton létrehozva ID: " + ujTekton.getID() + " hol (" + ujTekton.getKoordinataX() + ", " + ujTekton.getKoordinataY() + ")");
                }
            }
        }
    }

    public void csokkentFonalakElete() {
        Iterator<Gombafonal> iterator = fonalak.iterator();
        while (iterator.hasNext()) {
            Gombafonal fonal = iterator.next();
            boolean elpusztult = fonal.csokkentPusztulasSzamlalo();
            if (elpusztult) {
                System.out.println("Gombafonal ID " + fonal.getId() + " elpusztult.");
                iterator.remove(); // Remove threads that have expired
            }
        }
    }

    public void gombaszKor() {
      System.out.println("--- Gombászok köre ---");
      for (Gombasz g : gombaszok) {
          g.setHatralevoAkciopont(5);
  
          while (g.getHatralevoAkciopont() > 0) {
              
              if (g.getHatralevoAkciopont() >= 1) {
                  
                  //g.sporaLoves(null);
                  g.setHatralevoAkciopont(g.getHatralevoAkciopont() - 1);
                  System.out.println(g.getNev() + " szórt egy spórát.");
              }
  
              
              if (g.getHatralevoAkciopont() >= 1) {
                  Gomba gomba = new Gomba(0);
                  g.getGombak().add(gomba);
                  g.setHatralevoAkciopont(g.getHatralevoAkciopont() - 1);
                  System.out.println(g.getNev() + " gombatestet növesztett.");
              }
  
              
              if (g.getHatralevoAkciopont() >= 1 ) {
                  //.fonalNovesztes(null,null);
                  g.setHatralevoAkciopont(g.getHatralevoAkciopont() - 1);
                  System.out.println(g.getNev() + " gombafonalat növesztett.");
              }
  
              
              break;
          }
      }
  }
  

  public void rovaraszKor()
  {


  }

    public void kor() {
      System.out.println("Kör kezdete: #" + korSzamlalo);

        gombaszKor();        // 1. Gombászok körei
        rovaraszKor();       // 2. Rovarászok körei
        tektonTores(map);    // 3. Tekton törések
        korSzamlalo++;       // 4. Körszámláló növelése

        System.out.println("Kör vége. Új körszám: " + korSzamlalo);

    }

    /**
     * Az aktuális játékosnak ad pontot a tevékenysége alapján
     * @param f-től kapja meg az aktuális játékost
     */
    public void pontOsztas(Felhasznalo f) 
    {
        System.out.println("Pont hozzáadva. gameLogic->Felhasznalo");
    }
    /**
     * Inicializálja a játékteret, és véletlenszerűen sorrended
     * rak a felhsznaálók között
     *
     */
    public void jatekKezdes() 
    {
        List<Gombasz> gombaszok2 = new ArrayList<>();
        List<Rovarasz> rovaraszok = new ArrayList<>();
        Random rand = new Random();
        Tekton[] tektonok = new Tekton[3];

        for (int i = 0; i < 3; i++) 
        {
        int randomIndex = rand.nextInt(5); 
        switch (randomIndex) {
            case 0 -> tektonok[i] = new FelszivodosTekton(1, 2, 2);
            case 1 -> tektonok[i] = new GombatestNelkuliTekton(2, 3, 3);
            case 2 -> tektonok[i] = new MaxEgyFonalTekton(3, 4, 4);
            case 3 -> tektonok[i] = new TobbFonalTekton(4, 5, 5);
            case 4 -> tektonok[i] = new FonalMegtartoTekton(5,6, 6);
        }
        map.add(tektonok[i]);
        }
        Gombasz g1= new Gombasz("Gombasz1");
        Gombasz g2= new Gombasz("Gombasz2");
        Rovarasz r1 = new Rovarasz("Rovarasz1");
        Rovarasz r2 = new Rovarasz("Rovarasz2");
        Rovar rovar1 = new Rovar(null, 1);
        Rovar rovar2 = new Rovar(null, 2);
        Gomba gomba1 = new Gomba(0);
        Gomba gomba2 = new Gomba(1);
        korSzamlalo=0;

        gombaszok2.add(g1);
        gombaszok2.add(g2);
        rovaraszok.add(r1);
        rovaraszok.add(r2);

        gombaszok = gombaszok2;
            
        System.out.println("Játék inicializálva. Játékosok, tektonok, rovarok, gombák létrehozva.");
        
    }
    
    /**
     * A végén kihírdeti a helyezéseket 
     * @param f a felhasznalok listáját kapja meg
     */
    public void jatekOsszegzes(List<Felhasznalo> f) 
    {
        System.out.println("gameLogic->gameLogic");
    }

    public void addRovarasz(Rovarasz r)
    {
        rovarasz.add(r);
    }
    public void addGombasz(Gombasz g)
    {
        gombaszok.add(g);
    }

    /**
     *
     * Új rész!!!!!!!!!
     *
     */
    public void simulateRound(){

    }
}
