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
	
	 @Override
	void stopRovers() {
		if(state.equals("stop")){
			roverSim.stopRover();
		}
		
		//implement instructions from the Central Station
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
