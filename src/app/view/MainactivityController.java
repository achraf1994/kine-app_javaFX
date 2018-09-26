package app.view;


import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import com.itextpdf.kernel.pdf.PdfDocument;

import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;


import app.MainApp;
import app.model.KineInfo;
import app.model.Patient;
import app.model.Depenses;
import app.model.PatientdbConnect;

public class MainactivityController {
	@FXML
	private TableView<Patient> patientTable;
	@FXML
	private TableColumn<Patient, String> nomColumn;
	@FXML
	private TableColumn<Patient, String> prenomColumn;

	@FXML
	private TableView<Depenses> depensesTable;
	@FXML
	private TableColumn<Depenses, String> nomProduitColumn;
	@FXML
	private TableColumn<Depenses, Double> prixProduitColumn;
	@FXML
	private TableColumn<Depenses, String> dateProduitColumn;

	@FXML
	private Label nomLabel;
	@FXML
	private Label prenomLabel;
	@FXML
	private Label dateprisechargeLabel;
	@FXML
	private Label numPriseChargeLabel;
	@FXML
	private Label assurerLabel;
	@FXML
	private Label numAssurLabel;
	@FXML
	private Label nombreSLabel;
	@FXML
	private Label prixLabel;


	@FXML
    private  TextField dateField;
	@FXML
	private Label totalLabel;

	@FXML
    private  TextField nomAchatField;
	@FXML
    private  TextField prixAchatField;

	@FXML
    private  TextField nomPrenomField;
    @FXML
    private  TextField cnssField;
    @FXML
    private  TextField codeField;
    @FXML
    private  TextField mfField;
    @FXML
    private  TextField ripField ;


	// Reference to the main application.
	private MainApp mainApp;
	public ResultSet rs1;



	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public MainactivityController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 * @throws SQLException
	 */
	@FXML
	private void initialize() throws SQLException {
		// Initialize the patient table with the two columns.
		nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());

		nomProduitColumn.setCellValueFactory(cellDatay -> cellDatay.getValue().productProperty());
		dateProduitColumn.setCellValueFactory(cellData -> cellData.getValue().dateAchatProperty());
		prixProduitColumn.setCellValueFactory(cellData -> cellData.getValue().prixProductProperty().asObject());

		// Clear person details.
		showDetails(null);



		rs1 = PatientdbConnect.select("profile");

        if (rs1.next()) {
              System.out.println(rs1.getString("nom"));
              nomPrenomField.setText(rs1.getString("nom"));
              mfField.setText(rs1.getString("mf"));
              codeField.setText(rs1.getString("code"));
              cnssField.setText(rs1.getString("cnss"));
              ripField.setText(rs1.getString("rip"));
			}


