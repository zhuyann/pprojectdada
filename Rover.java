package rovuSystem;


public class Rover extends Observer{
	
	String name;
	Environment myCell;
	State state;

	Rover(String name, Environment cell, State initialState) {
		this.name = name;
		myCell = cell;
		state = initialState;
		
	}
	
	
	void update() {
		//implement instructions from the Central Station
	}
	
	void start() {
		//start rover
		state = State.MOVING;
	}
	
	void stop() {
		//stop rover
		state = State.STILL;
	}
	
	void avoidCollision() {
		//if obstacle is detected, implement avoid collision algorithm
	}
	
	void communicateWithTheCS() {
		//send feedback to Central Station
	}
	
	void takeAndSendPhotos() {
		
	}
	

}
