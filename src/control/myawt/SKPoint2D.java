package control.myawt;

import control.util.Ultility;
import control.SettingConstants;
import java.util.Comparator;
import model.shape2d.Transform2D;
import model.shape2d.Vector2D;

public class SKPoint2D {

    protected int coordX;
    protected int coordY;
    
    public static final int COORD_X_O_Animation = (int) (1362 / 2) - 1;
    public static final int COORD_Y_O_Animation = (int) (973 / 2) - 1;

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
    public String saveCoordToString() {
            int x = coordX - (COORD_X_O_Animation / SettingConstants.RECT_SIZE);
            int y = -(coordY - (COORD_Y_O_Animation / SettingConstants.RECT_SIZE));
            return "(" + x + ", " + y + ")";
        
}

    /**
     * Create a copy point rotated from <code>basePoint</code> by an
     * <code>angle</code>.
     *
     * @param basePoint SKPoint2D (in visual coordinate)
     * @param angle double (radian)
     * @return SKPoint2D
     */
    public SKPoint2D createRotate(SKPoint2D basePoint, double angle) {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getRotateFromPointMat(basePoint, angle));
        result.convertToSystemCoord();
        return result;
    }

    public SKPoint2D createScale(double sx, double sy) {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getScaleMat(sx, sy));
        result.convertToSystemCoord();
        return result;
    }

    /**
     * Rotate point in place from basePoint by an angle.
     *
     * @param basePoint (in visual coordinate)
     * @param angle (radian)
     * @return
     */
    public SKPoint2D rotate(SKPoint2D basePoint, double angle) {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getRotateFromPointMat(basePoint, angle));
        convertToSystemCoord();
        return this;
    }

    /**
     * Rotate point in place from O by an angle.
     *
     * @param angle
     * @return
     */
    public SKPoint2D rotate(double angle) {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getRotateMat(angle));
        convertToSystemCoord();
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
    public SKPoint2D createPointSym(SKPoint2D basePoint) {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getPointSymMat(basePoint));
        result.convertToSystemCoord();
        return result;
    }

    /**
     * Create a copy symmetric point from Oy axis.
     *
     * @return
     */
    public SKPoint2D createOCenterSym() {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getOCenterSymMat());
        result.convertToSystemCoord();
        return result;
    }

    /**
     * Create a copy symmetric point from Ox axis.
     *
     * @return
     */
    public SKPoint2D createOXSym() {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getOXSymMat());
        result.convertToSystemCoord();
        return result;
    }

    /**
     * Create a copy symmetric point from Oy axis.
     *
     * @return
     */
    public SKPoint2D createOYSym() {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getOYSymMat());
        result.convertToSystemCoord();
        return result;
    }
    
    /**
     * Create a copy symmetric point from line x = k
     * @param k
     * @return 
     */
    public SKPoint2D createVerticalSym(int k) {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getVerSymMat(k));
        result.convertToSystemCoord();
        return result;
    }

    public SKPoint2D symVertical(int k) {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getVerSymMat(k));
        convertToSystemCoord();
        return this;
    }
    
    /**
     * Create a copy symmetric point from line y = k
     * @param k
     * @return 
     */
    public SKPoint2D createHorizontalSym(int k) {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getHorSymMat(k));
        result.convertToSystemCoord();
        return result;
    }

    public SKPoint2D symHorizontal(int k) {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getHorSymMat(k));
        convertToSystemCoord();
        return this;
    }

    public SKPoint2D createLineSym(double a, double b, double c) {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getLineSymMat(a, b, c));
        result.convertToSystemCoord();
        return result;
    }

    /**
     * Create a copy point moved by a vector.
     *
     * @param vector
     * @return
     */
    public SKPoint2D createMove(Vector2D vector) {
        SKPoint2D result = new SKPoint2D(this);
        result.convertToVisualCoord();
        Transform2D.transform(result, Transform2D.getMoveMat(vector.getCoordX(),
                vector.getCoordY()));
        result.convertToSystemCoord();
        return result;
    }

    /**
     * Move this point in place.
     *
     * @param vector
     * @return
     */
    public SKPoint2D move(Vector2D vector) {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getMoveMat(vector.getCoordX(),
                vector.getCoordY()));
        convertToSystemCoord();
        return this;
    }

    /**
     * Change this point symmetric through Ox in place.
     *
     * @return
     */
    public SKPoint2D symOx() {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getOXSymMat());
        convertToSystemCoord();
        return this;
    }

    /**
     * Change this point symmetric through Oy in place.
     *
     * @return
     */
    public SKPoint2D symOy() {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getOYSymMat());
        convertToSystemCoord();
        return this;
    }

    /**
     * Change this point symmetric through center O in place.
     *
     * @return
     */
    public SKPoint2D symOCenter() {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getOCenterSymMat());
        convertToSystemCoord();
        return this;
    }

    /**
     * Change this point symmetric through another point in place.
     *
     * @param basePoint
     * @return
     */
    public SKPoint2D symPoint(SKPoint2D basePoint) {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getPointSymMat(basePoint));
        convertToSystemCoord();
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
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getLineSymMat(a, b, c));
        convertToSystemCoord();
        return this;
    }

    /**
     * Scale this point in place.
     *
     * @param sx
     * @param sy
     * @return
     */
    public SKPoint2D scale(double sx, double sy) {
        convertToVisualCoord();
        Transform2D.transform(this, Transform2D.getScaleMat(sx, sy));
        convertToSystemCoord();
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
                Math.round((pointA.coordX + pointB.coordX) / 2),
                Math.round((pointA.coordY + pointB.coordY) / 2)
        );
        return midPoint;
    }

    public SKPoint2D setMidLocation(SKPoint2D pointA, SKPoint2D pointB) {
        setLocation(
                Math.round((pointA.coordX + pointB.coordX) / 2),
                Math.round((pointA.coordY + pointB.coordY) / 2)
        );
        return this;
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
        coordX += SettingConstants.COORD_X_O / SettingConstants.RECT_SIZE;
        coordY = -coordY + SettingConstants.COORD_Y_O / SettingConstants.RECT_SIZE;
        return this;
    }

    /**
     * Convert machine coordinate of point to position in user's view.
     *
     * @return
     */
    public SKPoint2D convertToVisualCoord() {
        coordX -= SettingConstants.COORD_X_O / SettingConstants.RECT_SIZE;
        coordY = -coordY + SettingConstants.COORD_Y_O / SettingConstants.RECT_SIZE;
        return this;
    }
}
