package rovuSystem;


import simbad.gui.*;
import simbad.sim.*;
import javax.vecmath.Vector3d;


/**
  Derivate your own code from this example.
 */


public class Main {

	public static void main(String[] args) {

		System.setProperty("j3d.implicitAntialiasing", "true");

		EnvironmentSimulator environment = new EnvironmentSimulator();
		
		CentralStation centralStation = CentralStation.getInstance(environment);
		centralStation.intializeMission();
		
		Simbad frame = new Simbad(environment, false);
		frame.update(frame.getGraphics());
		
		centralStation.runMission();
	}

} 
