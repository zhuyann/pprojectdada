package rovuSystem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.CameraSensor;
import simbad.sim.RobotFactory;

public class RoverSimulator extends Agent {
	private State state;

	double Velocity;
	Vector3d initialPosition;

	BufferedImage[] picArray;
	int numberOfImages;
	BufferedImage cameraImage;
	CameraSensor camera;

	Environment myCell;
	Environment mainEnvironment;
	private double maxDistance;
	boolean stop;

	public RoverSimulator (Vector3d position, String name, Environment cell, Environment environment){   
		super(position,name);
		RobotFactory.addSonarBeltSensor(this, 4);

		Velocity = 0.5;
		initialPosition = position;

		picArray = new BufferedImage[1000];
		numberOfImages = 0;
		camera = RobotFactory.addCameraSensor(this);
		cameraImage = camera.createCompatibleImage();

		myCell = cell;
		mainEnvironment = environment; 
		maxDistance = 5-1.6;
		stop = false;
	}

	private Point3d location() {
		Point3d loc = new Point3d();
		this.getCoords(loc);
		return loc;
	}

	public void initBehavior() {
		setTranslationalVelocity(1);
	}

	private boolean onTheCellEdge() {
		Point3d loc = location();

		return initialPosition.getX()-0.1 >= loc.getX() || loc.getX() >=(initialPosition.getX() + maxDistance)
				|| initialPosition.getZ()+0.1 <= loc.getZ() || loc.getZ() <=(initialPosition.getZ() - maxDistance);
	}

	public void performBehavior() {	
		if(myCell.lampOn){
			this.state = State.FOLLOWING_LAMP;
		}
		if (onTheCellEdge()) {
			this.state = State.EDGE_OF_CELL;
		} else if (this.collisionDetected()) {
			this.state = State.AVOIDING_OBSTACLE;	
		} else if(stop) { 
			this.state = State.STILL;
		}	else {
			this.state = State.MOVING;
		} 


		if (this.state == State.STILL) {
			stopRover();
		} else if(this.state == State.FOLLOWING_LAMP) {
			//	followLamp();
		} else if (this.state == State.MOVING) {
			this.setTranslationalVelocity(Velocity);

			if ((getCounter() % 100) == 0) {
				setRotationalVelocity(Math.PI / 2 * (0.5 - Math.random()));
			}

			Point3d loc = location();
			for (int i = myCell.coordinatePool.length-1; i>=0; i--) {
				if ((Math.abs((Math.abs((double)myCell.coordinatePool[i].xValue) - Math.abs(loc.x))) < 0.1 ) && 
						(Math.abs((Math.abs((double)myCell.coordinatePool[i].yValue) - Math.abs(loc.z))) < 0.1 )) {
					// take photo and remove coordinate from coordinate pool if rover is close to coordinate from coordinate pool
					takePhoto();
					System.out.println("4 photos taken");
					myCell.removeFromCoordinatePool(myCell.coordinatePool[i]);
					mainEnvironment.removeFromCoordinatePool(mainEnvironment.coordinatePool[i]);
				}
			}
		} else if (this.state == State.EDGE_OF_CELL) {
			this.Velocity=-Velocity;
			this.setTranslationalVelocity(Velocity);		
		} else {
			this.Velocity=-Velocity;
			this.setTranslationalVelocity(Velocity);
		}
	}

	public void followLamp() {

	}

	public void stopRover() {
		setTranslationalVelocity(0.0);
		setRotationalVelocity(0);
	}

	public void takePhoto() {
		camera.copyVisionImage(cameraImage);
		savePhoto(cameraImage, numberOfImages);
		numberOfImages++;

	}

	public void savePhoto(BufferedImage pic, int number) {
		File outputFile = new File (name + "pic" + number + ".png");
		try {
			ImageIO.write(pic, "png", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
