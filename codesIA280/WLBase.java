package codesIA280;
import java.awt.Font;
import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.*;


/* an abstract super class for create different objects */
public abstract class WLBase {
	protected TransformGroup objTG = new TransformGroup(); // use 'objTG' to position an object
	protected abstract Node create_Object();               // allow derived classes to create different objects
	public TransformGroup position_Object() {	           // retrieve 'objTG' to which 'obj_shape' is attached
		return objTG;   
	}
	protected Appearance app;                              // allow each object to define its own appearance
	public void add_Child(TransformGroup nextTG) {
		objTG.addChild(nextTG);                            // A3: attach the next transformGroup to 'objTG'
	}
}

class grass extends WLBase {
	public grass() {
		Transform3D translator = new Transform3D();
		translator.setTranslation(new Vector3d(0.0, -0.54, 0));
		objTG = new TransformGroup(translator);            // down half of the tower and base's heights
		objTG.addChild(create_Object());                   // attach the object to 'objTG'
	}
	protected Node create_Object() { 
		app = CommonsWL.obj_Appearance(CommonsWL.White);   // set the appearance for the base
		app.setTexture(textured_App("WLgrass"));     // set texture for the base
		TransparencyAttributes ta =                        // value: FASTEST NICEST SCREEN_DOOR BLENDED NONE
				new TransparencyAttributes(TransparencyAttributes.SCREEN_DOOR, 0f);
		app.setTransparencyAttributes(ta);                 // set transparency for the base
		return new Box(8f, 0f, 8f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
	}
	
	private static Texture textured_App(String name) {
		String filename = "images/" + name + ".jpg";// tell the folder of the image
		TextureLoader loader = new TextureLoader(filename, null);
		ImageComponent2D image = loader.getImage();        // load the image
		if (image == null)
			System.out.println("Cannot load file: " + filename);
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL,
				Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);                        // set image for the texture
		return texture;
	}
}