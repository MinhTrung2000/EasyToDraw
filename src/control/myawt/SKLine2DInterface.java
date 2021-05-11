package control.myawt;

public interface SKLine2DInterface extends SKShapeInterface {

    void setLine(double x1, double y1, double x2, double y2);

    SKPoint2D getP1();

    SKPoint2D getP2();

    double getX1();

    double getY1();

    double getX2();

    double getY2();
}
