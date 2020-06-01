package elaborato_ing_sw.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import elaborato_ing_sw.model.Manager;
import elaborato_ing_sw.model.Role;

public class ManagerEditDialogController {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private DatePicker dateOfBirthField;
	@FXML
	private TextField serialNumberField;
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private ChoiceBox<Role> roleField;

	private Stage dialogStage;
	private static Manager manager;
	private boolean okClicked = false;
	
	private boolean isNewMode; //1 per new, 0 per edit
	
	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person
	 */
	public void setManager(Manager m) {
		manager = m;
		System.out.println("---");
		System.out.println("manager: " + manager);
		
		roleField.getItems().setAll(Role.values());
		
		if(manager == null) {
			isNewMode = true; 
			return;
		}
		
		firstNameField.setText(manager.getName());
		lastNameField.setText(manager.getSurname());
		dateOfBirthField.setValue(manager.getDateOfBirth());
		serialNumberField.setText(Integer.toString(manager.getSerialNumber()));
		passwordField.setDisable(true);
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if(!isInputValid())
			return;
		
		if(manager == null) {
			manager = new Manager(
					firstNameField.getText(),
					lastNameField.getText(),
					dateOfBirthField.getValue(),
					passwordField.getText(),
					Integer.parseInt(serialNumberField.getText()), 
					(Role)roleField.getValue()
					);
		} else {
			manager.setName(firstNameField.getText());
			manager.setSurname(lastNameField.getText());
			manager.setDateOfBirth(dateOfBirthField.getValue());
			manager.setSerialNumber(Integer.parseInt(serialNumberField.getText()));			
			manager.getCredentials().setUser(serialNumberField.getText());	
			manager.setRole((Role)roleField.getValue());
		}

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

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (firstNameField.getText() == null || firstNameField.getText().length() == 0)
			errorMessage += "No valid first name!\n";
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0)
			errorMessage += "No valid last name!\n";
		
		if (dateOfBirthField.getValue() == null)
			errorMessage += "No valid date of birth!\n";

		if (serialNumberField.getText() == null || serialNumberField.getText().length() == 0)
			errorMessage += "No valid serial number!\n";
		
		if (isNewMode && (passwordField.getText() == null || passwordField.getText().length() == 0))
			errorMessage += "No valid password!\n";

		if (roleField.getValue() == null)
			errorMessage += "No valid role!\n";

		if (errorMessage.length() == 0)
			return true;
		else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
	
	/**
	 * Restituisce l'oggetto manager modificato oppure creato nuovo
	 */
	public static Manager getManager() {
		return manager;
	}
	
}