package app;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import app.model.Depenses;
import app.model.KineInfo;
import app.model.Patient;
import app.model.PatientdbConnect;
import app.view.MainactivityController;
import app.view.PatientEditController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {




	 private ObservableList<Patient> patientData = FXCollections.observableArrayList();
	 private ObservableList<Depenses> depensesData = FXCollections.observableArrayList();


		/**
	     * Constructor
	     */
	 public MainApp() {
	       

	    }
	 public ObservableList<Patient> getPatientData() {
	        return patientData;
	    }

	 public ObservableList<Depenses> getDepensesData() {
	        return depensesData;
	    }

	private Stage primaryStage;
    private BorderPane rootLayout;
	public KineInfo profile  ;
	public ResultSet rs1;
	public String nomm ;

    public KineInfo getProfile() {
		return profile;
	}
	public void setProfile(KineInfo profile) {
		this.profile = profile;
	}

	@Override
    public void start(Stage primaryStage) throws Exception  {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("kiné logiciel");

        initRootLayout();



        showPersonOverview();

        PatientdbConnect.createDB();
      // create tables if not exist
        PatientdbConnect.createTable();
        PatientdbConnect.createTableKine();
        PatientdbConnect.createTableDepense();
        patientData.clear();

    //  extract info from query result and put them in list of patient
        ResultSet rs = PatientdbConnect.select("patient");
        while (rs.next())
		{
        	patientData.add(new Patient(rs.getString("nom"),
        			rs.getString("prenom"),
        			rs.getInt("seanceNumber"),
        			rs.getDouble("prix"),
        			rs.getString("assurer"),
        			rs.getInt("assurNum"),
        			rs.getInt("prisechargeNum"),
        			rs.getDate("dateprisCharge").toString()));
        }

        ResultSet rs2 = PatientdbConnect.select("depense");
        while (rs2.next())
		{
        	depensesData.add(new Depenses(rs2.getString("nomProduit"),
        			rs2.getDouble("prix"),

        			rs2.getDate("dateprisCharge").toString()));
        }
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //set profile


    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load mainactivity fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainActivity.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

         // Give the controller access to the main app.
            MainactivityController controller = loader.getController();
            controller.setMainApp(this);

            rootLayout.setCenter(personOverview);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean showPatientEdit(Patient patient) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PatientEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Patient");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the patient into the controller.
            PatientEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPatient(patient);

            // Show the dialog
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}