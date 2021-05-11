package control.myawt;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.geom.Path2D;

public class SKBasicStroke implements SKBasicStrokeInterface {

    private BasicStroke impl;

    public SKBasicStroke(BasicStroke bs) {
        this.impl = bs;
    }

    public SKBasicStroke(float width, int cap, int join) {
        this.impl = new BasicStroke(width, cap, join);
    }

    public SKBasicStroke(float width) {
        this.impl = new BasicStroke(width);
    }

    @Override
    public SKShapeInterface createStrokedShape(SKShapeInterface shape, int capacity) {
        Shape shapeD = SKGenericShape.getAwtShape(shape);

        if (shapeD instanceof Path2D) {
            Path2D p2d = (Path2D) shapeD;
            if (p2d.getCurrentPoint() != null
                    && Double.isNaN(p2d.getCurrentPoint().getX())) {
                // Log.debug("fix kicks in");
                return new SKGenericShape(shapeD);
            }
        }
        return new SKGenericShape(impl.createStrokedShape(shapeD));
    }

    @Override
    public int getEndCap() {
        return impl.getEndCap();
    }

    @Override
    public double getMiterLimit() {
        return impl.getMiterLimit();
    }

    @Override
    public int getLineJoin() {
        return impl.getLineJoin();
    }

    @Override
    public double getLineWidth() {
        return impl.getLineWidth();
    }

    @Override
    public float[] getDashArray() {
        return impl.getDashArray();
    }

}
