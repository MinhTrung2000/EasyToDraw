package control.myawt;

import java.awt.Shape;

public interface SKGeneralPathInterface extends SKShapeInterface {

    void moveTo(double x, double y);

    void reset();

    void lineTo(double x, double y);

    void append(SKShapeInterface shape, boolean connect);

    void closePath();

    SKShapeInterface createTransformedShape(SKAffineTranformInterface tranform);

    SKPoint2D getCurrentPoint();

//    boolean contains(SKRectangleInterface rect);
    boolean contains(double x, double y, double width, double height);

    boolean contains(SKPoint2D point);
    
    boolean contains(SKRectangleInterface rect);

}
