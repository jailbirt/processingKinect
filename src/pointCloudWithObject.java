
import processing.opengl.*;
import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.*;

public class pointCloudWithObject extends PApplet {
	SimpleOpenNI kinect;

public	void setup() {
		  size(1024, 768, OPENGL);
		  kinect = new SimpleOpenNI(this);
		  kinect.enableDepth();
		}
public	void draw() {
		  background(0);
		  kinect.update();
		  // prepare to draw centered in x-y
		  // pull it 1000 pixels closer on z
		  translate(width/2, height/2, -1000); //1
		  rotateX(radians(180)); // flip y-axis from "realWorld" 2
		  stroke(255); //3
		  // get the depth data as 3D points
		  PVector[] depthPoints = kinect.depthMapRealWorld(); //4
		  for(int i = 0; i < depthPoints.length; i++){
		    // get the current point from the point array
		    PVector currentPoint = depthPoints[i];
		    // draw the current point
		    point(currentPoint.x, currentPoint.y, currentPoint.z); //5
		  }
		}

}
