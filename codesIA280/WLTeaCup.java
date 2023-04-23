package codesIA280;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.*;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.picking.PickResult;
import org.jogamp.java3d.utils.picking.PickTool;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.vecmath.*;


public abstract class WLTeaCup {
	protected Alpha rotationAlpha;                           // NOTE: keep for future use
	protected BranchGroup objBG;                           // load external object to 'objBG'
	protected TransformGroup objTG;                        // use 'objTG' to position an object

	protected TransformGroup objRG;                        // use 'objRG' to rotate an object
	protected double scale;                                // use 'scale' to define scaling
	protected Vector3f post;                               // use 'post' to specify location
	protected Shape3D shape;
	protected Shape3D obj_shape;
	protected static Alpha RotationAlpha1;
	protected static Alpha RotationAlpha2;
	protected static Alpha RotationAlpha3;
	protected static Alpha RotationAlpha4;
	protected static Alpha RotationAlpha5;
	protected static Alpha RotationAlpha6;
	protected static Alpha RotationAlpha7;


	public abstract TransformGroup position_Object();      // need to be defined in derived classes
	public abstract void add_Child(TransformGroup nextTG);
	
	public Alpha get_Alpha() { return rotationAlpha; };    // NOTE: keep for future use 

	/* a function to load and return object shape from the file named 'obj_name' */
	private Scene loadShape(String obj_name) {
		ObjectFile f = new ObjectFile(ObjectFile.RESIZE, (float) (60 * Math.PI / 180.0));
		Scene s = null;
		try {                                              // load object's definition file to 's'
			s = f.load("TeaCup/" + obj_name + ".obj");
		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		} catch (ParsingErrorException e) {
			System.err.println(e);
			System.exit(1);
		} catch (IncorrectFormatException e) {
			System.err.println(e);
			System.exit(1);
		}
		obj_shape = (Shape3D) s.getSceneGroup().getChild(0);
        obj_shape.setUserData(0);

		return s;                                          // return the object shape in 's'
	}
	
	/* function to set 'objTG' and attach object after loading the model from external file */
	protected void transform_Object(String obj_name) {
		Transform3D scaler = new Transform3D();
		scaler.setScale(scale);                            // set scale for the 4x4 matrix
		scaler.setTranslation(post);                       // set translations for the 4x4 matrix
		objTG = new TransformGroup(scaler);                // set the translation BG with the 4x4 matrix
		objTG = new TransformGroup(scaler);                // set the translation BG with the 4x4 matrix
		objTG = new TransformGroup(scaler);                // set the translation BG with the 4x4 matrix
		objTG = new TransformGroup(scaler);                // set the translation BG with the 4x4 matrix
		objTG = new TransformGroup(scaler);                // set the translation BG with the 4x4 matrix
		objTG = new TransformGroup(scaler);                // set the translation BG with the 4x4 matrix
		objTG = new TransformGroup(scaler);                // set the translation BG with the 4x4 matrix

		objBG = loadShape(obj_name).getSceneGroup();       // load external object to 'objBG'
		obj_shape = (Shape3D) objBG.getChild(0);           // get and cast the object to 'obj_shape'	
		obj_shape.setName(obj_name);                       // use the name to identify the object 
	}
	
	protected Appearance app = new Appearance();
	private int shine = 32;                                // specify common values for object's appearance
	protected Color3f[] mtl_clr = {new Color3f(1.000000f, 1.000000f, 1.000000f),
			new Color3f(0.772500f, 0.654900f, 0.000000f),	
			new Color3f(0.175000f, 0.175000f, 0.175000f),
			new Color3f(0.000000f, 0.000000f, 0.000000f)};
	
    /* a function to define object's material and use it to set object's appearance */
	
	protected void obj_Appearance() {		
		Material material = new Material();                     // define material's attributes
		material.setShininess(shine);
		material.setAmbientColor(mtl_clr[0]);                   // use them to define different materials
		material.setDiffuseColor(mtl_clr[1]);
		material.setSpecularColor(mtl_clr[2]);
		material.setEmissiveColor(mtl_clr[3]);                  // use it to enlighten a button
		material.setLightingEnable(true);

		app.setMaterial(material);                              // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance2() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/Base.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance3() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/Top.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance4() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/pattern6.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance5() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/pattern7.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance6() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/pattern4.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance7() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/pattern1.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance8() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/pattern5.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance9() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/pattern2.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance10() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/pattern3.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
}


class Base extends WLTeaCup {
	public Base() {
		scale = 2.6d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0f, -1f, 0f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("TeacupBase");                     // set transformation to 'objTG' and load object file
		obj_Appearance2();                                  // set appearance after converting object node to Shape3D
		
	}

