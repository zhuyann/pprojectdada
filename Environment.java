package rovuSystem;

public class Environment {

	int length;
	int width;
	int numberOfCoordinatesDefined;      // = all coordinates
	int lengthOfCoordinatePool;			 // = unvisited coordinates
	Coordinate startingPoint;
	Coordinate[] coordinatePool;

	Environment(int length, int width, Coordinate startingPoint) {
		this.length = length;
		this.width = width;
		this.startingPoint = startingPoint;
		
		numberOfCoordinatesDefined = (length-1)*(width-1);
		lengthOfCoordinatePool = numberOfCoordinatesDefined;
		coordinatePool = new Coordinate[lengthOfCoordinatePool];
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

	void removeFromCoordinatePool(Coordinate coordinate) {
		for (int i=0; i<lengthOfCoordinatePool; i++) {
			if (coordinatePool[i] == coordinate) {
				coordinatePool[i] = new Coordinate(100,100);
				lengthOfCoordinatePool--;
			} 
		}
	}
	double percentageVisited() {
		if (numberOfCoordinatesDefined == 0) {
			return 0.0;
			
		} else {
			double percentage = ((double)numberOfCoordinatesDefined-(double)lengthOfCoordinatePool)/(double)numberOfCoordinatesDefined * 100;
			return percentage;
		}
	}

}
