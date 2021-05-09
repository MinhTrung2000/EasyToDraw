package model.shape3d;

import control.SettingConstants;
import control.myawt.SKPoint2D;
import java.awt.Color;
import control.myawt.SKPoint3D;
import control.util.Ultility;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public abstract class Shape3D extends Shape2D {

    protected SKPoint3D startPoint3D = new SKPoint3D();
    protected SKPoint3D endPoint3D = new SKPoint3D();
    protected SKPoint3D centerPoint3D = new SKPoint3D();

    public Shape3D(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {

    }

    protected boolean savePoint(double x, double y, double z) {
        SKPoint2D relativePoint = SKPoint3D.get2DRelativePosition(x, y, z).convertViewToMachineCoord();

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
        SKPoint2D relativePoint = SKPoint3D.get2DRelativePosition(x, y, z).convertViewToMachineCoord();

        int px = relativePoint.getCoordX();
        int py = relativePoint.getCoordY();

        if (Ultility.checkValidPoint(changedColorOfBoard, px, py)
                && Ultility.checkPixelPut(pixelCounter, lineStyle)) {
            markedChangeOfBoard[py][px] = true;
            changedColorOfBoard[py][px] = filledColor;
            pointSet.add(relativePoint);
            return true;
        }
        return false;
    }

    public void putEightSymmetricPoints(double x, double y, double z, double center_x, double center_y, boolean mode2) {
        pixelCounter++;
        savePointWithLineStyleCheck(y + center_x, -x + center_y, z, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(x + center_x, -y + center_y, z, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(-y + center_x, x + center_y, z, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(-x + center_x, y + center_y, z, pixelCounter, lineStyle);

        if (!mode2 || pixelCounter % 2 == 0) {
            savePointWithLineStyleCheck(y + center_x, x + center_y, z, pixelCounter, lineStyle);
            savePointWithLineStyleCheck(x + center_x, y + center_y, z, pixelCounter, lineStyle);
            savePointWithLineStyleCheck(-x + center_x, -y + center_y, z, pixelCounter, lineStyle);
            savePointWithLineStyleCheck(-y + center_x, -x + center_y, z, pixelCounter, lineStyle);
        }
    }

    public void putFourSymmetricPoints(double x, double y, double z, double center_x, double center_y, boolean mode2) {
        pixelCounter++;
        savePointWithLineStyleCheck(center_x + x, center_y + y, z, pixelCounter, lineStyle);
        savePointWithLineStyleCheck(center_x - x, center_y + y, z, pixelCounter, lineStyle);

        if (!mode2 || pixelCounter % 2 == 0) {
            savePointWithLineStyleCheck(center_x + x, center_y - y, z, pixelCounter, lineStyle);
            savePointWithLineStyleCheck(center_x - x, center_y - y, z, pixelCounter, lineStyle);
        }
    }
}
