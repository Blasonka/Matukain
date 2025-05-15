package frontend.components;

/**
 * Coordinate osztály
 *
 * @class Coordinate
 *
 * @brief A koordinátákat reprezentáló osztály
 *
 * @details
 * Osztály a koordináták tárolására
 *
 * @note Grafikus részhez készült
 *
 * @version 1.0
 * @date 2025-05-10
 */
public class Coordinate {
    /**
     * @var int x
     * @brief X koordinátát tárolja
     */
    int x;
    /**
     * @var int y
     * @brief Y koordinátát tárolja
     */
    int y;

    /**
     * Coordinate osztály konstruktora
     * @param x beállítja az X koordináta értékét
     * @param y beállítja az Y koordináta értékét
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Visszaadja az X koordinátát
     * @return X koordináta
     */
    public int getX() {
        return x;
    }

    /**
     * Visszaadja az Y koordinátát
     * @return Y koordináta
     */
    public int getY() {
        return y;
    }

    /**
     * Visszaadja, hogy a koordináták egyenlőek-e
     * @param other
     * @return true, ha egyenlőek, false, ha nem
     */
    public boolean isEqual(Coordinate other) {
        return (this.x == other.x && this.y == other.y);
    }
}
