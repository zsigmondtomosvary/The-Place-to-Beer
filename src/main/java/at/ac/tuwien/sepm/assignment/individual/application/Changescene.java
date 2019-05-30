package at.ac.tuwien.sepm.assignment.individual.application;

import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class Changescene {

    public static final String MAIN    = "/fxml/main.fxml";
    public static final String MAINSCENE = "/fxml/mainscene.fxml";
    public static final String PRODUCTMANAGEMENT = "/fxml/products.fxml";
    public static final String INVOICEMANAGMENT = "/fxml/invoices.fxml";
    public static final String TABLEMANAGEMENT = "/fxml/tables.fxml";
    public static final String DETAILEDINVOICE = "/fxml/detailedinvoice.fxml";
    public static final String DETAILEDPRODUCT = "/fxml/detailedproduct.fxml";

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        Changescene.mainController = mainController;
    }

    public static void loadScene(String fxml) {

        LOG.info("Changing scene");

        try {
            mainController.setScene(
                FXMLLoader.load(
                    Changescene.class.getResource(
                        fxml
                    )
                )
            );
        } catch (IOException e) {
            LOG.error("Error by changing scene" + e.getMessage());
            e.printStackTrace();
        }
    }


}
