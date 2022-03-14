package view;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MyFrame m = new MyFrame();
        m.setSize(1050 , 800);
        m.setLocation(500 , 200);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setResizable(false);
        m.setVisible(true);

    }
}
