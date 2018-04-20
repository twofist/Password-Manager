package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class WorldObject {

	boolean outofbounds;
	boolean outofbounds_right;
	boolean outofbounds_left;
	boolean outofbounds_top;
	boolean outofbounds_bottom;
	private WorldObject collision;
	private boolean collision_right;
	private boolean collision_left;
	private boolean collision_top;
	private boolean collision_bottom;
	double x, y;
	double width, height;
	Canvas canvas;
	Color color;
	double alpha;
	
	WorldObject(Canvas canvas) {
		super();
		this.outofbounds = false;
		this.outofbounds_right = false;
		this.outofbounds_left = false;
		this.outofbounds_top = false;
		this.outofbounds_bottom = false;
		this.collision = null;
		this.collision_right = false;
		this.collision_left = false;
		this.collision_top = false;
		this.collision_bottom = false;
		this.width = 0;
		this.height = 0;
		this.color = Color.BLACK;
		this.canvas = canvas;
		this.alpha = canvas.getGraphicsContext2D().getGlobalAlpha();
		this.x = 0;
		this.y = 0;
	}
	
	boolean getOutOfBoundsBottom() {
		return this.outofbounds_bottom;
	}
	boolean getOutOfBoundsTop() {
		return this.outofbounds_top;
	}
	boolean getOutOfBoundsRight() {
		return this.outofbounds_right;
	}
	boolean getOutOfBoundsLeft() {
		return this.outofbounds_left;
	}
	
	void setOutOfBoundsRight(boolean b){
		this.outofbounds_right = b;
	}
	void setOutOfBoundsLeft(boolean b){
		this.outofbounds_left = b;
	}
	void setOutOfBoundsTop(boolean b){
		this.outofbounds_top = b;
	}
	void setOutOfBoundsBottom(boolean b){
		this.outofbounds_bottom = b;
	}
	
	void setOutOfBounds(boolean b){
		this.outofbounds = b;
	}
	boolean getOutOfBounds(){
		return this.outofbounds;
	}
	
	void drawObjectRect(){
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.setGlobalAlpha(this.alpha);
		gc.setFill(this.color);
		gc.fillRect(this.x, this.y, this.width, this.height);
	}
	
	void drawObjectOval(){
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.setGlobalAlpha(this.alpha);
		gc.setFill(this.color);
		gc.fillOval(this.x, this.y, this.width, this.height);
	}
	
	void resetCollisionSide() {
		this.collision_right = false;
		this.collision_bottom = false;
		this.collision_left = false;
		this.collision_top = false;
	}
	
	void setCollision(WorldObject lf){
		this.collision = lf;
	}
	
	void resetCollision() {
		this.collision = null;
		this.resetCollisionSide();
	}
	
	boolean getCollisionRight(){
		return this.collision_right;
	}
	boolean getCollisionLeft(){
		return this.collision_left;
	}
	boolean getCollisionTop(){
		return this.collision_top;
	}
	boolean getCollisionBottom(){
		return this.collision_bottom;
	}
	
	void setCollisionRight(boolean b){
		this.collision_right = b;
	}
	void setCollisionLeft(boolean b){
		this.collision_left = b;
	}
	void setCollisionTop(boolean b){
		this.collision_top = b;
	}
	void setCollisionBottom(boolean b){
		this.collision_bottom = b;
	}
	
	WorldObject getCollision(){
		return this.collision;
	}

}
