package control;

import view.LeftPanel;
import view.MyFrame;
import view.RightPanel;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Actions {

    public static JFileChooser fc;

    public static String linePath;

    private final MyFrame mf;
    private final LeftPanel lp;
    private final RightPanel rp;

    public Actions(MyFrame mf) {
        this.mf = mf;
        this.lp = mf.getLp();
        this.rp = mf.getRp();
    }


    // Load File
    public void loadFile() {


        fc = new JFileChooser();
        int result = fc.showOpenDialog(mf);
        if (result == JFileChooser.APPROVE_OPTION) {

            lp.getInvoicesTblModel().setRowCount(0);
            rp.getItemsTableModel().setRowCount(1);

            FileReader path;
            FileReader path2;
            BufferedReader br;
            BufferedReader br2;
            try {
                // Invoice Table
                path = new FileReader(fc.getSelectedFile());
                br = new BufferedReader(path);
                String line;
                String[] tempArr;


                int i = 0;
                while ((line = br.readLine()) != null) {
                    tempArr = line.split(",");
                    lp.getInvoicesTblModel().insertRow(i, tempArr);
                    i++;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String splitPath = fc.getSelectedFile().getName();

            linePath = splitPath.substring(0, splitPath.length() - 4) + "Line.csv";

            try {
                path2 = new FileReader("C:\\Users\\omarm\\OneDrive\\Documents\\" + linePath + "");
                br2 = new BufferedReader(path2);
                String line;
                String[] tempArr;


                int i = 0;
                while ((line = br2.readLine()) != null) {
                    tempArr = line.split(",");
                    rp.getItemsTableModel().insertRow(i, tempArr);
                    i++;
                }
                br2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            updateTotal();

            String numOFCustomer = (String)lp.getInvoicesTable().getValueAt(lp.getInvoicesTable().getRowCount()-1,0);
            int numbsOfCustomer = Integer.parseInt(numOFCustomer);
            rp.getInvoiceNumber().setText((numbsOfCustomer + 1) + "");


        }
    }

    // Create New Invoice
    public void createNewInvoice() {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
            sdf.setLenient(false);
            date = sdf.parse(rp.getInvoiceDate().getText());

            String numOFCustomer = (String) lp.getInvoicesTable().getValueAt(lp.getInvoicesTable().getRowCount() - 1, 0);
            int numbsOfCustomer = Integer.parseInt(numOFCustomer);
            rp.getInvoiceNumber().setText((numbsOfCustomer + 1) + "");
            lp.getInvoicesTblModel().insertRow(lp.getInvoicesTable().getRowCount(), new Object[]{
                    rp.getInvoiceNumber().getText(),
                    rp.getInvoiceDate().getText(),
                    rp.getCustomerName().getText(),
                    rp.getInvoiceTotal().getText(),
            });

            rp.getInvoiceNumber().setText((numbsOfCustomer + 2) + "");
            rp.getInvoiceDate().setText("");
            rp.getCustomerName().setText("");



        }catch (ParseException ex) {
            JOptionPane.showMessageDialog(
                    rp, "Date format incorrect. Please change to save", "Invalid Date", JOptionPane.WARNING_MESSAGE);
        }


    }

    // Delete Invoice
    public void deleteInvoice() {

        int selectedRow = lp.getInvoicesTable().getSelectedRow();
        String noInvoiceStr = (String) lp.getInvoicesTable().getValueAt(selectedRow, 0);
        int noInvoice = Integer.parseInt(noInvoiceStr);


        for (int i = 0; i < rp.getItemsTable().getRowCount() - 1; i++) {
            String rpItemStr = (String) rp.getItemsTable().getValueAt(i, 0);
            int rpItem = Integer.parseInt(rpItemStr);

            if (rpItem == noInvoice) {
                rp.getItemsTableModel().removeRow(i);
                i--;
            }

        }

        lp.getInvoicesTblModel().removeRow(selectedRow);


    }

    // Save Changes
    public void saveChanges() {


        FileWriter path;
        BufferedWriter bw = null;


        try {
            path = new FileWriter("C:\\Users\\omarm\\OneDrive\\Documents\\" + linePath + "");
            bw = new BufferedWriter(path);

            int nRow = rp.getItemsTable().getRowCount() - 1;
            int nCol = rp.getItemsTable().getColumnCount();
            int nRowHeader = lp.getInvoicesTable().getRowCount();
            boolean check = false;

            for (int i = 0; i < nRow; i++) {
                for (int j = 0; j < nRowHeader; j++) {

                    int rpItem = Integer.parseInt((String) rp.getItemsTable().getValueAt(i, 0));
                    int lpItem = Integer.parseInt((String) lp.getInvoicesTable().getValueAt(j, 0));
                    if (rpItem == lpItem) {
                        System.out.println(check);
                        check = true;
                        break;
                    } else {
                        check = false;
                    }
                }
            }


            if (check == true) {
                for (int i = 0; i < nRow; i++) {
                    for (int j = 0; j < nCol; j++) {
                        bw.write(rp.getItemsTable().getValueAt(i, j).toString() + ",");
                        System.out.println(rp.getItemsTable().getValueAt(i, j).toString() + ",");
                    }
                    bw.newLine();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Can not Save", "Problem", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("finish");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateTotal();

    }

    public void cancelChanges() {
        FileReader path2;
        BufferedReader br2;

        try {
//            rp.setInvoiceNumber(new JLabel(""));
            rp.getInvoiceDate().setText("");
            rp.getCustomerName().setText("");
//            rp.setInvoiceTotal(new JLabel(""));


            path2 = new FileReader("C:\\Users\\omarm\\OneDrive\\Documents\\" + linePath + "");
            br2 = new BufferedReader(path2);
            String line;
            String[] tempArr;


            int i = 0;
            rp.getItemsTableModel().removeRow(rp.getItemsTableModel().getRowCount()-2);
            while ((line = br2.readLine()) != null) {
                tempArr = line.split(",");
                rp.getItemsTableModel().removeRow(i);
                rp.getItemsTableModel().insertRow(i, tempArr);
                i++;
            }
            br2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateTotal();

    }


    // Save File
    public void saveFile() {


        FileWriter path;
        BufferedWriter bw = null;


        try {
            path = new FileWriter(fc.getSelectedFile());
            bw = new BufferedWriter(path);

            int nRow = lp.getInvoicesTable().getRowCount();
            int nCol = lp.getInvoicesTable().getColumnCount();

            for (int i = 0; i < nRow; i++) {
                for (int j = 0; j < nCol; j++) {
                    bw.write(lp.getInvoicesTable().getValueAt(i, j).toString() + ",");
                    System.out.println(lp.getInvoicesTable().getValueAt(i, j).toString() + ",");
                }
                bw.newLine();
            }
            System.out.println("finish");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void updateTotal() {
        for (int i = 0; i < lp.getInvoicesTblModel().getRowCount(); i++) {
            int sum = 0;
            for (int j = 0; j < rp.getItemsTableModel().getRowCount() - 1; j++) {
                String valilinetable = (String) rp.getItemsTable().getValueAt(j, 0);


                String valininvoicetable = (String) lp.getInvoicesTable().getValueAt(i, 0);

                if (Integer.parseInt(valilinetable) == Integer.parseInt(valininvoicetable)) {

                    //

                    int valuelinetoadd = (int) (rp.getItemsTable().getValueAt(j, 4));
                    sum += valuelinetoadd;

                }

            }
            lp.getInvoicesTable().setValueAt(sum, i, 3);
        }
    }

}
