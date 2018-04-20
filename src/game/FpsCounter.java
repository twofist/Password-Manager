package game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

class FpsCounter {

	private Canvas canvas;
	private Color color;
	private double y;
	private double x;
	private double fps;
	private AnimationTimer timer;
	
	private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;
	private int size;

	FpsCounter(Canvas canvas, double x, double y, int size) {
		this.canvas = canvas;
		this.x = x;
		this.y = y+size;
		this.size = size;
		this.fps = 0;
		this.color = Color.WHITE;
		this.timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    setAverageFps(frameRate);
                }
			}
		};
	}
	
	void drawFps(){
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.setFill(this.color);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.setFont(new Font("Arial", this.size));
		gc.fillText(String.format("FPS: %.0f", this.fps), x, y);
	}
	
	void startCounting() {
		this.timer.start();
	}
	
	private void setAverageFps(double frameRate) {
		this.fps = frameRate;
	}

}
