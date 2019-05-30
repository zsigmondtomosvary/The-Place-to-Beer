package at.ac.tuwien.sepm.assignment.individual.restaurant.controller;

import at.ac.tuwien.sepm.assignment.individual.application.Changescene;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Invoice;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.service.Invoicemanagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class Invoicecontroller implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private TableView<Invoice> tblinvoices;

    @FXML
    private TableColumn<Invoice, Integer> coliid, coltablenr;

    @FXML
    private TableColumn<Invoice, Timestamp> colpaiddate;

    private ObservableList<Invoice> oInvoiceList = null;

    private Invoicemanagement invoicemanagement = new Invoicemanagement();

    public static Invoice selectedInvoice = null;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LOG.info("Initialize Invoicecontroller");

        displayinvoices();

    }

    private void RefreshInvoicesList(List<Invoice> invoices) {

        LOG.info("Set Invoices");

        tblinvoices.setItems(null);

        if(invoices != null) {
            oInvoiceList = FXCollections.observableList(invoices);

            tblinvoices.setEditable(false);

            coliid.setCellValueFactory(new PropertyValueFactory<Invoice, Integer>("id"));
            coltablenr.setCellValueFactory(new PropertyValueFactory<Invoice, Integer>("tablenumber"));
            colpaiddate.setCellValueFactory(new PropertyValueFactory<Invoice, Timestamp>("billdate"));

            tblinvoices.setItems(oInvoiceList);
        }
    }

    @FXML
    protected void displayinvoices(){

        LOG.info("Get Invoices");

        List<Invoice> result = null;
        try {
            result = invoicemanagement.displayInvoice();
        } catch (DatabaseException e) {
            LOG.error("Error", e.getMessage());
        }
        RefreshInvoicesList(result);
    }

    @FXML
    private void onDetailedViewClickEvent(){

        LOG.info("Clicked on Detailed View");

        if (tblinvoices.getSelectionModel().getSelectedItem() != null) {
            selectedInvoice = tblinvoices.getSelectionModel().getSelectedItem();
            Changescene.loadScene(Changescene.DETAILEDINVOICE);
        }else{
            JOptionPane.showMessageDialog(null, "You need to choose an invoice first!");
        }
    }

    @FXML
    private void onBackClickEvent(){

        LOG.info("Clicked on Back");

        Changescene.loadScene(Changescene.MAINSCENE);

    }

}
