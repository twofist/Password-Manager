package manager;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

class NewBundleDialog extends Dialog<Bundle> {
	
	private TextField tfGroupname;
	private String imagepath;
	private Stage stage;
	
	NewBundleDialog(Stage stage) {
		this.stage = stage;
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
		
		Label lblGroupname = new Label("GroupName:");
		tfGroupname = new TextField();
		
		Button btnAddImage = new Button("Add Image");
		btnAddImage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().add(
						new ExtensionFilter("Image", "*.png"));
				chooser.setTitle("Open");
				File file = chooser.showOpenDialog(stage);
				if (file != null) {
					imagepath = file.toURI().toString();
					Image image = new Image(imagepath, 50, 50, false ,false);
					ImageView iv = new ImageView(image);
					pane.add(iv, 1, 1);
				}
			}
		});
		
		pane.add(lblGroupname, 0, 0);
		pane.add(tfGroupname, 1, 0);
		pane.add(btnAddImage, 0, 1);
		
		
		ScrollPane scroller = new ScrollPane(pane);
		this.getDialogPane().setContent(scroller);
		this.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
	}

	private void addResultConverter() {
		this.setResultConverter(value -> {
			if (value.getButtonData().equals(ButtonData.OK_DONE))
				return checkFields();
			return null;
		});
	}

	private Bundle checkFields() {
		String name = tfGroupname.getText();
		if (name.trim().isEmpty())
			return null;
		
		Bundle group = new Bundle(name);
		group.setImage(imagepath);

		return group;
	}


}