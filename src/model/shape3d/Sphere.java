package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import java.awt.Color;
import model.shape2d.Vector2D;

public class Sphere extends Shape3D {

    private double radius;

    public Sphere(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(double center_x, double center_y, double center_z, double radius) {
        centerPoint3D.setLocation(center_x, center_y, center_z);
        this.radius = radius;
    }

    private void put_x_z(double x, double z, double center_x, double center_z) {
        savePoint(z + center_x, centerPoint3D.getCoordY(), -x + center_z);
        savePoint(x + center_x, centerPoint3D.getCoordY(), -z + center_z);
        savePoint(-z + center_x, centerPoint3D.getCoordY(), x + center_z);
        savePoint(-x + center_x, centerPoint3D.getCoordY(), z + center_z);

        savePoint(z + center_x, centerPoint3D.getCoordY(), x + center_z);
        savePoint(x + center_x, centerPoint3D.getCoordY(), z + center_z);
        savePoint(-x + center_x, centerPoint3D.getCoordY(), -z + center_z);
        savePoint(-z + center_x, centerPoint3D.getCoordY(), -x + center_z);
    }

    @Override
    public void drawOutline() {
        drawXZCircle();
        drawVirtualEllipse();
    }

    private void drawXZCircle() {
        double x = 0;
        double z = radius;

        pixelCounter = 1;
        put_x_z(x, z, centerPoint2D.getCoordX(), centerPoint2D.getCoordZ());

        double p = 5 / 4.0 - radius;

        while (x < z) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - z) + 5;
                z--;
            }
            x++;
            put_x_z(x, z, centerPoint2D.getCoordX(), centerPoint2D.getCoordZ());
        }
    }

    private void drawVirtualEllipse() {
        SKPoint2D relativeCenterPoint = centerPoint3D.get2DRelativePosition().convertViewToMachineCoord();
        relativeCenterPoint.saveCoord(changedCoordOfBoard);
        
        radius /= SettingConstants.RECT_SIZE;

        double a = radius;
        double b = radius / 2;

        double x = 0.0;
        double y = b;

        double fx = 0;
        double fy = 2 * a * a * y;

        pixelCounter = 0;

        putFourSymmetricPoints((int) x, (int) y, relativeCenterPoint.getCoordX(), relativeCenterPoint.getCoordY(), true);

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
            putFourSymmetricPoints((int) x, (int) y, relativeCenterPoint.getCoordX(), relativeCenterPoint.getCoordY(), true);
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
            putFourSymmetricPoints((int) x, (int) y, relativeCenterPoint.getCoordX(), relativeCenterPoint.getCoordY(), true);
        }
    }

    @Override
    public void saveCoordinates() {
    }

    @Override
    public void applyMove(Vector2D vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
