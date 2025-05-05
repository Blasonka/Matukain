package frontend.components;

import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private GamePanel gamePanel;
    Coordinate coordinate = new Coordinate(0, 0);

    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void mouseClicked(java.awt.event.MouseEvent e) {
        // Handle mouse click event
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
        coordinate.x = e.getX();
        coordinate.y = e.getY();
    }



    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // Handle mouse pressed event
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // Handle mouse released event
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // Handle mouse entered event
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // Handle mouse exited event
    }
}
