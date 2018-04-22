package game;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

class Player extends LifeForm {

	private boolean[] keys = {false, false, false, false};
	private JumpEffect jumpeffect;

	public Player(Canvas canvas) {
		super(canvas);
		this.speed = 1.5;
		this.vgravity = true;
		this.hgravity = true;
		this.width = 50;
		this.height = 50;
		this.jumpheight = 12;
		this.x = 0;
		this.y = 0;
		this.canjump = true;
		this.color = Color.BLUE;
		this.jumpeffect = new JumpEffect(this.canvas, this.x, this.y);
		addKeyEvents();
	}
	
	private void addKeyEvents(){
		canvas.setFocusTraversable(true);
		this.canvas.setOnKeyPressed(new EventHandler <KeyEvent>(){
            @Override
            public void handle(KeyEvent e) {
            	switch(e.getCode()){
            	case UP:
            		keys[0] = true;
            		break;
            	case DOWN:
            		keys[1] = true;
            		break;
            	case LEFT:
            		keys[2] = true;
            		break;
            	case RIGHT:
            		keys[3] = true;
            		break;
				default:
					break;
            	}
            }   
        });
		
		this.canvas.setOnKeyReleased(new EventHandler <KeyEvent>(){
            @Override
            public void handle(KeyEvent e) {
            	switch(e.getCode()){
            	case UP:
            		keys[0] = false;
            		break;
            	case DOWN:
            		keys[1] = false;
            		break;
            	case LEFT:
            		keys[2] = false;
            		break;
            	case RIGHT:
            		keys[3] = false;
            		break;
				default:
					break;
            	}
            }   
        });
	}
	
	void movePlayer(){
		int right = (this.keys[3]) ? 1 : 0;
		int left = (this.keys[2]) ? 1 : 0;
		
		if(this.velocity[0] <= this.speed){
			double check = (right - left) * this.speed;
			
			if((check < 0 && !this.getCollisionLeft()) || (check > 0 && !this.getCollisionRight()))
				this.velocity[0] += check;
		}
		
		if(this.keys[0] && this.canjump && this.getOutOfBoundsBottom()) {
			this.playerJump();
		}
			
	}
	
	void playerJump() {
		this.applyJump(-1);
		this.jumpeffect.resetEffect(this.x, this.y+this.height);
	}
	
	void drawJumpEffect() {
		this.jumpeffect.drawJumpEffect();
	}
	
	void onOutOfBounds(){
		if(this.getOutOfBoundsBottom()){
			this.setOnBottom();
		}
		if(this.getOutOfBoundsTop()){
			this.setOnTop();
		}
		if(this.getOutOfBoundsRight()){
			this.setOnRight();
		}
		if(this.getOutOfBoundsLeft()){
			this.setOnLeft();
		}
	}
	
	void handleCollision(){
		WorldObject obj = this.getCollision();
		
		if(obj instanceof Turbine) {
			if(this.getCollisionLeft()){
				this.resetVelocityH();
				this.x = obj.x + obj.width;
			}	
			if(this.getCollisionRight()){
				this.resetVelocityH();
				this.x = obj.x - this.width;
			}
			if(this.getCollisionBottom()){
				this.x = obj.x + obj.width;
				this.resetVelocityV();
			}
			if(this.getCollisionTop()){
				this.x = 0;
				this.resetVelocityV();
			}
		}
		
		if(obj instanceof LifeForm) {
			if(this.getCollisionLeft()){
				this.resetVelocityH();
				this.x = obj.x + obj.width;
				this.velocity[0] -= this.speed;
			}	
			if(this.getCollisionRight()){
				this.resetVelocityH();
				this.x = obj.x - this.width;
				this.velocity[0] -= this.speed;
			}
			if(this.getCollisionBottom()){
				this.resetVelocityV();
				this.y = obj.y-this.height;
				this.playerJump();
			}
			if(this.getCollisionTop()){
				this.x = 0;
				this.resetVelocityV();
			}
		}
	}
	
	private void setOnBottom(){
		this.y = this.canvas.getHeight() - this.height;
		this.resetVelocityV();
	}
	private void setOnRight(){
		this.x = this.canvas.getWidth() - this.width;
		this.resetVelocityH();
	}
	private void setOnLeft(){
		this.x = 0;
		this.resetVelocityH();
	}
	private void setOnTop() {
		this.y = 0;
//		this.resetVelocityV();
	}

}
