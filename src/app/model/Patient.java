package app.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Patient {

    private  StringProperty nom;
    private  StringProperty prenom;
    private  StringProperty assurer ;
    private  IntegerProperty numAssur ;
    private  IntegerProperty nombreS;
    private  IntegerProperty numPriseCharge;
    private  DoubleProperty prix ;

    private StringProperty datePrisChrage;







    public Patient(String nom, String prenom,int nombrS,Double prix ,String assurer,int numAssur,int numpris,String date) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);

        // Some initial dummy data, just for convenient testing.
        this.nombreS = new SimpleIntegerProperty(nombrS);
        this.prix = new SimpleDoubleProperty(prix);
        this.assurer = new SimpleStringProperty(assurer);
        this.numAssur = new SimpleIntegerProperty(numAssur);
        this.numPriseCharge = new SimpleIntegerProperty(numpris);
        this.datePrisChrage = new SimpleStringProperty(date);
    }



	public Patient() {

		this.nom = new SimpleStringProperty("");
	    this.prenom = new SimpleStringProperty("");
		this.prix = new SimpleDoubleProperty(11.5);
	    this.nombreS = new SimpleIntegerProperty(12);
	    this.assurer = new SimpleStringProperty("assurer");
	    this.numAssur = new SimpleIntegerProperty();
	    this.numPriseCharge = new SimpleIntegerProperty();
        this.datePrisChrage = new SimpleStringProperty("aujourd'hui");
	}



	public String getnom() {
        return nom.get();
    }

    public void setnom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getprenom() {
        return prenom.get();
    }

    public void setprenom(String prenom) {
        this.prenom.set(prenom);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public int getnombreS() {
        return nombreS.get();
    }

    public void setnombreS(int  nombreS) {
        this.nombreS.set(nombreS);
    }

    public IntegerProperty nombreSProperty() {
        return nombreS;
    }

    public Double getprix() {
        return prix.get();
    }

    public void setprix(Double prix) {
        this.prix.set(prix);
    }

    public DoubleProperty prixProperty() {
        return prix;
    }





	public String getdatePrisChrage() {
        return datePrisChrage.get();
    }

    public void setdatePrisChrage(String datePrisChrage) {
        this.datePrisChrage.set(datePrisChrage);
    }

    public StringProperty datePrisChrageProperty() {
        return datePrisChrage;
    }

	/**
	 * @return the assurer
	 */
	public String getAssurer() {
		return assurer.get();
	}

	public void setAssurer(String  assurer) {
	        this.assurer.set(assurer);
	    }
	public StringProperty assurerProperty() {
        return assurer;
    }

	/**
	 * @return the nassurer
	 */
	public int  getNumAssur() {
		return numAssur.get();
	}
	public void setNumAssur(int n) {
	        this.numAssur.set(n);
	    }
	public IntegerProperty numAssurProperty() {
        return numAssur;
    }

	/**
	 * @return the numPriseCharge
	 */
	public int getNumPriseCharge() {
		return numPriseCharge.get();
	}
	public void setNumPriseCharge(int  p) {
	        this.numPriseCharge.set(p);
	    }
	public IntegerProperty numPriseChargeProperty() {
        return numPriseCharge;
    }
}