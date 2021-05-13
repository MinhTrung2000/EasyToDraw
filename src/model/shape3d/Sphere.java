package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import control.myawt.SKPoint3D;
import java.awt.Color;
import java.util.ArrayList;
import model.shape2d.Ellipse;
import model.shape2d.Segment2D;
import model.shape2d.Vector2D;

public class Sphere extends Shape3D {

    private double radius = 0.0;

    private Ellipse circle = new Ellipse(markedChangeOfBoard, changedColorOfBoard,
            changedCoordOfBoard, filledColor);

    private ArrayList<SKPoint2D> circlePointList = new ArrayList<>();

    public Sphere(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);
    }

    public void setProperty(double center_x, double center_y, double center_z,
            double radius) {
        centerPoint3D.setLocation(center_x, center_y, center_z);
        this.radius = radius;

        circlePointList.clear();

        mergePointSetCircle(circlePointList, new SKPoint2D(
                centerPoint3D.getCoordX(), centerPoint3D.getCoordZ()), radius, true,
                true, true, true, true, true, true, true);

        pointSet3D.clear();

        for (int i = 0; i < circlePointList.size(); i++) {
            SKPoint2D p2d = circlePointList.get(i);
            pointSet3D.add(new SKPoint3D(p2d.getCoordX(), centerPoint3D.getCoordY(),
                    p2d.getCoordY()));
        }

        pointSet2D.clear();

        for (int i = 0; i < pointSet3D.size(); i++) {
            pointSet2D.add(pointSet3D.get(i).get2DRelativePosition());
        }
    }

    @Override
    public void drawOutline() {
        super.drawOutline();

        SKPoint2D p0 = getLeftTopPoint();
        SKPoint2D p1 = getRightBottomPoint();

        double a = Math.abs(p0.getCoordX() - p1.getCoordX()) / 2;
        double b = Math.abs(p0.getCoordY() - p1.getCoordY()) / 2;

        drawOutlineEllipse(centerPoint3D.get2DRelativePosition(), a, b, false, true, true, false);

        setLineStyle(SettingConstants.LineStyle.DOT);
        drawOutlineEllipse(centerPoint3D.get2DRelativePosition(), a, b, true, false, false, true);

        setLineStyle(DEFAULT_LINE_STYLE);
    }

    @Override
    public void saveCoordinates() {
        centerPoint3D.saveCoord(changedCoordOfBoard);
        SKPoint2D centerPointRelative2D = centerPoint3D.get2DRelativePosition();
        savePoint(centerPointRelative2D);
    }

    @Override
    public void applyMove(Vector2D vector) {
    }

}
