package rovuSystem;

import simbad.gui.*;
import simbad.sim.*;
import javax.vecmath.Vector3d;

public class Main {
	
	
	public static void main(String[] args) {
		
		
	// request antialising so that diagonal lines are not "stairy"
        System.setProperty("j3d.implicitAntialiasing", "true");
        
        // creation of the environment containing all obstacles and robots
        EnvironmentDescription environment = new ExampleEnvironment();
		
	//stuff
	// create central station and let it do its thing
	
	// here we create an instance of the whole Simbad simulator and we assign the newly created environment 
        Simbad frame = new Simbad(environment, false);
        frame.update(frame.getGraphics());
		
	}

}
