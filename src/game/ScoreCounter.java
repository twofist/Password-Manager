package game;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

class ScoreCounter {

	private Canvas canvas;
	private Color color;
	private double y;
	private double x;
	private int score;
	private int size;

	ScoreCounter(Canvas canvas, double x, double y, int size) {
		this.canvas = canvas;
		this.x = x;
		this.y = y+size;
		this.size = size;
		this.color = Color.CORAL;
		this.score = 0;
	}
	
	void drawScore(){
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.setFill(this.color);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setFont(new Font("Arial", this.size));
		gc.fillText("Score: " + this.score, x, y);
	}
	
	double getScore() {
		return this.score;
	}
	
	void resetScore() {
		this.score = 0;
	}
	
	void changeScore(int num){
		this.score += num;
		if(this.score < 0)
			this.resetScore();
	}

}
