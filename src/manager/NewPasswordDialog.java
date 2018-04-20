package manager;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

class NewPasswordDialog extends Dialog<String>{

	private TextField tfPassword;
	
	NewPasswordDialog() {
		addButtons();
		createInputMask();
		addResultConverter();
	}
	
	private void addButtons() {
		ButtonType btnOK = new ButtonType("Ok", ButtonData.OK_DONE);
		ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(btnOK, btnCancel);
	}

	
	private void createInputMask() {
		GridPane pane = new GridPane();

		Label lblPassword = new Label("Password:");
		tfPassword = new TextField();
		
		pane.add(lblPassword, 0, 0);
		pane.add(tfPassword, 0, 1);
		
		ScrollPane scroller = new ScrollPane(pane);
		this.getDialogPane().setContent(scroller);
	}

	private void addResultConverter() {
		this.setResultConverter(value -> {
			if (value.getButtonData().equals(ButtonData.OK_DONE))
				return checkFields();
			return null;
		});
	}

	private String checkFields() {
		if (tfPassword.getText().trim().isEmpty())
			return null;
		
		String password = new String(tfPassword.getText().trim());

		return password;
	}
	
}
