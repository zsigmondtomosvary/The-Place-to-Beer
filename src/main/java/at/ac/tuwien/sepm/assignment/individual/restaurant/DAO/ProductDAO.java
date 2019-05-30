package at.ac.tuwien.sepm.assignment.individual.restaurant.DAO;

import at.ac.tuwien.sepm.assignment.individual.application.ConnectDb;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.ProductalreadyexistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static DecimalFormat df2 = new DecimalFormat(".##");

    PreparedStatement preparedStatement(String query) throws SQLException {
        LOG.info("Preparing statement");
        if(ConnectDb.getConnection() == null) throw new SQLException("Can't establish connection!");
        return ConnectDb.getConnection().prepareStatement(query);
    }

    @Override
    public List<Products> displayProducts() throws DatabaseException {

        LOG.debug("Display Products");

        List<Products> list = new ArrayList();

        try {

            PreparedStatement preparedStatement = preparedStatement("SELECT * FROM products");
            LOG.debug("Executing the query");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                list.add(new Products(rs.getInt("productid"), rs.getString("productname"),
                    rs.getString("text"), rs.getDouble("netto"), rs.getDouble("brutto"),
                    rs.getTimestamp("addeddate"), rs.getTimestamp("lastedited"),
                    rs.getString("category"), rs.getString("taxclass")));
            }

            rs.close();
            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to display products:" + e.getMessage());

        }

        return list;


    }


    @Override
    public void updateDetailedProduct(Products products) throws DatabaseException {

        LOG.debug("Update Product");

        String sql = "UPDATE products SET productname = ?, text = ?, netto = ?, brutto = ?," +
            " lastedited = ?, category = ?, taxclass = ? WHERE productid = ? ";

        try {

            double brutto = 0;

            if (products.getTax().equals("20% Normal")){
                brutto = products.getNetto() * 1.2;
            }else{
                brutto = products.getNetto() * 1.2;
            }

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setString(1, products.getName());
            preparedStatement.setString(2, products.getText());
            preparedStatement.setDouble(3, products.getNetto());
            preparedStatement.setDouble(4, Double.valueOf(df2.format(brutto)));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, products.getCategory());
            preparedStatement.setString(7, products.getTax());
            preparedStatement.setInt(8, products.getId());
            LOG.debug("Executing the query");
            preparedStatement.execute();

            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to update the product:" + e.getMessage());
        }

    }

    @Override
    public boolean addnewProducttoDB(String name, String text, double price,
                                     String cat, String tax) throws ProductalreadyexistException {

        LOG.debug("Add new Product to DB");

        String sql = "INSERT INTO products(productid, productname, text, netto, brutto, addeddate, lastedited," +
            " category, taxclass) " + "VALUES (default ,?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            double brutto = 0;

            if (tax.equals("20% Normal")){
                brutto = price * 1.2;
            }else{
                brutto = price * 1.2;
            }

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, text);
            preparedStatement.setDouble(3, price);
            preparedStatement.setDouble(4, brutto);
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.setDate(6, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(7, cat);
            preparedStatement.setString(8, tax);
            LOG.debug("Executing the query");
            int a = preparedStatement.executeUpdate();

            if (a == 1) {
                return true;
            }

            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");
        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new ProductalreadyexistException("Failed to adding product:" + e.getMessage());
        }

        return false;

    }

    @Override
    public void deleteProductfromDB(int productid) throws DatabaseException {

        LOG.debug("Delete Product from DB");

        String sql = "DELETE FROM products WHERE productid = ? ";

        try{

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setInt(1, productid);
            LOG.debug("Executing the query");
            preparedStatement.execute();

            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to delete product:" + e.getMessage());
        }

    }

    @Override
    public boolean isProductonOrder(int productid) throws DatabaseException{

        LOG.debug("Check if Product is on Order");

        String sql = "select p.* from products p join orderproduct o on p.productid = o.pid where p.productid = ? order by p.productname asc";


        try{

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setInt(1, productid);
            LOG.debug("Executing the query");
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            }

            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to check if product is on order:" + e.getMessage());
        }

        return false;

    }

    @Override
    public List<Products> searchProduct(String productname, String productcategory) throws DatabaseException{

        LOG.debug("Search Product in DB");

        List<Products> list = new ArrayList();

        String sql = "SELECT * FROM products WHERE productname LIKE '%" + productname + "%' AND category = '" + productcategory + "'";

        if ((productname.equals("")) && !(productcategory.equals("null"))) {
            sql = "SELECT * FROM products WHERE category = '" + productcategory + "'";
        } else if (!(productname.equals("")) && (productcategory.equals("null"))) {
            sql = "SELECT * FROM products WHERE productname LIKE '%"+ productname+ "%'";
        } else if ((productname.equals("")) || (productcategory.equals("null"))) {
            return displayProducts();
        }

        try {

            PreparedStatement preparedStatement = preparedStatement(sql);
            LOG.debug("Executing the query");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                list.add(new Products(rs.getInt("productid"), rs.getString("productname"),
                    rs.getString("text"), rs.getDouble("netto"), rs.getDouble("brutto"),
                    rs.getTimestamp("addeddate"), rs.getTimestamp("lastedited"),
                    rs.getString("category"), rs.getString("taxclass")));
            }
            rs.close();
            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to search for products:" + e.getMessage());
        }


        return list;
    }

}
