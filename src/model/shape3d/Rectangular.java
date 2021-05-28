package model.shape3d;

import control.SettingConstants;
import java.awt.Color;
import control.myawt.SKPoint3D;
import model.shape2d.Rectangle;
import model.shape2d.Vector2D;

/*
    z
    |
    |
  O +--------------x
   /
  /
  y
   
   A2        B2
  /         /
A --------- B
|           |
|           |
|  D2       | C2
|/          |/
D-----------C

DC: width
CC2: height
BC: high
 */
public class Rectangular extends Shape3D {

    private SKPoint3D pointA = new SKPoint3D();
    private SKPoint3D pointB = new SKPoint3D();
    private SKPoint3D pointC = new SKPoint3D();
    private SKPoint3D pointD = new SKPoint3D();

    private SKPoint3D pointA2 = new SKPoint3D();
    private SKPoint3D pointB2 = new SKPoint3D();
    private SKPoint3D pointC2 = new SKPoint3D();
    private SKPoint3D pointD2 = new SKPoint3D();

    public Rectangular(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(double center_x, double center_y, double center_z, int width, int height, int high) {
        this.centerPoint3D.setLocation(center_x, center_y, center_z);

        double half_width = width / 2;
        double half_height = height / 2;
        double half_hight = high / 2;

        // Set location in visual system coordinate mode.
        pointA.setLocation(center_x - half_width, center_y + half_height,
                center_z + half_hight);
        pointB.setLocation(center_x + half_width, center_y + half_height,
                center_z + half_hight);
        pointC.setLocation(center_x + half_width, center_y + half_height,
                center_z - half_hight);
        pointD.setLocation(center_x - half_width, center_y + half_height,
                center_z - half_hight);

        pointA2.setLocation(center_x - half_width, center_y - half_height,
                center_z + half_hight);
        pointB2.setLocation(center_x + half_width, center_y - half_height,
                center_z + half_hight);
        pointC2.setLocation(center_x + half_width, center_y - half_height,
                center_z - half_hight);
        pointD2.setLocation(center_x - half_width, center_y - half_height,
                center_z - half_hight);

    }

    @Override
    public void saveCoordinates() {
        super.saveCoordinates();
        pointA.saveCoord(changedCoordOfBoard);
        pointB.saveCoord(changedCoordOfBoard);
        pointC.saveCoord(changedCoordOfBoard);
        pointD.saveCoord(changedCoordOfBoard);
        pointA2.saveCoord(changedCoordOfBoard);
        pointB2.saveCoord(changedCoordOfBoard);
        pointC2.saveCoord(changedCoordOfBoard);
        pointD2.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void applyMove(Vector2D vector) {
    }

    @Override
    public void drawOutline() {
        drawSegment(pointA, pointB, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointB, pointC, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointC, pointD, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointD, pointA, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointA, pointA2, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointA2, pointB2, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointB2, pointB, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointB2, pointC2, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointC2, pointC, SettingConstants.LineStyle.DEFAULT);

        drawSegment(pointD, pointD2, SettingConstants.LineStyle.DOT);
        drawSegment(pointA2, pointD2, SettingConstants.LineStyle.DOT);
        drawSegment(pointD2, pointC2, SettingConstants.LineStyle.DOT);
    }

}
