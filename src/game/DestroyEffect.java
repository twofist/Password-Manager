package game;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class DestroyEffect extends Particle{

	private double angle;
	private double distance;
	private double rotationspeed;

	DestroyEffect(Canvas canvas, double x, double y, double angle) {
		super(canvas);
		this.speed = 0.005;
		this.rotationspeed = 0.1;
		this.color = Color.ORANGE;
		this.width = 10;
		this.height = 10;
		this.vgravity = false;
		this.hgravity = false;
		this.angle = angle;
		this.x = x;
		this.y = y;
		this.distance = 0;
	}
	
	void moveDestroyEffect(){
		this.velocity[0] += this.distance * Math.sin(this.angle);
		this.velocity[1] += this.distance * Math.cos(this.angle);
		
		this.distance += this.speed;
		this.angle += this.rotationspeed;
	}
	
	void drawObjectOval() {
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.setGlobalAlpha(this.alpha-=0.01);
		gc.setFill(this.color);
		gc.fillOval(this.x, this.y, this.width, this.height);
	}

	void onOutOfBounds(List<DestroyEffect> delist) {
		if(this.alpha < 0) {
			delist.remove(this);
		}
		/*
		if(this.getOutOfBoundsBottom()){
			delist.remove(this);
		}
		if(this.getOutOfBoundsTop()){
			delist.remove(this);
		}
		if(this.getOutOfBoundsRight()){
			delist.remove(this);
		}
		if(this.getOutOfBoundsLeft()){
			delist.remove(this);
		}
		*/
	}

}
