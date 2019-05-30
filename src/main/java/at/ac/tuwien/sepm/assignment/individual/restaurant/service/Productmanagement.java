package at.ac.tuwien.sepm.assignment.individual.restaurant.service;

import at.ac.tuwien.sepm.assignment.individual.restaurant.DAO.InvoiceDAO;
import at.ac.tuwien.sepm.assignment.individual.restaurant.DAO.ProductDAO;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.ProductalreadyexistException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Productmanagement implements IProductmanagement {

    ProductDAO productDAO = new ProductDAO();
    InvoiceDAO invoiceDAO = new InvoiceDAO();
    Invoicemanagement invoicemanagement = new Invoicemanagement();

    @Override
    public List<Products> displayProducts() throws DatabaseException {
        productDAO = new ProductDAO();
        return productDAO.displayProducts();
    }

    @Override
    public boolean addProducttoDB(Products products) throws ProductalreadyexistException {

        boolean newProducttoDb;
        double brutto = 0;

        if (String.valueOf(products.getTax()).equals("20% Normal")) {
            brutto = products.getNetto() * 1.2;
        } else {
            brutto = products.getNetto() * 1.1;
        }

        products.setBrutto(brutto);

        try {
            newProducttoDb = productDAO.addnewProducttoDB(products.getName(), products.getText(),
                products.getNetto(), products.getCategory(), products.getTax());
            if (newProducttoDb) {
                JOptionPane.showMessageDialog(null, "Saved!");
            }
        } catch (Exception e) {
            throw new ProductalreadyexistException(e.getMessage());
        }

        return newProducttoDb;

    }

    @Override
    public List<Products> searchProduct(String productname, String productcategory) throws DatabaseException {
        List<Products> result = null;

        try {
            result = productDAO.searchProduct(productname, productcategory);
        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }
        return result;
    }

    @Override
    public void deleteProduct(int id) throws DatabaseException {

        try {
            productDAO.deleteProductfromDB(id);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void addProducttoOrder(int productid, int table) throws DatabaseException {

       int invoiceid = invoiceDAO.hasTableOrder(table);

        if (invoiceid != 0) {
            invoiceDAO.addProducttoOrder(productid, invoiceid);
        } else {
            if (invoicemanagement.addInvoicetoDB(table)) {
                invoiceid = invoiceDAO.hasTableOrder(table);
                invoiceDAO.addProducttoOrder(productid, invoiceid);
            }
        }
    }

    @Override
    public boolean isProtuctonOrder(int productid) throws DatabaseException {

        boolean on;

        try {
            on = productDAO.isProductonOrder(productid);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return on;

    }

    @Override
    public void updateProduct(Products products) throws DatabaseException {
        try {
            if (!productDAO.isProductonOrder(products.getId())) {
                productDAO.updateDetailedProduct(products);
                JOptionPane.showMessageDialog(null,"You updated a Product");
            } else {
                JOptionPane.showMessageDialog(null, "This product is on an Order, you can't update it!");
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
