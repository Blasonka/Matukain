package frontend;

import frontend.components.GamePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Fungorium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize(1280, 720);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
