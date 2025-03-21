package logika;

import spora.GyorsitoSpora;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); int valasztas = -1;
        while (valasztas != 0) {
            System.out.println("Az alábbi menüpontok közül válassz!\n" +
                    "(0) Kilépés\n" +
                    "(1) GyorsitoSpora teszt");
            if (sc.hasNextInt()) {
                valasztas = sc.nextInt();
                switch (valasztas) {
                    case 0:
                        break;
                    case 1:
                        GyorsitoSpora gsp = new GyorsitoSpora(0);
                        gsp.torles();
                        gsp.hatasKifejtes();
                        break;
                    default:
                        System.out.println(">Nem létező menüpont!\n");
                        break;
                }
            }
        }
    }
}
