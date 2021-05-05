package model.shape3d;

import java.awt.Color;
import control.myawt.SKPoint2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public abstract class Shape3D extends Shape2D {

    public Shape3D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    
    
}
