package at.ac.tuwien.sepm.assignment.individual.restaurant.controller;

import at.ac.tuwien.sepm.assignment.individual.application.Changescene;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.service.Productmanagement;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Productscontroller implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    public TableView<Products> tblproduct;

    @FXML
    public TableColumn<Products, String> colname, coltext, colcat, coltax;

    @FXML
    public TableColumn<Products, Double> colnetto, colbrutto;

    @FXML
    public TableColumn<Products, Timestamp> coladd;

    @FXML
    public TextField tfname, tfprice, tftext;

    @FXML
    public ComboBox cbcategory, cbtaxclass;

    private ObservableList<Products> observableList = null;

    private Productmanagement productmanagement = new Productmanagement();

    public static Products selectedProduct = null;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LOG.info("Initializing Productscontroller");

        tblproduct.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        tfprice.setTextFormatter(formatter);

        displayProducts();

    }

    public void RefreshProductlist(List<Products> list) {

        LOG.info("Set Products");

        tblproduct.setItems(null);

        if (list != null) {

            observableList = FXCollections.observableList(list);

            colname.setCellValueFactory(new PropertyValueFactory<>("name"));
            coltext.setCellValueFactory(new PropertyValueFactory<>("text"));
            colnetto.setCellValueFactory(new PropertyValueFactory<>("netto"));
            colbrutto.setCellValueFactory(new PropertyValueFactory<>("brutto"));
            coladd.setCellValueFactory(new PropertyValueFactory<>("addeddate"));
            colcat.setCellValueFactory(new PropertyValueFactory<>("category"));
            coltax.setCellValueFactory(new PropertyValueFactory<>("tax"));


            tblproduct.setRowFactory( tv -> {
                TableRow<Products> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        Products rowData = row.getItem();
                        tfname.setText(rowData.getName());
                        tftext.setText(rowData.getText());
                        tfprice.setText(String.valueOf(rowData.getNetto()));
                        cbcategory.setValue(rowData.getCategory());
                        cbtaxclass.setValue(rowData.getTax());
                    }
                });
                return row ;
            });
        }

        tblproduct.setItems(observableList);
    }


    @FXML
    protected void displayProducts() {

        LOG.info("Get Products");

        List<Products> result = null;
        try {
            result = productmanagement.displayProducts();
        } catch (DatabaseException e) {
            LOG.error("Error", e.getMessage());
        }
        RefreshProductlist(result);
    }

    @FXML
    protected void onSearchClickEvent() {

        LOG.info("Search Product");

        List<Products> searchresult = null;

        try {
            searchresult = productmanagement.searchProduct(tfname.getText(), String.valueOf(cbcategory.getValue()));
        } catch (DatabaseException e) {
            LOG.error("Error", e.getMessage());
        }
        RefreshProductlist(searchresult);

    }


    @FXML
    private void onNewProductClickEvent() throws Exception {

        LOG.info("Clicked on New Product");

        if (tfname.getText().equals("") || tfprice.getText().equals("") ||
            String.valueOf(cbcategory.getValue()).equals("") || String.valueOf(cbtaxclass.getValue()).equals("")) {
            JOptionPane.showMessageDialog(null, "You need name, price, category, taxclass to make new product");
        }else if(Double.parseDouble(tfprice.getText()) < 0){
            JOptionPane.showMessageDialog(null, "You can't set negative price for a product");
        } else {

            Products newproduct = new Products(0, tfname.getText(), tftext.getText(),
                Double.parseDouble(tfprice.getText()), 0.0, Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), String.valueOf(cbcategory.getValue()),
                String.valueOf(cbtaxclass.getValue()));
            productmanagement.addProducttoDB(newproduct);


            tfname.clear();
            tftext.clear();
            tfprice.clear();
            cbtaxclass.getSelectionModel().clearSelection();
            cbcategory.getSelectionModel().clearSelection();


            displayProducts();
        }

    }

    @FXML
    private void onUpdateProductClickEvent() throws DatabaseException{

        LOG.info("Clicked on Update Product");

        if (tfname.getText().equals("") || tfprice.getText().equals("") ||
            String.valueOf(cbcategory.getValue()).equals("") || String.valueOf(cbtaxclass.getValue()).equals("")) {
            JOptionPane.showMessageDialog(null, "You need name, price, category, taxclass to update");
        }else if(Double.parseDouble(tfprice.getText()) < 0){
            JOptionPane.showMessageDialog(null, "You can't set negative price for a product");
        } else {

            Products product = new Products(tblproduct.getSelectionModel().getSelectedItem().getId(),
                tfname.getText(), tftext.getText(), Double.parseDouble(tfprice.getText()),
                0.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                String.valueOf(cbcategory.getValue()), String.valueOf(cbtaxclass.getValue()));
            productmanagement.updateProduct(product);

            tfname.clear();
            tftext.clear();
            tfprice.clear();
            cbtaxclass.getSelectionModel().clearSelection();
            cbcategory.getSelectionModel().clearSelection();

            displayProducts();
        }


    }

    @FXML
    private void onDeleteClickEvent() throws DatabaseException{

        LOG.info("Clicked on Delete");

        if (tblproduct.getSelectionModel().getSelectedItem() != null) {
            int p = JOptionPane.showConfirmDialog(null, "Do you really want to delete?", "Delete", JOptionPane.YES_NO_OPTION);
            selectedProduct = tblproduct.getSelectionModel().getSelectedItem();

            if (p == 0) {
                if (!productmanagement.isProtuctonOrder(selectedProduct.getId())) {
                    productmanagement.deleteProduct(selectedProduct.getId());
                    tblproduct.getItems().removeAll(selectedProduct);
                } else {
                    JOptionPane.showMessageDialog(null, "This product is on an Order, you can't delete it!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "You need to choose a product first!");
        }

    }

    @FXML
    public void onAddtoOrderClickEvent() {

        LOG.info("Clicked on Add to Order");

        if (tblproduct.getSelectionModel().getSelectedItem() != null) {
            selectedProduct = tblproduct.getSelectionModel().getSelectedItem();
            try {
                Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                int table = (int) (JOptionPane.showInputDialog(null, "Choose Table", "Table", JOptionPane.QUESTION_MESSAGE, null, numbers, numbers[0]));
                productmanagement.addProducttoOrder(selectedProduct.getId(), table);
                JOptionPane.showMessageDialog(null, "Added to Order");
                Changescene.loadScene(Changescene.TABLEMANAGEMENT);
            } catch (Exception e) {
                LOG.error("Error:" + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "You need to choose a product first!");
        }

    }

    @FXML
    private void onDetailedViewClickEvent() {

        LOG.info("Clicked on Detailed View");

        if (tblproduct.getSelectionModel().getSelectedItem() != null) {
            selectedProduct = tblproduct.getSelectionModel().getSelectedItem();
            Changescene.loadScene(Changescene.DETAILEDPRODUCT);
        } else {
            JOptionPane.showMessageDialog(null, "You need to choose a product first!");
        }
    }

    @FXML
    private void onBackClickEvent() {

        LOG.info("Clicked on Back, go to Mainscene");

        Changescene.loadScene(Changescene.MAINSCENE);

    }


}
