package com.company;

import javax.swing.*;

/**
 * Created by kanes on 19.07.16.
 */
public class MenuScreenFrame extends JFrame {

    public MenuScreenFrame(){
        super("Snake");

        JPanel menu = new MenuScreen();
        add(menu);

        setLocation(960, 540);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
