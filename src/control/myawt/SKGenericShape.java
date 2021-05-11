package control.myawt;

import java.awt.Shape;

public class SKGenericShape implements SKShapeInterface {

    private Shape impl;

    public SKGenericShape() {
    }
    
    public static Shape getAwtShape(SKShapeInterface s) {
        if (s instanceof GeneralPathClipped) {
            return SKGeneralPath.getAwtGeneralPath(((GeneralPathClipped) s).getGeneralPath());
        }
        
        return null;
    }

    public SKGenericShape(Shape impl) {
        this.impl = impl;
    }
    
    @Override
    public SKRectangleInterface getBounds() {
        return new SKRectangle(impl.getBounds());
    }

    @Override
    public boolean contains(int x, int y) {
        return impl.contains(x, y);
    }

    @Override
    public boolean containts(double x, double y) {
        return impl.contains(x, y);
    }

    @Override
    public boolean contains(SKRectangleInterface rect) {
        return impl.contains(SKRectangle.getAwtRectangular(rect));
    }

    public boolean intersects(int x, int y, int width, int height) {
        return impl.intersects(x, y, width, height);
    }

    @Override
    public boolean intersects(double x, double y, double width, double height) {
        return impl.intersects(x, y, width, height);
    }

    @Override
    public boolean intersects(SKRectangleInterface rect) {
        return impl.intersects(SKRectangle.getAwtRectangular(rect));
    }

    @Override
    public SKPathIteratorInterface getPathIterator(SKAffineTranformInterface at) {
        return new SKPathIterator(impl.getPathIterator(SKAffineTranform.getAwtAffineTransform(at)));
    }

    @Override
    public Shape getAwtShape() {
        return impl;
    }

    @Override
    public boolean contains(SKPoint2D point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
