package game;

import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

class Wind extends Particle{

	private double maxheight;
	private double minheight;
	private double maxwidth;

	Wind(Canvas canvas, double w, double x, double y, double maxheight, double minheight, double maxwidth) {
		super(canvas);
		this.speed = 2;
		this.color = Color.LIGHTGREEN;
		this.width = w;
		this.height = 0.5;
		this.vgravity = false;
		this.hgravity = true;
		this.x = x;
		this.y = y;
		this.maxheight = maxheight;
		this.minheight = minheight;
		this.maxwidth = maxwidth;
	}
	
	void moveWind(){
		this.velocity[0] += 0;
		this.velocity[1] += 0;
	}

	void resetPosition() {
		if(this.x + this.width < 0) {
			this.x = this.canvas.getWidth();
			this.y = getRandomNumber(this.minheight, this.maxheight);
			this.width = getRandomNumber(0, this.maxwidth);
			this.resetVelocityH();
		}
	}
	
	private double getRandomNumber(double min, double max){
		Random rn = new Random();
		double num = (min + (max - min) * rn.nextDouble());
		return num;
	}

}
