package rovuSystem;

import java.awt.image.BufferedImage;
import java.util.Timer;
import 	java.util.TimerTask;

import javax.vecmath.Vector3d;

import simbad.sim.EnvironmentDescription;

public class CentralStation extends Subject{
	
	int timer = 60;
	double currentPercentageVisited;
	
	BufferedImage[] pictureArray;
	int lengthOfPictureArray;
	Environment mainEnvironment;
	RoverFactory roverFactory;
	static EnvironmentDescription environmentSim;
	
	Rover rover1;
	Rover rover2;
	Rover rover3;
	Rover rover4;
	
	private static CentralStation instance = new CentralStation();
	
	private CentralStation() {
		environmentSim = new EnvironmentSimulator();
		pictureArray = new BufferedImage[40];
		lengthOfPictureArray = 0;
		System.out.println("Central Station live");
	}
	
	// for singleton implementation
	public static CentralStation getInstance() {	
		return instance;
	}

	public void runMission () {
		
		startRovers();
		boolean missionIncomplete = true;
		while (missionIncomplete) {
			if (mainEnvironment.percentageVisited() >= 70.0 ) {
				missionIncomplete = false;
			}
		}
		
		currentPercentageVisited = mainEnvironment.percentageVisited();
		int test = 0;
		Timer t = new Timer();
		t.schedule(new TimerTask() {
	        @Override
	        public void run() {
	        	if (timer != 0 && currentPercentageVisited == mainEnvironment.percentageVisited()) {
	        		timer -= 1;
	        	} else
	        	if (timer != 0 && currentPercentageVisited != mainEnvironment.percentageVisited()) {
	        		currentPercentageVisited = mainEnvironment.percentageVisited();
	        		timer = 60;
	        	} else {
	        		System.out.println("Mission complete!");
	        		System.exit(1);
	        	}
	        }
	    }, 1000);
	}
	
	void intializeMission() {
		mainEnvironment = new Environment(10,10);
		
		int lengthOfEachCell = mainEnvironment.length / 2;
		int widthOfEachCell = mainEnvironment.width / 2;
		
		Environment cell1 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate (0,0));
		Environment cell2 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate (0,0 -widthOfEachCell));
		Environment cell3 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate (0 - lengthOfEachCell,0));
		Environment cell4 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate (0 - lengthOfEachCell,0 -widthOfEachCell));
		
		System.out.println("Cells are created");
		
		//rover1 = roverFactory.getRoverType("rover1", cell1, State.STILL, environmentSim);
		
		
		ExampleRobot robot1 = new ExampleRobot(new Vector3d(0, 0, 0), "Robot 1");
		environmentSim.add(robot1);
		System.out.println("Rover1 alive");
		ExampleRobot robot2 = new ExampleRobot(new Vector3d(1, 0, 0), "Robot 1");
		environmentSim.add(robot2);
		System.out.println("Rover2 alive");
		ExampleRobot robot3 = new ExampleRobot(new Vector3d(0, 0, 1), "Robot 1");
		environmentSim.add(robot3);
		System.out.println("Rover3 alive");
		ExampleRobot robot4 = new ExampleRobot(new Vector3d(1, 0, 1), "Robot 1");
		environmentSim.add(robot4);
		System.out.println("Rover4 alive");
		
		//rover2 = roverFactory.getRoverType("rover2", cell2, State.STILL);
		//rover3 = roverFactory.getRoverType("rover3", cell3, State.STILL);
		//rover4 = roverFactory.getRoverType("rover4", cell4, State.STILL);

		//attach(rover1);
		System.out.println("test1");
		//attach(rover2);
		//attach(rover3);
		//attach(rover4);
	}
	
	
	
	void startRovers() {
		rover1.start();
		rover2.start();
		rover3.start();
		rover4.start();
	}
	
	void stopRovers() {
		rover1.stop();
		rover2.stop();
		rover3.stop();
		rover4.stop();
	}
	
	void savePicture() {
		System.arraycopy(rover1.pictures, 0, pictureArray, 0, rover1.numberOfPictures);
		lengthOfPictureArray = rover1.numberOfPictures;
		
		System.arraycopy(rover2.pictures, 0, pictureArray, lengthOfPictureArray, rover2.numberOfPictures);
		lengthOfPictureArray += rover2.numberOfPictures;
		
		System.arraycopy(rover3.pictures, 0, pictureArray, lengthOfPictureArray, rover3.numberOfPictures);
		lengthOfPictureArray += rover3.numberOfPictures;
		
		System.arraycopy(rover4.pictures, 0, pictureArray, lengthOfPictureArray, rover4.numberOfPictures);
		lengthOfPictureArray += rover4.numberOfPictures;
	}
	
	void handleError() {
		
	}
}
