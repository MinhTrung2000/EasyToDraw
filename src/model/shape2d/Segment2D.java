package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;
import java.util.ArrayList;

public class Segment2D extends Shape2D {

    public enum Modal {
        LINE_45_90_DEGREE,
        STRAIGHT_LINE
    }

    public enum UnchangedPoint {
        HEAD_POINT,
        FEET_POINT
    }

    public Segment2D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint, Modal modal) {
        if (modal == Modal.STRAIGHT_LINE) {
            this.startPoint2D = startPoint;
            this.endPoint2D = endPoint;
        } else {
            int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
            int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());

            int widthValue = Math.abs(width);
            int heightValue = Math.abs(height);

            int heightDirection = this.getDirectionHeight(height);
            int widthDirection = this.getDirectionWidth(width);
            int preferedLength = this.getPreferredLength(width, height);
            int remainingLength;
            
            if (preferedLength == widthValue) remainingLength = heightValue;
            else remainingLength = widthValue;
            
            this.startPoint2D = startPoint;

            double ratio = (double) widthValue / (double) heightValue;
            if (ratio <= 0.3) {//dọc
                this.endPoint2D.setLocation(this.startPoint2D.getCoordX(), this.startPoint2D.getCoordY() + heightDirection * remainingLength);
            } else if (ratio > 0.3 && ratio <= 1.5) {//chéo
                this.endPoint2D.setLocation(this.startPoint2D.getCoordX() + widthDirection * preferedLength, this.startPoint2D.getCoordY() + heightDirection * preferedLength);
            } else {//ngang
                this.endPoint2D.setLocation(this.startPoint2D.getCoordX() + widthDirection * remainingLength, this.startPoint2D.getCoordY());
            }

        }

        this.centerPoint2D.setLocation(
                (startPoint.getCoordX() + endPoint.getCoordX()) / 2,
                (startPoint.getCoordY() + endPoint.getCoordY()) / 2
        );
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        setProperty(startPoint, endPoint, Modal.STRAIGHT_LINE);
    }

    @Override
    public void drawOutline() {
        SKPoint2D tempStartPoint = startPoint2D.createRotate(centerPoint2D, rotatedAngle);
        SKPoint2D tempEndPoint = endPoint2D.createRotate(centerPoint2D, rotatedAngle);

        drawSegment(tempStartPoint, tempEndPoint, lineStyle);
    }

    /**
     * Move the line and change in place.
     *
     * @param vector
     */
    @Override
    public void applyMove(Vector2D vector) {
        startPoint2D.move(vector);
        endPoint2D.move(vector);
        centerPoint2D.move(vector);
    }

    @Override
    public void saveCoordinates() {
        this.startPoint2D.saveCoord(changedCoordOfBoard);
        this.endPoint2D.saveCoord(changedCoordOfBoard);
    }

    public SKPoint2D intersect(Segment2D other) {
        Vector2D vec_a_b = new Vector2D(this.startPoint2D, this.endPoint2D);
        Vector2D vec_c_d = new Vector2D(other.startPoint2D, other.endPoint2D);
        Vector2D vec_c_a = new Vector2D(other.startPoint2D, this.endPoint2D);

        Vector2D r = Vector2D.getVectorOfLinearEquationRepr(vec_a_b, vec_c_d, vec_c_a);

        // Two segments are not collided if the root is not in range 0..1
        if (Double.compare(r.getCoordX(), 0.0) > 0 && Double.compare(r.getCoordX(), 1.0) < 0
                && Double.compare(r.getCoordY(), 0.0) > 0 && Double.compare(r.getCoordY(), 1.0) < 0) {
            return null;
        }

        SKPoint2D result = new SKPoint2D(this.startPoint2D);
        result.move(vec_a_b.scale(r.getCoordX()));

        return result;
    }

    /**
     * Get set of points without drawing.
     *
     * @param startPoint
     * @param endPoint
     * @return
     */
    public static void mergePointSet(ArrayList<SKPoint2D> array, 
            SKPoint2D startPoint, SKPoint2D endPoint) {
        array.add(startPoint);

        int dx = 0, dy = 0;
        int incx = 0, incy = 0;
        int balance = 0;

        if (endPoint.getCoordX() >= startPoint.getCoordX()) {
            dx = (int) (endPoint.getCoordX() - startPoint.getCoordX());
            incx = 1;
        } else {
            dx = (int) (startPoint.getCoordX() - endPoint.getCoordX());
            incx = -1;
        }

        if (endPoint.getCoordY() >= startPoint.getCoordY()) {
            dy = (int) (endPoint.getCoordY() - startPoint.getCoordY());
            incy = 1;
        } else {
            dy = (int) (startPoint.getCoordY() - endPoint.getCoordY());
            incy = -1;
        }

        int x = (int) startPoint.getCoordX();
        int y = (int) startPoint.getCoordY();

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != (int) endPoint.getCoordX()) {
                array.add(new SKPoint2D(x, y));
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            array.add(new SKPoint2D(x, y));
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != (int) endPoint.getCoordY()) {
                array.add(new SKPoint2D(x, y));
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            array.add(new SKPoint2D(x, y));
        }
    }

    @Override
    public void createRotate(SKPoint2D centerPoint, double angle) {
        if (pointSet2D.isEmpty()) {
            return;
        }

        double totalAngle = this.rotatedAngle + angle;

        SKPoint2D newStartPoint = startPoint2D.createRotate(centerPoint, totalAngle);
        SKPoint2D newEndPoint = endPoint2D.createRotate(centerPoint, totalAngle);

        newStartPoint.saveCoord(changedCoordOfBoard);
        newEndPoint.saveCoord(changedCoordOfBoard);
        
        drawSegmentUnSave(newStartPoint, newEndPoint);
    }

    @Override
    public void createSymOCenter() {
        super.createSymOCenter();
        
        SKPoint2D newStartPoint = startPoint2D.createOCenterSym();
        SKPoint2D newEndPoint = endPoint2D.createOCenterSym();

        newStartPoint.saveCoord(changedCoordOfBoard);
        newEndPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymOX() {
        super.createSymOX();
        
        SKPoint2D newStartPoint = startPoint2D.createOXSym();
        SKPoint2D newEndPoint = endPoint2D.createOXSym();

        newStartPoint.saveCoord(changedCoordOfBoard);
        newEndPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymOY() {
        super.createSymOY();
        
        SKPoint2D newStartPoint = startPoint2D.createOYSym();
        SKPoint2D newEndPoint = endPoint2D.createOYSym();

        newStartPoint.saveCoord(changedCoordOfBoard);
        newEndPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymPoint(SKPoint2D basePoint) {
        super.createSymPoint(basePoint);
        
        SKPoint2D newStartPoint = startPoint2D.createPointSym(basePoint);
        SKPoint2D newEndPoint = endPoint2D.createPointSym(basePoint);

        newStartPoint.saveCoord(changedCoordOfBoard);
        newEndPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void createSymLine(double a, double b, double c) {
        super.createSymLine(a, b, c);

        SKPoint2D newStartPoint = startPoint2D.createLineSym(a, b, c);
        SKPoint2D newEndPoint = endPoint2D.createLineSym(a, b, c);

        newStartPoint.saveCoord(changedCoordOfBoard);
        newEndPoint.saveCoord(changedCoordOfBoard);
    }
}
