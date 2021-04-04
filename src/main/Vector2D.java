package main;

public class Vector2D {

    private double coordX;
    private double coordY;

    public Vector2D() {
        coordX = 0.0;
        coordY = 0.0;
    }

    public Vector2D(double coordX, double coordY) {
        this.coordX = other.coordX;
        this.coordY = other.coordY;
    }

    public Vector2D(Point2D point_from, Point2D point_to) {
        this.coordX = point_to.coordX - point_from.coordX;
        this.coordY = point_to.coordY - point_from.coordY;
    }

    public Vector2D(Vector2D other) {
        this.coordX = other.coordX;
        this.coordY = other.coordY;
    }

    public void setCoord(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /**
     * Get vector unit.
     *
     * @return Vector 2D
     */
    public Vector2D getVectorUnit() {
        Vector2D vectorUnit = new Vector2D();
        double length = getLength();
        if (length > 0) {
            vectorUnit.setCoord(this.coordX / length, this.coordY / length);
        }
        return vectorUnit;
    }

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

    public double getAngleWithVector(Vector2D vector) {
        return (Math.atan2(-this.coordY, this.coordX)
                + Math.atan2(vector.coordY, vector.coordX));
    }

    public Vector2D getScaleVector(int k) {
        return new Vector2D(this.coordX * k, this.coordY * k);
    }
}
