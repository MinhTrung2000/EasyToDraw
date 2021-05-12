package model.shape2d;

import control.myawt.SKPoint2D;
import java.awt.Color;
import java.util.ArrayList;

public class Ellipse extends Shape2D {

    public enum Modal {
        ELLIPSE,
        CIRLCE,
    }

    private double majorRadius = 1.0;
    private double minorRadius = 1.0;

    private Modal modal = Modal.ELLIPSE;

    public Ellipse(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setModal(Modal modal) {
        this.modal = modal;
    }

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint, Modal modal) {

        int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
        int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());

        double half_x = Math.abs(width) / 2.0;
        double half_y = Math.abs(height) / 2.0;

        if (modal == Modal.ELLIPSE) {
            this.startPoint2D.setLocation(startPoint);
            this.endPoint2D.setLocation(endPoint);
            majorRadius = half_x;
            minorRadius = half_y;

        } else {
            int preferedLength = this.getPreferredLength(width, height);
            int widthDirection = this.getDirectionWidth(width);
            int heightDirection = this.getDirectionHeight(height);

            this.startPoint2D.setLocation(startPoint);
            this.endPoint2D.setLocation(startPoint.getCoordX()
                    + widthDirection * preferedLength, startPoint.getCoordY()
                    + heightDirection * preferedLength);
            majorRadius = preferedLength / 2;
            minorRadius = majorRadius;
        }

        centerPoint2D.setMidLocation(this.startPoint2D, this.endPoint2D);
        this.modal = modal;
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        setProperty(startPoint, endPoint, Modal.ELLIPSE);
    }

    public void drawOutlineEllipse() {
        super.drawOutlineEllipse(this.majorRadius, this.minorRadius,
                this.centerPoint2D, true, true, true, true);

        saveCoordinates();
    }

    public void drawOutlineCircle() {
        super.drawOutlineCircle(this.majorRadius, this.centerPoint2D,
                true, true, true, true, true, true, true, true);

        saveCoordinates();
    }

    @Override
    public void drawOutline() {
        if (modal == Modal.ELLIPSE) {
            drawOutlineEllipse();
        } else {
            drawOutlineCircle();
        }
    }

    @Override
    public void saveCoordinates() {
        centerPoint2D.saveCoord(changedCoordOfBoard);
        savePoint(centerPoint2D);
    }

    @Override
    public void applyMove(Vector2D vector) {
        centerPoint2D.move(vector);
    }

    public static ArrayList<SKPoint2D> getSetOfAllPointsEllipse(SKPoint2D centerPoint,
            double majorRadius, double minorRadius, boolean quadrant1,
            boolean quadrant2, boolean quadrant3, boolean quadrant4) {
        ArrayList<SKPoint2D> ret = new ArrayList<>();

        double x = 0.0;
        double y = minorRadius;

        double fx = 0;
        double fy = 2 * majorRadius * majorRadius * y;

        addFourSymPoints(ret, x, y, centerPoint.getCoordX(),
                centerPoint.getCoordY(), quadrant1, quadrant2,
                quadrant3, quadrant4);

        double p = minorRadius * minorRadius - majorRadius * majorRadius
                * minorRadius + majorRadius * majorRadius * 0.25;

        while (fx < fy) {
            x++;
            fx += 2 * minorRadius * minorRadius;
            if (p < 0) {
                p += minorRadius * minorRadius * (2 * x + 3);
            } else {
                p += minorRadius * minorRadius * (2 * x + 3)
                        + majorRadius * majorRadius * (-2 * y + 2);
                y--;
                fy -= 2 * majorRadius * majorRadius;
            }

            addFourSymPoints(ret, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), quadrant1, quadrant2,
                    quadrant3, quadrant4);
        }

        p = minorRadius * minorRadius * (x + 0.5) * (x + 0.5) + majorRadius
                * majorRadius * (y - 1.0) * (y - 1.0) - majorRadius * majorRadius
                * minorRadius * minorRadius;

        while (y >= 0) {
            y--;
            if (p < 0) {
                p += minorRadius * minorRadius * (2 * x + 2) + majorRadius * majorRadius * (-2 * y + 3);
                x++;
            } else {
                p += majorRadius * majorRadius * (3 - 2 * y);
            }

            addFourSymPoints(ret, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), quadrant1, quadrant2,
                    quadrant3, quadrant4);
        }

        return ret;
    }

    public static ArrayList<SKPoint2D> getSetOfAllPointsCircle(SKPoint2D centerPoint,
            double radius, boolean rightTopPart1, boolean octant1, boolean octant2,
            boolean octant3, boolean octant4, boolean octant5, boolean octant6,
            boolean octant7, boolean octant8) {
        ArrayList<SKPoint2D> ret = new ArrayList<>();

        double x = 0;
        double y = radius;

        addEightSymPoints(ret, x, y, centerPoint.getCoordX(),
                centerPoint.getCoordY(), octant1, octant2, octant3, octant4,
                octant5, octant6, octant7, octant8);

        double p = 5 / 4.0 - radius;

        while (x < y) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
            addEightSymPoints(ret, x, y, centerPoint.getCoordX(),
                    centerPoint.getCoordY(), octant1, octant2, octant3, octant4,
                    octant5, octant6, octant7, octant8);
        }

        return ret;
    }

    @Override
    public void createRotate(SKPoint2D centerPoint, double angle) {
        super.createRotate(centerPoint, angle);

        double totalAngle = rotatedAngle + angle;

        SKPoint2D newCenterPoint = this.centerPoint2D.createRotate(centerPoint, totalAngle);

        newCenterPoint.saveCoord(changedCoordOfBoard);
        savePoint(newCenterPoint);
    }

    @Override
    public void createSymOCenter() {
        super.createSymOCenter();

        SKPoint2D newCenterPoint = this.centerPoint2D.createOCenterSym();

        newCenterPoint.saveCoord(changedCoordOfBoard);
        savePoint(newCenterPoint);
    }

    @Override
    public void createSymOX() {
        super.createSymOX();

        SKPoint2D newCenterPoint = this.centerPoint2D.createOXSym();

        newCenterPoint.saveCoord(changedCoordOfBoard);
        savePoint(newCenterPoint);
    }

    @Override
    public void createSymOY() {
        super.createSymOY();

        SKPoint2D newCenterPoint = this.centerPoint2D.createOYSym();

        newCenterPoint.saveCoord(changedCoordOfBoard);
        savePoint(newCenterPoint);
    }

    @Override
    public void createSymPoint(SKPoint2D basePoint) {
        super.createSymPoint(basePoint);

        SKPoint2D newCenterPoint = this.centerPoint2D.createPointSym(basePoint);

        newCenterPoint.saveCoord(changedCoordOfBoard);
        savePoint(newCenterPoint);
    }

    @Override
    public void createSymLine(double majorRadius, double minorRadius, double c) {
        super.createSymLine(majorRadius, minorRadius, c);

        SKPoint2D newCenterPoint = this.centerPoint2D.createLineSym(majorRadius, minorRadius, c);

        newCenterPoint.saveCoord(changedCoordOfBoard);
        savePoint(newCenterPoint);
    }

}
