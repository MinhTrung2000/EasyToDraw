package control.myawt;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class SKGenericRectangle implements SKRectangleInterface {

    private Rectangle2D impl;

    public SKGenericRectangle() {
        impl = new Rectangle2D.Double();
    }

    public SKGenericRectangle(Rectangle2D bounds2d) {
        impl = bounds2d;
    }

    @Override
    public double getX() {
        return impl.getX();
    }

    @Override
    public double getY() {
        return impl.getY();
    }

    @Override
    public double getWidth() {
        return impl.getWidth();
    }

    @Override
    public double getHeight() {
        return impl.getHeight();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
    }

    @Override
    public void setBounds(SKRectangleInterface rect) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLocation(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(SKRectangleInterface rect) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(SKPoint2D point) {
        return impl.contains(point.getCoordX(), point.getCoordY());
    }

    @Override
    public boolean contains(double x, double y) {
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
    public double getMinX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMaxX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMinY() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMaxY() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setRect(double x, double y, double width, double height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFrame(double x, double y, double width, double height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SKRectangle createIntersection(SKRectangle rect) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean intersectsLine(double xc, double yc, double xe, double ye) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SKRectangleInterface union(SKRectangleInterface bounds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
