package rovuSystem;

import java.awt.image.BufferedImage;

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
	private int maxDistance;

	public RoverSimulator (Vector3d position, String name, Environment cell, Environment environment){   
		super(position,name);
		RobotFactory.addSonarBeltSensor(this, 4);

		Velocity = 0.5;
		initialPosition = position;

		picArray = new BufferedImage[10];
		numberOfImages = 0;
		camera = RobotFactory.addCameraSensor(this);
		cameraImage = camera.createCompatibleImage();

		myCell = cell;
		mainEnvironment = environment; 
		maxDistance = myCell.length-1;
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

		return initialPosition.getX()-1 >= loc.getX() || loc.getX() >=(initialPosition.getX() + maxDistance)
			|| initialPosition.getZ()+1 <= loc.getZ() || loc.getZ() <=(initialPosition.getZ() - maxDistance);
	}

	public void performBehavior() {	
		if (onTheCellEdge()) {
			this.state = State.EDGE_OF_CELL;
		} else if (this.collisionDetected()) {
			this.state = State.AVOIDING_OBSTACLE;	
		} else {
			this.state = State.MOVING;
		}

		if (this.state == State.MOVING) {
			this.setTranslationalVelocity(Velocity);
			
			if ((getCounter() % 100) == 0) {
				setRotationalVelocity(Math.PI / 2 * (0.5 - Math.random()));
			}

			Point3d loc = location();
			for (int i = myCell.coordinatePool.length-1; i>=0; i--) {
				if ((Math.abs((Math.abs((double)myCell.coordinatePool[i].xValue) - Math.abs(loc.x))) < 0.2 ) && 
					(Math.abs((Math.abs((double)myCell.coordinatePool[i].yValue) - Math.abs(loc.z))) < 0.2 )) {
					// take photo and remove coordinate from coordinate pool if rover is close to coordinate from coordinate pool
					//	takePhoto();
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
