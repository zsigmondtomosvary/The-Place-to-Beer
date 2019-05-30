package at.ac.tuwien.sepm.assignment.individual.restaurant.entities;

import java.sql.Timestamp;

public class Products {

    private int id;
    private String name;
    private String text;
    private double netto;
    private double brutto;
    private Timestamp addeddate;
    private Timestamp lastediteddate;
    private String category;
    private String tax;

    public Products(int id, String name, String text, double netto, double brutto,Timestamp addeddate, Timestamp lastediteddate, String category, String tax) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.netto = netto;
        this.brutto = brutto;
        this.addeddate = addeddate;
        this.lastediteddate = lastediteddate;
        this.category = category;
        this.tax = tax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public Timestamp getAddeddate() {
        return addeddate;
    }

    public void setAddeddate(Timestamp addeddate) {
        this.addeddate = addeddate;
    }

    public Timestamp getLastediteddate() {
        return lastediteddate;
    }

    public void setLastediteddate(Timestamp lastediteddate) {
        this.lastediteddate = lastediteddate;
    }

    @Override
    public String toString() {
        return "Product{" +
            "name=" + name + '\'' +
            ", text=" + text +
            ", nettoprice='" + netto + '\'' +
            ", bruttoprice='" + brutto + '\'' +
            ", category='" + category + '\'' +
            ", tax='" + tax + '\'' +
            ", addeddate=" + addeddate +
            ", lastediteddate=" + lastediteddate +
            '}';
    }
}
