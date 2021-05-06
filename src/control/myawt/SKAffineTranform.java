package control.myawt;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * This class is a wrapper of awt affine transform library.
 *
 * @author DELL
 */
public class SKAffineTranform implements SKAffineTranformInterface {

    private AffineTransform impl;

    public SKAffineTranform() {
        impl = new AffineTransform();
    }

    public SKAffineTranform(AffineTransform at) {
        impl = at;
    }

    AffineTransform getImpl() {
        return impl;
    }

    public void setTransform(SKAffineTranformInterface at) {
        impl.setTransform(((SKAffineTranform) at).getImpl());
    }

    public void setTransform(double m00, double m10, double m01, double m11,
            double m02, double m12) {
        impl.setTransform(m00, m10, m01, m11, m02, m12);
    }

    public void concatenate(SKAffineTranformInterface at) {
        impl.concatenate(((SKAffineTranform) at).getImpl());
    }

    public double getScaleX() {
        return impl.getScaleX();
    }

    public double getScaleY() {
        return impl.getScaleY();
    }

    public double getShearX() {
        return impl.getShearX();
    }

    public double getShearY() {
        return impl.getShearY();
    }

    public double getTranslateX() {
        return impl.getTranslateX();
    }

    public double getTranslateY() {
        return impl.getTranslateY();
    }

    public static AffineTransform getAwtAffineTransform(SKAffineTranformInterface at) {
        if (!(at instanceof SKAffineTranform)) {
            return null;
        }
        return ((SKAffineTranform) at).getImpl();
    }

    @Override
    public SKShapeInterface createTranformedShape(SKShapeInterface shape) {
        Shape ret = null;
        ret = impl.createTransformedShape(SKGenericShape.getAwtShape(shape));
        return new SKGenericShape(ret);
    }

    @Override
    public SKPoint2D transform(SKPoint2D point1, SKPoint2D point2) {
        Point2D point1_copy = new Point2D.Double(point1.getCoordX(), point1.getCoordY());
        
        Point2D point2_copy = null;
        
        if (point2 != null) {
            point2_copy = new Point2D.Double(point2.getCoordX(), point2.getCoordY());
        }
        
        point2_copy = impl.transform(point1_copy, point2_copy);
        
        if (point2 != null) {
            point2.setCoordX((int) point2_copy.getX());
            point2.setCoordY((int) point2_copy.getY());
            return point2;
        }
        
        return new SKPoint2D(point2_copy.getX(), point2_copy.getY());
    }

    @Override
    public void tranform(double[] labelCoords1, int i, double[] labelCoords2, int j, int k) {
        impl.transform(labelCoords1, i, labelCoords2, j, k);
    }

    @Override
    public SKAffineTranformInterface createInverse() throws Exception {
        return new SKAffineTranform(impl.createInverse());
    }

    @Override
    public void scale(double xScale, double d) {
        impl.scale(xScale, d);
    }

    @Override
    public void setToScale(double sx, double sy) {
        impl.setToScale(sx, sy);
    }

    @Override
    public void translate(double x, double y) {
        impl.translate(x, y);
    }

    @Override
    public void setToTranslation(double tx, double ty) {
        impl.setToTranslation(tx, ty);
    }

    @Override
    public void rotate(double angle) {
        impl.rotate(angle);
    }

    @Override
    public void setToRotation(double angle) {
        impl.setToRotation(angle);
    }

    @Override
    public void setToRotaion(double angle, double x, double y) {
        impl.setToRotation(angle, x, y);
    }

    @Override
    public boolean isIdentity() {
        return impl.isIdentity();
    }

    @Override
    public void getMatrix(double[] m) {
        impl.getMatrix(m);
    }

}
