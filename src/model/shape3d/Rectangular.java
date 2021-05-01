package model.shape3d;

import control.SettingConstants;
import java.awt.Color;
import model.shape2d.Point2D;
import model.shape2d.Vector2D;

public class Rectangular extends Shape3D {
    
    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;
    private Point2D pointD;
    
    private Point2D pointA2;
    private Point2D pointB2;
    private Point2D pointC2;
    private Point2D pointD2;
    
    public Rectangular(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
        
        pointA = new Point2D();
        pointB = new Point2D();
        pointC = new Point2D();
        pointD = new Point2D();
        pointA2 = new Point2D();
        pointB2 = new Point2D();
        pointC2 = new Point2D();
        pointD2 = new Point2D();
    }

    @Override
    public void setProperty(Point2D startPoint, Point2D endPoint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setProperty(Point2D centerPoint, int width, int height, int high) {
        this.centerPoint.setCoord(centerPoint);
        
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
