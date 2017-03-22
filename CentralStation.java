package rovuSystem;

import java.awt.image.BufferedImage;

public class CentralStation extends Subject{
	
	final static int TIMER = 60;
	
	BufferedImage[] pictureArray;
	Environment mainEnvironment;
	RoverFactory roverFactory;
	
	Rover rover1;
	Rover rover2;
	Rover rover3;
	Rover rover4;
	
	
	CentralStation() {
		pictureArray = new BufferedImage[100];
	}

	
	void intializeMission() {
		int lengthOfEachCell = mainEnvironment.length / 2;
		int widthOfEachCell = mainEnvironment.width / 2;
		
		Environment cell1 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell2 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell3 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell4 = new Environment(lengthOfEachCell, widthOfEachCell);
		
		rover1 = roverFactory.getRoverType("rover1", cell1, State.STILL);
		rover2 = roverFactory.getRoverType("rover2", cell2, State.STILL);
		rover3 = roverFactory.getRoverType("rover3", cell3, State.STILL);
		rover4 = roverFactory.getRoverType("rover4", cell4, State.STILL);
		
		attach(rover1);
		attach(rover2);
		attach(rover3);
		attach(rover4);
	}
	
	
	void startRovers() {
		rover1.start();
		rover2.start();
		rover3.start();
		rover4.start();
	}
	
	void stopRovers() {
		rover1.stop();
		rover2.stop();
		rover3.stop();
		rover4.stop();
	}
	
	void savePicture() {
		
	}
	
	void handleError() {
		
	}
}
