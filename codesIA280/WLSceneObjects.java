package codesIA280;


import java.io.FileNotFoundException;
import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.*;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.*;


public abstract class WLSceneObjects {
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
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	protected void obj_Appearance2() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Orange);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
	protected void obj_Appearance3() {	
		app = CommonsWL.obj_Appearance(CommonsWL.Red);
        TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparency(0.01f);
        ta.setTransparencyMode(TransparencyAttributes.SCREEN_DOOR);
        app.setTransparencyAttributes(ta);
        String filename = "/Users/ikramali/eclipse-workspace/COMP2800/images/woodBC.jpg";
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        app.setTexture(texture);	                             // set appearance's material
		obj_shape.setAppearance(app);                      // set object's appearance
	}
}
//*************************************************BUMPER CARS START***************************************************************** */
class field extends WLSceneObjects {
	public field() {
		scale = 5.7d;                                      // actual scale is 0.3 = 1.0 x 0.3
		post = new Vector3f(-12f, 0.5f, 14f);         // location to connect "FanSwitch" with "FanStand"
		transform_Object("field1");                     // set transformation to 'objTG' and load object file
		obj_Appearance3();                                  // set appearance after converting object node to Shape3D
	}

	public TransformGroup position_Object() {
		objTG.addChild(objBG);                             // attach "FanSwitch" to 'objTG'
		return objTG;                                      // use 'objTG' to attach "FanSwitch" to the previous TG
	}

	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}
class bumperCar1 extends WLSceneObjects{
    public bumperCar1(){
        scale = 1.3d;
        //post = new Vector3f(0.2f, -0.5f, 0.5f);
        post = new Vector3f(-11.82f, 0.5f, 16.0f);
        transform_Object("bumper");
        app = CommonsWL.obj_Appearance(CommonsWL.Blue);                                // set appearance's material
        obj_shape.setAppearance(app); 

    }

    public TransformGroup position_Object(){
        objTG.addChild(objBG);
        return objTG;
    }
    public void add_Child(TransformGroup nextTG){
        objTG.addChild(nextTG);
    }
}

class bumperCar2 extends WLSceneObjects{
    public bumperCar2(){
        scale = 1.3d;
        //post = new Vector3f(-1.2f, -0.5f, -1.9f);
        post = new Vector3f(-13.22f, 0.5f, 13.6f);
        transform_Object("bumper6");
        app = CommonsWL.obj_Appearance(CommonsWL.Black);                                 // set appearance's material
        obj_shape.setAppearance(app);

    }

    public TransformGroup position_Object(){
        objTG.addChild(objBG);
        return objTG;
    }
    public void add_Child(TransformGroup nextTG){
        objTG.addChild(nextTG);
    }
}
class bumperCar3 extends WLSceneObjects{
    public bumperCar3(){
        scale = 1.3d;
        //post = new Vector3f(0.02f,-0.5f, -3.9f);
        post = new Vector3f(-12.0f,0.5f, 9.5f);
        transform_Object("bumper2");
        app = CommonsWL.obj_Appearance(CommonsWL.Yellow);                                 // set appearance's material
        obj_shape.setAppearance(app);

    }

    public TransformGroup position_Object(){
        objTG.addChild(objBG);
        return objTG;
    }
    public void add_Child(TransformGroup nextTG){
        objTG.addChild(nextTG);
    }
}
class bumperCar4 extends WLSceneObjects{
    public bumperCar4(){
        scale = 1.3d;
        //post = new Vector3f(0.5f, -0.5f, -2.0f);
        post = new Vector3f(-11.52f, 0.5f, 13.5f);
        transform_Object("bumper7");
        app = CommonsWL.obj_Appearance(CommonsWL.Cyan);                                 // set appearance's material
        obj_shape.setAppearance(app);

    }

    public TransformGroup position_Object(){
        objTG.addChild(objBG);
        return objTG;
    }
    public void add_Child(TransformGroup nextTG){
        objTG.addChild(nextTG);
    }
}
class bumperCar5 extends WLSceneObjects{
    public bumperCar5(){
        scale = 1.3d;
        //post = new Vector3f(1.3f, -0.5f, -0.5f);
        post = new Vector3f(-9.72f, 0.5f, 14.5f);
        transform_Object("bumper5");
        app = CommonsWL.obj_Appearance(CommonsWL.Magenta);                                 // set appearance's material
        obj_shape.setAppearance(app); 
    } 

