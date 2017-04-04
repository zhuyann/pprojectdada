package rovuSystem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.CameraSensor;
import simbad.sim.RobotFactory;

import simbad.sim.LightSensor;
import simbad.sim.RangeSensorBelt;

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
	
	private static final double FACTOR = 1;
    private static final double MAX_VELOCITY = FACTOR * 0.4;
    private static final double MAX_ANGULAR_VELOCITY = FACTOR * Math.PI;
    private RangeSensorBelt sonars, bumpers;
    Sensor minAngle;
    LightSensor sensorLeft;
    LightSensor sensorRight;
    LightSensor sensorRearLeft;
    LightSensor sensorRearRight;
    
    private class Sensor {
        public double angle;
        public double measurement;
        public int number;

        public Sensor(double angle, double measurement, int number) {
            this.angle = angle;
            this.measurement = measurement;
            this.number = number;
        }

        public Sensor() {
            this(0, 0, 0);
        }
    }

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
		
		bumpers = RobotFactory.addBumperBeltSensor(this);
        sonars = RobotFactory.addSonarBeltSensor(this, 24);
        sensorLeft = RobotFactory.addLightSensorLeft(this);
        sensorRight = RobotFactory.addLightSensorRight(this);
        sensorRearLeft = addLightSensorRearLeft(this);
        sensorRearRight = addLightSensorRearRight(this);
	}
	
	 static public LightSensor addLightSensorRearRight(Agent agent) {
	        Vector3d front = new Vector3d(agent.getRadius() + 0.5, 0, 0);
	        Transform3D t3d = new Transform3D();
	        t3d.rotY((-Math.PI / 4) * 3);
	        Vector3d right = new Vector3d(front);
	        t3d.transform(right);
	        return RobotFactory.addLightSensor(agent, right, (float) (-Math.PI / 4) * 3,
	                "rear_right");
	    }

	    static public LightSensor addLightSensorRearLeft(Agent agent) {
	        Vector3d front = new Vector3d(agent.getRadius() + 0.5, 0, 0);
	        Transform3D t3d = new Transform3D();
	        t3d.rotY((Math.PI / 4) * 3);
	        Vector3d right = new Vector3d(front);
	        t3d.transform(right);
	        return RobotFactory.addLightSensor(agent, right, (float) (Math.PI / 4) * 3,
	                "rear_left");
	    }

	private Point3d location() {
		Point3d loc = new Point3d();
		this.getCoords(loc);
		return loc;
	}

	public void initBehavior() {
		System.out.println("I am alive");
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
			followLamp();
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
					for (int count = 0; count<4; count++) {
					takePhoto();
					setRotationalVelocity(Math.PI / 2);
					}
					System.out.println("New point scanned");
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

		System.out.println("Following lamp");
        Sensor minPositiveAngle = new Sensor();
        Sensor minNegativeAngle = new Sensor();
        if (collisionDetected()) moveToStartPosition();
        if (bumpers.oneHasHit()) {
            setTranslationalVelocity(-0.1);
            setRotationalVelocity(0.1 * Math.random());

        } else {
            for (int i = 0; i < sonars.getNumSensors(); i++) {
                if (sonars.hasHit(i)) {
                    if (i <= minPositiveAngle.number + 2) {
                        minPositiveAngle.angle = sonars.getSensorAngle(i) + (2 * Math.PI / sonars.getNumSensors());
                        minPositiveAngle.measurement = sonars.getMeasurement(i);
                    }
                    if ((i - sonars.getNumSensors()) % sonars.getNumSensors() >= minNegativeAngle.number - 2) {
                        minNegativeAngle.angle = (sonars.getSensorAngle(i) - 2 * Math.PI - (2 * Math.PI / sonars.getNumSensors())) % (2 * Math.PI);
                        minNegativeAngle.measurement = sonars.getMeasurement(i);
                    }
                }
            }
            if (Math.abs(minNegativeAngle.angle) >= Math.abs(minPositiveAngle.angle)) {
                minAngle = minNegativeAngle;
            } else {
                minAngle = minPositiveAngle;
            }

            double nextVel = MAX_VELOCITY;
            double nextAngVel = 0;

            double maxHappiness = 0;

            for (double i = 0.001; i < MAX_VELOCITY; i += MAX_VELOCITY / 10) {
                for (double j = -MAX_ANGULAR_VELOCITY; j < MAX_ANGULAR_VELOCITY; j += MAX_ANGULAR_VELOCITY / 10) {
                    double happiness = happinessFunction(i, j, 0.1, 5, 17);
                    if (happiness > maxHappiness) {
                        maxHappiness = happiness;
                        nextVel = i;
                        nextAngVel = j;
                    }
                }
            }

            setRotationalVelocity(nextAngVel);
            setTranslationalVelocity(nextVel);
        }
    }
	
	private double happinessFunction(double velocity, double angularVelocity, double velocityWeight, double obstacleWeight, double aimWeight) {
        return velocityHappinessFunction(velocity, angularVelocity) * velocityWeight
                + obstacleHappinessFunction(velocity, angularVelocity) * obstacleWeight
                + aimHappinessFunction(velocity, angularVelocity) * aimWeight;
    }

    private double aimHappinessFunction(double velocity, double angularVelocity) {
        float llum = sensorLeft.getAverageLuminance();
        float rlum = sensorRight.getAverageLuminance();
        float rllum = sensorRearLeft.getAverageLuminance();
        float rrlum = sensorRearRight.getAverageLuminance();
        if (Math.abs(rrlum - rlum) + Math.abs(rrlum - llum) + Math.abs(rrlum - rllum) < 0.1) {
            moveToStartPosition();
        }

        double desiredRotationalVelocity;
        if ((llum > rllum && llum > rrlum && rlum > rrlum && rlum > rllum)) { //front
            desiredRotationalVelocity = (llum - rlum) * Math.PI / 4;
        } else if (llum > rlum && llum > rrlum && rllum > rlum && rllum > rrlum) { //left
            desiredRotationalVelocity = (rllum - llum) * Math.PI / 4 + Math.PI / 2;
        } else if (rlum > llum && rlum > rllum && rrlum > llum && rrlum > rllum) { //right
            desiredRotationalVelocity = (rlum - rrlum) * Math.PI / 4 - Math.PI / 2;
        } else if (rrlum > rlum && rrlum > llum && rllum > llum && rllum > rlum) { //back
            desiredRotationalVelocity = (rllum - rrlum) * Math.PI / 4;
            if (rrlum > rllum) {
                desiredRotationalVelocity = Math.PI - desiredRotationalVelocity;
            } else {
                desiredRotationalVelocity = -Math.PI - desiredRotationalVelocity;
            }

        } else {
            desiredRotationalVelocity = 0;
        }
        desiredRotationalVelocity /= 1.5;
        double result = 1;
        if (angularVelocity == 0) {
            result = 1 - Math.abs(desiredRotationalVelocity / Math.PI);
        } else if (desiredRotationalVelocity == 0) {
            result = 1 - Math.abs(angularVelocity / Math.PI);
        } else if (Math.abs(angularVelocity) > Math.abs(desiredRotationalVelocity)) {
            result = desiredRotationalVelocity / angularVelocity;
        } else {
            result = angularVelocity / desiredRotationalVelocity;
        }
        if (result > 0) {
            return result;
        } else {
            return 0;
        }
    }

    private double obstacleHappinessFunction(double velocity, double angularVelocity) {
        if (minAngle.angle == 0) {
            return Math.floor(2 - 2 * Math.abs(angularVelocity / Math.PI));
        } else if (Math.signum(minAngle.angle) != Math.signum(angularVelocity)) {

            angularVelocity = Math.signum(minAngle.angle) * angularVelocity;
            double angle = Math.abs(Math.sin(minAngle.angle) / minAngle.measurement);

            if (2 * minAngle.angle / angle > angularVelocity / velocity) {
                return 1 - (angularVelocity / velocity) / (2 * minAngle.angle / angle);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private double velocityHappinessFunction(double velocity, double angularVelocity) {
        return velocity / MAX_VELOCITY;
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
