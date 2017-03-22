package rovuSystem;

import java.awt.image.BufferedImage;

public class CentralStation extends Subject{
	
	BufferedImage[] pictureArray;
	Environment mainEnvironment;
	RoverFactory roverFactory;
	String typeOfRover;
	State state;
	
	
	CentralStation() {
		typeOfRover = "CAMERAROVER";
		state = State.STILL;
	}

	
	void intializeMission() {
		int lengthOfEachCell = mainEnvironment.length / 2;
		int widthOfEachCell = mainEnvironment.width / 2;
		
		Environment cell1 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell2 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell3 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell4 = new Environment(lengthOfEachCell, widthOfEachCell);
		
		Rover rover1 = roverFactory.getRoverType(typeOfRover, cell1);
		Rover rover2 = roverFactory.getRoverType(typeOfRover, cell2);
		Rover rover3 = roverFactory.getRoverType(typeOfRover, cell3);
		Rover rover4 = roverFactory.getRoverType(typeOfRover, cell4);
		
		//put rovers in an array as observers.
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
