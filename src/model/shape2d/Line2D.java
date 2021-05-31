package model.shape2d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.util.Ultility;
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
        coeffA = startPoint2D.getCoordY() - endPoint2D.getCoordY();
        coeffB = -(startPoint2D.getCoordX() - endPoint2D.getCoordX());
        coeffC = -(coeffA * startPoint2D.getCoordX() + coeffB * startPoint2D.getCoordY());
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
        //Ax + By + C = 0
        /* x - x1     y - y1
           ------  =  ------
           x2 - x1    y2 - y1
        */
        coeffA = startPoint.getCoordY() - endPoint.getCoordY();
        coeffB = -(startPoint.getCoordX() - endPoint.getCoordX());
        coeffC = -(coeffA * startPoint.getCoordX() + coeffB * startPoint.getCoordY());
        //coeffC dùng bộ x, y của start hay end để tìm đều dc
        System.out.println(coeffA + " " + coeffB + " " + coeffC);
        //2 điểm trùng nhau => ko xác định được hướng
        if (startPoint.equal(endPoint)) {
            this.startPoint2D.setLocation(-1, -1);
            this.endPoint2D.setLocation(-1, -1);
            return;
        }

        int widthLimit = (control.SettingConstants.WIDTH_DRAW_AREA / control.SettingConstants.RECT_SIZE) - 1;
        int heightLimit = (control.SettingConstants.HEIGHT_DRAW_AREA / control.SettingConstants.RECT_SIZE) - 1;
        
        
        //Tìm điểm giới hạn (x: Top, Bot hoặc y: Left, Right)
        int limitTop = (int) Math.round((-coeffC - coeffB * 0) / coeffA);
        int limitLeft = (int) Math.round((-coeffC - coeffA * 0) / coeffB);
        int limitBot = (int) Math.round((-coeffC - coeffB * (heightLimit)) / coeffA);
        int limitRight = (int) Math.round((-coeffC - coeffA * (widthLimit)) / coeffB);
        
        this.startPoint2D = null;
        this.endPoint2D = null;

        //xử lý trường hợp đặc biệt (Bot = 0, right = left) khi nằm ngang 180 độ
        if (limitRight == limitLeft && limitRight >= 0 && limitRight < heightLimit) {
            this.startPoint2D = new SKPoint2D(0, limitLeft);
            this.endPoint2D = new SKPoint2D(widthLimit, limitRight);
        }
        //Vào 2 trong 4
        if (limitBot >= 0 && limitBot <= widthLimit) {
            if (this.startPoint2D == null) {
                this.startPoint2D = new SKPoint2D(limitBot, heightLimit);
            } else if (this.endPoint2D == null) {
                this.endPoint2D = new SKPoint2D(limitBot, heightLimit);
            }
        }

        if (limitTop >= 0 && limitTop <= widthLimit) {
            if (this.startPoint2D == null) {
                this.startPoint2D = new SKPoint2D(limitTop, 0);
            } else if (this.endPoint2D == null) {
                this.endPoint2D = new SKPoint2D(limitTop, 0);
            }
        }

        if (limitLeft >= 0 && limitLeft <= heightLimit) {
            if (this.startPoint2D == null) {
                this.startPoint2D = new SKPoint2D(0, limitLeft);
            } else if (this.endPoint2D == null) {
                this.endPoint2D = new SKPoint2D(0, limitLeft);
            }
        }

        if (limitRight >= 0 && limitRight <= heightLimit) {
            if (this.startPoint2D == null) {
                this.startPoint2D = new SKPoint2D(widthLimit, limitRight);
            } else if (this.endPoint2D == null) {
                this.endPoint2D = new SKPoint2D(widthLimit, limitRight);
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

    public void saveEquationLine(double a, double b, double c){
        int coordX = (this.startPoint2D.getCoordX() + this.endPoint2D.getCoordX()) / 2;
        int coordY = (this.startPoint2D.getCoordY() + this.endPoint2D.getCoordY()) / 2;
        SKPoint2D savingPoint = new SKPoint2D(coordX, coordY);
        String plusB;
        if(b < 0) plusB = "-"; else plusB = "+";

        if (Ultility.checkValidPoint(changedCoordOfBoard, coordX, coordY)) {
            changedCoordOfBoard[coordY][coordX] = a + "x "+ plusB + Math.abs(b) + "y = " + (c);
        }
    }
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

    @Override
    public void createRotate(SKPoint2D centerPoint, double angle) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        double totalAngle = this.rotatedAngle + angle;

        SKPoint2D newStartPoint = startPoint2D.createRotate(centerPoint, totalAngle);
        SKPoint2D newEndPoint = endPoint2D.createRotate(centerPoint, totalAngle);

        Line2D line = new Line2D(markedChangeOfBoard,
                changedColorOfBoard, changedCoordOfBoard, this.filledColor);
        line.setLineStyle(this.lineStyle);
        line.setProperty(newStartPoint, newEndPoint);
        line.drawOutline();
        line.saveCoordinates();
    }

    @Override
    public void createSymOCenter() {
        if (pointSet2D.isEmpty()) {
            return;
        }

        SKPoint2D newStartPoint = startPoint2D.createOCenterSym();
        SKPoint2D newEndPoint = endPoint2D.createOCenterSym();

        Line2D line = new Line2D(markedChangeOfBoard,
                changedColorOfBoard, changedCoordOfBoard, this.filledColor);
        line.setLineStyle(this.lineStyle);
        line.setProperty(newStartPoint, newEndPoint);
        line.drawOutline();
        line.saveCoordinates();
    }

    @Override
    public void createSymOX() {
        if (pointSet2D.isEmpty()) {
            return;
        }

        SKPoint2D newStartPoint = startPoint2D.createOXSym();
        SKPoint2D newEndPoint = endPoint2D.createOXSym();

        Line2D line = new Line2D(markedChangeOfBoard,
                changedColorOfBoard, changedCoordOfBoard, this.filledColor);
        line.setLineStyle(this.lineStyle);
        line.setProperty(newStartPoint, newEndPoint);
        line.drawOutline();
        line.saveCoordinates();
    }

    @Override
    public void createSymOY() {
        if (pointSet2D.isEmpty()) {
            return;
        }

        SKPoint2D newStartPoint = startPoint2D.createOYSym();
        SKPoint2D newEndPoint = endPoint2D.createOYSym();

        Line2D line = new Line2D(markedChangeOfBoard,
                changedColorOfBoard, changedCoordOfBoard, this.filledColor);
        line.setLineStyle(this.lineStyle);
        line.setProperty(newStartPoint, newEndPoint);
        line.drawOutline();
        line.saveCoordinates();
    }

    @Override
    public void createSymPoint(SKPoint2D basePoint) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        SKPoint2D newStartPoint = startPoint2D.createPointSym(basePoint);
        SKPoint2D newEndPoint = endPoint2D.createPointSym(basePoint);

        Line2D line = new Line2D(markedChangeOfBoard,
                changedColorOfBoard, changedCoordOfBoard, this.filledColor);
        line.setLineStyle(this.lineStyle);
        line.setProperty(newStartPoint, newEndPoint);
        line.drawOutline();
        line.saveCoordinates();
    }

    @Override
    public void createSymLine(double a, double b, double c) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        SKPoint2D newStartPoint = startPoint2D.createLineSym(a, b, c);
        SKPoint2D newEndPoint = endPoint2D.createLineSym(a, b, c);

        Line2D line = new Line2D(markedChangeOfBoard,
                changedColorOfBoard, changedCoordOfBoard, this.filledColor);
        line.setLineStyle(this.lineStyle);
        line.setProperty(newStartPoint, newEndPoint);
        line.drawOutline();
        line.saveCoordinates();
    }

}
