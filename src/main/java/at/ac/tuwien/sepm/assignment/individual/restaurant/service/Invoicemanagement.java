package at.ac.tuwien.sepm.assignment.individual.restaurant.service;

import at.ac.tuwien.sepm.assignment.individual.restaurant.DAO.InvoiceDAO;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Invoice;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import static at.ac.tuwien.sepm.assignment.individual.restaurant.controller.Tablecontroller.selectedOrder;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.List;

public class Invoicemanagement implements IInvoicemanagement{

    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private static DecimalFormat df2 = new DecimalFormat(".##");


    @Override
    public List<Invoice> displayInvoice() throws DatabaseException {
        invoiceDAO = new InvoiceDAO();
        return invoiceDAO.displayInvoice();
    }

    @Override
    public List<Invoice> displayOrders() throws DatabaseException{
        invoiceDAO = new InvoiceDAO();
        return invoiceDAO.displayOrders();
    }

    @Override
    public List<Products> displayInvoiceProducts(int id) throws DatabaseException{
        invoiceDAO = new InvoiceDAO();
        return  invoiceDAO.displayInvoiceProducts(id);
    }

    @Override
    public boolean addInvoicetoDB(int table) throws DatabaseException {

        boolean newInvoicetoDb = false;

        try {
            newInvoicetoDb = invoiceDAO.addnewInvoicetoDB(table);

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return newInvoicetoDb;

    }

    @Override
    public boolean deleteProductfromOrder(int pid, int iid) throws DatabaseException{
        boolean deleted;

        try {
            deleted = invoiceDAO.deleteProductfromOrder(pid, iid);
        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }
        return deleted;
    }

    @Override
    public void billorder() throws DatabaseException{
        String[] buttons = {"Card", "Cash"};
        String returnValue = (String) JOptionPane.showInputDialog(null, "Choose a payment Option", "Payment",
            JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
        int invoiceid = selectedOrder.getId();
        List<Products> result = invoiceDAO.displayInvoiceProducts(selectedOrder.getId());
        double billsum = 0;
        double normaltax = 0;
        double reducedtax = 0;
        StringBuilder list = new StringBuilder();

        for (int i = 0; i < result.size(); i++) {
            billsum += result.get(i).getBrutto();
            if (result.get(i).getTax().equals("20% Normal")){
                normaltax += (result.get(i).getBrutto() - result.get(i).getNetto());
            }else{
                reducedtax += (result.get(i).getBrutto() - result.get(i).getNetto());
            }
            list.append(result.get(i).getName() + " " + result.get(i).getBrutto() + "\n");
        }
        String taxstring = "10% Ermäßigt - " + Double.valueOf(df2.format(reducedtax)) + "\n20% Normal - " + Double.valueOf(df2.format(normaltax));
        String productlist = list.toString();

        invoiceDAO.billOrder(invoiceid, Double.valueOf(df2.format(billsum)), taxstring, productlist,returnValue);
        invoiceDAO.deleteorderproduct(invoiceid);
    }

}
