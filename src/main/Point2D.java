package main;

import java.awt.Color;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Point2D {
    protected int coordX;
    protected int coordY;
    protected Color color;
    
    public Point2D() {
        setCoord(0, 0);
    }
    
    public Point2D(int coordX, int coordY) {
        setCoord(coordX, coordY);
    }
    
    public void setCoord(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }
    
    public void setCoor(Point2D other) {
        this.coordX = other.coordX;
        this.coordY = other.coordY;
    }
    
    public int getCoordX() {
        return coordX;
    }
    
    public int getCoordY() {
        return coordY;
    }
    
    public void saveCoord(String[][] coordOfBoard) {
        String coordPointInformation = 
                "(" 
                + (coordX - (Settings.COORD_X_O / Settings.SIZE + Settings.SPACE))
                + ", "
                + (-(coordY - (Settings.COORD_Y_O / Settings.SIZE + Settings.SPACE)))
                + ")";
        
        coordOfBoard[coordX][coordY] = coordPointInformation;
    }
    
    /**
     * Rotate via <code>basePoint</code> by <code>angle</code>.
     * @param basePoint Point2D
     * @param angle double
     * @return Point2D
     */
    public Point2D rotate(Point2D basePoint, double angle) {
        Point2D resultPoint = new Point2D();
        
        int newCoordX = (int) (basePoint.coordX + (this.coordX - basePoint.coordX) * cos(angle)
                - (this.coordY - basePoint.coordY) * Math.sin(angle));
        int newCoordY = (int) (basePoint.coordY + (this.coordY - basePoint.coordY) * sin(angle)
                + (this.coordY - basePoint.coordY) * Math.cos(angle));
        
        resultPoint.setCoord(coordX, coordY);
        return resultPoint;
    }
    
    /**
     * Check if two points are collided.
     * @param other
     * @return true if equal, false otherwise.
     */
    public boolean equal(Point2D other) {
        return (this.coordX == other.coordX && this.coordY == other.coordY);
    }
    
    /**
     * Get symmetric point via <code>basePoint</code>.
     * @param basePoint Point2D
     * @return Point2D
     */
    public Point2D getSymmetricPoint(Point2D basePoint) {
        Point2D resultPoint = new Point2D();
        int newCoordX = 2 * basePoint.coordX - this.coordX;
        int newCoordY = 2 * basePoint.coordY - this.coordY;
        resultPoint.setCoord(newCoordX, newCoordY);
        return resultPoint;
    }
    
    public Point2D getSymmetricPoint(Line2D baseLine) {
        return new Point2D();
    }
    
//    public Point2D ConvertToPixelCoord (Point2D inputCoord, int OX, int OY){
//        Point2D result= new Point2D(0,0);
//        result.X= OX-2+inputCoord.X*7;// công thức chuyển tọa độ ( 7 = space + size);
//        result.Y= OY-2-inputCoord.Y*7;
//        return result;
//    }
}
