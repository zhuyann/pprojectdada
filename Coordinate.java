package rovuSystem;

public class Coordinate {

	int xValue;
	int yValue;
	
	Coordinate(int x, int y){
		this.xValue = x;
		this.yValue = y;	
	}
	
	public Boolean isEqual(Coordinate coordinate) {
		return this.xValue==coordinate.xValue&&this.yValue==coordinate.yValue;
	}
}
