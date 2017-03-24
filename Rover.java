package rovuSystem;

import java.awt.image.BufferedImage;
import javax.vecmath.Vector3d;

public class Rover extends Observer{
	
	String name;
	Environment myCell;
	State state;
	
	BufferedImage[] pictures;
	int numberOfPictures;
	
	RoverSimulator roverSim;
	
	
	public Rover(RoverSimulator roverSimulator, Environment cell, State still) {
		roverSim = roverSimulator;
		myCell = cell;
		state = still;
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
