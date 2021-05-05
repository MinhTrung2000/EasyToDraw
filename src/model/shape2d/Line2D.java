package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;

public class Line2D extends Segment2D {

    /*
    Coefficient of line.
    Form: ax + by + c = 0
     */
    private double coeffA;
    private double coeffB;
    private double coeffC;

    public Line2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        coeffA = startPoint.getCoordY() - endPoint.getCoordY();
        coeffB = -(startPoint.getCoordX() - endPoint.getCoordX());
        coeffC = -(coeffA * startPoint.getCoordX() + coeffB * startPoint.getCoordY());
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

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {

        coeffA = startPoint.getCoordY() - endPoint.getCoordY();
        coeffB = -(startPoint.getCoordX() - endPoint.getCoordX());
        coeffC = -(coeffA * startPoint.getCoordX() + coeffB * startPoint.getCoordY());

        //2 điểm trùng nhau => ko xác định được hướng
        if (startPoint.equal(endPoint)) {
            this.startPoint.setLocation(-1, -1);
            this.endPoint.setLocation(-1, -1);
            return;
        }

        int widthLimit = (control.SettingConstants.WIDTH_DRAW_AREA / control.SettingConstants.RECT_SIZE) - 1;
        int heightLimit = (control.SettingConstants.HEIGHT_DRAW_AREA / control.SettingConstants.RECT_SIZE) - 1;

        int limitTop = (int) Math.round((-coeffC - coeffB * 0) / coeffA);
        int limitLeft = (int) Math.round((-coeffC - coeffA * 0) / coeffB);
        int limitBot = (int) Math.round((-coeffC - coeffB * (heightLimit)) / coeffA);
        int limitRight = (int) Math.round((-coeffC - coeffA * (widthLimit)) / coeffB);

        this.startPoint = null;
        this.endPoint = null;

        //xử lý trường hợp đặc biệt (Bot = 0, right = left) khi nằm ngang 180 độ
        if (limitRight == limitLeft && limitRight >= 0 && limitRight < heightLimit) {
            this.startPoint = new SKPoint2D(0, limitLeft);
            this.endPoint = new SKPoint2D(widthLimit, limitRight);
        }

        if (limitBot >= 0 && limitBot <= widthLimit) {
            if (this.startPoint == null) {
                this.startPoint = new SKPoint2D(limitBot, heightLimit);
            } else if (this.endPoint == null) {
                this.endPoint = new SKPoint2D(limitBot, heightLimit);
            }
        }

        if (limitTop >= 0 && limitTop <= widthLimit) {
            if (this.startPoint == null) {
                this.startPoint = new SKPoint2D(limitTop, 0);
            } else if (this.endPoint == null) {
                this.endPoint = new SKPoint2D(limitTop, 0);
            }
        }

        if (limitLeft >= 0 && limitLeft <= heightLimit) {
            if (this.startPoint == null) {
                this.startPoint = new SKPoint2D(0, limitLeft);
            } else if (this.endPoint == null) {
                this.endPoint = new SKPoint2D(0, limitLeft);
            }
        }

        if (limitRight >= 0 && limitRight <= heightLimit) {
            if (this.startPoint == null) {
                this.startPoint = new SKPoint2D(widthLimit, limitRight);
            } else if (this.endPoint == null) {
                this.endPoint = new SKPoint2D(widthLimit, limitRight);
            }
        }

    }

    /**
     * Return a point of intersection of two points. This method will return a
     * point at coordinate (-1, -1) if two line are parallel or coincident.
     *
     * @param other
     * @return
     */
    public SKPoint2D intersect(Line2D other) {
        Vector2D vectorA12 = new Vector2D(this.coeffA, other.coeffA);
        Vector2D vectorB12 = new Vector2D(this.coeffB, other.coeffB);
        Vector2D vectorC12 = new Vector2D(this.coeffC, other.coeffC);

        Vector2D p = Vector2D.getVectorOfLinearEquationRepr(vectorA12, vectorB12, vectorC12);

        // If two lines are parallel.
        if (Double.isNaN(p.getCoordX())) {
            return new SKPoint2D(-1, -1);
        }

        // If two lines are coincident.
        if (Double.isInfinite(p.getCoordX())) {
            return new SKPoint2D(-1, -1);
        }

        return new SKPoint2D((int) p.getCoordX(), (int) p.getCoordY());
    }

    /**
     * Compute distance from one point to line.
     *
     * @param line
     * @param point
     * @return
     */
    public static double distToPoint(Line2D line, SKPoint2D point) {
        return Math.abs(
                (line.coeffA * point.getCoordX() + line.coeffB * point.getCoordY() + line.coeffC)
                / Math.sqrt(line.coeffA * line.coeffA + line.coeffB * line.coeffB)
        );
    }

    /**
     * Get perpendicular line from this through a point.
     *
     * @param point
     * @return
     */
    public Line2D getPerpendicularLine(SKPoint2D point) {
        Line2D line = new Line2D(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        line.coeffA = this.coeffB;
        line.coeffB = this.coeffA;
        line.coeffC = this.coeffA * point.getCoordX() - this.coeffB * point.getCoordY();

        return line;
    }
}
