package model.shape2d;

import java.awt.Color;
import main.Settings;
import main.Ultility;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Point2D {

    protected int coordX;
    protected int coordY;

    public Point2D() {
        coordX = 0;
        coordY = 0;
    }

    public Point2D(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Point2D(Point2D other) {
        this.coordX = other.coordX;
        this.coordY = other.coordY;
    }
    
    public void setCoord(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public void setCoord(Point2D other) {
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
        if (Ultility.checkValidPoint(coordOfBoard, coordX, coordY)) {
            int x = (int) (coordX - (Settings.COORD_X_O / Settings.RECT_SIZE));
            int y = (int) (- (coordY - (Settings.COORD_Y_O / Settings.RECT_SIZE)));
            coordOfBoard[coordX][coordY] = "(" + x + ", " + y + ")";
        }
    }

    /**
     * Create a copy point rotated from <code>basePoint</code> by an
     * <code>angle</code>.
     *
     * @param basePoint Point2D
     * @param angle double
     * @return Point2D
     */
    public Point2D createRotationPoint(Point2D basePoint, double angle) {
        Point2D resultPoint = new Point2D();

        Vector2D vector = new Vector2D(basePoint, this);

        int newCoordX = (int) (basePoint.coordX + vector.getCoordX() * cos(angle)
                - vector.getCoordY() * Math.sin(angle));
        int newCoordY = (int) (basePoint.coordY + vector.getCoordX() * sin(angle)
                + vector.getCoordY() * Math.cos(angle));

        resultPoint.setCoord(newCoordX, newCoordY);
        return resultPoint;
    }

    /**
     * Rotate point in place from basePoint by an angle.
     *
     * @param basePoint
     * @param angle
     * @return
     */
    public Point2D rotate(Point2D basePoint, double angle) {
        Vector2D vector = new Vector2D(basePoint, this);

        coordX = (int) (basePoint.coordX + vector.getCoordX() * cos(angle)
                - vector.getCoordY() * Math.sin(angle));
        coordY = (int) (basePoint.coordY + vector.getCoordX() * sin(angle)
                + vector.getCoordY() * Math.cos(angle));

        return this;
    }

    /**
     * Rotate point in place from O by an angle.
     *
     * @param angle
     * @return
     */
    public Point2D rotate(double angle) {
        coordX = (int) (coordX * cos(angle) - coordY * Math.sin(angle));
        coordY = (int) (coordX * sin(angle) + coordY * Math.cos(angle));
        return this;
    }

    /**
     * Check if two points are collided.
     *
     * @param other
     * @return true if equal, false otherwise.
     */
    public boolean equal(Point2D other) {
        return (this.coordX == other.coordX && this.coordY == other.coordY);
    }

    /**
     * Create a copy symmetric point from <code>basePoint</code>.
     *
     * @param basePoint Point2D
     * @return Point2D
     */
    public Point2D createSymmetryPoint(Point2D basePoint) {
        Point2D resultPoint = new Point2D();
        int newCoordX = 2 * basePoint.coordX - this.coordX;
        int newCoordY = 2 * basePoint.coordY - this.coordY;
        resultPoint.setCoord(newCoordX, newCoordY);
        return resultPoint;
    }

    /**
     * Get a copy symmetric point from line.
     *
     * @param line
     */
//    public Point2D getLineSymmetry(Line2D line) {
//        double coefficientA_PerpendicularLine = - (line.getCoefficientB() / line.getCoefficientA());
//        double coefficientC_PerpendicularLine = this.coordY + coefficientA_PerpendicularLine * this.coordX;
//        double coefficientB_PerpendicularLine = -1.0;
//        
//    }
    /**
     * Create a copy symmetric point from Ox axis.
     *
     * @return
     */
    public Point2D createOXSymmetryPoint() {
        Point2D result = new Point2D();

        result.setCoord(
                -(this.coordX - Settings.COORD_X_O),
                this.coordY
        );

        return result;
    }

    /**
     * Create a copy symmetric point from Oy axis.
     *
     * @return
     */
    public Point2D createOYSymmetryPoint() {
        Point2D result = new Point2D();

        result.setCoord(
                this.coordX,
                -(this.coordY - Settings.COORD_Y_O)
        );

        return result;
    }

    /**
     * Create a copy point moved by a vector.
     *
     * @param vector
     * @return
     */
    public Point2D createMovingPoint(Vector2D vector) {
        return new Point2D(
                this.coordX + (int) vector.getCoordX(),
                this.coordY + (int) vector.getCoordY()
        );
    }

    /**
     * Move this point in place.
     *
     * @param vector
     * @return
     */
    public Point2D move(Vector2D vector) {
        this.coordX += (int) vector.getCoordX();
        this.coordY += (int) vector.getCoordY();
        return this;
    }

    /**
     * Change this point symmetric through Ox in place.
     *
     * @return
     */
    public Point2D symOx() {
        setCoord(
                -(this.coordX - Settings.COORD_X_O),
                this.coordY
        );

        return this;
    }

    /**
     * Change this point symmetric through Oy in place.
     *
     * @return
     */
    public Point2D symOy() {
        setCoord(
                this.coordX,
                -(this.coordY - Settings.COORD_Y_O)
        );

        return this;
    }

    /**
     * Change this point symmetric through another point in place.
     *
     * @param basePoint
     * @return
     */
    public Point2D symPoint(Point2D basePoint) {
        int newCoordX = 2 * basePoint.coordX - this.coordX;
        int newCoordY = 2 * basePoint.coordY - this.coordY;
        setCoord(newCoordX, newCoordY);
        return this;
    }
}
