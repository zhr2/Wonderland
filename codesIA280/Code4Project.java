package codesIA280;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jogamp.java3d.Locale;
import org.jogamp.java3d.*;
import org.jogamp.java3d.Raster;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.geometry.Cone;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.java3d.utils.universe.*;
import org.jogamp.vecmath.*;

public class Code4Project extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;	
	static final int width = 600;                            // size of each Canvas3D
	static final int height = 600;

	// use hash table to map the name of a Viewer to its KeyNavigatorBehavior
	Hashtable<String, KeyNavigatorBehavior>	m_KeyHashtable = null;
	private Canvas3D[] canvas3D;
	private Wonderland[] code4Proj = new Wonderland[3];
	Hashtable<String, MouseListener> m_MouseHashtable = null;
	
	public Code4Project(){
		m_KeyHashtable = new Hashtable<String, KeyNavigatorBehavior>( );
		m_MouseHashtable = new Hashtable<String, MouseListener>( );
		
		canvas3D = new Canvas3D[2];		
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration( );
		for (int i = 0; i < 2; i++) {
			canvas3D[i] = new Canvas3D( config );
			canvas3D[i].setSize(width,height);
			if (i > 0)
				code4Proj[i] = new Wonderland(canvas3D[i]);
			add(canvas3D[i]);                            // add 3 Canvas3D to Frame
		}		
		ViewingPlatform vp = new ViewingPlatform(2);       // a VP with 2 TG about it		
		Viewer viewer = new Viewer( canvas3D[0] );         // point 1st Viewer to c3D[0]
		Transform3D t3d = new Transform3D( );
		t3d.rotX( Math.PI / 2.0 );                         // rotate and position the 1st ~
		t3d.setTranslation( new Vector3d( 0, 0, -35 ) );   // viewer looking down from top
		t3d.invert( );
		MultiTransformGroup mtg = vp.getMultiTransformGroup( );
		mtg.getTransformGroup(0).setTransform(t3d);

		SimpleUniverse su = new SimpleUniverse(vp, viewer); // a SU with one Vp and 3 Viewers
		Locale lcl = su.getLocale();                        // point 2nd/3rd Viewer to c3D[1,2]
		lcl.addBranchGraph(createViewer(canvas3D[1], "Button", CommonsWL.Orange, -20, 1, 20 ) );
		BranchGroup scene = Wonderland.create_Scene();                  // create a one-cube scene
		TransformGroup scene_TG = new TransformGroup();
		scene.addChild(scene_TG);
		scene.addChild(CommonsWL.add_Lights(CommonsWL.White, 1));
		
		scene.compile();
		su.addBranchGraph( scene );
	}
	
	ViewingPlatform createViewer(Canvas3D canvas3D, String name, Color3f clr, double x, double y, double z) {		
		// a Canvas3D can only be attached to a single Viewer
		Viewer viewer = new Viewer( canvas3D );	             // attach a Viewer to its canvas
		ViewingPlatform vp = new ViewingPlatform( 1 );       // 1 VP with 1 TG above
		                                                     // assign PG to the Viewer
		Point3d center = new Point3d(0, 0, 0);               // define where the eye looks at
		Vector3d up = new Vector3d(0, 1, 0);                 // define camera's up direction
		Transform3D viewTM = new Transform3D();
		Point3d eye = new Point3d(x, y, z);                  // define eye's location
		viewTM.lookAt(eye, center, up);
		viewTM.invert();  
		vp.getViewPlatformTransform().setTransform(viewTM);  // set VP with 'viewTG'

		// set TG's capabilities to allow KeyNavigatorBehavior modify the Viewer's position
		vp.getViewPlatformTransform( ).setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		vp.getViewPlatformTransform( ).setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
		KeyNavigatorBehavior key = new KeyNavigatorBehavior( vp.getViewPlatformTransform( ) );
		key.setSchedulingBounds( new BoundingSphere() );          // enable viewer navigation
		key.setEnable( false );		
		vp.addChild( key );                                   // add KeyNavigatorBehavior to VP
		viewer.setViewingPlatform( vp );                      // set VP for the Viewer	
		m_KeyHashtable.put( name, key );                      // label the Viewer	
		Button button = new Button( name ); 
		button.addActionListener( this );                     // button to switch the Viewer ON
		add( button );
		return vp;
	}

	/* a function to enable the KeyNavigatorBehavior associated with the selected AWT button and 
	 * disables all other KeyNavigatorBehaviors for non-active Viewers. */
	public void actionPerformed( ActionEvent event ) {
		KeyNavigatorBehavior key = (KeyNavigatorBehavior)m_KeyHashtable.get(event.getActionCommand());
		Object[] keysArray = m_KeyHashtable.values( ).toArray( );
		for( int n = 0; n < keysArray.length; n++ )	{
			KeyNavigatorBehavior keyAtIndex = (KeyNavigatorBehavior) keysArray[n];
			keyAtIndex.setEnable( keyAtIndex == key );
			if( keyAtIndex == key ) {
				if (n == 1) {
					canvas3D[1].addMouseListener(code4Proj[1]);
				}
			}
		}
	}

	public static void main( String[] args ) { 
		JFrame frame = new JFrame("Wonderland");
		frame.getContentPane().add(new Code4Project()); // create an instance of the class
		frame.setSize(1300, height + 40);                         // set the size of the JFrame
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}	
