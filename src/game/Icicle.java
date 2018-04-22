package game;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

class Icicle extends Particle{
	
	private double timer;
	private int maxheight;
	private final Timeline timeline = new Timeline();

	Icicle(Canvas canvas, double x, double y, double timer) {
		super(canvas);
		this.speed = 1;
		this.color = Color.LIGHTBLUE;
		this.width = 20;
		this.height = 0;
		this.maxheight = 40;
		this.vgravity = false;
		this.hgravity = false;
		this.x = x;
		this.y = y;
		this.timer = timer;
		this.createTimer();
	}
	
	void drawIcicle(){
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.beginPath();
		gc.setGlobalAlpha(this.alpha);
		gc.setFill(this.color);
		gc.moveTo(this.x, this.y);
		gc.lineTo(this.x+this.width, this.y);
		gc.lineTo(this.x+(this.width/2), this.y+this.height);
		if(this.height<this.maxheight)
			this.height += this.speed;
		gc.fill();
		gc.closePath();
	}
	
	private void createTimer() {
		final Duration dur = Duration.millis(this.timer);
		timeline.getKeyFrames().add(new KeyFrame(dur,
			a -> fallDown())
		);
		timeline.play();
	}
	
	private void fallDown() {
		this.vgravity = true;
	}
	
	void moveIcicle(){
		this.velocity[0] += 0;
		this.velocity[1] += 0;
	}

	void onOutOfBounds(List<Icicle> iciclelist) {
		if(this.getOutOfBoundsBottom()){
			if(this.y>this.canvas.getHeight())
				iciclelist.remove(this);
		}
		if(this.getOutOfBoundsTop()){
//			this.y = 0;
		}
		if(this.getOutOfBoundsRight()){
//			this.x = this.canvas.getWidth() - this.width;
		}
		if(this.getOutOfBoundsLeft()){
			iciclelist.remove(this);
		}
	}

}
