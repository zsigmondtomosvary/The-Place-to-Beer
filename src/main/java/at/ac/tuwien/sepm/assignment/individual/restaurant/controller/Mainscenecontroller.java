package at.ac.tuwien.sepm.assignment.individual.restaurant.controller;

import at.ac.tuwien.sepm.assignment.individual.application.Changescene;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.lang.invoke.MethodHandles;

public class Mainscenecontroller {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private void onProductsClickEvent() throws Exception{

        LOG.info("Opening Productmanagement");

        Changescene.loadScene(Changescene.PRODUCTMANAGEMENT);

    }

    @FXML
    private void onInvoicesClickEvent() throws Exception{

        LOG.info("Open Invoicemanagement");

        Changescene.loadScene(Changescene.INVOICEMANAGMENT);

    }


    @FXML
    private void onStatisticClickEvent() throws Exception{

        LOG.info("Open Statisticmanagement");

        JOptionPane.showMessageDialog(null, "There is no Statistic yet");

    }

    @FXML
    private void  onTablesClickEvent(){

        LOG.info("Open Tablecontroller");

        Changescene.loadScene(Changescene.TABLEMANAGEMENT);

    }

}
