package rovuSystem;

import java.awt.image.BufferedImage;

public class Rover extends Observer{

	String name;
	Environment myCell;
	State state;

	BufferedImage[] pictures;
	int numberOfPictures;

	RoverSimulator roverSim;


	public Rover(RoverSimulator roverSimulator, Environment cell, State still,CentralStation centralStation) {
		this.subject = centralStation;
		this.subject.attach(this);
		roverSim = roverSimulator;
		state = still;
	}

	void avoidCollision() {
		roverSim.performBehavior();
	}

	void sendPhotos() {
		roverSim.takePhoto();
		pictures = roverSim.picArray;
		numberOfPictures = roverSim.picArray.length;
	}

	void stopRovers() {
		state = State.STILL;
		if(subject.getState().contentEquals("stop")) {
			roverSim.stop = true;
			roverSim.performBehavior();
		}

	}


}
