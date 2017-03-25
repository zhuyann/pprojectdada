package rovuSystem;

import java.awt.image.BufferedImage;
import java.util.Timer;
import 	java.util.TimerTask;

import javax.vecmath.Vector3d;

import simbad.sim.EnvironmentDescription;

public class CentralStation extends Subject{
	
	int timer = 60;
	double currentPercentageVisited = 0;
	
	BufferedImage[] pictureArray;
	int lengthOfPictureArray;
	Environment mainEnvironment;
	static EnvironmentDescription environmentSim;
	
	Rover rover1;
	Rover rover2;
	Rover rover3;
	Rover rover4;
	
	private static CentralStation instance = new CentralStation();
	
	private CentralStation() {
		pictureArray = new BufferedImage[40];
		lengthOfPictureArray = 0;
		System.out.println("Central Station is initialized");

	}

	// for singleton implementation
	public static CentralStation getInstance(EnvironmentSimulator environment) {
		environmentSim = environment;
		return instance;
	}

	public void runMission () {
		
		startRovers();
		boolean missionIncomplete = true;
		
		System.out.println("run");
		while (missionIncomplete) {
			System.out.println("running");
			if (currentPercentageVisited >= 70.0 ) {
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
				stopRovers();
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

		Environment cell1 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell2 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell3 = new Environment(lengthOfEachCell, widthOfEachCell);
		Environment cell4 = new Environment(lengthOfEachCell, widthOfEachCell);

		rover1 = new Rover(new RoverSimulator(new Vector3d(1,0,1),"rover1"), cell1, State.STILL);
		environmentSim.add(rover1.roverSim);
		
		rover2 = new Rover(new RoverSimulator(new Vector3d(-1,0,-1),"rover2"), cell2, State.STILL);
		environmentSim.add(rover2.roverSim);
		
		rover3 = new Rover(new RoverSimulator(new Vector3d(1,0,-1),"rover3"), cell3, State.STILL);
		environmentSim.add(rover3.roverSim);
		
		rover4 = new Rover(new RoverSimulator(new Vector3d(-1,0,1),"rover4"), cell4, State.STILL);
		environmentSim.add(rover4.roverSim);
		
		
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
