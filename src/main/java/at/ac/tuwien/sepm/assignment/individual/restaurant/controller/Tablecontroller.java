package at.ac.tuwien.sepm.assignment.individual.restaurant.controller;

import at.ac.tuwien.sepm.assignment.individual.application.Changescene;
import at.ac.tuwien.sepm.assignment.individual.restaurant.DAO.InvoiceDAO;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Invoice;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.service.Invoicemanagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class Tablecontroller implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    public TableView<Invoice> tblorders;

    @FXML
    public TableColumn<Invoice, Timestamp> coldate;

    @FXML
    public TableColumn<Invoice, Integer> coltblnr;

    @FXML
    public TableView<Products> tblproducts;

    @FXML
    public TableColumn<Products, String> colname, coltext, colcat, coltax;

    @FXML
    public TableColumn<Products, Double> colprice;

    private ObservableList<Invoice> orders = null;

    private ObservableList<Products> observeProducts = null;

    private InvoiceDAO invoiceDAO = new InvoiceDAO();

    private Invoicemanagement invoicemanagement = new Invoicemanagement();

    public static Invoice selectedOrder = null;

    public static Products selectedProduct = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LOG.info("Initialize Tablecontroller");

        displayOrders();

    }


    private void RefreshOrders(List<Invoice> orderlist) {

        LOG.info("Set Orders");

        tblorders.setItems(null);

        if(orderlist != null) {
            orders = FXCollections.observableList(orderlist);

            coltblnr.setCellValueFactory(new PropertyValueFactory<>("tablenumber"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("addeddate"));

            tblorders.setItems(orders);
        }
    }

    @FXML
    private void displayOrders() {

        LOG.info("Get Orders");

        List<Invoice> result = null;
        try {
            result = invoicemanagement.displayOrders();
        } catch (DatabaseException e) {
            LOG.error("Error", e.getMessage());
        }
        RefreshOrders(result);
    }

    private void RefreshOrderProductList(List<Products> products) {

        LOG.info("Set Products of Orders");

        tblproducts.setItems(null);

        if(products != null) {
            observeProducts = FXCollections.observableList(products);

            colname.setCellValueFactory(new PropertyValueFactory<>("name"));
            coltext.setCellValueFactory(new PropertyValueFactory<>("text"));
            colprice.setCellValueFactory(new PropertyValueFactory<>("brutto"));
            colcat.setCellValueFactory(new PropertyValueFactory<>("category"));
            coltax.setCellValueFactory(new PropertyValueFactory<>("tax"));


            tblproducts.setItems(observeProducts);
        }
    }

    @FXML
    private void displayOrderProducts() {

        LOG.info("Get Products od Orders");

        List<Products> result = null;
        if (tblorders.getSelectionModel().getSelectedItem() != null) {
            selectedOrder = tblorders.getSelectionModel().getSelectedItem();
            try {
                result = invoicemanagement.displayInvoiceProducts(selectedOrder.getId());
            } catch (DatabaseException e) {
                LOG.error("Error", e.getMessage());
            }
            RefreshOrderProductList(result);
        }else{
            JOptionPane.showMessageDialog(null, "You need to choose a table first!");
        }
    }


    @FXML
    public void onAddClickEvent() {

        LOG.info("Clicked on Add Product, go to Productscene");

        Changescene.loadScene(Changescene.PRODUCTMANAGEMENT);

    }

    @FXML
    private void onDeleteClickEvent() throws DatabaseException{

        LOG.info("Clicked on Delete");

        if (tblproducts.getSelectionModel().getSelectedItem() != null || tblorders.getSelectionModel().getSelectedItem() != null) {

            selectedProduct = tblproducts.getSelectionModel().getSelectedItem();
            selectedOrder = tblorders.getSelectionModel().getSelectedItem();

            int p = JOptionPane.showConfirmDialog(null, "Do you really want to delete?", "Delete", JOptionPane.YES_NO_OPTION);

            if (p == 0) {
                if(invoicemanagement.deleteProductfromOrder(selectedProduct.getId(), selectedOrder.getId())) {
                    JOptionPane.showMessageDialog(null, "Deleted");
                    tblproducts.getItems().removeAll(selectedProduct);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "You need to choose a product first!");
        }

    }


    @FXML
    private void onBillClickEvent() throws DatabaseException{

        LOG.info("Clicked on Bill");

        if (tblorders.getSelectionModel().getSelectedItem() != null) {
            selectedOrder = tblorders.getSelectionModel().getSelectedItem();
            int p = JOptionPane.showConfirmDialog(null, "Do you really want to bill?", "Bill", JOptionPane.YES_NO_OPTION);
            if (p == 0) {
                invoicemanagement.billorder();
                JOptionPane.showMessageDialog(null, "Billed");
                tblproducts.getItems().clear();
                displayOrders();
            }
        }else{
            JOptionPane.showMessageDialog(null, "You need to choose a table first!");
        }
    }


    @FXML
    private void onBackClickEvent() {

        LOG.info("Clicked on Back, go to Mainscene");

        Changescene.loadScene(Changescene.MAINSCENE);

    }

}
