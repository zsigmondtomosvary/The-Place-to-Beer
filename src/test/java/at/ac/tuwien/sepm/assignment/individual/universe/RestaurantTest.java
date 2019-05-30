package at.ac.tuwien.sepm.assignment.individual.universe;

import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Invoice;
import at.ac.tuwien.sepm.assignment.individual.restaurant.entities.Products;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.DatabaseException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.exception.ProductalreadyexistException;
import at.ac.tuwien.sepm.assignment.individual.restaurant.service.Invoicemanagement;
import at.ac.tuwien.sepm.assignment.individual.restaurant.service.Productmanagement;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.core.Is.is;


public class RestaurantTest {

    private Productmanagement productmanagement = new Productmanagement();
    private Invoicemanagement invoicemanagement = new Invoicemanagement();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test(expected = DatabaseException.class)
    public void testUpdateProduct() throws DatabaseException {

        Products products = new Products(0,null,"Coca",1.5,1.8,null,null,null,null);
        productmanagement.updateProduct(products);
    }

    @Test
    public void testAddProduct()throws ProductalreadyexistException {
        Products products = new Products(1000,"Cola","Coca",1.5,1.8,null,null,"Getranke","normal");
        Assert.assertThat(productmanagement.addProducttoDB(products), is(true));
    }

    @Test
    public void testProductonOrder() throws DatabaseException {
        Products products = new Products(5,"Cola","Coca",1.5,1.8,null,null,"Getranke","normal");
        Assert.assertThat(productmanagement.isProtuctonOrder(products.getId()), is(false));
    }

    @Test(expected = DatabaseException.class)
    public void testAddProducttoOrder() throws DatabaseException {
        int table = -5;
        Products products = new Products(-5,"Cola","Coca",1.5,1.8,null,null,"Getranke","normal");
        productmanagement.addProducttoOrder(products.getId(), table);
    }

}
