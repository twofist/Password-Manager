package game;

import javafx.scene.canvas.Canvas;

class TurbineWindBox extends WorldObject{

	TurbineWindBox(Canvas canvas, double x, double y, double w, double h) {
		super(canvas);
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

}
