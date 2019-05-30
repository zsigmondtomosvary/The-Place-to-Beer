package at.ac.tuwien.sepm.assignment.individual.restaurant.service;

import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Invoice;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;

import java.util.List;

public interface IInvoicemanagement {

    /**
     * Display all the invoice from the database.
     * @return It is a list with all the invoices in the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    List<Invoice> displayInvoice() throws DatabaseException;

    /**
     * Display all the orders(open invoices) from the database.
     * @return It is a list with all the orders(open invoices) in the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    List<Invoice> displayOrders() throws DatabaseException;

    /**
     * Display all the products of an invoice from the database.
     * @param id It is the ID of the invoices.
     * @return It is a list with all the products of an invoice from the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    List<Products> displayInvoiceProducts(int id) throws DatabaseException;

    /**
     * Adding a new invoice to database.
     * @param table The new invoice id for these tablenumber.
     * @return It is a boolean if the invoice was added to database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    boolean addInvoicetoDB(int table) throws DatabaseException;

    /**
     * Delete the product from an order from the database.
     * @param pid The ID of the product what we delete from the order.
     * @param iid the ID of the order, where the product is deleted.
     * @return It is a boolean if the product was deleted from the order from the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    boolean deleteProductfromOrder(int pid, int iid) throws DatabaseException;

    /**
     * Bill an order.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void billorder() throws DatabaseException;


}
