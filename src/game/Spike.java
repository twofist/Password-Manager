package game;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Spike extends LifeForm {

	Spike(Canvas canvas) {
		super(canvas);
		this.vgravity = true;
		this.hgravity = false;
		this.speed = 3;
		this.width = 40;
		this.height = 40;
		this.color = Color.RED;
		this.x = canvas.getWidth() + this.width;
	}
	
	void drawSpike(){
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.beginPath();
		gc.setGlobalAlpha(this.alpha);
		gc.setFill(this.color);
		gc.moveTo(this.x, this.y+this.height);
		gc.lineTo(this.x+this.width, this.y+this.height);
		gc.lineTo(this.x+(this.width/2), this.y);
		gc.fill();
		gc.closePath();
	}
	
	void moveSpike(){
		this.resetVelocityH();
		this.velocity[0] -= this.speed;
	}

	void onOutOfBounds(ScoreCounter scorecounter, List<Spike> spikelist){
		if(this.getOutOfBoundsBottom()){
			this.setOnBottom();
		}
		if(this.getOutOfBoundsTop()){
//			this.y = 0 + this.height;
		}
		if(this.getOutOfBoundsRight()){
//			this.x = this.canvas.getWidth() - this.width;
		}
		if(this.getOutOfBoundsLeft()){
			spikelist.remove(this);
		}
	}
	
	void setOnBottom(){
		this.y = this.canvas.getHeight() - this.height;
	}
	
}
