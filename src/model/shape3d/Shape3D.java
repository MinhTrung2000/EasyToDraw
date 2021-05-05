package model.shape3d;

import control.myawt.SKPoint2D;
import java.awt.Color;
import control.myawt.SKPoint3D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public abstract class Shape3D extends Shape2D {

    protected SKPoint3D startPoint3D = new SKPoint3D();
    protected SKPoint3D endPoint3D = new SKPoint3D();
    protected SKPoint3D centerPoint3D = new SKPoint3D();
    
    public Shape3D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {

    }
}
