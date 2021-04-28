package model.shape2d;

import java.awt.Color;
import java.util.ArrayList;

public class Ellipse extends Shape2D {

    public enum Modal {
        ELLIPSE,
        CIRLCE,
    }

    private double a;
    private double b;

    private Modal modal;

    public Ellipse(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        a = b = 1.0;
        modal = Modal.ELLIPSE;
    }

    public void setProperty(Point2D startPoint, Point2D endPoint, Modal modal) {
        int width = endPoint.coordX - startPoint.coordX;
        int height = endPoint.coordY - startPoint.coordY;
        double half_x = Math.abs(width) / 2.0;
        double half_y = Math.abs(height) / 2.0;

        if (modal == Modal.ELLIPSE) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            a = half_x;
            b = half_y;

        } else {

            int preferedLength = this.getPreferredLength(width, height);
            int widthDirection = this.getWidthDirection(width);
            int heightDirection = this.getHeightDirection(height);

            this.startPoint = startPoint;
            this.endPoint.setCoord(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);
            a = preferedLength / 2;
            b = a;
        }

        centerPoint.setCoord(
                (int) Math.round(this.startPoint.coordX + this.endPoint.coordX) / 2,
                (int) Math.round(this.startPoint.coordY + this.endPoint.coordY) / 2
        );

        this.modal = modal;
    }

    @Override
    public void setProperty(Point2D startPoint, Point2D endPoint) {
        setProperty(startPoint, endPoint, Modal.ELLIPSE);
    }

    private void drawOutlineEllipse() {
        pointSet.clear();

        // Save center point coordination
        centerPoint.saveCoord(changedCoordOfBoard);
        
        double x = 0.0;
        double y = b;

        double fx = 0;
        double fy = 2 * a * a * y;

        pixelCounter = 1;
        putFourSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);

        double p = b * b - a * a * b + a * a * 0.25;

        while (fx < fy) {
            x++;
            fx += 2 * b * b;
            if (p < 0) {
                p += b * b * (2 * x + 3);
            } else {
                p += b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                y--;
                fy -= 2 * a * a;
            }
            pixelCounter++;
            putFourSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);
        }

        p = b * b * (x + 0.5) * (x + 0.5) + a * a * (y - 1.0) * (y - 1.0) - a * a * b * b;

        while (y >= 0) {
            y--;
            if (p < 0) {
                p += b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
            } else {
                p += a * a * (3 - 2 * y);
            }
            pixelCounter++;
            putFourSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);
        }
    }

    private void drawOutlineCircle() {
        pointSet.clear();

        // Save center point coordination
        centerPoint.saveCoord(changedCoordOfBoard);
        
        double x = 0;
        double y = a;

        pixelCounter = 1;
        pointSet.add(new Point2D((int) x, (int) y));
        putEightSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);

        double p = 5 / 4.0 - a;

        while (x < y) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
            pixelCounter++;
            putEightSymmetricPoints((int) x, (int) y, centerPoint.coordX, centerPoint.coordY);
        }
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
        centerPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void drawVirtualMove(Vector2D vector) {
        if (pointSet.size() == 0) {
            return;
        }

        for (int i = 0; i < pointSet.size(); i++) {
            Point2D pt = pointSet.get(i).createMovingPoint(vector);
            savePoint(pt.getCoordX(), pt.getCoordY());
        }
    }

    @Override
    public void applyMove(Vector2D vector) {
        centerPoint.move(vector);
    }

    @Override
    public void drawOXSymmetry() {
        if (pointSet.size() == 0) {
            return;
        }

        Point2D tempCenterPoint = this.centerPoint.createOXSymmetryPoint();

        pixelCounter = 0;
        for (int i = 0; i < pointSet.size(); i++) {
            Point2D pt = pointSet.get(i).createOXSymmetryPoint();
            pixelCounter++;
            putEightSymmetricPoints(pt.coordX, pt.coordY, tempCenterPoint.coordX, tempCenterPoint.coordY);
        }
    }

    @Override
    public void drawOYSymmetry() {
        if (pointSet.size() == 0) {
            return;
        }

        Point2D tempCenterPoint = this.centerPoint.createOYSymmetryPoint();

        pixelCounter = 0;
        for (int i = 0; i < pointSet.size(); i++) {
            Point2D pt = pointSet.get(i).createOYSymmetryPoint();
            pixelCounter++;
            putEightSymmetricPoints(pt.coordX, pt.coordY, tempCenterPoint.coordX, tempCenterPoint.coordY);
        }
    }

    @Override
    public void drawLineSymmetry(double a, double b, double c) {
        if (pointSet.size() == 0) {
            return;
        }

        Point2D tempCenterPoint = this.centerPoint.createLineSymmetryPoint(a, b, c);

        pixelCounter = 0;
        for (int i = 0; i < pointSet.size(); i++) {
            Point2D pt = pointSet.get(i).createLineSymmetryPoint(a, b, c);
            pixelCounter++;
            putEightSymmetricPoints(pt.coordX, pt.coordY, tempCenterPoint.coordX, tempCenterPoint.coordY);
        }
    }

    @Override
    public void drawPointSymmetry(Point2D basePoint) {
        if (pointSet.size() == 0) {
            return;
        }

        Point2D tempCenterPoint = this.centerPoint.createSymmetryPoint(basePoint);

        pixelCounter = 0;
        for (int i = 0; i < pointSet.size(); i++) {
            Point2D pt = pointSet.get(i).createSymmetryPoint(basePoint);
            pixelCounter++;
            putEightSymmetricPoints(pt.coordX, pt.coordY, tempCenterPoint.coordX, tempCenterPoint.coordY);
        }
    }
}
