package control.myawt;

import java.awt.Shape;

public interface SKShapeInterface {

    SKRectangleInterface getBounds();

    boolean contains(int x, int y);

    boolean containts(double x, double y);

    boolean contains(SKRectangleInterface rect);
    
    boolean contains(SKPoint2D point);
    
//    boolean intersects(int x, int y, int width, int height);

    boolean intersects(double x, double y, double width, double height);

    boolean intersects(SKRectangleInterface rect);

    SKPathIteratorInterface getPathIterator(SKAffineTranformInterface at);

    Shape getAwtShape();
}
