package model.shape2d;

public class Vector2D {

    private double x;
    private double y;

    public Vector2D() {
        x = 1.0;
        y = 1.0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point2D point_from, Point2D point_to) {
        this.x = point_to.coordX - point_from.coordX;
        this.y = point_to.coordY - point_from.coordY;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getCoordX() {
        return x;
    }

    public double getCoordY() {
        return y;
    }

    public void setCoord(double coordX, double coordY) {
        this.x = coordX;
        this.y = coordY;
    }

    /**
     * Get the length of this vector.
     *
     * @return
     */
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Get the angle of this vector with Ox axis.
     *
     * @return double
     */
    public double getAngleWithOx() {
        return Math.atan2(this.y, this.x);
    }

    /**
     * Get the angle of this vector with another vector.
     *
     * @param vector
     * @return
     */
    public double getAngleWithVector(Vector2D vector) {
        return (Math.atan2(-this.y, this.x)
                + Math.atan2(vector.y, vector.x));
    }

    /**
     * Get a scale vector of this vector by k factor.
     *
     * @param k
     * @return
     */
    public Vector2D getScaleVector(int k) {
        Vector2D result = new Vector2D(this.x * k, this.y * k);
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
            vectorUnit.setCoord(this.x / length, this.y / length);
        }
        return vectorUnit;
    }

    public Vector2D scale(double k) {
        this.x *= k;
        this.y *= k;
        return this;
    }

    /**
     * Compute dot product of two vectors.
     */
    public static double getDotProduct(Vector2D vec_a, Vector2D vec_b) {
        return (vec_a.x * vec_b.x + vec_a.y * vec_b.y);
    }

    /**
     * Compute cross product of two vectors.
     */
    public static double getCrossProduct(Vector2D vec_a, Vector2D vec_b) {
        return (vec_a.x * vec_b.y - vec_a.y * vec_b.x);
    }

    /**
     * Get vector of linear equation representation.
     *
     * @param vector_a
     * @param vector_b
     * @param vector_c
     * @return
     */
    public static Vector2D getVectorOfLinearEquationRepr(Vector2D vector_a, Vector2D vector_b, Vector2D vector_c) {
        double crossProduct_a_b = getCrossProduct(vector_a, vector_b);
        double crossProduct_c_b = getCrossProduct(vector_c, vector_b);
        double crossProduct_a_c = getCrossProduct(vector_a, vector_c);
        return new Vector2D(
                crossProduct_c_b / crossProduct_a_b,
                crossProduct_a_c / crossProduct_a_b
        );
    }

    /**
     * Get a new vector equals k times unit of this vector.
     *
     * @param k
     * @return
     */
    public Vector2D getKTimesUnit(int k) {
        Vector2D unitVector = this.getUnitVector();
        Vector2D result = new Vector2D(unitVector.x * k, unitVector.y * k);
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
                (int) (startPoint.coordX + unitVector.x * k + 0.5),
                (int) (startPoint.coordY + unitVector.y * k + 0.5)
        );
        return result;
    }

    public static Vector2D getVectorOfLinearEquation(Vector2D vector_a, Vector2D vector_b, Vector2D vector_c) {
        double crossProduct = getCrossProduct(vector_a, vector_b);
        return new Vector2D(
                getCrossProduct(vector_c, vector_b) / crossProduct,
                getCrossProduct(vector_a, vector_c) / crossProduct
        );
    }

public static Vector2D getVectorMinus(Vector2D vec_a, Vector2D vec_b) {
        return new Vector2D(vec_a.x - vec_b.x, vec_a.y - vec_b.y);
    }
}
