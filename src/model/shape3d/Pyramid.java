package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint3D;
import java.awt.Color;
import model.shape2d.Vector2D;

/*

            (Top)
            *
           *
          *
         *
       ...
       ...
                
            (center)
              |  
        D     v       C
       /      *      /
     A--------------B
*/

public class Pyramid extends Shape3D {

    private SKPoint3D pointTop = new SKPoint3D();
    private SKPoint3D pointA = new SKPoint3D();
    private SKPoint3D pointB = new SKPoint3D();
    private SKPoint3D pointC = new SKPoint3D();
    private SKPoint3D pointD = new SKPoint3D();
    
    public Pyramid(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }
    
    public void setProperty(double center_x, double center_y, double center_z, double edge, double high) {
        double half_edge = edge / 2;
        
        centerPoint3D.setLocation(center_x, center_y, center_z);
        
        pointTop.setLocation(centerPoint3D.getCoordX(), centerPoint3D.getCoordY(), centerPoint3D.getCoordZ() + high);
        pointA.setLocation(centerPoint3D.getCoordX() - half_edge, centerPoint3D.getCoordY() + half_edge, centerPoint3D.getCoordZ());
        pointB.setLocation(centerPoint3D.getCoordX() + half_edge, centerPoint3D.getCoordY() + half_edge, centerPoint3D.getCoordZ());
        pointC.setLocation(centerPoint3D.getCoordX() + half_edge, centerPoint3D.getCoordY() - half_edge, centerPoint3D.getCoordZ());
        pointD.setLocation(centerPoint3D.getCoordX() - half_edge, centerPoint3D.getCoordY() - half_edge, centerPoint3D.getCoordZ());
    }

    @Override
    public void drawOutline() {
        drawSegment(pointTop, pointA, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointTop, pointB, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointTop, pointC, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointTop, pointD, SettingConstants.LineStyle.DOT);
        
        drawSegment(pointA, pointB, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointB, pointC, SettingConstants.LineStyle.DEFAULT);
        drawSegment(pointC, pointD, SettingConstants.LineStyle.DOT);
        drawSegment(pointD, pointA, SettingConstants.LineStyle.DOT);
    }

    @Override
    public void saveCoordinates() {
        pointTop.saveCoord(changedCoordOfBoard);
        pointA.saveCoord(changedCoordOfBoard);
        pointB.saveCoord(changedCoordOfBoard);
        pointC.saveCoord(changedCoordOfBoard);
        pointD.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void applyMove(Vector2D vector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
