package rovuSystem;

public class RoverFactory {


	RoverFactory() {

	}


	Rover getRoverType(String name, Environment cell) {
		
		return new Rover(name, cell);

	}



}
