package manager;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import game.GameWindow;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PasswordManagerGUI extends Application{
	
	private List<Bundle> bundlelist;
	private ObservableList<Data> bundledata;
	private TableView<Data> table;
	private Bundle selectedbundle;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		setImage(primaryStage);
		setSecurity();
		initFields();

		VBox vbox = new VBox();
		
		MenuBar menubar = buildMenuBar(primaryStage);
		
		GridPane maingrid = new GridPane();
		
		Node viewlist = buildViewList();
		
		maingrid.add(viewlist, 0, 0);
		
		vbox.getChildren().addAll(menubar, maingrid);
		
		Scene scene = new Scene(vbox, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Password-Manager");
        primaryStage.show();
	}
	
	private void setImage(Stage stage) {
		stage.getIcons().add(new Image("file:lock.png"));
	}

	private void setSecurity() {
		Security.setProperty("crypto.policy", "unlimited");
	}
	
	private ObservableList<Data> getGroupData(Bundle group) {
		ObservableList<Data> subdata = FXCollections.observableArrayList();
		ObservableList<Data> data = selectedbundle.getBundleData();
		
		for(int ii = 0; ii < data.size(); ii++){
			subdata.add(data.get(ii));
		}
		
		return subdata;
	}
	
	private void initFields() {
		bundlelist = new ArrayList<Bundle>();
	}
	
	private MenuBar buildMenuBar(Stage stage) {
		MenuBar menubar = new MenuBar();
		
		Menu mv = buildMenuView();
		Menu me = buildMenuEdit(stage, mv);
		Menu mf = buildMenuFile(stage, mv, me);
		Menu mg = buildMenuGame();
		
		menubar.getMenus().addAll(mf, me, mv, mg);
		
		return menubar;
	}
	
	private Menu buildMenuView() {
		Menu mv = new Menu("View");
		
		return mv;
	}
	
	private void refreshMenuView(Menu mv) {
		mv.getItems().clear();
		
		for(int ii = 0; ii < bundlelist.size(); ii++) {
			Bundle group = bundlelist.get(ii);
			MenuItem bundle = new MenuItem(
					group.getBundleName(),
					new ImageView(group.getImage())
			);
			bundle.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					selectedbundle = group;
					showCurrent();
					}
			});
			mv.getItems().add(bundle);
		}
	}
	
	private void refreshEditBundle(Menu editbundle, Stage stage, Menu mv){
		editbundle.getItems().clear();
		
		for(int ii = 0; ii < bundlelist.size(); ii++) {
			Bundle bundle = bundlelist.get(ii);
			Menu somebundle = new Menu(
					bundle.getBundleName(),
					new ImageView(bundle.getImage())
			);
			somebundle.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					Optional<Bundle> result = new NewBundleDialog(stage).showAndWait();
					if(result.isPresent()) {
						Bundle group = result.get();
						bundle.setBundleName(group.getBundleName());
						bundle.setImage(group.getImageURL());
						showCurrent();
						refreshEditBundle(editbundle, stage, mv);
					}
				}
			});
			
			MenuItem adddata = new MenuItem("Add Data");
			adddata.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					Optional<Data> result = new NewDataDialog().showAndWait();
					if (result.isPresent()) {
						Data data = result.get();
						bundle.setBundleData(data);
						showCurrent();
						refreshEditBundle(editbundle, stage, mv);
					}
				}
			});
			
			Menu editdata = new Menu("Edit Data");
			for(int ii2 = 0; ii2 < bundlelist.get(ii).getBundleData().size(); ii2++) {
				Data selecteddata = bundlelist.get(ii).getBundleData().get(ii2);
				Menu somedata = new Menu(
					selecteddata.getUsername()+"-"+
					selecteddata.getEmail()+"-"+
					selecteddata.getPassword()
				);
				
			    somedata.setOnAction(new EventHandler<ActionEvent>() {
			    	public void handle(ActionEvent t) {
			    		Optional<Data> result = new NewDataDialog().showAndWait();
						if(result.isPresent()) {
							Data data = result.get();
							selecteddata.setUsername(data.getUsername());
				    		selecteddata.setEmail(data.getEmail());
				    		selecteddata.setPassword(data.getPassword());
							showCurrent();
							refreshEditBundle(editbundle, stage, mv);
						}
			    		
			    	}
			    });
			    
			    MenuItem removedata = new MenuItem("Delete Data");
				removedata.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent t) {
						bundle.getBundleData().remove(selecteddata);
						showCurrent();
						refreshEditBundle(editbundle, stage, mv);
					}
				});
				
				somedata.getItems().add(removedata);
				editdata.getItems().add(somedata);
			}
			
			MenuItem removebundle = new MenuItem("Delete Website");
			removebundle.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					bundle.getBundleData().clear();
					bundlelist.remove(bundle);
					showCurrent();
					refreshEditBundle(editbundle, stage, mv);
					refreshMenuView(mv);
				}
			});
			
			somebundle.getItems().addAll(adddata, editdata, removebundle);
			editbundle.getItems().add(somebundle);
		}
	}
	
	private Menu buildMenuGame() {
		Menu mg = new Menu("Game");
		
		MenuItem play = new MenuItem("Play");
		
		play.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				GameWindow gw = new GameWindow();
				gw.startGame();
			}
		});
		
		mg.getItems().addAll(play);
		
		return mg;
	}
	
	private Menu buildMenuEdit(Stage stage, Menu mv) {
		Menu me = new Menu("Edit");
		
		Menu editbundle = new Menu("Edit Website");
		
		MenuItem addbundle = new MenuItem("Add Website");
		addbundle.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Optional<Bundle> result = new NewBundleDialog(stage).showAndWait();
				if(result.isPresent()){
					Bundle group = result.get();
					bundlelist.add(group);
					selectedbundle = group;
					showCurrent();
					refreshMenuView(mv);
					refreshEditBundle(editbundle, stage, mv);
				}
			}
		});
		
		me.getItems().addAll(addbundle, editbundle);
		
		return me;
	}
	
	private Menu buildMenuFile(Stage stage, Menu mv, Menu me) {
		Menu mf = new Menu("File");
		
		MenuItem createnew = new MenuItem("New");
		createnew.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				clearEverything(mv);
			}
		});
		
		MenuItem save = new MenuItem("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				String pw = askForPassword();
				FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().add(
						new ExtensionFilter("Password-Manager-Data", "*.adt"));
				chooser.setTitle("Save");
				File file = chooser.showSaveDialog(stage);
				if (file != null) {
					try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
						String toencrypt = "";
						for(Bundle b : bundlelist){
							String s = new String();
							for(Data d : b.getBundleData()){
								s += d.getUsername() + "#~#" + 
										d.getEmail() + "#~#" +
										d.getPassword() +"#~#";
							}
							s = s.substring(0, s.length() - 3);
							toencrypt += b.getBundleName() + "#~#" +
									b.getImageURL() + "#~#" +
									s + "#!#";
						}
						String towrite = toencrypt;
						try {
							Crypter crypter = new Crypter(pw);
							towrite = crypter.encryptText(toencrypt);
						} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException
								| NoSuchPaddingException | InvalidParameterSpecException | IllegalBlockSizeException e) {
							e.printStackTrace();
						} catch (InvalidAlgorithmParameterException e) {
							e.printStackTrace();
						} catch (BadPaddingException e) {
							System.out.println("wrong key");
						}
						oos.writeObject(
								towrite
						);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		MenuItem open = new MenuItem("Open");
		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				String pw = askForPassword();
				FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().add(new ExtensionFilter("Password-Manager-Data", "*.adt"));
				chooser.setTitle("Open");
				File file = chooser.showOpenDialog(stage);
				if (file != null) {
					clearEverything(mv);
					String tosplit = "";
					
					try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
						System.out.println("reading");
						while (true) {
							tosplit += ois.readObject().toString();
						}
					} catch (EOFException e) {
						System.out.println("loading");
//						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
					String[] splitteddata = tosplit.split("###");
					String iv = splitteddata[0];
					String todecrypt = splitteddata[1];
					String getdata = "";
					
					try {
						Crypter crypter = new Crypter(pw);
						getdata = crypter.decryptText(iv, todecrypt);
					} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException
							| NoSuchPaddingException | InvalidAlgorithmParameterException
							| IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
					String[] bundles = getdata.split("#!#");

					for(int ii = 0; ii < bundles.length; ii++) {
						String[] bdata = bundles[ii].split("#~#");
						List<String> array = new ArrayList<String>(Arrays.asList(bdata));
						
						String name = array.remove(0);
						Bundle bundle = new Bundle(name);
						selectedbundle = bundle;
						bundlelist.add(selectedbundle);
							
						//fix later
						String image = array.remove(0);
						File stuff = new File(image);
						image = stuff.toURI().toString();
						selectedbundle.setImage(image);
						
						while(array.size() > 0) {
							String username = array.remove(0);
							String email = array.remove(0);
							String password = array.remove(0);
							
							Data data = new Data(password);
							data.setEmail(email);
							data.setUsername(username);
							selectedbundle.setBundleData(data);
						}
					}
					
					showCurrent();
					refreshMenuView(mv);
					Menu edit = (Menu) me.getItems().get(1);
					refreshEditBundle(edit, stage, mv);
				}
			}
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        System.exit(0);
		    }
		});
		
		mf.getItems().addAll(createnew, save, open, exit);
		
		return mf;
	}
	
	private void clearEverything(Menu mv) {
		mv.getItems().clear();
		bundlelist.clear();
		if(bundledata != null)
			bundledata.clear();
		selectedbundle = null;
		table.refresh();
	}

	private String askForPassword() {
		Optional<String> result = new NewPasswordDialog().showAndWait();
		while(!result.isPresent()) {
			result = new NewPasswordDialog().showAndWait();
		}
		String data = result.get();
		return data;
	}

	private Node buildViewList() {
		HBox hbox = new HBox();
		
		table = new TableView<Data>();
		
		TableColumn<Data, String> username = NewDataColumn("Username", "username");
		TableColumn<Data, String> email = NewDataColumn("Email", "email");
		TableColumn<Data, String> password = NewDataColumn("Password", "password");
		
		table.getColumns().setAll(username, email, password);
		
		hbox.getChildren().add(table);
		return hbox;
	}
	
	private TableColumn<Data, String> NewDataColumn(String Name, String DataName) {
		TableColumn<Data, String> col = new TableColumn<Data, String>(Name);
		col.setCellValueFactory(new PropertyValueFactory<Data, String>(DataName));
		
		return col;
	}
	
	private void showCurrent(){
		bundledata = getGroupData(selectedbundle);
		
		table.setItems(bundledata);
		table.refresh();
	}
	
	

}