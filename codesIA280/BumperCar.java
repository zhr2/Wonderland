package codesIA280;

import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.*;

import org.jdesktop.j3d.examples.collision.Box;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Alpha;
import org.jogamp.java3d.ColoringAttributes;




import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;


import org.jogamp.vecmath.Vector3d;

import codesIA280.CollisionDetectShapes;


public class BumperCar {
    protected static Alpha bc1alpha, bc2alpha, bc3alpha, bc4alpha, bc5alpha;
    private static final int OBJ_NUM = 11;
	public static WLSceneObjects[] object3DWL = new WLSceneObjects[OBJ_NUM];
    
	public static TransformGroup createColumn(double scale, Vector3d pos) {
		Transform3D transM = new Transform3D();
		transM.set(scale, pos);                            // Create baseTG with 'scale' and 'position'
		TransformGroup baseTG = new TransformGroup(transM);

		Shape3D shape = new Box(4.5, 3.0, 3.5);
		baseTG.addChild(shape);                            // Create a column as a box and add to 'baseTG'

		Appearance app = shape.getAppearance();
		ColoringAttributes ca = new ColoringAttributes();
		ca.setColor(0.6f, 0.3f, 0.0f);                     // set column's color and make changeable
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		app.setColoringAttributes(ca);
		TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparency(0.7f);
			                             // set appearance's material
		
        
		ta.setTransparencyMode(TransparencyAttributes.BLEND_ZERO);
        app.setTransparencyAttributes(ta);

		CollisionDetectShapes cd = new CollisionDetectShapes(shape);
		cd.setSchedulingBounds(CommonsWL.twentyBS);        // detect column's collision

		baseTG.addChild(cd);                               // add column with behavior of CollisionDector
		return baseTG;
	}
	public static TransformGroup createColumn2(double scale, Vector3d pos) {
		Transform3D transM = new Transform3D();
		transM.set(scale, pos);                            // Create baseTG with 'scale' and 'position'
		TransformGroup baseTG = new TransformGroup(transM);

		Shape3D shape = new Box(1.5, 3.0, 3.5);
		baseTG.addChild(shape);                            // Create a column as a box and add to 'baseTG'

		Appearance app = shape.getAppearance();
		ColoringAttributes ca = new ColoringAttributes();
		ca.setColor(0.6f, 0.3f, 0.0f);                     // set column's color and make changeable
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		app.setColoringAttributes(ca);
		TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparency(0.7f);
			                             // set appearance's material
		
        
		ta.setTransparencyMode(TransparencyAttributes.BLEND_ZERO);
        app.setTransparencyAttributes(ta);

		WLCollision2 cd2 = new WLCollision2(shape);
		cd2.setSchedulingBounds(CommonsWL.twentyBS);        // detect column's collision

		baseTG.addChild(cd2);                               // add column with behavior of CollisionDector
		return baseTG;
	}
	public static TransformGroup createColumn3(double scale, Vector3d pos) {
		Transform3D transM = new Transform3D();
		transM.set(scale, pos);                            // Create baseTG with 'scale' and 'position'
		TransformGroup baseTG = new TransformGroup(transM);

		Shape3D shape = new Box(4.5, 3.0, 3.5);
		baseTG.addChild(shape);                            // Create a column as a box and add to 'baseTG'

		Appearance app = shape.getAppearance();
		ColoringAttributes ca = new ColoringAttributes();
		ca.setColor(0.6f, 0.3f, 0.0f);                     // set column's color and make changeable
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		app.setColoringAttributes(ca);
		TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparency(0.7f);
			                             // set appearance's material
		
        
		ta.setTransparencyMode(TransparencyAttributes.BLEND_ZERO);
        app.setTransparencyAttributes(ta);

		WLCollision3 cd2 = new WLCollision3(shape);
		cd2.setSchedulingBounds(CommonsWL.twentyBS);        // detect column's collision

		baseTG.addChild(cd2);                               // add column with behavior of CollisionDector
		return baseTG;
	}

