package app.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Depenses {

	private  StringProperty product;
    private  StringProperty dateAchat;
    private  DoubleProperty prixProduct ;

    public Depenses(String product,Double prixProduct,String date) {
        this.product = new SimpleStringProperty(product);
        this.dateAchat = new SimpleStringProperty(date);


        this.prixProduct = new SimpleDoubleProperty(prixProduct);
    }

    public Depenses() {
        this.product = new SimpleStringProperty(" ");
        this.dateAchat = new SimpleStringProperty(" ");


        this.prixProduct = new SimpleDoubleProperty(0.0);
    }
    public String getdateAchat() {
        return dateAchat.get();
    }

    public void setdateAchat(String nom) {
        this.dateAchat.set(nom);
    }

    public StringProperty dateAchatProperty() {
        return dateAchat;
    }

    public String getproduct() {
        return product.get();
    }

    public void setproduct(String nom) {
        this.product.set(nom);
    }

    public StringProperty productProperty() {
        return product;
    }

    public Double getprixProduct() {
        return prixProduct.get();
    }

    public void setprixProduct(Double prix) {
        this.prixProduct.set(prix);
    }

    public DoubleProperty prixProductProperty() {
        return prixProduct;
    }




}
