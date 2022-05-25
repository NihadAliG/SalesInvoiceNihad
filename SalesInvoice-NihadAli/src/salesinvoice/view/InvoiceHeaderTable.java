
package salesinvoice.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


//this class for create invoice dialog
public class InvoiceHeaderTable extends JDialog {
    private JTextField customertNameTextField;
    private JTextField invoiceDateTextField;
    private JLabel customerNameLabel;
    private JLabel invoiceDateLable;
    private JButton okButton;
    private JButton cancelButton;

    public InvoiceHeaderTable(GUI frame) {
        customerNameLabel = new JLabel("Customer Name:");
        customertNameTextField = new JTextField(20);
        invoiceDateLable = new JLabel("Invoice Date:");
        invoiceDateTextField = new JTextField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        okButton.setActionCommand("createInvoiceOK");
        cancelButton.setActionCommand("createInvoiceCancel");
        
        okButton.addActionListener(frame.getController());
        cancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLable);
        add(invoiceDateTextField);
        add(customerNameLabel);
        add(customertNameTextField);
        add(okButton);
        add(cancelButton);
        
        pack();
        
    }

    public JTextField getCustomerNameTextField() {
        return customertNameTextField;
    }

    public JTextField getInvoiceDateTextField() {
        return invoiceDateTextField;
    }
    
}
