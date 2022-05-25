//Nihad Ali
//testing project
package salesinvoice.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceLineTableModel extends AbstractTableModel {

    private ArrayList<InvoiceLine> lines;
    private String[] tableHeader = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public InvoiceLineTableModel(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    public ArrayList<InvoiceLine> getLines() {
        return lines;
    }
    
    
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return tableHeader.length;
    }

    @Override
    public String getColumnName(int x) {
        return tableHeader[x];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line = lines.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return line.getInvoice().getInvoiceNumber();
            case 1: return line.getItemName();
            case 2: return line.getItemPrice();
            case 3: return line.getItemsCount();
            case 4: return line.getTotalByLIne();
            default : return "";
        }
    }
    
}
