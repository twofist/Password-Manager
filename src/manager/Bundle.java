package manager;
import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

class Bundle implements Serializable{
	
	private String bundlename, imageurl;
	private Image image;
	private ObservableList<Data> bundledata = FXCollections.observableArrayList();
	
	Bundle(String bundlename) {
		super();
		this.bundlename = bundlename;
	}
	
	Image getImage() {
		return this.image;
	}
	
	String getBundleName() {
		return this.bundlename;
	}
	
	void setImage(String imagepath) {
		if(imagepath != null) {
			this.image = new Image(imagepath, 20, 20, false ,false);
			this.imageurl = imagepath;
		}
	}
	
	String getImageURL() {
		return this.imageurl;
	}
	
	void setBundleName(String bundlename) {
		this.bundlename = bundlename;
	}
	
	void setBundleData(Data bundledata){
		this.bundledata.add(bundledata);
	}
	
	ObservableList<Data> getBundleData(){
		return this.bundledata;
	}
	
	@Override
	public String toString() {
		String str = bundlename + " " + image;
		return str;
	}
}
