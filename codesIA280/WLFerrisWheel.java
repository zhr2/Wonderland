package codesIA280;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;

import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.*;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.vecmath.*;
public class WLFerrisWheel {
    protected BranchGroup objBG;
    protected TransformGroup objTG;

    public TransformGroup position_Object() {
        objTG.addChild(objBG);
        return objTG;
    }

    protected void load_Object(String obj_name, Color color) {
        objTG = new TransformGroup();
        objBG = load_Shape(obj_name);
        set_Object_Color(color);
    }

    private void set_Object_Color(Color color) {
        Appearance appearance = new Appearance();
        Material material = new Material();
        material.setAmbientColor(new Color3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f));
        material.setDiffuseColor(new Color3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f));
        material.setSpecularColor(new Color3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f));
        appearance.setMaterial(material);
        Shape3D shape = get_Shape3D(objBG);
        shape.setAppearance(appearance);
    }

    private Shape3D get_Shape3D(BranchGroup bg) {
        for (int i = 0; i < bg.numChildren(); i++) {
            Node child = bg.getChild(i);
            if (child instanceof BranchGroup) {
                return get_Shape3D((BranchGroup) child);
            } else if (child instanceof Shape3D) {
                return (Shape3D) child;
            }
        }
        return null;
    }

    private BranchGroup load_Shape(String obj_name) {
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE, (float) (60 * Math.PI / 180.0));
        Scene s = null;
        try {
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
        return s.getSceneGroup();
    }
}

class Wheel extends WLFerrisWheel {
    public Wheel() {
        Color wheelColor = new Color(255, 102, 0); 
        load_Object("FWwheel", wheelColor);
    }
    
    
}

class Stand extends WLFerrisWheel {
    public Stand() {
        Color standColor = new Color(102, 102, 102); 
        load_Object("FWstand", standColor);
    }
}

class BaseFW extends WLFerrisWheel {
    public BaseFW() {
    	Color Basecolor = new Color(255, 165, 0); 
        load_Object("FWbase", Basecolor);
    }
}
