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

    private Ellipse Circle = new Ellipse(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    
    private double radius = 0.0;
    private double high = 0.0;

    public Cylinder(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(double center_x, double center_y, double center_z, double radius, double high) {
        this.centerPoint3D.setLocation(center_x, center_y, center_z);
        this.radius = radius;
        this.high = high;

        Circle.setProperty(new SKPoint2D(center_x - radius, center_y + radius),
                new SKPoint2D(center_x + radius, center_y - radius)
        );

        Circle.setModal(Ellipse.Modal.CIRLCE);
    }

    @Override
    public void drawOutline() {
//        double half_high = high / 2;
//
//        ArrayList<SKPoint2D> topPointList = Circle.getSetOfAllPoints(true, false);
//        ArrayList<SKPoint2D> bottomPointList = Circle.getSetOfAllPoints(false, true);
//        
//        Color shapeColor = new Color(225, 239, 252);
//
//        setFilledColor(shapeColor);
//        for (int k = 0; k < half_high - 1; k++) {
//            for (int i = 0; i < topPointList.size(); i++) {
//                SKPoint2D p = topPointList.get(i);
//                SKPoint2D p1 = SKPoint3D.get2DRelativePosition(p.getCoordX(), 
//                        p.getCoordY(), centerPoint3D.getCoordZ() + k)
//                        .convertToSystemCoord();
//                savePoint(p1.getCoordX(), p1.getCoordY());
//                SKPoint2D p2 = SKPoint3D.get2DRelativePosition(p.getCoordX(), 
//                        p.getCoordY(), centerPoint3D.getCoordZ() - k)
//                        .convertToSystemCoord();
//                savePoint(p2.getCoordX(), p2.getCoordY());
//            }
//            for (int i = 0; i < bottomPointList.size(); i++) {
//                SKPoint2D p = bottomPointList.get(i);
//                SKPoint2D p1 = SKPoint3D.get2DRelativePosition(p.getCoordX(), 
//                        p.getCoordY(), centerPoint3D.getCoordZ() + k)
//                        .convertToSystemCoord();
//                savePoint(p1.getCoordX(), p1.getCoordY());
//                SKPoint2D p2 = SKPoint3D.get2DRelativePosition(p.getCoordX(), 
//                        p.getCoordY(), centerPoint3D.getCoordZ() - k)
//                        .convertToSystemCoord();
//                savePoint(p2.getCoordX(), p2.getCoordY());
//            }
//        }
//
//        setFilledColor(Color.BLACK);
//        for (int i = 0; i < topPointList.size(); i++) {
//            SKPoint2D p = topPointList.get(i);
//            SKPoint2D p1 = SKPoint3D.get2DRelativePosition(p.getCoordX(), 
//                    p.getCoordY(), centerPoint3D.getCoordZ() + half_high)
//                    .convertToSystemCoord();
//            savePoint(p1.getCoordX(), p1.getCoordY());
//        }
//        for (int i = 0; i < bottomPointList.size(); i++) {
//            SKPoint2D p = bottomPointList.get(i);
//            SKPoint2D p1 = SKPoint3D.get2DRelativePosition(p.getCoordX(), 
//                    p.getCoordY(), centerPoint3D.getCoordZ() + half_high)
//                    .convertToSystemCoord();
//            savePoint(p1.getCoordX(), p1.getCoordY());
//        }
//        topPointList.sort(new Comparator<SKPoint2D>() {
//            @Override
//            public int compare(SKPoint2D o1, SKPoint2D o2) {
//                return (o1.getCoordX() > o2.getCoordX() ? 1 : 
//                        (o1.getCoordX() == o2.getCoordX() ? 0 : -1));
//            }
//        });
//        for (int i = 0; i < topPointList.size(); i++) {
//            SKPoint2D p = topPointList.get(i);
//            SKPoint2D p1 = SKPoint3D.get2DRelativePosition(p.getCoordX(), 
//                    p.getCoordY(), centerPoint3D.getCoordZ() - half_high)
//                    .convertToSystemCoord();
//            if (p1.getCoordX() % 3 == 0) {
//                continue;
//            }
//            savePoint(p1.getCoordX(), p1.getCoordY());
//        }
//        for (int i = 0; i < bottomPointList.size(); i++) {
//            SKPoint2D p = bottomPointList.get(i);
//            SKPoint2D p1 = SKPoint3D.get2DRelativePosition(p.getCoordX(), 
//                    p.getCoordY(), centerPoint3D.getCoordZ() - half_high)
//                    .convertToSystemCoord();
//            savePoint(p1.getCoordX(), p1.getCoordY());
//        }
//
//        SKPoint3D ULPoint = new SKPoint3D(centerPoint3D.getCoordX(), 
//                centerPoint3D.getCoordY() - radius, centerPoint3D.getCoordZ() + half_high);
//        SKPoint3D URPoint = new SKPoint3D(centerPoint3D.getCoordX(), 
//                centerPoint3D.getCoordY() + radius, centerPoint3D.getCoordZ() + half_high);
//        SKPoint3D LLPoint = new SKPoint3D(centerPoint3D.getCoordX(), 
//                centerPoint3D.getCoordY() - radius, centerPoint3D.getCoordZ() - half_high);
//        SKPoint3D LRPoint = new SKPoint3D(centerPoint3D.getCoordX(), 
//                centerPoint3D.getCoordY() + radius, centerPoint3D.getCoordZ() - half_high);
//
//        drawSegment(ULPoint, LLPoint, SettingConstants.LineStyle.DOT);
//        drawSegment(URPoint, LRPoint, SettingConstants.LineStyle.DEFAULT);
    }

    @Override
    public void saveCoordinates() {
        Circle.saveCoordinates();
    }

    @Override
    public void applyMove(Vector2D vector) {
    }

}
