package control.myawt;

public interface SKRectangleInterface extends SKShapeInterface {

    double getX();

    double getY();

    double getWidth();

    double getHeight();

    double getMinX();

    double getMaxX();

    double getMinY();

    double getMaxY();

    void setRect(double x, double y, double width, double height);

    void setFrame(double x, double y, double width, double height);

    SKRectangle createIntersection(SKRectangle rect);

    boolean intersectsLine(double xc, double yc, double xe, double ye);

    void setBounds(int x, int y, int width, int height);

    void setBounds(SKRectangleInterface rect);

    void setLocation(int x, int y);

    void add(SKRectangleInterface rect);

    void add(double x, double y);

    boolean contains(double x, double y);

    SKRectangleInterface union(SKRectangleInterface bounds);

//    void setSize(int width, int height);
}
