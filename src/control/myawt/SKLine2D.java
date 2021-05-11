package control.myawt;

import java.awt.Shape;
import java.awt.geom.Line2D;

public class SKLine2D implements SKLine2DInterface {

    private Line2D impl;

    public SKLine2D() {
        this.impl = new Line2D.Double();
    }

    public SKLine2D(Line2D impl) {
        this.impl = impl;
    }

    @Override
    public void setLine(double x1, double y1, double x2, double y2) {
        impl.setLine(x1, y1, x2, y2);
    }

    @Override
    public SKPoint2D getP1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SKPoint2D getP2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getX1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getY1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getX2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getY2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public boolean contains(SKPoint2D point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean intersects(double x, double y, double width, double height) {
        return impl.intersects(x, y, width, height);
    }

    @Override
    public boolean intersects(SKRectangleInterface rect) {
        return true;
    }

    @Override
    public SKPathIteratorInterface getPathIterator(SKAffineTranformInterface at) {
        return new SKPathIterator(impl.getPathIterator(SKAffineTranform.getAwtAffineTransform(at)));
    }

    @Override
    public Shape getAwtShape() {
        return impl;
    }

}
