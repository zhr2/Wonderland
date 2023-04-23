/* Copyright material for the convenience of students working on Lab Exercises */
package codesIA280;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.j3d.examples.sound.PointSoundBehavior;
import org.jdesktop.j3d.examples.sound.audio.JOALMixer;
import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.java3d.utils.universe.Viewer;
import org.jogamp.vecmath.*;



import codesIA280.DtShapes.DropAnimation;
import codesIA280.DtShapes.DropKeyBehavior;


import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.picking.PickResult;
import org.jogamp.java3d.utils.picking.PickTool;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.*;




public class Wonderland extends JPanel  implements MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static final int OBJ_NUM = 10;
	private static WLSceneObjects[] object3D1 = new WLSceneObjects[OBJ_NUM];
	private static WLShip[] shipobject3D = new WLShip[3];
	private static WLTeaCup[] object3DCup = new WLTeaCup[OBJ_NUM];
	private static WLTeaCup[] object3D = new WLTeaCup[OBJ_NUM];
	private static WLSceneObjects[] object3DBC= new WLSceneObjects[OBJ_NUM];
    
	private static WLTrain[] object3D2 = new WLTrain[OBJ_NUM];
	private static PickTool pickTool;
	private static Canvas3D canvas;
	private static Alpha RotationAlpha;
	private static SoundUtilityJOAL soundJOAL;  
	    private static Alpha RotationAlpha2;
	    private static PointSound ps;
	    private static PointSound ps2;
		//private static PointSound BCpntsnd = CommonsWL.pointSoundBC(); 
		private static WLObjects[] object3D3 = new WLObjects[3];
		private Vector3d viewerPosition = new Vector3d(0, 0, 10); // Adjust these values according to your scene
		private Vector3d viewerRotation = new Vector3d(0, 0, 0);

	    private SimpleUniverse su;
	    private final Point3d initialViewerPosition = new Point3d(0.0, 1.0, 10.0);
	    private final Vector3d initialViewerRotation = new Vector3d(0.0, 0.0, 0.0);



	//this base is just the grass ground
	public static TransformGroup create_Base(String str) {
		grass baseShape = new grass();
		Transform3D scaler = new Transform3D();
		scaler.setScale(new Vector3d(4d, 2d, 4d));         // set scale for the 4x4 matrix
		TransformGroup baseTG = new TransformGroup(scaler); 
		baseTG.addChild(baseShape.position_Object());
		return baseTG;
	}
	
	//Fountain as the reference point in the middle
	private static TransformGroup create_Fountain() {
		TransformGroup fountainTG = new TransformGroup();
		object3D1[0] = new Fountain();                  // create "Fountain"
		fountainTG = object3D1[0].position_Object();             // 
		return fountainTG;
	}
	private static TransformGroup create_Bball() {
		TransformGroup BballTG = new TransformGroup();
		Transform3D zAxis = new Transform3D();
		zAxis.setTranslation(new Vector3d(0.0, -4.0, 1.0)); // Move object away from origin
		zAxis.rotZ(Math.PI/2); // Rotate around the z-axis by 90 degrees (in radians)
		Alpha alpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
                2500, 2500, 200, 2500, 2500,2000);
		BballTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		RotationInterpolator rot_beh = new RotationInterpolator(
				alpha, BballTG, zAxis,  (float) Math.PI * 1.8f,  (float) Math.PI * 2.0f );
		rot_beh.setSchedulingBounds(CommonsWL.hundredBS); 
		BballTG.addChild(rot_beh);
	    BballTG.addChild(new Bball().position_Object());
	    return BballTG;
	}
	private static TransformGroup create_Bball1() {
		TransformGroup BballTG = new TransformGroup();
		Transform3D zAxis = new Transform3D();
		zAxis.setTranslation(new Vector3d(0.0, -4.0, 1.0)); // Move object away from origin
		zAxis.rotZ(Math.PI/2); // Rotate around the z-axis by 90 degrees (in radians)
		Alpha alpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
                2500, 5000, 200, 5000, 2500,2000);
		BballTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		RotationInterpolator rot_beh = new RotationInterpolator(
				alpha, BballTG, zAxis,  (float) Math.PI * 1.8f,  (float) Math.PI * 2.0f );
		rot_beh.setSchedulingBounds(CommonsWL.hundredBS); 
		BballTG.addChild(rot_beh);
	    BballTG.addChild(new Bball1().position_Object());
	    return BballTG;
	}

	private static TransformGroup create_Booth() {
        TransformGroup BoothTG = new TransformGroup();

        object3D3[0] = new boothBase(); 
        object3D3[1] = new boothCenter();  
        object3D3[2] = new boothWindow();   

        BoothTG.addChild(object3D3[0].position_Object()); 
        BoothTG.addChild(object3D3[1].position_Object());
        BoothTG.addChild(object3D3[2].position_Object());
        Transform3D trans = new Transform3D();

        trans.setTranslation(new Vector3d(0, 1.4, 17.5)); // set translation to desired point
        trans.setScale(3d);
        BoothTG.setTransform(trans);


        return BoothTG;
    }
    
    private static TransformGroup create_Trash() {
        TransformGroup BoothTG = new TransformGroup();

        object3D3[0] = new Trash();  
        BoothTG.addChild(object3D3[0].position_Object()); 
        
        Transform3D trans = new Transform3D();
        trans.setTranslation(new Vector3d(-5.0, -0.4, -9.5)); // set translation to desired point

        BoothTG.setTransform(trans);
        return BoothTG;
    }
    
    private static TransformGroup create_Lamp() {
        TransformGroup BoothTG = new TransformGroup();

        object3D3[0] = new StreetLamp();  
        BoothTG.addChild(object3D3[0].position_Object()); 
        Transform3D trans = new Transform3D();
        trans.setTranslation(new Vector3d(-5.0, -0.4, -9.5)); // set translation to desired point
        BoothTG.setTransform(trans);

        return BoothTG;
    }
	private static TransformGroup create_Ship() {
	    TransformGroup ShipTG = new TransformGroup();
	    TransformGroup parentTG = new TransformGroup();
	    Transform3D zAxis = new Transform3D();
	    
	    zAxis.rotZ(Math.PI/2); // Rotate around the z-axis by 90 degrees (in radians)
	    
	    Alpha alpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0,
	            5000, 2500, 200, 5000, 2500,200);
	    ShipTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    RotationInterpolator rot_beh = new RotationInterpolator(
	            alpha, ShipTG, zAxis, (float) Math.PI * 0.5f , (float) Math.PI * 1.5f  );
	    rot_beh.setSchedulingBounds(CommonsWL.hundredBS); 
	    ShipTG.addChild(rot_beh);
	    
	    shipobject3D[1] = new ShipAxel();
	    ShipTG.addChild(shipobject3D[1].position_Object());

	    parentTG.addChild(ShipTG);
	    Transform3D translation = new Transform3D();
	    translation.setTranslation(new Vector3d(0, 9.5, -1.5)); // set translation to desired point
	    parentTG.setTransform(translation);

	    return parentTG;
	}
	
	private static void create_balloon(TransformGroup sceneTG){
        TransformGroup baseTG = new TransformGroup();
        baseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        balloon balloon = new balloon();
        baseTG.addChild(balloon.position_Object());

        Alpha alpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0, 0, 2000, 1000, 4000, 2000, 1000);
        Transform3D axisPosition = new Transform3D();
        axisPosition.rotZ(Math.PI / 2.0);
        ScaleInterpolator scaleInterpol = new ScaleInterpolator(alpha, baseTG, axisPosition, 5.0f, 1.0f);
        scaleInterpol.setSchedulingBounds(CommonsWL.hundredBS);

        sceneTG.addChild(baseTG);
        sceneTG.addChild(scaleInterpol);

    }
	 private static Transform3D tranform1() {
	        Transform3D trans3d = new Transform3D();
	        trans3d.setScale(4.75d);
	        trans3d.setTranslation(new Vector3f(0.0f, -0.07f, 0.2f) );
	        return trans3d;
	    }
	    
	    private static TransformGroup obj1(){
	        TransformGroup obj1 = new TransformGroup(tranform1());
	        obj1.addChild(rotation1());
	        return obj1;
	    }
	    
	    private static TransformGroup rotation1() {
	        TransformGroup trans = new TransformGroup(); 
	        Transform3D rotator = new Transform3D(); 
	        rotator.rotY(Math.PI); 
	        trans.setTransform(rotator); 
	        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); // making it capable to rotate using alpha.
	        RotationAlpha2 = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 6000, 0, 0, 0, 0, 0);
	        RotationInterpolator rotate = new RotationInterpolator(RotationAlpha2, trans, rotator, 0, (float) Math.PI * 2.0f);
	        BoundingSphere zone = new BoundingSphere(new Point3d(), 100.0);
	        rotate.setSchedulingBounds(zone);
	        trans.addChild(rotate);
	        
	        trans.addChild(Train()); //rotate ring     
	        return trans;
	    }
	    
	    private static TransformGroup Train() {
	        TransformGroup comp_TG = new TransformGroup();
	        int flags = ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY;
	        ObjectFile f = new ObjectFile(flags, (float) (60* Math.PI/180.0));
	        Scene s = null;
	        try {
	            s = f.load("/Users/ikramali/eclipse-workspace/COMP2800/train/Train2.obj");
	            
	        } catch (FileNotFoundException e) {
	            System.err.println(e);
	            System.exit(1);
	        }
	        comp_TG.addChild(s.getSceneGroup());
	        Shape3D shape =  (Shape3D) s.getSceneGroup().getChild(0);
	        shape.setAppearance(CommonsWL.obj_Appearance(CommonsWL.Black));
	        
	        shape.setUserData(2);
	        
	        return comp_TG;
	    }
	    private static TransformGroup create_TrainRail() {
			TransformGroup fanTG = new TransformGroup();

		    object3D2[0] = new Trainrail() {
		        public TransformGroup position_Object() {
		            TransformGroup tg = super.position_Object();
		            Transform3D transform = new Transform3D();
		            transform.setTranslation(new Vector3f(0.5f, -1f,0f));
		            transform.setScale(new Vector3d(5.0, 5.0, 5.0)); // scale by (1.5, 1.5, 1.5)
// translate by -5 units in the z-direction
		            tg.setTransform(transform);
		            return tg;
		        }
		    };
		    object3D2[1] = new Trainrail2() {
		        public TransformGroup position_Object() {
		            TransformGroup tg = super.position_Object();
		            Transform3D transform = new Transform3D();
		            transform.setTranslation(new Vector3f(0.0f, 0f, 0.0f));
		            transform.setScale(new Vector3d(0.9, 0.9, 0.9)); // scale by (1.5, 1.5, 1.5)
// translate by -5 units in the z-direction
		            tg.setTransform(transform);
		            return tg;
		        }
		    };
			fanTG = object3D2[0].position_Object(); 
			fanTG.addChild(object3D2[1].position_Object());
			return fanTG;
		}
		
	private static TransformGroup create_Cup() {
	    TransformGroup CupTG = new TransformGroup(); 
	    TransformGroup parentTG = new TransformGroup(); 
	    Transform3D rotator = new Transform3D(); 
	    rotator.rotY(Math.PI); 
	    
	    CupTG.setTransform(rotator); 
	    CupTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); // making it capable to rotate using alpha.
	    RotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 4000, 0, 0, 0, 0, 0);
	    RotationInterpolator rotate = new RotationInterpolator(RotationAlpha, CupTG, rotator, 0, (float) Math.PI * 2.0f);
	    BoundingSphere zone = new BoundingSphere(new Point3d(), 100.0);
	    rotate.setSchedulingBounds(zone);
	    CupTG.addChild(rotate);
	    
	    object3D[0] = new Base(); 
	    object3D[1] = new TeaCup1();
	    object3D[2] = new TeaCup2();  
	    object3D[3] = new TeaCup3();  
	    object3D[4] = new TeaCup4();  
	    object3D[5] = new TeaCup5();  
	    object3D[6] = new TeaCup6();  
	    object3D[7] = new TeaCup7();  

	    
	    CupTG.addChild(object3D[0].position_Object()); 
	    CupTG.addChild(object3D[1].position_Object());
	    CupTG.addChild(object3D[2].position_Object());
	    CupTG.addChild(object3D[3].position_Object()); 
	    CupTG.addChild(object3D[4].position_Object());
	    CupTG.addChild(object3D[5].position_Object());
	    CupTG.addChild(object3D[6].position_Object());
	    CupTG.addChild(object3D[7].position_Object());
	    
	    CupTG.setUserData(0); 

	    // Add translation and scaling to the parentTG TransformGroup
	    Transform3D translation = new Transform3D();
	    translation.setTranslation(new Vector3d(16, 1.6, -6)); // set translation to desired point
	    translation.setScale(2.5);
	    parentTG.setTransform(translation);

	    parentTG.addChild(CupTG);
	    return parentTG;        
	    
	}

	private static TransformGroup create_Cup2() {
	    TransformGroup fanTG = new TransformGroup();
	    TransformGroup parentTG = new TransformGroup(); // Create a new parentTG TransformGroup

	    object3D[0] = new MetalBase(); // create "FanStand"
	    object3D[1] = new Top();  

	    fanTG = object3D[0].position_Object(); 
	    fanTG.addChild(object3D[1].position_Object());

	    // Add translation and scaling to the parentTG TransformGroup
	    Transform3D translation = new Transform3D();
	    translation.setTranslation(new Vector3d(16, 1.5, -6)); // set translation to desired point
	    translation.setScale(2.3);
	    parentTG.setTransform(translation);

	    parentTG.addChild(fanTG);
	    return parentTG;
	}

	
	public static TransformGroup create_BaseFW() {
		Transform3D translator = new Transform3D();        // set 'baseTG' with translation in Y-axis
		translator.setTranslation(new Vector3d(0d, -1.1d, 0d));
		TransformGroup baseTG = new TransformGroup(translator);
		return baseTG;                                     // return an empty 'baseTG'
	}
	
	/* a function to create a scaled string label with or without rotation */	
	private static TransformGroup string_LabelFW(String s, double c, Point3f p, Color3f clr) {		                                                   
		Font my2DFont = new Font("Arial", Font.PLAIN, 1);  // set font's name, style, and size
		FontExtrusion myExtrude = new FontExtrusion();
		Font3D font3D = new Font3D(my2DFont, myExtrude);

		Text3D text3D = new Text3D(font3D, s, p);          // prepare string for the label
		text3D.setAlignment(1);
		Appearance app = CommonsWL.obj_Appearance(clr);

		Transform3D tmp = new Transform3D();
		tmp.setScale(c);                                   // set 'tmp' to a scaling 4x4 matrix
		TransformGroup stringTG = new TransformGroup(tmp); // set 'stringTG' with the scaling factor

		stringTG.addChild(new Shape3D(text3D, app));       // apply scaling to the string label
			
		return stringTG;                                   // return 'stringTG' with the scaled string
	}
	
	private static TransformGroup create_FerrisWheel() {
		WLFerrisWheel ring3D = new  Wheel(); // create the external object
        TransformGroup ringTG = ring3D.position_Object(); 
        TransformGroup sceneTG = new TransformGroup();
        TransformGroup parentTG = new TransformGroup();
	    
        // Create a new TransformGroup for the rotating part of the wheel
        TransformGroup rotatingWheelTG = new TransformGroup();
        rotatingWheelTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rotatingWheelTG.addChild(ringTG); // Add the rotating part of the wheel to the rotatingWheelTG
     // Create a new TransformGroup for the stationary part of the wheel
        TransformGroup stationaryWheelTG = new TransformGroup();
        stationaryWheelTG.addChild(rotatingWheelTG);
        
        // Position the wheel
        Transform3D wheelPosition = new Transform3D();
        wheelPosition.setTranslation(new Vector3f(0, 0, 0));//(0, 0, 0)
        stationaryWheelTG.setTransform(wheelPosition);

        // Add rotation behavior to the rotatingWheelTG
        RotationInterpolator wheelRotator = CommonsWL.RotationBehaviorFW(rotatingWheelTG, 10000); // 10000ms = 10 seconds for a full rotation
        sceneTG.addChild(wheelRotator);
        sceneTG.addChild(stationaryWheelTG); // Add the stationaryWheelTG to the scene

	    WLFerrisWheel stand3D = new Stand();                  // create a new object
	    TransformGroup standTG = stand3D.position_Object(); // position the object
	    Transform3D standTransform = new Transform3D();  // create a new Transform3D object
	    standTransform.setTranslation(new Vector3d(0d, -0.76d, -0.09d)); // set the translation(0d, -0.76d, -0.09d)
	    standTG.setTransform(standTransform);            // set the TransformGroup's transform
	    sceneTG.addChild(standTG); 
	    
	    WLFerrisWheel base3D = new BaseFW();                    // create a new object
	    TransformGroup baseTG = base3D.position_Object(); // position the object
	    Transform3D baseTransform = new Transform3D();   // create a new Transform3D object
	    baseTransform.setTranslation(new Vector3d(0d, -1.6d, 0d)); // set the translation(0d, -1.6d, 0d)
	    baseTG.setTransform(baseTransform);              // set the TransformGroup's transform
	    sceneTG.addChild(baseTG);                        // add the object to the scene
	    Transform3D translation = new Transform3D();
        translation.setTranslation(new Vector3d(9, 8.6, -20.5)); // set translation to desired point
        translation.setScale(6);
        parentTG.setTransform(translation);
        
        parentTG.addChild(sceneTG);
        return parentTG ;
	    
	}
	
	private static TransformGroup create_Field() {
		TransformGroup fieldTG = new TransformGroup();
		object3DBC[0] = new field();                  // create "FanStand"
		fieldTG = object3DBC[0].position_Object();             // set 'fan_baseTG' to FanStand's 'objTG'  
		return fieldTG;
	}
	

	/* a function to create the base with a box attached with a string label */
	public static TransformGroup create_Base() {
		Transform3D translator = new Transform3D();        // set 'baseTG' with translation in Y-axis
		translator.setTranslation(new Vector3d(0d, -1.1d, 0d));
		TransformGroup baseTG = new TransformGroup(translator);
		
		Appearance app = CommonsProj.obj_Appearance(CommonsProj.Orange);
		Box box = new Box(1.0f, 0.1f, 0.25f, Primitive.GENERATE_NORMALS, app);		
		baseTG.addChild(box);                              // attach a box as base to 'baseTG'
		
		String str = "Test";
		baseTG.addChild(string_Label(str, 0.15d,           // attach the string label to 'baseTG'
				new Point3f(-str.length() / 4.0f, -0.25f, 1.6f),  CommonsProj.Magenta));
		
		return baseTG;                                     // return 'baseTG' with labeled base
	}

	/* a function to create a scaled string label with or without rotation */	
	private static TransformGroup string_Label(String s, double c, Point3f p, Color3f clr) {		                                                   
		Font my2DFont = new Font("Arial", Font.PLAIN, 1);  // set font's name, style, and size
		FontExtrusion myExtrude = new FontExtrusion();
		Font3D font3D = new Font3D(my2DFont, myExtrude);

		Text3D text3D = new Text3D(font3D, s, p);          // prepare string for the label
		text3D.setAlignment(1);
		Appearance app =  CommonsProj.obj_Appearance(clr);

		Transform3D tmp = new Transform3D();
		tmp.setScale(c);                                   // set 'tmp' to a scaling 4x4 matrix
		TransformGroup stringTG = new TransformGroup(tmp); // set 'stringTG' with the scaling factor

		stringTG.addChild(new Shape3D(text3D, app));       // apply scaling to the string label
			
		return stringTG;                                   // return 'stringTG' with the scaled string
	}
	private static TransformGroup create_DropTower() {
	    TransformGroup dropTG = new TransformGroup();
	    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
	    DtObject tower3d = new Tower();
	    tower3d.setTranslation(new Vector3d(2.0, 0.8, 2.0));

	    TransformGroup towerTG = new TransformGroup();
	    towerTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	    towerTG.addChild(tower3d.position_Object());
	    dropTG.addChild(towerTG);

	    DtObject drop3d = new Drop();
	    drop3d.setTranslation(new Vector3d(2.0, 0.0, 2.0));

	    TransformGroup dropTG2 = new TransformGroup();
	    dropTG2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	    dropTG2.addChild(drop3d.position_Object());

	    Box box3d = new Box();

	    TransformGroup boxTG = new TransformGroup();
	    boxTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);


	    Transform3D scaleAndTranslate = new Transform3D();
	    scaleAndTranslate.setScale(0.1); 

	    Vector3d boxTranslation = new Vector3d(2.0, -0.95, 2.0);
	    scaleAndTranslate.setTranslation(boxTranslation);

	    Transform3D combinedTransform = new Transform3D();
	    combinedTransform.mul(scaleAndTranslate);
	    boxTG.setTransform(combinedTransform);
	    boxTG.addChild(box3d);

	    dropTG2.addChild(boxTG);
	    dropTG.addChild(dropTG2);

	    DropAnimation dropAnimation = new DropAnimation(dropTG2, bounds);
	    dropTG.addChild(dropAnimation);

	    DropKeyBehavior dropKeyBehavior = new DropKeyBehavior(dropAnimation, bounds);
	    dropTG.addChild(dropKeyBehavior);


	    double scaleFactor = 10; 
	    Transform3D scalingTransform = new Transform3D();
	    scalingTransform.setScale(scaleFactor);

	    TransformGroup scaledDropTG = new TransformGroup(scalingTransform);
	    scaledDropTG.addChild(dropTG);

	    return scaledDropTG;
	}


	
	static class DropAnimation extends Behavior {
	    private TransformGroup targetTG;
	    private Transform3D t3d;
	    private WakeupCriterion wakeupCriterion;
	    private double posY;
	    private double speed;
	    private boolean goingUp;
	    private boolean paused;
	    public DropAnimation(TransformGroup targetTG, BoundingSphere bounds) {
	        this.targetTG = targetTG;
	        this.setSchedulingBounds(bounds);
	        t3d = new Transform3D();
	        posY = 1.0; // Initial position
	        speed = 0.005; // Speed of the drop
	        goingUp = true;
	    }

	    public void initialize() {
	        wakeupCriterion = new WakeupOnElapsedFrames(0);
	        wakeupOn(wakeupCriterion);
	    }

	    public void processStimulus(Iterator<WakeupCriterion> criteria) {
	        if (!paused) { // Add this condition to respect the paused state
	            targetTG.getTransform(t3d);
	            Vector3d translation = new Vector3d();
	            t3d.get(translation);

	            if (goingUp) {
	                posY += speed;
	                if (posY >= 2.0) { // Set the top position
	                    posY = 2.0;
	                    goingUp = false;
	                }
	            } else {
	                posY -= speed;
	                if (posY <= 1.0) { // Set the bottom position
	                    posY = 1.0;
	                    goingUp = true;
	                }
	            }

	            translation.y = posY;
	            t3d.setTranslation(translation);
	            targetTG.setTransform(t3d);
	        }
	        wakeupOn(wakeupCriterion);
	    }
	        
	        public void setPaused(boolean paused) {
	            this.paused = paused;
	        }

	        public boolean isPaused() {
	            return paused;
	        }
	    }
	    
	public static class DropKeyBehavior extends Behavior {
	    private DropAnimation dropAnimation;
	    private WakeupOnAWTEvent wakeupCriterion;

	    public DropKeyBehavior(DropAnimation dropAnimation, BoundingSphere bounds) {
	        this.dropAnimation = dropAnimation;
	        this.setSchedulingBounds(bounds);
	    }

	    public void initialize() {
	        wakeupCriterion = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	        wakeupOn(wakeupCriterion);
	    }

	    public void processStimulus(Iterator<WakeupCriterion> criteria) {
	        while (criteria.hasNext()) {
	            WakeupCriterion wakeupCriterion = criteria.next();
	            if (wakeupCriterion instanceof WakeupOnAWTEvent) {
	                AWTEvent[] events = ((WakeupOnAWTEvent) wakeupCriterion).getAWTEvent();
	                for (AWTEvent event : events) {
	                    if (event instanceof KeyEvent) {
	                        KeyEvent keyEvent = (KeyEvent) event;
	                        if (keyEvent.getKeyChar() == 'x' || keyEvent.getKeyChar() == 'X') {
	                            // Toggle the pause state of the DropAnimation
	                            dropAnimation.setPaused(!dropAnimation.isPaused());
	                        }
	                    }
	                }
	            }
	        }
	        wakeupOn(wakeupCriterion);
	    }
	}
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();

	    if (keyCode == KeyEvent.VK_UP) {
	        viewerRotation.x -= 0.1;
	    } else if (keyCode == KeyEvent.VK_DOWN) {
	        viewerRotation.x += 0.1;
	    } else if (keyCode == KeyEvent.VK_LEFT) {
	        viewerRotation.y -= 0.1;
	    } else if (keyCode == KeyEvent.VK_RIGHT) {
	        viewerRotation.y += 0.1;
	    }
	      else if (keyCode == KeyEvent.VK_R) {
	        resetViewer();
	    }

	    else if (keyCode == KeyEvent.VK_W) {
	        viewerPosition.x += 0.1 * Math.sin(viewerRotation.y);
	        viewerPosition.y += 0.1 * Math.sin(viewerRotation.x);
	        viewerPosition.z -= 0.1 * Math.cos(viewerRotation.y);
	    }

	    updateViewer();
	    if (keyCode == KeyEvent.VK_B) {
	        if (BumperCar.bc1alpha.isPaused() && BumperCar.bc2alpha.isPaused() && BumperCar.bc3alpha.isPaused() && BumperCar.bc4alpha.isPaused() && BumperCar.bc5alpha.isPaused()) {
	            BumperCar.bc1alpha.resume();
	            BumperCar.bc2alpha.resume();
	            BumperCar.bc3alpha.resume();
	            BumperCar.bc4alpha.resume();
	            BumperCar.bc5alpha.resume();
	            //BCpntsnd.setEnable(true);
	        } else {
	            BumperCar.bc1alpha.pause();
	            BumperCar.bc2alpha.pause();
	            BumperCar.bc3alpha.pause();
	            BumperCar.bc4alpha.pause();
	            BumperCar.bc5alpha.pause();
	            //BCpntsnd.setEnable(false);
	        }
	    }
	}

	public void keyReleased(KeyEvent e) { }
	public void keyTyped(KeyEvent e) { }
	
	private void updateViewer() {
	    Transform3D transform = new Transform3D();
	    transform.lookAt(new Point3d(viewerPosition), new Point3d(viewerPosition.x + Math.sin(viewerRotation.y), viewerPosition.y + Math.sin(viewerRotation.x), viewerPosition.z - Math.cos(viewerRotation.y)), new Vector3d(0, 1, 0));
	    transform.invert();
	    su.getViewingPlatform().getViewPlatformTransform().setTransform(transform);
	}
	private void resetViewer() {
	    viewerPosition.set(initialViewerPosition);
	    viewerRotation.set(initialViewerRotation);
	    updateViewer();
	}



	public static TransformGroup create_Basecar() {
		Transform3D translator = new Transform3D();        
		translator.setTranslation(new Vector3d(0d, -1.1d, 0d));
		TransformGroup baseTG = new TransformGroup(translator);
		
		return baseTG;                                     // return an empty 'baseTG'
	}

	/* a function to create a scaled string label with or without rotation */	
	private static TransformGroup string_Labelcar(String s, double c, Point3f p, Color3f clr) {		                                                   
		Font my2DFont = new Font("Arial", Font.PLAIN, 1);  // set font's name, style, and size
		FontExtrusion myExtrude = new FontExtrusion();
		Font3D font3D = new Font3D(my2DFont, myExtrude);

		Text3D text3D = new Text3D(font3D, s, p);          // prepare string for the label
		text3D.setAlignment(1);
		Appearance app = CommonsProj.obj_Appearance(clr);

		Transform3D tmp = new Transform3D();
		tmp.setScale(c);                                   // set 'tmp' to a scaling 4x4 matrix
		TransformGroup stringTG = new TransformGroup(tmp); // set 'stringTG' with the scaling factor

		stringTG.addChild(new Shape3D(text3D, app));       // apply scaling to the string label
			
		return stringTG;                                   // return 'stringTG' with the scaled string
	}

	private static TransformGroup create_Carousel() {
	    TransformGroup carouselTG = new TransformGroup();
	    carouselTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	    // Create and position the car base
	    CarObject horse3D = new Chorse();
	    TransformGroup horseTG = horse3D.position_Object();
	    Transform3D horseT3D = new Transform3D();
	    horseT3D.setTranslation(new Vector3f(0.0f, 3.0f, 0.0f));
	    horseT3D.setScale(5.0);
	    horseTG.setTransform(horseT3D);
	    
	    CarObject tent3D = new Ctent();
	    TransformGroup tentTG = tent3D.position_Object();
	    Transform3D tentT3D = new Transform3D();
	    tentT3D.setTranslation(new Vector3f(0.0f, 4.2f, 0.0f));
	    tentT3D.setScale(5.0);
	    tentTG.setTransform(tentT3D);

	    // Create and position the floor
	    CarObject floor3D = new Cfloor();
	    TransformGroup floorTG = floor3D.position_Object();
	    Transform3D floorT3D = new Transform3D();
	    floorT3D.setTranslation(new Vector3f(0.0f, 1.6f, 0.0f));
	    floorT3D.setScale(5.0);
	    floorTG.setTransform(floorT3D);
	    
	    // Add the floor, horse, and tent to the carousel TransformGroup
	    carouselTG.addChild(floorTG);
	    carouselTG.addChild(horseTG);
	    carouselTG.addChild(tentTG);

	    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.POSITIVE_INFINITY);

	    // Create CarouselAnimation behavior to control the carousel's rotation
	    CarouselAnimation carouselAnimation = new CarouselAnimation(carouselTG, bounds);
	    carouselTG.addChild(carouselAnimation);

	    // Create CarouselKeyBehavior to control the pause and unpause of the carousel animation
	    CarouselKeyBehavior carouselKeyBehavior = new CarouselKeyBehavior(carouselAnimation, bounds);
	    carouselTG.addChild(carouselKeyBehavior);

	    return carouselTG;
	}


	static class CarouselAnimation extends Behavior {
	    private TransformGroup targetTG;
	    private Transform3D t3d;
	    private Alpha carouselAlpha;
	    private WakeupCriterion wakeupCriterion;
	    private boolean paused;

	    public CarouselAnimation(TransformGroup targetTG, BoundingSphere bounds) {
	        this.targetTG = targetTG;
	        this.setSchedulingBounds(bounds);
	        t3d = new Transform3D();
	        carouselAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 20000, 0, 0, 0, 0, 0);
	    }

	    public void initialize() {
	        wakeupCriterion = new WakeupOnElapsedFrames(0);
	        wakeupOn(wakeupCriterion);
	    }

	    public void processStimulus(Iterator<WakeupCriterion> criteria) {
	        if (!paused) {
	            float rotationAngle = carouselAlpha.value() * (float) Math.PI * 2.0f;
	            t3d.rotY(rotationAngle);
	            targetTG.setTransform(t3d);
	        }
	        wakeupOn(wakeupCriterion);
	    }

	    public void setPaused(boolean paused) {
	        this.paused = paused;
	    }

	    public boolean isPaused() {
	        return paused;
	    }
	}

	static class CarouselKeyBehavior extends Behavior {
	    private CarouselAnimation carouselAnimation;
	    private WakeupOnAWTEvent wakeupCriterion;

	    
	    public CarouselKeyBehavior(CarouselAnimation carouselAnimation, BoundingSphere bounds) {
	        this.carouselAnimation = carouselAnimation;
	        this.setSchedulingBounds(bounds);
	    }
	    
	    public void initialize() {
	        wakeupCriterion = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	        wakeupOn(wakeupCriterion);
	    }

	    public void processStimulus(Iterator<WakeupCriterion> criteria) {
	        while (criteria.hasNext()) {
	            WakeupCriterion wakeupCriterion = criteria.next();
	            if (wakeupCriterion instanceof WakeupOnAWTEvent) {
	                AWTEvent[] events = ((WakeupOnAWTEvent) wakeupCriterion).getAWTEvent();
	                for (AWTEvent event : events) {
	                    if (event instanceof KeyEvent) {
	                        KeyEvent keyEvent = (KeyEvent) event;
	                        if (keyEvent.getKeyChar() == 'z' || keyEvent.getKeyChar() == 'Z') {
	                            // Toggle the pause state of the CarouselAnimation
	                            carouselAnimation.setPaused(!carouselAnimation.isPaused());
	                        }

	                    }
	                }
	            }
	        }
	        wakeupOn(wakeupCriterion);
	    }
	}




	//a function to build the content branch 
	public static BranchGroup create_Scene() {
		BranchGroup sceneBG = new BranchGroup();
		TransformGroup sceneTG = new TransformGroup();	 
		TransformGroup sceneTG2 = new TransformGroup();	   // make 'sceneTG' continuously rotating

		
		sceneTG2.addChild(CommonsWL.rotate_Behavior(5000, sceneTG2));
		sceneTG.addChild(create_Cup());                    // add the fan to the rotating 'sceneTG'
		sceneTG.addChild(create_Cup2());
		sceneTG.addChild(create_TrainRail());  
		sceneTG.addChild(obj1());
		
		 sceneTG.addChild(pointSound()); 
	  sceneTG.addChild(pointSoundTrain());
	        ps2.setEnable(false);
	        
		sceneTG.addChild(create_Fountain());                    // add the fan to the rotating 'sceneTG'
		sceneTG.addChild(create_Ship());
		sceneTG.addChild(new ShipObject().position_Object());
		sceneTG.addChild(new BballBase().position_Object());
		sceneTG.addChild(new BballNet().position_Object());
		sceneTG.addChild(create_Bball());
		sceneTG.addChild(new BballBase1().position_Object());
		sceneTG.addChild(new BballNet1().position_Object());
		sceneTG.addChild(create_Bball1());
		sceneTG.addChild(new treeTop().position_Object());
		sceneTG.addChild(new treeBot().position_Object());
		sceneTG.addChild(new treeTop1().position_Object());
		sceneTG.addChild(new treeBot1().position_Object());
		sceneTG.addChild(new treeTop2().position_Object());
		sceneTG.addChild(new treeBot2().position_Object());
		sceneTG.addChild(new treeTop3().position_Object());
		sceneTG.addChild(new treeBot3().position_Object());
		sceneTG.addChild(new Gate().position_Object());
		sceneTG.addChild(new HotAir().position_Object());
		sceneTG.addChild(new HotAir1().position_Object());
		sceneTG.addChild(new HotAir2().position_Object());
		sceneTG.addChild(new HotAir3().position_Object());
		sceneTG.addChild(new Bench().position_Object());
		   BumperCar.createContent1(sceneTG);
			BumperCar.create_bc1(sceneTG);                    // add the fan to the rotating 'sceneTG'
			BumperCar.create_bc2(sceneTG);   
			BumperCar.create_bc3(sceneTG); 
			BumperCar.create_bc4(sceneTG); 
			BumperCar.create_bc5(sceneTG);
			bumperCar6 b6 = new bumperCar6();
			sceneTG.addChild(b6.position_Object());
			sceneTG.addChild(BumperCar.create_Field());			//sceneTG.addChild(BCpntsnd);

		pickTool = new PickTool(sceneBG);                // allow picking of objects in 'sceneBG'
		pickTool.setMode(PickTool.BOUNDS);                 // pick by bounding volume
		pickTool.setMode(PickTool.GEOMETRY);
		
		
		//create_balloon(sceneTG);
	     sceneTG.addChild(create_Booth());
	        sceneTG.addChild(create_Trash());
	       sceneTG.addChild(create_Lamp());
		
	       // TransformGroup swingsTG = create_Swings();
	       //3 sceneTG.addChild(swingsTG);
		
	    // create the drop tower and add it to a new TransformGroup
	   //create the drop tower and add it to a new TransformGroup
	    TransformGroup dropTG = new TransformGroup();
	   dropTG.addChild(create_DropTower());
	    sceneTG.addChild(dropTG); // add the dropTG TransformGroup to the scene graph

	    sceneTG.addChild(create_FerrisWheel());
	    TransformGroup carouselTG = create_Carousel();
	    Transform3D carouselTranslation = new Transform3D();
	    carouselTranslation.setTranslation(new Vector3f(-5.0f, -2.5f, -21.0f));

	    TransformGroup carouselTranslationTG = new TransformGroup();
	    carouselTranslationTG.setTransform(carouselTranslation);
	    carouselTranslationTG.addChild(carouselTG);

	    sceneBG.addChild(carouselTranslationTG);

	    sceneBG.addChild(sceneTG);
	    sceneBG.addChild(sceneTG2);
		// keep the following stationary
		KeyListener keyListener = null;
		frame.addKeyListener(keyListener);
		sceneBG.addChild(CommonsWL.add_Lights(CommonsWL.White, 1));
		sceneBG.addChild(new AxisShape(3).position_Object());
		TextureLoader loader = new TextureLoader("/Users/ikramali/eclipse-workspace/COMP2800/images/WLbackground.jpg", null);
	    sceneBG.addChild(create_Base("RA's Lab6"));
		ImageComponent2D image = loader.getImage();
        Background background = new Background();
        background.setImage(image);
        background.setApplicationBounds(sceneBG.getBounds());
        sceneBG.addChild(background);
		return sceneBG;
	}
	public Wonderland(Canvas3D canvas3D) {
	    canvas = canvas3D;
	    frame = new JFrame("Wonderland"); // initialize frame variable
	}

	public Wonderland(BranchGroup sceneBG) {
	    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
	    
	    canvas = new Canvas3D(config);
	    canvas.addMouseListener(this);                     // NOTE: enable mouse clicking 
	    su = new SimpleUniverse(canvas);
	    
	    Point3d viewerPosition = new Point3d(0.0, 1.0, 10.0);
	    CommonsWL.define_Viewer(su, viewerPosition);
	    // Remove the following line
	    // SimpleUniverse su = new SimpleUniverse(canvas);    // create a SimpleUniverse
	    CommonsWL.enableAudio(su);  

	    CommonsWL.define_Viewer(su, new Point3d(0.25d, 0.25d, 10.0d));
	    
	    sceneBG.addChild(CommonsWL.key_Navigation(su));     // allow key navigation
	    sceneBG.compile();                                   // optimize the BranchGroup
	    su.addBranchGraph(sceneBG);                        // attach the scene to SimpleUniverse

	    setLayout(new BorderLayout());
	    add("Center", canvas);
	    frame.setSize(800, 800);                           // set the size of the JFrame
	    frame.setVisible(true);
	    canvas.addKeyListener(this);
	    frame.addKeyListener(this);
	}


	 
		
		private static PointSound pointSound() {
	        URL url = null;
	        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/sound/carniva.wav";
	        try {
	            url = new URL("file", "localhost", filename);
	        } catch (Exception e) {
	            System.out.println("Can't open " + filename);
	        }
	        ps = new PointSound(); // create and position a point sound
	        ps.setCapability(PointSound.ALLOW_ENABLE_WRITE);
	        ps.setCapability(PointSound.ALLOW_IS_PLAYING_READ);
	        PointSoundBehavior player = new PointSoundBehavior(ps, url, new Point3f(0.0f, 0.0f, 0.0f));
	        player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
	        return ps;
	    }
		  private static PointSound pointSoundTrain() {
		        URL url = null;
		        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/sound/trainSoundEffect.wav";
		        try {
		            url = new URL("file", "localhost", filename);
		        } catch (Exception e) {
		            System.out.println("Can't open " + filename);
		        }
		        ps2 = new PointSound(); // create and position a point sound
		        ps2.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		        ps2.setCapability(PointSound.ALLOW_IS_PLAYING_READ);
		        PointSoundBehavior player = new PointSoundBehavior(ps2, url, new Point3f(0.0f, 0.0f, 0.0f));
		        player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		        return ps2;
		    }
	    
	    private void enableAudio(SimpleUniverse simple_U) {
	        JOALMixer mixer = null;                                 // create a null mixer as a joalmixer
	        Viewer viewer = simple_U.getViewer();
	        viewer.getView().setBackClipDistance(20.0f);         // make object(s) disappear beyond 20f 

	        if (mixer == null && viewer.getView().getUserHeadToVworldEnable()) {                                                             
	            mixer = new JOALMixer(viewer.getPhysicalEnvironment());
	            if (!mixer.initialize()) {                       // add mixer as audio device if successful
	                System.out.println("Open AL failed to init");
	                viewer.getPhysicalEnvironment().setAudioDevice(null);
	            }
	        }
	    }

	   

	public static void main(String[] args) {
		frame = new JFrame("Wonderland");                   // NOTE: change XY to student's initials
		frame.getContentPane().add(new Wonderland(create_Scene()));  // start the program
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void mouseClicked(MouseEvent event) {
        TransformGroup trans = new TransformGroup(); 
        int x = event.getX(); int y = event.getY();        // mouse coordinates
        Point3d point3d = new Point3d(), center = new Point3d();
        canvas.getPixelLocationInImagePlate(x, y, point3d);// obtain AWT pixel in ImagePlate coordinates
        canvas.getCenterEyeInImagePlate(center);           // obtain eye's position in IP coordinates
        
        Transform3D transform3D = new Transform3D();       // matrix to relate ImagePlate coordinates~
        canvas.getImagePlateToVworld(transform3D);         // to Virtual World coordinates
        transform3D.transform(point3d);                    // transform 'point3d' with 'transform3D'
        transform3D.transform(center);                     // transform 'center' with 'transform3D'

        Vector3d mouseVec = new Vector3d();
        mouseVec.sub(point3d, center);
        mouseVec.normalize();
        pickTool.setShapeRay(point3d, mouseVec);           // send a PickRay for intersection
        
        if (pickTool.pickClosest() != null) {
            PickResult pickResult = pickTool.pickClosest(); 
            Node node = pickResult.getNode(PickResult.SHAPE3D);
            
            if((int) node.getUserData() == 0) {
                RotationAlpha.pause();
                node.setUserData(1);
            }
            
            else if((int) node.getUserData() == 1){
                RotationAlpha.resume();
                node.setUserData(0);
            }
            
            else if((int) node.getUserData() == 2){
                RotationAlpha2.pause();
                node.setUserData(3);
                ps2.setEnable(true);

            }
            
            else if((int) node.getUserData() == 3){
                RotationAlpha2.resume();
                node.setUserData(2);
                ps2.setEnable(false);

            }
            
        }
    }
	 public void mouseEntered(MouseEvent arg0) {}
	    public void mouseExited(MouseEvent arg0) { }
	    public void mousePressed(MouseEvent e) { }
	    public void mouseReleased(MouseEvent e) { }
}