	public TransformGroup position_Object() {
		objTG.addChild(objBG);                             // attach "FanSwitch" to 'objTG'
		return objTG;   
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}


class TeaCup1 extends WLTeaCup {
	public TeaCup1() {
		scale = 0.9d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(1.1f, -0.6f, 1.5f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Teacup1");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Yellow); // set "FanStand" to a different color than the common
		obj_Appearance7();
	}

	public TransformGroup position_Object() {		
		Transform3D spin = new Transform3D();
		spin.rotX(Math.PI);
		TransformGroup objRG2 = new TransformGroup(spin);
		objRG2.addChild(objBG);
		RotationInterpolator rot = CommonsZS.rotate_Behavior(1500, objRG2,spin);
		objRG2.addChild(rot);
		RotationAlpha1 = rot.getAlpha();
		objTG.addChild(objRG2);

		return objTG;    
		
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class TeaCup2 extends WLTeaCup {
	public TeaCup2() {
		scale = 0.9d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(-0.6f, -0.6f, 1.7f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Teacup2");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Orange); // set "FanStand" to a different color than the common
		obj_Appearance9();
	}

	public TransformGroup position_Object() {
		Transform3D spin = new Transform3D();
		spin.rotX(Math.PI);
		TransformGroup objRG2 = new TransformGroup(spin);
		objRG2.addChild(objBG);
		RotationInterpolator rot = CommonsZS.rotate_Behavior(1500, objRG2,spin);
		objRG2.addChild(rot);
		RotationAlpha2 = rot.getAlpha();
		objTG.addChild(objRG2);
		
		return objTG;    
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class TeaCup3 extends WLTeaCup {
	public TeaCup3() {
		scale = 0.9d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(-1.8f, -0.6f, 0.3f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Teacup3");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Cyan); // set "FanStand" to a different color than the common
		obj_Appearance5();
	}

	public TransformGroup position_Object() {
		Transform3D spin = new Transform3D();
		spin.rotX(Math.PI);
		TransformGroup objRG2 = new TransformGroup(spin);
		objRG2.addChild(objBG);
		RotationInterpolator rot = CommonsZS.rotate_Behavior(1500, objRG2,spin);
		objRG2.addChild(rot);
		RotationAlpha3 = rot.getAlpha();
		objTG.addChild(objRG2);

		return objTG;    
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class TeaCup4 extends WLTeaCup {
	public TeaCup4() {
		scale = 0.9d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(-1f, -0.6f, -1.4f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Teacup4");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Magenta); // set "FanStand" to a different color than the common
		obj_Appearance6();
	}

	public TransformGroup position_Object() {
		Transform3D spin = new Transform3D();
		spin.rotX(Math.PI);
		TransformGroup objRG2 = new TransformGroup(spin);
		objRG2.addChild(objBG);
		RotationInterpolator rot = CommonsZS.rotate_Behavior(1500, objRG2,spin);
		objRG2.addChild(rot);
		RotationAlpha4 = rot.getAlpha();
		objTG.addChild(objRG2);

		return objTG;    
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class TeaCup5 extends WLTeaCup {
	public TeaCup5() {
		scale = 0.9d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0.7f, -0.6f, -1.6f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Teacup5");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Red); // set "FanStand" to a different color than the common
		obj_Appearance8();
	}

	public TransformGroup position_Object() {
		obj_shape.setUserData(0);
		
		Transform3D spin = new Transform3D();
		spin.rotX(Math.PI);
		TransformGroup objRG2 = new TransformGroup(spin);
		objRG2.addChild(objBG);
		RotationInterpolator rot = CommonsZS.rotate_Behavior(1500, objRG2,spin);
		objRG2.addChild(rot);
		RotationAlpha5 = rot.getAlpha();
		objTG.addChild(objRG2);

		return objTG;    
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class TeaCup6 extends WLTeaCup {
	public TeaCup6() {
		scale = 0.9d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(1.7f, -0.6f, -0.1f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Teacup6");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Green); // set "FanStand" to a different color than the common
		obj_Appearance4();
	}

	public TransformGroup position_Object() {
		Transform3D spin = new Transform3D();
		spin.rotX(Math.PI);
		TransformGroup objRG2 = new TransformGroup(spin);
		objRG2.addChild(objBG);
		RotationInterpolator rot = CommonsZS.rotate_Behavior(1500, objRG2,spin);
		objRG2.addChild(rot);
		RotationAlpha6 = rot.getAlpha();
		objTG.addChild(objRG2);

		return objTG;    
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class TeaCup7 extends WLTeaCup {
	public TeaCup7() {
		scale = 0.9d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0f, -0.6f, 0f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Teacup7");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Blue); // set "FanStand" to a different color than the common
		obj_Appearance10();
	}

	public TransformGroup position_Object() {
		Transform3D spin = new Transform3D();
		spin.rotX(Math.PI);
		TransformGroup objRG2 = new TransformGroup(spin);
		objRG2.addChild(objBG);
		RotationInterpolator rot = CommonsZS.rotate_Behavior(1500, objRG2,spin);
		objRG2.addChild(rot);
		RotationAlpha7 = rot.getAlpha();
		objTG.addChild(objRG2);

		return objTG;    
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class MetalBase extends WLTeaCup {
	public MetalBase() {
		scale = 2.8d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0f, 1.0f, 0f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("MetalBase2");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Grey); // set "FanStand" to a different color than the common
		obj_Appearance();
	}

	public TransformGroup position_Object() {
		objTG.addChild(objBG);                             // attach "FanSwitch" to 'objTG'
		return objTG;                                      // use 'objTG' to attach "FanSwitch" to the previous TG
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class Top extends WLTeaCup {
	public Top() {
		scale = 1.1d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0f, 0.48f, 0f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Top");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Red); // set "FanStand" to a different color than the common
		obj_Appearance();
		obj_Appearance3();
	}

	public TransformGroup position_Object() {
		objTG.addChild(objBG);                             // attach "FanSwitch" to 'objTG'
		return objTG;                                      // use 'objTG' to attach "FanSwitch" to the previous TG
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}