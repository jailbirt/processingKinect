import processing.opengl.*;
import SimpleOpenNI.*;
import processing.core.PApplet;
import processing.core.PVector;

public class pointCloudwithButton extends PApplet {

        SimpleOpenNI kinect;
        float rotation = 0;
        int boxSize = 150;
        PVector boxCenter = new PVector(0, 0, 600);
        // this will be used for zooming
        // start at normal
        double s = 1;
        public void setup() {
                size(1024, 768, OPENGL);
                kinect = new SimpleOpenNI(this);
                kinect.enableDepth();
        }
        public void draw() {
                background(0);
                kinect.update();
                translate(width/2, height/2, -1000);
                rotateX(radians(180));
                // bumped up the translation
                // so that scale is better centered
                translate(0, 0, 1400);
                rotateY(radians(map(mouseX, 0, width, -180, 180)));
                // make everything bigger, i.e. zoom in
                translate(0,0,(float)s*-1000); //1 Zooming the point Cloud 
                //Every scale 0.1 scale movement we move 100 pixels
                // 1 -> 100% , 1.5 -> increase 50%, 0.8 decrease 20%
                scale((float)s);
                println(s);
                stroke(255);
                PVector[] depthPoints = kinect.depthMapRealWorld();
                // initialize a variable
                // for storing the total
                // points we find inside the box
                // on this frame
                int depthPointsInBox = 0;
                for (int i = 0; i < depthPoints.length; i+=10) {
                        PVector currentPoint = depthPoints[i];
                        // The nested if statements inside of our loop 2
                        // These ones Verify that your points are
                        // inside the box.
                        if (currentPoint.x > boxCenter.x - boxSize/2
                                        && currentPoint.x < boxCenter.x + boxSize/2)
                        {
                                if (currentPoint.y > boxCenter.y - boxSize/2
                                                && currentPoint.y < boxCenter.y + boxSize/2)
                                {
                                        if (currentPoint.z > boxCenter.z - boxSize/2
                                                        && currentPoint.z < boxCenter.z + boxSize/2)
                                        {
                                                depthPointsInBox++; // 3 Increments transparency color.
                                        }
                                }
                        }
                        point(currentPoint.x, currentPoint.y, currentPoint.z);
                }
                println(depthPointsInBox);                                
                //3
                // set the box color's transparency
                // 0 is transparent, 1000 points is fully opaque red
                float boxAlpha = map(depthPointsInBox, 0, 1000, 0, 255); 
                translate(boxCenter.x, boxCenter.y, boxCenter.z);
                // the fourth argument to fill() is "alpha"
                // it determines the color's opacity
                // we set it based on the number of points
                fill(255, 0, 0, boxAlpha);
                stroke(255, 0, 0);
                box(boxSize);

        }
        // use keys to control zoom
        // up-arrow zooms in
        // down arrow zooms out
        // s gets passed to scale() in draw()
        public void keyPressed(){ // 4
                if(keyCode == 38){
                        s = s + 0.01;
                }

                if(keyCode == 40){
                        s = s - 0.01;
                }
        }
        public void mousePressed(){
                save("touchedPoint.png");
        }

}
