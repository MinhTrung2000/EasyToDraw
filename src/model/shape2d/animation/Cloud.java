/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.shape2d.animation;

import control.SettingConstants;
import java.awt.Color;
import control.myawt.SKPoint2D;
import control.util.Ultility;
import model.shape2d.Segment2D;
import model.shape2d.Shape2D;
import model.shape2d.Vector2D;

/**
 *
 * @author Minh Tu
 */
public class Cloud extends Shape2D {

    public static final int COULD_WIDTH = 18;
    public static final int SLIP_NUMBER = 18;
    public static final Color CLOUD_COLOR = Color.WHITE;

    private int slip = 0;
    private boolean forward = true;
    private int test;

    public Cloud(boolean[][] markedChangeOfBoard, Color[][] changedColorOfBoard, String[][] changedCoordOfBoard, Color filledColor) {
        super(markedChangeOfBoard, changedColorOfBoard, changedCoordOfBoard, filledColor);
    }

    public void setProperty(SKPoint2D centerPoint) {
        this.centerPoint2D = centerPoint;

        pointSet.clear();

        Segment2D.mergePointSet(this.pointSet, this.centerPoint2D, new SKPoint2D(this.centerPoint2D, COULD_WIDTH, 0));
        Segment2D.mergePointSet(this.pointSet, new SKPoint2D(this.centerPoint2D, -1, -1), new SKPoint2D(this.centerPoint2D, -1, -1 - 3));
        Segment2D.mergePointSet(this.pointSet, new SKPoint2D(this.centerPoint2D, COULD_WIDTH + 1, -1), new SKPoint2D(this.centerPoint2D, COULD_WIDTH + 1, -1 - 3));

        mergePointSetCircle(this.pointSet, new SKPoint2D(this.centerPoint2D, 5, -4), 6, true, true, false, false, false, false, true, true);
        mergePointSetCircle(this.pointSet, new SKPoint2D(this.centerPoint2D, COULD_WIDTH - 2, -4), 3, true, true, false, false, false, false, false, true);
        mergePointSetCircle(this.pointSet, new SKPoint2D(this.centerPoint2D, COULD_WIDTH - 6, -7), 3, true, true, false, false, false, false, true, true);
    }

    public void drawCloud() {
        Vector2D vectorSlip = new Vector2D(slip, 0);
        SKPoint2D centerPointToPaint = this.centerPoint2D.createMovingPoint(vectorSlip);

        for (int i = 0; i < pointSet.size(); i++) {
            SKPoint2D point = new SKPoint2D(pointSet.get(i));
            point.move(vectorSlip);
            savePoint(point.getCoordX(), point.getCoordY());
        }
        test = slip;
        if (slip >= 0 && slip / SettingConstants.RECT_SIZE < SLIP_NUMBER && forward) {
            if(test<= slip) {
                forward = true;
                slip += SettingConstants.RECT_SIZE -4;
            }
            
            else {
                forward = false;
            }
            
        } else {
            if(slip <= 0) forward = true;
            else {
                forward = false;
                slip -= SettingConstants.RECT_SIZE -4;
            }
            
        }
        
        Ultility.paint(changedColorOfBoard, markedChangeOfBoard, new SKPoint2D(centerPointToPaint, 1, -1), CLOUD_COLOR, false);
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
