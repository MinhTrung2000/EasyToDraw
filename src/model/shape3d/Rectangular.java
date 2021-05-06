package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import java.awt.Color;
import control.myawt.SKPoint3D;
import model.shape2d.Rectangle;
import model.shape2d.Vector2D;

/*
    A2        B2
  /         /
A --------- B
|           |
|           |
|  D2       | C2
|/          |/
D-----------C

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
        
        Rectangle.setFourPointSymmetricFromCenter(this.centerPoint3D.getCoordX(), 
                this.centerPoint3D.getCoordY(), this.centerPoint3D.getCoordZ() + height / 2, 
                width, high, pointA, pointB, pointC, pointD);
        
        Rectangle.setFourPointSymmetricFromCenter(this.centerPoint3D.getCoordX(),
                this.centerPoint3D.getCoordY(), this.centerPoint3D.getCoordZ() - height / 2,
                width, high, pointA2, pointB2, pointC2, pointD2);
    }
    
    @Override
    public void saveCoordinates() {
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
