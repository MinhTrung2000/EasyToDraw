package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.myawt.SKPoint3D;
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
            pointSet3D.add(new SKPoint3D(p2d.getCoordX(), p2d.getCoordY(), 
                    this.centerPoint3D.getCoordZ() - half_high));
        }

        pointSet2D.clear();
        
        for (int i = 0; i < pointSet3D.size(); i++) {
            pointSet2D.add(pointSet3D.get(i).get2DRelativePosition());
        }
    }

    @Override
    public void drawOutline() {
        super.drawOutline();
        
        SKPoint2D leftTopPoint = getLeftTopPoint();
        SKPoint2D rightTopPoint = getRightTopPoint();
        SKPoint2D rightBottomPoint = getRightBottomPoint();
        SKPoint2D leftBottomPoint = getLeftBottomPoint();
        
        drawSegmentUnSave(leftTopPoint, leftBottomPoint);
        drawSegmentUnSave(rightTopPoint, rightBottomPoint);
        
        leftTopPoint.saveCoord(changedCoordOfBoard);
        rightTopPoint.saveCoord(changedCoordOfBoard);
        rightBottomPoint.saveCoord(changedCoordOfBoard);
        leftBottomPoint.saveCoord(changedCoordOfBoard);
    }

    @Override
    public void applyMove(Vector2D vector) {
    }

}
