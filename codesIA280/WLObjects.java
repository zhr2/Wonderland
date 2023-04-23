package codesIA280;

import java.io.FileNotFoundException;

import org.jogamp.java3d.Alpha;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.ImageComponent2D;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.RotationInterpolator;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.Texture2D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.TransparencyAttributes;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Vector3f;

public abstract class WLObjects {
	protected Alpha rotationAlpha;                           // NOTE: keep for future use
	protected BranchGroup objBG;                           // load external object to 'objBG'
	protected TransformGroup objTG;                        // use 'objTG' to position an object

	protected TransformGroup objRG;                        // use 'objRG' to rotate an object
	protected double scale;                                // use 'scale' to define scaling
	protected Vector3f post;                               // use 'post' to specify location
	protected Shape3D shape;
	protected Shape3D obj_shape;


	public abstract TransformGroup position_Object();      // need to be defined in derived classes
	public abstract void add_Child(TransformGroup nextTG);
	
	public Alpha get_Alpha() { return rotationAlpha; };    // NOTE: keep for future use 

	/* a function to load and return object shape from the file named 'obj_name' */
	private Scene loadShape(String obj_name) {
		ObjectFile f = new ObjectFile(ObjectFile.RESIZE, (float) (60 * Math.PI / 180.0));
		Scene s = null;
		try {            // load object's definition file to 's'
		       s = f.load("other/" + obj_name + ".obj");
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
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/objects/images/WoodFloor.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance3() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/objects/images/tex_besi_2.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance4() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/objects/images/Glass.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.SCREEN_DOOR, 0.5f);
        texture.setImage(0, image);
        app.setTexture(texture);	
		app.setTransparencyAttributes(ta);                 // set transparency for the base
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
}

class boothBase extends WLObjects {
	public boothBase() {
		scale = 1d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0f, 0f, 0f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("boothBase");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Grey); // set "FanStand" to a different color than the common
		obj_Appearance2();
	}

	public TransformGroup position_Object() {
		objTG.addChild(objBG);                             // attach "FanSwitch" to 'objTG'
		return objTG;                                      // use 'objTG' to attach "FanSwitch" to the previous TG
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class boothCenter extends WLObjects {
	public boothCenter() {
		scale = 1d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(-0.1f, 0.1f, -0.5f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("boothCenter");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Red); // set "FanStand" to a different color than the common
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

class boothWindow extends WLObjects {
	public boothWindow() {
		scale = 1d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0f, 0.2f, -0.49f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("boothWindow");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Grey); // set "FanStand" to a different color than the common
		obj_Appearance4();
	}

	public TransformGroup position_Object() {
		objTG.addChild(objBG);                             // attach "FanSwitch" to 'objTG'
		return objTG;                                      // use 'objTG' to attach "FanSwitch" to the previous TG
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class Trash extends WLObjects {
	public Trash() {
		scale = 0.5d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(-2f, -0.2f, -0.5f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Trash");                     // set transformation to 'objTG' and load object file
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

class StreetLamp extends WLObjects {
	public StreetLamp() {
		scale = 1d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(2f, 0.2f, -0.49f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("StreetLamp");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Black); // set "FanStand" to a different color than the common
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
