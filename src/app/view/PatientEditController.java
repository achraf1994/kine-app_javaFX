package app.view;


	import app.model.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


	
	public class PatientEditController {

	    @FXML
	    private TextField nomField;
	    @FXML
	    private TextField prenomField;
	    @FXML
	    private TextField prixField;
	    @FXML
	    private TextField numprisField;
	    @FXML
	    private TextField assurerField;
	    @FXML
	    private TextField datePriseField;
	    @FXML
	    private TextField numAssurField;
	    @FXML
	    private TextField nombSField;


	    private Stage dialogStage;
	    private Patient patient;
	    private boolean okClicked = false;

	   
	    @FXML
	    private void initialize() {
	    }

	   
	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }

	   
	    public void setPatient(Patient patient) {
	        this.patient = patient;
          if (patient!= null){
	        nomField.setText(patient.getnom());
	        prenomField.setText(patient.getprenom());
	        prixField.setText(Double.toString(patient.getprix()));
	        numprisField.setText(Integer.toString(patient.getNumPriseCharge()));
	        assurerField.setText(patient.getAssurer());
	        numAssurField.setText(Integer.toString(patient.getNumAssur()));
	        nombSField.setText(Integer.toString(patient.getnombreS()));
	        datePriseField.setText(patient.getdatePrisChrage());
          }
	    }

	    /**
	     * Returns true if the user clicked OK, false otherwise.
	     */
	    public boolean isOkClicked() {
	        return okClicked;
	    }

	    /**
	     * Called when the user clicks ok.
	     */
	    @FXML
	    private void handleOk() throws Exception {
	       
	    	   
	            patient.setnom(nomField.getText());
	            patient.setprenom(prenomField.getText());
	            patient.setprix(Double.parseDouble(prixField.getText()));
	            patient.setNumPriseCharge(Integer.parseInt(numprisField.getText()));
	            patient.setAssurer(assurerField.getText());
	            patient.setNumAssur(Integer.parseInt(numAssurField.getText()));
                patient.setnombreS(Integer.parseInt(nombSField.getText()));
               
                patient.setdatePrisChrage(datePriseField.getText());
	            okClicked = true;
	            dialogStage.close();
	      
	    }
     
	    /**
	     * Called when the user clicks cancel.
	     */
	    @FXML
	    private void handleCancel() {
	        dialogStage.close();
	    }

	   
	}