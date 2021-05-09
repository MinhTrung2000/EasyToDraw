package control.myawt;

public abstract class AwtFactory {

    private static AwtFactory prototype = null;

    private static final Object lock = new Object();

    public static AwtFactory getPrototype() {
        return prototype;
    }

    public static void setPrototypeIfNull(AwtFactory prototype) {
        synchronized (lock) {
            if (AwtFactory.prototype == null) {
                AwtFactory.prototype = prototype;
            }
        }
    }

    public abstract SKAffineTranform newAffineTransform();

    public abstract SKRectangleInterface newRectangle(int x, int y, int width, int height);

    public abstract SKRectangleInterface newRectangle(int width, int height);

    public abstract SKRectangleInterface newRectangle(SKRectangleInterface rect);

    public abstract SKRectangleInterface newRectangle();

    public abstract SKDimension newDimension(int width, int height);

    public abstract SKArea newArea(SKShapeInterface shape);

    public abstract SKArea newArea();

    public abstract SKGeneralPath newGeneralPath();

    public abstract SKGeneralPath newGeneralPath(int rule);

    public abstract SKBasicStroke newBasicStroke(double f, int cap, int join);

    public abstract SKBasicStroke newBasicStroke(double width, int endCap,
            int lineJoin, double miterLimit, double[] dash);

    public abstract SKBasicStroke newBasicStroke(double f);

    public abstract SKLine2D newLine2D();

    public abstract SKEllipse2DDouble newEllipse2DDouble();

    public abstract SKEllipse2DDouble newEllipse2DDouble(double x, double y,
            double width, double height);

    public static SKAffineTranform getTranslateInstance(double tx, double ty) {
        SKAffineTranform Tx = prototype.newAffineTransform();
        Tx.setToTranslation(tx, ty);
        return Tx;
    }

    public static SKAffineTranform getScaleInstance(double sx, double sy) {
        SKAffineTranform Tx = prototype.newAffineTransform();
        Tx.setToScale(sx, sy);
        return Tx;
    }

    public static SKAffineTranform getRotationInstance(double angle) {
        SKAffineTranform Tx = prototype.newAffineTransform();
        Tx.setToRotation(angle);
        return Tx;
    }

    public static void fillTriangle(SKGraphic2D graphics, double x1, double y1,
            double x2, double y2, double x3, double y3) {
        SKGeneralPath gp = AwtFactory.getPrototype().newGeneralPath();
        gp.moveTo(x1, y1);
        gp.lineTo(x2, y2);
        gp.lineTo(x3, y3);
        gp.lineTo(x1, y1);
        SKArea shape = AwtFactory.getPrototype().newArea(gp);
        graphics.fill(shape);
    }

}
