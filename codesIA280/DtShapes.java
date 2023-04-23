package codesIA280;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.util.Iterator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.universe.*;
import org.jogamp.vecmath.*;

import codesIA280.Wonderland.DropAnimation;
import codesIA280.Wonderland.DropKeyBehavior;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.util.Iterator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.universe.*;
import org.jogamp.vecmath.*;
public class DtShapes extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	
	
	/* a function to create the base with a box attached with a string label */
	public static TransformGroup create_Basedt() {
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
	//this base is just the grass ground
		public static TransformGroup create_Base(String str) {
			grass baseShape = new grass();
			Transform3D scaler = new Transform3D();
			scaler.setScale(new Vector3d(4d, 2d, 4d));         // set scale for the 4x4 matrix
			TransformGroup baseTG = new TransformGroup(scaler); 
			baseTG.addChild(baseShape.position_Object());
			return baseTG;
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
	
	/* a function to build the content branch */
	private static BranchGroup create_Scene() {
	    BranchGroup sceneBG = new BranchGroup();
	    TransformGroup sceneTG = new TransformGroup();
	    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

	    // create Tower object and position it
	    DtObject tower3d = new Tower();
	    tower3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));

	    TransformGroup towerTG = new TransformGroup();
	    towerTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	    // Allow changes to the TransformGroup
	    towerTG.addChild(tower3d.position_Object());
	    sceneTG.addChild(towerTG);
	    // create Drop object and position it
	    DtObject drop3d = new Drop();
	    drop3d.setTranslation(new Vector3d(0.0, 0.0, 0.5));

	    TransformGroup dropTG = new TransformGroup();
	    dropTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	    // Allow changes to the TransformGroup
	    dropTG.addChild(drop3d.position_Object());
	    sceneTG.addChild(dropTG);

	    // Add DropAnimation behavior to control the drop's up and down movement
	    DropAnimation dropAnimation = new DropAnimation(dropTG, bounds);
	    sceneTG.addChild(dropAnimation);

	    // Add DropKeyBehavior to control the pause and unpause of the drop animation
	    DropKeyBehavior dropKeyBehavior = new DropKeyBehavior(dropAnimation, bounds);
	    sceneTG.addChild(dropKeyBehavior);
	    sceneBG.addChild(create_Base("RA's Lab6"));
	    sceneBG.addChild(sceneTG);
	    

	    return sceneBG;
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
	        posY = 0; // Initial position
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
	                if (posY >= 0.5) { // Set the top position
	                    posY = 0.5;
	                    goingUp = false;
	                }
	            } else {
	                posY -= speed;
	                if (posY <= -0.6) { // Set the bottom position
	                    posY = -0.6;
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


	/* NOTE: Keep the constructor for each of the labs and assignments */
	public DtShapes(BranchGroup sceneBG) {
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		
		SimpleUniverse su = new SimpleUniverse(canvas);    // create a SimpleUniverse
		CommonsProj.define_Viewer(su, new Point3d(1.0d, 1.0d, 4.0d));
		
		sceneBG.addChild(CommonsProj.add_Lights(CommonsProj.White, 1));
		sceneBG.addChild(CommonsProj.key_Navigation(su));    // allow key navigation
		sceneBG.compile();		                           // optimize the BranchGroup
		su.addBranchGraph(sceneBG);                        // attach the scene to SimpleUniverse

		setLayout(new BorderLayout());
		add("Center", canvas);
		frame.setSize(800, 800);                           // set the size of the JFrame
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		frame = new JFrame("Drop Tower");          
		frame.getContentPane().add(new DtShapes(create_Scene()));  // create an instance of the class
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
