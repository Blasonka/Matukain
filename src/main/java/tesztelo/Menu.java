package tesztelo;

import jateklogika.gameLogic;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Menu osztály
 *
 * @class Menu
 *
 * @brief A prototípus fázisbeli működést megvalósító osztály
 *
 * @details
 * Menu osztály, mely két fő opcióval rendelkezik:
 * (1) manuális mód
 * (2) teszt mód
 *
 * @see tesztelo.ParancsFeldolgozo
 *
 * @author todortoth
 * @version 1.0 - Manuális mód
 * @date 2025-04-22
 */
public class Menu {
    /**
     * A program futásához elengedhetetlen információkat tartalmazó osztály
     * @var gameLogic jatekLogika
     * @brief a rovarászok, gombászok, tektonok listáját tartalmazó elem
     */
    public static gameLogic jatekLogika = new gameLogic();

    /**
     * A standard bemenetről történő beolvasásért felelős változó
     * @var Scanner beolvaso
     * @brief A módválasztáshoz (és az 1-es mód esetében a parnacsbeolvasáshoz) szükséges
     */
    static Scanner beolvaso = new Scanner(System.in);

    /**
     * A parancsok értelmezését végző osztály egy objektuma
     * @var ParancsFeldolgozo parancsFeldolgozo
     * @brief Bemeneti nyelven megadott parancsok értelmezését végzi
     */
    public static ParancsFeldolgozo parancsFeldolgozo;

    public static void main(String[] args) {
        int valasztas;
        System.out.println("****MATUKAIN PROTOTÍPUS****");
        while (true) {
            System.out.println("Add meg mit szeretnél csinálni:\n" +
                    "(0) Kilépés\n" +
                    "(1) Manuális mód\n" +
                    "(2) Teszt mód");
            try {
                valasztas = Integer.parseInt(beolvaso.nextLine());
                switch (valasztas) {
                    case 0:
                        beolvaso.close();
                        return;
                    case 1:
                        System.out.println("A program várja a parancsokat!\n" +
                                "(/help a parancsok listájáért)");
                        parancsFeldolgozo = new ParancsFeldolgozo(jatekLogika);
                        while (beolvaso.hasNext()) {
                            parancsFeldolgozo.interpret(beolvaso.nextLine());
                        }
                        break;
                    case 2:
                        System.out.println("Add meg a futtatni kívánt teszt mappa elérési útját! (pl: C:\\Matukain\\test1)");
                        String mappa = beolvaso.nextLine();
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(mappa + "\\input.txt"));
                            parancsFeldolgozo = new ParancsFeldolgozo(jatekLogika);
                            while (reader.ready()) {
                                parancsFeldolgozo.interpret(reader.readLine());
                            } parancsFeldolgozo.interpret("/save " + mappa + "\\output.txt" + " -k");
                            // Az fc windows-1250 kódolást szereti, viszont a fájlok alapvetően utf-8-ban vannak, szóval ezt meg kell kerülni különben olvashatatlan lesz
                            Path tmp_exp = Paths.get(mappa + "\\tmp_expected.txt");
                            Path tmp_out = Paths.get(mappa + "\\tmp_output.txt");
                            List<String> lines = Files.readAllLines(Paths.get(mappa + "\\expected.txt"), StandardCharsets.UTF_8);
                            Files.write(tmp_exp, lines, Charset.forName("windows-1250"));
                            List<String> lines2 = Files.readAllLines(Paths.get(mappa + "\\output.txt"), StandardCharsets.UTF_8);
                            Files.write(tmp_out, lines2, Charset.forName("windows-1250"));
                            // fc meghívása
                            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "fc", mappa + "\\output.txt", mappa + "\\expected.txt");
                            Process p = pb.start();
                            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                            String kimenet;
                            while ((kimenet = stdInput.readLine()) != null) {
                                System.out.println(kimenet);
                            } p.waitFor();
                            // Töröljük a létrejövő fájlt windows-1250 fájlt
                            if (Files.exists(tmp_exp)) Files.delete(tmp_exp);
                            if (Files.exists(tmp_out)) Files.delete(tmp_out);
                        } catch (FileNotFoundException e) {
                            System.err.println("A megadott mappában nem található a keresett fájl: " + mappa);
                        } catch (IOException e) {
                            System.err.println("Beolvasási hiba történt.");
                        } catch (InterruptedException e) {
                            System.err.println("Hiba a fájlok összehasonlításában.");
                        }
                        break;
                    default:
                        System.out.println("Érvénytelen választás!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nem megfelelő számformátum!");
            }
        }
    }
}
