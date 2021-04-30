package model.shape2d;

import control.util.Ultility;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import control.SettingConstants;

public class Point2D {

    protected int coordX;
    protected int coordY;

    /**
     * One point has only one shape as it's parent.
     */
    protected Shape2D parentShape;

    public Point2D() {
        coordX = 0;
        coordY = 0;
        parentShape = null;
    }

    public Point2D(int coordX, int coordY, Shape2D parentShape) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.parentShape = parentShape;
    }

    public Point2D(int coordX, int coordY) {
        this(coordX, coordY, null);
    }

    public Point2D(Point2D other) {
        this(other.coordX, other.coordY, other.parentShape);
    }

    public void setParent(Shape2D parentShape) {
        this.parentShape = parentShape;
    }

    public void setCoord(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public void setCoord(Point2D other) {
        this.coordX = other.coordX;
        this.coordY = other.coordY;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void saveCoord(String[][] coordOfBoard) {
        if (Ultility.checkValidPoint(coordOfBoard, coordX, coordY)) {
            int x = (int) (coordX - (SettingConstants.COORD_X_O / SettingConstants.RECT_SIZE));
            int y = (int) (-(coordY - (SettingConstants.COORD_Y_O / SettingConstants.RECT_SIZE)));
            coordOfBoard[coordY][coordX] = "(" + x + ", " + y + ")";
        }
    }

    /**
     * Create a copy point rotated from <code>basePoint</code> by an
     * <code>angle</code>.
     *
     * @param basePoint Point2D
     * @param angle double (radian)
     * @return Point2D
     */
    public Point2D createRotationPoint(Point2D basePoint, double angle) {
//        Point2D resultPoint = new Point2D();

        Vector2D vec = new Vector2D(basePoint, this);

        vec = Transform2D.transform(vec, Transform2D.getRotateMat(angle));
        Point2D ret = Transform2D.transform(basePoint, Transform2D.getMoveMat(vec.getCoordX(), vec.getCoordY()));

//        int newCoordX = (int) (basePoint.coordX + vector.getCoordX() * cos(angle)
//                - vector.getCoordY() * Math.sin(angle));
//        int newCoordY = (int) (basePoint.coordY + vector.getCoordX() * sin(angle)
//                + vector.getCoordY() * Math.cos(angle));
//        resultPoint.setCoord(newCoordX, newCoordY);
        return ret;
    }

    /**
     * Rotate point in place from basePoint by an angle.
     *
     * @param basePoint
     * @param angle
     * @return
     */
    public Point2D rotate(Point2D basePoint, double angle) {
        Vector2D vec = new Vector2D(basePoint, this);

//        coordX = (int) (basePoint.coordX + vector.getCoordX() * cos(angle)
//                - vector.getCoordY() * Math.sin(angle));
//        coordY = (int) (basePoint.coordY + vector.getCoordX() * sin(angle)
//                + vector.getCoordY() * Math.cos(angle));
        vec = Transform2D.transform(vec, Transform2D.getRotateMat(angle));
        Point2D result = Transform2D.transform(basePoint, Transform2D.getMoveMat(vec.getCoordX(), vec.getCoordY()));
        setCoord(result);
        return this;
    }

    /**
     * Rotate point in place from O by an angle.
     *
     * @param angle
     * @return
     */
    public Point2D rotate(double angle) {
//        coordX = (int) (coordX * cos(angle) - coordY * Math.sin(angle));
//        coordY = (int) (coordX * sin(angle) + coordY * Math.cos(angle));
        Point2D result = Transform2D.transform(this, Transform2D.getRotateMat(angle));
        setCoord(result);
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
        Point2D result = new Point2D(this);
        result.convertMachineToViewCoord();
        result.coordX = 2 * basePoint.coordX - result.coordX;
        result.coordY = 2 * basePoint.coordY - result.coordY;
        result.convertViewToMachineCoord();
        return result;
    }

    /**
     * Create a copy symmetric point from Oy axis.
     *
     * @return
     */
    public Point2D createCenterOSymmetry() {
        Point2D result = new Point2D(this);
        result.convertMachineToViewCoord();
//        point.reflect();
        result.setCoord(Transform2D.transform(result, Transform2D.getSymOCenterMat()));
        result.convertViewToMachineCoord();
        return result;
    }

    /**
     * Create a copy symmetric point from Ox axis.
     *
     * @return
     */
    public Point2D createOXSymmetryPoint() {
        Point2D result = new Point2D(this);
        result.convertMachineToViewCoord();
//        result.coordY = -result.coordY;
        result.setCoord(Transform2D.transform(result, Transform2D.getSymOXMat()));
        result.convertViewToMachineCoord();
        return result;
    }

    /**
     * Create a copy symmetric point from Oy axis.
     *
     * @return
     */
    public Point2D createOYSymmetryPoint() {
        Point2D result = new Point2D(this);
        result.convertMachineToViewCoord();
//        result.coordX = -result.coordX;
        result.setCoord(Transform2D.transform(result, Transform2D.getSymOYMat()));
        result.convertViewToMachineCoord();
        return result;
    }

    /**
     * https://stackoverflow.com/questions/3306838/algorithm-for-reflecting-a-point-across-a-line
     *
     * XXX Work later.
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public Point2D createLineSymmetryPoint(double a, double b, double c) {
        Point2D result = new Point2D(this);
        result.symLine(a, b, c);
        return result;
    }

    /**
     * Create a copy point moved by a vector.
     *
     * @param vector
     * @return
     */
    public Point2D createMovingPoint(Vector2D vector) {
//        return new Point2D(
//                this.coordX + (int) vector.getCoordX(),
//                this.coordY + (int) vector.getCoordY(),
//                this.parentShape
//        );
        return Transform2D.transform(this, Transform2D.getMoveMat(vector.getCoordX(), vector.getCoordY()));
    }

    /**
     * Move this point in place.
     *
     * @param vector
     * @return
     */
    public Point2D move(Vector2D vector) {
//        this.coordX += (int) vector.getCoordX();
//        this.coordY += (int) vector.getCoordY();
        setCoord(Transform2D.transform(this, Transform2D.getMoveMat(vector.getCoordX(), vector.getCoordY())));
        return this;
    }

    /**
     * Change this point symmetric through Ox in place.
     *
     * @return
     */
    public Point2D symOx() {
        setCoord(Transform2D.transform(this, Transform2D.getSymOXMat()));
        return this;
    }

    /**
     * Change this point symmetric through Oy in place.
     *
     * @return
     */
    public Point2D symOy() {
        setCoord(Transform2D.transform(this, Transform2D.getSymOYMat()));
        return this;
    }

    /**
     * Change this point symmetric through center O in place.
     *
     * @return
     */
    public Point2D symCenterO() {
        setCoord(Transform2D.transform(this, Transform2D.getSymOCenterMat()));
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

    /**
     * Change this point symmetric through a line in place.
     *
     * @param a coefficient a
     * @param b coefficient b
     * @param c coefficient c
     * @return
     */
    public Point2D symLine(double a, double b, double c) {
        convertMachineToViewCoord();
        double m = -a / b;
        double d = (this.coordX + (this.coordY - c) * m) / (1 + m * m);
        this.coordX = (int) (2 * d - this.coordX);
        this.coordY = (int) (2 * d * m - this.coordY + 2 * c);
        convertViewToMachineCoord();
        return this;
    }

    /**
     * Get the middle point between two distinct points.
     *
     * @param pointA
     * @param pointB
     * @return
     */
    public static Point2D midPoint(Point2D pointA, Point2D pointB) {
        Point2D midPoint = new Point2D();
        midPoint.setCoord(
                (int) (pointA.coordX + pointB.coordX) / 2,
                (int) (pointA.coordY + pointB.coordY) / 2
        );
        return midPoint;
    }

    public String toString() {
        int x = (int) (coordX - (SettingConstants.COORD_X_O / SettingConstants.RECT_SIZE));
        int y = (int) (-(coordY - (SettingConstants.COORD_Y_O / SettingConstants.RECT_SIZE)));

        String result = "(" + x + ", " + y + ")";
        return result;
    }

    public static void swap(Point2D pointA, Point2D pointB) {
        Point2D temp = new Point2D();
        temp.setCoord(pointA);
        pointA.setCoord(pointB);
        pointB.setCoord(temp);
    }

    /**
     * Convert relative coordinate of point in user's view to machine coordinate
     * rule (the center coordinate system is placed at the left top corner of
     * component).
     *
     * @return
     */
    public Point2D convertViewToMachineCoord() {
        coordX = (coordX + SettingConstants.COORD_X_O) / SettingConstants.RECT_SIZE;
        coordY = (coordY - SettingConstants.COORD_Y_O) / (-SettingConstants.RECT_SIZE);
        return this;
    }

    /**
     * Convert machine coordinate of point to position in user's view.
     *
     * @return
     */
    public Point2D convertMachineToViewCoord() {
        coordX = coordX * SettingConstants.RECT_SIZE - SettingConstants.COORD_X_O;
        coordY = coordY * (-SettingConstants.RECT_SIZE) + SettingConstants.COORD_Y_O;
        return this;
    }

    public static void main(String[] args) {
    }
}
