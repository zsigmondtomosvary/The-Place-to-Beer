package at.ac.tuwien.sepm.assignment.individual.restaurant.controller;

import at.ac.tuwien.sepm.assignment.individual.application.Changescene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ResourceBundle;

import static at.ac.tuwien.sepm.assignment.individual.restaurant.controller.Invoicecontroller.selectedInvoice;

public class DetailInvoicecontroller implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private TextField tfid, tftablenr, tfaddeddate, tfpaiddate, tfsum, tfpayment;

    @FXML
    private TextArea tfproducts, tftaxsum;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LOG.info("Initialize DetailInvoicecontroller");

        LOG.info("Set Invoice Details");
        tfid.setText(String.valueOf(selectedInvoice.getId()));
        tftablenr.setText(String.valueOf(selectedInvoice.getTablenumber()));
        tfaddeddate.setText(selectedInvoice.getAddeddate().toString());
        tfpaiddate.setText(selectedInvoice.getBilldate().toString());
        tfsum.setText(String.valueOf(selectedInvoice.getBillprice()));
        tfpayment.setText(selectedInvoice.getPayment());
        tftaxsum.setText(selectedInvoice.getTaxclasses());
        tfproducts.setText(selectedInvoice.getProducts());

    }

    @FXML
    private void onBackClickEvent() throws Exception{

        LOG.info("Clicked on Back, go to Mainscene");

        Changescene.loadScene(Changescene.MAINSCENE);

    }

}
