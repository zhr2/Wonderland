package codesIA280;

import java.io.FileNotFoundException;

import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.*;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.vecmath.*;

public abstract class WLTrain {
	private Alpha rotationAlpha;                           // NOTE: keep for future use
	protected BranchGroup objBG;                           // load external object to 'objBG'
	protected TransformGroup objTG;                        // use 'objTG' to position an object
	protected TransformGroup objRG;                        // use 'objRG' to rotate an object
	protected double scale;                                // use 'scale' to define scaling
	protected Vector3f post;                               // use 'post' to specify location
	protected Shape3D obj_shape;

	public abstract TransformGroup position_Object();      // need to be defined in derived classes
	public abstract void add_Child(TransformGroup nextTG);
	
	public Alpha get_Alpha() { return rotationAlpha; };    // NOTE: keep for future use 

	/* a function to load and return object shape from the file named 'obj_name' */
	private Scene loadShape(String obj_name) {
		ObjectFile f = new ObjectFile(ObjectFile.RESIZE, (float) (60 * Math.PI / 180.0));
		Scene s = null;
		try {                                              // load object's definition file to 's'
		s = f.load("train/" + obj_name + ".obj");
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
		return s;                                          // return the object shape in 's'
	}
	
	/* function to set 'objTG' and attach object after loading the model from external file */
	protected void transform_Object(String obj_name) {
		Transform3D scaler = new Transform3D();
		scaler.setScale(scale);                            // set scale for the 4x4 matrix
		scaler.setTranslation(post);                       // set translations for the 4x4 matrix
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
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/train/TrainTexture.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance3() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/train/stylish-panoramic-background-silver-steel-metal-texture-vector.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
}

class Trainrail extends WLTrain {
	public Trainrail() {
		scale = 5.0d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0f, -0.9f, 0f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("TrainTrack1");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Brown); // set "FanStand" to a different color than the common
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

class Trainrail2 extends WLTrain {
	public Trainrail2() {
		scale = 1.0d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0f, 0f, 0f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("TrainTrack2");                     // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(CommonsWL.Grey); // set "FanStand" to a different color than the common
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

class Train extends WLTrain {
	public Train() {
		scale = 5.2d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(0.0f, 0.3f, -0.5f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("Train2");                     // set transformation to 'objTG' and load object file
		obj_Appearance2();                                  // set appearance after converting object node to Shape3D
	}

	public TransformGroup position_Object() {
		objTG.addChild(objBG);                             // attach "FanSwitch" to 'objTG'
		return objTG;                                      // use 'objTG' to attach "FanSwitch" to the previous TG
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}