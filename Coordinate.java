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
	Boolean isEqual(Coordinate coordinate){
	return this.xValue==coordinate.xValue&&this.yValue==coordinate.yValue;
		
	}
	
	Coordinate translate(Point3d coordinate3d) {
		Coordinate coordinate = new Coordinate ((int)(coordinate3d.x), (int)(coordinate3d.y));
		return coordinate;
	}
}
