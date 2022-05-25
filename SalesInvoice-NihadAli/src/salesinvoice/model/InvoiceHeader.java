
package salesinvoice.model;

import java.util.ArrayList;

public class InvoiceHeader {
    private int invoiceNumber;
    private String invoiceDate;
    private String customerName;
    private ArrayList<InvoiceLine> invoiceLines;
    
    public InvoiceHeader() {
    }

    public InvoiceHeader(int num, String date, String customer) {
        this.invoiceNumber = num;
        this.invoiceDate = date;
        this.customerName = customer;
    }

    public double getInvoiceTotal() {
        double total = 0.0;
        for (InvoiceLine line : getLines()) {
            total += line.getTotalByLIne();
        }
        return total;
    }
    
    public ArrayList<InvoiceLine> getLines() {
        if (invoiceLines == null) {
            invoiceLines = new ArrayList<>();
        }
        return invoiceLines;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customer) {
        this.customerName = customer;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int num) {
        this.invoiceNumber = num;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String date) {
        this.invoiceDate = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + invoiceNumber + ", date=" + invoiceDate + ", customer=" + customerName + '}';
    }
    
    public String getInvoiceAsCSV() {
        return invoiceNumber + "," + invoiceDate + "," + customerName;
    }
    
}
