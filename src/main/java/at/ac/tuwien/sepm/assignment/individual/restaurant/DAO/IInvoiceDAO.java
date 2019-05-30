package at.ac.tuwien.sepm.assignment.individual.restaurant.DAO;

import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Invoice;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;

import java.util.List;

public interface IInvoiceDAO {

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
     * @param invoiceid It is the ID of the invoices.
     * @return It is a list with all the products of an invoice from the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    List<Products> displayInvoiceProducts(int invoiceid) throws DatabaseException;

    /**
     * Adding a new invoice to database.
     * @param table The new invoice id for these tablenumber.
     * @return It is a boolean if the invoice was added to database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    boolean addnewInvoicetoDB(int table) throws DatabaseException;

    /**
     * Check if there is an order(open invoice) with this tablenumber.
     * @param table It is the tablenumber to check.
     * @return If there is an order, than returns it's id, if not then returns 0.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    int hasTableOrder(int table) throws DatabaseException;

    /**
     * Add Product to Order in the database.
     * @param pid The productid of the product what we want to give to the table in the database.
     * @param iid The invoiceid of the order where we want to give the product in the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void addProducttoOrder(int pid, int iid) throws DatabaseException;

    /**
     * Delete the product from an order from the database.
     * @param productid The ID of the product what we delete from the order.
     * @param invoiceid the ID of the order, where the product is deleted.
     * @return It is a boolean if the product was deleted from the order from the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    boolean deleteProductfromOrder(int productid, int invoiceid) throws DatabaseException;

    /**
     * Bill an order.
     * @param iid The ID of the order what we want to bill/close.
     * @param sum The bruttosum of the products.
     * @param tax The brutto tax pro taxclasses as string.
     * @param products The products of order as a string.
     * @param payment The paymenttype of the invoice.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void billOrder(int iid, double sum, String tax, String products,String payment) throws DatabaseException;

    /**
     * Delete the constraints of products and invoice of the given invoiceid.
     * @param iid It is the ID of the invoices.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void deleteorderproduct(int iid) throws DatabaseException;

}
