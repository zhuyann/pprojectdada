package rovuSystem;

import java.awt.image.BufferedImage;

public class CentralStation extends Subject{
	
	BufferedImage[] pictureArray;
	Environment mainEnvironment;
	RoverFactory roverFactory;
	
	
	CentralStation() {
	}

	
	void intializeMission() {
		int lengthOfEachCell = mainEnvironment.length / 2;
		int widthOfEachCell = mainEnvironment.width / 2;
		
		Environment cell1 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell2 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell3 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell4 = new Environment(lengthOfEachCell, widthOfEachCell);
		
		Rover rover1 = roverFactory.getRoverType("rover1", cell1);
		Rover rover2 = roverFactory.getRoverType("rover2", cell2);
		Rover rover3 = roverFactory.getRoverType("rover3", cell3);
		Rover rover4 = roverFactory.getRoverType("rover4", cell4);
		
		//put rovers in an array as observers.
		
		attach(rover1);
		attach(rover2);
		attach(rover3);
		attach(rover4);
	}
	
	
	void startRovers() {
		//start rovers
	}
	
	void stopRovers() {
		//stop rovers
	}
	
	void savePicture() {
		
	}
	
	void handleError() {
		
	}
}
