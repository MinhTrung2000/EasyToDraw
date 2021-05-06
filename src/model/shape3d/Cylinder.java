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

        UEllipse.setProperty(new SKPoint2D(center_x - radius, center_y - radius),
                new SKPoint2D(center_x + radius, center_y + radius)
        );

        LEllipse.setProperty(new SKPoint2D(center_x - radius, center_y - radius),
                new SKPoint2D(center_x + radius, center_y + radius)
        );
//        
//        UEllipse.drawOutline();
//        LEllipse.drawOutline();
    }

    @Override
    public void drawOutline() {
        System.out.println("model.shape3d.Cylinder.drawOutline()");
        double half_high = high / 2;

        ArrayList<SKPoint2D> list1 = UEllipse.getSetOfAllPoints(false);

        for (int i = 0; i < list1.size(); i++) {
            SKPoint2D p = list1.get(i);
            p = SKPoint3D.get2DRelativePosition(p.getCoordX(), p.getCoordY(), centerPoint3D.getCoordZ() + half_high).convertViewToMachineCoord();
            boolean res = savePoint(p.getCoordX(), p.getCoordY());
            System.out.println("Point " + p + " : " + res);
        }

        ArrayList<SKPoint2D> list2 = LEllipse.getSetOfAllPoints(true);

        System.out.println("=============================");

        for (int i = 0; i < list2.size(); i++) {
            SKPoint2D p = list2.get(i);
            p = SKPoint3D.get2DRelativePosition(p.getCoordX(), p.getCoordY(), centerPoint3D.getCoordZ() - half_high).convertViewToMachineCoord();
            boolean res = savePoint(p.getCoordX(), p.getCoordY());
            System.out.println("Point " + p + " : " + res);
        }

        SKPoint3D ULPoint = new SKPoint3D(centerPoint3D.getCoordX() - radius, centerPoint3D.getCoordY(), centerPoint3D.getCoordZ() + half_high);
        SKPoint3D URPoint = new SKPoint3D(centerPoint3D.getCoordX() + radius, centerPoint3D.getCoordY(), centerPoint3D.getCoordZ() + half_high);
        SKPoint3D LLPoint = new SKPoint3D(centerPoint3D.getCoordX() - radius, centerPoint3D.getCoordY(), centerPoint3D.getCoordZ() - half_high);
        SKPoint3D LRPoint = new SKPoint3D(centerPoint3D.getCoordX() + radius, centerPoint3D.getCoordY(), centerPoint3D.getCoordZ() - half_high);

        drawSegment(ULPoint, LLPoint, SettingConstants.LineStyle.DEFAULT);
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
