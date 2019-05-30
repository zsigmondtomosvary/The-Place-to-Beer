package at.ac.tuwien.sepm.assignment.individual.restaurant.service;

import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.ProductalreadyexistException;

import java.util.List;


public interface IProductmanagement {


    /**
     * Display all the product from the database.
     * @return It is a list with all the products in the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    List<Products> displayProducts() throws DatabaseException;

    /**
     * Add Product to database.
     * @param products The product what we want to give to database.
     * @return It is a boolean if the product was added to database.
     * @throws ProductalreadyexistException If the product alrrady in the database than it shows more Information about the error.
     */
    boolean addProducttoDB(Products products) throws ProductalreadyexistException;

    /**
     * Search for a product in the database.
     * @param productname The productname what we could look for.
     * @param productcategory The productcategory what we could look for.
     * @return It is a list of the results of the search.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    List<Products> searchProduct(String productname, String productcategory) throws DatabaseException;

    /**
     * Delete Product from the database.
     * @param id The product what we want to delete from database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void deleteProduct(int id) throws DatabaseException;

    /**
     * Add Product to Order in the database.
     * @param pid The productid of the product what we want to give to the table in the database.
     * @param table The tablenumber where the product should go.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void addProducttoOrder(int pid, int table) throws DatabaseException;

    /**
     * Check if there is an order with the given product in the database.
     * @param productid The productid of the product what we want to check on the orders in the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    boolean isProtuctonOrder(int productid) throws DatabaseException;

    /**
     * Update Product in the database.
     * @param products The product what we want to update in the database.
     * @throws DatabaseException If there was an error in the database than it shows more Information about the error.
     */
    void updateProduct(Products products) throws DatabaseException;

}
