package game;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

class RainDrop extends Particle{
	
	RainDrop(Canvas canvas, double x, double y, double ampspeed) {
		super(canvas);
		this.speed = 2+ampspeed;
		this.color = Color.LIGHTBLUE;
		this.width = 1;
		this.height = 5;
		this.vgravity = true;
		this.hgravity = true;
		this.x = x;
		this.y = y;
	}
	
	void moveRainDrop(){
		this.velocity[0] += 0;
		this.velocity[1] += 0;
	}

	void onOutOfBounds(List<RainDrop> rainlist) {
		if(this.getOutOfBoundsBottom()){
			rainlist.remove(this);
		}
		if(this.getOutOfBoundsTop()){
//			this.y = 0;
		}
		if(this.getOutOfBoundsRight()){
//			this.x = this.canvas.getWidth() - this.width;
		}
		if(this.getOutOfBoundsLeft()){
			rainlist.remove(this);
		}
	}

}
