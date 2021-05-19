package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import java.awt.Color;
import control.myawt.SKPoint3D;
import control.util.Ultility;
import java.util.ArrayList;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public abstract class Shape3D extends Shape2D {

    protected SKPoint3D startPoint3D = new SKPoint3D();
    protected SKPoint3D endPoint3D = new SKPoint3D();
    protected SKPoint3D centerPoint3D = new SKPoint3D();

    // Now use pointSet2D to be a container of relative point in 2D from this.
    protected ArrayList<SKPoint3D> pointSet3D = new ArrayList<>();

    public Shape3D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {

    }

    /**
     * Save from pointSet3D to pointSet2D
     */
    public void setRelative2DPointSet() {
        if (pointSet3D.isEmpty()) {
            return;
        }

        pointSet2D.clear();

        for (int i = 0; i < pointSet3D.size(); i++) {
            pointSet2D.add(pointSet3D.get(i).get2DRelativePosition());
        }
    }

    protected boolean savePoint(double x, double y, double z) {
        SKPoint2D relativePoint = SKPoint3D.get2DRelativePosition(x, y, z).convertToSystemCoord();

        int px = relativePoint.getCoordX();
        int py = relativePoint.getCoordY();

        if (Ultility.checkValidPoint(changedColorOfBoard, px, py)) {
            markedChangeOfBoard[py][px] = true;
            changedColorOfBoard[py][px] = filledColor;
            return true;
        }
        return false;
    }

    public boolean savePointWithLineStyleCheck(double x, double y, double z, int pixelCounter, SettingConstants.LineStyle lineStyle) {
        SKPoint2D relativePoint = SKPoint3D.get2DRelativePosition(x, y, z).convertToSystemCoord();

        int px = relativePoint.getCoordX();
        int py = relativePoint.getCoordY();

        if (Ultility.checkValidPoint(changedColorOfBoard, px, py)
                && Ultility.checkPixelPut(pixelCounter, lineStyle)) {
            markedChangeOfBoard[py][px] = true;
            changedColorOfBoard[py][px] = filledColor;
            pointSet2D.add(relativePoint);
            return true;
        }
        return false;
    }

    @Override
    public void saveCoordinates() {
        centerPoint3D.saveCoord(changedCoordOfBoard);
        SKPoint2D centerPointRelative2D = centerPoint3D.get2DRelativePosition();
        savePoint(centerPointRelative2D);
    }

}
