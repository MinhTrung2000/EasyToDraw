package model.shape3d;

import control.SettingConstants;
import java.awt.Color;
import control.myawt.SKPoint2D;
import model.shape2d.Vector2D;

/*
    A2        B2
  /         /
A --------- B
|           |
|           |
|  C2       | D2
|/          |/
C-----------D

*/

public class Rectangular extends Shape3D {
    
    private SKPoint2D pointA;
    private SKPoint2D pointB;
    private SKPoint2D pointC;
    private SKPoint2D pointD;
    
    private SKPoint2D pointA2;
    private SKPoint2D pointB2;
    private SKPoint2D pointC2;
    private SKPoint2D pointD2;
    
    public Rectangular(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        
        pointA = new SKPoint2D();
        pointB = new SKPoint2D();
        pointC = new SKPoint2D();
        pointD = new SKPoint2D();
        pointA2 = new SKPoint2D();
        pointB2 = new SKPoint2D();
        pointC2 = new SKPoint2D();
        pointD2 = new SKPoint2D();
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setProperty(SKPoint2D centerPoint, int width, int height, int high) {
        this.centerPoint.setLocation(centerPoint);
        
    }
    
    @Override
    public void saveCoordinates() {
        pointA.rotate(rotatedAngle).saveCoord(changedCoordOfBoard);
        pointB.rotate(rotatedAngle).saveCoord(changedCoordOfBoard);
        pointC.rotate(rotatedAngle).saveCoord(changedCoordOfBoard);
        pointD.rotate(rotatedAngle).saveCoord(changedCoordOfBoard);
        pointA2.rotate(rotatedAngle).saveCoord(changedCoordOfBoard);
        pointB2.rotate(rotatedAngle).saveCoord(changedCoordOfBoard);
        pointC2.rotate(rotatedAngle).saveCoord(changedCoordOfBoard);
        pointD2.rotate(rotatedAngle).saveCoord(changedCoordOfBoard);
    }

    @Override
    public void applyMove(Vector2D vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        drawSegment(pointD, pointD2, SettingConstants.LineStyle.DASH);
        drawSegment(pointA2, pointD2, SettingConstants.LineStyle.DASH);
        drawSegment(pointD2, pointC2, SettingConstants.LineStyle.DASH);
    }
    
}
