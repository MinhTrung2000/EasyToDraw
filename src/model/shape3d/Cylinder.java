package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.myawt.SKPoint3D;
import java.awt.Color;
import java.util.ArrayList;
import model.shape2d.Ellipse;
import model.shape2d.Vector2D;

public class Cylinder extends Shape3D {

    private Ellipse UEllipse = new Ellipse(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    private Ellipse LEllipse = new Ellipse(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    private double radius = 0.0;
    private double high = 0.0;

    public Cylinder(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(double center_x, double center_y, double center_z, double radius, double high) {
        this.centerPoint3D.setLocation(center_x, center_y, center_z);
        this.radius = radius;
        this.high = high;

        UEllipse.setProperty(new SKPoint2D(center_x - radius, center_y + radius),
                new SKPoint2D(center_x + radius, center_y - radius)
        );

        UEllipse.setModal(Ellipse.Modal.CIRLCE);

        LEllipse.setProperty(new SKPoint2D(center_x - radius, center_y + radius),
                new SKPoint2D(center_x + radius, center_y - radius)
        );

        LEllipse.setModal(Ellipse.Modal.CIRLCE);
    }

    @Override
    public void drawOutline() {
        double half_high = (int) (high / 2);

        ArrayList<SKPoint2D> list1 = UEllipse.getSetOfAllPoints(true);

        Color cc = new Color(225, 239, 252);
        
        for (int k = 0; k < half_high; k++) {
            if (k < half_high - 1) {
                setFilledColor(cc);
            } else {
                setFilledColor(Color.BLACK);
            }

            for (int i = 0; i < list1.size(); i++) {
                SKPoint2D p = list1.get(i);
                SKPoint2D p1 = SKPoint3D.get2DRelativePosition(p.getCoordX(), p.getCoordY(), centerPoint3D.getCoordZ() + k).convertViewToMachineCoord();
                savePoint(p1.getCoordX(), p1.getCoordY());
                SKPoint2D p2 = SKPoint3D.get2DRelativePosition(p.getCoordX(), p.getCoordY(), centerPoint3D.getCoordZ() - k).convertViewToMachineCoord();
                savePoint(p2.getCoordX(), p2.getCoordY());
            }
        }

//        ArrayList<SKPoint2D> list2 = LEllipse.getSetOfAllPoints(true);
//
//        for (int i = 0; i < list2.size(); i++) {
//            SKPoint2D p = list2.get(i);
//            p = SKPoint3D.get2DRelativePosition(p.getCoordX(), p.getCoordY(), centerPoint3D.getCoordZ() - half_high).convertViewToMachineCoord();
//            savePoint(p.getCoordX(), p.getCoordY());
//        }
        SKPoint3D ULPoint = new SKPoint3D(centerPoint3D.getCoordX(), centerPoint3D.getCoordY() - radius, centerPoint3D.getCoordZ() + half_high);
        SKPoint3D URPoint = new SKPoint3D(centerPoint3D.getCoordX(), centerPoint3D.getCoordY() + radius, centerPoint3D.getCoordZ() + half_high);
        SKPoint3D LLPoint = new SKPoint3D(centerPoint3D.getCoordX(), centerPoint3D.getCoordY() - radius, centerPoint3D.getCoordZ() - half_high);
        SKPoint3D LRPoint = new SKPoint3D(centerPoint3D.getCoordX(), centerPoint3D.getCoordY() + radius, centerPoint3D.getCoordZ() - half_high);
        
        drawSegment(ULPoint, LLPoint, SettingConstants.LineStyle.DOT);
        drawSegment(URPoint, LRPoint, SettingConstants.LineStyle.DEFAULT);
    }

    @Override
    public void saveCoordinates() {
        UEllipse.saveCoordinates();
        LEllipse.saveCoordinates();
    }

    @Override
    public void applyMove(Vector2D vector) {
    }

}
