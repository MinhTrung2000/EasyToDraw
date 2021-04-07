package main;

public class Vector2D {

    private double coordX;
    private double coordY;

    public Vector2D() {
        coordX = 0.0;
        coordY = 0.0;
    }

    public Vector2D(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Vector2D(Point2D point_from, Point2D point_to) {
        this.coordX = point_to.coordX - point_from.coordX;
        this.coordY = point_to.coordY - point_from.coordY;
    }

    public Vector2D(Vector2D other) {
        this.coordX = other.coordX;
        this.coordY = other.coordY;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoord(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /**
     * Get the length of this vector.
     *
     * @return
     */
    public double getLength() {
        return Math.sqrt(coordX * coordX + coordY * coordY);
    }

    /**
     * Get the angle of this vector with Ox axis.
     *
     * @return double
     */
    public double getAngleWithOx() {
        return Math.atan2(this.coordY, this.coordX);
    }

    /**
     * Get the angle of this vector with another vector.
     *
     * @param vector
     * @return
     */
    public double getAngleWithVector(Vector2D vector) {
        return (Math.atan2(-this.coordY, this.coordX)
                + Math.atan2(vector.coordY, vector.coordX));
    }

    /**
     * Get a scale vector of this vector by k factor.
     *
     * @param k
     * @return
     */
    public Vector2D getScaleVector(int k) {
        Vector2D result = new Vector2D(this.coordX * k, this.coordY * k);
        return result;
    }

    /**
     * Get vector unit from this vector.
     *
     * @return Vector 2D
     */
    public Vector2D getUnitVector() {
        Vector2D vectorUnit = new Vector2D();
        double length = getLength();
        if (length > 0) {
            vectorUnit.setCoord(this.coordX / length, this.coordY / length);
        }
        return vectorUnit;
    }

    /**
     * Get a new vector equals k times unit of this vector.
     *
     * @param k
     * @return
     */
    public Vector2D getKTimesUnit(int k) {
        Vector2D unitVector = this.getUnitVector();
        Vector2D result = new Vector2D(unitVector.coordX * k, unitVector.coordY * k);
        return result;
    }

    /**
     * Get an ending point of vector equaling k times unit of this vector.
     *
     * @param startPoint
     * @param k
     * @return
     */
    public Point2D getKTimesUnitPoint(Point2D startPoint, int k) {
        Vector2D unitVector = this.getUnitVector();
        Point2D result = new Point2D(
                (int) (startPoint.coordX + unitVector.coordX * k + 0.5),
                (int) (startPoint.coordY + unitVector.coordY * k + 0.5)
        );
        return result;
    }

    /**
     * Compute a cross product of two vectors.
     *
     * @param vector1
     * @param vector2
     * @return
     */
    public static double getCrossProduct(Vector2D vector_u, Vector2D vector_v) {
        return (vector_u.coordX * vector_v.coordY - vector_u.coordY * vector_v.coordX);
    }
    
    public static Vector2D getVectorOfLinearEquation(Vector2D vector_a, Vector2D vector_b, Vector2D vector_c) {
        double crossProduct = getCrossProduct(vector_a, vector_b);
        return new Vector2D(
                getCrossProduct(vector_c, vector_b) / crossProduct, 
                getCrossProduct(vector_a, vector_c) / crossProduct
        );
    }

}
