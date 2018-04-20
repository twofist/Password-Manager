package game;

import javafx.scene.canvas.Canvas;

class Particle extends WorldObject{

	double speed;
	double[] velocity = {0, 0};
	boolean vgravity;
	boolean hgravity;
	
	
	Particle(Canvas canvas){
		super(canvas);
		this.speed = 0;
		this.vgravity = false;
		this.hgravity = false;
	}
	
	void verticalGravity(double g){
		if(!this.vgravity) return;
		if(!this.outofbounds_bottom)
            this.velocity[1] += g;
	}
	
	void horizontalGravity(double g){
		if(!this.hgravity) return;
		if(!this.outofbounds_left)
			this.velocity[0] -= g;
	}
	
	void applyMovement(){
		this.x += this.velocity[0];
		this.y += this.velocity[1];
	}
	
	void resetVelocityH(){
		this.velocity[0] = 0;
	}
	
	void resetVelocityV(){
		this.velocity[1] = 0;
	}

}
