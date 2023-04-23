package codesIA280;
/* Copyright material for students taking COMP-2800 to work on assignment/labs/projects. */

import java.awt.Font;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Cone;
import org.jogamp.vecmath.*;

public abstract class Lab1ShapesRA {
	protected abstract Node create_Object();	           // use 'Node' for both Group and Shape3D
	public abstract Node position_Object();
}

class StarShape extends Lab1ShapesRA {
	protected Node create_Object() {
		float r = 0.6f, x, y;                              // vertices at 0.6 away from origin
		Point3f coor[] = new Point3f[5];                   // declare 5 points for star shape
		LineArray lineArr = new LineArray(10, LineArray.COLOR_3 | LineArray.COORDINATES);
		for (int i = 0; i <= 4; i++) {                     // define coordinates for star shape
			x = (float) Math.cos(Math.PI / 180 * (90 + 72 * i)) * r;
			y = (float) Math.sin(Math.PI / 180 * (90 + 72 * i)) * r;
			coor[i] = new Point3f(x, y, -0.6f);            // use z-value to position star shape
		}
		for (int i = 0; i <= 4; i++) {
			lineArr.setCoordinate(i * 2, coor[i]);         // define point pairs for each line
			lineArr.setCoordinate(i * 2 + 1, coor[(i + 2) % 5]);
			lineArr.setColor(i * 2, CommonsRA.Red);        // specify color for each pair of points
			lineArr.setColor(i * 2 + 1, CommonsRA.Green);
		}
		return new Shape3D(lineArr);                        // create and return a Shape3D
	}
	public Node position_Object() {
		return create_Object();
	}
}

class CircleShape extends Lab1ShapesRA {
	int n;
	public CircleShape(int numSides) { //constructor determines # of sides to use
		if (numSides < 5) //needs atleadt 5 sides to circle the star
			n = 5;
		else //rounds value to the nearesr 5 
			n = numSides-numSides%5;
	}
	protected Node create_Object() {
		float r = 0.6f, x, y;                              // vertices at 0.6 away from origin
		Point3f coor[] = new Point3f[n];                   // declare n points for circle based on constructor
		LineArray lineArr = new LineArray(2*n, LineArray.COLOR_3 | LineArray.COORDINATES);
		for (int i = 0; i < n; i++) {                     // define coordinates for circle shape
			x = (float) Math.cos(Math.PI / 180 * (90 + 72.0 / (n/5) * i)) * r; //n/5 sides in between each star point
			y = (float) Math.sin(Math.PI / 180 * (90 + 72.0 / (n/5) * i)) * r; //srat equation but with n/5
			coor[i] = new Point3f(x, y, -0.6f);            // use z-value to position star shape
		}
		for (int i = 0; i < n; i++) {
			lineArr.setCoordinate(i*2, coor[i]);         // define point pairs for each line
			lineArr.setCoordinate(i*2 + 1, coor[(i+1)%n]); //% makes sure last and first point connect
			lineArr.setColor(i*2 , CommonsRA.Red);        // specify color for each pair of points
			lineArr.setColor(i *2+ 1, CommonsRA.Green);
		}
		return new Shape3D(lineArr);  
	}
	public Node position_Object() {
		return create_Object();
	}
}

class AxisShape extends Lab1ShapesRA {
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
		lineArr.setColor(0, CommonsRA.Red);
		lineArr.setColor(1, CommonsRA.Red);
		lineArr.setCoordinate(2, coor[0]);       
		lineArr.setCoordinate(3, coor[2]);
		lineArr.setColor(2, CommonsRA.Green);
		lineArr.setColor(3, CommonsRA.Green);
		lineArr.setCoordinate(4, coor[0]);       
		lineArr.setCoordinate(5, coor[3]);
		lineArr.setColor(4, CommonsRA.Blue);
		lineArr.setColor(5, CommonsRA.Blue);
		return new Shape3D(lineArr);  
	}
	public Node position_Object() {
		return create_Object();
	}
}

class ConeShape extends Lab1ShapesRA {
	private TransformGroup objTG;                          // use 'objTG' to position an object
	public ConeShape() {
		Transform3D translator = new Transform3D();        // 4x4 matrix for translation
		translator.setTranslation(new Vector3f(0f, 0f, 0.3f));
		Transform3D rotator = new Transform3D();           // 4x4 matrix for rotation
		rotator.rotX(Math.PI / -2);
		Transform3D trfm = new Transform3D();              // 4x4 matrix for composition
		trfm.mul(translator);                              // apply translation next
		trfm.mul(rotator);                                 // apply rotation first
		objTG = new TransformGroup(trfm);                  // set the combined transformation
		
		objTG.addChild(create_Object());
	}
	protected Node create_Object() {
		return new Cone(0.6f, 0.6f, CommonsRA.obj_Appearance(CommonsRA.Orange));
	}
	public Node position_Object() {
		return objTG;
	}
}

class StringShape extends Lab1ShapesRA {
	private TransformGroup objTG;                              // use 'objTG' to position an object
	private String str;
	public StringShape(String str_ltrs) {
		str = str_ltrs;		
		Transform3D scaler = new Transform3D();
		scaler.setScale(0.2);                              // scaling 4x4 matrix 
		objTG = new TransformGroup(scaler);
		objTG.addChild(create_Object());		   // apply scaling to change the string's size
	}
	protected Node create_Object() {
		Font my2DFont = new Font("Arial", Font.PLAIN, 1);  // font's name, style, size
		FontExtrusion myExtrude = new FontExtrusion();
		Font3D font3D = new Font3D(my2DFont, myExtrude);		

		Point3f pos = new Point3f(-str.length()/4f, 0, 3f);// position for the string 
		Text3D text3D = new Text3D(font3D, str, pos);      // create a text3D object
		return new Shape3D(text3D);
	}
	public Node position_Object() {
		return objTG;
	}
}
