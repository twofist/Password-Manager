package game;

import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class JumpEffect extends Particle{

	private int lineamount;
	private int offsetx;
	private int offsety;
	private boolean direction;
	private int linelength;
	private double[] difference = {this.getRandomNumber(1, 3), this.getRandomNumber(5, 8), this.getRandomNumber(1, 3)};
	private double overtimex;
	private double overtimey;
	private int angle;

	JumpEffect(Canvas canvas, double x, double y) {
		super(canvas);
		this.alpha = 0;
		this.speed = 0.5;
		this.lineamount = 3;
		this.x = x;
		this.y = y;
		this.overtimex = x;
		this.overtimey = y;
		this.offsetx = 15;
		this.offsety = 4;
		this.color = Color.WHITE;
		this.direction = false;
		this.linelength = 20;
		this.angle = 4;
	}
	
	void resetEffect(double x, double y) {
		this.setDifference();
		this.resetAnimation();
		this.resetAlpha();
		this.setX(x);
		this.setY(y);
	}
	
	void setDifference() {
		this.difference[0] = this.getRandomNumber(1, 3);
		this.difference[1] = this.getRandomNumber(3, 6);
		this.difference[2] = this.getRandomNumber(1, 3);
	}
	
	void resetAlpha() {
		this.alpha = 1;
	}
	
	void resetAnimation() {
		this.overtimex = 0;
		this.overtimey = 0;
	}
	
	void setX(double x) {
		this.x = x;
	}
	
	void setY(double y) {
		this.y = y;
	}
	
	void drawJumpEffect(){
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.save();
		gc.setGlobalAlpha(this.alpha-=0.05);
		for(int ii = 0; ii < this.lineamount; ii++) {
			drawPath(gc, ii);
		}
		gc.restore();
	}
	
	void drawPath(GraphicsContext gc, int ii) {
		gc.setStroke(this.color);
		int dir = (this.direction) ? this.angle : -this.angle;
		
		double startx = this.x+(this.offsetx*ii)+dir;
		double starty = this.y-this.offsety-this.difference[ii];
		
		double endx = startx+this.overtimex;
		double endy = starty+this.overtimey;
		
		double direction = this.x+(this.offsetx*ii) - endx;
		
		if(direction > 0) {
			this.overtimex += this.speed;
		}else if(direction < 0) {
			this.overtimex -= this.speed;
		}
			
		if(endy > this.y-this.linelength) {
			this.overtimey -= this.speed;
		}
		
		gc.strokeLine(startx, starty, endx, endy);
	}
	
	private double getRandomNumber(double min, double max){
		Random rn = new Random();
		double num = (min + (max - min) * rn.nextDouble());
		return num;
	}

	void setDirection(boolean b) {
		this.direction = b;
	}

}
