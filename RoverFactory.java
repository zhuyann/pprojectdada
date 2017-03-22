package rovuSystem;

public class RoverFactory {


	RoverFactory() {

	}


	Rover getRoverType(String name, Environment cell, State initialState) {
		
		return new Rover(name, cell, initialState);

	}



}
