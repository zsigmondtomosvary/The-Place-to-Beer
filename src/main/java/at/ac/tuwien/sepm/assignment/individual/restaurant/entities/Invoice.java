package at.ac.tuwien.sepm.assignment.individual.restaurant.entities;

import java.sql.Timestamp;


public class Invoice {

    public static enum Status {
        Open, Closed
    }

    private int id;
    private int tablenumber;
    private Timestamp addeddate;
    private Timestamp billdate;
    private Status status;
    private String products;
    private double billprice;
    private String taxclasses;
    private String payment;


    public Invoice(int id, int tablenumber, Timestamp addeddate, Timestamp billdate, Status status, String products, double billprice, String taxclasses, String payment) {
        this.id = id;
        this.tablenumber = tablenumber;
        this.addeddate = addeddate;
        this.billdate = billdate;
        this.status = status;
        this.products = products;
        this.billprice = billprice;
        this.taxclasses = taxclasses;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTablenumber() {
        return tablenumber;
    }

    public void setTablenumber(int tablenumber) {
        this.tablenumber = tablenumber;
    }

    public Timestamp getAddeddate() {
        return addeddate;
    }

    public void setAddeddate(Timestamp addeddate) {
        this.addeddate = addeddate;
    }

    public Timestamp getBilldate() {
        return billdate;
    }

    public void setBilldate(Timestamp billdate) {
        this.billdate = billdate;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) { this.products = products; }

    public String getTaxclasses() {
        return taxclasses;
    }

    public void setTaxclasses(String taxclasses) {
        this.taxclasses = taxclasses;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public double getBillprice() {
        return billprice;
    }

    public void setBillprice(double billprice) {
        this.billprice = billprice;
    }

}
