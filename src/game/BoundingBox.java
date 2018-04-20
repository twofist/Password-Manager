package game;

import javafx.scene.canvas.Canvas;

class BoundingBox {
	
	private double left;
	private double right;
	private double top;
	private double bottom;

	BoundingBox(Canvas canvas){
		super();
		this.left = 0;
		this.right = canvas.getWidth();
		this.top = 0;
		this.bottom = canvas.getHeight();
	}
	
	boolean isOutOfBounds(WorldObject lf) {
		return (lf.x < this.left || lf.x+lf.width > this.right || lf.y+lf.height > this.bottom || lf.y < this.top);
	}
	
	void outOfBoundsSide(WorldObject lf){
		if(lf.x <= this.left){
			lf.setOutOfBoundsLeft(true);
		}
		if(lf.x+lf.width >= this.right){
			lf.setOutOfBoundsRight(true);
		}
		if(lf.y <= this.top){
			lf.setOutOfBoundsTop(true);
		}
		if(lf.y+lf.height >= this.bottom){
			lf.setOutOfBoundsBottom(true);
		}
	}
	
	void resetOutOfBoundsSide(WorldObject lf) {
		lf.setOutOfBounds(false);
		lf.setOutOfBoundsBottom(false);
		lf.setOutOfBoundsLeft(false);
		lf.setOutOfBoundsRight(false);
		lf.setOutOfBoundsTop(false);
	}
	
	double getTop(){
		return this.top;
	}
	
	double getBottom() {
		return this.bottom;
	}
	
	double getRight() {
		return this.right;
	}
	
	double getLeft() {
		return this.left;
	}
	
}
