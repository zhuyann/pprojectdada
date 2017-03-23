package rovuSystem;

import java.awt.image.BufferedImage;
import java.util.Timer;
import 	java.util.TimerTask;

public class CentralStation extends Subject{
	
	int timer = 60;
	double currentPercentageVisited;
	
	BufferedImage[] pictureArray;
	int lengthOfPictureArray;
	Environment mainEnvironment;
	RoverFactory roverFactory;
	
	Rover rover1;
	Rover rover2;
	Rover rover3;
	Rover rover4;
	
	private static CentralStation instance = new CentralStation();
	
	private CentralStation() {
		pictureArray = new BufferedImage[40];
		lengthOfPictureArray = 0;
	}
	
	// for singleton implementation
	public static CentralStation getInstance() {
		return instance;
	}

	public void runMission () {
		intializeMission();
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
		int lengthOfEachCell = mainEnvironment.length / 2;
		int widthOfEachCell = mainEnvironment.width / 2;
		
		Environment cell1 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate (0,0));
		Environment cell2 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate (0,0 -widthOfEachCell));
		Environment cell3 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate (0 - lengthOfEachCell,0));
		Environment cell4 = new Environment(lengthOfEachCell, widthOfEachCell, new Coordinate (0 - lengthOfEachCell,0 -widthOfEachCell));
		
		rover1 = roverFactory.getRoverType("rover1", cell1, State.STILL);
		rover2 = roverFactory.getRoverType("rover2", cell2, State.STILL);
		rover3 = roverFactory.getRoverType("rover3", cell3, State.STILL);
		rover4 = roverFactory.getRoverType("rover4", cell4, State.STILL);
		
		attach(rover1);
		attach(rover2);
		attach(rover3);
		attach(rover4);
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
