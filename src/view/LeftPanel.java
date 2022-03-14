package view;

import control.Actions;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class LeftPanel extends JFrame {


    // 1) left Panel
    private JPanel leftPanel;

    // 2) Invoices Table
    private JTable invoicesTable;
    private DefaultTableModel invoicesTblModel;
    private final String[] cols = {"No." ,"Date" , "Customer" , "Total"};
    private final int numRows = 3;

    // 3) Buttons
    private JButton create;
    private JButton delete;

    // 4) Frame
    private final MyFrame mf;


    public LeftPanel(MyFrame mf){
        this.mf = mf;
    }

    public JTable getInvoicesTable() {
        return invoicesTable;
    }

    public DefaultTableModel getInvoicesTblModel() {
        return invoicesTblModel;
    }


    public JPanel Left_Panel(){
        // 1) left panel
        leftPanel = new JPanel();
        leftPanel.setSize(500,700);


        add(leftPanel);

        // 2) Invoices Table


        invoicesTblModel = new DefaultTableModel(numRows, cols.length);
        invoicesTblModel.setColumnIdentifiers(cols);
        invoicesTable = new JTable(invoicesTblModel);
        JScrollPane tblScrollPane = new JScrollPane(invoicesTable);
        tblScrollPane.setPreferredSize(new Dimension(470, 455));
        invoicesTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        Border titledBorder = BorderFactory.createTitledBorder("Invoice Table");
        tblScrollPane.setBorder(titledBorder);

        leftPanel.add(tblScrollPane);

        // 3) Buttons
        create = new JButton("Create New Invoice");
        leftPanel.add(create);
        create.addActionListener(e -> new Actions(mf).createNewInvoice());

        delete = new JButton("Delete Invoice");
        leftPanel.add(delete);
        delete.addActionListener(e -> new Actions(mf).deleteInvoice());

        return leftPanel;
    }
}
