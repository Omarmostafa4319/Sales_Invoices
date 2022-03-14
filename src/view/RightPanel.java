package view;

import control.Actions;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RightPanel extends JFrame {

    public static int numOfCustomer = 0;
    // 1) Right panel
    private JPanel rightPanel;

    // 2) Data Table
    private JPanel dataTable;
    private JLabel invoiceNumber;
    private JLabel invoiceTotal;
    private JTextField invoiceDate;
    private JTextField customerName;

    // 3) Items Table
    private JTable itemsTable;
    private DefaultTableModel itemsTableModel;
    private final String[] cols = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
    private final int numRows = 3;

    // 4) Buttons
    private JButton save;
    private JButton cancel;

    // 5)
    private final MyFrame mf;

    public RightPanel(MyFrame mf) {
        this.mf = mf;
    }

    public JTable getItemsTable() {
        return itemsTable;
    }

    public DefaultTableModel getItemsTableModel() {
        return itemsTableModel;
    }

    public JLabel getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(JLabel invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public JLabel getInvoiceTotal() {
        return invoiceTotal;
    }


    public JTextField getInvoiceDate() {
        return invoiceDate;
    }

    public JTextField getCustomerName() {
        return customerName;
    }


    public JPanel Right_Panel() {
        // 1) Right panel
        rightPanel = new JPanel();
        rightPanel.setSize(500, 700);
        add(rightPanel);

        // 2) data Table
        dataTable = new JPanel();
        dataTable.setLayout(new BoxLayout(dataTable, BoxLayout.Y_AXIS));
        rightPanel.add(dataTable);


        // 1 )
        JPanel newPanel1 = new JPanel();
        invoiceNumber = new JLabel("0");
        newPanel1.add(new JLabel("Invoice Number      "));
        newPanel1.add(invoiceNumber);
        newPanel1.setLayout(new BoxLayout(newPanel1, BoxLayout.X_AXIS));
        newPanel1.setPreferredSize(new Dimension(490, 25));
        newPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        dataTable.add(newPanel1);

        // 2)
        JPanel newPanel2 = new JPanel();
        invoiceDate = new JTextField(31);
        newPanel2.add(new JLabel("Invoice Date             "));
        newPanel2.add(invoiceDate);
        newPanel2.setLayout(new BoxLayout(newPanel2, BoxLayout.X_AXIS));
        newPanel2.setPreferredSize(new Dimension(490,25));
        newPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

        dataTable.add(newPanel2);

        // 3)
        JPanel newPanel3 = new JPanel();
        customerName = new JTextField(31);
        newPanel3.add(new JLabel("Customer Name     "));
        newPanel3.add(customerName);
        newPanel3.setLayout(new BoxLayout(newPanel3, BoxLayout.X_AXIS));
        newPanel3.setPreferredSize(new Dimension(490,25));
        newPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        dataTable.add(newPanel3);

        // 4)
        JPanel newPanel4 = new JPanel();
        invoiceTotal = new JLabel("0");
        newPanel4.add(new JLabel("Invoice Total            "));
        newPanel4.add(invoiceTotal);
        newPanel4.setLayout(new BoxLayout(newPanel4, BoxLayout.X_AXIS));
        newPanel4.setPreferredSize(new Dimension(490,25));
        newPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        dataTable.add(newPanel4);



        // 3) Item Table

        JPanel newPanel5 = new JPanel();
        Border titledBorder = BorderFactory.createTitledBorder("Invoice Items");
        newPanel5.setBorder(titledBorder);

        itemsTableModel = new DefaultTableModel(numRows, cols.length);
        itemsTableModel.setColumnIdentifiers(cols);
        itemsTable = new JTable(itemsTableModel);
        JScrollPane tblScrollPane = new JScrollPane(itemsTable);
        tblScrollPane.setPreferredSize(new Dimension(470, 455));
        itemsTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        itemsTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 3 || e.getType() == 1) {
                    int row = e.getLastRow();
                    String itemPrice = (String) itemsTableModel.getValueAt(row, 2);
                    String itemCount = (String) itemsTableModel.getValueAt(row, 3);
                    try {
                        itemsTableModel.setValueAt(Integer.parseInt(itemPrice) * Integer.parseInt(itemCount), row, 4);
                    } catch (Exception ex) {
                        ex.getStackTrace();
                    }
                } else if ((e.getLastRow() + 1) == itemsTableModel.getRowCount() && e.getType() == 0) {
                    itemsTableModel.addRow(new Object[]{null, null, null, null, null});

                } else if (e.getColumn() == 4) {
                    String numOFCustomer = mf.getRp().getInvoiceNumber().getText();
                    int numbsOfCustomer = Integer.parseInt(numOFCustomer);
                    if (Integer.parseInt((String) itemsTable.getValueAt(e.getLastRow(), 0)) == numbsOfCustomer) {

                        int invTblTotal = (int) itemsTableModel.getValueAt(e.getLastRow(), 4);
                        int totalAmount = Integer.parseInt(getInvoiceTotal().getText());
                        totalAmount += invTblTotal;
                        getInvoiceTotal().setText("" + totalAmount);
                    }
                }
            }
        });



        newPanel5.add(tblScrollPane);
        rightPanel.add(newPanel5);

        // 3) Buttons
        save = new JButton("Save");
        rightPanel.add(save);
        save.addActionListener(e -> new Actions(mf).saveChanges());

        cancel = new JButton("Cancel");
        rightPanel.add(cancel);
        cancel.addActionListener(e -> new Actions(mf).cancelChanges());

        return rightPanel;
    }
}
