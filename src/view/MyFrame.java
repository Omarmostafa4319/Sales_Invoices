package view;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{

    private final Menu m;
    private final LeftPanel lp;
    private final RightPanel rp;


    public LeftPanel getLp() {
        return lp;
    }


    public RightPanel getRp() {
        return rp;
    }

    public MyFrame(){
        super("Design Preview");

        setLayout(new GridLayout(1,2));

        // File Menu
        m = new Menu(this);
        setJMenuBar(m.fullMenu());

        // 1) left panel
        lp = new LeftPanel(this);
        add(lp.Left_Panel());

        // 1) Right panel
        rp = new RightPanel(this);
        rp.setPreferredSize(new Dimension());
        add(rp.Right_Panel());

    }
}
