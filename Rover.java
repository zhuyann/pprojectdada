package rovuSystem;

public class Rover extends Observer{
	
	String name;
	Environment myCell;
	State state;

	Rover(String name, Environment cell) {
		this.name = name;
		myCell = cell;
		state
		
	}
	
	
	void update() {
		//implement instructions from the Central Station
	}
	
	void start() {
		//start rover
	}
	
	void stop() {
		//stop rover
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
