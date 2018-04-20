package game;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

class Enemy extends LifeForm {

	Enemy(Canvas canvas) {
		super(canvas);
		this.vgravity = true;
		this.hgravity = false;
		this.speed = 3;
		this.width = 40;
		this.height = 40;
		this.color = Color.RED;
		this.x = canvas.getWidth() + this.width;
	}
	
	void moveEnemy(){
		this.resetVelocityH();
		this.velocity[0] -= this.speed;
	}
	
	void onOutOfBounds(ScoreCounter scorecounter, List<Enemy> enemylist){
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
			if(!this.hasBeenHit())
				scorecounter.changeScore(1);
			enemylist.remove(this);
		}
	}
	
	void setOnBottom(){
		this.y = this.canvas.getHeight() - this.height;
	}
	
}
