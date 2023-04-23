package codesIA280;
import java.io.FileNotFoundException;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.ColoringAttributes;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.TextureAttributes;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Vector3d;
import java.io.FileNotFoundException;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.ColoringAttributes;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Vector3d;
public class DtObject {
	protected BranchGroup objBG;                           // load external object to 'objBG'
	protected TransformGroup objTG;                        // use 'objTG' to position an object
	  protected Shape3D getShape() {
	        return (Shape3D) objBG.getChild(0);
	    }
	/* a function to attach the current object to 'objTG' and return 'objTG' */
	public TransformGroup position_Object() {
		objTG.addChild(objBG);                             // attach external object to 'objTG'
		return objTG;                                      // return 'objTG' as object's position
	}

	/* a function to set 'objTG' and load external object to 'objBG' */
	protected void load_Object(String obj_name, Color3f color) {
	    objTG = new TransformGroup();                      
	    objBG = load_Shape(obj_name, color);
	    
	}



	private BranchGroup load_Shape(String obj_name, Color3f color) {
	    ObjectFile f = new ObjectFile(ObjectFile.RESIZE, (float) (60 * Math.PI / 180.0));
	    Scene s = null;
	    try {                                       
	        s = f.load("images/" + obj_name + ".obj");
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
	    
	    // Create appearance
	    Appearance appearance = new Appearance();

	    // Apply color only if it is not null
	    if (color != null) {
	        ColoringAttributes coloringAttributes = new ColoringAttributes(color, ColoringAttributes.FASTEST);
	        appearance.setColoringAttributes(coloringAttributes);
	    }

	    // Create shape and set appearance
	    Shape3D shape = (Shape3D) s.getSceneGroup().getChild(0);
	    shape.setAppearance(appearance);

	    return s.getSceneGroup();
	}

public void setTranslation(Vector3d translation) {
    Transform3D transform3D = new Transform3D();
    objTG.getTransform(transform3D);
    transform3D.setTranslation(translation);
    objTG.setTransform(transform3D);
}
}

class Tower extends DtObject {
    public Tower() {
        load_Object("TowerTest",null); 
        
        // Load texture image
        TextureLoader textureLoader = new TextureLoader("/Users/ikramali/eclipse-workspace/COMP2800/images/tower.jpg", null);
        Texture texture = textureLoader.getTexture();
        

        TextureAttributes textureAttributes = new TextureAttributes();
        textureAttributes.setTextureMode(TextureAttributes.MODULATE);
      
        Appearance appearance = new Appearance();
        appearance.setTexture(texture);
        appearance.setTextureAttributes(textureAttributes);
  
        Shape3D shape = getShape();
        shape.setAppearance(appearance);

        setTranslation(new Vector3d(0.0, 0.0, 0.0));
    }
}




class Drop extends DtObject {
	public Drop() {                               

	    load_Object("chair", null); 


	    TextureLoader textureLoader = new TextureLoader("/Users/ikramali/eclipse-workspace/COMP2800/images/chairtexture.jpeg", null);
	    Texture texture = textureLoader.getTexture();

	    TextureAttributes textureAttributes = new TextureAttributes();
	    textureAttributes.setTextureMode(TextureAttributes.MODULATE);


	    Appearance appearance = new Appearance();
	    appearance.setTexture(texture);
	    appearance.setTextureAttributes(textureAttributes);

	  
	    Shape3D shape = getShape();
	    shape.setAppearance(appearance);

	    setTranslation(new Vector3d(0.0, 0.0, 0.0));
	}
}


class Box extends DtObject {
    public Box() {
        load_Object("box", new Color3f(1.0f, 1.0f, 1.0f)); 
        setTranslation(new Vector3d(0.0, 0.0, 0.0));
    }
    
    public void setScale(double scale) {
        Transform3D transform3D = new Transform3D();
        transform3D.setScale(scale);
        objTG.setTransform(transform3D);
    }


    
}