		// Listen for selection changes and show the person details when
		// changed.
		patientTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDetails(newValue));

	}


	@FXML
	private void deletePatient() throws SQLException {
		int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
			String query = " delete from patient where nom='" + selectedPatient.getnom() + "' and  prenom='"
					+ selectedPatient.getprenom() + "';";
			PreparedStatement preparedStmt = PatientdbConnect.con.prepareStatement(query);

			preparedStmt.execute();
			patientTable.getItems().remove(selectedIndex);

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Aucun patient selectionné");
			alert.setContentText("Merci de selectioné un patient.");

			alert.showAndWait();
		}
	}

	@FXML
	private void newPatient() throws SQLException {

		Patient pat = new Patient();
		boolean okClicked = mainApp.showPatientEdit(pat);
		if (okClicked) {

			mainApp.getPatientData().add(pat);
			// TODO: insert to mysql new row
			// the mysql insert statement

			String query = " insert into patient (nom,prenom,prisechargeNum,assurer,assurNum,seanceNumber,prix)"
					+ " values (?,?,?,?,?,?,?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = PatientdbConnect.con.prepareStatement(query);
			preparedStmt.setString(1, pat.getnom());
			preparedStmt.setString(2, pat.getprenom());

			preparedStmt.setInt(3, pat.getNumPriseCharge());
			preparedStmt.setString(4, pat.getAssurer());
			preparedStmt.setInt(5, pat.getNumAssur());
			preparedStmt.setInt(6, pat.getnombreS());
			preparedStmt.setDouble(7, pat.getprix());
			preparedStmt.execute();
		}
	}


	@FXML
	private void addProduct() throws SQLException {

		Depenses dep = new Depenses();
		dep.setproduct(nomAchatField.getText());
		dep.setprixProduct(Double.parseDouble(prixAchatField.getText()));

			mainApp.getDepensesData().add(dep);
			// TODO: insert to mysql new row
			// the mysql insert statement

			String query = " insert into depense (nomProduit,prix)"
					+ " values (?,?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = PatientdbConnect.con.prepareStatement(query);
			preparedStmt.setString(1, dep.getproduct());

			preparedStmt.setDouble(2, dep.getprixProduct());
			preparedStmt.execute();
			nomAchatField.setText("");
			prixAchatField.setText("");
	}

	@FXML
	private void editPatient() throws SQLException {
		Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
		if (selectedPatient != null) {
			String nom = selectedPatient.getnom();
			String prenom = selectedPatient.getprenom();
			boolean okClicked = mainApp.showPatientEdit(selectedPatient);
			if (okClicked) {
				showDetails(selectedPatient);
				// TODO update in mysql selectedpatient
				String query = " update  patient set nom='" + selectedPatient.getnom() + "'," + "prenom='"
						+ selectedPatient.getprenom() + "'," + "prisechargeNum='" + selectedPatient.getNumPriseCharge()
						+ "'," + "dateprisCharge='" + selectedPatient.getdatePrisChrage() + "'," + "assurer='"
						+ selectedPatient.getAssurer() + "'," + "assurNum=" + selectedPatient.getNumAssur() + ","
						+ "seanceNumber=" + selectedPatient.getnombreS() + "," + "prix=" + selectedPatient.getprix()
						+ " where nom='" + nom + "' and prenom='" + prenom + "';";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = PatientdbConnect.con.prepareStatement(query);

				preparedStmt.execute();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}
	@FXML
	private void saveProfile() throws SQLException {
		PreparedStatement Stmt = PatientdbConnect.con.prepareStatement("delete from profile where id>0 ;");

		Stmt.execute();
		String query = " insert into profile (nom,code,rip,mf,cnss)"
		        + " values (?,?,?,?,?);";

		PreparedStatement preparedStmt = PatientdbConnect.con.prepareStatement(query);
		preparedStmt.setString(1, nomPrenomField.getText());
		preparedStmt.setString(2, codeField.getText());

		preparedStmt.setString(3, ripField.getText());
		preparedStmt.setString(4,mfField.getText());
		preparedStmt.setString(5,cnssField.getText());

		preparedStmt.execute();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		patientTable.setItems(mainApp.getPatientData());
		depensesTable.setItems(mainApp.getDepensesData());
	}

	private void showDetails(Patient patient) {
		if (patient != null) {
			// Fill the labels with info from the patient object.
			nomLabel.setText(patient.getnom());
			prenomLabel.setText(patient.getprenom());
			prixLabel.setText(Double.toString(patient.getprix()));
			numAssurLabel.setText(Integer.toString(patient.getNumAssur()));
			assurerLabel.setText(patient.getAssurer());
			nombreSLabel.setText(Integer.toString(patient.getnombreS()));
			numPriseChargeLabel.setText(Integer.toString(patient.getNumPriseCharge()));
			dateprisechargeLabel.setText(patient.getdatePrisChrage().toString());// patient.getdatePrisChrage().toString()
			// TODO: We need a way to convert the birthday into a String!
			// birthdayLabel.setText(...);
		} else {
			// patient is null, remove all the text.
			nomLabel.setText("");
			prenomLabel.setText("");
			prixLabel.setText("");
			numAssurLabel.setText("");
			assurerLabel.setText("");
			nombreSLabel.setText("");
			numPriseChargeLabel.setText("");
			dateprisechargeLabel.setText("");
		}
	}
	// set profile
	public  void showProfile(KineInfo profile) {

      if (profile!= null){
        nomPrenomField.setText(profile.getNomPrenom());
        codeField.setText(profile.getCodeConv());
        mfField.setText(profile.getMf());
        cnssField.setText(profile.getCnss());
        ripField.setText(profile.getRip());
      }else {
    	  nomPrenomField.setText("");
          codeField.setText("");
          mfField.setText("");
          cnssField.setText("");
          ripField.setText("");

      }
    }


	@FXML
	private void facture() throws IOException {
        String dossier = "C:/Users/acer/Desktop/Facture Patient";
        if (!new File(dossier).exists()) new File(dossier).mkdirs();

		int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			// get patient information
			Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
			String nom = selectedPatient.getnom(), prenom = selectedPatient.getprenom(),
					assurer = selectedPatient.getAssurer(), dateprise = selectedPatient.getdatePrisChrage();
			int numAss = selectedPatient.getNumAssur(), nombreS = selectedPatient.getnombreS(),
					nPrisCharge = selectedPatient.getNumPriseCharge();
			double prix = selectedPatient.getprix();
			double prixHT = (int) ((prix * 0.94) * 1000) / 1000.0, htTotal = prixHT * nombreS,
					prixTotal = prix * nombreS;
			int tva1 = (int) ((prixTotal - htTotal) * 1000);
			double tva = tva1 / 1000.000;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			// print patient info into pdf file
			PdfDocument pdfDoc = new PdfDocument(new PdfReader("template/123.pdf"),
					new PdfWriter("C:/Users/acer/Desktop/Facture Patient/" + nom+" " + prenom + ".pdf"));
			// add content
			Document doc = new Document(pdfDoc);

			PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
			Text nomprenom = new Text(nom + " " + prenom).setFont(bold);
			doc.add(new Paragraph(nomprenom).setFixedPosition(243, 385, 200));

			Text assurerr = new Text(assurer).setFont(bold);
			doc.add(new Paragraph(assurerr).setFixedPosition(243, 358, 200));
			Text nassurerr = new Text(numAss + "").setFont(bold);
			doc.add(new Paragraph(nassurerr).setFixedPosition(243, 330, 200));
			Text npris = new Text(nPrisCharge + "").setFont(bold);
			doc.add(new Paragraph(npris).setFixedPosition(243, 284, 200));
			Text date1 = new Text(dateprise).setFont(bold);
			doc.add(new Paragraph(date1).setFixedPosition(243, 258, 200));
			Text nSeance = new Text(nombreS + "").setFont(bold);
			doc.add(new Paragraph(nSeance).setFixedPosition(243, 208, 200));
			Text prixx = new Text(prix + "").setFont(bold);
			doc.add(new Paragraph(prixx).setFixedPosition(243, 178, 200));
			Text tvva = new Text(tva + "").setFont(bold);
			doc.add(new Paragraph(tvva).setFixedPosition(243, 140, 200));
			Text ttc = new Text(prixTotal + "").setFont(bold);
			doc.add(new Paragraph(ttc).setFixedPosition(243, 100, 200));
			Text htTot = new Text(htTotal + "").setFont(bold);
			doc.add(new Paragraph(htTot).setFixedPosition(515, 100, 200));
			Text ht = new Text(prixHT + "").setFont(bold);
			doc.add(new Paragraph(ht).setFixedPosition(515, 178, 200));
			Text datee = new Text(dateFormat.format(date)).setFont(bold);
			doc.add(new Paragraph(datee).setFixedPosition(665, 43, 200));

			Text nompre = new Text(nomPrenomField.getText()).setFont(bold);
			doc.add(new Paragraph(nompre).setFixedPosition(185, 545, 200));
			Text code1 = new Text(codeField.getText()).setFont(bold);
			doc.add(new Paragraph(code1).setFixedPosition(185, 512, 200));
			Text rip  = new Text(ripField.getText()).setFont(bold);
			doc.add(new Paragraph(rip).setFixedPosition(185, 485, 200));
			Text mf = new Text(mfField.getText()).setFont(bold);
			doc.add(new Paragraph(mf).setFixedPosition(600, 548, 200));
			Text cnss = new Text(cnssField.getText()).setFont(bold);
			doc.add(new Paragraph(cnss).setFixedPosition(600, 516, 200));
			doc.close();

			pdfDoc.close();



		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Aucun patient selectionné");
			alert.setContentText("Merci de selectioné un patient.");

			alert.showAndWait();
		}

	}
	@FXML
	private void calculTotal () throws SQLException{
		Double some = 0.0 ;
		String query = "select * from depense where dateprisCharge >='"+dateField.getText()+"';";
		Statement st = PatientdbConnect.con.createStatement();
		ResultSet rs = st.executeQuery(query);
		 while (rs.next())
			{

	        			some=some+rs.getDouble("prix");
	        			
            }
		 some =(int) (some * 1000)/1000.0 ;
		totalLabel.setText(some.toString());

	}




}