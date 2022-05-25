package salesinvoice.controller;

import salesinvoice.model.InvoiceHeader;
import salesinvoice.model.InvoiceHeaderTableModel;
import salesinvoice.model.InvoiceLine;
import salesinvoice.model.InvoiceLineTableModel;
import salesinvoice.view.InvoiceHeaderTable;
import salesinvoice.view.GUI;
import salesinvoice.view.InvoiceLineTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private GUI gui;
    private InvoiceHeaderTable invoiceDialog;
    private InvoiceLineTable lineDialog;

    public Controller(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = gui.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            InvoiceHeader currentInvoice = gui.getInvoices().get(selectedIndex);
            gui.getInvoiceNumLabel().setText("" + currentInvoice.getInvoiceNumber());
            gui.getInvoiceDateLabel().setText(currentInvoice.getInvoiceDate());
            gui.getCustomerNameLabel().setText(currentInvoice.getCustomerName());
            gui.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            InvoiceLineTableModel linesTableModel = new InvoiceLineTableModel(currentInvoice.getLines());
            gui.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showOpenDialog(gui);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");
                // 1,22-11-2020,Ali
                // 2,13-10-2021,Saleh
                // 3,11-11-2021,heikal
                // 4,2-1-2021,keko
                ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] headerParts = headerLine.split(",");
                    int invoiceNum = Integer.parseInt(headerParts[0]);
                    String invoiceDate = headerParts[1];
                    String customerName = headerParts[2];

                    InvoiceHeader invoice = new InvoiceHeader(invoiceNum, invoiceDate, customerName);
                    invoicesArray.add(invoice);
                }
                System.out.println("Check point");
                result = fc.showOpenDialog(gui);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    for (String lineLine : lineLines) {
                        String lineParts[] = lineLine.split(",");
                        int invoiceNum = Integer.parseInt(lineParts[0]);
                        String itemName = lineParts[1];
                        double itemPrice = Double.parseDouble(lineParts[2]);
                        int count = Integer.parseInt(lineParts[3]);
                        InvoiceHeader inv = null;
                        for (InvoiceHeader invoice : invoicesArray) {
                            if (invoice.getInvoiceNumber() == invoiceNum) {
                                inv = invoice;
                                break;
                            }
                        }

                        InvoiceLine line = new InvoiceLine(itemName, itemPrice, count, inv);
                        inv.getLines().add(line);
                    }
                    System.out.println("Check point");
                }
                gui.setInvoices(invoicesArray);
                InvoiceHeaderTableModel invoicesTableModel = new InvoiceHeaderTableModel(invoicesArray);
                gui.setInvoicesTableModel(invoicesTableModel);
                gui.getInvoiceTable().setModel(invoicesTableModel);
                gui.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveFile() {
        ArrayList<InvoiceHeader> invoices = gui.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoiceHeader invoice : invoices) {
            String invCSV = invoice.getInvoiceAsCSV();
            headers += invCSV;
            headers += "\n";

            for (InvoiceLine line : invoice.getLines()) {
                String lineCSV = line.getLinesAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(gui);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(gui);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoice() {
        invoiceDialog = new InvoiceHeaderTable(gui);
        invoiceDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = gui.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            gui.getInvoices().remove(selectedRow);
            gui.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        lineDialog = new InvoiceLineTable(gui);
        lineDialog.setVisible(true);
    }

    private void deleteItem() {
        int selectedRow = gui.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            InvoiceLineTableModel linesTableModel = (InvoiceLineTableModel) gui.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            gui.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createInvoiceOK() {
        String date = invoiceDialog.getInvoiceDateTextField().getText();
        String customer = invoiceDialog.getCustomerNameTextField().getText();
        int num = gui.getNextInvoiceNum();

        InvoiceHeader invoice = new InvoiceHeader(num, date, customer);
        gui.getInvoices().add(invoice);
        gui.getInvoicesTableModel().fireTableDataChanged();
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createLineOK() {
        String item = lineDialog.getItemNameTextField().getText();
        String countStr = lineDialog.getItemCountTextField().getText();
        String priceStr = lineDialog.getItemPriceTextField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = gui.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoiceHeader invoice = gui.getInvoices().get(selectedInvoice);
            InvoiceLine line = new InvoiceLine(item, price, count, invoice);
            invoice.getLines().add(line);
            InvoiceLineTableModel linesTableModel = (InvoiceLineTableModel) gui.getLineTable().getModel();
            linesTableModel.fireTableDataChanged();
            gui.getInvoicesTableModel().fireTableDataChanged();
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

}
