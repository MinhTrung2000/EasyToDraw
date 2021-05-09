package control.myawt;

import java.awt.Rectangle;

public class SKRectangle implements SKRectangleInterface {

    Rectangle impl;

    public SKRectangle() {
        impl = new Rectangle();
    }

    public SKRectangle(SKRectangleInterface rect) {
        impl = ((SKRectangle) rect).impl;
    }

    public SKRectangle(int x, int y, int width, int height) {
        impl = new Rectangle(x, y, width, height);
    }

    public SKRectangle(Rectangle frameBounds) {
        impl = frameBounds;
    }

    @Override
    public double getY() {
        return impl.getY();
    }

    @Override
    public double getX() {
        return impl.getX();
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
        impl.setBounds(x, y, width, height);

    }

    @Override
    public void setBounds(SKRectangleInterface rect) {
        impl.setBounds((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    @Override
    public void setLocation(int x, int y) {
        impl.setLocation(x, y);
    }

    @Override
    public void add(SKRectangleInterface rect) {
        impl.add(((SKRectangle) rect).impl);
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
        return impl.contains(x, y);
    }

    @Override
    public SKRectangleInterface union(SKRectangleInterface bounds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSize(int width, int height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static Rectangle getAwtRectangular(SKRectangleInterface rect) {
        if (!(rect instanceof SKRectangle)) {
            return null;
        }
        return ((SKRectangle) rect).impl;
    }
    
}
