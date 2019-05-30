package at.ac.tuwien.sepm.assignment.individual.restaurant.DAO;

import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.ProductalreadyexistException;

import java.util.List;

public interface IProductDAO {

    /**
     * Display all the product from the database.
     * @return It is a list with all the products in the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    List<Products> displayProducts() throws DatabaseException;

    /**
     * Update the product of every(almost) column.
     * @param products The products that should be updated.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void updateDetailedProduct(Products products) throws DatabaseException;

    /**
     * Add Product to database.
     * @param name The name of the product what we want to give to database.
     * @param text The desciption of the product what we want to give to database.
     * @param price The price of the product what we want to give to database.
     * @param cat The category of the product what we want to give to database.
     * @param tax The taxclass of the product what we want to give to database.
     * @return It is a boolean if the product was added to database.
     * @throws ProductalreadyexistException If the product alrrady in the database than it shows more Information about the error.
     */
    boolean addnewProducttoDB(String name, String text, double price,
                              String cat, String tax) throws ProductalreadyexistException;

    /**
     * Delete Product from the database.
     * @param productid The product what we want to delete from database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void deleteProductfromDB(int productid) throws DatabaseException;

    /**
     * Check if the product is on any order.
     * @param productid It is the ID of the invoices.
     * @return It is a boolean if the product is on an order in the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    boolean isProductonOrder(int productid) throws DatabaseException;

    /**
     * Search for a product in the database.
     * @param productname The productname what we could look for.
     * @param productcategory The productcategory what we could look for.
     * @return It is a list of the results of the search.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    List<Products> searchProduct(String productname, String productcategory) throws DatabaseException;


}
