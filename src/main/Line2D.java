package main;

public class Line2D  {

    private double coefficientA;
    private double coefficientB;
    private double coefficientC;

    public double getCoefficientA() {
        return coefficientA;
    }

    public double getCoefficientB() {
        return coefficientB;
    }

    public double getCoefficientC() {
        return coefficientC;
    }

    /**
     * Return a point of intersection of two points. This method will return a
     * point at coordinate (-1, -1) if two line are parallel or coincident.
     *
     * @param other
     * @return
     */
    public Point2D intersect(Line2D other) {
        Vector2D vectorA12 = new Vector2D(this.coefficientA, other.coefficientA);
        Vector2D vectorB12 = new Vector2D(this.coefficientB, other.coefficientB);
        Vector2D vectorC12 = new Vector2D(this.coefficientC, other.coefficientC);

        Vector2D p = Vector2D.getVectorOfLinearEquation(vectorA12, vectorB12, vectorC12);

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
}