package manager;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

class NewDataDialog extends Dialog<Data> {
	
	private TextField tfUsername;
	private TextField tfEmail;
	private TextField tfPassword;
	
	NewDataDialog() {
		addButtons();
		createInputMask();
		addResultConverter();
	}
	
	void addButtons() {
		ButtonType btnOK = new ButtonType("Ok", ButtonData.OK_DONE);
		ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(btnOK, btnCancel);
	}

	
	void createInputMask() {
		GridPane pane = new GridPane();
		
		Label lblUsername = new Label("Username:");
		tfUsername = new TextField();
		Label lblEmail = new Label("Email:");
		tfEmail = new TextField();
		Label lblPassword = new Label("Password:");
		tfPassword = new TextField();
		
		pane.add(lblUsername, 0, 0);
		pane.add(tfUsername, 1, 0);
		
		pane.add(lblEmail, 0, 1);
		pane.add(tfEmail, 1, 1);
		
		pane.add(lblPassword, 0, 2);
		pane.add(tfPassword, 1, 2);
		
		ScrollPane scroller = new ScrollPane(pane);
		this.getDialogPane().setContent(scroller);
	}

	void addResultConverter() {
		this.setResultConverter(value -> {
			if (value.getButtonData().equals(ButtonData.OK_DONE))
				return checkFields();
			return null;
		});
	}

	Data checkFields() {
		if (tfPassword.getText().trim().isEmpty())
			return null;
		
		Data data = new Data(tfPassword.getText().trim());
		data.setEmail(tfEmail.getText().trim());
		data.setUsername(tfUsername.getText().trim());

		return data;
	}


}
