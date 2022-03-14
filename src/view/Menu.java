package view;

import control.Actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Menu extends JFrame{

    // File Menu
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadItem;
    private JMenuItem saveItem;


    private final MyFrame mf;


    public Menu(MyFrame mf){
        this.mf = mf;
    }

    public JMenuBar fullMenu() {
        // File Menu
        loadItem = new JMenuItem("Load File", 'L');
        loadItem.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Actions(mf).loadFile();
            }
        });

        saveItem = new JMenuItem("Save File", 'S');
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Actions(mf).saveFile();
            }
        });

        fileMenu = new JMenu("File");
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);

        return menuBar;
    }

}
