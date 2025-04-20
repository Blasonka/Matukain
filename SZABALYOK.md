# Szabályok

Ez a dokumentum foglalja össze azokat a szabályokat, amelyekhez kérünk benneteket tartsátok magatokat.

## ChatGPT használata

Mivel a plágium gyanúkat próbáljuk minél távolabb tartani magunktól, ezért szeretnénk, ha a ChatGPT-t és egyéb AI-okat csak az alábbi feladatok megkönnyítésére használnánk:
- Debug
    - Ha valami hibát nem találtok meg magatoktól, segítségül hívhatjátok, **azonban** az általa adott outputot (*javított kódot*) 1/1 bemásolni tilos;
- Ötletek keresése
    - Dokumentum vázlat;
    - Egy programozási probléma megoldása;

ChatGPT által generált bármilyen tartalmat **SZIGORÚAN TILOS** beilleszteni!

## Stack Overflow és egyéb oldalakon található kód

Csak akkor másoljátok be, ha forrásmegjelölést használtok kommentben.

## Commitok

- Sűrűn commitoljatok
- Lehetőség szerint minden funkciót külön branchre nyomjunk
- Értelmes commit nevek és leírások kötelezőek
- Aki a Main-re merge-öl **seggbe-kuki**

## Kommentek

Lehetőség szerint amit tudtok a kódban azt kommenteljétek, hogy mindenki megértse.
Java-ban, hogy a dokumentációt a Doxygen letudja generálni, ilyen formátumú kommenteket várunk el:
```sh
/**
 * Ez az osztály a(z) ...-ra szolgál.
 *
 * @author Jónás Gergely Péter
 * @since 2025-02-017
 */
public class Osztalyneve {
    /**
     * Ez a metódus betölti a járatokat.
     * @param emberek emberek listája
     * @return a járatok listája
     * @throws IOException
     */
         public static List<Jarat> jaratBetoltes(List<Ember> ember) throws IOException {
```
