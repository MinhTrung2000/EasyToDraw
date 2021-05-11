package control.myawt;

import control.util.Ultility;
import control.SettingConstants;
import java.util.Comparator;
import model.shape2d.Transform2D;
import model.shape2d.Vector2D;

public class SKPoint2D {

    protected int coordX;
    protected int coordY;

    public SKPoint2D() {
        SKPoint2D.this.setLocation(0, 0);
    }

    public SKPoint2D(double coordX, double coordY) {
        setLocation(coordX, coordY);
    }

    public SKPoint2D(int coordX, int coordY) {
        setLocation(coordX, coordY);
    }

    public SKPoint2D(SKPoint2D other) {
        setLocation(other.coordX, other.coordY);
    }

    public SKPoint2D(SKPoint2D other, int adjustmentValue_X, int adjustmentValue_Y) {
        setLocation(other.coordX + adjustmentValue_X, other.coordY + adjustmentValue_Y);
    }

    public void setLocation(double coordX, double coordY) {
        setLocation((int) coordX, (int) coordY);
    }

    public void setLocation(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public void setLocation(SKPoint2D other) {
        setLocation(other.coordX, other.coordY);
    }

    public void setLocation(SKPoint2D other, double x, double y) {
        setLocation(other.coordX + x, other.coordY + y);
    }

    public void setLocation(SKPoint2D other, int x, int y) {
        setLocation(other.coordX + x, other.coordY + y);
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

    public int getCoordZ() {
        return 0;
    }

    public static double distance(SKPoint2D p1, SKPoint2D p2) {
        return Math.hypot(p2.coordX - p1.coordX, p2.coordY - p1.coordY);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }

    public double distanceSquare(SKPoint2D p1, SKPoint2D p2) {
        return distanceSquare(p1.coordX, p1.coordY, p2.coordX, p2.coordY);
    }

    public static double distanceSquare(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return dx * dx + dy * dy;
    }

    public void saveCoord(String[][] coordOfBoard) {
        if (Ultility.checkValidPoint(coordOfBoard, coordX, coordY)) {
            int x = coordX - (SettingConstants.COORD_X_O / SettingConstants.RECT_SIZE);
            int y = -(coordY - (SettingConstants.COORD_Y_O / SettingConstants.RECT_SIZE));
            coordOfBoard[coordY][coordX] = "(" + x + ", " + y + ")";
        }
    }

    /**
     * Create a copy point rotated from <code>basePoint</code> by an
     * <code>angle</code>.
     *
     * @param basePoint SKPoint2D
     * @param angle double (radian)
     * @return SKPoint2D
     */
    public SKPoint2D getRotationPoint(SKPoint2D basePoint, double angle) {
        return Transform2D.getTransformPoint(this, Transform2D.getRotateFromPointMat(basePoint, angle));
    }

    public SKPoint2D getScalePoint(double sx, double sy) {
        return Transform2D.getTransformPoint(this, Transform2D.getScaleMat(sx, sy));
    }
    
    /**
     * Rotate point in place from basePoint by an angle.
     *
     * @param basePoint
     * @param angle
     * @return
     */
    public SKPoint2D rotate(SKPoint2D basePoint, double angle) {
        Transform2D.transform(this, Transform2D.getRotateFromPointMat(basePoint, angle));
        return this;
    }

    /**
     * Rotate point in place from O by an angle.
     *
     * @param angle
     * @return
     */
    public SKPoint2D rotate(double angle) {
        Transform2D.transform(this, Transform2D.getRotateMat(angle));
        return this;
    }

    /**
     * Check if two points are collided.
     *
     * @param other
     * @return true if equal, false otherwise.
     */
    public boolean equal(SKPoint2D other) {
        return (this.coordX == other.coordX && this.coordY == other.coordY);
    }

    /**
     * Create a copy symmetric point from <code>basePoint</code>.
     *
     * @param basePoint SKPoint2D
     * @return SKPoint2D
     */
    public SKPoint2D createSymmetryPoint(SKPoint2D basePoint) {
        return Transform2D.getTransformPoint(this, Transform2D.getPointSymMat(basePoint));
    }

    /**
     * Create a copy symmetric point from Oy axis.
     *
     * @return
     */
    public SKPoint2D createCenterOSymmetry() {
        convertToVisualCoord();
        SKPoint2D result = Transform2D.getTransformPoint(this, Transform2D.getOCenterSymMat());
        convertToSystemCoord();
        return result;
    }

    /**
     * Create a copy symmetric point from Ox axis.
     *
     * @return
     */
    public SKPoint2D createOXSymmetryPoint() {
        convertToVisualCoord();
        SKPoint2D result = Transform2D.getTransformPoint(this, Transform2D.getOXSymMat());
        convertToSystemCoord();
        return result;
    }

    /**
     * Create a copy symmetric point from Oy axis.
     *
     * @return
     */
    public SKPoint2D createOYSymmetryPoint() {
        convertToVisualCoord();
        SKPoint2D result = Transform2D.getTransformPoint(this, Transform2D.getOYSymMat());
        convertToSystemCoord();
        return result;
    }

    public SKPoint2D getLineSymPoint(double a, double b, double c) {
        return Transform2D.getTransformPoint(this, Transform2D.getLineSymMat(a, b, c));
    }

    /**
     * Create a copy point moved by a vector.
     *
     * @param vector
     * @return
     */
    public SKPoint2D getMovePoint(Vector2D vector) {
        return Transform2D.getTransformPoint(this, Transform2D.getMoveMat(
                vector.getCoordX(), vector.getCoordY()));
    }

    /**
     * Move this point in place.
     *
     * @param vector
     * @return
     */
    public SKPoint2D move(Vector2D vector) {
        Transform2D.transform(this, Transform2D.getMoveMat(vector.getCoordX(),
                vector.getCoordY()));
        return this;
    }

    /**
     * Change this point symmetric through Ox in place.
     *
     * @return
     */
    public SKPoint2D symOx() {
        Transform2D.transform(this, Transform2D.getOXSymMat());
        return this;
    }

    /**
     * Change this point symmetric through Oy in place.
     *
     * @return
     */
    public SKPoint2D symOy() {
        Transform2D.transform(this, Transform2D.getOYSymMat());
        return this;
    }

    /**
     * Change this point symmetric through center O in place.
     *
     * @return
     */
    public SKPoint2D symCenterO() {
        Transform2D.transform(this, Transform2D.getOCenterSymMat());
        return this;
    }

    /**
     * Change this point symmetric through another point in place.
     *
     * @param basePoint
     * @return
     */
    public SKPoint2D symPoint(SKPoint2D basePoint) {
        Transform2D.transform(this, Transform2D.getPointSymMat(basePoint));
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
    public SKPoint2D symLine(double a, double b, double c) {
        Transform2D.transform(this, Transform2D.getLineSymMat(a, b, c));
        return this;
    }
    
    /**
     * Scale this point in place.
     * @param sx
     * @param sy
     * @return 
     */
    public SKPoint2D scale(double sx, double sy) {
        Transform2D.transform(this, Transform2D.getScaleMat(sx, sy));
        return this;
    }

    /**
     * Get the middle point between two distinct points.
     *
     * @param pointA
     * @param pointB
     * @return
     */
    public static SKPoint2D midPoint(SKPoint2D pointA, SKPoint2D pointB) {
        SKPoint2D midPoint = new SKPoint2D();
        midPoint.setLocation(
                (int) (pointA.coordX + pointB.coordX) / 2,
                (int) (pointA.coordY + pointB.coordY) / 2
        );
        return midPoint;
    }

    public String toString() {
        return "(" + coordX + ", " + coordY + ")";
    }

    public static void swap(SKPoint2D pointA, SKPoint2D pointB) {
        SKPoint2D temp = new SKPoint2D();
        temp.setLocation(pointA);
        pointA.setLocation(pointB);
        pointB.setLocation(temp);
    }

    /**
     * Convert relative coordinate of point in user's view to machine coordinate
     * rule (the center coordinate system is placed at the left top corner of
     * component).
     *
     * @return
     */
    public SKPoint2D convertToSystemCoord() {
        coordX = (coordX + SettingConstants.COORD_X_O) / SettingConstants.RECT_SIZE;
        coordY = (coordY - SettingConstants.COORD_Y_O) / (-SettingConstants.RECT_SIZE);
        return this;
    }

    /**
     * Convert machine coordinate of point to position in user's view.
     *
     * @return
     */
    public SKPoint2D convertToVisualCoord() {
        coordX = coordX * SettingConstants.RECT_SIZE - SettingConstants.COORD_X_O;
        coordY = coordY * (-SettingConstants.RECT_SIZE) + SettingConstants.COORD_Y_O;
        return this;
    }
}
