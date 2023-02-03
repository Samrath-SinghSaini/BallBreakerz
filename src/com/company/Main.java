package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Gameplay gameplay = new Gameplay();
        JFrame mainFrame = new JFrame();
        mainFrame.setBounds(10, 10, 700, 600);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.add(gameplay);

    }
}
