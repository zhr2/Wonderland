package codesIA280;
import java.util.Iterator;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.ColoringAttributes;
import org.jogamp.java3d.PointSound;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnCollisionEntry;
import org.jogamp.java3d.WakeupOnCollisionExit;
import org.jogamp.vecmath.Color3f;
//import org.jogamp.java3d.*;

/* This behavior of collision detection highlights the
    object when it is in a state of collision. */
public class WLCollision2 extends Behavior {
	public static boolean inCollision;
	private Shape3D shape;
	private ColoringAttributes objColoring;
	private Appearance objAppearance;
	private WakeupOnCollisionEntry wEnter;
	private WakeupOnCollisionExit wExit;
	
	//public static PointSound pntsnd = CommonsAH.pointSound();
	

	public WLCollision2(Shape3D s) {
		shape = s; // save the original color of 'shape"
		objAppearance = shape.getAppearance();
		objColoring = objAppearance.getColoringAttributes();
		inCollision = false;
	}

	@Override
	public void initialize() { // USE_GEOMETRY USE_BOUNDS
		wEnter = new WakeupOnCollisionEntry(shape, WakeupOnCollisionEntry.USE_GEOMETRY);
		wExit = new WakeupOnCollisionExit(shape, WakeupOnCollisionExit.USE_GEOMETRY);
		wakeupOn(wEnter); // initialize the behavior
	}

	@Override
	public void processStimulus(Iterator<WakeupCriterion> criteria) {
		Color3f hilightClr = CommonsWL.Red;
		ColoringAttributes highlight = new ColoringAttributes(hilightClr, ColoringAttributes.SHADE_GOURAUD);
		inCollision = !inCollision; // collision has taken place

		if (inCollision) { // change color to highlight 'shape'
            
			//Wonderland.object3DWL[1].get_obj().setAppearance(CommonsAH.obj_Appearance(CommonsAH.Green)); 
			objAppearance.setColoringAttributes(highlight);
			//pntsnd.setEnable(true);
			wakeupOn(wExit); // keep the color until no collision
		} else { // change color back to its original
			objAppearance.setColoringAttributes(objColoring);
			//Wonderland.object3DWL[1].get_obj().setAppearance(CommonsAH.obj_Appearance(CommonsAH.Green)); 
			//pntsnd.setEnable(false);
			wakeupOn(wEnter); // wait for collision happens
		}
	}
}
