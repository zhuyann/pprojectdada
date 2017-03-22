package rovuSystem;

public class Rover extends Observer{
	
	String name;
	Environment myCell;

	Rover(Environment cell) {
		myCell = cell;
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
