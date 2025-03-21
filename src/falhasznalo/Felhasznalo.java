package falhasznalo;

/**
 * Felhasználó osztály, melyből a különböző felhasználók (rovarász, gombész) származnak.
 * Az osztály feladata a felhasználók pontjainak tárolása.
 */
public abstract class Felhasznalo {
    /// Pontok tárolására szolgáló tagváltozó
    protected int pontokSzama;
    /// A körben még felhasználható akciópontok számát tároló tagváltozó
    protected int hatralevoAkciopont;
    /// Az alap akciópontok számát tároló tagváltozó
    protected int alapAkciopont;
}
