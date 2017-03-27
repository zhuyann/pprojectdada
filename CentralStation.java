package rovuSystem;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.vecmath.Vector3d;
import simbad.sim.EnvironmentDescription;

public class CentralStation extends Subject{
	
	int timer = 30;
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
	
	void intializeMission() {	
		mainEnvironment = new Environment(10,10, new Coordinate(-5,-5));
		int lengthOfEachCell = mainEnvironment.length / 2;
		int widthOfEachCell = mainEnvironment.width / 2;
				
		Environment cell1 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate(0,0));
		Environment cell2 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate(0,-widthOfEachCell));
		Environment cell3 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate(-lengthOfEachCell,-widthOfEachCell));
		Environment cell4 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate(-lengthOfEachCell,0));

		rover1 = new Rover(new RoverSimulator(new Vector3d(-4,0,-1),"rover1", cell1, mainEnvironment), cell1, State.STILL);
		environmentSim.add(rover1.roverSim);
		
		rover2 = new Rover(new RoverSimulator(new Vector3d(1,0,-1),"rover2", cell2, mainEnvironment), cell2, State.STILL);
		environmentSim.add(rover2.roverSim);
		
		rover3 = new Rover(new RoverSimulator(new Vector3d(-4,0,4),"rover3", cell3, mainEnvironment), cell3, State.STILL);
		environmentSim.add(rover3.roverSim);
		
		rover4 = new Rover(new RoverSimulator(new Vector3d(1,0,4),"rover4", cell4, mainEnvironment), cell4, State.STILL);
		environmentSim.add(rover4.roverSim);
		
		cell1.defineCoordinates();
		cell2.defineCoordinates();
		cell3.defineCoordinates();
		cell4.defineCoordinates();
		mainEnvironment.defineCoordinates();
	}
	
	public void runMission () {
		
		startRovers();
		boolean missionIncomplete = true;
		
		System.out.println("Running");
		
		while (missionIncomplete) {
			if (mainEnvironment.percentageVisited() >= 70.0 ) {
				System.out.println("The rovers covered more than 70%, but will continue running for a while.");
				missionIncomplete = false;
			}
		}
		
		
		currentPercentageVisited = mainEnvironment.percentageVisited();
		Timer t = new Timer();
		t.schedule(new TimerTask() {
	        @Override
	        public void run() {
	        	if (timer != 0 && currentPercentageVisited == mainEnvironment.percentageVisited()) {
	        		timer -= 1;
	        		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
	        		run();
	        	} else
	        	if (timer != 0 && currentPercentageVisited != mainEnvironment.percentageVisited()) {
	        		currentPercentageVisited = mainEnvironment.percentageVisited();
	        		timer = 30;
	        		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
	        		run();
	        	} else {
	        		stopRovers();
	        		System.out.printf("Mission complete! \nThe rovers visited %.3f %% of the environment. \n ", currentPercentageVisited);
	        		System.exit(0);
	        	}
	        }
	    }, 1000);
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
