package rovuSystem;

public class RoverFactory {


	RoverFactory() {

	}


	Rover getRoverType(String type, Environment cell) {
		if(!(type.contentEquals("CAMERAROVER"))) {
			return null;
		} 
		return new Rover(cell);

	}



}
