package rovuSystem;

public class Environment {

	int length;
	int width;
	int numberOfCoordinatesDefined;      // = all
	int lengthOfCoordinatePool;			// = unvisited
	Coordinate startingPoint;
	Coordinate[] coordinatePool;


	Environment() {
		length = 4;
		width = 3;
		startingPoint = new Coordinate(-4,0);

		numberOfCoordinatesDefined = (length-1)*(width-1);
		lengthOfCoordinatePool = numberOfCoordinatesDefined;
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

	void defineCoordinates() {
		int counter = 0;
		for (int i = startingPoint.xValue+1, x = 1 ; x < length; i ++, x++) {
			for (int j = startingPoint.yValue+1, y = 1; y < width; j ++, y++) {
				coordinatePool[counter] = new Coordinate(i, j);
				counter++;
			}
		}
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
		if (numberOfCoordinatesDefined == 0) {
			return 0;
		} else {
			double percentage = (numberOfCoordinatesDefined-lengthOfCoordinatePool)/numberOfCoordinatesDefined * 100;
			return percentage;
		}
	}

}
