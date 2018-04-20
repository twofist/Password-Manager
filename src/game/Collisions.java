package game;

class Collisions {

	Collisions() {
		
	}
	
	boolean collisionAABB(WorldObject lf1, WorldObject lf2){
		double r1_right = lf1.x+lf2.width;
		double r1_bottom = lf1.y + lf1.height;
		
		double r2_right = lf2.x+lf2.width;
		double r2_bottom = lf2.y+lf2.width;
		
		return !(lf2.x > r1_right || r2_right < lf1.x || lf2.y > r1_bottom || r2_bottom < lf1.y);
	}
	
	void collisionSide(WorldObject lf1, WorldObject lf2){
		double obj_right = lf1.x + lf1.width;
		double obj_bottom = lf1.y + lf1.height;
		
		double obj2_right = lf2.x + lf2.width;
		double obj2_bottom = lf2.y + lf2.height;
		
		double b = obj2_bottom-lf1.y;
		double t = obj_bottom-lf2.y;
		double l = obj_right-lf2.x;
		double r = obj2_right-lf1.x;
		
		if(t < b && t < l && t < r){
			lf1.setCollisionBottom(true);
			lf2.setCollisionTop(true);
		}
		
		if(b < t && b < l && b < r){
			lf1.setCollisionTop(true);
			lf2.setCollisionBottom(true);
		}
		
		if(l < r && l < t && l < b){
			lf1.setCollisionRight(true);
			lf2.setCollisionLeft(true);
		}
			
		if(r < l && r < t && r < b){
			lf1.setCollisionLeft(true);
			lf2.setCollisionRight(true);
		}
			
	}

	boolean collisionRectTriangle(WorldObject lf1, WorldObject lf2) {
		if(lf2.getCollisionLeft()) {
			return !this.pointTriangle(lf1.x+lf1.width, lf1.y, lf2.x, lf2.y+lf2.height, lf2.x+lf2.width, lf2.y+lf2.height, lf2.x+lf2.width/2, lf2.y);
		}else if(lf2.getCollisionRight()) {
			return !this.pointTriangle(lf1.x, lf1.y, lf2.x, lf2.y+lf2.height, lf2.x+lf2.width, lf2.y+lf2.height, lf2.x+lf2.width/2, lf2.y);
		}
		
		return false;
	}
	
	boolean pointTriangle(double px, double py, double x1, double y1, double x2, double y2, double x3, double y3) {
		  // get the area of the triangle
		  double areaOrig = Math.abs((x2-x1)*(y3-y1)-(x3-x1)*(y2-y1));

		  // get the area of 3 triangles made between the point and the corners of the triangle
		  double area1 = Math.abs((x1-px)*(y2-py)-(x2-px)*(y1-py));
		  double area2 = Math.abs((x2-px)*(y3-py)-(x3-px)*(y2-py));
		  double area3 = Math.abs((x3-px)*(y1-py)-(x1-px)*(y3-py));

		  // if the sum of the three areas equals the original, we're inside the triangle!
		  return (area1 + area2 + area3 == areaOrig);
		}
	
//    //barycentric
//	boolean isInside(double px, double py, double ax, double ay, double bx, double by, double cx, double cy) {
//		double[] v0 = {cx-ax,cy-ay};
//		double[] v1 = {bx-ax,by-ay};
//		double[] v2 = {px-ax,py-ay};
//		
//		double dot00 = (v0[0]*v0[0]) + (v0[1]*v0[1]);
//		double dot01 = (v0[0]*v1[0]) + (v0[1]*v1[1]);
//		double dot02 = (v0[0]*v2[0]) + (v0[1]*v2[1]);
//		double dot11 = (v1[0]*v1[0]) + (v1[1]*v1[1]);
//		double dot12 = (v1[0]*v2[0]) + (v1[1]*v2[1]);
//
//		double invDenom = 1/ (dot00 * dot11 - dot01 * dot01);
//
//		double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
//		double v = (dot00 * dot12 - dot01 * dot02) * invDenom;
//
//		return ((u >= 0) && (v >= 0) && (u + v < 1));
//	}
}
