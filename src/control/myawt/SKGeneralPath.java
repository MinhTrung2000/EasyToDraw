package control.myawt;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

/**
 * Awt general path wrapper.
 *
 * @author DELL
 */
public class SKGeneralPath implements SKGeneralPathInterface {

    private final GeneralPath impl;

    public SKGeneralPath() {
        // default winding rule changed for ggb50 (for Polygons) #3983
        this.impl = new GeneralPath(Path2D.WIND_EVEN_ODD);
    }

    public SKGeneralPath(GeneralPath impl) {
        this.impl = impl;
    }

    public static GeneralPath getAwtGeneralPath(SKGeneralPathInterface gp) {
        if (!(gp instanceof SKGeneralPath)) {
            return null;
        }
        return ((SKGeneralPath) gp).impl;
    }

    @Override
    public synchronized void moveTo(double x, double y) {
        impl.moveTo(x, y);
    }

    @Override
    public synchronized void reset() {
        impl.reset();
    }

    @Override
    public synchronized void lineTo(double x, double y) {
        impl.lineTo(x, y);
    }

    @Override
    public synchronized void closePath() {
        impl.closePath();
    }

    @Override
    public synchronized void append(SKShapeInterface shape, boolean connect) {
        impl.append(shape.getAwtShape(), connect);
    }

    @Override
    public SKShapeInterface createTransformedShape(SKAffineTranformInterface tranform) {
        return new SKGenericShape(impl.createTransformedShape(
                ((SKAffineTranform) tranform).getImpl()
        ));
    }

    @Override
    public SKPoint2D getCurrentPoint() {
        if (impl.getCurrentPoint() == null) {
            return null;
        }
        
        return new SKPoint2D(impl.getCurrentPoint().getX(), impl.getCurrentPoint().getY());
    }

    @Override
    public boolean contains(double x, double y, double width, double height) {
        return impl.contains(x, y, width, height);
    }

    @Override
    public boolean contains(SKPoint2D point) {
        if (point == null) {
            return false;
        }
        
        return impl.contains(point.getCoordX(), point.getCoordY());
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

}
