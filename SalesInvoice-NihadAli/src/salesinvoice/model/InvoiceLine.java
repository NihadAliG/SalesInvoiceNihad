
package salesinvoice.model;

public class InvoiceLine {
    private String itemName;
    private double itemPrice;
    private int itemsCount;
    private InvoiceHeader invoiceHeader;

    public InvoiceLine() {
    }

    public InvoiceLine(String item, double price, int count, InvoiceHeader invoice) {
        this.itemName = item;
        this.itemPrice = price;
        this.itemsCount = count;
        this.invoiceHeader = invoice;
    }

    public double getTotalByLIne() {
        return itemPrice * itemsCount;
    }
    
    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int count) {
        this.itemsCount = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String item) {
        this.itemName = item;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double price) {
        this.itemPrice = price;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + invoiceHeader.getInvoiceNumber() + ", item=" + itemName + ", price=" + itemPrice + ", count=" + itemsCount + '}';
    }

    public InvoiceHeader getInvoice() {
        return invoiceHeader;
    }
    
    public String getLinesAsCSV() {
        return invoiceHeader.getInvoiceNumber() + "," + itemName + "," + itemPrice + "," + itemsCount;
    }
    
}
