package control.myawt;

import control.util.DoubleUtil;

public class MyPoint extends SKPoint2D {

    private PointDrawingType pointType = PointDrawingType.LINE_TO;

    public MyPoint() {
    }
    
    public MyPoint(double x, double y) {
        super(x, y);
    }
    
    public MyPoint(double x, double y, PointDrawingType pointType) {
        super(x, y);
        this.pointType = pointType;
    }
    
    public PointDrawingType getPointDrawingType() {
        return pointType;
    }
    
    public boolean isEqual(double px, double py) {
        return DoubleUtil.isEqual(coordX, px, Kernel.MIN_PRECISION)
                && DoubleUtil.isEqual(coordY, py, Kernel.MIN_PRECISION);
    }

    @Override
    public String toString() {
        return "(" + coordX + ", " + coordY + ")";
    }
    
    public boolean isEqual(MyPoint point) {
        return isEqual(point.coordX, point.coordY);
    }
    
    public boolean isFinite() {
        return DoubleUtil.isFinite(coordX) && DoubleUtil.isFinite(coordY);
    }
    
    public MyPoint copy() {
        return new MyPoint(coordX, coordY, pointType);
    }
    
    public MyPoint copy(PointDrawingType newPointType) {
        return new MyPoint(coordX, coordY, newPointType);
    }
    
}