    public TransformGroup position_Object(){
        objTG.addChild(objBG);
        return objTG;
    }
    public void add_Child(TransformGroup nextTG){
        objTG.addChild(nextTG);
    }
}
class bumperCar6 extends WLSceneObjects{
    public bumperCar6(){
        scale = 1.3d;
        //post = new Vector3f(1.3f, -0.5f, -0.5f);
        post = new Vector3f(-14.5f, 0.5f, 9.3f);
        transform_Object("bumper8");
        app = CommonsWL.obj_Appearance(CommonsWL.Orange);                                 // set appearance's material
        obj_shape.setAppearance(app); 
    } 

    public TransformGroup position_Object(){
        objTG.addChild(objBG);
        return objTG;
    }
    public void add_Child(TransformGroup nextTG){
        objTG.addChild(nextTG);
    }
}

//*************************************************BUMPER CARS END***************************************************************** */

class Fountain extends WLSceneObjects {
	public Fountain() {
		scale = 2d;                                        // use to scale up/down original size
		post = new Vector3f(0f, 0.54f, 0f);                   // use to move object for positioning
		transform_Object("fountain1");                      // set transformation to 'objTG' and load object file
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
class Gate extends WLSceneObjects {
	public Gate() {
		scale = 15d;                                        // use to scale up/down original size
		post = new Vector3f(0f, 3f, 30f);                   // use to move object for positioning
		transform_Object("gate");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0f, 0.50f, 0.42f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

/*********************************************BASKETBALL****************************************************************/
class BballBase extends WLSceneObjects {
	public BballBase() {
		scale = 2d;                                        // use to scale up/down original size
		post = new Vector3f(2.01f, 1f, -25f);                   // use to move object for positioning
		transform_Object("bballbase");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(1.0f, 0.0f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class BballNet extends WLSceneObjects {
	public BballNet() {
		scale = 2.01d;                                        // use to scale up/down original size
		post = new Vector3f(2f, 1.46f, -25.01f);                   // use to move object for positioning
		transform_Object("bballnet");                      // set transformation to 'objTG' and load object file
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

class Bball extends WLSceneObjects {
	public Bball() {
		scale = 0.15d;                                        // use to scale up/down original size
		post = new Vector3f(2f, 1f, -26.3f);                   // use to move object for positioning
		transform_Object("bball");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(1.0f, 0.5f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class BballBase1 extends WLSceneObjects {
	public BballBase1() {
		scale = 2d;                                        // use to scale up/down original size
		post = new Vector3f(-0.01f, 1f, -25f);                   // use to move object for positioning
		transform_Object("bballbase");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(1.0f, 0.0f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class BballNet1 extends WLSceneObjects {
	public BballNet1() {
		scale = 2.01d;                                        // use to scale up/down original size
		post = new Vector3f(-0f, 1.46f, -25.01f);                   // use to move object for positioning
		transform_Object("bballnet");                      // set transformation to 'objTG' and load object file
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

class Bball1 extends WLSceneObjects {
	public Bball1() {
		scale = 0.15d;                                        // use to scale up/down original size
		post = new Vector3f(-0f, 1f, -26.3f);                   // use to move object for positioning
		transform_Object("bball");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(1.0f, 0.5f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}
/*********************************************TREETOPPPP****************************************************************/

class treeTop extends WLSceneObjects {
	public treeTop() {
		scale = 2d;                                        // use to scale up/down original size
		post = new Vector3f(-4f, 1.5f, -12f);                   // use to move object for positioning
		transform_Object("treetop");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.13f, 0.40f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}class treeBot extends WLSceneObjects {
	public treeBot() {
		scale = 2.5d;                                        // use to scale up/down original size
		post = new Vector3f(-4f, 1.5f, -12f);                   // use to move object for positioning
		transform_Object("treebot");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.10f, 0.20f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}


class treeTop1 extends WLSceneObjects {
	public treeTop1() {
		scale = 2d;                                        // use to scale up/down original size
		post = new Vector3f(14f, 1.5f, -15f);                   // use to move object for positioning
		transform_Object("treetop");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.13f, 0.40f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}class treeBot1 extends WLSceneObjects {
	public treeBot1() {
		scale = 2.5d;                                        // use to scale up/down original size
		post = new Vector3f(14f, 1.5f, -15f);                   // use to move object for positioning
		transform_Object("treebot");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.10f, 0.20f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}
class treeTop2 extends WLSceneObjects {
	public treeTop2() {
		scale = 2d;                                        // use to scale up/down original size
		post = new Vector3f(-8f, 1.5f, -6f);                   // use to move object for positioning
		transform_Object("treetop");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.13f, 0.40f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}class treeBot2 extends WLSceneObjects {
	public treeBot2() {
		scale = 2.5d;                                        // use to scale up/down original size
		post = new Vector3f(-8f, 1.5f, -6f);                   // use to move object for positioning
		transform_Object("treebot");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.10f, 0.20f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class treeTop3 extends WLSceneObjects {
	public treeTop3() {
		scale = 2d;                                        // use to scale up/down original size
		post = new Vector3f(15f, 1.5f, 4f);                   // use to move object for positioning
		transform_Object("treetop");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.13f, 0.40f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}class treeBot3 extends WLSceneObjects {
	public treeBot3() {
		scale = 2.5d;                                        // use to scale up/down original size
		post = new Vector3f(15f, 1.5f, 4f);                   // use to move object for positioning
		transform_Object("treebot");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.10f, 0.20f, 0.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}


/*********************************************HOTAIRBALLOOON****************************************************************/
class HotAir extends WLSceneObjects {
	public HotAir() {
		scale = 4d;                            
		// use to scale up/down original size
		post = new Vector3f(-5f, 12f, -35f);                   // use to move object for positioning
		transform_Object("balloon");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0f, 0.50f, 0.42f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}
class HotAir1 extends WLSceneObjects {
	public HotAir1() {
		scale = 4d;                                        // use to scale up/down original size
		post = new Vector3f(7f, 15f, -35f);                   // use to move object for positioning
		transform_Object("balloon");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.88f, 0.30f, 1.0f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class HotAir2 extends WLSceneObjects {
	public HotAir2() {
		scale = 4d;                                        // use to scale up/down original size
		post = new Vector3f(18f, 14f, -28f);                   // use to move object for positioning
		transform_Object("balloon");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.50f, 1.0f, 0.40f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class HotAir3 extends WLSceneObjects {
	public HotAir3() {
		scale = 4d;                                        // use to scale up/down original size
		post = new Vector3f(-16f, 14f, -30f);                   // use to move object for positioning
		transform_Object("balloon");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(1.0f, 0.50f, 0.84f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class Slide extends WLSceneObjects {
	public Slide() {
		scale = 5d;                                        // use to scale up/down original size
		post = new Vector3f(10f, 0.7f, 11f);                   // use to move object for positioning
		transform_Object("slide");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0.35f, 0.35f, 0.35f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class Cycle extends WLSceneObjects {
	public Cycle() {
		scale = 1d;                                        // use to scale up/down original size
		post = new Vector3f(10f, -0.5f, 14f);                   // use to move object for positioning
		transform_Object("cycle1");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(0f, 0.50f, 0.42f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}
class Cycle1 extends WLSceneObjects {
	public Cycle1() {
		scale = 1d;                                        // use to scale up/down original size
		post = new Vector3f(10f, -0.5f, 12.5f);                   // use to move object for positioning
		transform_Object("cycle2");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(7f, 15f, -35f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}
class Cycle2 extends WLSceneObjects {
	public Cycle2() {
		scale = 1d;                                        // use to scale up/down original size
		post = new Vector3f(8f, -0.5f, 16f);                   // use to move object for positioning
		transform_Object("cycle3");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(1.0f, 0.50f, 0.84f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}



class Pavilion extends WLSceneObjects {
	public Pavilion() {
		scale = 3d;                                        // use to scale up/down original size
		post = new Vector3f(-17f, 1.5f, -20f);                   // use to move object for positioning
		transform_Object("pavilion");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(1f, 1f, 1f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}




class Bench extends WLSceneObjects {
	public Bench() {
		scale = 1.5d;                                        // use to scale up/down original size
		post = new Vector3f(-7f, -.5f, -12f);                   // use to move object for positioning
		transform_Object("bench");                      // set transformation to 'objTG' and load object file
		mtl_clr[1] = new Color3f(1f, 1f, 1f);     // set "FanStand" to a different color than the common  		                                              
		obj_Appearance();                                  // set appearance after converting object node to Shape3D
	}
	public TransformGroup position_Object() {              // attach object BranchGroup "FanStand" to 'objTG'                             // rotate "FanStand" by attaching 'objBG' to 'objRG'
		objTG.addChild(objBG);
		return objTG;                                      
	}
	public void add_Child(TransformGroup nextTG) {
		objRG.addChild(nextTG);                            // attach the next transformGroup to 'objTG'
	}
}

class balloon extends WLSceneObjects{
    public balloon(){
        scale = 0.2d;
        post = new Vector3f(0f, 0f, 0.5f);
        transform_Object("Air_Balloon");
        mtl_clr[1] = new Color3f(1f, 1f, 1f); 
        obj_Appearance();
    } 

    public TransformGroup position_Object(){
        objTG.addChild(objBG);
        return objTG;
    }
    public void add_Child(TransformGroup nextTG){
        objTG.addChild(nextTG);
    }
}




