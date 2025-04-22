package tesztelo;

/**
 * Parancsfeldolgozó osztály
 *
 * @class PranacsFeldolgozo
 *
 * @brief A prototípus fázisbeli működés parancsfeldolgozó részét megvalósító osztály
 *
 * @details
 * Parancsefeldolgozó osztály, mely a kapott parancsokat végrehajtva obejktumokat hoz létre,
 * módosítja azok állapotát. A parancsok
 *
 * @see tesztelo.ParancsFeldolgozo
 *
 * @author todortoth
 * @version 1.0
 * @date 2025-04-22
 */
public class ParancsFeldolgozo {
    StringBuffer kimenet = new StringBuffer();

    public void print(String ki) {
        kimenet.append(ki);
        System.out.println(ki);
    }
}
