package rovuSystem;

public class Environment {
	
	int length;
	int width;
	int numberOfCoordinatesDefined;
	int lengthOfCoordinatePool;
	Coordinate[] coordinatePool;
	Coordinate startingPoint;
	
	
	Environment() {
		length = 0;
		width = 0;
		lengthOfCoordinatePool = 0;
		numberOfCoordinatesDefined = 0;
		coordinatePool = new Coordinate[lengthOfCoordinatePool];
	}
	
	Environment(int length, int width) {
		this.length = length;
		this.width = width;
	}
	
	Environment(int length, int width, Coordinate startingPoint) {
		this.length = length;
		this.width = width;
		this.startingPoint = startingPoint;
	}
	
	void defineCoordinates(Coordinate coordinate) {
		coordinatePool[lengthOfCoordinatePool] = coordinate;
		lengthOfCoordinatePool++;
		numberOfCoordinatesDefined++;
	}

	void removeFromCoordinatePool(Coordinate coordinate) {	//new method
		for (int i=0; i<lengthOfCoordinatePool; i++) {
			if (coordinatePool[i] == coordinate) {
				for (int j = i+1; j<lengthOfCoordinatePool; j++) {
				coordinatePool[i] = coordinatePool[j];
				i++;
				}
				lengthOfCoordinatePool--;
			} 
		}
	}
	
	
	double percentageVisited() {
		double percentage = (numberOfCoordinatesDefined-lengthOfCoordinatePool)/numberOfCoordinatesDefined * 100;
		return percentage;
	}
	
	
}
