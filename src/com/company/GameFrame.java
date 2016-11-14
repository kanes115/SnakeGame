package com.company;

import javax.swing.*;

/**
 * Created by kanes on 19.07.16.
 */
public class GameFrame extends JFrame {

    public GameFrame(){
        super("Snake");

        JPanel gamePanel = new GamePanel();
        add(gamePanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);

    }
}
