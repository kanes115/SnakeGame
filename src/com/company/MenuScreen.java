package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kanes on 19.07.16.
 */

public class MenuScreen extends JPanel implements ActionListener {

    private JButton startButt;
    private JButton endButt;

    public MenuScreen(){
        super();

        setLayout(new FlowLayout(FlowLayout.CENTER));

        startButt = new JButton("Play");
        startButt.addActionListener(this);
        add(startButt);

        endButt = new JButton("End");
        endButt.addActionListener(this);
        add(endButt);

        setVisible(true);

    }

    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == startButt){
            new GameFrame();
        }
        if(source == endButt) System.exit(0);
    }

}
