package control.myawt;

public interface SKRectangleInterface {

    double getX();

    double getY();
    
    double getWidth();
    
    double getHeight();
    
    void setBounds(int x, int y, int width, int height);
    
    void setBounds(SKRectangleInterface rect);
    
    void setLocation(int x, int y);
    
    void add(SKRectangleInterface rect);
    
    void add(double x, double y);
    
    boolean contains(SKPoint2D point);
    
    boolean contains(double x, double y);
    
    SKRectangleInterface union(SKRectangleInterface bounds);
    
    void setSize(int width, int height);
}
