package codesIA280;

import java.awt.Font;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.*;
import codesIA280.CommonsRA;
import codesIA280.Lab1ShapesRA;
import codesIA280.Lab6ShapesRA;
/* an abstract super class for create different objects */
public abstract class WLSceneShapes {
	protected TransformGroup objTG = new TransformGroup(); // use 'objTG' to position an object
	protected abstract Node create_Object();               // allow derived classes to create different objects
	public abstract Node position_Object();         // retrieve 'objTG' to which 'obj_shape' is attached
	protected Appearance app;                              // allow each object to define its own appearance
	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // A3: attach the next transformGroup to 'objTG'
	}
}

class AxisShape extends WLSceneShapes {
	float length;
	public AxisShape(float n) {
		length = n; 
	}
	protected Node create_Object() {
		Point3f coor[] = new Point3f[4]; //4 points, one for origin and 3 for the endpoints of axis
		LineArray lineArr = new LineArray(6, LineArray.COLOR_3 | LineArray.COORDINATES); 
		coor[0] = new Point3f(0, 0, 0); //origin
		coor[1] = new Point3f(length, 0, 0); //x
		coor[2] = new Point3f(0, length, 0); //y
		coor[3] = new Point3f(0, 0, length); //z
		lineArr.setCoordinate(0, coor[0]);       
		lineArr.setCoordinate(1, coor[1]);
		lineArr.setColor(0, CommonsWL.Red);
		lineArr.setColor(1, CommonsWL.Red);
		lineArr.setCoordinate(2, coor[0]);       
		lineArr.setCoordinate(3, coor[2]);
		lineArr.setColor(2, CommonsWL.Green);
		lineArr.setColor(3, CommonsWL.Green);
		lineArr.setCoordinate(4, coor[0]);       
		lineArr.setCoordinate(5, coor[3]);
		lineArr.setColor(4, CommonsWL.Blue);
		lineArr.setColor(5, CommonsWL.Blue);
		return new Shape3D(lineArr);  
	}
	public Node position_Object() {
		return create_Object();
	}
}


