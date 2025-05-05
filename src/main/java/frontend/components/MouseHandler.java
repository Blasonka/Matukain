package frontend.components;

import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private GamePanel gamePanel;
    Coordinate coordinate = new Coordinate(0, 0);
    boolean clicked = false;

    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void mouseClicked(java.awt.event.MouseEvent e) {
        // Handle mouse click event
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
        coordinate.x = e.getX();
        coordinate.y = e.getY();
        clicked= true;
    }
    public void returnFalse() {
        clicked= false;
    }


    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // Handle mouse pressed event
        coordinate.x = e.getX();
        coordinate.y = e.getY();
        clicked= true;
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // Handle mouse released event
        coordinate.x = e.getX();
        coordinate.y = e.getY();
        clicked= true;
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }
}
