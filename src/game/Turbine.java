package game;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Turbine extends WorldObject{

	private double x2;
	private double y2;
	private double difference;
	private List<Wind> windlist;
	private double intakespeed;
	
	Turbine(Canvas canvas, double x, double y, double w, double h, double difference) {
		super(canvas);
		this.color = Color.LIGHTGREEN;
		this.width = w;
		this.height = h;
		this.intakespeed = 0.3;
		this.x = x;
		this.y = y;
		this.x2 = x+w;
		this.y2 = y+h;
		this.difference = difference;
		this.windlist = new ArrayList<Wind>();
	}
	
	void drawTurbine() {
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.beginPath();
		gc.setGlobalAlpha(this.alpha);
		gc.setFill(this.color);
		gc.moveTo(this.x, this.y);
		gc.lineTo(this.x, this.y2);
		gc.lineTo(this.x2, this.y-difference);
		gc.lineTo(this.x2, this.y2+difference);
		gc.lineTo(this.x, this.y);
		gc.lineTo(this.x2, this.y-difference);
		gc.lineTo(this.x, this.y2);
		gc.lineTo(this.x2, this.y2+difference);
		gc.fill();
		gc.closePath();
	}
	
	private void createWind() {
		if(this.windlist.size() > 10) return;
		Wind previouswind = null;
		double distance = 0;
		if(this.windlist.size() > 0) {
			previouswind = this.windlist.get(this.windlist.size()-1);
			distance = this.height - previouswind.x;
		}
		if(previouswind == null || distance > 10) {
			Wind wind = new Wind(this.canvas, this.width, this.y, this.y+(this.height/2), this.y, 30);
			this.windlist.add(wind);
		}
	}
	
	void handleWind() {
		createWind();
		for(int ii = 0; ii < this.windlist.size(); ii++) {
			Wind wind = this.windlist.get(ii);
			wind.drawObjectRect();
			wind.moveWind();
			wind.applyMovement();
			wind.horizontalGravity(this.intakespeed);
			
			resetWind(wind);
		}
	}
	
	private void resetWind(Wind wind) {
		if(wind.x < this.x2)
			wind.resetPosition();
	}
}