	public static void createContent1(TransformGroup scene_TG) {
        
		scene_TG.addChild(createColumn3(0.22, new Vector3d(-13.02, -0.5, 17.0)));
        scene_TG.addChild(createColumn2(0.22, new Vector3d(-13.8, -0.5, 9.3)));
        scene_TG.addChild(createColumn(0.22, new Vector3d(-9.82, -0.5, 11.0))); //0.12 og scale
    }
    public static void create_bc1(TransformGroup scene_TG){
		TransformGroup baseTG = new TransformGroup();
		baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		bumperCar1 car = new bumperCar1();
		baseTG.addChild(car.position_Object());

		bc1alpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0 ,0, 2000, 1000, 
		2000, 3000, 1000);
		Transform3D axisPosition = new Transform3D();
		axisPosition.rotY(Math.PI / 6.0);
		
		//axisPosition.set(new Vector3d(1, 0, 0), Math.PI / 4.0);
		PositionInterpolator positionInterpol = new PositionInterpolator(bc1alpha,  baseTG, axisPosition, -1.2f, 0.9f);
		positionInterpol.setSchedulingBounds(CommonsWL.hundredBS);
		

		scene_TG.addChild(baseTG);
		scene_TG.addChild(positionInterpol);
		bc1alpha.pause();
		
	}
	public static void create_bc2(TransformGroup scene_TG){
		TransformGroup baseTG = new TransformGroup();
		baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		bumperCar2 car = new bumperCar2();
		baseTG.addChild(car.position_Object());

		bc2alpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0 ,0, 2000, 1000, 
		2000, 3000, 1000);
		Transform3D axisPosition = new Transform3D();
		axisPosition.rotY(Math.PI / 2.0);
		PositionInterpolator positionInterpol = new PositionInterpolator(bc2alpha, baseTG, axisPosition, -2.7f, 1.4f);
		positionInterpol.setSchedulingBounds(CommonsWL.hundredBS);


		scene_TG.addChild(baseTG);
		scene_TG.addChild(positionInterpol);
		bc2alpha.pause();
		
	}
	public static void create_bc3(TransformGroup scene_TG){
		TransformGroup baseTG = new TransformGroup();
		baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		bumperCar3 car = new bumperCar3();
		baseTG.addChild(car.position_Object());

		bc3alpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0 ,0, 2000, 1000, 
		3000, 2000, 1000);
		Transform3D axisPosition = new Transform3D();
		axisPosition.rotX(Math.PI / 2.0);
		PositionInterpolator positionInterpol = new PositionInterpolator(bc3alpha, baseTG, axisPosition, -1.5f, 0.9f);
		positionInterpol.setSchedulingBounds(CommonsWL.hundredBS);


		scene_TG.addChild(baseTG);
		scene_TG.addChild(positionInterpol);
		bc3alpha.pause();
		
	}
	public static void create_bc4(TransformGroup scene_TG){
		TransformGroup baseTG = new TransformGroup();
		baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		bumperCar4 car = new bumperCar4();
		baseTG.addChild(car.position_Object());

		bc4alpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0 ,0, 2000, 500, 
		3500, 500, 1000);
		Transform3D axisPosition = new Transform3D();
		axisPosition.rotY(4.0 * (Math.PI) / 3.0);
		PositionInterpolator positionInterpol = new PositionInterpolator(bc4alpha, baseTG, axisPosition, -2.4f, 0.9f);
		positionInterpol.setSchedulingBounds(CommonsWL.hundredBS);
		//try making decressing speed really low/high so that bumpercars actually BUMP

		scene_TG.addChild(baseTG);
		scene_TG.addChild(positionInterpol);
		bc4alpha.pause();
		
	}
	public static void create_bc5(TransformGroup scene_TG){
		TransformGroup baseTG = new TransformGroup();
		baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		bumperCar5 car = new bumperCar5();
		baseTG.addChild(car.position_Object());

		bc5alpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0 ,0, 2000, 1000, 
		3000, 2000, 1000);
		Transform3D axisPosition = new Transform3D();
		axisPosition.rotY(3.0 * (Math.PI) / 2.0);
		PositionInterpolator positionInterpol = new PositionInterpolator(bc5alpha, baseTG, axisPosition, -2.7f, 2.3f);
		positionInterpol.setSchedulingBounds(CommonsWL.hundredBS);


		scene_TG.addChild(baseTG);
		scene_TG.addChild(positionInterpol);
		bc5alpha.pause();
		
	}
    public static TransformGroup create_Field() {
		TransformGroup fieldTG = new TransformGroup();
		object3DWL[0] = new field();                  // create "FanStand"
		fieldTG = object3DWL[0].position_Object();             // set 'fan_baseTG' to FanStand's 'objTG'  
		return fieldTG;
	}
}

