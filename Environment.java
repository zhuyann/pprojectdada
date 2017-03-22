package rovuSystem;

public class Environment {
	
	int length;
	int width;
	int numberOfCoordinatesDefined;
	int lengthOfCoordinatePool;
	Coordinate[] coordinatePool;
	
	
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
	
	
	int percentageVisited() {
		int percentage = (numberOfCoordinatesDefined-lengthOfCoordinatePool)/numberOfCoordinatesDefined * 100;
		return percentage;
	}
	
	
}
