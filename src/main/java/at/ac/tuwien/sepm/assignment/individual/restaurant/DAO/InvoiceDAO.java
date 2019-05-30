package at.ac.tuwien.sepm.assignment.individual.restaurant.DAO;

import at.ac.tuwien.sepm.assignment.individual.application.ConnectDb;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Invoice;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO implements IInvoiceDAO{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    PreparedStatement preparedStatement(String query) throws SQLException {
        LOG.info("Preparing statement");
        if(ConnectDb.getConnection() == null) throw new SQLException("Can't establish connection!");
        return ConnectDb.getConnection().prepareStatement(query);
    }

    @Override
    public List<Invoice> displayInvoice() throws DatabaseException {

        LOG.debug("Display Invoices");

        List<Invoice> list = new ArrayList();

        try {
            PreparedStatement preparedStatement = preparedStatement("SELECT * FROM invoices ORDER BY billdate asc");
            LOG.debug("Executing the query");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                if(rs.getString("status").equals("Closed")) {
                    list.add(new Invoice(rs.getInt("invoiceid"), rs.getInt("tablenumber"),
                        rs.getTimestamp("invoicedate"), rs.getTimestamp("billdate"),
                        Invoice.Status.valueOf(rs.getString("status")), rs.getString("products"),
                        rs.getDouble("billsum"), rs.getString("taxsum"),
                        rs.getString("paymenttype")));
                }
            }
            rs.close();
            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to display invoices:" + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Invoice> displayOrders() throws DatabaseException {

        LOG.debug("Display Orders");

        List<Invoice> list = new ArrayList();

        try {

            PreparedStatement preparedStatement = preparedStatement("SELECT * FROM invoices ORDER BY invoicedate asc");
            LOG.debug("Executing the query");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                if(rs.getString("status").equals("Open")) {
                    list.add(new Invoice(rs.getInt("invoiceid"), rs.getInt("tablenumber"),
                        rs.getTimestamp("invoicedate"), null,
                        Invoice.Status.valueOf(rs.getString("status")), null,
                        rs.getDouble("billsum"), rs.getString("taxsum"),
                        rs.getString("paymenttype")));
                }
            }
            rs.close();
            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to display orders:" + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Products> displayInvoiceProducts(int invoiceid) throws DatabaseException{

        LOG.debug("Display Products of Invoice");

        List<Products> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = preparedStatement("select p.* from products p join orderproduct o on p.productid = o.pid where o.iid = ? order by p.productname asc");
            preparedStatement.setInt(1, invoiceid);
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

        }catch (SQLException e){
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to display products of invoices:" + e.getMessage());
        }

        return list;
    }

    @Override
    public boolean addnewInvoicetoDB(int table) throws DatabaseException{

        LOG.debug("Adding new Order to DB");

        String sql = "INSERT INTO invoices(invoiceid, tablenumber, invoicedate, billdate, status," +
            "products, billsum, taxsum, paymenttype) " + "VALUES (default ,?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setInt(1, table);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(3, null);
            preparedStatement.setString(4, "Open");
            preparedStatement.setString(5, "");
            preparedStatement.setDouble(6, 0.0);
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "");
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
            throw new DatabaseException("Failed to add new invoice:" + e.getMessage());
        }

        return false;
    }

    @Override
    public int hasTableOrder(int table) throws DatabaseException{

        LOG.debug("Check if there is an Order with this tablenumber");

        String sql = "SELECT * FROM invoices WHERE status = ? AND tablenumber = ?";

        try {

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setString(1, "Open");
            preparedStatement.setInt(2, table);
            LOG.debug("Executing the query");
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt("invoiceid");
            }

            rs.close();
            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to check if table has order:" + e.getMessage());
        }

        return 0;

    }

    @Override
    public void addProducttoOrder(int pid, int iid) throws DatabaseException{

        LOG.debug("Add Product to Order");

        String sql = "INSERT INTO orderproduct(pid, iid) " + "VALUES (?, ?)";

        try {

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, iid);
            LOG.debug("Executing the query");
            preparedStatement.executeUpdate();

            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to add product to order:" + e.getMessage());
        }


    }

    @Override
    public boolean deleteProductfromOrder(int productid, int invoiceid) throws DatabaseException{

        LOG.debug("Delete Product from Order");

        String sql = "DELETE FROM orderproduct WHERE pid = ? AND iid = ?";

        try {

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setInt(1, productid);
            preparedStatement.setInt(2, invoiceid);
            LOG.debug("Executing the query");
            boolean deleted = preparedStatement.execute();
            if (!deleted) {
                return !deleted;
            }

            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to delete product from order:" + e.getMessage());
        }

        return false;

    }

    @Override
    public void billOrder(int iid, double sum, String tax, String products,String payment) throws DatabaseException{

        LOG.debug("Bill Order");

        String sql = "UPDATE invoices SET billdate = ?, status = ?, billsum = ?," +
            "products = ?, taxsum = ?, paymenttype = ? WHERE invoiceid = ?";

        try {

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(2, "Closed");
            preparedStatement.setDouble(3, sum);
            preparedStatement.setString(4, products);
            preparedStatement.setString(5, tax);
            preparedStatement.setString(6, payment);
            preparedStatement.setInt(7, iid);
            LOG.debug("Executing the query");
            preparedStatement.executeUpdate();

            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to bill order:" + e.getMessage());
        }

    }

    @Override
    public void deleteorderproduct(int iid) throws DatabaseException{

        LOG.debug("Delete constraints from orderproduct table");

        String sql = "DELETE FROM orderproduct WHERE iid = ?";

        try {

            PreparedStatement preparedStatement = preparedStatement(sql);
            preparedStatement.setInt(1, iid);
            LOG.debug("Executing the query");
            preparedStatement.execute();

            preparedStatement.close();
            ConnectDb.getConnection().commit();
            LOG.debug("Successful operation");

        } catch (SQLException e) {
            LOG.error("Error:" + e.getMessage());
            throw new DatabaseException("Failed to delete constraints:" + e.getMessage());
        }

    }

}
