package codesIA280;

import java.io.FileNotFoundException;
import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.*;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.*;

public abstract class WLShip {
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
			s = f.load("objects/" + obj_name + ".obj");
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
		Material mtl = new Material();                     // define material's attributes
		mtl.setShininess(shine);
		mtl.setAmbientColor(mtl_clr[0]);                   // use them to define different materials
		mtl.setDiffuseColor(mtl_clr[1]);
		mtl.setSpecularColor(mtl_clr[2]);
		mtl.setEmissiveColor(mtl_clr[3]);                  // use it to enlighten a button
		mtl.setLightingEnable(true);
		app.setMaterial(mtl);                              // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	
	protected void obj_Appearance1() {	//making it WHITE
		app = CommonsWL.obj_Appearance(CommonsWL.White);	                             // set appearance's material
        TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparency(0f);
        ta.setTransparencyMode(TransparencyAttributes.SCREEN_DOOR);
        app.setTransparencyAttributes(ta);
        String filename = "/Users/ikramali/eclipse-workspace/Comp2800/images/woodentexture.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);  
	}	 
}

class ShipAxel extends WLShip {//name is ambiguos but this is the pat ofhte ship that will rotate
	public ShipAxel() {
		scale = 5.9d;                                        // use to scale up/down original size
		post = new Vector3f(-20f, 4f, -2f);                   // use to move object for positioning
		transform_Object("lololol");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.58f, 0.69f, 0.11f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance1();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class ShipObject extends WLShip {
	public ShipObject() {
		scale = 6.5d;                                        // use to scale up/down original size
		post = new Vector3f(-20f, 5.5f, -2f);                   // use to move object for positioning
		transform_Object("botship");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.58f, 0.69f, 0.11f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance1();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}


