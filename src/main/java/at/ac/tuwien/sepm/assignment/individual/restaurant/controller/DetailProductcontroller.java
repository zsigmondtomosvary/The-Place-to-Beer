package at.ac.tuwien.sepm.assignment.individual.restaurant.controller;

import at.ac.tuwien.sepm.assignment.individual.application.Changescene;
import at.ac.tuwien.sepm.assignment.individual.restaurant.DAO.ProductDAO;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.service.Productmanagement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static at.ac.tuwien.sepm.assignment.individual.restaurant.controller.Productscontroller.selectedProduct;

public class DetailProductcontroller implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    public TextField tfname, tfbruttoprice, tftext, tfnettoprice, tfaddeddate, tflastediteddate;

    @FXML
    public ChoiceBox<String> cbcategory, cbtax;

    public ProductDAO productDAO = new ProductDAO();

    public Productmanagement productmanagement = new Productmanagement();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LOG.info("Initialize DetailProductcontroller");

        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        tfnettoprice.setTextFormatter(formatter);

        tfname.setText(selectedProduct.getName());
        tftext.setText(selectedProduct.getText());
        tfbruttoprice.setText(String.valueOf(selectedProduct.getBrutto()));
        tfnettoprice.setText(String.valueOf(selectedProduct.getNetto()));
        cbcategory.setValue(selectedProduct.getCategory());
        cbtax.setValue(selectedProduct.getTax());
        tfaddeddate.setText(selectedProduct.getAddeddate().toString());
        tflastediteddate.setText(selectedProduct.getLastediteddate().toString());

    }

    @FXML
    private void onDeleteClickEvent() throws DatabaseException {

        LOG.info("Clicked on Delete");

        int p = JOptionPane.showConfirmDialog(null, "Do you really want to delete?", "Delete", JOptionPane.YES_NO_OPTION);

        if (p == 0) {
            if(!productmanagement.isProtuctonOrder(selectedProduct.getId())){
                productmanagement.deleteProduct(selectedProduct.getId());
                JOptionPane.showMessageDialog(null, "Deleted");
                Changescene.loadScene(Changescene.PRODUCTMANAGEMENT);
            } else {
                JOptionPane.showMessageDialog(null, "This product is on an Order, you can't delete it!");
            }
        }
    }

    @FXML
    private void onUpdateClickEvent() throws DatabaseException{

        if (tfname.getText().equals("") || tfnettoprice.getText().equals("")){
            JOptionPane.showMessageDialog(null, "You need name, price");
        }else if(Double.parseDouble(tfnettoprice.getText()) < 0){
            JOptionPane.showMessageDialog(null, "You can't set negative price for a product");
        }else {
            int p = JOptionPane.showConfirmDialog(null, "Do you really want to update?", "Update", JOptionPane.YES_NO_OPTION);

            if (p == 0) {
                Products product = new Products(selectedProduct.getId(), tfname.getText(), tftext.getText(),
                    Double.parseDouble(tfnettoprice.getText()), selectedProduct.getBrutto(),
                    selectedProduct.getAddeddate(), null, cbcategory.getValue(), cbtax.getValue());
                productmanagement.updateProduct(product);
                Changescene.loadScene(Changescene.PRODUCTMANAGEMENT);
            }
        }

    }

    @FXML
    private void AddProtucttoOrder(){

        LOG.info("Clicked on Add to Order");

        Products newproduct = selectedProduct;
        try {
            Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            int table = (int) (JOptionPane.showInputDialog(null, "Choose Table", "Table", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]));

            productmanagement.addProducttoOrder(newproduct.getId(), table);
            Changescene.loadScene(Changescene.TABLEMANAGEMENT);
        }catch (Exception e){
            LOG.error("Error:" + e.getMessage());
        }

    }

    @FXML
    private void onBackClickEvent() throws Exception{

        LOG.info("Clicked on Back, go to Products");

        Changescene.loadScene(Changescene.PRODUCTMANAGEMENT);

    }

}
