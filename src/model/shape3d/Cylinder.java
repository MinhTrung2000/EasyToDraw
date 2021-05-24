package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.myawt.SKPoint3D;
import control.util.Ultility;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import model.shape2d.Ellipse;
import model.shape2d.Vector2D;

public class Cylinder extends Shape3D {

    private Ellipse circle = new Ellipse(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    private ArrayList<SKPoint2D> circlePointList = new ArrayList<>();

    private double radius = 0.0;
    private double high = 0.0;
    
    private SKPoint3D topCenter;
    private SKPoint3D botCenter;
    
    public Cylinder(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(double center_x, double center_y, double center_z, 
            double radius, double high) {
        this.centerPoint3D.setLocation(center_x, center_y, center_z);
        this.radius = radius;
        this.high = high;

        circle.setProperty(new SKPoint2D(center_x - radius, center_y + radius),
                new SKPoint2D(center_x + radius, center_y - radius)
        );

        circle.setModal(Ellipse.Modal.CIRLCE);

        circlePointList.clear();

        mergePointSetCircle(circlePointList, centerPoint3D, this.radius, true, true, 
                true, true, true, true, true, true);
        
        
        double half_high = this.high / 2;

        pointSet3D.clear();
        
        for (int i = 0; i < circlePointList.size(); i++) {
            SKPoint2D p2d = circlePointList.get(i);
            pointSet3D.add(new SKPoint3D(p2d.getCoordX(), p2d.getCoordY(), 
                    this.centerPoint3D.getCoordZ() + half_high));          
        }
        circlePointList.clear();
        merPointSetCircleS(circlePointList, centerPoint3D, this.radius);
        
        for (int i = 0; i < circlePointList.size(); i++) {
            SKPoint2D p2d = circlePointList.get(i);
            pointSet3D.add(new SKPoint3D(p2d.getCoordX(), p2d.getCoordY(), 
                    this.centerPoint3D.getCoordZ() - half_high));          
        }

        pointSet2D.clear();
        
        for (int i = 0; i < pointSet3D.size(); i++) {
            pointSet2D.add(pointSet3D.get(i).get2DRelativePosition());
        }
        
        topCenter = new SKPoint3D(centerPoint3D.getCoordX(),centerPoint3D.getCoordY(), 
                centerPoint3D.getCoordZ()- this.high/2);
        botCenter = new SKPoint3D(centerPoint3D.getCoordX(),centerPoint3D.getCoordY(), 
                centerPoint3D.getCoordZ()+ this.high/2);
        
        
        pointSet2D.add(topCenter.get2DRelativePosition());
        pointSet2D.add(botCenter.get2DRelativePosition());
        
    }

    private void customSaveCoord(SKPoint2D pos, SKPoint3D coord, String[][] coordOfBoard){
         if (Ultility.checkValidPoint(coordOfBoard, pos.getCoordX(), pos.getCoordY())){
             coordOfBoard[pos.getCoordY()][pos.getCoordX()] = "(" + coord.getCoordX() + ", " + coord.getCoordY() + ", " + coord.getCoordZ() + ")";
         }
    }
    
    @Override
    public void drawOutline() {
        super.drawOutline();
        
        SKPoint2D leftTopPointToDraw = getLeftTopPoint();
        SKPoint2D rightTopPointToDraw = getRightTopPoint();
        SKPoint2D rightBottomPointToDraw = getRightBottomPoint();
        SKPoint2D leftBottomPointToDraw = getLeftBottomPoint();
        
        SKPoint3D leftTopPoint = new SKPoint3D(this.centerPoint3D.getCoordX() - radius , this.centerPoint3D.getCoordY()
        ,this.centerPoint3D.getCoordZ() + this.high/2 );
        
        customSaveCoord(leftTopPointToDraw, leftTopPoint, changedCoordOfBoard);
        
        
        SKPoint3D leftBottomPoint = new SKPoint3D(this.centerPoint3D.getCoordX() - radius , this.centerPoint3D.getCoordY()
        ,this.centerPoint3D.getCoordZ() - this.high/2 );
        
        customSaveCoord(leftBottomPointToDraw, leftBottomPoint, changedCoordOfBoard);
        
        SKPoint3D rightTopPoint = new SKPoint3D(this.centerPoint3D.getCoordX() + radius , this.centerPoint3D.getCoordY()
        ,this.centerPoint3D.getCoordZ() + this.high/2 );
        
        customSaveCoord(rightTopPointToDraw, rightTopPoint, changedCoordOfBoard);
        
        SKPoint3D rightBottomPoint = new SKPoint3D(this.centerPoint3D.getCoordX() + radius , this.centerPoint3D.getCoordY()
        ,this.centerPoint3D.getCoordZ() - this.high/2 );
        
        customSaveCoord(rightBottomPointToDraw, rightBottomPoint, changedCoordOfBoard);
            
        drawSegmentUnSave(leftTopPointToDraw, leftBottomPointToDraw);
        drawSegmentUnSave(rightTopPointToDraw, rightBottomPointToDraw);
        
        
        
    }

    @Override
    public void applyMove(Vector2D vector) {
    }
    
    @Override
    public void saveCoordinates(){
        super.saveCoordinates();
        topCenter.saveCoord(changedCoordOfBoard);
        botCenter.saveCoord(changedCoordOfBoard);
        
    }

}
