package game;
import javafx.scene.canvas.Canvas;

class LifeForm extends WorldObject {
	
	double speed;
	double jumpheight;
	boolean canjump;
	double[] velocity = {0, 0};
	private boolean beenhit;
	boolean vgravity;
	boolean hgravity;
	
	LifeForm(Canvas canvas){
		super(canvas);
		this.speed = 0;
		this.vgravity = false;
		this.hgravity = false;
		this.jumpheight = 0;
		this.beenhit = false;
		this.canjump = false;
	}
	
	void verticalGravity(double g){
		if(!this.vgravity) this.resetVelocityV();
		if(!this.outofbounds_bottom)
            this.velocity[1] += g;
	}
	
	void horizontalGravity(double g){
		if(!this.hgravity) this.resetVelocityH();
		if(!this.outofbounds_left)
			this.velocity[0] -= g;
	}
	
	void applyJump(int up){
		if(!this.canjump) return;
		this.velocity[1] += (up * this.jumpheight);
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
	
	boolean hasBeenHit(){
		return this.beenhit;
	}
	
	void setBeenHit(boolean bool){
		this.beenhit = bool;
	}
}
