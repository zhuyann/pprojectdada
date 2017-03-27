package rovuSystem;

import java.awt.image.BufferedImage;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.CameraSensor;
import simbad.sim.RobotFactory;

public class RoverSimulator extends Agent {
	private String currentMode;
	private int maxDistance = 4;                   // hardcoded in this case, we should calculate it instead
	//private int maxDistance = EnvironmentSimulator.WORLD_SIZE / 2 - 1;	
	double Velocity;
	Vector3d initialPosition;
	
	BufferedImage[] picArray;
	int numberOfImages;
	BufferedImage cameraImage;
	CameraSensor camera;
	Environment myCell;

	public RoverSimulator (Vector3d position, String name, Environment cell){   
		super(position,name);
		Velocity = 0.5;
		initialPosition = position;
		myCell = cell;
		RobotFactory.addSonarBeltSensor(this, 4);  
		camera = RobotFactory.addCameraSensor(this);
		cameraImage = camera.createCompatibleImage();
		picArray = new BufferedImage[10];
		numberOfImages = 0;
	}
	
	private Point3d location() {
		Point3d loc = new Point3d();
		this.getCoords(loc);
		return loc;
	}
	

	public void initBehavior() {
		setTranslationalVelocity(10.5);
	}
	
	private boolean onTheCellEdge() {

		Point3d loc = location();
		
		
	 return initialPosition.getX()-1 >= loc.getX() || loc.getX() >=(initialPosition.getX() + maxDistance)
		 || initialPosition.getZ()+1 <= loc.getZ() || loc.getZ() <=(initialPosition.getZ() - maxDistance);

	}

	public void performBehavior() {
		if (collisionDetected()) {
		//	System.out.println("collision");

			//collision avoidance strategy

			
		} else if(onTheCellEdge()) {
		//	System.out.print(name);
		//	System.out.println("on edge");
	
		}
		
		
		else {
			// progress at 0.5 m/s
			setTranslationalVelocity(0.5);
			if ((getCounter() % 100)==0) 
				setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
			
			// check current location and see if we are on coordinate to take photo
			Coordinate currentLocation = new Coordinate();
			Point3d loc = new Point3d();
	        this.getCoords(loc);
	//		currentLocation = currentLocation.translate(loc);
			for (int i = myCell.coordinatePool.length-1; i>=0; i--) {
				

	//			if ((double)myCell.coordinatePool[i].xValue == loc.x && (double)myCell.coordinatePool[i].yValue == loc.y) {              == exactly on coordinate
				if ((Math.abs((Math.abs((double)myCell.coordinatePool[i].xValue) - Math.abs(loc.x))) < 0.2 )) { // (Math.abs((Math.abs((double)myCell.coordinatePool[i].yValue) - Math.abs(loc.y))) < 0.7 )) {              // close enough
					// take photo and remove coordinate from coordinate pool
					System.out.println("On coordinate");
				//	takePhoto();
					myCell.removeFromCoordinatePool(myCell.coordinatePool[i]);
					
				}
			}
	//		System.out.print(loc.x);
	//		System.out.println(loc.y);
		}
	}

	public void stopRover() {
		setTranslationalVelocity(0.0);
		setRotationalVelocity(0);
	}

	public void takePhoto() {
		camera.copyVisionImage(cameraImage);
		picArray[numberOfImages] = cameraImage;
		numberOfImages++;
	}


}
