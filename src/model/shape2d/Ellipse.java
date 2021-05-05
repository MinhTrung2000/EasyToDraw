package model.shape2d;

import control.myawt.SKPoint2D;
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

    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint, Modal modal) {
        int width = (int) (endPoint.getCoordX() - startPoint.getCoordX());
        int height = (int) (endPoint.getCoordY() - startPoint.getCoordY());

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
            this.endPoint.setLocation(startPoint.getCoordX() + widthDirection * preferedLength, startPoint.getCoordY() + heightDirection * preferedLength);
            a = preferedLength / 2;
            b = a;
        }

        centerPoint.setLocation(
                (int) Math.round(this.startPoint.getCoordX() + this.endPoint.getCoordX()) / 2,
                (int) Math.round(this.startPoint.getCoordY() + this.endPoint.getCoordY()) / 2
        );

        this.modal = modal;
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        setProperty(startPoint, endPoint, Modal.ELLIPSE);
    }

    private void drawOutlineEllipse() {
        pointSet.clear();

        // Save center point coordination
        centerPoint.saveCoord(changedCoordOfBoard);
        markedChangeOfBoard[(int) centerPoint.getCoordY()][(int) centerPoint.getCoordX()] = true;
        changedColorOfBoard[(int) centerPoint.getCoordY()][(int) centerPoint.getCoordX()] = filledColor;

        double x = 0.0;
        double y = b;

        double fx = 0;
        double fy = 2 * a * a * y;

        pixelCounter = 1;
        putFourSymmetricPoints((int) x, (int) y, centerPoint.getCoordX(), centerPoint.getCoordY());

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
            putFourSymmetricPoints((int) x, (int) y, centerPoint.getCoordX(), centerPoint.getCoordY());
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
            putFourSymmetricPoints((int) x, (int) y, centerPoint.getCoordX(), centerPoint.getCoordY());
        }
    }

    private void drawOutlineCircle() {
        pointSet.clear();

        // Save center point coordination
        centerPoint.saveCoord(changedCoordOfBoard);
        markedChangeOfBoard[(int) centerPoint.getCoordY()][(int) centerPoint.getCoordX()] = true;
        changedColorOfBoard[(int) centerPoint.getCoordY()][(int) centerPoint.getCoordX()] = filledColor;

        double x = 0;
        double y = a;

        pixelCounter = 1;
        pointSet.add(new SKPoint2D((int) x, (int) y));
        putEightSymmetricPoints(x, y, centerPoint.getCoordX(), centerPoint.getCoordY());

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
            putEightSymmetricPoints((int) x, (int) y, centerPoint.getCoordX(), centerPoint.getCoordY());
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
    public void applyMove(Vector2D vector) {
        centerPoint.move(vector);
    }

}
