package frontend.components;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean tekton;
    private int sporeCount = 0;

    public void incrementSporeCount() {
        if (sporeCount < 3) {
            sporeCount++;
        }
    }

    public void decrementSporeCount() {
        if (sporeCount > 0) {
            sporeCount--;
        }
    }

    public int getSporeCount() {
        return sporeCount;
    }
}
