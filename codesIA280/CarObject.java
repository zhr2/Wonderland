package codesIA280;
import java.io.FileNotFoundException;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.Node;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.vecmath.Color3f;
public class CarObject {
    protected BranchGroup objBG; // load external object to 'objBG'
    protected TransformGroup objTG; // use 'objTG' to position an object

    /* a function to attach the current object to 'objTG' and return 'objTG' */
    public TransformGroup position_Object() {
        objTG.addChild(objBG); // attach external object to 'objTG'
        return objTG; // return 'objTG' as object's position
    }

    /* a function to set 'objTG' and load external object to 'objBG' */
    protected void load_Object(String obj_name, Color3f color) {
        objTG = new TransformGroup(); // initialize 'objTG'
        objBG = load_Shape(obj_name); // load external object to 'objBG'

        // Set the appearance of the object
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setAmbientColor(color);
        mat.setDiffuseColor(color);
        mat.setSpecularColor(color);
        app.setMaterial(mat);

        // Apply the appearance to the Shape3D object
        Shape3D shape = getShape3D(objBG);
        shape.setAppearance(app);
    }

    // A helper method to extract the first Shape3D object from a BranchGroup
    private Shape3D getShape3D(BranchGroup bg) {
        for (int i = 0; i < bg.numChildren(); i++) {
            Node child = bg.getChild(i);
            if (child instanceof BranchGroup) {
                return getShape3D((BranchGroup) child);
            } else if (child instanceof Shape3D) {
                return (Shape3D) child;
            }
        }
        return null;
    }

    /* a function to load and return object shape from the file named 'obj_name' */
    private BranchGroup load_Shape(String obj_name) {
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE, (float) (60 * Math.PI / 180.0));
        Scene s = null;
        try { // load object's definition file to 's'
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
        return s.getSceneGroup();
    }
}

class Chorse extends CarObject {
    public Chorse() {
        Color3f baseColor = new Color3f(1.0f, 1.0f, 1.0f); // Reddish color for the car base
        load_Object("horses9", baseColor);
    }
}



class Ctent extends CarObject {
    public Ctent() {
        Color3f baseColor = new Color3f(1.0f, 0.5f, 0.0f); // Reddish color for the car base
        load_Object("cartent02", baseColor);
    }
}

class Cfloor extends CarObject {
    public Cfloor() {
        Color3f floorColor = new Color3f(1.0f, 0.5f, 0.0f); // Dark green color for the floor
        load_Object("floor", floorColor);
    }
    }


