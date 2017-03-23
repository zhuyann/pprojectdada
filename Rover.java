package rovuSystem;

import java.awt.image.BufferedImage;
import javax.vecmath.Vector3d;

public class Rover extends Observer{
	
	String name;
	Environment myCell;
	State state;
	RoverSimulator roverSim;
	BufferedImage[] pictures;
	int numberOfPictures;

	Rover(String name, Environment cell, State initialState) {
		this.name = name;
		myCell = cell;
		state = initialState;
		
		roverSim = new RoverSimulator(new Vector3d(0, 0, 0), name);
		pictures = new BufferedImage[10];
		numberOfPictures = 0;
		
	}
	
	
	void update() {
		//implement instructions from the Central Station
	}
	
	void start() {
		roverSim.initBehavior();
		state = State.MOVING;
	}
	
	void stop() {
		roverSim.stopRover();
		state = State.STILL;
	}
	
	void avoidCollision() {
		roverSim.performBehavior();
	}
	
	void communicateWithTheCS() {
		//send feedback to Central Station
	}
	
	void takeAndSendPhotos() {
		roverSim.takePhoto();
		pictures = roverSim.picArray;
		numberOfPictures = roverSim.picArray.length;
	}
	

}
