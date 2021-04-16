package model.shape2d;

import java.awt.Color;

public class Line2D extends Segment2D {

    private double coefficientA;
    private double coefficientB;
    private double coefficientC;

    /*
    Coefficient of line.
    Form: ax + by + c = 0
     */
    private double coeffA;
    private double coeffB;
    private double coeffC;

    public Line2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        coeffA = startPoint.coordY - endPoint.coordY;
        coeffB = -(startPoint.coordX - endPoint.coordX);
        coeffC = -(coeffA * startPoint.coordX + coeffB * startPoint.coordY);
    }

    public double getCoefficientA() {
        return coeffA;
    }

    public double getCoefficientB() {
        return coeffB;
    }

    public double getCoefficientC() {
        return coeffC;
    }

    /**
     * Return a point of intersection of two points. This method will return a
     * point at coordinate (-1, -1) if two line are parallel or coincident.
     *
     * @param other
     * @return
     */
    public Point2D intersect(Line2D other) {
        Vector2D vectorA12 = new Vector2D(this.coeffA, other.coeffA);
        Vector2D vectorB12 = new Vector2D(this.coeffB, other.coeffB);
        Vector2D vectorC12 = new Vector2D(this.coeffC, other.coeffC);

        Vector2D p = Vector2D.getVectorOfLinearEquationRepr(vectorA12, vectorB12, vectorC12);

        // If two lines are parallel.
        if (Double.isNaN(p.getCoordX())) {
            return new Point2D(-1, -1);
        }

        // If two lines are coincident.
        if (Double.isInfinite(p.getCoordX())) {
            return new Point2D(-1, -1);
        }

        return new Point2D((int) p.getCoordX(), (int) p.getCoordY());
    }

    /**
     * Compute distance from one point to line.
     *
     * @param line
     * @param point
     * @return
     */
    public static double distToPoint(Line2D line, Point2D point) {
        return Math.abs(
                (line.coeffA * point.coordX + line.coeffB * point.coordY + line.coeffC)
                / Math.sqrt(line.coeffA * line.coeffA + line.coeffB * line.coeffB)
        );
    }

    /**
     * Get perpendicular line from this through a point.
     *
     * @param point
     * @return
     */
    public Line2D getPerpendicularLine(Point2D point) {
        Line2D line = new Line2D(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        line.coeffA = this.coeffB;
        line.coeffB = this.coeffA;
        line.coeffC = this.coeffA * point.coordX - this.coeffB * point.coordY;

        return line;
    }
}
