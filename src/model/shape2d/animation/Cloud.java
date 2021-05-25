package model.shape2d.animation;

import control.SettingConstants;
import java.awt.Color;
import control.myawt.SKPoint2D;
import control.util.Ultility;
import model.shape2d.Segment2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

public class Cloud extends Shape2D {

    public static final int COULD_WIDTH = 18;
    public static final int SLIP_NUMBER = 10;
    public static final Color CLOUD_COLOR = new Color (254,254,254);

    private int slip = 0;
    private boolean nextMoveIsForward = true;
    private int test;

    public Cloud(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard,
            String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard,
                filledColor);
    }

    public void setProperty(SKPoint2D centerPoint) {
        this.centerPoint2D = centerPoint;

        pointSet2D.clear();

        Segment2D.mergePointSet(this.pointSet2D, this.centerPoint2D,
                new SKPoint2D(this.centerPoint2D, COULD_WIDTH, 0));

        Segment2D.mergePointSet(this.pointSet2D,
                new SKPoint2D(this.centerPoint2D, -1, -1),
                new SKPoint2D(this.centerPoint2D, -1, -1 - 3));

        Segment2D.mergePointSet(this.pointSet2D,
                new SKPoint2D(this.centerPoint2D, COULD_WIDTH + 1, -1),
                new SKPoint2D(this.centerPoint2D, COULD_WIDTH + 1, -1 - 3));

        mergePointSetCircle(this.pointSet2D,
                new SKPoint2D(this.centerPoint2D, 5, -4), 6,
                true, true, false, false, false, false, true, true);

        mergePointSetCircle(this.pointSet2D,
                new SKPoint2D(this.centerPoint2D, COULD_WIDTH - 2, -4), 3,
                true, true, false, false, false, false, false, true);

        mergePointSetCircle(this.pointSet2D, new SKPoint2D(this.centerPoint2D,
                COULD_WIDTH - 6, -7), 3,
                true, true, false, false, false, false, true, true);
    }

    public void drawCloud() {
        Vector2D vectorSlip = new Vector2D(slip, 0);

        SKPoint2D centerPointToPaint = this.centerPoint2D.createMove(vectorSlip);

        for (int i = 0; i < pointSet2D.size(); i++) {
            SKPoint2D point = new SKPoint2D(pointSet2D.get(i));
            point.move(vectorSlip);
            savePoint(point.getCoordX(), point.getCoordY());
        }


        if (slip >= 0 && slip / SettingConstants.RECT_SIZE < SLIP_NUMBER
                && nextMoveIsForward) {
                slip += SettingConstants.RECT_SIZE - 4;
            
        } else {
            if (slip <= 0) {
                nextMoveIsForward = true;
                slip += SettingConstants.RECT_SIZE - 4;
            } else {
                nextMoveIsForward = false;
                slip -= SettingConstants.RECT_SIZE - 4;
            }

        }

        Ultility.paint(changedColorOfBoard, markedChangeOfBoard,
                new SKPoint2D(centerPointToPaint, 1, -1), CLOUD_COLOR, false);
    }

    @Override
    public void applyMove(Vector2D vector) {

    }

    @Override
    public void saveCoordinates() {

    }

    @Override
    public void drawOutline() {

    }

    @Override
    public void setProperty(SKPoint2D startPoint, SKPoint2D endPoint) {

    }
}
