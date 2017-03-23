package rovuSystem;

import java.awt.image.BufferedImage;

import javax.vecmath.Vector3d;
import simbad.sim.Agent;
import simbad.sim.CameraSensor;
import simbad.sim.RobotFactory;

public class RoverSimulator extends Agent {
	BufferedImage[] picArray;
	int numberOfImages;
	BufferedImage cameraImage;
	CameraSensor camera;
	
    public RoverSimulator (Vector3d position, String name) {     
        super(position,name);
        
        RobotFactory.addSonarBeltSensor(this, 4);  
        camera = RobotFactory.addCameraSensor(this);
        cameraImage = camera.createCompatibleImage();
        picArray = new BufferedImage[10];
        numberOfImages = 0;
    }
    
    public void initBehavior() {
    	setTranslationalVelocity(0.5);
    }
    
    
    public void performBehavior() {
        if (collisionDetected()) {
        	
        	//collision avoidance strategy
            
        } else {
            // progress at 0.5 m/s
            setTranslationalVelocity(0.5);
            // frequently change orientation 
            if ((getCounter() % 100)==0) 
               setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
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