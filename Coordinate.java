package rovuSystem;

import javax.vecmath.Point3d;

public class Coordinate {

	int xValue;
	
	int yValue;
	
	Coordinate() {
		xValue = 0;
		yValue = 0;
		
	}
	
	Coordinate(int x, int y){
		this.xValue = x;
		this.yValue = y;	
	}
	
	Coordinate translate(Point3d coordinate3d) {
		Coordinate coordinate = new Coordinate ((int)(coordinate3d.x), (int)(coordinate3d.y));
		return coordinate;
	}
}
